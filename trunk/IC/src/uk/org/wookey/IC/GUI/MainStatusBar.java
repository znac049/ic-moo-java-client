package uk.org.wookey.IC.GUI;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainStatusBar extends JPanel {
	private static final long serialVersionUID = 3127282971169408687L;
	private static MainStatusBar _sb = new MainStatusBar();
	
	private JLabel lab;

	public MainStatusBar() {
		super();
		
		this.setBackground(new Color(7, 141, 133));
		
		lab = new JLabel("Status Bar - needs some coding!");
		add(lab);
	}
	
	public void setMsg(String msg) {
		lab.setText(msg);
		repaint();
	}
	
	public static MainStatusBar getBar() {
		return _sb;
	}
}
