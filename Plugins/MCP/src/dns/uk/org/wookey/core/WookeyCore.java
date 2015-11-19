package dns.uk.org.wookey.core;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerConnection;
import uk.org.wookey.ICPlugin.MCP.MCP;
import uk.org.wookey.ICPlugin.MCP.MCPCommand;
import uk.org.wookey.ICPlugin.MCP.MCPException;
import uk.org.wookey.ICPlugin.MCP.MCPHandler;

public class WookeyCore  extends MCPHandler implements Runnable {
	private Logger _logger = new Logger("MCPWookeyCore");
	private WkCoreTreePanel corePanel;
	private WkObjectDB objectDB;
	
	public final static String packageName = "dns-uk-org-wookey-core";
	
	public WookeyCore(ServerConnection svr, MCP mcp) throws ParserException {
		super(packageName, "1.0", "1.0", svr, mcp);
	}
	
	public void handle(MCPCommand command, String key) {
		String commandName = command.getName();
		if (commandName.equalsIgnoreCase(name + "-info")) {
			handleInfoCommand(command, key);
		}
		else if (commandName.equalsIgnoreCase(name + "-obj")) {
			handleObjCommand(command, key);
		}
		else if (commandName.equalsIgnoreCase(name + "-prop")) {
			handlePropCommand(command, key);
		}
		else if (commandName.equalsIgnoreCase(name + "-verb")) {
			handleVerbCommand(command, key);
		}
	}
	
	private void handleInfoCommand(MCPCommand command, String key) {
		String maxObj = command.getParam("maxobj");			
		String playerObj = command.getParam("playerobj");	
		
		try {
			int num = WkObjectDB.decodeObjectNum(maxObj);
			objectDB.setMaxObject(num);

			num = WkObjectDB.decodeObjectNum(playerObj);
			corePanel.setPlayerObj(num);
		}
		catch (MCPException e) {
			
		}

		//_logger.logMsg("Info command. Player is " + playerObj + ", maxObject is " + maxObj);
		
		// Ask for info about the player object
		MCPCommand cmd = new MCPCommand();
		cmd.setAuthKey(mcp.authKey);
		cmd.setName(name, "getobj");
		cmd.addParam("objnum", playerObj.substring(1));
		mcp.queueOutgoingCommand(cmd);
		//cmd.sendToServer(server);
	}
	
	private void handleObjCommand(MCPCommand command, String key) {
		String obName = command.getParam("name");
		String obNum = command.getParam("objnum");
		String properties = command.getParam("properties");			
		String verbs = command.getParam("verbs");			
		String parents = command.getParam("parents");			

		//_logger.logInfo("Obj command, objnum " + obNum);
		//_logger.logInfo("  props: '" + properties + "'");
		//_logger.logInfo("  verbs: '" + verbs + "'");
		//_logger.logInfo("  parents: '" + parents + "'");
		
		corePanel.registerObject(mcp, WkObjectDB.decodeObjectNumNoEx(obNum), obName, properties, verbs, parents);
		
		corePanel.revalidate();
		corePanel.repaint();
	}
	
	private void handlePropCommand(MCPCommand command, String key) {
		String obName = command.getParam("name");
		String obNum = command.getParam("objnum");
		String properties = command.getParam("properties");			
		String verbs = command.getParam("verbs");			
		String parents = command.getParam("parents");			

		//_logger.logInfo("Obj command, objnum " + obNum);
		//_logger.logInfo("  props: '" + properties + "'");
		//_logger.logInfo("  verbs: '" + verbs + "'");
		//_logger.logInfo("  parents: '" + parents + "'");
		
		corePanel.registerObject(mcp, WkObjectDB.decodeObjectNumNoEx(obNum), obName, properties, verbs, parents);
		
		corePanel.revalidate();
		corePanel.repaint();
	}
	
	private void handleVerbCommand(MCPCommand command, String key) {
		String obName = command.getParam("name");
		String obNum = command.getParam("objnum");
		String properties = command.getParam("properties");			
		String verbs = command.getParam("verbs");			
		String parents = command.getParam("parents");			

		//_logger.logInfo("Obj command, objnum " + obNum);
		//_logger.logInfo("  props: '" + properties + "'");
		//_logger.logInfo("  verbs: '" + verbs + "'");
		//_logger.logInfo("  parents: '" + parents + "'");
		
		corePanel.registerObject(mcp, WkObjectDB.decodeObjectNumNoEx(obNum), obName, properties, verbs, parents);
		
		corePanel.revalidate();
		corePanel.repaint();
	}
	
	public void loadObject(int objNum) {
		if (objNum != -1) {
			//try {
				//WkObject ob = WkObjectDB.getObject(objNum);
				
				if (!objectDB.objectExists(objNum)) {
					MCPCommand cmd = new MCPCommand();
					cmd.setAuthKey(mcp.authKey);
					cmd.setName(packageName, "getobj");
					cmd.addParam("objnum", ""+objNum);
					mcp.queueOutgoingCommand(cmd);
				}
			//} catch (MCPException e) {
			//	_logger.logError("Failed to send object info to WookeyCore", e);
			//}
		}
	}
	
	public void born() {
		objectDB = new WkObjectDB(mcp);
		addGUI();
		
		new Thread(this, "wookey-core: " + mcp.getWorldName()).start();
	}

	private void addGUI() {
		JPanel lhs = (JPanel) mcp.getWorldTab().getLeftPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		
		WkCorePanel parentPanel = new WkCorePanel();

		corePanel = new WkCoreTreePanel(getMCP(), server, objectDB);
		parentPanel.setTopComponent(corePanel);
		parentPanel.setBottomComponent(new WkCorePropsPanel(corePanel));
		
		_logger.logInfo("Adding to lhs side panel...");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		
		lhs.add(parentPanel, gbc);

		lhs.setMinimumSize(new Dimension(300, 100));
		lhs.setSize(lhs.getMinimumSize());
		
		mcp.getWorldTab().resetToPreferredSizes();
	}
	
	public void run() {
		MCPCommand command = new MCPCommand();
		command.setName(name, "getinfo");
		command.setAuthKey(mcp.authKey);
		mcp.queueOutgoingCommand(command);
		//command.sendToServer(server);
		
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
