package dns.uk.org.wookey.core;

import java.util.ArrayList;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.ICPlugin.MCP.MCPException;

public class MooObject {
	private Logger _logger = new Logger("WkObject");
	private int objNum;
	private String objName;
	private CodeNode treeNode;
	private int parentObjNum;
	
	private ArrayList<Verb> verbList;
	private ArrayList<Property> propertyList;
	
	private WookeyCoreHandler mcpHandler;

	public MooObject(int objNum, WookeyCoreHandler handler) {
		this.objNum = objNum;
		
		objName = null;
		
		parentObjNum = -1;
		
		treeNode = new CodeNode("#" + objNum);
		treeNode.setUserObject(this); 
		
		verbList = new ArrayList<Verb>();
		propertyList = new ArrayList<Property>();
		
		mcpHandler = handler;
	}
	
	public String tostring() {
		if (objName != null) {
			return objName + " (#" + objNum + ")";
		}
		
		return "#" + objNum;
	}
	
	public void addProperty(String name) {
		for (Property prop: propertyList) {
			if (prop.getName().equals(name)) {
				// Already got it
				return;
			}
		}
		
		Property prop = new Property(this, name);
		propertyList.add(prop);
	}
	
	public void addVerb(String name) {
		for (Verb vb: verbList) {
			if (vb.getName().equals(name)) {
				// Already got it
				return;
			}
		}
		
		Verb vb = new Verb(this, name);
		verbList.add(vb);
	}
	
	public Property getProperty(String name) {
		for (Property prop: propertyList) {
			if (prop.hasName(name)) {
				return prop;
			}
		}
		
		return null;
	}
	
	public Verb getVerb(String name) {
		for (Verb verb: verbList) {
			if (verb.hasName(name)) {
				return verb;
			}
		}
		
		return null;
	}
	
	public void setName(String objName) {
		this.objName = objName;
	}
	
	public String getName() {
		return objName;
	}
	
	public int getObjNum() {
		return objNum;
	}

	public void setObjNum(int objNum) {
		this.objNum = objNum;
	}

	public CodeNode getTreeNode() {
		return treeNode;
	}

	public CodeNode getKidsNode() {
		return treeNode;
	}

	public int getParentObjNum() {
		return parentObjNum;
	}

	public void setParentObjNum(int parentObjNum) {
		this.parentObjNum = parentObjNum;
	}
	
	private int locationOf(Property prop, ArrayList<Property> props) {
		for (int i=0; i<props.size(); i++) {
			Property p = props.get(i);
			
			if (p.getName().equalsIgnoreCase(prop.getName())) {
				return i;
			}
		}
		
		return -1;
	}
	
	private ArrayList<Property> mergeWithParentProperties() {
		_logger.logInfo("merging with parent properties");
		
		return propertyList;
	}
	
	public ArrayList<Property> getPropertyList(boolean recurse) {
		if (recurse) {
			return mergeWithParentProperties();
		}
		
		return propertyList;
	}
	
	public ArrayList<Verb> getVerbList() {
		return verbList;
	}
	
	public WookeyCoreHandler getMCPHandler() {
		return mcpHandler;
	}
}
