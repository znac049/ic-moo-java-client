package uk.org.wookey.IC.Interfaces;

public interface OOBHandlerInterface {
	public static final int OOBHandled = 1;
	public static final int OOBHandledFinal = 2;
	public static final int OOBNotInterested = 3;
	
	public boolean isOutOfBand(String line);
	public int handle(String line);
}
