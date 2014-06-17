package uk.org.wookey.IC.Utils;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Prefs {
	public static Logger _logger = new Logger("Prefs");
	
	public static final String AppRoot = "uk/org/wookey/IC";
	public static final String WorldsRoot = AppRoot + "/worlds";
	
	public final static String pluginNodeName = AppRoot + "/plugins";

	public static final String AUTOCONNECT = "Autoconnect";
	public static final String SERVER = "Server";
	public static final String PORT = "Port";
	public static final String AUTOLOGIN = "Autologin";
	public static final String USERNAME = "Username";
	public static final String PASSWORD = "Password";
	public static final String LOCALECHO = "LocalEcho";

	public static Preferences node(String name) {
		return Preferences.userRoot().node(name);
	}

	public static Preferences node(String root, String name) {  
		return Prefs.node(root + "/" + name);
	}
	
	public static boolean pluginEnabledGlobally(String name) {
		Preferences prefs = node(pluginNodeName);
		
		_logger.logInfo("root node: " + Preferences.userRoot());
		_logger.logInfo("checking pref: " + prefs.absolutePath() + "/" + name + "Enabled");
		
		boolean res = prefs.getBoolean(name+"Enabled", false);
		prefs.putBoolean(name+"Enabled", res);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			_logger.logError("Failed to save prefs");
		}
		
		return res;
	}
}
