package uk.org.wookey.IC.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import uk.org.wookey.IC.GUI.WorldTab;

public class ServerPort {
	private Logger _logger = new Logger("ServerPort");
	private Socket socket;
	private String host;
	private int port;
	private boolean connected = false;
	private ArrayList<CorePlugin> plugins;
	
	private BufferedReader remoteInput = null;
	private PrintWriter remoteOutput = null;
	
	private Preferences prefs = null;
	
	private WorldTab worldTab = null;
	
	public ServerPort(String hostName, int hostPort, WorldTab tab) {
		worldTab = tab;
		
		connect(hostName, hostPort);
	}

	public ServerPort(String hostName, int hostPort, WorldTab tab, Preferences root) {
		prefs = root;
		worldTab = tab;
		
		connect(hostName, hostPort);
	}
	
	private void connect(String hostName, int hostPort) {
		plugins = new ArrayList<CorePlugin>();
		
		try {
			if ((hostPort != -1) && !hostName.equals("")) {
				_logger.logMsg("Connecting to: " + hostName + " port " + hostPort);
				socket = new Socket(hostName, hostPort);
					
				remoteInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				remoteOutput = new PrintWriter(socket.getOutputStream(), true);
				
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
			try {
				String line = remoteInput.readLine();
				Line l = new Line(line);
				boolean consumed = false;
				
				_logger.logSuccess("S->C: " + line);
				
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
					return line;
				}
				
			} catch (IOException e) {
				try {
					socket.close();
				} catch (IOException ex) {
					_logger.logError("Failed to close socket to " + host + ", port " + port);
				}
				connected = false;
				return null;
			}	
		}
		
		return null;
	}
	
	public void writeLine(String line) {
		_logger.logSuccess("C->S: " + line);
		
		remoteOutput.println(line);
		remoteOutput.flush();
	}
	
	public boolean connected() {
		if (connected) {
			
			if (socket.isClosed()) {
				connected = false;
			}
		}
		
		return connected;
	}
	
	private void loadIOPlugins() {
		_logger.logInfo("The following IOPlugins were found:");
		for (CorePluginInterface plugin: PluginManager.pluginsSupporting(CorePluginInterface.PluginType.IOPLUGIN)) {
			IOPlugin p = (IOPlugin) plugin;
				
			if (Prefs.pluginEnabledGlobally(p.getName())) {
				_logger.logInfo("  " + plugin.getName() + " enabled globally");
				
				plugins.add((CorePlugin) plugin);
				
				p.attach(this, worldTab);
			}
			else {
				_logger.logInfo("  - Plugin " + p.getName() + " has been disabled globally");
			}
		}
	}
}
