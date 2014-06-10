package uk.org.wookey.ICPlugin.MCP;

import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;

public class MCPSimpleEdit extends MCPHandler {
	private Logger _logger = new Logger("MCP SimpleEdit");
	
	public MCPSimpleEdit(WorldTab tab, MCPRoot mcpRoot) throws ParserException {
		super("dns-org-mud-moo-simpleedit", "1.0", "1.0", tab, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
		String commandName = command.getName();
		if (commandName.equalsIgnoreCase(name + "-content")) {
			handleContentCommand(command, key);
		}
	}
	
	private void handleContentCommand(MCPCommand command, String key) {
		_logger.logMsg("Content command.");
		if (command.isMultiLine()) {
			mcp.registerMultiline(command);
		}
		else {
			String ref = command.getParam("reference");
			String itemName = command.getParam("name:");
			String type = command.getParam("type");
			String content = command.getParam("content");
			
			_logger.logMsg("Ref='" + ref + "'");
			_logger.logMsg("Name='" + itemName + "'");
			_logger.logMsg("Type='" + type + "'");
			_logger.logMsg("Content='" + content + "'");
			
			new MCPEditorForm(itemName, ref, type, content, worldTab, key);
		}
	}
}
