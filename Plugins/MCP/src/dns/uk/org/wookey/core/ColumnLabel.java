package dns.uk.org.wookey.core;

import java.awt.Dimension;

import javax.swing.JLabel;

public class ColumnLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	public ColumnLabel(String text, int minWidth) {
		super(text);
		
		Dimension min = getMinimumSize();
		min.width = minWidth;
		setMinimumSize(min);
	}
}
