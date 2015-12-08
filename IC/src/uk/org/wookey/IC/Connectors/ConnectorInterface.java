package uk.org.wookey.IC.Connectors;

import java.io.IOException;

public interface ConnectorInterface {
	public void connect(String remote) throws IOException;
	public String getRemoteName();
	public boolean isConnected();
	public String readLine();
	public void writeLine(String s) throws IOException;
}