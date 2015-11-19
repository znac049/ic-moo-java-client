package dns.com.awns.status;

import java.awt.TrayIcon;

import Application.MainApplication;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerConnection;
import uk.org.wookey.ICPlugin.MCP.MCP;
import uk.org.wookey.ICPlugin.MCP.MCPCommand;
import uk.org.wookey.ICPlugin.MCP.MCPHandler;

public class MCPStatus extends MCPHandler {
	private Logger _logger = new Logger("MCP Status");
	
	public MCPStatus(ServerConnection svr, MCP mcpRoot) throws ParserException {
		super("dns-com-awns-status", "1.0", "1.0", svr, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
		String text = command.getParam("text");
		
		_logger.logInfo("TEXT='" + text + "'");
		
		MainApplication.getAppWindow().getTrayIcon().displayMessage(mcp.getWorldTab().getName(), text, TrayIcon.MessageType.NONE);
	}
	
	public void born() {
		_logger.logInfo("No thread needed");
	}
}
