package uk.org.wookey.IC.GUI;

import java.awt.*;

import javax.swing.*;

import uk.org.wookey.IC.Factories.PluginFactory;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.WorldCache;
import uk.org.wookey.IC.Utils.WorldSettings;
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
	private JTabbedPane pluginTabs;
	
	public static final String AUTOCONNECT = "Autoconnect";
	public static final String SERVER = "Server";
	public static final String PORT = "Port";
	public static final String AUTOLOGIN = "Autologin";
	public static final String USERNAME = "Username";
	public static final String PASSWORD = "Password";
	public static final String LOCALECHO = "LocalEcho";

	public WorldDetailsPanel() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridFlowLayout(6, 4));
		mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel pluginPanel = new JPanel();
		pluginPanel.setLayout(new FlowLayout());
		
		saveName = new JTextField(20);
		addComp(mainPanel, "Save As:", saveName);
		
		serverName = new JTextField(20);
		addComp(mainPanel, "Server:", serverName);
		
		JLabel lab = new JLabel("Port:");
		mainPanel.add(lab, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 3));
		
		serverPort = new JTextField(5);
		lab.setLabelFor(serverPort);
		mainPanel.add(serverPort, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 4));
		
		autoConnect = new JCheckBox("Auto Connect");
		mainPanel.add(autoConnect, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		autoLogin = new JCheckBox("Auto Login");
		mainPanel.add(autoLogin, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		playerName = new JTextField(20);
		addComp(mainPanel, "Player Name:", playerName);
		
		playerPassword = new JPasswordField(20);
		playerPassword.setEchoChar('X');
		addComp(mainPanel, "Player Password:", playerPassword);
		
		localEcho = new JCheckBox("Local echo");
		mainPanel.add(localEcho, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		add(mainPanel, BorderLayout.NORTH);
		
		pluginTabs = new JTabbedPane();
		if (PluginFactory.populateSettingsTabs(pluginTabs)) {
			// Only add the plugin tabs if there are any plugins!
			add(pluginTabs, BorderLayout.SOUTH);
		}

		//mcpPanel = new MCPDetailPanel();
		//add(mcpPanel, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
	}
	
	private void addComp(JPanel panel, String label, Component comp) {
		JLabel lab = new JLabel(label, JLabel.TRAILING);
		panel.add(lab, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));

		lab.setLabelFor(comp);
		panel.add(comp, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 2));		
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
	}
	
	public void loadDetails(Object o) {
		String worldName = o.toString();
		WorldCache cache = new WorldCache();
		WorldSettings detail = cache.getWorld(worldName);
		
		_logger.logMsg("Load details for: " + worldName);

		saveName.setText(worldName);
		autoConnect.setSelected(detail.getBoolean(AUTOCONNECT));
		serverName.setText(detail.getString(SERVER));
		serverPort.setText(Integer.toString(detail.getInt(PORT)));
		
		autoLogin.setSelected(detail.getBoolean(AUTOLOGIN));
		playerName.setText(detail.getString(USERNAME));
		playerPassword.setText(detail.getString(PASSWORD));
		localEcho.setSelected(detail.getBoolean(LOCALECHO));
	}

	public void saveDetails() {
		String worldName = saveName.getText();
		WorldCache cache = new WorldCache();
		WorldSettings detail = cache.getWorld(worldName);
				
		_logger.logMsg("Save details for: " + worldName);

		if (!worldName.equals("")) {
			detail.set(AUTOCONNECT, autoConnect.isSelected());
			
			String port = serverPort.getText();
			int portNum = -1;
			if (!port.equals("")) {
				portNum = Integer.parseInt(port);
			}
			detail.set(SERVER, serverName.getText());
			detail.set(PORT, portNum);
			
			detail.set(AUTOLOGIN, autoLogin.isSelected());
			detail.set(USERNAME, playerName.getText());
			detail.set(PASSWORD, playerPassword.getPassword());

			detail.set(LOCALECHO, localEcho.isSelected());

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
