package uk.org.wookey.ICPlugin.MCP;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ServerConnection;

public class WkCorePanel extends JPanel {
	private Logger _logger = new Logger("WkCorePanel");
	private JTree tree;
	private DefaultMutableTreeNode objectRoot;
	private int playerObjNum;
	private ServerConnection server;
	private ImageIcon objectIcon;;
	
	public WkCorePanel(ServerConnection server) {
		super();
		
		setLayout(new BorderLayout());
	
		add(new JLabel("A long header of some sort!"), BorderLayout.PAGE_START);
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("The Matrix");
	
		createNodes(top);
		tree = new JTree(top);
		
		playerObjNum = -1;
		this.server = server;
		
		objectIcon = new ImageIcon("images/object.png");

        JScrollPane scroller = new JScrollPane();
        scroller.getViewport().add(tree);
        scroller.setMinimumSize(new Dimension(300, scroller.getMaximumSize().height));
		add(scroller, BorderLayout.CENTER);
	}
	
	private void createNodes(DefaultMutableTreeNode top) {
	    DefaultMutableTreeNode node;
	   
	    objectRoot = new DefaultMutableTreeNode("Objects");
	    top.add(objectRoot);
	}
	
	public void registerObject(MCPRoot mcp, int objNum, String props, String verbs, String parents) {
		String[] parentItems;
		boolean root = true;
		WkObject ancestor = null;
		int ancestorObjNum = -1;
		
		if (!parents.equals("")) {
			parentItems = parents.split("\n");

			for (int i=parentItems.length-1; i>=0; i--) {
				String parent = parentItems[i];
				int parentObjNum = WkObjectDB.decodeObjectNumNoEx(parent);
				
				if (WkObjectDB.objectExists(parentObjNum)) {
					_logger.logInfo("Parent: " + parent + " exists");
					try {
						ancestor = WkObjectDB.getObject(parentObjNum);
						ancestorObjNum = parentObjNum;
					} catch (MCPException e) {
						_logger.logError("Object hierarcy broken:", e);
						return;
					}
				}
				else {
					_logger.logInfo("Parent: " + parent + " doesn't exist");
					WkObject ob = new WkObject(parentObjNum);
					
					ob.setParentObjNum(ancestorObjNum);
					
					if (root) {
						// Needs adding to the top of the tree
						_logger.logInfo("Root node");
						root = false;
						objectRoot.add(ob.getTreeNode());
					}
					else {
						// needs adding to an existing object node
						_logger.logMsg("tree node");
						ancestor.getTreeNode().add(ob.getTreeNode());
					}
					
					//Request details about the object
					//MCPCommand cmd = new MCPCommand();
					//cmd.setAuthKey(mcp.authKey);
					//cmd.setName(WookeyCore.packageName, "getobj");
					//cmd.addParam("objnum", ""+parentObjNum);
					//cmd.sendToServer(server);
					
					ancestor = ob;				
					ancestorObjNum = parentObjNum;
				}
				
				root = false;
			}
		}

		WkObject ob;
		if (WkObjectDB.objectExists(objNum)) {
			try {
				ob = WkObjectDB.getObject(objNum);
			} catch (MCPException e) {
				_logger.logError("Object database is corrupt", e);
				return;
			}
		}
		else {
			ob = new WkObject(objNum);
		}

		if (ancestor == null) {
			objectRoot.add(ob.getTreeNode());
		}
		else {
			ancestor.getTreeNode().add(ob.getTreeNode());
		}
		
		//Add verb and property info now.
	}
	
	public void setPlayerObj(int num) {
		playerObjNum = num;
		_logger.logInfo("Player object number set to " + playerObjNum);
	}
}
