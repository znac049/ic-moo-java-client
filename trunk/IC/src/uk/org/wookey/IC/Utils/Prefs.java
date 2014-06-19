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
		String keyName = name + "Enabled";
		boolean keyExists = false;
		
		_logger.logInfo("checking global pref: " + prefs.absolutePath() + "/" + keyName);
		
		// Does the key exist?
		try {
			for (String key: prefs.keys()) {
				if (key.equals(keyName)) {
					keyExists = true;
					break;
				}
			}
		} catch (BackingStoreException e) {
			_logger.logError("Caught exception while getting list of pref keys", e);
		}
		
		if (!keyExists) {
			_logger.logInfo("no key - defaulting to disabled");
			return false;
		}
		
		
		boolean enabled = prefs.getBoolean(keyName, false);
		_logger.logInfo(enabled?"Enabled":"Disabled");
		
		return enabled;
	}

	public static boolean pluginEnabledForWorld(String worldName, String pluginName) {
		Preferences prefs = node(WorldsRoot);
		String keyName = pluginName + "Enabled";
		boolean keyExists = false;
		boolean worldExists = false;
		
		_logger.logInfo("checking world pref: " + prefs.absolutePath() + "/" + worldName + "/" + keyName);
		
		// Does an entry for that world exist?
		try {
			for (String world: prefs.childrenNames()) {
				if (world.equals(worldName)) {
					worldExists = true;
					break;
				}
			}
		} catch (BackingStoreException e) {
			_logger.logError("Caught exception while getting list of pref keys", e);
			return pluginEnabledGlobally(pluginName);
		}
		
		if (!worldExists) {
			return pluginEnabledGlobally(pluginName);
		}
		
		// Does the key exist?
		try {
			for (String key: prefs.keys()) {
				if (key.equals(keyName)) {
					keyExists = true;
					break;
				}
			}
		} catch (BackingStoreException e) {
			_logger.logError("Caught exception while getting list of pref keys", e);
		}
		
		if (!keyExists) {
			_logger.logInfo("no key - defaulting to disabled");
			return pluginEnabledGlobally(pluginName);
		}
		
		
		boolean enabled = prefs.getBoolean(keyName, false);
		_logger.logInfo(enabled?"Enabled":"Disabled");
		
		return enabled;
	}
}
