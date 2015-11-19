package dns.uk.org.wookey.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import uk.org.wookey.IC.Utils.SpringUtilities;

public class PropertyList extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel props;
	
	public PropertyList() {
		super();
		
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
	
	public void buildList(ArrayList<Property> pList) {
		props.removeAll();

		for (Property prop: pList) {
			final JButton pName = new JButton(prop.getName());
			
			JLabel pIdle = new JLabel("42");
			
			pName.setOpaque(true);
			pName.setContentAreaFilled(false);
			pName.setBorder(null);
			pName.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));
			//pName.setBorderPainted(false);
			pName.setFocusPainted(false);
			pName.setMargin(new Insets(0, 0, 0, 0));
			pName.setCursor(new Cursor(Cursor.HAND_CURSOR));
			pName.setHorizontalAlignment(SwingConstants.LEFT);
			pName.setBackground(Color.GREEN);

			//pLoc.setOpaque(false);
			pIdle.setOpaque(false);
			
			props.add(pName);
			//connectedPlayers.add(pLoc);
			props.add(pIdle);
		}
		
		SpringUtilities.makeCompactGrid(props, pList.size(), 2, 0, 0, 2, 2);
		
		props.revalidate();
	}
}
