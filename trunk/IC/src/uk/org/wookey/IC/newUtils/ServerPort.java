package uk.org.wookey.IC.newUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import uk.org.wookey.IC.Utils.CorePlugin;
import uk.org.wookey.IC.Utils.IOPlugin;
import uk.org.wookey.IC.Utils.Logger;

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
	
	public ServerPort(String hostName, int hostPort) {
		connect(hostName, hostPort);
	}

	public ServerPort(String hostName, int hostPort, Preferences root) {
		prefs = root;
		
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
				
				if (plugins != null) {
					for (CorePluginInterface plugin: plugins) {
						IOPlugin p = (IOPlugin) plugin;
						
						_logger.logInfo("Pass to " + p.getName());
					
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
		plugins = PluginFactory.pluginsSupporting(CorePluginInterface.PluginType.IOPLUGIN);
		
		if (plugins == null) {
			_logger.logInfo("No IOPlugins found");
		}
		else {
			_logger.logInfo("The following IOPlugins were loaded:");
			for (CorePluginInterface plugin: plugins) {
				IOPlugin p = (IOPlugin) plugin;
				
				_logger.logInfo("  " + plugin.getName());
				
				p.attach(this);
			}
		}
	}
}
