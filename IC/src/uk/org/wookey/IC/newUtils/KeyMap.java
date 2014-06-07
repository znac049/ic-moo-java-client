package uk.org.wookey.IC.newUtils;

import java.util.ArrayList;

import uk.org.wookey.IC.Utils.Logger;

public class KeyMap {
	private Logger _logger;
	private ArrayList<KeyMapping> map;
	private String name;
	private JSEngine js;
	private ServerPort server;
	
	public KeyMap(String mapName, JSEngine jsEngine, ServerPort serverPort) {
		_logger = new Logger("KeyMap - " + mapName);
		
		name = mapName;
		map = new ArrayList<KeyMapping>();
		
		js = jsEngine;
		server = serverPort;
	}
	
	public void add(int keyCode, String macroName) {
		add(new KeyCode(keyCode), macroName);
	}
	
	public void add(KeyCode keyCode, String macroName) {
		_logger.logInfo("Mapping key " + keyCode.toString() + " to macro '" + macroName + "'");
		
		for (KeyMapping km: map) {
			if (km.mapsToKey(keyCode.get())) {
				// Need to replace existing mapping
				km.set(keyCode.get(),  macroName);
				return;
			}
		}
		
		// It's a new mapping
		map.add(new KeyMapping(keyCode.get(), macroName));
	}
	
	public boolean exec(int keyCode) {
		return exec(new KeyCode(keyCode));
	}
	
	public boolean exec(KeyCode keyCode) {
		int targetCode = keyCode.get();
		
		_logger.logInfo("exec " + keyCode.toString());
		
		for (KeyMapping km: map) {
			if (km.mapsToKey(targetCode)) {
				km.invoke(js, server);
				return true;
			}
		}
	
		return false;
	}
	
	public ArrayList<KeyMapping> getMappings() {
		return map;
	}
}
