package uk.org.wookey.ICPlugin.MCP;

import java.util.ArrayList;

import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.IOPluginInterface;
import uk.org.wookey.IC.Utils.Line;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.IOPlugin;

public class MCPRoot extends IOPlugin {
	private Logger _logger = new Logger("MCP Root");
	private MCPVersion _minVer;
	private MCPVersion _maxVer;
	public MCPSession _mcpSession;
	private CoreHandler _core;
	private ArrayList<MCPHandler> _handlers;
	private WorldTab worldTab;
	private String outOfBandToken = "#$#";

	public MCPRoot(WorldTab tab) throws ParserException {
		_minVer = new MCPVersion("2.1");
		_maxVer = new MCPVersion("2.1");
		worldTab = tab;
		_mcpSession = new MCPSession();
		_handlers = new ArrayList<MCPHandler>();
		
		//_handlers.clear();
		
		try {
			_core = new CoreHandler(worldTab, this);
			_handlers.add(_core);
			_handlers.add(new MCPNegotiate(worldTab, this));
			_handlers.add(new MCPSimpleEdit(worldTab, this));
			_handlers.add(new MCPVisual(worldTab, this));
		} catch (ParserException e) {
			// Any handler that generates an exception just gets binned.
			_logger.logMsg("Caught MCP Exception");
			_logger.printBacktrace(e);
		}
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
		
		line = execute(cmd);
		
		// Before we go on, check that there aren't any pending commands
		// to run as a consequence of this command.
		MCPCommand pending = _core.getPendingCommand();
		while (pending != null) {
			execute(pending);
			pending = _core.getPendingCommand();
		}
		
		return IOPluginInterface.Status.CONSUMED;
	}
	
	public void registerMultiline(MCPCommand command) {
		_core.registerMultiline(command);
	}
	
	public void activate(String name, String minVersion, String maxVersion) {
		for (int i=0; i< _handlers.size(); i++) {
			MCPHandler hand = _handlers.get(i);
			
			if (hand.getName().equalsIgnoreCase(name)) {
				hand.born();
			}
		}
	}

	private String execute(MCPCommand cmd) {
		//Do we recognise the command?
		String cmdName = cmd.getName();
		if (cmdName.equalsIgnoreCase("mcp")) {
			// The other end supports mcp - hurrah!
			try {
				_minVer = _minVer.min(cmd.getParam("version"));
				_maxVer = _maxVer.min(cmd.getParam("to"));
				
				// Tell the other end that we do MCP too!
				String key = _mcpSession.getSessionKey();
				sendToServer("#$#mcp authentication-key: " + key + " version: " + _minVer + " to: " + _maxVer);
				//sendToServer("#$#mcp-negotiate-can " + key + " package: mcp-negotiate min-version: 1.0 max-version: 2.0");

				//Tell the other end what we can do...
				for (int i=0; i<_handlers.size(); i++) {
					MCPHandler h = _handlers.get(i);
					sendToServer("#$#mcp-negotiate-can " + key + " package: " + h.getName() + " min-version: " + h.getMinVersion() + " max-version: " + h.getMaxVersion());
				}
				sendToServer("#$#mcp-negotiate-end " + key);
			}
			catch (ParserException e) {
				// Do nothing
				_logger.logMsg("Caught an MCP Exception");
				_logger.printBacktrace(e);
				return cmd.getLine();
			}
			
			return null;
		}
		else {
			// Take a look through our registered handlers for someone to take this
			MCPHandler h = findHandler(cmdName);
			
			if (h != null) {
				//_logger.logMsg("Handler for '" + cmdName + "' is " + h.getName());
				
				h.handle(cmd, _mcpSession.getSessionKey());
				return null;
			}
			else {
				_logger.logMsg("No handler for MCP command '" + cmdName + "'");
			}
		}

		return cmd.getLine();
	}
	
	public void sendMCPCommand(String command) {
		sendToServer(outOfBandToken + command);
	}
	
	private void sendToServer(String line) {
		_logger.logMsg("MCP C->S: " + line);
		worldTab.getServerPort().writeLine(line);
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
		return worldTab.getName();
	}
	
	public boolean worldIsVisible() {
		return worldTab.tabVisible();
	}
	
	public WorldTab getWorldTab() {
		return worldTab;
	}
}
