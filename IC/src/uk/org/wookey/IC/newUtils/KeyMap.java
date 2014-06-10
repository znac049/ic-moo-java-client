package uk.org.wookey.IC.newUtils;

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

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
	
	public boolean save(Preferences parentPrefs) {
		Preferences prefs = parentPrefs.node("KeyMap");

		_logger.logInfo("Saving keymap to node " + prefs.absolutePath());
		
		for (KeyMapping km: map) {
			KeyCode key = new KeyCode(km.getKeyCode());
			String keyName = key.toString(); 
			Preferences p = prefs.node(keyName);
			
			_logger.logInfo("Saving key " + key.toString());
			p.putInt("KeyCode", km.getKeyCode());
			p.put("Macro", km.getMacro().getName());
		}
		
		try {
			prefs.flush();
		} catch (BackingStoreException e1) {
			_logger.logError("Failed to flush preferences at " + prefs.absolutePath());
			return false;
		}
		
		return true;
	}
	
	public boolean load(Preferences parentPrefs) {
		Preferences prefs = parentPrefs.node("KeyMap");

		_logger.logInfo("Loading keymap from node " + prefs.absolutePath());

		try {
			for (String nodeName: prefs.childrenNames()) {
				Preferences p = prefs.node(nodeName);
				KeyCode keyCode = new KeyCode(p.getInt("KeyCode", 0));
				String macroName = p.get("Macro", "");
				
				add(keyCode, macroName);
				
				_logger.logInfo("Loading key " + nodeName + " for " + macroName);
			}
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return true;
	}
}
