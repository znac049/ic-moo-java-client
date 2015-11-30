package uk.org.wookey.ICPlugin.MCP;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JLabel;

import mcp.negotiate.MCPNegotiate;
import dns.com.awns.serverinfo.MCPServerInfo;
import dns.com.awns.status.MCPStatus;
import dns.com.awns.timezone.MCPTimezone;
import dns.com.awns.visual.MCPVisual;
import dns.org.mud.moo.simpleedit.MCPSimpleEdit;
import dns.uk.org.wookey.core.WookeyCoreHandler;
import uk.org.wookey.IC.GUI.GlobalConfigPanel;
import uk.org.wookey.IC.Utils.CorePluginInterface;
import uk.org.wookey.IC.Utils.IOPluginInterface;
import uk.org.wookey.IC.Utils.Line;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.IOPlugin;

public class MCP extends IOPlugin {
	private Logger _logger = new Logger("MCP Root");

	private final String outOfBandToken = "#$#";

	private MCPVersion _minVer;
	private MCPVersion _maxVer;
	public MCPSession _mcpSession;
	private WookeyCoreHandler _core;
	private ArrayList<MCPHandler> _handlers;
	public String authKey;
	
    private ArrayList<MCPCommand> _multilineSessions;

    private LinkedList<MCPCommand> _incomingCommandQueue;
    private volatile MCPCommandQueue _outgoingCommandQueue;
    
    private Thread commandRunner;

	public MCP() throws ParserException {
		_minVer = new MCPVersion("2.1");
		_maxVer = new MCPVersion("2.1");
		_mcpSession = new MCPSession();
		_handlers = new ArrayList<MCPHandler>();
		
		_incomingCommandQueue = new LinkedList<MCPCommand>();
		_multilineSessions = new ArrayList<MCPCommand>();
		
		_outgoingCommandQueue = new MCPCommandQueue();

		authKey = "ic0" + System.currentTimeMillis() % 10;
		
		try {
			_core = new WookeyCoreHandler(server, this);
			_handlers.add(_core);
			_handlers.add(new MCPNegotiate(server, this));
			_handlers.add(new MCPSimpleEdit(server, this));
			_handlers.add(new MCPVisual(server, this));
			_handlers.add(new MCPServerInfo(server, this));
			_handlers.add(new MCPTimezone(server, this));
			_handlers.add(new MCPStatus(server, this));
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
	public void initGlobalSettings(GlobalConfigPanel conf) {
		conf.add(new JLabel("MCP Packages"));
		for (MCPHandler h: _handlers) {
			if (!h.isMandatory()) {
				conf.addItem(h.getConfigUI(CONFIG_GLOBAL));
			}
		}
		
		conf.registerConfigHandler(this);
	}

	@Override
	public boolean supports(CorePluginInterface.PluginType pluginType) {
		switch (pluginType) {
		case IOPLUGIN:
			return true;
			
		default:
			break;
		}
		
		return true;
	}

	@Override
	public Status remoteLineIn(Line l) {
		String line = l.get();
		MCPCommand cmd = new MCPCommand();
		
		if (!line.startsWith(outOfBandToken)) {
			return IOPluginInterface.Status.IGNORED;
		}
		
		//_logger.logInfo("Got: '" + line + "'");
		
		line = line.substring(outOfBandToken.length());

		try {
			cmd.parseLine(line);
		} catch (ParserException e) {
			_logger.logMsg("Badly formed MCP line:");
			_logger.printBacktrace(e);
		}
		
		execute(cmd);
		
		// Before we go on, check that there aren't any pending commands
		// to run as a consequence of this command.
		while (queuedInputCommandAvailable()) {
			MCPCommand pending = getQueuedInputCommand();
			//_logger.logInfo("execute pending command: '" + cmd.getName() + "'");
			execute(pending);
		}
		
		return IOPluginInterface.Status.CONSUMED;
	}
	
	@Override
	public boolean activate() {
		setName("MCP");
		
		return true;
	}
	
	@Override
	public void deactivate() {
		_logger.logError("MCP.deactivate needs to be coded, Bob");
	}
	
	public void activatePackage(String name, String minVersion, String maxVersion) {
		for (MCPHandler hand: _handlers) {			
			if (hand.getName().equalsIgnoreCase(name)) {
				hand.born();
			}
		}
	}
	
	public boolean packageActive(String packageName) {
		for (MCPHandler hand: _handlers) {			
			if (hand.getName().equalsIgnoreCase(packageName)) {
				return true;
			}
		}
		
		return false;
	}

	public void registerMultiline(MCPCommand command) {
		_multilineSessions.add(command);
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
                    _incomingCommandQueue.addLast(session);
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
	
	private boolean handleInitialMcpCommand(MCPCommand mcpCmd) {
		// The other end supports mcp - hurrah!
		try {
			_minVer = _minVer.min(mcpCmd.getParam("version"));
			_maxVer = _maxVer.min(mcpCmd.getParam("to"));
			
			// Tell the other end that we do MCP too!
			MCPCommand cmd = new MCPCommand();
			cmd.setName("mcp");
			cmd.addParam("authentication-key", authKey);
			cmd.addParam("version", _minVer);
			cmd.addParam("to", _maxVer);
			
			queueOutgoingCommand(cmd);
			//sendToServer("#$#mcp authentication-key: " + authKey + " version: " + _minVer + " to: " + _maxVer);

			//Tell the other end what we can do...
			for (int i=0; i<_handlers.size(); i++) {
			}
			for (MCPHandler h: _handlers) {
				cmd.clear();
				
				cmd.setName("mcp-negotiate", "can");
				cmd.setAuthKey(authKey);
				
				cmd.addParam("package", h.getName());
				cmd.addParam("min-version", h.getMinVersion());
				cmd.addParam("max-version", h.getMaxVersion());

				queueOutgoingCommand(cmd);
				//sendToServer("#$#mcp-negotiate-can " + authKey + " package: " + h.getName() + " min-version: " + h.getMinVersion() + " max-version: " + h.getMaxVersion());
			}
			
			cmd.clear();
			cmd.setName("mcp-negotiate", "end");
			cmd.setAuthKey(authKey);
			queueOutgoingCommand(cmd);
			//sendToServer("#$#mcp-negotiate-end " + authKey);
		}
		catch (ParserException e) {
			// Do nothing
			_logger.logMsg("Caught an MCP Exception");
			_logger.printBacktrace(e);
			return false;
		}
		
		return true;		
	}
	
	public MCPHandler findHandler(String command) {
		for (MCPHandler h: _handlers) {
			if (h.handlesCommand(command)) {
				return h;
			}
		}
		
		return null;
	}
	
	public String getWorldName() {
		return "World '" + worldTab.getName();
	}
	
	private boolean queuedInputCommandAvailable() {
		return (_incomingCommandQueue.size() > 0)?true:false;
	}
	
	private MCPCommand getQueuedInputCommand() {
		if (_incomingCommandQueue.size() > 0) {
			MCPCommand command = _incomingCommandQueue.removeFirst();
	               
			return command;
        }
       
        return null;
	}
	
	public void queueOutgoingCommand(MCPCommand cmd, int priority) {
		_outgoingCommandQueue.queueCommand(new MCPCommand(cmd), priority);
		_logger.logInfo("Added command " + cmd.getName() + " to queue " + priority + ". New length is " + _outgoingCommandQueue.getQueueLength(priority));
	}
	
	public void queueOutgoingCommand(MCPCommand cmd) {
		queueOutgoingCommand(cmd, MCPCommandQueue.normalPriority);
	}
	
	@Override
	public boolean saveConfig(int configType) {
		_logger.logInfo("Save MCP config");
		
		for (MCPHandler h: _handlers) {
			h.saveConfig(configType);
		}

		return true;
	}

	private class CommandRunner implements Runnable {
		private Logger _logger = new Logger("CommandRunner");
		
		public void run() {
			_logger.logError("CommandRunner thread started");
			try {
				while (true) {
					int delay = 5;
					MCPCommand cmd = null;
					
					if (_outgoingCommandQueue.getQueueLength(MCPCommandQueue.highPriority) > 0) {
						cmd = _outgoingCommandQueue.getQueuedCommand(MCPCommandQueue.highPriority);
					}
					else if (_outgoingCommandQueue.getQueueLength(MCPCommandQueue.normalPriority) > 0) {
						cmd = _outgoingCommandQueue.getQueuedCommand(MCPCommandQueue.normalPriority);
						delay = 10;
					}
					else if (_outgoingCommandQueue.getQueueLength(MCPCommandQueue.lowPriority) > 0) {
						cmd = _outgoingCommandQueue.getQueuedCommand(MCPCommandQueue.lowPriority);
						delay = 100;
					}
 
					if (cmd != null) {
						_logger.logError("Sending queued command " + cmd.getName());
						cmd.sendToServer(server);
					}
					
					// Don't be too aggressive - also gives us a chance to pick
					// up any pending interrupts
					Thread.sleep(delay);
				}
			} catch (InterruptedException e) {
				_logger.logError("CommandRunner thread interrupted", e);
				
				return;
			}
		}
	}
}
