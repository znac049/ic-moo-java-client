package uk.org.wookey.ICPlugin.MCP;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WkCorePropsPanel extends JPanel {
	public WkCorePropsPanel() {
		super();
		
		setLayout(new BorderLayout());
		
		add(new JLabel("Properties"), BorderLayout.PAGE_START);
	}
}
