package uk.org.wookey.IC.Utils;

import java.util.ArrayList;

public class Sorter {
	public static final void sort(ArrayList<Player> players) {
		for (int i=0; i<players.size(); i++) {
			for (int j=i+1; j<players.size(); j++) {
				Player a = players.get(i);
				Player b = players.get(j);
				
				if (a.getName().compareToIgnoreCase(b.getName()) > 0) {
					players.set(i, b);
					players.set(j, a);
				}
			}
		}
	}
}
