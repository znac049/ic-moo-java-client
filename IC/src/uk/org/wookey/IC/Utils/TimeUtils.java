package uk.org.wookey.IC.Utils;

public class TimeUtils {
	public final static String approxString(long seconds) {
		String res = "";
		
		if (seconds >= 86400) {
			long days = seconds / 86400;
			return res + days + "d";
		}
		
		if (seconds >= 3600) {
			long hours = seconds / 3600;
			return res + hours + "h";
		}
		
		if (seconds >= 60) {
			long mins = seconds / 60;
			return res + mins + "m";
		}
		
		res = res + seconds + "s";
		
		return res;
	}
	
	public final static String approxString(String seconds) {
		return approxString(Long.parseLong(seconds));
	}
}
