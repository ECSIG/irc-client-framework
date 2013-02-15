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

		privmsg.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.WHITE);
		privmsg.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		privmsg.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		action.addAttribute(StyleConstants.CharacterConstants.Foreground,  Color.GRAY);
		action.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		action.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		error.addAttribute(StyleConstants.CharacterConstants.Foreground, new Color(0xf00));
		error.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		error.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		errorReply.addAttribute(StyleConstants.CharacterConstants.Foreground, new Color(0xf00));
		errorReply.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		errorReply.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		invite.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		invite.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		invite.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		join.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		join.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		join.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		kick.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		kick.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		kick.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		kill.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		kill.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		kill.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		mode.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		mode.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		mode.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		notice.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.BLUE);
		notice.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		notice.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);

		nick.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		nick.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		nick.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);
		
		part.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		part.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		part.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);
		
		quit.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		quit.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		quit.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);
		
		reply.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		reply.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		reply.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);
		
		system.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		system.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		system.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);
		
		topic.addAttribute(StyleConstants.CharacterConstants.Foreground, new Color(0x8e9c69));
		topic.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		topic.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);
		
		wallops.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GRAY);
		wallops.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		wallops.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);
	
		highlight.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		highlight.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GREEN);
		
		privMsg.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.WHITE);
		privMsg.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		privMsg.addAttribute(StyleConstants.CharacterConstants.Alignment, StyleConstants.CharacterConstants.ALIGN_LEFT);
		
		hyperlink.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.BLUE);
		hyperlink.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		hyperlink.addAttribute(StyleConstants.CharacterConstants.Underline, Boolean.TRUE);
		
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
}
