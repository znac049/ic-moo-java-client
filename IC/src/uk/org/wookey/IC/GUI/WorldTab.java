package uk.org.wookey.IC.GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.*;

import uk.org.wookey.IC.Utils.JSEngine;
import uk.org.wookey.IC.Utils.KeyCode;
import uk.org.wookey.IC.Utils.KeyMap;
import uk.org.wookey.IC.Utils.LED;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.Macro;
import uk.org.wookey.IC.Utils.Prefs;
import uk.org.wookey.IC.Utils.ServerPort;
import uk.org.wookey.IC.Utils.TabInterface;

public class WorldTab extends JPanel implements ActionListener, TabInterface, Runnable, MouseListener {
	private static final long serialVersionUID = 1L;
	public static final int LEFT_SIDEBAR = 1;
	public static final int RIGHT_SIDEBAR = 2;
	
	private static int tabNum = 0;
	
	private Logger _logger = new Logger("WorldTab");
	private Screen screen;
	private JTextField keyboard;
	private StatusPanel infoPanel;
	private ServerPort server;
	private LED statusLED;
	
	private JPanel leftSide;
	private JPanel rightSide;
	
	private String worldName;
	private String hostName;
	private int hostPort;
	private boolean localEcho;
	
	private Preferences prefs;
	
	private ArrayList<String> keyboardHistory;
	private int historyIndex;
	
	private JSEngine jsEngine = new JSEngine();
	
	private KeyMap keyMap;
	
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
		KeyHandler kh = new KeyHandler();
		
		localEcho = true;
		
		statusLED = new LED(0, 0, 0);
		
		setLayout(new GridBagLayout());
		
		screen = new Screen();
		screen.setFocusable(true);
		screen.requestFocusInWindow();
		//screen.addKeyListener(new KeyForwarder());
		screen.addKeyListener(kh);

		JScrollPane scroller = new JScrollPane(screen);
		scroller.setFocusable(false);
		add(scroller, 1, 0, 1.0, 1.0);
				
		keyboardHistory = new ArrayList<String>();
		historyIndex = 0;
		
		keyboard = new JTextField();
		Font font = new Font("Courier", Font.BOLD, 16);
		keyboard.setFont(font);
		keyboard.requestFocus();
		keyboard.setBackground(new Color(0xf0, 0xff, 0xf0));
		keyboard.addActionListener(this);
		keyboard.addKeyListener(kh);
		add(keyboard, 1, 1, 1.0, 0.0);
		
		infoPanel = new StatusPanel();
		add(infoPanel, 1, 2, 1.0, 0.0);
		
		leftSide = new JPanel();
		leftSide.setLayout(new GridBagLayout());
		add(leftSide, 0, 0, 0.0, 1.0);
		
		rightSide = new JPanel();
		rightSide.setLayout(new GridBagLayout());
		add(rightSide, 2, 0, 0.0, 1.0);
	}
	
	public JPanel getPanel(int whichPanel) {
		if (whichPanel == LEFT_SIDEBAR) {
			return leftSide;
		}
		else if (whichPanel == RIGHT_SIDEBAR) {
			return rightSide;
		}
		
		return null;
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

	private void add(Component c, int x, int y, double wx, double wy, int fill) {
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.weightx = wx;
		constraints.weighty = wy;
		constraints.fill = fill;
		
		add(c, constraints);
	}

	private void add(Component c, int x, int y, double wx, double wy) {
		add(c, x, y, wx, wy, GridBagConstraints.BOTH);
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
			server = new ServerPort(hostName, hostPort, this, prefs);
			
			setupKeyMap();
					
			if (server.connected() & autoConnect & !userName.equals("")) {
				_logger.logInfo("Autologin as '" + userName + "'");
				server.writeLine("connect " + userName + " " + password);
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
	
	public String getWorldName() {
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
	
	public ServerPort getServerPort() {
		return server;
	}
	
	class KeyHandler implements KeyListener {
		private KeyCode keyCode = new KeyCode(0);

		@Override
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
				keyCode.alt(true);;
				break;

			case KeyEvent.VK_CONTROL:
				keyCode.ctrl(true);;
				break;
			
			case KeyEvent.VK_SHIFT:
				keyCode.shift(true);
				break;
			
			case KeyEvent.VK_WINDOWS:
				keyCode.windows(true);
				break;
				
			case KeyEvent.VK_ENTER:
				_logger.logInfo("Pressed ENTER");
				if (keyEvent.getComponent() != keyboard) {
					keyboard.postActionEvent();
				}
				break;
				
			default:
				// some other key. take an interest if any of the magic keys are also pressed
				keyCode.set(key);
				if (keyCode.nonPrintable()) {
					// Maybe its been mapped ?
					if (keyMap.exec(keyCode)) {
						_logger.logInfo("Hotkey " + keyCode.toString() + " executed");
					}
					else {
						_logger.logInfo("Key " + keyCode.toString() + " not mapped");
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent keyEvent) {
			int key = keyEvent.getKeyCode();
			
			switch (key) {
			case KeyEvent.VK_ALT:
				keyCode.alt(false);;
				break;

			case KeyEvent.VK_CONTROL:
				keyCode.ctrl(false);;
				break;
			
			case KeyEvent.VK_SHIFT:
				keyCode.shift(false);
				break;
			
			case KeyEvent.VK_WINDOWS:
				keyCode.windows(false);
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if (e.getComponent() != keyboard) {
				// forward the character to the keyboard
				_logger.logInfo("Forwarding key '" + e.getKeyChar() + "'");
				keyboard.setText(keyboard.getText() + e.getKeyChar());
			}
		}	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int button = e.getButton();
		
		if ((button & MouseEvent.BUTTON1) == MouseEvent.BUTTON1) {
			_logger.logInfo("Left click!");
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
