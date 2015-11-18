package uk.org.wookey.ICPlugin.MCP;

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

public class VerbList extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel verbs;
	
	public VerbList() {
		super();
		
		setLayout(new BorderLayout());
		setBorder(new LineBorder(Color.black));
		
		//setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		
		verbs = new JPanel();
		
		verbs.setLayout(new SpringLayout());
		verbs.setBackground(Color.white);
		verbs.setBorder(BorderFactory.createEmptyBorder(2, 4, 0, 4));
		
		JScrollPane scroller = new JScrollPane();
        scroller.getViewport().add(verbs);
        scroller.setMinimumSize(new Dimension(20, scroller.getMaximumSize().height));
        scroller.setSize(scroller.getMinimumSize());
		add(scroller, BorderLayout.CENTER);
	}
	
	public void buildList(ArrayList<WkVerb> vList) {
		verbs.removeAll();

		for (WkVerb vb: vList) {
			//JLabel pName = new JLabel(p.getName() + " (" + p.getId() + ")");
			final JButton vName = new JButton(vb.getName());
			
			//JLabel pLoc = new JLabel(p.getLocation());
			JLabel pIdle = new JLabel("42");
			
			vName.setOpaque(true);
			vName.setContentAreaFilled(false);
			vName.setBorder(null);
			vName.setBorderPainted(false);
			vName.setFocusPainted(false);
			vName.setMargin(new Insets(0, 0, 0, 0));
			vName.setCursor(new Cursor(Cursor.HAND_CURSOR));
			vName.setHorizontalAlignment(SwingConstants.LEFT);
			vName.setBackground(Color.GREEN);

			//pLoc.setOpaque(false);
			pIdle.setOpaque(false);
			
			verbs.add(vName);
			//connectedPlayers.add(pLoc);
			verbs.add(pIdle);
		}
		
		SpringUtilities.makeCompactGrid(verbs, vList.size(), 2, 0, 0, 2, 2);
		
		verbs.revalidate();
	}
}
