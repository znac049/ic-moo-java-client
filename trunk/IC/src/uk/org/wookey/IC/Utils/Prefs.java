package uk.org.wookey.IC.Utils;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Prefs {
	private final static String root = "uk/org/wookey/IC";
	private static Preferences appRoot = Preferences.userRoot().node(root);
	
	public static Preferences getWorldPrefs(String worldName){
		Preferences worldsNode = appRoot.node("worlds");
		Preferences node = null;
		
		try {
			if (!worldsNode.nodeExists(worldName)) {
				node = worldsNode.node(worldName);
				node.flush();
			}
			else {
				node = worldsNode.node(worldName);
			}
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		
		return node;
	}
}
