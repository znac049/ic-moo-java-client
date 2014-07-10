package uk.org.wookey.IC.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import uk.org.wookey.IC.GUI.WorldTab;

public class ServerConnection {
	private Logger _logger = new Logger("ServerConnection");
	private Socket socket;
	private String host;
	private int port;
	private boolean connected = false;
	private boolean logging = false;
	private ArrayList<CorePlugin> plugins;
	private PrintWriter logFile = null;
	private Preferences prefs = null;
	private WorldTab worldTab = null;
	private FileNameFormatter fileNameFormatter;
	
	public ServerConnection(String hostName, int hostPort, WorldTab tab) {
		worldTab = tab;
		host = hostName;
		port = hostPort;
		prefs = tab.getPrefs();
		
		connect(hostName, hostPort);
	}

	public ServerConnection(String hostName, int hostPort, WorldTab tab, Preferences root) {
		worldTab = tab;
		host = hostName;
		port = hostPort;
		prefs = root;
		
		connect(hostName, hostPort);
	}
	
	private void connect(String hostName, int hostPort) {
		fileNameFormatter = new FileNameFormatter();
		
		fileNameFormatter.addMapping("h", hostName);
		fileNameFormatter.addMapping("p", String.valueOf(hostPort));
		fileNameFormatter.addMapping("w", worldTab.getName());
		fileNameFormatter.addMapping("u", "none");

		plugins = new ArrayList<CorePlugin>();
		
		try {
			if ((hostPort != -1) && !hostName.equals("")) {
				_logger.logMsg("Connecting to: " + hostName + " port " + hostPort);
				socket = new Socket(hostName, hostPort);
					
				connected = true;
				
				_logger.logSuccess("Connected");
				
				loadIOPlugins();
			}
		} catch (IOException e) {
			_logger.logError("Failed to connect to '" + hostName + " on port " + hostPort);
		}
	} 
	
	public String readLine() {
		while (connected) {
			String line = getStr();
			
			//_logger.logSuccess("S->C: " + line);
			
			Line l = new Line(line);
			boolean consumed = false;
			
			if (plugins != null) {
				for (CorePluginInterface plugin: plugins) {
					IOPlugin p = (IOPlugin) plugin;
								
					if (p.remoteLineIn(l) == IOPluginInterface.Status.CONSUMED) {
						consumed = true;
						break;
					}
				}
			}
			
			if (!consumed) {
				logInput(line);
				
				return line;
			}
		}
		
		return null;
	}
	
	private void loadIOPlugins() {
		_logger.logInfo("The following IOPlugins were found:");
		for (CorePluginInterface plugin: PluginManager.pluginsSupporting(CorePluginInterface.PluginType.IOPLUGIN)) {
			IOPluginInterface p = (IOPluginInterface) plugin;
				
			if (Prefs.pluginEnabledForWorld(worldTab.getName(), p.getName())) {
				_logger.logInfo("  " + plugin.getName() + " enabled");
				
				plugins.add((CorePlugin) plugin);
				
				p.attach(this, worldTab);
			}
			else {
				_logger.logInfo("  - Plugin " + p.getName() + " has been disabled");
			}
		}
	}
	
	private String getStr() {
		String line = "";
		char ch = getCh();
		
		while (true) {
			while (ch == '\r') {
				ch = getCh();
			}
			
			if ((ch == '\0') || (ch == '\n')) {
				return line;
			}
			
			line += ch;
			
			if (plugins != null) {
				for (CorePluginInterface plugin: plugins) {
					IOPlugin p = (IOPlugin) plugin;
					
					line = p.remoteLinePeek(line);
				}
			}

			ch = getCh();
		}
	}
	
	private char getCh() {
		int c = '\0'; 

		try {
			if (Thread.interrupted()) {
				_logger.logInfo("World backend thread interrupted while reading from socket");
				disconnect();
			}

			c = socket.getInputStream().read();
			return (char)c;
		} catch (IOException e) {
			_logger.logError("Caught IOException while reading from socket", e);
		}
		
		disconnect();

		return '\0';
	}
	
	public void writeLine(String line) {
		//_logger.logSuccess("C->S: " + line);
		
		logOutput(line);
		
		putStr(line + '\n');
	}
	
	private void putStr(String s) {
		byte[] b = s.getBytes(Charset.forName("UTF-8"));
		for (byte c: b) {
			putCh((char) c);
		}
		
		try {
			socket.getOutputStream().flush();
		} catch (IOException e) {
			_logger.logError("Caught exception when flushing socket");
			disconnect();
		}
	}
	
	private void putCh(char c) {
		try {
			if (Thread.interrupted()) {
				_logger.logInfo("World backend thread interrupted while writing to socket");
				connected = false;
				disconnect();
			}

			socket.getOutputStream().write(c);
		} catch (IOException e) {
			_logger.logMsg("Error writing char to socket");
			disconnect();
		}
	}
	
	public void disconnect() {
		_logger.logInfo("Closing connection to " + host + ":" + port);

		for (int i=0; i<plugins.size(); i++) {
			IOPlugin p = (IOPlugin) plugins.get(i);
			p.deactivate();
		}
		plugins.clear();
		
		try {
			socket.close();
		} catch (IOException e) {
			_logger.logError("Caught IOException when trying to close socket", e);
		}
		
		connected = false;
	}

	public boolean isConnected() {
		if (connected) {	
			if (socket.isClosed()) {
				connected = false;
			}
		}
		
		return connected;
	}
	
	private void logInput(String line) {
		if (logging) {
			logFile.println(line);
		}
	}
	
	private void logOutput(String line) {
		if (logging) {
			logFile.println(line);
		}
	}
	
	public void setLogging(boolean enabled) {
		if (enabled && !logging) {
			// open/create log file
			try {
				String name = fileNameFormatter.makeSpecialFilename("%w-%u.txt");
				_logger.logInfo("Logging session to file '" + name + "'");
				
				logFile = new PrintWriter("logs/" + name, "UTF-8");
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (logging) {
			// close log file
			if (logFile != null) {
				logFile.close();
			}
		}
		
		logging = enabled;
	}
	
	public void addFormatTranslation(String key, String val) {
		fileNameFormatter.addMapping(key, val);
	}
}
