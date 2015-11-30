package dns.uk.org.wookey.core;

import java.util.ArrayList;
import java.util.Collections;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.ICPlugin.MCP.MCPCommand;
import uk.org.wookey.ICPlugin.MCP.MCPCommandQueue;
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
		
		//Request details about the object
		MCPCommand cmd = new MCPCommand();
		cmd.setAuthKey(mcpHandler.getMCP().authKey);
		cmd.setName(mcpHandler.getName(), "getprop");
		cmd.addParam("objnum", ""+objNum);
		cmd.addParam("propname", name);
		mcpHandler.getMCP().queueOutgoingCommand(cmd, MCPCommandQueue.highPriority);

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
	
	private ArrayList<Property> getParentProperties() {
		ArrayList<Property> props = new ArrayList<Property>();
		
		_logger.logInfo("Looking for parent properties of #" + getObjNum());
		_logger.logInfo("..parent is #" + getParentObjNum());
		
		if (getParentObjNum() != -1) {
			try {
				MooObject ob = mcpHandler.getObjectDB().getObject(getParentObjNum());
				
				for (Property prop: ob.getPropertyList(true)) {
					props.add(new Property(prop));
				}
			} catch (MCPException e) {

			}
		}
		
		for (Property prop: props) {
			prop.setInherited(true);
		}
		
		return props;
	}
	
	public ArrayList<Property> getPropertyList(boolean recurse) {
		ArrayList<Property> allProperties = new ArrayList<Property>();
		
		if (recurse) {
			ArrayList<Property> parentProps = getParentProperties();
			
			for (Property prop: parentProps) {
				allProperties.add(prop);
			}
		}
		
		for (Property prop: propertyList) {
			allProperties.add(prop);
		}
		
		Collections.sort(allProperties);
		
		return allProperties;
	}
	
	public ArrayList<Verb> getVerbList() {
		return verbList;
	}
	
	public WookeyCoreHandler getMCPHandler() {
		return mcpHandler;
	}
}
