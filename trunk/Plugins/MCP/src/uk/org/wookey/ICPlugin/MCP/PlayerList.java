package uk.org.wookey.ICPlugin.MCP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.SpringUtilities;
import uk.org.wookey.IC.Utils.TimeUtils;

public class PlayerList extends JPanel {
    private static final long serialVersionUID = 1L;
    private Logger _logger = new Logger("MCP PlayerList");

	private ArrayList<Player> players;
	private JPanel connectedPlayers;
	private MCP mcp;

	public PlayerList(MCP mcp) {
		super();
		
		setLayout(new BorderLayout());
		setBorder(new LineBorder(Color.black));
		
		this.mcp = mcp;
		
		//setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		
		JLabel title = new JLabel("Connected Players", JLabel.CENTER);
		add(title, BorderLayout.NORTH);
		
		players = new ArrayList<Player>();
		connectedPlayers = new JPanel();
		
		connectedPlayers.setLayout(new SpringLayout());
		connectedPlayers.setBackground(Color.white);
		connectedPlayers.setBorder(BorderFactory.createEmptyBorder(2, 4, 0, 4));
		
		JScrollPane scroller = new JScrollPane();
        scroller.getViewport().add(connectedPlayers);
        scroller.setMinimumSize(new Dimension(50, scroller.getMaximumSize().height));
        scroller.setSize(scroller.getMinimumSize());
		add(scroller, BorderLayout.CENTER);}
	
	public void add(Player p) {
		players.add(p);
		Sorter.sort(players);
		
		buildPlayerList();
	}
	
	public void setData(ArrayList<Player> p) {
		players = p;
		Sorter.sort(players);
		
		buildPlayerList();
	}
	
	private void buildPlayerList() {
		boolean showDetail = true;
		
		connectedPlayers.removeAll();

		// If the wookey-core package is active, then only show minimal info in the player
		// list as we can click on a player and get all the dirt from the wookey-core package
		if (mcp.packageActive("dns-uk-org-wookey-core")) {
			showDetail = false;
		}
		
		for (Player p: players) {
			//JLabel pName = new JLabel(p.getName() + " (" + p.getId() + ")");
			String lab = p.getName();
			if (showDetail) {
				lab = lab + " (" + p.getId() + ")";
			}
			final JButton pName = new JButton(lab);
			
			//JLabel pLoc = new JLabel(p.getLocation());
			JLabel pIdle = new JLabel(TimeUtils.approxString(p.getIdle()));
			
			pName.setOpaque(true);
			pName.setContentAreaFilled(false);
			pName.setBorder(null);
			pName.setBorderPainted(false);
			pName.setFocusPainted(false);
			pName.setMargin(new Insets(0, 0, 0, 0));
			pName.setCursor(new Cursor(Cursor.HAND_CURSOR));
			pName.setHorizontalAlignment(SwingConstants.LEFT);
			pName.setBackground(Color.GREEN);
			pName.addMouseListener(new Mousie(p.getId()));

			//pLoc.setOpaque(false);
			pIdle.setOpaque(false);
			
			connectedPlayers.add(pName);
			//connectedPlayers.add(pLoc);
			connectedPlayers.add(pIdle);
		}
		
		SpringUtilities.makeCompactGrid(connectedPlayers, players.size(), 2, 0, 0, 2, 2);
		
		connectedPlayers.revalidate();
	}
	
	private class Mousie implements MouseListener {
		private String id;
		
		public Mousie(String id) {
			this.id = id;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			((JButton) e.getSource()).setBackground(Color.CYAN);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			((JButton) e.getSource()).setBackground(Color.WHITE);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			_logger.logInfo("Click! - '" + id + "'");
			
			// If the Wookey-core package is loaded, tell it about this object.
			if (mcp.packageActive("dns-uk-org-wookey-core")) {
				WookeyCore handler = (WookeyCore) mcp.findHandler("dns-uk-org-wookey-core");
					
				if (handler != null) {
					_logger.logInfo("Tell WookeyCore about object " + id);
					handler.loadObject(WkObjectDB.decodeObjectNumNoEx(id));
				}
				else {
					_logger.logWarn("wookey core package not active");
				}
			}
		}
	}
}
