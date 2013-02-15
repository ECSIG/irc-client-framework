package com.test9.irc.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoundedRangeModel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.test9.irc.engine.User;

public class TerminalPanel extends JPanel {


	private static final long serialVersionUID = 345712746534728702L;
	private JTextPane textPane = new JTextPane();
	private JScrollPane scrollPane = new JScrollPane(textPane);
	private Font font = new Font("Lucida Grande", Font.BOLD, 12);
	private StyledDocument doc = textPane.getStyledDocument();


	private JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
	private BoundedRangeModel model = scrollBar.getModel();
	private DefaultCaret caret = (DefaultCaret) textPane.getCaret();

	private SimpleAttributeSet privMsg = new SimpleAttributeSet();
	private SimpleAttributeSet originAttrSet = new SimpleAttributeSet();
	private SimpleAttributeSet replyAttrSet = new SimpleAttributeSet();
	private SimpleAttributeSet timeAttrSet = new SimpleAttributeSet();
	

	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

	TerminalPanel() {
		setMinimumSize(new Dimension(0,0));
		setLayout(new BorderLayout());
		textPane.setBackground(Color.BLACK);
		add(scrollPane, BorderLayout.CENTER);
		setBackground(Color.BLACK);
		textPane.setMargin(new Insets(3,3,3,3));
		textPane.setForeground(Color.WHITE);
		textPane.setFont(font);
		textPane.setEditable(false);
		initScrollBar();
		initAttributes();
	}

	/**
	 * 
	 */
	private void initAttributes() {
		privMsg.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.WHITE);
		privMsg.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		privMsg.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		originAttrSet.addAttribute(StyleConstants.CharacterConstants.Foreground, new Color(0xae81ff));
		originAttrSet.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		originAttrSet.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		replyAttrSet.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		replyAttrSet.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		replyAttrSet.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		timeAttrSet.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.CYAN);
		timeAttrSet.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		timeAttrSet.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

	}

	/**
	 * 
	 */
	private void initScrollBar() {
		scrollBar.addAdjustmentListener(new AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent e) {
				if (model.getValue() == model.getMaximum() - model.getExtent()) {
					caret.setDot(textPane.getText().length());
					caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				} else {
					caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
				}
			}
		});
		scrollPane.getVerticalScrollBar().setPreferredSize(ChatWindow.getScrollBarDim());
		scrollPane.setBackground(Color.BLACK);
		scrollPane.setBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}

	/**
	 * 
	 * @param user
	 * @param nick
	 * @param origin
	 * @param message
	 * @param reply
	 */
	void newMessage(User user, String nick, String origin, String message, SimpleAttributeSet sas)
	{
		String timeStamp = new SimpleDateFormat("\r\nHH:mm").format(Calendar.getInstance().getTime());

		try {
			doc.insertString(doc.getLength(), timeStamp, timeAttrSet);
			doc.insertString(doc.getLength(), " <"+origin+">", originAttrSet);
			if(user != null)
				doc.insertString(doc.getLength(),"["+nick+"] ", user.getUserSimpleAttributeSet());
			doc.insertString(doc.getLength(), " "+message, sas);

		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		textPane.setCaretPosition(textPane.getDocument().getLength());
	}
}
