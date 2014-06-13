package uk.org.wookey.ICPlugin.MCP;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerPort;

public class MCPTimezone extends MCPHandler implements Runnable {
	public MCPTimezone(ServerPort svr, MCPRoot mcpRoot) throws ParserException {
		super("dns-com-awns-timezone", "1.0", "1.0", svr, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
	}
	
	public void born() {
		new Thread(this, "tz: " + mcp.getWorldName()).start();
	}
	
	public void run() {
		MCPCommand command = new MCPCommand();
		command.setName(name);
		
		command.setAuthKey(mcp.authKey);
		command.addParam("timezone", "UTC");
		
		command.sendToServer(server);
	}
}
