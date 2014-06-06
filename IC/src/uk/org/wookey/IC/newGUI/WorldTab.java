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
		add(keyboard, 0, 1, 1.0, 0.0);
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
		int mod = keyEvent.getModifiers();
		
		if (keyEvent.isControlDown()) {
			_logger.logMsg("Special char! " + key);
		}
		
		if (key == KeyEvent.VK_UP) {
			if (historyIndex > 0) {
				historyIndex--;
				keyboard.setText(keyboardHistory.get(historyIndex));
			}
		}
		else if (key == KeyEvent.VK_DOWN) {
			if (historyIndex+1 < keyboardHistory.size()) {
				historyIndex++;
				keyboard.setText(keyboardHistory.get(historyIndex));
			}
			else {
				keyboard.setText("");
			}
		}	
	}

	public void keyReleased(KeyEvent arg0) {
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
