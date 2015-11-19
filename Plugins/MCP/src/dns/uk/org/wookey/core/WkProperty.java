package dns.uk.org.wookey.core;

import uk.org.wookey.IC.Utils.Logger;

public class WkProperty implements Comparable<WkProperty> {
	@SuppressWarnings("unused")
	private Logger _logger = new Logger("WkProperty");
	
	public String name;

	public WkProperty(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(WkProperty other) {
		return name.compareToIgnoreCase(other.name);
	}
}
