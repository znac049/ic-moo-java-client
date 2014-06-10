package uk.org.wookey.IC.newUtils;

import uk.org.wookey.IC.Utils.Logger;

public class KeyMapping {
	Logger _logger = new Logger("KeyMapping");
	
	private int key;
	private Macro macro;
	
	public KeyMapping() {
		key = -1;
		macro = null;
	}
	
	public KeyMapping(int keyCode, String name) {
		set(keyCode, name);
	}
	
	public void set(int keyCode, Macro mapsTo) {
		key = keyCode;
		macro = mapsTo;
	}
	
	public void set(int keyCode, String macroName) {
		key = keyCode;
		macro = new Macro(macroName);
	}
	
	public boolean invoke(JSEngine js, ServerPort server) {
		_logger.logInfo("invoking macro '" + macro.getName());
		
		return macro.exec(js,  server);
	}
	
	public boolean mapsToKey(int keyCode) {
		if (key == keyCode) {
			return true;
		}
		
		return false;
	}
	
	public int getKeyCode() {
		return key;
	}
	
	public Macro getMacro() {
		return macro;
	}
}
