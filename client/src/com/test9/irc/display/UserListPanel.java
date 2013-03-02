package com.test9.irc.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class UserListPanel extends JPanel implements ListSelectionListener, FocusListener, KeyListener{

	private static final long serialVersionUID = 3331343604631033360L;
	/**
	 * Holds the bounds for the panel.
	 */
	private static Rectangle boundsRect;

	/**
	 * Holds the font for the panel.
	 */
	private static Font font = new Font("Lucida Grande", Font.BOLD, 10);

	/**
	 * Holds the model of the list that will be used.
	 */
	private SortedListModel<String> listModel = new SortedListModel<String>();

	/**
	 * Holds the name of the channel that the list is for.
	 */
	private String channel;

	/**
	 * Holds the name of the server the list is on.
	 */
	private String server;

	/**
	 * The scroll pane for hte list.
	 */
	private JScrollPane scrollPane;

	/**
	 * The list that contains all the information.
	 */
	private JList jList = new JList();

	private ChatWindow owner;
	
	private String selected;
	
	/**
	 * Constructs a new UserListPanel for a channel.
	 * @param server Name of the server the list is on.
	 * @param channel Name of the channel the list is on.
	 * @param width Width.
	 * @param height Height.
	 */
	UserListPanel(String server, String channel, int width, int height, ChatWindow owner)
	{
		this.owner = owner;
		jList.setBackground(Color.BLACK);
		jList.setForeground(Color.WHITE);
		this.server = server;
		this.channel = channel;
		setLayout(new BorderLayout());
		boundsRect = new Rectangle(0,0,width,height);
		setBounds(boundsRect);
		setBackground(Color.BLACK);
		jList.setModel(listModel);
		jList.setFont(font);
		scrollPane = new JScrollPane(jList);
		scrollPane.setBackground(Color.BLACK);
		scrollPane.setBorder(null);
		scrollPane.addKeyListener(this);
		add(scrollPane, BorderLayout.CENTER);
		jList.addFocusListener(this);
		addKeyListener(this);
		initPopupMenu();
		if(ChatWindow.isOSX()) {
			scrollPane.getVerticalScrollBar().setPreferredSize(ChatWindow.getScrollbardim());
		}
		
		jList.addMouseListener( new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				if ( SwingUtilities.isRightMouseButton(e) )
				{
					jList.setSelectedIndex(getRow(e.getPoint()));
					selected = jList.getSelectedValue().toString();
					showPopupMenu(e);
				} 
			}
		});


	}
	private int getRow(Point point)
	{
		return jList.locationToIndex(point);
	}
	
	private void showPopupMenu(MouseEvent e) {
		JPopupMenu popMenu = new JPopupMenu();
		JMenuItem pm = new JMenuItem("PM");
		pm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Listener listener = owner.getListener();
				String activeServer = owner.getActiveServer();
				listener.createPrivateChannel(activeServer, selected, selected);
				//listener.onJoinChannel(activeServer, selected);
				
			}
			
		});
		JMenuItem about = new JMenuItem("About "+jList.getSelectedValue().toString());
		popMenu.add(pm);
		popMenu.add(about);
		popMenu.show(e.getComponent(), e.getX(), e.getY());
		
	}
	
	private void initPopupMenu() {
		
	}

	/**
	 * Adds a new user.
	 * @param user User's nick.
	 */
	void newUser(String user)
	{
		listModel.add(user);
		invalidate();
	}

	/**
	 * Removes a parted user.
	 * @param user User's nick.
	 */
	void userPart(String user)
	{
		listModel.removeElement(user);
		invalidate();
	}

	void userAway(String nick) {

	}

	/**
	 * Changes a nick if someone changes their nick on the server.
	 * @param oldNick The original nick.
	 * @param newNick The user's new nick.
	 */
	boolean nickChange(String oldNick, String newNick)
	{
		if(listModel.removeElement(oldNick)) {
			newUser(newNick);
			return true;
		}
		invalidate();
		return false;
	}

	String getTabComplete(String prefix, int tabs) {
		int ltabs = 0;

		for(int i = 0; i < listModel.getSize(); i++){
			String n = (String) listModel.getElementAt(i);
			if(!Character.isLetter(n.charAt(0))) {
				n = n.substring(1, n.length());
			}
			if (n.toLowerCase().startsWith(prefix.toLowerCase()))
			{
				ltabs++;
				if(ltabs == tabs) {
					return n;
				}
			}
		}
		System.out.println("returning null");
		return null;
	}

	/**
	 * Used for resizing the UserListPanel.
	 * @param width Width.
	 * @param height Height.
	 */
	static void setNewBounds(int width, int height)
	{
		boundsRect.setBounds(0, 0, width, height);
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		invalidate();
	}


	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}


	/**
	 * @param channel the channel to set
	 */
	void setChannel(String channel) {
		this.channel = channel;
		invalidate();
	}


	/**
	 * @return the scrollPane
	 */
	public JScrollPane getScrollPane() {
		return scrollPane;
	}


	/**
	 * @param scrollPane the scrollPane to set
	 */
	void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}


	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * @return the bounds
	 */
	public static Rectangle getBoundsRec() {
		return boundsRect;
	}


	/**
	 * @param bounds the bounds to set
	 */
	static void setBoundsRec(Rectangle bounds) {
		UserListPanel.boundsRect = bounds;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}


	/**
	 * @param server the server to set
	 */
	void setServer(String server) {
		this.server = server;
		invalidate();
	}

	/**
	 * @return the listModel
	 */
	public SortedListModel<String> getListModel() {
		return listModel;
	}

	/**
	 * @param listModel the listModel to set
	 */
	public void setListModel(SortedListModel<String> listModel) {
		this.listModel = listModel;
		invalidate();
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		jList.getSelectionModel().clearSelection();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {
		if(!ChatWindow.hasMetaKey)
		{
			if(e.getModifiers() == InputEvent.CTRL_MASK) {
				if(Character.isDigit(e.getKeyChar())) {
					owner.connectionTreeTabSelection(Character.getNumericValue(e.getKeyChar())-1);
				} 
			} else if(e.getModifiers() == InputEvent.ALT_MASK) {
				if(Character.isDigit(e.getKeyChar())) {
					owner.connectionTreeTabSelection(Character.getNumericValue(e.getKeyChar())-1);
				} 
			} else {
				owner.giveInputFieldFocus(e.getKeyChar());
			}
		} else {
			if(e.getModifiers()== InputEvent.META_MASK) {
				if(Character.isDigit(e.getKeyChar())) {
					owner.connectionTreeTabSelection(Character.getNumericValue(e.getKeyChar())-1);
				} // end digit?
			} else {
				owner.giveInputFieldFocus(e.getKeyChar());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
