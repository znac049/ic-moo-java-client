package Application;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import uk.org.wookey.IC.Factories.PluginFactory;
import uk.org.wookey.IC.Factories.WorldTabFactory;
import uk.org.wookey.IC.GUI.ApplicationWindow;
import uk.org.wookey.IC.GUI.WorldDetailsPanel;
import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.WorldSettings;
import uk.org.wookey.IC.newUtils.Prefs;
import uk.org.wookey.IC.newUtils.Worlds;

public class MainApplication {
	//public final static String rootNodeName = "uk/org/wookey/IC";
	//public final static String pluginNodeName = "plugins";
	//public final static String worldsNodeName = "worlds";
	
	//private Preferences appRoot = Preferences.userRoot().node(MainApplication.rootNodeName);
	
	private ApplicationWindow mainWindow;
	private final Logger _logger = new Logger("MainApplication");

	public MainApplication() {
		// Plugins
		_logger.logMsg("Looking for plugins");
		PluginFactory.scanForPlugins();
		
		//new ApplicationWindow();
		mainWindow = new ApplicationWindow(true);
		
		// Check for worlds to autoconnect to
		_logger.logMsg("Look for worlds to autoconnect to...");
		for (String world: Worlds.getListOfWorlds()) {
			Preferences prefs = Preferences.userRoot().node(Prefs.WorldsRoot + "/" + world);
			
			if (prefs.getBoolean(Prefs.AUTOCONNECT, false)) {
				_logger.logMsg("Autoconnect to world '" + world + "'");
				
				// Create a tab and connect
				String worldServer = prefs.get(Prefs.SERVER, null);
				int worldPort = prefs.getInt(Prefs.PORT, -1);
				if ((worldServer != null) && (worldPort != -1)) {
					try {
						WorldTab tab = new WorldTab(world);
						mainWindow.addTab(tab);
					} catch (IOException e) {
						_logger.logMsg("Caught exception trying to create WorldTab('" + world + "'");
					}
				}
			}
				
		}
	}

	public static void main(String[] args) {
		new MainApplication();
	}
}
