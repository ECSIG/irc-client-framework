package com.test9.irc.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import com.test9.irc.engine.NewEngineTester;
import com.test9.irc.engine.User;

public class TerminalPanel extends JPanel {


	private static final long serialVersionUID = 345712746534728702L;
	private JTextPane textPane = new JTextPane();
	private JScrollPane scrollPane = new JScrollPane(textPane);
	private StyledDocument doc = textPane.getStyledDocument();


	TerminalPanel() {
		setMinimumSize(new Dimension(0,0));
		setLayout(new BorderLayout());
		textPane.setBackground(Color.BLACK);
		add(scrollPane, BorderLayout.CENTER);
		setBackground(Color.BLACK);
		textPane.setMargin(new Insets(3,3,3,3));
		textPane.setForeground(Color.WHITE);
		textPane.setFont(TextFormat.font);
		textPane.setEditable(false);
		try {
			doc.insertString(0, "!!!!!!!!!!VERSION: "+ NewEngineTester.VERSION+" !!!!!!!!!!!!!!!!!", null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		initScrollBar();
	}

	/**
	 * 
	 */
	private void initScrollBar() {
//		scrollBar.addAdjustmentListener(new AdjustmentListener() {
//
//			@Override
//			public void adjustmentValueChanged(AdjustmentEvent e) {
//				if (model.getValue() == model.getMaximum() - model.getExtent()) {
//					caret.setDot(textPane.getText().length());
//					caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//				} else {
//					caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
//				}
//			}
//		});
//		scrollPane.getVerticalScrollBar().setPreferredSize(ChatWindow.getScrollBarDim());
		scrollPane.setBackground(Color.BLACK);
		scrollPane.setBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	}

	/**
	 * 
	 * @param user User the message is from if a private message will be appended
	 * @param nick Nick of the user if a private message
	 * @param origin Where the message originated from (either a server or channel)
	 * @param message The content of the message
	 * @param sas The attribute set needed for the content of the message
	 */
	void newMessage(User user, String nick, String origin, String message, SimpleAttributeSet sas)
	{
		String timeStamp = new SimpleDateFormat("\r\nHH:mm").format(Calendar.getInstance().getTime());

		try {
			doc.insertString(doc.getLength(), timeStamp, TextFormat.timeAttrSet);
			doc.insertString(doc.getLength(), " <"+origin+">", TextFormat.originAttrSet);
			if(user != null)
				doc.insertString(doc.getLength(),"["+nick+"] ", user.getUserSimpleAttributeSet());
			doc.insertString(doc.getLength(), " "+message, sas);

		} catch (BadLocationException e) {
			System.err.println("Whoops, error inserting a string in the terminal.");
		}
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}
}
