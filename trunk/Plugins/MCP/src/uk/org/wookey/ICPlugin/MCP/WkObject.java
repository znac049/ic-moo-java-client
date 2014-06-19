package uk.org.wookey.ICPlugin.MCP;

import javax.swing.tree.TreeNode;

import uk.org.wookey.IC.Utils.Logger;

public class WkObject {
	private Logger _logger = new Logger("WkObject");
	private int objNum;
	private String objName;
	private boolean retrieved;
	private CodeNode treeNode;
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
		
		retrieved = false;
	}
	
	public String tostring() {
		if (objName != null) {
			return objName + "(#" + objNum + ")";
		}
		
		return "#" + objNum;
	}
	
	public void addProperty(String name) {
		addLeafNode("." + name);
	}
	
	public void addVerb(String name) {
		addLeafNode(":" + name);
	}
	
	private void addLeafNode(String name) {
		// If a node already exists, do nothing...
		for (int i=0; i<treeNode.getChildCount(); i++) {
			if (treeNode.getUserObject().toString().equals(name)) {
				// node already exists
				return;
			}
		}
		
		// ok to add it..
		CodeNode leaf = new CodeNode(name);
		leaf.setUserObject(name);
		treeNode.add(leaf);
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
