package uk.org.wookey.ICPlugin.MCP;

import javax.swing.JCheckBox;

public interface MCPHandlerInterface {
	String getName();
	String getMinVersion();
	String getMaxVersion();
	boolean handlesCommand(String cmd);
	void handle(MCPCommand cmd, String key);
	void sendToServer(MCPCommand cmd);
	void born();
	JCheckBox getGlobalEnabledCheckBox();
}
