package uk.org.wookey.IC.Utils;

import uk.org.wookey.IC.GUI.WorldTab;


public interface IOPluginInterface extends CorePluginInterface {
	public enum Status {
		IGNORED,
		CONSUMED
	};
	
	public boolean attach(ServerConnection server, WorldTab tab);
	
	public WorldTab getWorldTab();
	
	public Status remoteLineIn(Line line);
	public String remoteLinePeek(String lineSoFar);
}
