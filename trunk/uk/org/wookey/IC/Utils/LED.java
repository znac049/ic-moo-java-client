package uk.org.wookey.IC.Utils;

import javax.swing.*;
import java.awt.*;

public class LED implements Icon {
	private static final long serialVersionUID = -6537631324255210149L;
	int _width, _height;
	int rVal, gVal, bVal;
	
	public LED(int r, int g, int b)
	{
		_width = 16;
		_height = 16;
		
		rVal = r;
		gVal = g;
		bVal = b;
	}
	
	public boolean setColour(int r, int g, int b) {
		if ((rVal == r) && (gVal == g) && (bVal == b)) {
			return false;
		}
		
		rVal = r;
		gVal = g;
		bVal = b;
		
		return true;
	}

	public int getIconHeight() {
		return _width;
	}

	public int getIconWidth() {
		return _height;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		int wid = _width-4;
		int ht = _height-4;
		
		//g.translate(x, y+2);
		
		if ((rVal == 0) && (gVal == 0) && (bVal == 0)) {
			g.setColor(new Color(128, 128, 128));
			g.drawRect(x, y+2, wid, ht);
		}
		else {
			g.setColor(new Color(rVal, gVal, bVal));
			//	g.fillOval(x, y+2, wid, ht);
			g.fillRect(x, y+2, wid, ht);
			g.setColor(Color.BLACK);
			g.drawRect(x, y+2, wid, ht);
		}
	}
}
