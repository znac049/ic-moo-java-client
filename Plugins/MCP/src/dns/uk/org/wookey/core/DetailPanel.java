package dns.uk.org.wookey.core;

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
import uk.org.wookey.ICPlugin.MCP.MCPCommand;

public class DetailPanel extends JPanel implements TreeSelectionListener {
	private static final long serialVersionUID = 1L;

	private Logger _logger = new Logger("WkCorePropsPanel");
	
	private JTree tree;
	private PropertyList props;
	private VerbList verbs;
	private JTabbedPane tabs;
	
	public DetailPanel(TreePanel corePanel) {
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
		MooObject ob = (MooObject) node.getUserObject();
		
		_logger.logInfo("NODE: " + ob.getName());
		
		ArrayList<Property> pList = ob.getPropertyList();
		for (Property prop: pList) {
			// fire off MCP getprop requests to the server
			MCPCommand cmd = new MCPCommand();
			
			cmd.setAuthKey(ob.getMCP().authKey);
			cmd.setName(WookeyCoreHandler.packageName, "getprop");
			cmd.addParam("objnum", "" + ob.getObjNum());
			cmd.addParam("propertyname", "" + prop.getName());
			
			ob.getMCP().queueOutgoingCommand(cmd);
		}
				
		Collections.sort(pList);
		props.buildList(pList);
		
		ArrayList<Verb> vList = ob.getVerbList();
		for (Verb verb: vList) {
			// fire off MCP getverb requests to the server
			MCPCommand cmd = new MCPCommand();
			
			cmd.setAuthKey(ob.getMCP().authKey);
			cmd.setName(WookeyCoreHandler.packageName, "getverb");
			cmd.addParam("objnum", "" + ob.getObjNum());
			cmd.addParam("propertyname", "" + verb.getName());
			
			ob.getMCP().queueOutgoingCommand(cmd);
		}
				
		Collections.sort(vList);
		verbs.buildList(vList);
		
		tabs.repaint();
	}
}
