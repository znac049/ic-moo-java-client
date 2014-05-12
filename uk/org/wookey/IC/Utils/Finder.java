package uk.org.wookey.IC.Utils;

import java.util.ArrayList;

public class Finder {
	static ArrayList<NamedObject> things = new ArrayList<NamedObject>();

	public final static void add(String name, Object o) {
		things.add(new NamedObject(name, o));
	}	
	
	public final static Object find(String name) {
		for(int i=0; i<things.size(); i++) {
			NamedObject thing = things.get(i);
			
			if (thing.oName.equalsIgnoreCase(name)) {
				return thing.object;
			}
		}
		
		return null;
	}
}
