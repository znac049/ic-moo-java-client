package uk.org.wookey.IC.newGUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.*;

import uk.org.wookey.IC.Utils.LED;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.newUtils.JSEngine;
import uk.org.wookey.IC.newUtils.KeyMap;
import uk.org.wookey.IC.newUtils.Prefs;
import uk.org.wookey.IC.newUtils.ServerPort;
import uk.org.wookey.IC.newUtils.TabInterface;

public class WorldTab extends JPanel implements ActionListener, KeyListener, TabInterface, Runnable {
	private static final long serialVersionUID = 1L;
	
	private static int tabNum = 0;
	
	private Logger _logger = new Logger("WorldTab");
	private Screen screen;
	private JTextField keyboard;
	
	private ServerPort server;
	private LED statusLED;
	
	private String worldName;
	private String hostName;
	private int hostPort;
	private boolean localEcho;
	
	private Preferences prefs;
	
	private ArrayList<String> keyboardHistory;
	private int historyIndex;
	
	private JSEngine jsEngine = new JSEngine();
	
	private KeyMap keyMap;
	
	private boolean controlKeyPressed;
	private boolean altKeyPressed;
	private boolean windowsKeyPressed;

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
	
	public void setup() {
		localEcho = true;
		
		statusLED = new LED(0, 0, 0);
		
		setLayout(new GridBagLayout());
		
		screen = new Screen();
		JScrollPane scroller = new JScrollPane(screen);
		scroller.setFocusable(false);
		add(scroller, 0, 0, 1.0, 1.0);
				
		keyboardHistory = new ArrayList<String>();
		historyIndex = 0;
		
		keyboard = new JTextField();
		Font font = new Font("Courier", Font.BOLD, 14);
		keyboard.setFont(font);
		keyboard.requestFocus();
		keyboard.setBackground(new Color(0xf0, 0xff, 0xf0));
		keyboard.addActionListener(this);
		keyboard.addKeyListener(this);
		controlKeyPressed = false;
		altKeyPressed = false;
		windowsKeyPressed = false;
		add(keyboard, 0, 1, 1.0, 0.0);		
	}
	
	private void setupKeyMap() {
		if (worldName == null) {
			keyMap = new KeyMap(hostName, jsEngine, server);
		}
		else {
			keyMap = new KeyMap(worldName, jsEngine, server);
		}
		
		keyMap.add(KeyEvent.VK_F1, "help");

	}
	
	public void runThread() {		
		tabNum++;
		new Thread(this, "World: " + tabNum).start();
	}
	
	public void run() {
		screen.info("Connecting to world '" + worldName + "'.");
		attemptToConnect();
		
		while (server.connected()) {
			String line = server.readLine();
				
			if (line != null) {
				handleRemoteInputLine(line);
			}
		}
		
		screen.info("Connection closed");
	}

	private void add(Component c, int x, int y, double wx, double wy) {
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.weightx = wx;
		constraints.weighty = wy;
		constraints.fill = 1;
		
		add(c, constraints);
	}
	
	private void attemptToConnect() {
		boolean autoConnect = false;
		String userName = null;
		String password = null;
		
		if (worldName != null) {
			Preferences prefs = Prefs.node(Prefs.WorldsRoot + "/" + worldName);

			hostName = prefs.get(Prefs.SERVER, null);
			hostPort = prefs.getInt(Prefs.PORT, -1);
			localEcho = prefs.getBoolean(Prefs.LOCALECHO, true);

			autoConnect = prefs.getBoolean(Prefs.AUTOLOGIN, false);
			userName = prefs.get(Prefs.USERNAME, "");
			password = prefs.get(Prefs.PASSWORD, "");
		}
		else {
			localEcho = true;
			autoConnect = false;
		}
		
		if ((hostPort != -1) && !hostName.equals("")) {
			server = new ServerPort(hostName, hostPort, prefs);
			
			setupKeyMap();
					
			if (server.connected() & autoConnect & !userName.equals("")) {
				_logger.logInfo("Autologin as '" + userName + "'");
				server.writeLine("connect " + userName + " " + password);
			}
		}
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
	
	public void actionPerformed(ActionEvent evt) {
		String line = keyboard.getText();
		
		// Local echo
		if (localEcho) {
			screen.local(line);
		}
		
		// Add it to the history
		keyboardHistory.add(line);
		int size = keyboardHistory.size();

		while (size > 100) {
			keyboardHistory.remove(0);
			size--;
		}
		historyIndex = size;
		
		// Send it down the socket...
		server.writeLine(line);

		keyboard.setText("");
	}
	
	public void handleRemoteInputLine(String line) {
		if (line != null) {
			// None of the plugins were interested
			screen.remote(line);
					
			if (tabVisible()) {
				clearActivity();
			}
			else {
				flagActivity();
			}
		}
	}
	
	public void keyPressed(KeyEvent keyEvent) {
		int key = keyEvent.getKeyCode();
		
		switch (key) {
		case KeyEvent.VK_UP:
			if (historyIndex > 0) {
				historyIndex--;
				keyboard.setText(keyboardHistory.get(historyIndex));
			}
			break;
			
		case KeyEvent.VK_DOWN:
			if (historyIndex+1 < keyboardHistory.size()) {
				historyIndex++;
				keyboard.setText(keyboardHistory.get(historyIndex));
			}
			else {
				keyboard.setText("");
			}

		case KeyEvent.VK_ALT:
			altKeyPressed = true;
			break;

		case KeyEvent.VK_CONTROL:
			controlKeyPressed = true;
			break;
		
		case KeyEvent.VK_WINDOWS:
			windowsKeyPressed = true;
			break;
			
		default:
			// some other key. take an interest if any of the magic keys are also pressed
			if (altKeyPressed | controlKeyPressed | windowsKeyPressed) {
				_logger.logInfo("Non printable key combo, key=" + key);
			}
		}
	}

	public void keyReleased(KeyEvent keyEvent) {
		int key = keyEvent.getKeyCode();
		
		switch (key) {
		case KeyEvent.VK_ALT:
			altKeyPressed = false;
			break;

		case KeyEvent.VK_CONTROL:
			controlKeyPressed = false;
			break;
		
		case KeyEvent.VK_WINDOWS:
			windowsKeyPressed = false;
			break;
		}
	}

	public void keyTyped(KeyEvent keyEvent) {
		int key = keyEvent.getKeyCode();
	}
	
	public String getWorldName() {
		if (worldName != null) {
			return worldName;
		}
		
		return hostName;
	}
	
	public LED getIndicator() {
		return statusLED;
	}
}
