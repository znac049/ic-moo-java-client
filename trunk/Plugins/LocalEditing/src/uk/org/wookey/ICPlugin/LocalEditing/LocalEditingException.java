package uk.org.wookey.ICPlugin.LocalEditing;

public class LocalEditingException extends Exception {
	private static final long serialVersionUID = -426601644766789411L;

	public LocalEditingException(String msg) {
		super(msg);
	}
	
	public LocalEditingException(Throwable cause) {
		super(cause);
	}
}
