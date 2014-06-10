package uk.org.wookey.ICPlugin.MCP;

public class Player {
	private String name;
	private String id;
	private String idleTime;
	private String location;
	
	public Player(String pName, String objNum, String loc, String idle) {
		name = pName;
		id = objNum;
		idleTime = idle;
		location = loc;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIdle() {
		return idleTime;
	}
	
	public String getLocation() {
		return location;
	}
}
