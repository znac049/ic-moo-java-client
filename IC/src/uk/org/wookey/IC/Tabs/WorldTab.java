package uk.org.wookey.IC.Tabs;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import uk.org.wookey.IC.Factories.PluginFactory;
import uk.org.wookey.IC.Factories.WorldTabFactory;
import uk.org.wookey.IC.GUI.VisInfo;
import uk.org.wookey.IC.GUI.WorldDetailsPanel;
import uk.org.wookey.IC.Interfaces.TabInterface;
import uk.org.wookey.IC.Utils.DocWriter;
import uk.org.wookey.IC.Utils.LED;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.Plugin;
import uk.org.wookey.IC.Utils.PluginInfo;
import uk.org.wookey.IC.Utils.WorldCache;
import uk.org.wookey.IC.Utils.WorldSettings;

public class WorldTab extends JPanel implements ActionListener, KeyListener, TabInterface, Runnable {
	private static final long serialVersionUID = -4222553304590523399L;
	private Logger _logger = new Logger("WorldTab");
	private JTextPane screen;
	private JTextField keyboard;
	private VisInfo visInfo;
	private SimpleAttributeSet remoteTextAttribs;
	private SimpleAttributeSet localTextAttribs;
	private SimpleAttributeSet statusAttribs;
	private SimpleAttributeSet errorAttribs;
	private String worldName;
	private Socket socket;
	private BufferedReader remoteInput = null;
	private PrintWriter remoteOutput = null;
	private LED statusLED;
	private boolean connected;
	private boolean localEcho;
	private DocWriter doc;
	private ArrayList<String> keyboardHistory;
	private int historyIndex;
	private ArrayList<Plugin> remoteLineInputHandlers;
	private ScriptEngine scriptEngine;

	public WorldTab(String name) throws IOException {
		super();
		
		worldName = name;
		connected = false;
		localEcho = true;
		
		remoteLineInputHandlers = new ArrayList<Plugin>();
		
		statusLED = new LED(0, 0, 0);
		
		setLayout(new GridBagLayout());
		
		screen = new JTextPane();
		screen.setEditable(false);
		screen.setFocusable(false);

		remoteTextAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(remoteTextAttribs, Color.blue);

		localTextAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(localTextAttribs, new Color(12, 128, 23));
		StyleConstants.setBold(localTextAttribs, true);

		statusAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(statusAttribs, new Color(96, 64, 128));
		StyleConstants.setItalic(statusAttribs, true);

		errorAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(errorAttribs, new Color(192, 64, 64));
		StyleConstants.setBold(errorAttribs, true);

		Font font = new Font("Courier", Font.PLAIN, 12);
		screen.setFont(font);

		JScrollPane scroller = new JScrollPane(screen);
		scroller.setFocusable(false);
		add(scroller, 0, 0, 1.0, 1.0);
				
		doc = new DocWriter(screen);
		doc.addHighLight("Bob", errorAttribs);
		doc.addHighLight("Kira", errorAttribs);
		
		keyboardHistory = new ArrayList<String>();
		historyIndex = 0;
		
		keyboard = new JTextField();
		font = new Font("Courier", Font.BOLD, 14);
		keyboard.setFont(font);
		keyboard.requestFocus();
		keyboard.setBackground(new Color(0xf0, 0xff, 0xf0));
		keyboard.addActionListener(this);
		keyboard.addKeyListener(this);
		add(keyboard, 0, 1, 1.0, 0.0);

		visInfo = new VisInfo();
		add(visInfo, 1, 0, 0.0, 1.0);
		visInfo.hide();

		ScriptEngineManager factory = new ScriptEngineManager();
		scriptEngine = factory.getEngineByName("JavaScript");

		_logger.logMsg("Creating Line Input Handlers");

		initPlugins();

		WorldTabFactory.getWorldTabs().getTabPane().addTab(worldName, statusLED, this);
		WorldTabFactory.getWorldTabs().getTabPane().setSelectedComponent(this);

		new Thread(this, "World: " + name).start();
	}
	
	private void initPlugins() {
		_logger.logMsg("Looking for plugins that handle remote line input");

		for (PluginInfo info: PluginFactory.plugins) {
			Plugin plugin = (Plugin) info.newInstance();
			
			// Does the plugin handle remote line input?
			if (plugin.handlesRemoteLineInput() && plugin.energizePlugin()) {
				_logger.logMsg("  plugin '" + plugin.getName() + "' handles remote line input");

				if (plugin.connectTo(this)) {
					remoteLineInputHandlers.add(plugin);
				}
				else {
					_logger.logMsg("Plugin '" + plugin.getName() + "' failed to connect to worldTab");
				}
			}
		}
	}
	
	public void run() {
		doc.writeln("Connecting to world '" + worldName + "'.", statusAttribs);
		attemptToConnect();
		
		while (connected && !socket.isClosed()) {
			try {
				String line = remoteInput.readLine();
				
				if (line == null) {
					connected = false;
				}
				else {
					handleRemoteInputLine(line);
				}
			} catch (IOException e) {
				_logger.logMsg("Unhandled IOexception");
				e.printStackTrace();
			}
		}
		
		doc.writeln("Connection closed", statusAttribs);
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
		WorldCache cache = new WorldCache();
		WorldSettings detail = cache.getWorld(worldName);
		
		try {
			String server = detail.getString(WorldDetailsPanel.SERVER);
			int port = detail.getInt(WorldDetailsPanel.PORT);
				
			localEcho = detail.getBoolean(WorldDetailsPanel.LOCALECHO);
				
			if ((port != -1) && !server.equals("")) {
				socket = new Socket(server, port);
					
				remoteInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				remoteOutput = new PrintWriter(socket.getOutputStream(), true);
					
				connected = true;
					
				if (detail.getBoolean(WorldDetailsPanel.AUTOCONNECT)) {
					String userName = detail.getString(WorldDetailsPanel.USERNAME);
					String password = detail.getString(WorldDetailsPanel.PASSWORD);
						
					if (!userName.equals("")) {
						_logger.logMsg("Autologin as '" + userName + "'");
						writeRemote("connect " + userName + " " + password);
					}
				}
			}
		} catch (UnknownHostException e) {
			doc.writeln("I can't resolve the hostname '" + worldName + "'.", errorAttribs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean tabVisible() {
		return (WorldTabFactory.getWorldTabs().getTabPane().getSelectedComponent() == this);
	}

	protected void appendd(String msg, SimpleAttributeSet attributes) {
		Document docu = screen.getDocument();
		
		try {
			docu.insertString(docu.getLength(), msg, attributes);
			screen.setCaretPosition(docu.getLength());		
		} catch (BadLocationException e) {
			e.printStackTrace();
		}		
	}

	public void writeRemote(String line) {
		remoteOutput.println(line);
		remoteOutput.flush();		
	}
	
	public String readRemote() throws IOException {
		return remoteInput.readLine();
	}
	
	public void clearActivity() {
		if (statusLED.setColour(0, 0, 0)) {
			WorldTabFactory.getWorldTabs().repaint();
		}
	}
	
	public void flagActivity() {
		if (statusLED.setColour(255, 120, 0)) {
			WorldTabFactory.getWorldTabs().repaint();
		}
	}
	
	public void actionPerformed(ActionEvent evt) {
		String line = keyboard.getText();
		
		// Local echo
		if (localEcho) {
			doc.writeln(line, localTextAttribs);
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
		writeRemote(line);

		keyboard.setText("");
	}
	
	public void handleRemoteInputLine(String line) {
		if (line != null) {
			// Are any of the plugins interested in this line?
			int code = Plugin.NotInterested;
			
			for (Plugin plugin : remoteLineInputHandlers) {
				//_logger.logMsg(plugin.getName() + "??");
				code = plugin.handleRemoteLineInput(line);
				
				if (code == Plugin.HandledFinal) {
					// Handled and don't want anyone else to be allowed a look-in
					break;
				}
			}
			
			if (code == Plugin.NotInterested) {
				// None of the plugins were interested
				doc.format(line+'\n', remoteTextAttribs);
				
				if (tabVisible()) {
					clearActivity();
				}
				else {
					flagActivity();
				}
			}
		}
	}
	
	public VisInfo getVisInfo() {
		return visInfo;
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
		return worldName;
	}
}
