package uk.org.wookey.IC.newUtils;

import java.util.ArrayList;

import uk.org.wookey.IC.Utils.Logger;

public class KeyMap {
	private Logger _logger = new Logger("KeyMap");
	private ArrayList<KeyMapping> map;
	private String name;
	private JSEngine js;
	private ServerPort server;
	
	public KeyMap(String mapName, JSEngine jsEngine, ServerPort serverPort) {
		name = mapName;
		map = new ArrayList<KeyMapping>();
		
		js = jsEngine;
		server = serverPort;
	}
	
	public void add(int keyCode, String macroName) {
		for (KeyMapping km: map) {
			if (km.mapsToKey(keyCode)) {
				// Need to replace existing mapping
				km.set(keyCode,  macroName);
				return;
			}
		}
		
		// It's a new mapping
		map.add(new KeyMapping(keyCode, macroName));
	}
	
	public boolean exec(int keyCode) {
		for (KeyMapping km: map) {
			if (km.mapsToKey(keyCode)) {
				km.invoke(js, server);
				return true;
			}
		}
	
		return false;
	}
}
