package uk.org.wookey.IC.newUtils;

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Worlds {
	public static String[] getListOfWorlds() {
		String[] worlds;
		
		try {
			worlds = Preferences.userRoot().node(Prefs.WorldsRoot).childrenNames();
		} catch (BackingStoreException e) {
			worlds = null;
		}
		
		return worlds;
	}
}
