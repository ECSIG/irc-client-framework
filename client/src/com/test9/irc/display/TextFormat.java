package com.test9.irc.display;

import java.awt.Color;
import java.awt.Font;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class TextFormat extends SimpleAttributeSet {

	private static final long serialVersionUID = 4318003799401379752L;
	public final static SimpleAttributeSet debugSend = new SimpleAttributeSet();
	public final static SimpleAttributeSet debugReceive = new SimpleAttributeSet();
	public final static SimpleAttributeSet privmsg = new SimpleAttributeSet();
	public final static SimpleAttributeSet action = new SimpleAttributeSet();
	public final static SimpleAttributeSet notice = new SimpleAttributeSet();
	public final static SimpleAttributeSet system = new SimpleAttributeSet();
	public final static SimpleAttributeSet error = new SimpleAttributeSet();
	public final static SimpleAttributeSet reply = new SimpleAttributeSet();
	public final static SimpleAttributeSet errorReply = new SimpleAttributeSet();
	public final static SimpleAttributeSet join = new SimpleAttributeSet();
	public final static SimpleAttributeSet part = new SimpleAttributeSet();
	public final static SimpleAttributeSet quit = new SimpleAttributeSet();
	public final static SimpleAttributeSet kick = new SimpleAttributeSet();
	public final static SimpleAttributeSet kill = new SimpleAttributeSet();
	public final static SimpleAttributeSet nick = new SimpleAttributeSet();
	public final static SimpleAttributeSet mode = new SimpleAttributeSet();
	public final static SimpleAttributeSet topic = new SimpleAttributeSet();
	public final static SimpleAttributeSet invite = new SimpleAttributeSet();
	public final static SimpleAttributeSet wallops = new SimpleAttributeSet();
	public final static SimpleAttributeSet privMsg = new SimpleAttributeSet();
	public final static SimpleAttributeSet hyperlink = new SimpleAttributeSet();
	public final static SimpleAttributeSet highlight = new SimpleAttributeSet();
	public final static SimpleAttributeSet originAttrSet = new SimpleAttributeSet();
	public final static SimpleAttributeSet replyAttrSet = new SimpleAttributeSet();
	public final static SimpleAttributeSet timeAttrSet = new SimpleAttributeSet();
	public final static Font font = new Font("Lucida Grande", Font.BOLD, 12);
	
	
	static void loadColors() {

		privmsg.addAttribute(StyleConstants.Foreground, Color.WHITE);
		privmsg.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		privmsg.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		action.addAttribute(StyleConstants.Foreground,  Color.GRAY);
		action.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		action.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		error.addAttribute(StyleConstants.Foreground, new Color(0xf00));
		error.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		error.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		errorReply.addAttribute(StyleConstants.Foreground, new Color(0xf00));
		errorReply.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		errorReply.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		invite.addAttribute(StyleConstants.Foreground, Color.GRAY);
		invite.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		invite.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		join.addAttribute(StyleConstants.Foreground, Color.GRAY);
		join.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		join.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		kick.addAttribute(StyleConstants.Foreground, Color.GRAY);
		kick.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		kick.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		kill.addAttribute(StyleConstants.Foreground, Color.GRAY);
		kill.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		kill.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		mode.addAttribute(StyleConstants.Foreground, Color.GRAY);
		mode.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		mode.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		notice.addAttribute(StyleConstants.Foreground, Color.BLUE);
		notice.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		notice.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		nick.addAttribute(StyleConstants.Foreground, Color.GRAY);
		nick.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		nick.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		part.addAttribute(StyleConstants.Foreground, Color.GRAY);
		part.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		part.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		quit.addAttribute(StyleConstants.Foreground, Color.GRAY);
		quit.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		quit.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		reply.addAttribute(StyleConstants.Foreground, Color.GRAY);
		reply.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		reply.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		system.addAttribute(StyleConstants.Foreground, Color.GRAY);
		system.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		system.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		topic.addAttribute(StyleConstants.Foreground, new Color(0x8e9c69));
		topic.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		topic.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		wallops.addAttribute(StyleConstants.Foreground, Color.GRAY);
		wallops.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		wallops.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
	
		highlight.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		highlight.addAttribute(StyleConstants.Foreground, Color.GREEN);
		
		privMsg.addAttribute(StyleConstants.Foreground, Color.WHITE);
		privMsg.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		privMsg.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		hyperlink.addAttribute(StyleConstants.Foreground, Color.BLUE);
		hyperlink.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		hyperlink.addAttribute(StyleConstants.Underline, Boolean.TRUE);
		
		originAttrSet.addAttribute(StyleConstants.Foreground, new Color(0xae81ff));
		originAttrSet.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		originAttrSet.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		replyAttrSet.addAttribute(StyleConstants.Foreground, Color.GRAY);
		replyAttrSet.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		replyAttrSet.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		timeAttrSet.addAttribute(StyleConstants.Foreground, Color.CYAN);
		timeAttrSet.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		timeAttrSet.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

	}
}
