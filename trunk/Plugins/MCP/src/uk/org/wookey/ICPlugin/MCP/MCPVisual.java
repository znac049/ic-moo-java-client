package uk.org.wookey.ICPlugin.MCP;

import java.awt.GridBagConstraints;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.LED;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerPort;

public class MCPVisual extends MCPHandler implements Runnable {
	private Logger _logger = new Logger("MCP Visual");
	private ArrayList<Player> players;
	private PlayerList playerList;
	
	public MCPVisual(ServerPort svr, MCPRoot mcpRoot) throws ParserException {
		super("dns-com-awns-visual", "1.0", "1.0", svr, mcpRoot);
		
		players = new ArrayList<Player>();
	}
	
	public void handle(MCPCommand command, String key) {
		String commandName = command.getName();
		if (commandName.equalsIgnoreCase(name + "-location")) {
			handleLocationCommand(command, key);
		}
		else if (commandName.equalsIgnoreCase(name + "-users")) {
			handleUsersCommand(command, key);
		}
		else if (commandName.equalsIgnoreCase(name + "-topology")) {
			handleTopologyCommand(command, key);
		}
		else if (commandName.equalsIgnoreCase(name + "-self")) {
			handleSelfCommand(command, key);
		}
	}
	
	private void handleLocationCommand(MCPCommand command, String key) {
		_logger.logMsg("Location command.");
		String id = command.getParam("id");			
		
		_logger.logMsg("Id='" + id + "'");
	}
	
	private void handleUsersCommand(MCPCommand command, String key) {
		if (command.isMultiLine()) {
			mcp.registerMultiline(command);
		}
		else {
			String id = command.getParam("id");
			String playerName = command.getParam("name");
			String location = command.getParam("location");
			String idle = command.getParam("idle");
				
			String names[] = playerName.split("\n");
			String ids[] = id.split("\n");
			String locs[] = location.split("\n");
			String idles[] = idle.split("\n");
			players.clear();
			for (int i=0; i<names.length; i++) {
				players.add(new Player(names[i], ids[i], locs[i], idles[i]));
			}
			
			playerList.setData(players);
		}
	}
	
	private void handleTopologyCommand(MCPCommand command, String key) {
		_logger.logMsg("Topology command.");
		String id = command.getParam("id");
		String playerName = command.getParam("name");
		String exit = command.getParam("exit");
		String idle = command.getParam("idle");
			
		_logger.logMsg("Id='" + id + "'");
		_logger.logMsg("Name='" + playerName + "'");
		_logger.logMsg("Exit='" + exit + "'");
		_logger.logMsg("Idle='" + idle + "'");
	}
	
	private void handleSelfCommand(MCPCommand command, String key) {
		_logger.logMsg("Self command.");
		String id = command.getParam("id");

		_logger.logMsg("Id='" + id + "'");
	}

	public void born() {
		addGUI();
		new Thread(this, "vis: " + mcp.getWorldName()).start();
	}
	
	private void addGUI() {
		JPanel lhs = mcp.getWorldTab().getPanel(WorldTab.LEFT_SIDEBAR);
		GridBagConstraints gbc = new GridBagConstraints();
		
		_logger.logInfo("Adding to lhs side panel...");
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 1.0;
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		
		playerList = new PlayerList();
		
		lhs.add(playerList, gbc);
		
		lhs.revalidate();
		lhs.repaint();
	}
	
	public void run() {
		while (true) {
			MCPCommand command = new MCPCommand();
			command.setName(name, "getusers");
		
			command.setAuthKey(mcp.authKey);
		
			command.sendToServer(server);

			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
