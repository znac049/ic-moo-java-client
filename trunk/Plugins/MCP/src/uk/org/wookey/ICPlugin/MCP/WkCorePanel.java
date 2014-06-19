package uk.org.wookey.ICPlugin.MCP;

import javax.swing.JSplitPane;

public class WkCorePanel extends JSplitPane {
	public WkCorePanel() {
		super(JSplitPane.VERTICAL_SPLIT);
		
		setResizeWeight(0.66);
		setOneTouchExpandable(true);
	}
}
