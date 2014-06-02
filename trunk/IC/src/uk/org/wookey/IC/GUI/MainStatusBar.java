package uk.org.wookey.IC.GUI;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainStatusBar extends JPanel {
	private static final long serialVersionUID = 3127282971169408687L;

	public MainStatusBar() {
		super();
		
		this.setBackground(new Color(7, 141, 133));
		
		add(new JLabel("Status Bar - needs some coding!"));
	}
}
