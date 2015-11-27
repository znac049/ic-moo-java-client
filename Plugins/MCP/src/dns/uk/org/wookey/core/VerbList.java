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
import javax.swing.border.Border;
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
	
	public void buildList(ArrayList<Verb> vList) {
		Border b = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray);
		verbs.removeAll();

		for (Verb verb: vList) {
			VerbLabel vName = new VerbLabel(verb);
			
			JLabel owner = new JLabel("-");
			JLabel perms = new JLabel("-");
			JLabel direct = new JLabel("-");
			JLabel prep = new JLabel("-");
			JLabel indirect = new JLabel("-");
			
			owner.setBorder(b);
			perms.setBorder(b);
			direct.setBorder(b);
			prep.setBorder(b);
			indirect.setBorder(b);
			
			owner.setMinimumSize(new Dimension(100, 20));
			//perms.setMinimumSize(new Dimension(15, 7));
			
			if (verb.isValid()) {
				owner.setText(verb.getOwner());
				perms.setText(verb.getPerms());
				direct.setText(verb.getDirect());
				prep.setText(verb.getPreposition());
				indirect.setText(verb.getIndirect());
			}
			
			verbs.add(vName);
			verbs.add(owner);
			verbs.add(perms);			
			verbs.add(direct);			
			verbs.add(prep);			
			verbs.add(indirect);			
		}
		
		SpringUtilities.makeCompactGrid(verbs, vList.size(), 6, 0, 0, 2, 2);
		
		verbs.revalidate();
	}
}
