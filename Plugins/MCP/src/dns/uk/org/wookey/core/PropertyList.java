package dns.uk.org.wookey.core;

import java.util.ArrayList;

import javax.swing.JLabel;

public class PropertyList extends ColumnList {
	private static final long serialVersionUID = 1L;
	
	public PropertyList() {
		super(3);
	}
	
	protected void rebuildList() {
		ArrayList<Property> pList = ob.getPropertyList(true);
		
		reset();
		
		addColumn("Property ");
		addColumn("Owner ");
		addColumn("Perms");

		for (Property prop: pList) {
			addProp(prop);
		}

		packColumns();
	}
	
	private void addProp(Property prop) {
		PropertyLabel vName = new PropertyLabel(prop);	
		JLabel owner = new JLabel(" - ");
		JLabel perms = new JLabel(" - ");
		
		if (prop.isValid()) {
			owner.setText(" " + prop.getOwner() + " ");
			perms.setText(prop.getPerms() + " ");
		}
		
		addColumn(vName);
		addColumn(owner);
		addColumn(perms);			
	}
}
