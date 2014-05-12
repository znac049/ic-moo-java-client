package uk.org.wookey.IC.Interfaces;

import uk.org.wookey.IC.MCP.MCPCommand;

public interface MCPHandlerInterface {
	String getName();
	String getMinVersion();
	String getMaxVersion();
	boolean handlesCommand(String cmd);
	void handle(MCPCommand cmd, String key);
	void sendCommandToServer(String cmd);
	void sendToServer(String cmd);
	void born();
}
