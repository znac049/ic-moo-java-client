package uk.org.wookey.IC.Utils;

public class TimedEvent {
	private static Logger _logger = new Logger("TimedEvent");
	private long nextTriggerTime;
	private long repeatInterval;
	private int repeatCount;
	private boolean repeats;
	private boolean active;
	
	public TimedEvent() {
		nextTriggerTime = -1;
		repeatInterval = -1;
		repeatCount = 0;
		
		repeats = false;
		active = false;
	}
	
	public boolean isReady(long timeNow) {
		if (active) {
			if (timeNow > nextTriggerTime) {
				return true;
			}
		}
		
		return false;
	}
	
	public void setRepeat(long millis) {
		setRepeat(millis, -1);
	}
	
	public void setRepeat(long millis, int count) {
		repeatInterval = millis;
		repeatCount = count;
		repeats = true;
	}	
	
	public void triggerEvent() {
		nextTriggerTime = System.currentTimeMillis();
		active = true;
	}
	
	public void runEvent() {
		_logger.logInfo("Timer event runEvent() called");
	}
	
	public void resetForNext() {
		if (repeats) {
			if ((repeatCount == -1) || (repeatCount > 0)) {
				if (repeatCount > 0) {
					repeatCount--;
					
					if (repeatCount == 0) {
						active = false;
						return;
					}
				}
				
				// ok - it needs to run again - work out when...
				nextTriggerTime = System.currentTimeMillis() + repeatInterval;

				return;
			}
		}
		
		active = false;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void dump() {
		_logger.logInfo("NTT=" + nextTriggerTime +", RI=" + repeatInterval + ", RC=" + repeatCount +
						", repeats=" + repeats + ", active=" + active);
	}
}
