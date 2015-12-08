package uk.org.wookey.IC.Connectors;

import java.io.IOException;

public interface ConnectorDeviceInterface {
	public void connect(String remote) throws IOException;
	public char getCh() throws IOException;
	public void putCh(char c) throws IOException;
	public boolean canRead();
	public boolean canWrite();
}
