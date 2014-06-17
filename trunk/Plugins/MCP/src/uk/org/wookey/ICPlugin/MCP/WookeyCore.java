package uk.org.wookey.ICPlugin.MCP;

import java.awt.GridBagConstraints;
import java.util.TimeZone;

import javax.swing.JPanel;

import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerPort;

public class WookeyCore  extends MCPHandler implements Runnable {
	private Logger _logger = new Logger("MCPTimezone");
	private WookeyCorePanel corePanel;
	
	public WookeyCore(ServerPort svr, MCPRoot mcpRoot) throws ParserException {
		super("dns-uk-org-wookey-core", "1.0", "1.0", svr, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
	}
	
	public void born() {
		JPanel rhs = mcp.getWorldTab().getPanel(WorldTab.LEFT_SIDEBAR);
		GridBagConstraints gbc = new GridBagConstraints();
		
		_logger.logInfo("Adding to rhs side panel...");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		
		corePanel = new WookeyCorePanel();
		rhs.add(corePanel, gbc);
		
		rhs.revalidate();
		rhs.repaint();
		
		new Thread(this, "wookey-core: " + mcp.getWorldName()).start();
	}
	
	public void run() {
		MCPCommand command = new MCPCommand();
		command.setName(name, "getmaxobj");
		command.setAuthKey(mcp.authKey);
		
		command.sendToServer(server);
	}
}
