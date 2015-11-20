package dns.uk.org.wookey.core;

import uk.org.wookey.IC.Utils.Logger;

public class Property implements Comparable<Property> {
	@SuppressWarnings("unused")
	private Logger _logger = new Logger("WkProperty");
	
	private String name;
	private MooObject ownerObject;

	public Property(MooObject ob, String propName) {
		ownerObject = ob;
		name = propName;
	}
	
	public String getName() {
		return name;
	}
	
	public MooObject getOwnerObject() {
		return ownerObject;
	}

	@Override
	public int compareTo(Property other) {
		return name.compareToIgnoreCase(other.name);
	}
}
