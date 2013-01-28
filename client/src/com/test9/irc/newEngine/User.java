package com.test9.irc.newEngine;

import java.awt.Color;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class User {
	
	private String nick;
	private String realName;
	private static int userID = 0;
	private Color color;
	private boolean init = false;
	private SimpleAttributeSet userSimpleAttributeSet;
	
	private static Color[] colors = {
			new Color(255, 105, 105), new Color(105, 198, 252), new Color(252, 216, 105),
			new Color(122, 105, 252), new Color(179, 252, 105), new Color(233, 105, 252), 
			new Color(105, 252, 142), new Color(252, 105, 161), new Color(105, 252, 252), 
			new Color(252, 159, 105), new Color(105, 142, 252), new Color(235, 252, 105), 
			new Color(179, 105, 252), new Color(125, 252, 105), new Color(252, 105, 216), 
			new Color(105, 252, 196), Color.BLUE
	};
	
	private static SimpleAttributeSet[] attributes = new SimpleAttributeSet[17];
	
	
	
	User(String nick) {
		if(!init )
			initAttributes();
		
		this.nick = nick;
		setUserAttributeSet(userID);
		userID++;
	}
	
	private void initAttributes() {
		for(int i = 0; i < attributes.length; i++)
		{
			attributes[i] = new SimpleAttributeSet();
			attributes[i].addAttribute(StyleConstants.CharacterConstants.Foreground, User.getColors()[i]);
			attributes[i].addAttribute(StyleConstants.CharacterConstants.Bold, true);
		}
		init = true;
		
	}

	private void setUserAttributeSet(int index) {
		try {
			userSimpleAttributeSet = attributes[index];
		} catch (IndexOutOfBoundsException e) {
			setUserAttributeSet(index-15);			
		} catch (Exception e) {
			System.err.println("Problem setting the user color.");
		}
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the userID
	 */
	public static int getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public static void setUserID(int userID) {
		User.userID = userID;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the colors
	 */
	public static Color[] getColors() {
		return colors;
	}

	/**
	 * @return the attributes
	 */
	public SimpleAttributeSet[] getAttributes() {
		return attributes;
	}

	/**
	 * @return the userSimpleAttributeSet
	 */
	public SimpleAttributeSet getUserSimpleAttributeSet() {
		return userSimpleAttributeSet;
	}
}
