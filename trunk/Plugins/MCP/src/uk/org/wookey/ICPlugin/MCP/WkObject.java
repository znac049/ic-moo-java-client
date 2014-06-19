package uk.org.wookey.ICPlugin.MCP;

import uk.org.wookey.IC.Utils.Logger;

public class WkObject {
	private Logger _logger = new Logger("WkObject");
	private int objNum;
	private String objName;
	private boolean retrieved;
	private CodeNode treeNode;
	private CodeNode propsNode;
	private CodeNode verbsNode;
	private CodeNode kidsNode;
	private int parentObjNum;
	private String verbs;
	private String properties;

	public WkObject(int objNum) {
		this.objNum = objNum;
		
		objName = null;
		
		parentObjNum = -1;
		properties = "";
		verbs = "";
		
		treeNode = new CodeNode("#" + objNum);
		treeNode.setUserObject(this);
		
		propsNode = new CodeNode("Props");
		treeNode.add(propsNode);
		
		verbsNode = new CodeNode("Verbs");
		treeNode.add(verbsNode);
		
		kidsNode = new CodeNode("Kids");
		treeNode.add(kidsNode);
		
		retrieved = false;
	}
	
	public String tostring() {
		if (objName != null) {
			return objName + " (#" + objNum + ")";
		}
		
		return "#" + objNum;
	}
	
	public void addProperty(String name) {
		addLeafNode("." + name, propsNode);
	}
	
	public void addVerb(String name) {
		addLeafNode(":" + name, verbsNode);
	}
	
	private void addLeafNode(String name, CodeNode parent) {
		// If a node already exists, do nothing...
		for (int i=0; i<parent.getChildCount(); i++) {
			if (parent.getUserObject().toString().equals(name)) {
				// node already exists
				return;
			}
		}
		
		// ok to add it..
		CodeNode leaf = new CodeNode(name);
		leaf.setUserObject(name);
		parent.add(leaf);
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

	public CodeNode getPropertiesNode() {
		return propsNode;
	}

	public CodeNode getVerbsNode() {
		return verbsNode;
	}

	public CodeNode getKidsNode() {
		return kidsNode;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getVerbs() {
		return verbs;
	}

	public void setVerbs(String verbs) {
		this.verbs = verbs;
	}

	public int getParentObjNum() {
		return parentObjNum;
	}

	public void setParentObjNum(int parentObjNum) {
		this.parentObjNum = parentObjNum;
	}
}
