package uk.org.wookey.IC.newUtils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import uk.org.wookey.IC.Utils.Logger;

public class SimpleKeyHandler implements KeyListener {
	private Logger _logger = new Logger("SimpleKeyListener");
	private KeyCode keyCode = new KeyCode(0);

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		int key = keyEvent.getKeyCode();
		
		switch (key) {
		case KeyEvent.VK_ALT:
			keyCode.alt(true);;
			break;

		case KeyEvent.VK_CONTROL:
			keyCode.ctrl(true);;
			break;
		
		case KeyEvent.VK_SHIFT:
			keyCode.shift(true);
			break;
		
		case KeyEvent.VK_WINDOWS:
			keyCode.windows(true);
			break;
			
		default:
			// some other key. take an interest if any of the magic keys are also pressed
			keyCode.set(key);
			if (keyCode.nonPrintable()) {
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		int key = keyEvent.getKeyCode();
		
		switch (key) {
		case KeyEvent.VK_ALT:
			keyCode.alt(false);;
			break;

		case KeyEvent.VK_CONTROL:
			keyCode.ctrl(false);;
			break;
		
		case KeyEvent.VK_SHIFT:
			keyCode.shift(false);
			break;
		
		case KeyEvent.VK_WINDOWS:
			keyCode.windows(false);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}	
}
