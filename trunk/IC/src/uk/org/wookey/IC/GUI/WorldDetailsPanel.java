package uk.org.wookey.IC.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.org.wookey.IC.Utils.CorePlugin;
import uk.org.wookey.IC.Utils.CorePluginInterface;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.PluginManager;
import uk.org.wookey.IC.Utils.Prefs;
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
	private JCheckBox logSession;
	private JTextField logFile;
	private JCheckBox newPluginsEnabledByDefault;
	private ArrayList<PluginDetails> pluginDetails;
	private JLabel logLabel;
	
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
		playerPassword.setEchoChar('-');
		addComp(mainPanel, "Player Password:", playerPassword);
		
		localEcho = new JCheckBox("Local echo");
		mainPanel.add(localEcho, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		logSession = new JCheckBox("Log session");
		mainPanel.add(logSession, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		logFile = new JTextField(15);

		JPanel logFilePanel = new JPanel();
		logLabel = new JLabel("Log file name:");
		
		logFilePanel.add(logLabel);
		
		lab.setLabelFor(logFile);
		logFilePanel.add(logFile);
		
		mainPanel.add(logFilePanel, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 2));
		
		newPluginsEnabledByDefault = new JCheckBox("Enable new plugins by default");
		mainPanel.add(newPluginsEnabledByDefault, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		logSession.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				AbstractButton abstractButton =
						(AbstractButton)changeEvent.getSource();
				ButtonModel buttonModel = abstractButton.getModel();
				boolean selected = buttonModel.isSelected();
				
				if (selected) {
					logLabel.setEnabled(true);
					logFile.setEnabled(true);
				}
				else {
					logLabel.setEnabled(false);
					logFile.setEnabled(false);
				}
			}
		});
		
		pluginDetails = new ArrayList<PluginDetails>();
		ArrayList<CorePlugin> plugins = PluginManager.pluginsSupporting(CorePluginInterface.PluginType.IOPLUGIN);
		if (plugins.size() > 0) {
			BorderPanel selections = new BorderPanel("Plugins");
			selections.setLayout(new BoxLayout(selections, BoxLayout.PAGE_AXIS));
			
			selections.add(Box.createRigidArea(new Dimension(0,3)));

			for (CorePlugin p: plugins) {
				JCheckBox cb = new JCheckBox(p.getName());
				
				pluginDetails.add(new PluginDetails(p.getName(), cb));
				
				selections.add(cb);
			}
			
			mainPanel.add(selections, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));			
		}
		
		add(mainPanel, BorderLayout.NORTH);
	}
	
	private void addComp(JPanel panel, String label, Component comp) {
		JLabel lab = new JLabel(label, JLabel.TRAILING);
		panel.add(lab, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));

		lab.setLabelFor(comp);
		panel.add(comp, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 2));		
	}

	public void clearDetails() {
		saveName.setEnabled(true);
		saveName.setText("");
		
		autoConnect.setEnabled(true);
		autoConnect.setSelected(false);
		
		serverName.setEnabled(true);
		serverName.setText("");
		
		serverPort.setEnabled(true);
		serverPort.setText("");
		
		autoLogin.setEnabled(true);
		autoLogin.setSelected(false);
		
		playerName.setEnabled(true);
		playerName.setText("");
		
		playerPassword.setEnabled(true);
		playerPassword.setText("");
		
		localEcho.setEnabled(true);
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
		
		boolean pluginDefault = prefs.getBoolean(Prefs.NEWPLUGINSENABLED, false);
		newPluginsEnabledByDefault.setSelected(pluginDefault);
		
		boolean logToFile = prefs.getBoolean(Prefs.LOGTOFILE, false);
		logSession.setSelected(logToFile);
		if (logToFile) {
			logFile.setText(prefs.get(Prefs.LOGFILE, "%w.txt"));
		}
		else {
			logFile.setText("");
		}
		
		// Move onto the prefs node for plugins
		prefs = prefs.node(Prefs.PLUGINS);
		
		for (PluginDetails pd: pluginDetails) {
			JCheckBox cb = (JCheckBox) pd.getUiItem();
			String name = pd.getName();
			
			_logger.logInfo("Loading plugin enabled setting for '" + name + "'");
			
			cb.setSelected(prefs.getBoolean(name + "Enabled",  pluginDefault));
		}
	}
	
	public void saveDetails() {
		String worldName = saveName.getText();
		Preferences prefs = Prefs.node(Prefs.WorldsRoot, worldName);
				
		_logger.logMsg("Save details for: " + worldName);

		if (worldName.equals("") || worldName.startsWith("<")) {
			_logger.logError("Invalid world name!");
			JOptionPane.showMessageDialog(this, "You need to provide a world name", "Save World", JOptionPane.WARNING_MESSAGE);
		}
		else {
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
			prefs.put(Prefs.PASSWORD, new String(playerPassword.getPassword()));

			prefs.putBoolean(Prefs.LOCALECHO, localEcho.isSelected());

			prefs.putBoolean(Prefs.NEWPLUGINSENABLED, newPluginsEnabledByDefault.isSelected());
			
			prefs.putBoolean(Prefs.LOGTOFILE, logSession.isSelected());
			prefs.put(Prefs.LOGFILE, logFile.getText());

			// Move onto the prefs node for plugins
			prefs = prefs.node(Prefs.PLUGINS);
			
			for (PluginDetails pd: pluginDetails) {
				JCheckBox cb = (JCheckBox) pd.getUiItem();
				String name = pd.getName();
				
				_logger.logInfo("Saving plugin enabled setting for '" + name + "'");
			
				prefs.putBoolean(name + "Enabled", cb.isSelected());
			}

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
		
	class PluginDetails {
		private String name;
		private Container uiItem;
		
		public PluginDetails(String name, Container uiItem) {
			this.name = name;
			this.uiItem = uiItem;
		}
		
		public String getName() {
			return name;
		}
		
		public Container getUiItem() {
			return uiItem;
		}
	}
}
