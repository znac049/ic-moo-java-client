package uk.org.wookey.IC.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.org.wookey.IC.Utils.ImagePanel;

public class MainStatusBar extends JPanel {
	private static final long serialVersionUID = 3127282971169408687L;
	private static MainStatusBar _sb = new MainStatusBar();
	
	private JLabel timerLabel;

	public MainStatusBar() {
		super();
		
		this.setBackground(new Color(0xd0, 0xd0, 0xd0));
		
		timerLabel = new JLabel();
		timerLabel.setMinimumSize(new Dimension(170, timerLabel.getMinimumSize().height));
		add(timerLabel);
		
		
		//addSpacer();

		setTimerMessage(0);
	}
	
	public void addSpacer() {
		ImagePanel spacer = new ImagePanel(new File("images/h-spacer.png"));
		spacer.setOpaque(false);
		add(spacer);
	}
	
	public void setTimerMessage(int queueLength) {
		timerLabel.setText("Timer Queue: " + queueLength);
	}

	public static MainStatusBar getMainStatusBar() {
		return _sb;
	}
	
}
