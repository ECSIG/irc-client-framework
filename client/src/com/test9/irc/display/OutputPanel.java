package com.test9.irc.display;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class OutputPanel extends JPanel{

	private static final long serialVersionUID = 3331343604631033360L;
	private static int width, height;
	private String channel;
	private JScrollPane scrollPane;
	private JTextArea textArea; 


	public OutputPanel(String channel, int width, int height)
	{
		new BorderLayout();
		this.width = width;
		this.height = height;
		setBounds(0, 0, this.width, this.height);
		System.out.println(new Dimension(this.getWidth(), this.getHeight()));
		textArea = new JTextArea();
		textArea.setEditable(true);
		textArea.setLineWrap(true);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		add(scrollPane);
		textArea.setText("working?");

	}
	
	
	/**
	 * This is used to append a new string to a channels text area.
	 * @param message
	 */
	public void newMessage(String message)
	{
		
	}
	
	public static void setNewBounds(int height, int width)
	{
		OutputPanel.width = width;
		OutputPanel.height = height;
		//setBounds(0,0,width,height);
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
	 * @param width the width to set
	 */
	public static void setWidth(int width) {
		OutputPanel.width = width;
	}


	/**
	 * @param height the height to set
	 */
	public static void setHeight(int height) {
		OutputPanel.height = height;
	}
}
