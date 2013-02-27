package com.test9.irc.display;

import java.awt.Color;
import java.awt.Font;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class TextFormat extends SimpleAttributeSet {

	private static final long serialVersionUID = 4318003799401379752L;
	public final static SimpleAttributeSet DEBUGSEND = new SimpleAttributeSet();
	public final static SimpleAttributeSet DEBUGRECEIVE = new SimpleAttributeSet();
	public final static SimpleAttributeSet PRIVMSG = new SimpleAttributeSet();
	public final static SimpleAttributeSet ACTION = new SimpleAttributeSet();
	public final static SimpleAttributeSet NOTICE = new SimpleAttributeSet();
	public final static SimpleAttributeSet SYSTEM = new SimpleAttributeSet();
	public final static SimpleAttributeSet ERROR = new SimpleAttributeSet();
	public final static SimpleAttributeSet REPLY = new SimpleAttributeSet();
	public final static SimpleAttributeSet ERRORREPLY = new SimpleAttributeSet();
	public final static SimpleAttributeSet JOIN = new SimpleAttributeSet();
	public final static SimpleAttributeSet PART = new SimpleAttributeSet();
	public final static SimpleAttributeSet QUIT = new SimpleAttributeSet();
	public final static SimpleAttributeSet KICK = new SimpleAttributeSet();
	public final static SimpleAttributeSet KILL = new SimpleAttributeSet();
	public final static SimpleAttributeSet NICK = new SimpleAttributeSet();
	public final static SimpleAttributeSet MODE = new SimpleAttributeSet();
	public final static SimpleAttributeSet TOPIC = new SimpleAttributeSet();
	public final static SimpleAttributeSet INVITE = new SimpleAttributeSet();
	public final static SimpleAttributeSet WALLOPS = new SimpleAttributeSet();
	public final static SimpleAttributeSet privMsg = new SimpleAttributeSet();
	public final static SimpleAttributeSet HYPERLINK = new SimpleAttributeSet();
	public final static SimpleAttributeSet HIGHLIGHT = new SimpleAttributeSet();
	public final static SimpleAttributeSet ORIGINATTRSET = new SimpleAttributeSet();
	public final static SimpleAttributeSet REPLYATTRSET = new SimpleAttributeSet();
	public final static SimpleAttributeSet TIMEATTRSET = new SimpleAttributeSet();
	public final static Font font = new Font("Lucida Grande", Font.BOLD, 12);
	
	
	static void loadColors() {

		PRIVMSG.addAttribute(StyleConstants.Foreground, Color.WHITE);
		PRIVMSG.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		PRIVMSG.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		ACTION.addAttribute(StyleConstants.Foreground,  Color.GRAY);
		ACTION.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		ACTION.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		ERROR.addAttribute(StyleConstants.Foreground, new Color(0xf00));
		ERROR.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		ERROR.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		ERRORREPLY.addAttribute(StyleConstants.Foreground, new Color(0xf00));
		ERRORREPLY.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		ERRORREPLY.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		INVITE.addAttribute(StyleConstants.Foreground, Color.GRAY);
		INVITE.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		INVITE.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		JOIN.addAttribute(StyleConstants.Foreground, Color.GRAY);
		JOIN.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		JOIN.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		KICK.addAttribute(StyleConstants.Foreground, Color.GRAY);
		KICK.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		KICK.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		KILL.addAttribute(StyleConstants.Foreground, Color.GRAY);
		KILL.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		KILL.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		MODE.addAttribute(StyleConstants.Foreground, Color.GRAY);
		MODE.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		MODE.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		NOTICE.addAttribute(StyleConstants.Foreground, Color.BLUE);
		NOTICE.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		NOTICE.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		NICK.addAttribute(StyleConstants.Foreground, Color.GRAY);
		NICK.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		NICK.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		PART.addAttribute(StyleConstants.Foreground, Color.GRAY);
		PART.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		PART.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		QUIT.addAttribute(StyleConstants.Foreground, Color.GRAY);
		QUIT.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		QUIT.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		REPLY.addAttribute(StyleConstants.Foreground, Color.GRAY);
		REPLY.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		REPLY.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		SYSTEM.addAttribute(StyleConstants.Foreground, Color.GRAY);
		SYSTEM.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		SYSTEM.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		TOPIC.addAttribute(StyleConstants.Foreground, new Color(0x8e9c69));
		TOPIC.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		TOPIC.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		WALLOPS.addAttribute(StyleConstants.Foreground, Color.GRAY);
		WALLOPS.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		WALLOPS.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
	
		HIGHLIGHT.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		HIGHLIGHT.addAttribute(StyleConstants.Foreground, Color.GREEN);
		
		privMsg.addAttribute(StyleConstants.Foreground, Color.WHITE);
		privMsg.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		privMsg.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);
		
		HYPERLINK.addAttribute(StyleConstants.Foreground, Color.BLUE);
		HYPERLINK.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		HYPERLINK.addAttribute(StyleConstants.Underline, Boolean.TRUE);
		
		ORIGINATTRSET.addAttribute(StyleConstants.Foreground, new Color(0xae81ff));
		ORIGINATTRSET.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		ORIGINATTRSET.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		REPLYATTRSET.addAttribute(StyleConstants.Foreground, Color.GRAY);
		REPLYATTRSET.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		REPLYATTRSET.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

		TIMEATTRSET.addAttribute(StyleConstants.Foreground, Color.CYAN);
		TIMEATTRSET.addAttribute(StyleConstants.Bold, Boolean.TRUE);
		TIMEATTRSET.addAttribute(StyleConstants.Alignment, StyleConstants.ALIGN_LEFT);

	}
}
