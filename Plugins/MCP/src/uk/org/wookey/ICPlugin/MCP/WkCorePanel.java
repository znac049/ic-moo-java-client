package uk.org.wookey.ICPlugin.MCP;

import javax.swing.JSplitPane;

public class WkCorePanel extends JSplitPane {
	private static final long serialVersionUID = 1L;

	public WkCorePanel() {
		super(JSplitPane.VERTICAL_SPLIT);
		
		//setResizeWeight(0.5);
		//setOneTouchExpandable(true);
		setDividerLocation(0.6);
		setContinuousLayout(true);
	}
}
