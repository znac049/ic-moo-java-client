package uk.org.wookey.IC.GUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.*;

import uk.org.wookey.IC.GUI.Forms.WorldSettingsForm;
import uk.org.wookey.IC.Utils.JSEngine;
import uk.org.wookey.IC.Utils.KeyCode;
import uk.org.wookey.IC.Utils.KeyMap;
import uk.org.wookey.IC.Utils.LED;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.Macro;
import uk.org.wookey.IC.Utils.Prefs;
import uk.org.wookey.IC.Utils.ServerConnection;
import uk.org.wookey.IC.Utils.TabInterface;

public class WorldTab extends TriPanel implements ActionListener, TabInterface, Runnable, MouseListener {
	private static final long serialVersionUID = 1L;
	public static final int LEFT_SIDEBAR = 1;
	public static final int RIGHT_SIDEBAR = 2;
	
	private Logger _logger = new Logger("WorldTab");
	private Screen screen;
	private JTextField keyboard;
	private StatusPanel infoPanel;
	private ServerConnection server;
	private LED statusLED;
	
	private String worldName;
	private String hostName;
	private int hostPort;
	private boolean localEcho;
	
	private Thread listenerThread;
	
	private Preferences prefs;
	
	private ArrayList<String> keyboardHistory;
	private int historyIndex;
	
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
		KeyHandler kh = new KeyHandler();
		
		localEcho = true;
		
		statusLED = new LED(0, 0, 0);
		
		screen = new Screen();
		screen.setFocusable(true);
		screen.requestFocusInWindow();

		JScrollPane scroller = new JScrollPane(screen);
		scroller.setFocusable(false); 
		getMiddlePanel().add(scroller, BorderLayout.CENTER);
				
		keyboardHistory = new ArrayList<String>();
		historyIndex = 0;
		
		loadCommandHistory();
		
		keyboard = new JTextField();
		Font font = new Font("Courier", Font.BOLD, 16);
		keyboard.setFont(font);
		keyboard.requestFocus();
		keyboard.setBackground(new Color(0xf0, 0xff, 0xf0));
		keyboard.addActionListener(this);
		keyboard.addKeyListener(kh);
		getMiddlePanel().add(keyboard, BorderLayout.PAGE_END);
		
		infoPanel = new StatusPanel();
		//middle.add(infoPanel, 0, 2, 1.0, 0.0, GridBagConstraints.BOTH, 3, 1);
		
		getLeftPanel().setLayout(new GridBagLayout());
		getRightPanel().setLayout(new GridBagLayout());
		
		setResizeWeight(0.0, 1.0);
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
		
			for (String command: keyboardHistory) {
				fd.println(command);
			}
			
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
				keyboardHistory.add(command);
			}

			br.close();
			historyIndex = keyboardHistory.size();
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
		screen.info("Connecting to '" + getName() + "'");
		
		attemptToConnect();
		
		while (server.isConnected()) {
			String line = server.readLine();
			handleRemoteInputLine(line);
		}
		
		screen.info("Connection closed");
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
	
	public void actionPerformed(ActionEvent evt) {
		String line = keyboard.getText();
		
		// Local echo
		if (localEcho) {
			screen.local(line);
		}
		
		// Add it to the history
		keyboardHistory.add(line);
		int size = keyboardHistory.size();

		while (size > 500) {
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
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
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
	}
}
