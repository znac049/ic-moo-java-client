package uk.org.wookey.IC.Utils;

public class StringBuffer {
	private static final int MAXBUFF = 4096;
	
	private Logger _logger = new Logger("StringBuffer");
	
	private int[] buffer;
	private volatile int insert;
	private volatile int remove;
	
	public StringBuffer() {
		insert = remove = 0;
		buffer = new int[MAXBUFF];
	}
	
	public int charsAvailable() {
		return (insert - remove) % MAXBUFF;
	}
	
	public boolean isLineAvailable() {
		return false;
	}
	
	public String getLine() {
		return "";
	}
	
	public String peek() {
		return "";
	}
	
	public void add(int c) {
		buffer[insert++] = c;
	}
}
