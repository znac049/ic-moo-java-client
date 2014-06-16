package uk.org.wookey.ICPlugin.MCP;

import java.util.TimeZone;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerPort;

public class WookeyCore  extends MCPHandler implements Runnable {
	Logger _logger = new Logger("MCPTimezone");
	
	public WookeyCore(ServerPort svr, MCPRoot mcpRoot) throws ParserException {
		super("dns-uk-org-wookey-core", "1.0", "1.0", svr, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
	}
	
	public void born() {
		new Thread(this, "wookey-core: " + mcp.getWorldName()).start();
	}
	
	public void run() {
		MCPCommand command = new MCPCommand();
		command.setName(name, "getmaxobj");
		command.setAuthKey(mcp.authKey);
		
		command.sendToServer(server);
	}
}
