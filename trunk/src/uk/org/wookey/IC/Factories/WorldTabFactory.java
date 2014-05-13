package uk.org.wookey.IC.Factories;

import java.io.IOException;

import uk.org.wookey.IC.GUI.WorldTabs;
import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.AssociativeArray;

public class WorldTabFactory {
	private static WorldTabs worldTabs = null;
	private static AssociativeArray tabs = new AssociativeArray();
	
	public static WorldTabs getWorldTabs() {
		if (worldTabs == null) {
			worldTabs = new WorldTabs();
		}
		
		return worldTabs;
	}
	
	public static WorldTab getWorldTab(String name) {
		WorldTab res = (WorldTab) tabs.get(name);
		
		if (res == null) {
			try {
				res = new WorldTab(name);
				tabs.add(name, res);
			} catch (IOException e) {
				// DO nothing for now
			}
		}
		
		if (res != null) {
			// Bring this tab to the front
			WorldTabFactory.getWorldTabs().getTabPane().setSelectedComponent(res);
		}
		
		return res;
	}
}
