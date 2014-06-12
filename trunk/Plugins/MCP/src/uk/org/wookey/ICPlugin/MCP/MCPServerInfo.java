package uk.org.wookey.ICPlugin.MCP;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerPort;

public class MCPServerInfo extends MCPHandler implements Runnable {
	private Logger _logger = new Logger("MCP ServerInfo");
	
	public MCPServerInfo(ServerPort svr, MCPRoot mcpRoot) throws ParserException {
		super("dns-com-awns-serverinfo", "1.0", "1.0", svr, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
		String commandName = command.getName();
		if (commandName.equalsIgnoreCase(name)) {
			String homeUrl = command.getParam("home_url");			
			String helpUrl = command.getParam("help_url");			
			
			_logger.logMsg("Home='" + homeUrl + "'");
			_logger.logMsg("Help='" + helpUrl + "'");
		}
	}
	
	public void born() {
		new Thread(this, "vis: " + mcp.getWorldName()).start();
	}
	
	public void run() {
		MCPCommand command = new MCPCommand();
		command.setName(name);
		
		command.setKey(mcp.authKey);
		command.sendToServer(server);
	}
}

