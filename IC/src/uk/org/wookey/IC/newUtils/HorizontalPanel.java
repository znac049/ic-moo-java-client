package uk.org.wookey.IC.newUtils;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class HorizontalPanel extends JPanel {
	public HorizontalPanel() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

}
