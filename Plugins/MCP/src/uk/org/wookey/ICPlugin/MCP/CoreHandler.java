package uk.org.wookey.ICPlugin.MCP;

import java.util.ArrayList;

import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.ParserException;

public class CoreHandler extends MCPHandler {
	private final String commands[] = {"*", ":", "mcp"};
	private ArrayList<MCPCommand> _multilineSessions;
	private ArrayList<MCPCommand> _pendingSessions;
	
	public CoreHandler(WorldTab sess, MCPRoot mcpRoot) throws ParserException {
		super("dns-uk-org-wookey-core", "1.0", "1.0", sess, mcpRoot);
		
		_multilineSessions = new ArrayList<MCPCommand>();
		_pendingSessions = new ArrayList<MCPCommand>();
		
		negotiated = true;
	}

	public void handle(MCPCommand command, String key) {
		//_logger.logMsg("MCP Core: " + command.getName());
		
		String commandName = command.getName();
		
		if (commandName.equals("*")) {
			handleMultilineCommand(command);
		}
		else if (commandName.equals(":")) {
			completeMultilineCommand(command);
		}
		else if (commandName.equals("mcp")) {

		}
	}
	
	private void completeMultilineCommand(MCPCommand command) {
		String dataTag = command.getKey();
		//_logger.logMsg("Look for a multiline keymatch for " + dataTag);
		for (int i=0; i<_multilineSessions.size(); i++) {
			MCPCommand session = _multilineSessions.get(i);
			
			if (dataTag.equals(session.getParam("_data-tag"))) {
				//_logger.logMsg("Great - its a completion of command '" + session.getName() + "'");
				session.clearMultiline();
				_pendingSessions.add(session);
				_multilineSessions.remove(i);
				return;
			}
		}
	}
	
	private void handleMultilineCommand(MCPCommand command) {
		String dataTag = command.getKey();
		//_logger.logMsg("Look for a multiline keymatch for " + dataTag);
		for (int i=0; i<_multilineSessions.size(); i++) {
			MCPCommand session = _multilineSessions.get(i);
			
			if (dataTag.equals(session.getParam("_data-tag"))) {
				//_logger.logMsg("Great - its a continuation of command '" + session.getName() + "'");

				// The command passed in should only have one parameter which is the name of the 
				// parameter to append to.
				ArrayList<MCPParam> paras = command.getParams();
				String paramName = paras.get(0).getKey();			
				if (paramName != null) {
					session.appendParam(paramName + "*", command.getParam(paramName) + '\n');
				}
				return;
			}
		}
	}

	public boolean handlesCommand(String cmd) {
		for (int i=0; i<commands.length; i++) {
			if (cmd.equalsIgnoreCase(commands[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public void registerMultiline(MCPCommand command) {
		//_logger.logMsg("register multiline handler: data-tag is " + command.getParam("_data-tag"));
		
		_multilineSessions.add(command);
	}
	
	public MCPCommand getPendingCommand() {
		if (_pendingSessions.size() > 0) {
			MCPCommand command = _pendingSessions.get(0);
			_pendingSessions.remove(0);
			
			return command;
		}
		
		return null;
	}
}
