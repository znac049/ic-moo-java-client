package dns.com.awns.timezone;

import java.util.TimeZone;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerConnection;
import uk.org.wookey.ICPlugin.MCP.MCP;
import uk.org.wookey.ICPlugin.MCP.MCPCommand;
import uk.org.wookey.ICPlugin.MCP.MCPHandler;

public class MCPTimezone extends MCPHandler implements Runnable {
	Logger _logger = new Logger("MCPTimezone");
	
	public MCPTimezone(ServerConnection svr, MCP mcpRoot) throws ParserException {
		super("dns-com-awns-timezone", "1.0", "1.0", svr, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
	}
	
	public void born() {
		new Thread(this, "mcp-awns-timezone: " + mcp.getWorldName()).start();
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
