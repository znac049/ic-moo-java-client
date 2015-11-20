package dns.uk.org.wookey.core;

import uk.org.wookey.IC.Utils.Logger;

public class Verb implements Comparable<Verb> {
	@SuppressWarnings("unused")
	private Logger _logger = new Logger("WkVerb");
	
	private String name;
	private MooObject ownerObject;

	public Verb(MooObject ob, String verbName) {
		ownerObject = ob;
		name = verbName;
	}
	
	public String getName() {
		return name;
	}
	
	public MooObject getOwnerObject() {
		return ownerObject;
	}

	@Override
	public int compareTo(Verb other) {
		return name.compareToIgnoreCase(other.name);
	}
}
