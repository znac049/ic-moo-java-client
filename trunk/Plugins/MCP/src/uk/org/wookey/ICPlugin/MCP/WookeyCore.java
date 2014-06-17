package uk.org.wookey.ICPlugin.MCP;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.util.TimeZone;

import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerConnection;

public class WookeyCore  extends MCPHandler implements Runnable {
	private Logger _logger = new Logger("MCPWookeyCore");
	private WookeyCorePanel corePanel;
	
	public WookeyCore(ServerConnection svr, MCPRoot mcpRoot) throws ParserException {
		super("dns-uk-org-wookey-core", "1.0", "1.0", svr, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
		String commandName = command.getName();
		if (commandName.equalsIgnoreCase(name + "-maxobj")) {
			handleMaxObjCommand(command, key);
		}
		else if (commandName.equalsIgnoreCase(name + "-interests")) {
			handleInterestsCommand(command, key);
		}

	}
	
	private void handleMaxObjCommand(MCPCommand command, String key) {
		String id = command.getParam("oid");			

		_logger.logMsg("Maxobj command. Current max is " + id);
	}
	
	private void handleInterestsCommand(MCPCommand command, String key) {
		_logger.logMsg("Interests command.");
	}
	
	public void born() {
		addGUI();
		
		new Thread(this, "wookey-core: " + mcp.getWorldName()).start();
	}

	private void addGUI() {
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

	}
	public void run() {
		MCPCommand command = new MCPCommand();
		command.setName(name, "getmaxobj");
		command.setAuthKey(mcp.authKey);
		command.sendToServer(server);
		
		command.setName(name, "getinterests");
		command.sendToServer(server);
		
		while (true) {
			try {
				Thread.sleep(5000);
				
				corePanel.add(new JLabel("FredFredFredFredFredFred"), BorderLayout.PAGE_END);
				corePanel.revalidate();
				corePanel.repaint();
			} catch (InterruptedException e) {
				_logger.logError("Backend thread for " + getName() + " has been interrupted");
				return;
			}
		}
	}
}
