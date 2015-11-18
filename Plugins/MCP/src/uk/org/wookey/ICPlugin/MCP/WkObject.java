package uk.org.wookey.ICPlugin.MCP;

import java.util.ArrayList;

public class WkObject {
	//private Logger _logger = new Logger("WkObject");
	private int objNum;
	private String objName;
	private CodeNode treeNode;
	private int parentObjNum;
	
	private ArrayList<WkVerb> verbList;
	private ArrayList<WkProperty> propertyList;

	public WkObject(int objNum) {
		this.objNum = objNum;
		
		objName = null;
		
		parentObjNum = -1;
		
		treeNode = new CodeNode("#" + objNum);
		treeNode.setUserObject(this);
		
		//propsNode = new CodeNode("Props");
		//treeNode.add(propsNode);
		
		//verbsNode = new CodeNode("Verbs");
		//treeNode.add(verbsNode);
		
		verbList = new ArrayList<WkVerb>();
		propertyList = new ArrayList<WkProperty>();
		
		//kidsNode = new CodeNode("Kids");
		//treeNode.add(kidsNode);
	}
	
	public String tostring() {
		if (objName != null) {
			return objName + " (#" + objNum + ")";
		}
		
		return "#" + objNum;
	}
	
	public void addProperty(String name) {
		//addLeafNode("." + name, propsNode);

		for (WkProperty prop: propertyList) {
			if (prop.getName().equals(name)) {
				// Already got it
				return;
			}
		}
		
		WkProperty prop = new WkProperty(name);
		propertyList.add(prop);
	}
	
	public void addVerb(String name) {
		//addLeafNode(":" + name, verbsNode);
		
		for (WkVerb vb: verbList) {
			if (vb.getName().equals(name)) {
				// Already got it
				return;
			}
		}
		
		WkVerb vb = new WkVerb(name);
		verbList.add(vb);
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
	
	public ArrayList<WkProperty> getPropertyList() {
		return propertyList;
	}
	
	public ArrayList<WkVerb> getVerbList() {
		return verbList;
	}
}
