package dns.uk.org.wookey.core;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.ICPlugin.MCP.MCP;
import uk.org.wookey.ICPlugin.MCP.MCPCommand;
import uk.org.wookey.ICPlugin.MCP.MCPHandler;

public class VerbLabel extends JLabel implements MouseListener {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static Logger _logger = new Logger("VerbLabel");
	
	private Verb verb;

	public VerbLabel(Verb vb) {
		super();
		
		verb = vb;
		
		setText("<html>" + 
				verb.getName().replaceAll(" ", "<br/>") + 
				"</html>");
		
		setOpaque(true);
		setBorder(null);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setHorizontalAlignment(SwingConstants.LEFT);
		//setBackground(Color.YELLOW);
		
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MooObject ob = verb.getOwnerObject();
		MCPHandler handler = ob.getMCPHandler();
		MCP mcp = handler.getMCP();
				
		//_logger.logInfo("Click on '#" + ob.getObjNum() + ":" + verb.getName() + "'");
		
		MCPCommand cmd = new MCPCommand();
		cmd.setAuthKey(mcp.authKey);
		cmd.setName(handler.getName(), "editverb");
		cmd.addParam("objnum", "" + ob.getObjNum());
		cmd.addParam("verbname", verb.getName());
		
		mcp.queueOutgoingCommand(cmd);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
