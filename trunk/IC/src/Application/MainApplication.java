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
		
		dumpPrefTree();
		
		//new ApplicationWindow();
		new ApplicationWindow();
	}
	
	private void dumpPrefTree() {
		dumpPrefTree(appRoot);
	}
	
	private void dumpPrefTree(Preferences node) {
		try {
			for (String child: node.childrenNames()) {
				_logger.logMsg(node.absolutePath() + "/" + child);
				
				if (appRoot.nodeExists(child)) {
					dumpPrefTree(node.node(child));
				}
			}
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MainApplication();
	}
}
