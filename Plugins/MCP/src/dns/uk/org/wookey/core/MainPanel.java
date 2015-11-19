package dns.uk.org.wookey.core;

import javax.swing.JSplitPane;

public class MainPanel extends JSplitPane {
	private static final long serialVersionUID = 1L;

	public MainPanel() {
		super(JSplitPane.VERTICAL_SPLIT, true);
		
		setResizeWeight(0.5);
		//setOneTouchExpandable(true);
		setDividerLocation(200);
	}
}
