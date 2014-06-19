package uk.org.wookey.ICPlugin.MCP;

import java.util.TimeZone;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerConnection;

public class MCPTimezone extends MCPHandler implements Runnable {
	Logger _logger = new Logger("MCPTimezone");
	
	public MCPTimezone(ServerConnection svr, MCP mcpRoot) throws ParserException {
		super("dns-com-awns-timezone", "1.0", "1.0", svr, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
	}
	
	public void born() {
		new Thread(this, "tz: " + mcp.getWorldName()).start();
	}
	
	public void run() {
		String tz = TimeZone.getDefault().getID();
		
		_logger.logInfo("TZ=" + tz);
		
		MCPCommand command = new MCPCommand();
		command.setName(name);
		
		command.setAuthKey(mcp.authKey);
		command.addParam("timezone", tz);
		
		mcp.queueOutgoingCommand(command);
		//command.sendToServer(server);
	}
}
