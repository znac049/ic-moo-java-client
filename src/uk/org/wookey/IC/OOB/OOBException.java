package uk.org.wookey.IC.OOB;

public class OOBException extends Exception {
	private static final long serialVersionUID = -426601644766789411L;

	public OOBException(String msg) {
		super(msg);
	}
	
	public OOBException(Throwable cause) {
		super(cause);
	}
}
