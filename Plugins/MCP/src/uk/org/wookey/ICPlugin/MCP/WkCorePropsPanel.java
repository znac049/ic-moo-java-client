package uk.org.wookey.ICPlugin.MCP;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import uk.org.wookey.IC.Utils.Logger;

public class WkCorePropsPanel extends JPanel implements TreeSelectionListener {
	private static final long serialVersionUID = 1L;

	private Logger _logger = new Logger("WkCorePropsPanel");
	
	private JTree tree;
	private PropertyList props;
	private VerbList verbs;
	private JTabbedPane tabs;
	
	public WkCorePropsPanel(WkCoreTreePanel corePanel) {
		super();
		
		setLayout(new BorderLayout());
		
		tree = corePanel.getTree();
		tree.addTreeSelectionListener(this);		

		tabs = new JTabbedPane();
		
		props = new PropertyList();
		tabs.addTab("Properties",  props);
		
		verbs = new VerbList();
		tabs.addTab("Verbs", verbs);
		
		add(tabs, BorderLayout.CENTER);
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

		/* if nothing is selected */ 
		if (node == null) return;

		/* retrieve the node that was selected */ 
		WkObject wk = (WkObject) node.getUserObject();
		
		_logger.logInfo("NODE: " + wk.getName());
		
		ArrayList<WkProperty> pList = wk.getPropertyList();
		Collections.sort(pList);
		props.buildList(pList);
		
		ArrayList<WkVerb> vList = wk.getVerbList();
		Collections.sort(vList);
		verbs.buildList(vList);
	}
}
