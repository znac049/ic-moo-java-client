package dns.uk.org.wookey.core;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import uk.org.wookey.IC.Utils.Logger;

public class PropertyLabel extends JLabel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private static Logger _logger = new Logger("PropertyLabel");
	
	private Property property;
	
	public PropertyLabel(Property prop) {
		super();
		
		property = prop;
		
		setText(property.getName());
		
		setOpaque(true);
		setBorder(null);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setHorizontalAlignment(SwingConstants.LEFT);
		
		if (!prop.isInherited()) {
			setForeground(Color.BLUE);
		}
		else {
			setForeground(Color.DARK_GRAY);
		}
		
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		_logger.logInfo("Click on '" + property.getName() + "'");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
