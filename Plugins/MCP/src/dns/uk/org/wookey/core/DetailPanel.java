package dns.uk.org.wookey.core;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import uk.org.wookey.IC.Utils.Logger;

public class DetailPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;

	private Logger _logger = new Logger("WkCorePropsPanel");
	
	private JTree tree;
	private PropertyList props;
	private VerbList verbs;
	private JTabbedPane tabs;
	@SuppressWarnings("unused")
	private WookeyCoreHandler handler;
	
	public DetailPanel(WookeyCoreHandler wookeyCoreHandler, TreePanel corePanel) {
		super();
		
		setLayout(new BorderLayout());
		
		handler = wookeyCoreHandler;
		
		tree = corePanel.getTree();
		//tree.addTreeSelectionListener(this);
		tree.addMouseListener(this);

		tabs = new JTabbedPane();
		
		props = new PropertyList();
		tabs.addTab("Properties",  props);
		
		verbs = new VerbList();
		tabs.addTab("Verbs", verbs);
		
		add(tabs, BorderLayout.CENTER);
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
                
                verbs.setObject(ob);
                props.setObject(ob);
                
        		revalidate();
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
