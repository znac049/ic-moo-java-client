package uk.org.wookey.ICPlugin.MCP;

public class MCPException extends Exception {
	private static final long serialVersionUID = 1L;

	public MCPException(String msg) {
		super(msg);
	}
	
	public MCPException(Throwable cause) {
		super(cause);
	}
}
