package dns.uk.org.wookey.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.SpringUtilities;

public class PropertyList extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Logger _logger = new Logger("PropertyList");

	private JPanel props;
	@SuppressWarnings("unused")
	private WookeyCoreHandler handler;
	
	public PropertyList(WookeyCoreHandler wookeyCoreHandler) {
		super();
		
		handler = wookeyCoreHandler;
		
		setLayout(new BorderLayout());
		setBorder(new LineBorder(Color.black));
		
		//setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		
		props = new JPanel();
		
		props.setLayout(new SpringLayout());
		props.setBackground(Color.white);
		props.setBorder(BorderFactory.createEmptyBorder(2, 4, 0, 4));
		
		JScrollPane scroller = new JScrollPane();
        scroller.getViewport().add(props);
        scroller.setMinimumSize(new Dimension(150, scroller.getMaximumSize().height));
        scroller.setSize(scroller.getMinimumSize());
		add(scroller, BorderLayout.CENTER);
	}
	
	public void buildList(MooObject obj) {
		ArrayList<Property> pList = obj.getPropertyList(true);
		
		props.removeAll();

		for (Property prop: pList) {
			PropertyLabel pName = new PropertyLabel(prop);
			JLabel owner = new JLabel("-");
			JLabel perms = new JLabel("-");
			
			if (prop.isValid()) {
				owner.setText(prop.getOwner());
				perms.setText(prop.getPerms());
			}
			
			props.add(pName);
			props.add(owner);
			props.add(perms);
		}
		
		SpringUtilities.makeCompactGrid(props, pList.size(), 3, 0, 0, 2, 2);
		
		props.revalidate();
	}
	
	public void validate() {
		_logger.logInfo("WOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
	}
}
