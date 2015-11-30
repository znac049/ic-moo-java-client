package dns.uk.org.wookey.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
		
		Color odd = Color.LIGHT_GRAY;
		Color even = Color.WHITE;
		int i = 0;
		
		for (Property prop: pList) {
			PropertyLabel pName = new PropertyLabel(prop);
			ColumnLabel owner = new ColumnLabel("-", 50);
			JLabel perms = new JLabel("-");
			
			if (prop.isValid()) {
				owner.setText(" " + prop.getOwner() + " ");
				perms.setText(prop.getPerms());
			}

			owner.setOpaque(true);
			perms.setOpaque(true);
			
			if (i == 0) {
				pName.setBackground(even);
				owner.setBackground(even);
				perms.setBackground(even);
				
				i++;
			}
			else {
				pName.setBackground(odd);
				owner.setBackground(odd);
				perms.setBackground(odd);
				
				i = 0;
			}

			props.add(pName);
			props.add(owner);
			props.add(perms);
		}
		
		//packGrid();
		SpringUtilities.makeCompactGrid(props, pList.size(), 3, 0, 0, 0, 0);
		packGrid();
		
		props.revalidate();
	}
	
	private void packGrid() {
		final int numColumns = 3;
		int count = props.getComponentCount();
		double[] widths = new double[numColumns];
		Component[] components = props.getComponents();
		
		for (int i=0; i<numColumns; i++) {
			widths[i] = 0;
		}
		
		_logger.logMsg("Num components is " + count);
		if ((count % numColumns) != 0) {
			_logger.logError("Incorrect component count - should be a multiple of " + numColumns);
		}
		
		for (int i=0; i<count; i+=numColumns) {
			for (int j=0; j<numColumns; j++) {
				Component comp = components[i+j];
				
				if (comp.getPreferredSize().getWidth() > widths[j]) {
					widths[j] = comp.getPreferredSize().getWidth();
				}
			}
		}

		for (int i=0; i<count; i+=numColumns) {
			for (int j=0; j<numColumns; j++) {
				Component comp = components[i+j];
				Dimension d = comp.getPreferredSize();
				
				d.setSize(widths[j], d.getHeight());
				comp.setPreferredSize(d);
			}
		}
		
		_logger.logInfo("Final prop widths:");
		for (int i=0; i<numColumns; i++) {
			_logger.logInfo(" " + i + ": " + widths[i]);
		}
	}
}
