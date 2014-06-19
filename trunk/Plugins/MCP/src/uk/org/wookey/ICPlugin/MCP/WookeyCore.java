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
	private WkCorePanel corePanel;
	public final static String packageName = "dns-uk-org-wookey-core";
	
	public WookeyCore(ServerConnection svr, MCPRoot mcpRoot) throws ParserException {
		super(packageName, "1.0", "1.0", svr, mcpRoot);
	}
	
	public void handle(MCPCommand command, String key) {
		String commandName = command.getName();
		if (commandName.equalsIgnoreCase(name + "-info")) {
			handleInfoCommand(command, key);
		}
		else if (commandName.equalsIgnoreCase(name + "-obj")) {
			handleObjCommand(command, key);
		}

	}
	
	private void handleInfoCommand(MCPCommand command, String key) {
		String maxObj = command.getParam("maxobj");			
		String playerObj = command.getParam("playerobj");	
		
		try {
			int num = WkObjectDB.decodeObjectNum(maxObj);
			WkObjectDB.setMaxObject(num);

			num = WkObjectDB.decodeObjectNum(playerObj);
			corePanel.setPlayerObj(num);
		}
		catch (MCPException e) {
			
		}

		_logger.logMsg("Info command. Player is " + playerObj + ", maxObject is " + maxObj);
		
		// Ask for info about the player object
		MCPCommand cmd = new MCPCommand();
		cmd.setAuthKey(mcp.authKey);
		cmd.setName(name, "getobj");
		cmd.addParam("objnum", playerObj.substring(1));
		cmd.sendToServer(server);
	}
	
	private void handleObjCommand(MCPCommand command, String key) {
		String obNum = command.getParam("objnum");
		String properties = command.getParam("properties");			
		String verbs = command.getParam("verbs");			
		String parents = command.getParam("parents");			

		_logger.logInfo("Obj command, objnum " + obNum);
		_logger.logInfo("  props: '" + properties + "'");
		_logger.logInfo("  verbs: '" + verbs + "'");
		_logger.logInfo("  parents: '" + parents + "'");
		
		corePanel.registerObject(mcp, WkObjectDB.decodeObjectNumNoEx(obNum), properties, verbs, parents);
		
		corePanel.revalidate();
		corePanel.repaint();
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
		
		corePanel = new WkCorePanel(server);
		rhs.add(corePanel, gbc);
		
		rhs.revalidate();
		rhs.repaint();

	}
	public void run() {
		MCPCommand command = new MCPCommand();
		command.setName(name, "getinfo");
		command.setAuthKey(mcp.authKey);
		command.sendToServer(server);
		
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				_logger.logError("Backend thread for " + getName() + " has been interrupted");
				return;
			}
		}
	}
}
