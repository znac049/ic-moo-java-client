package uk.org.wookey.ICPlugin.MCP;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class WookeyCorePanel extends JPanel {
	private JTree tree;
	
	public WookeyCorePanel() {
		super();
		
		setLayout(new BorderLayout());
	
		add(new JLabel("A long header of some sort!"));
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("The Matrix");
	
		createNodes(top);
		tree = new JTree(top);
		
		add(tree, BorderLayout.CENTER);
	}
	
	private void createNodes(DefaultMutableTreeNode top) {
	    DefaultMutableTreeNode category = null;
	    DefaultMutableTreeNode book = null;
	    
	    category = new DefaultMutableTreeNode("#0");
	    top.add(category);
	    
	    category = new DefaultMutableTreeNode("player");
	    top.add(category);

	    category = new DefaultMutableTreeNode("location");
	    top.add(category);
	}
}
