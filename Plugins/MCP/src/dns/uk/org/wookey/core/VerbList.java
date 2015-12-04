package dns.uk.org.wookey.core;

import java.util.ArrayList;

import javax.swing.JLabel;

public class VerbList extends ColumnList {
	private static final long serialVersionUID = 1L;
	
	public VerbList() {
		super(6);
	}
	
	protected void rebuildList() {
		ArrayList<Verb> vList = ob.getVerbList();
		
		reset();
		
		addColumn("Verb ");
		addColumn("Owner ");
		addColumn("Perms");
		addColumn("Direct ");
		addColumn("Prep ");
		addColumn("Ind.");

		for (Verb verb: vList) {
			addVerb(verb);
		}

		packColumns();
	}
	
	private void addVerb(Verb verb) {
		VerbLabel vName = new VerbLabel(verb);	
		JLabel owner = new JLabel(" - ");
		JLabel perms = new JLabel(" - ");
		JLabel direct = new JLabel(" - ");
		JLabel prep = new JLabel(" - ");
		JLabel indirect = new JLabel(" - ");
		
		if (verb.isValid()) {
			owner.setText(" " + verb.getOwner() + " ");
			perms.setText(verb.getPerms() + " ");
			direct.setText(verb.getDirect() + " ");
			prep.setText(verb.getPreposition() + " ");
			indirect.setText(verb.getIndirect());
		}
		
		addColumn(vName);
		addColumn(owner);
		addColumn(perms);			
		addColumn(direct);			
		addColumn(prep);			
		addColumn(indirect);					
	}
}
