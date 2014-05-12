package uk.org.wookey.IC.MCP;

import java.util.ArrayList;

import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.Player;

public class MCPVisual extends MCPHandler implements Runnable {
	private Logger _logger = new Logger("MCP Visual");
	private ArrayList<Player> players;
	
	public MCPVisual(WorldTab tab, MCPRoot mcpRoot) throws MCPException {
		super("dns-com-awns-visual", "1.0", "1.0", tab, mcpRoot);
		
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
			
			mcp.getWorldTab().getVisInfo().getPlayerList().setData(players);
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
		new Thread(this, "vis: " + mcp.getWorldName()).start();
	}
	
	public void run() {
		String command = name + "-getusers " + mcp._mcpSession.getSessionKey();
		
		while (true) {
			mcp.sendMCPCommand(command);

			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
