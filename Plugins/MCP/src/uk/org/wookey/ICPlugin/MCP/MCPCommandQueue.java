package uk.org.wookey.ICPlugin.MCP;

import java.util.LinkedList;

import uk.org.wookey.IC.Utils.Logger;

public class MCPCommandQueue {
	private Logger _logger = new Logger("MCPCommandQueue");
	
	private volatile LinkedList<MCPCommand> highPriorityQueue;
	private volatile LinkedList<MCPCommand> normalPriorityQueue;
	private volatile LinkedList<MCPCommand> lowPriorityQueue;
	
	public static final int lowPriority = 0;
	public static final int normalPriority = 1;
	public static final int highPriority = 2;
	
	public MCPCommandQueue() {
		highPriorityQueue = new LinkedList<MCPCommand>();
		normalPriorityQueue = new LinkedList<MCPCommand>();
		lowPriorityQueue = new LinkedList<MCPCommand>();
	}
	
	public void queueCommand(MCPCommand cmd, int priority) {
		switch (priority) {
		case lowPriority:
			lowPriorityQueue.addLast(cmd);
			break;
			
		case highPriority:
			highPriorityQueue.addLast(cmd);
			break;
			
		default:
			_logger.logError("Unknown queue priority - assuming normalPriority");
		case normalPriority:
			normalPriorityQueue.addLast(cmd);
			break;
		}
	}

	public void queueCommand(MCPCommand cmd) {
		queueCommand(cmd, normalPriority);
	}
	
	public int getQueueLength(int priority) {
		switch (priority) {
		case lowPriority:
			return lowPriorityQueue.size();
			
		case highPriority:
			return highPriorityQueue.size();
			
		default:
			_logger.logError("Unknown queue priority - assuming normalPriority");
		case normalPriority:
			return normalPriorityQueue.size();
		}
	}

	public MCPCommand getQueuedCommand(int priority) {
		switch (priority) {
		case lowPriority:
			return lowPriorityQueue.removeFirst();
			
		case highPriority:
			return highPriorityQueue.removeFirst();
			
		default:
			_logger.logError("Unknown queue priority - assuming normalPriority");
		case normalPriority:
			return normalPriorityQueue.removeFirst();
		}
	}
}
