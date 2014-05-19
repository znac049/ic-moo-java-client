package uk.org.wookey.IC.Utils;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class WorldSettings {
	private String worldName;
	private Preferences world;
	private Logger _Logger = new Logger("WorldSettings");
	
	public WorldSettings(String name) {
		Preferences worlds = Preferences.userRoot().node("uk/org/wookey/IC/worlds");
		world = worlds.node(name);
		worldName = name;
	}
	
	public void set(String keyName, String val) {
		world.put(keyName, val);
	}
	
	public void set(String keyName, int val) {
		world.putInt(keyName, val);
	}
	
	public void set(String keyName, boolean val) {
		world.putBoolean(keyName, val);
	}
	
	public void set(String keyName, char val[]) {
		String stringVal = "";
		for (int i=0; i<val.length; i++) {
			stringVal = stringVal + val[i];
		}

		set(keyName, stringVal);
	}
	
	public String getString(String keyName) {
		return world.get(keyName, "");
	}
	
	public int getInt(String keyName) {
		return world.getInt(keyName, 0);
	}
	
	public boolean getBoolean(String keyName) {
		return world.getBoolean(keyName, false);
	}
	
	public void setWorldName(String name) {
		worldName = name;
	}
	
	public String getWorldName() {
		return worldName;
	}
}
