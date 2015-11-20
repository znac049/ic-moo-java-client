package dns.uk.org.wookey.core;

import uk.org.wookey.IC.Utils.Logger;

public class Property implements Comparable<Property> {
	@SuppressWarnings("unused")
	private Logger _logger = new Logger("WkProperty");
	
	private String name;
	private MooObject containingObject;
	private String owner;
	private String perms;
	
	private boolean isValid;

	public Property(MooObject ob, String propName) {
		containingObject = ob;
		name = propName;
		
		isValid = false;
	}
	
	public void setDetail(String owner, String perms) {
		this.owner = owner;
		this.perms = perms;
		
		isValid = true;
	}
	
	public String getName() {
		return name;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public String getPerms() {
		return perms;
	}
	
	public MooObject getContainingObject() {
		return containingObject;
	}
	
	public boolean isValid() {
		return isValid;
	}
	
	public boolean hasName(String target) {
		return name.equalsIgnoreCase(target);
	}

	@Override
	public int compareTo(Property other) {
		return name.compareToIgnoreCase(other.name);
	}
}
