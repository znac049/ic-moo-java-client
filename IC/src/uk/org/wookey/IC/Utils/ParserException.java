package uk.org.wookey.IC.Utils;

public class ParserException extends Exception {
	private static final long serialVersionUID = -3602407159679521799L;

	public ParserException(String msg) {
		super(msg);
	}
	
	public ParserException(Throwable cause) {
		super(cause);
	}
}
