package com.test9.irc.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

public class ChatWindow extends JFrame{
	
	private static final long serialVersionUID = -6373704295052845871L;
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	private static final int SPLITPANEWIDTH = 3;
	private Dimension defaultWindowSize = new Dimension(
			KIT.getScreenSize().width / 2, KIT.getScreenSize().height / 2);
	private static JTextArea chatOutput = new JTextArea();
	private static JTextArea consoleOutput = new JTextArea();
	private static JList<String> userList = new JList<String>();
	private static JTree channelList = new JTree();
	private static JTextField inputField = new JTextField();
	private static JPanel outputInputPanel = new JPanel(new BorderLayout());
	
	
	public ChatWindow()
	{
		
		
		setPreferredSize(defaultWindowSize);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		outputInputPanel.add(chatOutput, BorderLayout.NORTH);
		outputInputPanel.add(inputField, BorderLayout.SOUTH);
		
		consoleOutput.setText("Console OUTPUT JTEXTAREA");
		chatOutput.setText("Chat OUTPUT JTEXTAREA");
		userList.setBackground(Color.BLACK);
		//consoleOutput.setPreferredSize(new Dimension(KIT.getScreenSize().width/2,0));
		
		
		JSplitPane listsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				userList, channelList);
		listsSplitPane.setDividerSize(SPLITPANEWIDTH);
		
		
		JSplitPane chat_ConsoleSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
				outputInputPanel, consoleOutput);
		chat_ConsoleSplitPane.setDividerSize(SPLITPANEWIDTH);
		chat_ConsoleSplitPane.setOneTouchExpandable(true);
		
		JSplitPane lists_OutputSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				chat_ConsoleSplitPane, listsSplitPane);
		lists_OutputSplitPane.setDividerSize(SPLITPANEWIDTH);
		
		
		
		add(lists_OutputSplitPane, BorderLayout.CENTER);
		
		setVisible(true);
		pack();
	}

}
