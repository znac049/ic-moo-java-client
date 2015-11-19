package dns.uk.org.wookey.core;

import uk.org.wookey.IC.Utils.Logger;

public class Verb implements Comparable<Verb> {
	private Logger _logger = new Logger("WkVerb");
	
	public String name;

	public Verb(String name) {
		_logger.logInfo("new verb '" + name + "'");
		
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Verb other) {
		return name.compareToIgnoreCase(other.name);
	}
}
