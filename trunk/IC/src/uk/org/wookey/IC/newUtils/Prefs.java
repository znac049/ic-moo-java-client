package uk.org.wookey.IC.newUtils;

import java.util.prefs.Preferences;

public class Prefs {
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
}
