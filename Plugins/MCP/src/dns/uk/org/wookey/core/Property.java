package dns.uk.org.wookey.core;

import uk.org.wookey.IC.Utils.Logger;

public class Property implements Comparable<Property> {
	@SuppressWarnings("unused")
	private Logger _logger = new Logger("WkProperty");
	
	private String name;
	private MooObject containingObject;
	private String owner;
	private String perms;
	private boolean inherited;
	
	private boolean isValid;
	
	public Property(Property orig) {
		name = orig.name;
		containingObject = orig.containingObject;
		owner = orig.owner;
		perms = orig.perms;
		inherited = orig.inherited;
		isValid = orig.isValid;
	}

	public Property(MooObject ob, String propName) {
		containingObject = ob;
		name = propName;
		
		owner = null;
		perms = null;
		
		inherited = false;
		
		isValid = false;
	}
	
	public void setDetail(String owner, String perms) {
		this.owner = owner;
		this.perms = perms;
		
		isValid = true;
	}
	
	public void setInherited(boolean val) {
		inherited = val;
	}
	
	public boolean isInherited() {
		return inherited;
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
	
	public String toString() {
		String res = name;
		
		if (inherited) {
			res = name + " (i)";
		}
		
		return res;
	}

	@Override
	public int compareTo(Property other) {
		return name.compareToIgnoreCase(other.name);
	}
}
