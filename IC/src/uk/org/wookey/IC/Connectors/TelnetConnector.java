package uk.org.wookey.IC.Connectors;

import java.io.IOException;

import uk.org.wookey.IC.Utils.Logger;

public class TelnetConnector extends Connector implements ConnectorInterface {
	private Logger _logger = new Logger("TelnetConnector");
	
	private final static int DEFAULT_PORT = 8888;
	
	private String host;
	private int port;
	
	@Override
	public void connect(String remote) throws IOException {
		host = remote;
		port = DEFAULT_PORT;
		
		int colon = host.indexOf(':');
		if (colon >= 1) {
			String portStr = host.substring(colon);
			host = remote.substring(0,  colon-1);
			port = Integer.parseInt(portStr);
		}
		
		_logger.logInfo("Connecting to '" + host +"' on port " + port);
	}

}
