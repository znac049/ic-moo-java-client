package uk.org.wookey.IC.Utils;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;


public class WorldCache {
	private static AssociativeArray worlds = new AssociativeArray();
	private static Logger _logger = new Logger("WorldCache");
	
	public WorldDetail getWorld(String name) {
		_logger.logMsg("Lookup world '" + name + "'.");
		int index = worlds.getIndex(name);
		
		if (index != -1) {
			_logger.logMsg("Found in cache");
			return (WorldDetail) worlds.get(index);
		}
		
		WorldDetail world = new WorldDetail(name);
		worlds.add(name, world);
		_logger.logMsg("Added to cache.");
		
		return world;
	}
	
	public void populateCache() {
		Preferences wlds = Preferences.userRoot().node("uk/org/wookey/IC/worlds");
		
		// Attempt to connect to any worlds flagged for autoconnect
		String worldNames[];
		try {
			worldNames = wlds.childrenNames();

			for (int i=0; i<worldNames.length; i++) {
				getWorld(worldNames[i]);
			}
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String [] getWorldnames() {
		Preferences wlds = Preferences.userRoot().node("uk/org/wookey/IC/worlds");
	
		try {
			String [] names = wlds.childrenNames();
			return names;
		} catch (BackingStoreException e) {
			String [] names = {};
			return names;
		}
	}
	
	public void  deleteEntry(String name) {
		Preferences wlds = Preferences.userRoot().node("uk/org/wookey/IC/worlds");
		int index = worlds.getIndex(name);

		try {
			wlds.node(name).removeNode();
		} catch (BackingStoreException e) {
			// Its probably never been saved - ignore this.
		}
				
		if (index != -1) {
			worlds.remove(index);
		}
	}
}
