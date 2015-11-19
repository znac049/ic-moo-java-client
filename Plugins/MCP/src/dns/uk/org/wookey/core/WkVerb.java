package dns.uk.org.wookey.core;

import uk.org.wookey.IC.Utils.Logger;

public class WkVerb implements Comparable<WkVerb> {
	private Logger _logger = new Logger("WkVerb");
	
	public String name;

	public WkVerb(String name) {
		_logger.logInfo("new verb '" + name + "'");
		
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(WkVerb other) {
		return name.compareToIgnoreCase(other.name);
	}
}
