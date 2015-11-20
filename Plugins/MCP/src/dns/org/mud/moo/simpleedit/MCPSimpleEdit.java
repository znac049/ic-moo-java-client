package dns.org.mud.moo.simpleedit;

import dns.uk.org.wookey.core.ObjectDB;
import dns.uk.org.wookey.core.WookeyCoreHandler;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerConnection;
import uk.org.wookey.ICPlugin.MCP.MCP;
import uk.org.wookey.ICPlugin.MCP.MCPCommand;
import uk.org.wookey.ICPlugin.MCP.MCPHandler;

public class MCPSimpleEdit extends MCPHandler {
	private Logger _logger = new Logger("MCP SimpleEdit");
	
	public MCPSimpleEdit(ServerConnection svr, MCP mcpRoot) throws ParserException {
		super("dns-org-mud-moo-simpleedit", "1.0", "1.0", svr, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
		String commandName = command.getName();
		if (commandName.equalsIgnoreCase(name + "-content")) {
			handleContentCommand(command, key);
		}
	}
	
	private void handleContentCommand(MCPCommand command, String key) {
		_logger.logMsg("Content command.");
		_logger.logError("KEY IS " + key);
		
		if (command.isMultiLine()) {
			mcp.registerMultiline(command);
		}
		else {
			String ref = command.getParam("reference");
			String itemName = command.getParam("name:");
			String type = command.getParam("type");
			String content = command.getParam("content");
			
			// If the WookeyCore package is active, tell it what we're editing
			if (mcp.packageActive("dns-uk-org-wookey-core")) {
				int colon = ref.indexOf(':');
				
				if (colon != -1) {
					String objNum = ref.substring(0, colon);
					
					_logger.logInfo("Tell WookeyCore about object " + objNum);
					WookeyCoreHandler handler = (WookeyCoreHandler) mcp.findHandler("dns-uk-org-wookey-core");
					
					if (handler != null) {
						handler.loadObject(ObjectDB.decodeObjectNumNoEx(objNum));
					}
				}
			}
			
			_logger.logMsg("Ref='" + ref + "'");
			_logger.logMsg("Name='" + itemName + "'");
			_logger.logMsg("Type='" + type + "'");
			_logger.logMsg("Content='" + content + "'");
			
			String fileExt = ".txt";
			String saveName = itemName;
			
			if (itemName.startsWith("Verb: ")) {
				saveName = itemName.substring(6);
				fileExt = ".moo";
			}
			else if (type.equalsIgnoreCase("string-list")) {
				fileExt = ".note";
			}
			
			saveName = saveName.replace(":",  "-");
			saveName = saveName.replace("@",  "_at_");
			
			MCPEditorForm form = new MCPEditorForm(saveName, ref, type, content, mcp, server, key);
			form.setSaveName(saveName + fileExt);
		}
	}
}
