package uk.org.wookey.IC.GUI;

import java.util.prefs.*;
import javax.swing.*;

import uk.org.wookey.IC.Utils.Logger;

public class WorldList extends JList {
	private static final long serialVersionUID = -8852387005116477784L;
	private final Logger _logger = new Logger("WorldList");

	public WorldList() {
		super();

		populateList();
	}

	private void populateList() {
		String worldNames[] = {"<New World>"};
		
		try {
			worldNames = Preferences.userRoot().node("uk/org/wookey/IC/worlds").childrenNames();
			String fullWorldNames[] = new String[worldNames.length+1];
			fullWorldNames[0] = "<New World>";
			for (int i=0; i<worldNames.length; i++) {
				fullWorldNames[i+1] = worldNames[i];
			}
			
			setListData(fullWorldNames);
		} catch (BackingStoreException e) {
			_logger.logMsg("Preferences problem.");
			_logger.printBacktrace(e);
		}
	}
	
	public void resetList() {
		populateList();
	}
}
