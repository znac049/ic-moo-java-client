package uk.org.wookey.IC.Utils;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class VerticalPanel extends JPanel {
	public VerticalPanel() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
}
