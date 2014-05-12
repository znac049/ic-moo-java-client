package uk.org.wookey.IC.GUI;

import java.awt.*;
import javax.swing.*;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.WorldCache;
import uk.org.wookey.IC.Utils.WorldDetail;
import webBoltOns.layoutManager.*;

public class WorldDetailsPanel extends JPanel {
	private static final long serialVersionUID = 7832805464430880015L;
	private final Logger _logger = new Logger("NewWorldDetails");
	private JTextField saveName;
	private JTextField serverName;
	private JTextField serverPort;
	private JTextField playerName;
	private JPasswordField playerPassword;
	private JCheckBox autoConnect;
	private JCheckBox autoLogin;
	private JCheckBox localEcho;
	private JCheckBox mcpSupport;
	private MCPDetailPanel mcpPanel;

	public WorldDetailsPanel() {
		super();
		
		setLayout(new GridFlowLayout(6, 4));

		saveName = new JTextField(20);
		addComp("Save As:", saveName);
		
		serverName = new JTextField(20);
		addComp("Server:", serverName);
		
		JLabel lab = new JLabel("Port:");
		add(lab, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 3));
		
		serverPort = new JTextField(5);
		lab.setLabelFor(serverPort);
		add(serverPort, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 4));
		
		autoConnect = new JCheckBox("Auto Connect");
		add(autoConnect, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		autoLogin = new JCheckBox("Auto Login");
		add(autoLogin, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		playerName = new JTextField(20);
		addComp("Player Name:", playerName);
		
		playerPassword = new JPasswordField(20);
		playerPassword.setEchoChar('X');
		addComp("Player Password:", playerPassword);
		
		localEcho = new JCheckBox("Local echo");
		add(localEcho, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		mcpSupport = new JCheckBox("MCP 2.1 Support");
		mcpSupport.setSelected(true);
		add(mcpSupport, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		mcpPanel = new MCPDetailPanel();
		add(mcpPanel, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
	}
	
	private void addComp(String label, Component comp) {
		JLabel lab = new JLabel(label, JLabel.TRAILING);
		add(lab, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));

		lab.setLabelFor(comp);
		add(comp, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 2));		
	}

	public void clearDetails() {
		saveName.setText("");
		autoConnect.setSelected(false);
		serverName.setText("");
		serverPort.setText("");
		autoLogin.setSelected(false);
		playerName.setText("");
		playerPassword.setText("");
		localEcho.setSelected(true);
		mcpSupport.setSelected(true);
	}
	
	public void loadDetails(Object o) {
		String worldName = o.toString();
		WorldCache cache = new WorldCache();
		WorldDetail detail = cache.getWorld(worldName);
		
		_logger.logMsg("Load details for: " + worldName);

		saveName.setText(worldName);
		autoConnect.setSelected(detail.getAutoConnect());
		serverName.setText(detail.getServerName());
		serverPort.setText(Integer.toString(detail.getServerPort()));
		autoLogin.setSelected(detail.getAutoLogin());
		playerName.setText(detail.getUserName());
		playerPassword.setText(detail.getUserPassword());
		localEcho.setSelected(detail.getLocalEcho());
		mcpSupport.setSelected(detail.getLocalEcho());
	}

	public void saveDetails() {
		String worldName = saveName.getText();
		WorldCache cache = new WorldCache();
		WorldDetail detail = cache.getWorld(worldName);
				
		_logger.logMsg("Save details for: " + worldName);

		if (!worldName.equals("")) {
			detail.setWorldName(worldName);

			detail.setAutoConnect(autoConnect.isSelected());
			detail.setServerName(serverName.getText());
			
			String port = serverPort.getText();
			int portNum = -1;
			if (!port.equals("")) {
				portNum = Integer.parseInt(port);
			}
			detail.setServerPort(portNum);
			
			detail.setAutoLogin(autoLogin.isSelected());
			detail.setUserName(playerName.getText());
			
			char pw[] = playerPassword.getPassword();
			String password = "";
			for (int i=0; i<pw.length; i++) {
				password = password + pw[i];
			}
			detail.setUserPassword(password);

			detail.setLocalEcho(localEcho.isSelected());
			detail.setMCPEnabled(mcpSupport.isSelected());

			clearDetails();
		}
	}

	public void deleteWorld() {
		String worldName = saveName.getText();
		
		_logger.logMsg("Delete details for: " + worldName);

		if (!worldName.equals("")) {
			WorldCache cache = new WorldCache();

			cache.deleteEntry(worldName);
			clearDetails();
			_logger.logMsg("World '" + worldName + "' deleted.");
		}
	}
}
