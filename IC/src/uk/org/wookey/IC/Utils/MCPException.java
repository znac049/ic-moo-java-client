package uk.org.wookey.IC.MCP;

public class MCPException extends Exception {
	private static final long serialVersionUID = -3602407159679521799L;

	public MCPException(String msg) {
		super(msg);
	}
	
	public MCPException(Throwable cause) {
		super(cause);
	}
}
