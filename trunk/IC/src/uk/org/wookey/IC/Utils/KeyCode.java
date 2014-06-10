package uk.org.wookey.IC.Utils;

import java.awt.event.KeyEvent;

public class KeyCode {
	public final static int ALT_MASK = 0x0400;
	public final static int CONTROL_MASK = 0x0800;
	public final static int WINDOWS_MASK = 0x1000;
	public final static int SHIFT_MASK = 0x2000;
	public final static int KEYCODE_MASK = 0x03ff;
	
	private int key;
	
	public KeyCode() {
		key = 0;
	}
	
	public KeyCode(int fullKeyCode) {
		key = fullKeyCode;
	}
	
	public void alt(boolean pressed) {
		key = pressed ? (key | ALT_MASK) : (key & ~ALT_MASK);
		set(0);
	}
	
	public void ctrl(boolean pressed) {
		key = pressed ? (key | CONTROL_MASK) : (key & ~CONTROL_MASK);
		set(0);
	}
	
	public void shift(boolean pressed) {
		key = pressed ? (key | SHIFT_MASK) : (key & ~SHIFT_MASK);
		set(0);
	}
	
	public void windows(boolean pressed) {
		key = pressed ? (key | WINDOWS_MASK) : (key & ~WINDOWS_MASK);
		set(0);
	}
	
	public void set(int keyCode) {
		key = (key & ~KEYCODE_MASK) | (keyCode & KEYCODE_MASK);
	}
	
	public int get() {
		return key;
	}
	
	public void reset() {
		reset(0);
	}
	
	public void reset(int fullKeyCode) {
		key = fullKeyCode;
	}
	
	public boolean nonPrintable() {
		int code = key & KEYCODE_MASK;
		int specialKeys = key & (~KEYCODE_MASK | SHIFT_MASK);
		
		if ((code != 0) & (specialKeys != 0)) {
			return true;
		}
		
		return false;
	}
	
	public String toString() {
		String res = "";
		int code = key & KEYCODE_MASK;
		
		if ((key & CONTROL_MASK) != 0) {
			res += "Ctrl-";
		}
		
		if ((key & ALT_MASK) != 0) {
			res += "Alt-";
		}
		
		if ((key & WINDOWS_MASK) != 0) {
			res += "Win-";
		}
		
		if ((key & SHIFT_MASK) != 0) {
			res += "Shift-";
		}
		
		if ((code >= KeyEvent.VK_A) & (code <= KeyEvent.VK_Z)) {
			res += Character.toString((char) (65 + code - KeyEvent.VK_A));
		}
		else if ((code >= KeyEvent.VK_0) & (code <= KeyEvent.VK_9)) {
			res += Character.toString((char) (48 + code - KeyEvent.VK_0));
		}
		else {
			res += "???";
		}
		
		return res;
	}
}
