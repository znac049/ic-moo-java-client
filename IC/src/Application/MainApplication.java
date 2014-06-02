package Application;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import uk.org.wookey.IC.Factories.PluginFactory;
import uk.org.wookey.IC.GUI.ApplicationWindow;
import uk.org.wookey.IC.Utils.Logger;

public class MainApplication {
	public final static String rootNodeName = "uk/org/wookey/IC";
	public final static String pluginNodeName = "plugins";
	public final static String worldsNodeName = "worlds";
	
	private Preferences appRoot = Preferences.userRoot().node(MainApplication.rootNodeName);

	private final Logger _logger = new Logger("MainApplication");

	public MainApplication() {
		// Plugins
		_logger.logMsg("Looking for plugins");
		PluginFactory.scanForPlugins();
		
		//new ApplicationWindow();
		new ApplicationWindow(true);
	}

	public static void main(String[] args) {
		new MainApplication();
	}
}
