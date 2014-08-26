package uk.org.wookey.ICPlugin.MCP;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

import uk.org.wookey.IC.Utils.ConfigInterface;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerConnection;

public class MCPHandler implements MCPHandlerInterface, ConfigInterface {
	private static boolean handlerGloballyEnabled = true;
	private Logger logger = new Logger("MCPHandler");
	protected String name;
	private MCPVersion minVersion;
	private MCPVersion maxVersion;
	protected ServerConnection server;
	protected boolean negotiated;
	protected MCP mcp;
	protected JCheckBox enabled;
	
	private boolean instanceEnabled = true;
	
	public MCPHandler(String pkgName, String min, String max, ServerConnection svr, MCP mcpRoot) throws ParserException {
		name = pkgName;
		minVersion = new MCPVersion(min);
		maxVersion = new MCPVersion(max);
		server = svr;
		mcp = mcpRoot;
		negotiated = false;
		
		enabled = new JCheckBox(name);
		enabled.setSelected(isEnabled());
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

	public MCP getMCP() {
		return mcp;
	}
	
	public boolean isMandatory() {
		// Most handlers are not mandatory
		return false;
	}

	@Override
	public void sendToServer(MCPCommand cmd) {
		mcp.queueOutgoingCommand(cmd);
	}

	@Override
	public JComponent getConfigUI(int configType) {
		if (configType == ConfigInterface.CONFIG_GLOBAL) {
			enabled.setSelected(handlerGloballyEnabled);
		}
		else {
			enabled.setSelected(isEnabled());
		}
		
		return enabled;
	}

	@Override
	public boolean saveConfig(int configType) {
		if (configType == ConfigInterface.CONFIG_GLOBAL) {
			handlerGloballyEnabled = enabled.isSelected();
			if (!handlerGloballyEnabled) {
				instanceEnabled = false;
			}
		}
		else {
			instanceEnabled = enabled.isSelected();
		}

		return false;
	}

	@Override
	public boolean isEnabled() {
		logger.logInfo("MCP handler is enabled");
		if (!MCPHandler.handlerGloballyEnabled) {
			logger.logInfo("Globall disabled.");
			return false;
		}
		
		if (instanceEnabled) {
			logger.logInfo("Handler locally enabled");
		}
		else {
			logger.logInfo("Handler locally disabled");
		}
		
		return instanceEnabled;
	}
}
