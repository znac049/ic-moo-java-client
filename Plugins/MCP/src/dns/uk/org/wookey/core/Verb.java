package dns.uk.org.wookey.core;

import uk.org.wookey.IC.Utils.Logger;

public class Verb implements Comparable<Verb> {
	@SuppressWarnings("unused")
	private Logger _logger = new Logger("WkVerb");
	
	private String name;
	private MooObject containingObject;
	private String owner;
	private String perms;
	private String direct;
	private String preposition;
	private String indirect;
	
	private boolean isValid;

	public Verb(MooObject ob, String verbName) {
		containingObject = ob;
		name = verbName;
		
		owner = null;
		perms = null;
		
		isValid = false;
	}
	
	public void setDetail(String owner, String perms, String direct, String preposition, String indirect) {
		this.owner = owner;
		this.perms = perms;
		this.direct = direct;
		this.preposition = preposition;
		this.indirect = indirect;
		
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
	
	public String getDirect() {
		return direct;
	}
	
	public String getPreposition() {
		return preposition;
	}
	
	public String getIndirect() {
		return indirect;
	}
	
	public MooObject getOwnerObject() {
		return containingObject;
	}

	public boolean isValid() {
		return isValid;
	}
	
	public boolean hasName(String target) {
		return name.equalsIgnoreCase(target);
	}

	@Override
	public int compareTo(Verb other) {
		return name.compareToIgnoreCase(other.name);
	}
}
