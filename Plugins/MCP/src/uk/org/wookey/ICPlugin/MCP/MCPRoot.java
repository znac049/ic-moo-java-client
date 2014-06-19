package uk.org.wookey.ICPlugin.MCP;

import java.util.ArrayList;
import java.util.LinkedList;

import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.IOPluginInterface;
import uk.org.wookey.IC.Utils.Line;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.IOPlugin;
import uk.org.wookey.IC.Utils.ServerConnection;

public class MCPRoot extends IOPlugin {
	private static int worldCounter = 0;
	private Logger _logger = new Logger("MCP Root");
	private MCPVersion _minVer;
	private MCPVersion _maxVer;
	public MCPSession _mcpSession;
	private WookeyCore _core;
	private ArrayList<MCPHandler> _handlers;
	private String outOfBandToken = "#$#";
	public String authKey;
    private ArrayList<MCPCommand> _pendingSessions;
    private ArrayList<MCPCommand> _multilineSessions;
    private volatile LinkedList<MCPCommand> _outgoingCommandQueue;
    private Thread commandRunner;

	public MCPRoot(ServerConnection svr, WorldTab tab) throws ParserException {
		_minVer = new MCPVersion("2.1");
		_maxVer = new MCPVersion("2.1");
		_mcpSession = new MCPSession();
		_handlers = new ArrayList<MCPHandler>();
		
		_pendingSessions = new ArrayList<MCPCommand>();
		_multilineSessions = new ArrayList<MCPCommand>();
		_outgoingCommandQueue = new LinkedList<MCPCommand>();

		authKey = "ic0" + System.currentTimeMillis() % 10;
		
		try {
			attach(svr, tab);

			_core = new WookeyCore(svr, this);
			_handlers.add(_core);
			_handlers.add(new MCPNegotiate(svr, this));
			_handlers.add(new MCPSimpleEdit(svr, this));
			//_handlers.add(new MCPVisual(svr, this));
			_handlers.add(new MCPServerInfo(svr, this));
			_handlers.add(new MCPTimezone(svr, this));
		} catch (ParserException e) {
			// Any handler that generates an exception just gets binned.
			_logger.logMsg("Caught MCP Exception");
			_logger.printBacktrace(e);
		}
		
		// Start a thread for coordinating messages to the server
		commandRunner = new Thread(new CommandRunner());
		commandRunner.start();
	}

	@Override
	public Status remoteLineIn(Line l) {
		String line = l.get();
		MCPCommand cmd = new MCPCommand();
		
		try {
			cmd.parseLine(line);
		} catch (ParserException e) {
			_logger.logMsg("Badly formed MCP line:");
			_logger.printBacktrace(e);
		}
		
		execute(cmd);
		
		// Before we go on, check that there aren't any pending commands
		// to run as a consequence of this command.
		MCPCommand pending = getPendingCommand();
		while (pending != null) {
			//_logger.logInfo("execute pending command: '" + cmd.getName() + "'");
			execute(pending);
			pending = getPendingCommand();
		}
		
		return IOPluginInterface.Status.CONSUMED;
	}
	
	public void registerMultiline(MCPCommand command) {
		_multilineSessions.add(command);
	}
	
	public void activate(String name, String minVersion, String maxVersion) {
		for (int i=0; i< _handlers.size(); i++) {
			MCPHandler hand = _handlers.get(i);
			
			if (hand.getName().equalsIgnoreCase(name)) {
				hand.born();
			}
		}
	}

	private boolean execute(MCPCommand cmd) {
		//Do we recognise the command?
		
		/* 
		 * A few possibilities here:
		 * 
		 * 1. it's the initial handshake (command will be "mcp")
		 * 
		 * 2. it's a new single line command. Hopefully we can pass it to a registered handler.
		 * 
		 * 3. it's a new multiline command. We have to hang on to it until we've received any continuation lines plus the final termination line
		 * 
		 * 4. it's a continuation line (hopefully for a command we have already been sent)
		 * 
		 * 5. it's a termination line (hopefully for a command we've already been sent)
		*/
		String cmdName = cmd.getName();
		
		//_logger.logError("executing command '" + cmdName + "'");
		
		if (cmdName.equalsIgnoreCase("mcp")) {
			return handleInitialMcpCommand(cmd);
		}
		else if (cmdName.equals("*")) {
			// multiline continuation
			return handleMcpContinuation(cmd);
		}
		else if (cmdName.equals(":")) {
			//termination of multiline
			return handleMcpTermination(cmd);
		}
		else {
			// single or multiline command
			if (cmd.isMultiLine()) {
				//_logger.logInfo("Multiline partial");
				return handleMcpPartial(cmd);
			}
			else {
				//_logger.logInfo("Singleline");
				return handleCompleteMcpCommand(cmd);
			}
		}

		//return false;
	}
	
	private boolean handleCompleteMcpCommand(MCPCommand cmd) {
		MCPHandler h = findHandler(cmd.getName());
		
		if (h != null) {
			//_logger.logMsg("Handler for '" + cmd.getName() + "' is " + h.getName());
			
			h.handle(cmd, cmd.getAuthKey());
			return true;
		}
		
		_logger.logError("No handler for MCP command '" + cmd.getName() + "'");

		return false;
	}

	private boolean handleMcpTermination(MCPCommand cmd) {
		String dataTag = cmd.getAuthKey();
		
        for (int i=0; i<_multilineSessions.size(); i++) {
            MCPCommand session = _multilineSessions.get(i);
           
            if (dataTag.equals(session.getParam("_data-tag"))) {
                    //_logger.logSuccess("Great - its a completion of command '" + session.getName() + "'");
                    session.clearMultiline();
                    _pendingSessions.add(session);
                    _multilineSessions.remove(i);
                    return true;
            }   
        }
        
		return false;
	}

	private boolean handleMcpPartial(MCPCommand cmd) {
		//_logger.logInfo("Register command as incomplete: " + cmd.getName());
		
		_multilineSessions.add(cmd);
		
		return true;
	}
	
	private boolean handleMcpContinuation(MCPCommand cmd) {
		String dataTag = cmd.getAuthKey();
		
		//_logger.logInfo("continuation - data tag is '" + dataTag + "'");
		
		for (MCPCommand session: _multilineSessions) {
			//_logger.logInfo("  session tag is '" + session.getParam("_data-tag") + "'");
			
			if (dataTag.equals(session.getParam("_data-tag"))) {
				//_logger.logInfo("Found a session to append the continuation data to");
				
				if (cmd.getParamCount() != 1) {
					_logger.logError("Continuation lines should only have data for a single parameter, but we found " + cmd.getParamCount());
					return false;
				}
				
				//_logger.logInfo("Continuation for param " + cmd.getParamName(0));
				session.appendParam(cmd.getParamName(0) + "*", cmd.getParam(0) + '\n');
				
				return true;
			}
		}
		
		_logger.logError("Didn't find a saved session with a matching data tag");
		
		return false;
	}
	
	private boolean handleInitialMcpCommand(MCPCommand cmd) {
		// The other end supports mcp - hurrah!
		try {
			_minVer = _minVer.min(cmd.getParam("version"));
			_maxVer = _maxVer.min(cmd.getParam("to"));
			
			// Tell the other end that we do MCP too!
			sendToServer("#$#mcp authentication-key: " + authKey + " version: " + _minVer + " to: " + _maxVer);

			//Tell the other end what we can do...
			for (int i=0; i<_handlers.size(); i++) {
				MCPHandler h = _handlers.get(i);
				sendToServer("#$#mcp-negotiate-can " + authKey + " package: " + h.getName() + " min-version: " + h.getMinVersion() + " max-version: " + h.getMaxVersion());
			}
			sendToServer("#$#mcp-negotiate-end " + authKey);
		}
		catch (ParserException e) {
			// Do nothing
			_logger.logMsg("Caught an MCP Exception");
			_logger.printBacktrace(e);
			return false;
		}
		
		return true;		
	}
	
	public void sendMCPCommand(String command) {
		sendToServer(outOfBandToken + command);
	}
	
	private void sendToServer(String line) {
		_logger.logMsg("MCP C->S: " + line);
		if (server != null) {
			server.writeLine(line);
		}
		else {
			_logger.logError("server is NULL in MCP.sendToServer");
		}
	}
	
	private MCPHandler findHandler(String command) {
		for (int i=0; i<_handlers.size(); i++) {
			MCPHandler h = _handlers.get(i);
			
			if (h.handlesCommand(command)) {
				return h;
			}
		}
		return null;
	}
	
	public String getWorldName() {
		worldCounter++;
		
		return "World #" + worldCounter;
	}
	
	public MCPCommand getPendingCommand() {
        if (_pendingSessions.size() > 0) {
                MCPCommand command = _pendingSessions.get(0);
                _pendingSessions.remove(0);
               
                return command;
        }
       
        return null;
	}

	public class CommandRunner implements Runnable {
		private Logger _logger = new Logger("CommandRunner");
		
		public void run() {
			_logger.logInfo("CommandRunner thread started");
			try {
				while (true) {
					if (_outgoingCommandQueue.size() > 0) {
						MCPCommand cmd = _outgoingCommandQueue.removeFirst();
						
						_logger.logInfo("Sending queued command");
						cmd.sendToServer(server);
					}
					
					// Don't be too aggressive - also gives us a chance to pick
					// up any pending interrupts
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
				_logger.logError("CommandRunner thread interrupted", e);
				
				return;
			}
		}
	}

}
