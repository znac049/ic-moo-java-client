package uk.org.wookey.IC.Utils;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.*;

public class Logger {
	private static JTextPane _log = null;
	private String _logName;
	private int _logLevel = 1;
	private SimpleAttributeSet _labAttribs;
	private SimpleAttributeSet _msgAttribs;

	private SimpleAttributeSet _okAttribs;
	private SimpleAttributeSet _warnAttribs;
	private SimpleAttributeSet _errAttribs;
	
	private ArrayList<String[]> _msgBuffer;
	
	public Logger(JTextPane log)
	{
		_log = log;
		initialise("");
	}
	
	public Logger(String tag) {
		initialise(tag);
	}
	
	public Logger(String tag, int level) {
		initialise(tag);
		_logLevel = level;
	}
	
	private void initialise(String tag) {
		if (tag.equals("")) {
			_logName = "";
		}
		else {
			_logName = "[" + tag + "]:";
		}
		
		_msgBuffer = new ArrayList<String[]>();
		
		_labAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(_labAttribs, Color.blue);

		_msgAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(_msgAttribs, Color.white);
		StyleConstants.setBold(_msgAttribs, false);

		_okAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(_okAttribs, Color.green);

		_warnAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(_warnAttribs, Color.orange);

		_errAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(_errAttribs, Color.red);
	}
	
	public void logMsg(String msg, SimpleAttributeSet labAttribs, SimpleAttributeSet msgAttribs) {
		if (_logLevel == 0) {
			return;
		}
		
		if (_log != null) {
			if (_msgBuffer.size() > 0) {
				for (int i=0; i<_msgBuffer.size(); i++) {
					String bits[] = _msgBuffer.get(i);
					append(bits[0], labAttribs);
					append(bits[1], msgAttribs);
				}
				_msgBuffer.clear();
			}
			append(_logName, labAttribs);
			append(' ' + msg + '\n', msgAttribs);
		}
		else {
			// We don't have a text pane yet - buffer it for later display.
			String item[] = new String[2];
			item[0] = _logName;
			item[1] = msg;
			_msgBuffer.add(item);
			
			System.out.println(_logName + ' ' +msg);
		}
	}
	
	private void logMsg(String msg, SimpleAttributeSet attribs) {
		logMsg(msg, _labAttribs, attribs);
	}
	
	public void printBacktrace(String msg, Exception e) {
		StackTraceElement[] trace = e.getStackTrace();

		logMsg(msg);
		for (int i=0; i<trace.length; i++) {
			append(trace[i].toString() + '\n', _errAttribs);
		}
	}
	
	public void printBacktrace(Exception e) {
		printBacktrace("Caught an exception:", e);
	}
	
	protected void append(String msg, SimpleAttributeSet attributes) {
		Document doc = _log.getDocument();
		
		try {
			doc.insertString(doc.getLength(), msg, attributes);
			_log.setCaretPosition(doc.getLength());		
		} catch (BadLocationException e) {
			e.printStackTrace();
		}		
	}
	
	public void logMsg(String msg) {
		logMsg(msg, _labAttribs, _msgAttribs);
	}
	
	public void logInfo(String msg) {
		logMsg(msg);
	}
	
	public void logSuccess(String msg) {
		logMsg(msg, _okAttribs);
	}
	
	public void logWarn(String msg) {
		logMsg(msg, _warnAttribs);
	}
	
	public void logError(String msg) {
		logMsg(msg, _errAttribs);
	}
}
