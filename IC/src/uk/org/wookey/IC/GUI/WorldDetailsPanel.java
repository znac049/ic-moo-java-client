package uk.org.wookey.IC.GUI;

import java.awt.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.*;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.newUtils.Prefs;
import webBoltOns.layoutManager.*;

public class WorldDetailsPanel extends JPanel {
	private static final long serialVersionUID = 7832805464430880015L;
	private final Logger _logger = new Logger("WorldDetailsPanel");
	private JTextField saveName;
	private JTextField serverName;
	private JTextField serverPort;
	private JTextField playerName;
	private JPasswordField playerPassword;
	private JCheckBox autoConnect;
	private JCheckBox autoLogin;
	private JCheckBox localEcho;
	
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
		Preferences prefs = Prefs.node(Prefs.WorldsRoot, worldName);
		
		_logger.logMsg("Load details for: " + worldName);

		saveName.setText(worldName);
		autoConnect.setSelected(prefs.getBoolean(Prefs.AUTOCONNECT, false));
		serverName.setText(prefs.get(Prefs.SERVER, ""));
		serverPort.setText(Integer.toString(prefs.getInt(Prefs.PORT, -1)));
		
		autoLogin.setSelected(prefs.getBoolean(Prefs.AUTOLOGIN, false));
		playerName.setText(prefs.get(Prefs.USERNAME, ""));
		playerPassword.setText(prefs.get(Prefs.PASSWORD, ""));
		localEcho.setSelected(prefs.getBoolean(Prefs.LOCALECHO, true));
	}

	public void saveDetails() {
		String worldName = saveName.getText();
		Preferences prefs = Prefs.node(Prefs.WorldsRoot, worldName);
				
		_logger.logMsg("Save details for: " + worldName);

		if (!worldName.equals("")) {
			prefs.putBoolean(Prefs.AUTOCONNECT, autoConnect.isSelected());
			
			String port = serverPort.getText();
			int portNum = -1;
			if (!port.equals("")) {
				portNum = Integer.parseInt(port);
			}
			prefs.put(Prefs.SERVER, serverName.getText());
			prefs.putInt(Prefs.PORT, portNum);
			
			prefs.putBoolean(Prefs.AUTOLOGIN, autoLogin.isSelected());
			prefs.put(Prefs.USERNAME, playerName.getText());
			prefs.put(Prefs.PASSWORD, playerPassword.getPassword().toString());

			prefs.putBoolean(Prefs.LOCALECHO, localEcho.isSelected());

			clearDetails();
		}
	}

	public void deleteWorld() {
		String worldName = saveName.getText();
		
		_logger.logMsg("Delete details for: " + worldName);

		if (!worldName.equals("")) {
			Preferences prefs = Prefs.node(Prefs.WorldsRoot, worldName);

			try {
				prefs.removeNode();
				prefs.flush();
			} catch (BackingStoreException e) {
				_logger.logMsg("Failed to delete preferences node.");
			}
			clearDetails();
			_logger.logMsg("World '" + worldName + "' deleted.");
		}
	}
}
