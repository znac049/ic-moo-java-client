package uk.org.wookey.IC.GUI;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class BorderPanel extends JPanel {
	public BorderPanel(String title) {
		super();
		
		setBorder(BorderFactory.createTitledBorder(title));
	}
}
