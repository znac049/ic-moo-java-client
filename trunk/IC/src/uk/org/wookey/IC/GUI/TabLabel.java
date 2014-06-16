package uk.org.wookey.IC.GUI;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.org.wookey.IC.Utils.LED;
import uk.org.wookey.IC.Utils.Logger;

public class TabLabel extends JPanel {
	private Logger _logger = new Logger("TabLabel");
	
	public TabLabel(String lab, LED led) {
		super();
		
		setOpaque(false);
		
		JLabel tabLab = new JLabel(lab, led, JLabel.LEFT);
		
		add(tabLab);
		
		BufferedImage crossIcon = null;
		BufferedImage emptyIcon = null;
		try {
			emptyIcon = ImageIO.read(new File("images/empty-cross.png"));
			crossIcon = ImageIO.read(new File("images/cross.png"));
		} catch (IOException e) {
			_logger.logError("Failed to load cross.png");
		}
		JButton closeButton = new JButton(new ImageIcon(emptyIcon));
		closeButton.setRolloverIcon(new ImageIcon(crossIcon));
		closeButton.setBorder(BorderFactory.createEmptyBorder());
		closeButton.setContentAreaFilled(false);
		closeButton.setRolloverEnabled(true);
		
		add(closeButton);
		
        //addMouseMotionListener(new MouseStuff());
	}
	
	class MouseStuff implements MouseMotionListener {
		public void MouseEntered(MouseEvent e) {
			_logger.logInfo("Entered tab area");
		}

		public void MouseExited(MouseEvent e) {
			_logger.logInfo("Exited tab area");
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
		}
	}
}
