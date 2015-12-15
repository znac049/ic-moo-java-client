package uk.org.wookey.IC.GUI.Tabs;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.*;

import uk.org.wookey.IC.GUI.ScreenWithHighlighting;
import uk.org.wookey.IC.GUI.StatusPanel;
import uk.org.wookey.IC.GUI.TriPanel;
import uk.org.wookey.IC.GUI.Forms.WorldSettingsForm;
import uk.org.wookey.IC.GUI.Terminal.Terminal;
import uk.org.wookey.IC.GUI.Terminal.TerminalActivityInterface;
import uk.org.wookey.IC.Utils.JSEngine;
import uk.org.wookey.IC.Utils.KeyCode;
import uk.org.wookey.IC.Utils.KeyMap;
import uk.org.wookey.IC.Utils.LED;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.Macro;
import uk.org.wookey.IC.Utils.Prefs;
import uk.org.wookey.IC.Utils.ServerConnection;
import uk.org.wookey.IC.Utils.TabInterface;

public class WorldTab extends TriPanel implements TabInterface, Runnable, TerminalActivityInterface {
	private static final long serialVersionUID = 1L;
	public static final int LEFT_SIDEBAR = 1;
	public static final int RIGHT_SIDEBAR = 2;
	
	private Logger _logger = new Logger("WorldTab");
	
	//private ScreenWithHighlighting screen;
	//private JTextField keyboard;
	
	private Terminal terminal;
	
	private StatusPanel infoPanel;
	private ServerConnection server;
	private LED statusLED;
	
	private String worldName;
	private String hostName;
	private int hostPort;
	
	private Thread listenerThread;
	
	private Preferences prefs;
	
	private JSEngine jsEngine = new JSEngine();
	
	private KeyMap keyMap;
	
	private String homePage = null;
	private String helpPage = null;
	
	public WorldTab(String host, int port) throws IOException {
		super();
		
		hostName = host;
		hostPort = port;
		
		worldName = null;
		
		prefs = null;
		
		setup();
	}
	
	public WorldTab(String name) throws IOException {
		super();
		
		worldName = name;
		hostName = null;
		
		hostPort = -1;
		
		prefs = Prefs.node(Prefs.WorldsRoot, name);
		
		setup();
	}
	
	public String getName() {
		if (worldName == null) {
			return hostName + ":" + hostPort;
		}
		
		return worldName;
	}
	
	public void setup() {
		statusLED = new LED(0, 0, 0);
		
		terminal = new Terminal();
		terminal.addInputListener(this);
		
		getMiddlePanel().add(terminal, BorderLayout.CENTER);
				
		loadCommandHistory();
		
		infoPanel = new StatusPanel();
		//middle.add(infoPanel, 0, 2, 1.0, 0.0, GridBagConstraints.BOTH, 3, 1);
		
		getLeftPanel().setLayout(new GridBagLayout());
		getRightPanel().setLayout(new GridBagLayout());
		
		setResizeWeight(0.0, 1.0);
		
		this.setFocusTraversalPolicy(new WorldTraversalPolicy());
	}
	
	public void tearDown() {
		_logger.logInfo("Die, die!");
		
		saveCommandHistory();
		
		Macro onClose = new Macro("onClose");
		onClose.exec(jsEngine, server);
		
		listenerThread.interrupt();
		server.disconnect();
	}
	
	private String getCommandHistoryName() {
		return "conf/" + getName() + "_commandhistory.log";
	}
	
	private void saveCommandHistory() {
		PrintWriter fd;
		try {
			fd = new PrintWriter(new FileWriter(getCommandHistoryName()));
		
			fd.close();
		} catch (IOException e) {
			_logger.logError("Failed to create file to save command history", e);
			return;
		}		
	}
	
	private void loadCommandHistory() {
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(getCommandHistoryName()));
		} catch (FileNotFoundException e) {
			_logger.logError("Failed to open file to read command history", e);
			return;
		}
		
		String command;
		try {
			while ((command = br.readLine()) != null) {
				command = command.replace("\r","").replace("\n","");
			}

			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setupKeyMap() {
		if (worldName == null) {
			keyMap = new KeyMap(hostName, jsEngine, server);
		}
		else {
			keyMap = new KeyMap(worldName, jsEngine, server);
		}
		
		keyMap.load(prefs);
	}
	
	public void runThread() {
		listenerThread = new Thread(this, "Listener-" + getName());
		listenerThread.start();
	}
	
	public void run() {
		terminal.info("Connecting to '" + getName() + "'");
		
		attemptToConnect();
		
		while (server.isConnected()) {
			String line = server.readLine();
			handleRemoteInputLine(line);
		}
		
		terminal.info("Connection closed");
	}

	private void attemptToConnect() {
		boolean autoConnect = false;
		String userName = null;
		String password = null;
		
		if (worldName != null) {
			Preferences prefs = Prefs.node(Prefs.WorldsRoot + "/" + worldName);

			hostName = prefs.get(Prefs.SERVER, null);
			hostPort = prefs.getInt(Prefs.PORT, -1);
			//localEcho = prefs.getBoolean(Prefs.LOCALECHO, true);

			autoConnect = prefs.getBoolean(Prefs.AUTOLOGIN, false);
			userName = prefs.get(Prefs.USERNAME, "");
			password = prefs.get(Prefs.PASSWORD, "");
		}
		else {
			//localEcho = true;
			autoConnect = false;
		}
		
		if ((hostPort != -1) && !hostName.equals("")) {
			server = new ServerConnection(hostName, hostPort, this, prefs);
			
			if (worldName != null) {
				setupKeyMap();
					
				if (server.isConnected() & autoConnect & !userName.equals("")) {
					_logger.logInfo("Autologin as '" + userName + "'");
					server.writeLine("connect " + userName + " " + password);
					server.addFormatTranslation("u", userName);
				}
			}
		}
		
		// Run onConnect macro, if it exists
		Macro onConnect = new Macro("onConnect");
		onConnect.exec(jsEngine, server);
	}
	
	public boolean tabVisible() {
		JTabbedPane tabs = (JTabbedPane) getParent();
		return (tabs.getSelectedComponent() == this);
	}

	public void clearActivity() {
		if (statusLED.setColour(0, 0, 0)) {
			getParent().repaint();
		}
	}
	
	public void flagActivity() {
		if (statusLED.setColour(255, 120, 0)) {
			getParent().repaint();
		}
	}
	
	public void handleRemoteInputLine(String line) {
		if (line != null) {
			// None of the plugins were interested
			terminal.remote(line + "\n");
					
			if (tabVisible()) {
				clearActivity();
				terminal.repaint();
				terminal.requestFocusInWindow();
			}
			else {
				flagActivity();
			}
		}
	}
	
	public String OLDgetWorldName() {
		if (worldName != null) {
			return worldName;
		}
		
		return hostName;
	}
	
	public LED getIndicator() {
		return statusLED;
	}
	
	public KeyMap getKeyMap() {
		return keyMap;
	}
	
	public Preferences getPrefs() {
		return prefs;
	}
	
	public ServerConnection getServerPort() {
		return server;
	}
	
	public void editSettings() {
		WorldSettingsForm settings = new WorldSettingsForm();
		
		settings.show(true);
	}
	
	public void setHomePage(String url) {
		homePage = url;
	}
	
	public String getHomepage() {
		return homePage;
	}
	
	public void setHelpPage(String url) {
		helpPage = url;
	}
	
	public String getHelpPage() {
		return helpPage;
	}

	@Override
	public void characterTyped(char c) {
		_logger.logInfo("Typed char:");
		server.putCh(c);
	}
	
	private class WorldTraversalPolicy extends FocusTraversalPolicy {
		@Override
		public Component getComponentAfter(Container arg0, Component arg1) {
			return terminal;
		}

		@Override
		public Component getComponentBefore(Container arg0, Component arg1) {
			return terminal;
		}

		@Override
		public Component getDefaultComponent(Container arg0) {
			return terminal;
		}

		@Override
		public Component getFirstComponent(Container arg0) {
			return terminal;
		}

		@Override
		public Component getLastComponent(Container arg0) {
			return terminal;
		}
		
	}
}
