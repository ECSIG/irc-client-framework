package com.test9.irc.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class UserListPanel extends JPanel implements ListSelectionListener{

	private static final long serialVersionUID = 3331343604631033360L;
	private static Rectangle boundsRect;
	private static Font font = new Font("Lucida Grande", Font.PLAIN, 12);
	private SortedListModel listModel = new SortedListModel();
	private String channel, server;
	private JScrollPane scrollPane;
	private JList<String> jList;


	@SuppressWarnings("unchecked")
	UserListPanel(String server, String channel, int width, int height)
	{
		this.server = server;
		this.channel = channel;
		setLayout(new BorderLayout());
		boundsRect = new Rectangle(0,0,width,height);
		setBounds(boundsRect);
		setBackground(Color.BLACK);
		jList = new JList<String>();
		jList.setModel(listModel);
		jList.setFont(font);
		scrollPane = new JScrollPane(jList);
		add(scrollPane, BorderLayout.CENTER);

	}

	/**
	 * This is used to append a new string to a channels text area.
	 * @param message
	 */
	void newUser(String user)
	{
		listModel.add(user);
	}


	void deleteUser(String user)
	{
		listModel.removeElement(user);
	}

	void nickChange(String oldNick, String newNick)
	{
		listModel.removeElement(oldNick);
		newUser(newNick);
	}

	static void setNewBounds(int width, int height)
	{
		boundsRect.setBounds(0, 0, width, height);
	}


	public void valueChanged(ListSelectionEvent e) {

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
	}
}
