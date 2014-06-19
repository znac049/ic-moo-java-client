package uk.org.wookey.ICPlugin.MCP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.SystemColor;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ServerConnection;

public class WkCorePanel extends JPanel {
	private Logger _logger = new Logger("WkCorePanel");

	private ServerConnection server;
	private MCP mcp;
	
	private JTree tree;
	private CodeNode objectRoot;
	private int playerObjNum;
	
	public WkCorePanel(MCP mcp, ServerConnection server) {
		super();
		
		setLayout(new BorderLayout());
	
		add(new JLabel("A long header of some sort!"), BorderLayout.PAGE_START);
		
		CodeNode top = new CodeNode("The Matrix");
	
		createNodes(top);
		tree = new JTree(top);
		tree.setCellRenderer(new ObjectTreeCellRenderer());
		
		playerObjNum = -1;
		this.mcp = mcp;
		this.server = server;
		
        JScrollPane scroller = new JScrollPane();
        scroller.getViewport().add(tree);
        scroller.setMinimumSize(new Dimension(300, scroller.getMaximumSize().height));
		add(scroller, BorderLayout.CENTER);
	}
	
	private void createNodes(CodeNode top) {
	    CodeNode node;
	   
	    objectRoot = new CodeNode("Objects");
	    top.add(objectRoot);
	}
	
	public void registerObject(MCP mcp, int objNum, String props, String verbs, String parents) {
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
					MCPCommand cmd = new MCPCommand();
					cmd.setAuthKey(mcp.authKey);
					cmd.setName(WookeyCore.packageName, "getobj");
					cmd.addParam("objnum", ""+parentObjNum);
					//queueCommand(cmd);
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
		if (!props.equals("")) {
			for (String prop: props.split("\n")) {
				ob.addProperty(prop);
			}
		}

		if (!verbs.equals("")) {
			for (String verb: verbs.split("\n")) {
				ob.addVerb(verb);
			}
		}
	}
	
	public void setPlayerObj(int num) {
		playerObjNum = num;
		_logger.logInfo("Player object number set to " + playerObjNum);
	}
	
	private void queueCommand(MCPCommand cmd) {
		mcp.queueOutgoingCommand(cmd);
	}
	
	public class ObjectTreeCellRenderer extends	JLabel implements TreeCellRenderer
	{
		private ImageIcon objectImage;
		private ImageIcon propertyImage;
		private ImageIcon verbImage;
		private ImageIcon nodeImage;

		private boolean	bSelected;
		
		public ObjectTreeCellRenderer() {
			// Load the images
			objectImage = new ImageIcon("images/object.png");
			propertyImage = new ImageIcon("images/property.png");
			verbImage = new ImageIcon("images/verb.png");			
			nodeImage = new ImageIcon("images/node.png");			
		}
		
		public Component getTreeCellRendererComponent( JTree tree,
						Object value, boolean bSelected, boolean bExpanded,
								boolean bLeaf, int iRow, boolean bHasFocus ) {
			// Find out which node we are rendering and get its text
			CodeNode node = (CodeNode)value;
			Object userObj = node.getUserObject();
			String label;
			
			if (userObj instanceof WkObject) {
				WkObject ob = (WkObject)userObj;
				
				setIcon(objectImage);
				label = ob.tostring();
			}
			else if (userObj instanceof String) {
				label = (String)userObj;
				
				if ((label.length() > 0) && (label.startsWith("."))) {
					setIcon(propertyImage);
				}
				else if ((label.length() > 0) && (label.startsWith(":"))) {
					setIcon(verbImage);
					label = label.substring(1) + "()";
				}
				else {
					setIcon(nodeImage);
				}
			}
			else {
				label = "???";
				setIcon(nodeImage);
			}
			
			this.bSelected = bSelected;
			
			// Set the correct foreground color
			if( !bSelected )
				setForeground( Color.black );
			else
				setForeground( Color.white );
		
		
			// Add the text to the cell
			setText(label);
		
			return this;
		}
		
		// This is a hack to paint the background.  Normally a JLabel can
		// paint its own background, but due to an apparent bug or
		// limitation in the TreeCellRenderer, the paint method is
		// required to handle this.
		public void paint(Graphics g) {
			Color		bColor;
			Icon		currentI = getIcon();
		
			// Set the correct background color
			bColor = bSelected ? SystemColor.textHighlight : Color.white;
			g.setColor(bColor);
		
			// Draw a rectangle in the background of the cell
			g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
		
			super.paint(g);
		}
	}
}
