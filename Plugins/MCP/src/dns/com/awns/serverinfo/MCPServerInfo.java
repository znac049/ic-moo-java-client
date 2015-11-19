package dns.com.awns.serverinfo;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerConnection;
import uk.org.wookey.ICPlugin.MCP.MCP;
import uk.org.wookey.ICPlugin.MCP.MCPCommand;
import uk.org.wookey.ICPlugin.MCP.MCPHandler;

public class MCPServerInfo extends MCPHandler implements Runnable {
	private Logger _logger = new Logger("MCP ServerInfo");
	
	public MCPServerInfo(ServerConnection svr, MCP mcpRoot) throws ParserException {
		super("dns-com-awns-serverinfo", "1.0", "1.0", svr, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
		String commandName = command.getName();
		if (commandName.equalsIgnoreCase(name)) {
			String homeUrl = command.getParam("home_url");			
			String helpUrl = command.getParam("help_url");			
			
			_logger.logMsg("Home='" + homeUrl + "'");
			_logger.logMsg("Help='" + helpUrl + "'");
			
			mcp.getWorldTab().setHomePage(homeUrl);
			mcp.getWorldTab().setHelpPage(helpUrl);
		}
	}
	
	public void born() {
		new Thread(this, "vis: " + mcp.getWorldName()).start();
	}
	
	public void run() {
		MCPCommand command = new MCPCommand();
		command.setName(name);
		
		command.setAuthKey(mcp.authKey);
		mcp.queueOutgoingCommand(command);
		//command.sendToServer(server);
	}
}

