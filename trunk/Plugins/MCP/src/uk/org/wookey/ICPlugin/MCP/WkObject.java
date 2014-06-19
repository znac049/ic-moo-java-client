package uk.org.wookey.ICPlugin.MCP;

import javax.swing.tree.DefaultMutableTreeNode;

import uk.org.wookey.IC.Utils.Logger;

public class WkObject {
	private Logger _logger = new Logger("WkObject");
	private int objNum;
	private boolean retrieved;
	private DefaultMutableTreeNode treeNode;
	private int parentObjNum;
	private String verbs;
	private String properties;

	public WkObject(int objNum) {
		this.objNum = objNum;
		
		parentObjNum = -1;
		properties = "";
		verbs = "";
		
		treeNode = new DefaultMutableTreeNode("#" + objNum);
		
		retrieved = false;
	}

	public int getObjNum() {
		return objNum;
	}

	public void setObjNum(int objNum) {
		this.objNum = objNum;
	}

	public DefaultMutableTreeNode getTreeNode() {
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
