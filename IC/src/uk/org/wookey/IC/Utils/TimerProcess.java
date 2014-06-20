package uk.org.wookey.IC.Utils;

import java.util.ArrayList;

import uk.org.wookey.IC.GUI.MainStatusBar;

public class TimerProcess  implements Runnable {
	private static Logger _logger = new Logger("TimerProcess");
	private volatile ArrayList<TimedEvent> eventQueue = new ArrayList<TimedEvent>();
	
	private static TimerProcess _tp = new TimerProcess();
	
	@Override
	public void run() {
		_logger.logInfo("Starting up");
		try {
			while (true) {
				long now = System.currentTimeMillis();
				
				for (int i=0; i<eventQueue.size(); i++) {
					TimedEvent ev = eventQueue.get(i);
					
					if (ev.isActive()) {
						if (ev.isReady(now)) {
							_logger.logInfo("Running timed event");
						
							ev.runEvent();
							
							_logger.logInfo("Resetting TimedEvent");
							ev.resetForNext();
							ev.dump();
						}
					}
					else {
						_logger.logInfo("Removing dead timed event from the queue");
						eventQueue.remove(i);
					}
				}
				
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			_logger.logError("Thread caught an interrupt", e);
		}
		
		_logger.logInfo("Shutting down");
	}
	
	public static void queueTimerEvent(TimedEvent event) {
		_tp.eventQueue.add(event);
		event.triggerEvent();
		
		MainStatusBar.getMainStatusBar().setTimerMessage(_tp.eventQueue.size());
	}
	
	public static TimerProcess getTimerProcess() {
		return _tp;
	}
}
