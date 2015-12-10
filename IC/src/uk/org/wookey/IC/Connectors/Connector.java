package uk.org.wookey.IC.Connectors;

import java.io.IOException;

public class Connector implements ConnectorInterface {
	protected String remote;
	
	public Connector() {
	}

	@Override
	public void connect(String rem) throws IOException {
		remote = rem;
	}

	@Override
	public String getRemoteName() {
		return remote;
	}

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public String readLine() {
		return null;
	}

	@Override
	public void writeLine(String s) throws IOException {
	}

	@Override
	public void disconnect() {
	}
}
