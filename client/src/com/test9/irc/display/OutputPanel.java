package com.test9.irc.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OutputPanel extends JPanel{

	private static final long serialVersionUID = 3331343604631033360L;
	private static Rectangle boundsRect;
	private String channel, server;
	private JScrollPane scrollPane;
	private JTextArea textArea; 
	@SuppressWarnings("unused")
	private static Font font = new Font("Lucida Grande", Font.PLAIN, 12);


	public OutputPanel(String server, String channel, int width, int height)
	{
		this.server = server;
		this.channel = channel;
		setLayout(new BorderLayout());
		boundsRect = new Rectangle(0,0,width,height);
		setBounds(boundsRect);
		setBackground(Color.BLACK);
		textArea = new JTextArea();
		textArea.setMargin(new Insets(5,5,5,5));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		scrollPane = new JScrollPane(textArea);
		add(scrollPane, BorderLayout.CENTER);

	}
	
	
	/**
	 * This is used to append a new string to a channels text area.
	 * @param message
	 */
	public void newMessage(String message)
	{
		textArea.append(message+"\r\n");
		textArea.setCaretPosition(textArea.getText().length());
	}
	
	public static void setNewBounds(int width, int height)
	{
		boundsRect.setBounds(0, 0, width, height);
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
	public void setChannel(String channel) {
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
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}


	/**
	 * @return the textArea
	 */
	public JTextArea getTextArea() {
		return textArea;
	}


	/**
	 * @param textArea the textArea to set
	 */
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
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
	public static void setBoundsRec(Rectangle bounds) {
		OutputPanel.boundsRect = bounds;
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
	public void setServer(String server) {
		this.server = server;
	}
}
