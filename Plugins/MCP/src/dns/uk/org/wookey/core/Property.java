package dns.uk.org.wookey.core;

import uk.org.wookey.IC.Utils.Logger;

public class Property implements Comparable<Property> {
	@SuppressWarnings("unused")
	private Logger _logger = new Logger("WkProperty");
	
	public String name;

	public Property(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Property other) {
		return name.compareToIgnoreCase(other.name);
	}
}
