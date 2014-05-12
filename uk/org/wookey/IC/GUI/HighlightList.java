package uk.org.wookey.IC.GUI;

import javax.swing.JList;

public class HighlightList extends JList {
	private static final long serialVersionUID = -2388781237818604445L;
	public HighlightList() {
		super();

		populateList();
	}

	private void populateList() {
	}
	
	public void resetList() {
		populateList();
	}
}
