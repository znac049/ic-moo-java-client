package dns.uk.org.wookey.core;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.ICPlugin.MCP.MCP;
import uk.org.wookey.ICPlugin.MCP.MCPCommand;

public class DetailPanel extends JPanel implements TreeSelectionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	private Logger _logger = new Logger("WkCorePropsPanel");
	
	private JTree tree;
	private PropertyList props;
	private VerbList verbs;
	private JTabbedPane tabs;
	private WookeyCoreHandler handler;
	
	public DetailPanel(WookeyCoreHandler wookeyCoreHandler, TreePanel corePanel) {
		super();
		
		setLayout(new BorderLayout());
		
		handler = wookeyCoreHandler;
		
		tree = corePanel.getTree();
		//tree.addTreeSelectionListener(this);
		tree.addMouseListener(this);

		tabs = new JTabbedPane();
		
		props = new PropertyList(wookeyCoreHandler);
		tabs.addTab("Properties",  props);
		
		verbs = new VerbList();
		tabs.addTab("Verbs", verbs);
		
		add(tabs, BorderLayout.CENTER);
	}
	
	private void queryPropertyDetails(MooObject ob) {
		MCP mcp = ob.getMCPHandler().getMCP();
		ArrayList<Property> pList = ob.getPropertyList(false);

		for (Property prop: pList) {
			// fire off MCP getprop requests to the server
			MCPCommand cmd = new MCPCommand();
			
			_logger.logInfo("getprop: #" + ob.getObjNum() + "." + prop.getName());
			
			cmd.setAuthKey(mcp.authKey);
			cmd.setName(handler.getName(), "getprop");
			cmd.addParam("objnum", "" + ob.getObjNum());
			cmd.addParam("propname", "" + prop.getName());
			
			mcp.queueOutgoingCommand(cmd);
		}

		Collections.sort(pList);
		props.buildList(ob);
	}

	private void queryVerbDetails(MooObject ob) {
		MCP mcp = ob.getMCPHandler().getMCP();
		ArrayList<Verb> vList = ob.getVerbList();

		for (Verb verb: vList) {
			// fire off MCP getverb requests to the server
			MCPCommand cmd = new MCPCommand();
			
			String vbName = verb.getName();
			int space = vbName.indexOf(' ');
			if (space != -1) {
				vbName = vbName.substring(0,  space);
			}
			
			int asterix = vbName.indexOf('*');
			if (asterix != -1) {
				vbName = vbName.substring(0,  asterix);
			}
			
			cmd.setAuthKey(mcp.authKey);
			cmd.setName(handler.getName(), "getverb");
			cmd.addParam("objnum", "" + ob.getObjNum());
			cmd.addParam("verbname", "" + vbName);
			
			mcp.queueOutgoingCommand(cmd);
		}
				
		Collections.sort(vList);
		verbs.buildList(vList);
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

		/* if nothing is selected */ 
		if (node == null) return;

		/* retrieve the node that was selected */ 
		MooObject ob = (MooObject) node.getUserObject();
		
		_logger.logInfo("NODE: " + ob.getName());
		
		queryPropertyDetails(ob);
		queryVerbDetails(ob);
		
		tabs.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
        int selRow = tree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
        
        if (selPath == null) {
        	return;
        }
        
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) selPath.getLastPathComponent();

		if(selRow != -1) {
			MooObject ob = (MooObject) node.getUserObject();

			if(e.getClickCount() == 1) {
                _logger.logInfo("SINGLE: " + ob.getName());
                
        		queryPropertyDetails(ob);
        		queryVerbDetails(ob);
        		
        		tabs.repaint();
            }
            else if(e.getClickCount() == 2) {
                _logger.logInfo("DOUBLE: " + ob.getName());
            }
        }
 	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
