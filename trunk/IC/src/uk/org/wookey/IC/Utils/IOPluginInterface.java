package uk.org.wookey.IC.Utils;


public interface IOPluginInterface {
	public enum Status {
		IGNORED,
		CONSUMED
	};
	
	public boolean attach(ServerPort server);
	
	public Status remoteCharIn(Char c);
	public Status remoteLineIn(Line line);
	
	public Status transformRemoteInputLine();
}
