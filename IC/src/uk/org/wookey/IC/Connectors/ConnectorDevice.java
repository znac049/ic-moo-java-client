package uk.org.wookey.IC.Connectors;

import java.io.IOException;

public class ConnectorDevice implements ConnectorDeviceInterface {
	@Override
	public void connect(String remote) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public char getCh() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void putCh(char c) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canRead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canWrite() {
		// TODO Auto-generated method stub
		return false;
	}
}
