package uk.org.wookey.IC.Utils;

import uk.org.wookey.IC.GUI.WorldTab;


public interface IOPluginInterface {
	public enum Status {
		IGNORED,
		CONSUMED
	};
	
	public boolean attach(ServerPort server, WorldTab tab);
	
	public WorldTab getWorldTab();
	
	public Status remoteCharIn(Char c);
	public Status remoteLineIn(Line line);
	
	public Status transformRemoteInputLine();
}
