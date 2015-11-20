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

public class WookeyCoreHandler  extends MCPHandler implements Runnable {
	private Logger _logger = new Logger("MCPWookeyCore");
	private TreePanel treePanel;
	private DetailPanel detailPanel;
	private ObjectDB objectDB;	
	
	public final static String packageName = "dns-uk-org-wookey-core";
	
	public WookeyCoreHandler(ServerConnection svr, MCP mcp) throws ParserException {
		super(packageName, "1.0", "1.0", svr, mcp);
		
		detailPanel = null;
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
			int num = ObjectDB.decodeObjectNum(maxObj);
			objectDB.setMaxObject(num);

			num = ObjectDB.decodeObjectNum(playerObj);
			treePanel.setPlayerObj(num);
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
		
		treePanel.registerObject(mcp, ObjectDB.decodeObjectNumNoEx(obNum), obName, properties, verbs, parents);
		
		treePanel.revalidate();
		treePanel.repaint();
	}
	
	private void handlePropCommand(MCPCommand command, String key) {
		String propName = command.getParam("propname");
		String obNum = command.getParam("objnum");
		String owner = command.getParam("owner");
		String perms = command.getParam("perms");

		_logger.logInfo("Prop command,  #" + obNum + "." + propName);
		_logger.logInfo("  owner: '" + owner + "'");
		_logger.logInfo("  perms: '" + perms + "'");
		
		MooObject ob;
		try {
			ob = objectDB.getObject(Integer.parseInt(obNum));
		} catch (NumberFormatException | MCPException e) {
			_logger.logError("Bad object number '" + obNum + "' in response", e);
			return;
		}

		Property prop = ob.getProperty(propName);
		if (prop == null) {
			_logger.logError("No property '" + propName + "' on object '" + ob.getName() + "' (#" + ob.getObjNum() + ")");
			return;
		}
		
		prop.setDetail(owner, perms);
		
		detailPanel.revalidate();
		detailPanel.repaint();
	}
	
	private void handleVerbCommand(MCPCommand command, String key) {
		String verbName = command.getParam("verbname");
		String obNum = command.getParam("objnum");
		String owner = command.getParam("owner");
		String perms = command.getParam("perms");
		String name = command.getParam("name");
		String direct = command.getParam("direct");
		String prep = command.getParam("prep");
		String indirect = command.getParam("indirect");

		_logger.logInfo("Verb command,  #" + obNum + ":" + verbName);
		_logger.logInfo("  owner: '" + owner + "'");
		_logger.logInfo("  perms: '" + perms + "'");
		_logger.logInfo("  name: '" + name + "'");
		_logger.logInfo("  direct: '" + direct + "'");
		_logger.logInfo("  prep: '" + prep + "'");
		_logger.logInfo("  indirect: '" + indirect + "'");
		
		//corePanel.revalidate();
		//corePanel.repaint();
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
		objectDB = new ObjectDB(this);
		
		addGUI();
		
		new Thread(this, "wookey-core: " + mcp.getWorldName()).start();
	}

	private void addGUI() {
		JPanel lhs = (JPanel) mcp.getWorldTab().getLeftPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		
		MainPanel parentPanel = new MainPanel();

		treePanel = new TreePanel(getMCP(), server, objectDB);
		detailPanel = new DetailPanel(treePanel);
		
		parentPanel.setTopComponent(treePanel);
		parentPanel.setBottomComponent(detailPanel);
		
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
