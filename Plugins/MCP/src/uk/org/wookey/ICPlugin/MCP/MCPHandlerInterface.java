package uk.org.wookey.ICPlugin.MCP;

public interface MCPHandlerInterface {
	String getName();
	String getMinVersion();
	String getMaxVersion();
	boolean handlesCommand(String cmd);
	void handle(MCPCommand cmd, String key);
	void sendToServer(MCPCommand cmd);
	void born();
	boolean isEnabled();
}
