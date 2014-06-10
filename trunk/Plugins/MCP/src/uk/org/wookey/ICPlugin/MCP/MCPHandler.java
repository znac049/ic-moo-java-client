package uk.org.wookey.ICPlugin.MCP;

import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;

public class MCPHandler implements MCPHandlerInterface {
	private Logger logger = new Logger("MCPHandler");
	protected String name;
	private MCPVersion minVersion;
	private MCPVersion maxVersion;
	protected WorldTab worldTab;
	protected boolean negotiated;
	protected MCPRoot mcp;
	
	public MCPHandler(String pkgName, String min, String max, WorldTab tab, MCPRoot mcpRoot) throws ParserException {
		name = pkgName;
		minVersion = new MCPVersion(min);
		maxVersion = new MCPVersion(max);
		worldTab = tab;
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
	
	public void sendCommandToServer(String command) {
		MCPSession sess = new MCPSession();
		String auth = sess.getSessionKey();
		sendToServer(name + "-" + command + " " + auth);
	}
	
	public void sendToServer(String line) {
		logger.logMsg("MCP C->S: " + line);
		worldTab.getServerPort().writeLine(line);
	}
}
