package com.test9.irc.display.notifications;

import com.test9.irc.engine.User;
/**
 *  This class just holds the important fields of a 
 * highlight notification.
 *  
 *  We should probably have a class somewhere that encapsulates
 * messages in general. It would probably look very similar to
 * this. For now, this does the job.
 * 
 * @author Jason Stedman
 *
 */
public class HighlightNotification {
	private String message;
	private String channel;
	private User user;

	public HighlightNotification(String channel, User user, String message){
		this.setMessage(message);
		this.setChannel(channel);
		this.setUser(user);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String toString(){
		return this.channel +" : " + this.user.getNick() + " : " + this.message;
	}
}
