package uk.org.wookey.ICPlugin.MCP;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerConnection;

public class MCPHandler implements MCPHandlerInterface {
	private Logger logger = new Logger("MCPHandler");
	protected String name;
	private MCPVersion minVersion;
	private MCPVersion maxVersion;
	protected ServerConnection server;
	protected boolean negotiated;
	protected MCP mcp;
	
	public MCPHandler(String pkgName, String min, String max, ServerConnection svr, MCP mcpRoot) throws ParserException {
		name = pkgName;
		minVersion = new MCPVersion(min);
		maxVersion = new MCPVersion(max);
		server = svr;
		mcp = mcpRoot;
		negotiated = false;
	}
	
	public void born() {
		logger.logMsg("Birth of '" + name + "'");
		negotiated = true;
	}
	
	public void handle(MCPCommand command, String key) {
		logger.logMsg("handle '" + command.getName() + "'. This should never happen");
	}
	
	public String getName() {
		return name;
	}
	
	public String getMinVersion() {
		return minVersion.toString();
	}
	
	public String getMaxVersion() {
		return maxVersion.toString();
	}
	
	public boolean handlesCommand(String cmd) {
		if (cmd.startsWith(getName())) {
			return true;
		}
		
		return false;
	}
	
	public void ZZZZZsendCommandToServer(String command) {
		MCPSession sess = new MCPSession();
		String auth = sess.getSessionKey();
		ZZZZZsendToServer(name + "-" + command + " " + auth);
	}
	
	public void ZZZZZsendToServer(String line) {
		logger.logMsg("C->S: " + line);
		server.writeLine(line);
	}
	
	public MCP getMCP() {
		return mcp;
	}

	@Override
	public void sendToServer(MCPCommand cmd) {
		mcp.queueOutgoingCommand(cmd);
	}
}
