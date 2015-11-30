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

import uk.org.wookey.IC.Utils.SpringUtilities;

public class VerbList extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel verbs;
	
	public VerbList() {
		super();
		
		setLayout(new BorderLayout());		
		setBorder(new LineBorder(Color.black));
		
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
		verbs.removeAll();

		Color odd = Color.LIGHT_GRAY;
		Color even = Color.WHITE;
		int i = 0;
		
		for (Verb verb: vList) {
			VerbLabel vName = new VerbLabel(verb);
			
			JLabel owner = new JLabel(" - ");
			JLabel perms = new JLabel(" - ");
			JLabel direct = new JLabel(" - ");
			JLabel prep = new JLabel(" - ");
			JLabel indirect = new JLabel(" - ");
			
			owner.setOpaque(true);
			perms.setOpaque(true);
			direct.setOpaque(true);
			prep.setOpaque(true);
			indirect.setOpaque(true);
			
			owner.setMinimumSize(new Dimension(100, 20));
			
			if (verb.isValid()) {
				owner.setText(" " + verb.getOwner() + " ");
				perms.setText(verb.getPerms() + " ");
				direct.setText(verb.getDirect() + " ");
				prep.setText(verb.getPreposition() + " ");
				indirect.setText(verb.getIndirect());
			}
			
			if (i == 0) {
				vName.setBackground(even);
				owner.setBackground(even);
				perms.setBackground(even);
				direct.setBackground(even);
				prep.setBackground(even);
				indirect.setBackground(even);
				
				i++;
			}
			else {
				vName.setBackground(odd);
				owner.setBackground(odd);
				perms.setBackground(odd);
				direct.setBackground(odd);
				prep.setBackground(odd);
				indirect.setBackground(odd);
				
				i = 0;
			}
			
			verbs.add(vName);
			verbs.add(owner);
			verbs.add(perms);			
			verbs.add(direct);			
			verbs.add(prep);			
			verbs.add(indirect);			
		}
		
		SpringUtilities.makeCompactGrid(verbs, vList.size(), 6, 0, 0, 0, 0);
		
		verbs.revalidate();
	}
}
