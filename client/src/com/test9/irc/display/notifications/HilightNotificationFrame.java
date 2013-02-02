package com.test9.irc.display.notifications;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JWindow;

import com.test9.irc.engine.User;

/**
 * 	This class implements a notification feature for when a message is
 * highlighted for a user. The notification appears as a translucent
 * pop-up in the top left of the user's screen. 
 * 
 * @author Jason Stedman
 *
 */
public class HilightNotificationFrame extends JWindow {

	// These should be options for the user to set somewhere
	private float maxIntensity=1;
	private int maxNotifications = 5;	
	private float[] bgColor = new float[]{.3f,1f,.3f,.7f};
	private float[] fgColor = new float[]{1f,1f,1f,1f};
	private Font font = new Font("Lucida Grande", Font.BOLD, 14);
	
	// These should be kept private
	private ArrayList<HighlightNotification> notifications = new ArrayList<HighlightNotification>();
	private boolean redraw = true;
	private boolean running = false;
	private FontMetrics metrics;
	private RedrawThread redrawThread;
	private float intensity = maxIntensity;

	/**
	 * Returns the maximum intensity of the pop-up. The intensity
	 * is actually the alpha channel of the window. An intensity
	 * of 1 is opaque, an intensity of 0 is invisible.
	 * 
	 * @return maxIntensity
	 */
	public float getMaxIntensity() {
		return maxIntensity;
	}
	
	/**
	 * Sets the maximum intensity of the pop-up. The intensity
	 * is actually the alpha channel of the window. An intensity
	 * of 1 is opaque, an intensity of 0 is invisible.

	 * @param maxIntensity
	 */
	public void setMaxIntensity(float maxIntensity) {
		this.maxIntensity = maxIntensity;
	}

	/**
	 * Returns the maximum number of messages that will be displayed
	 * in the notification window. This prevents ass munches from 
	 * taking up a user's whole screen with highlight spam.
	 * 
	 * @return maxNotifications
	 */
	public int getMaxNotifications() {
		return maxNotifications;
	}

	/**
	 * Sets the maximum number of messages that will be displayed
	 * in the notification window. This prevents ass munches from 
	 * taking up a user's whole screen with highlight spam.

	 * @param maxNotifications
	 */
	public void setMaxNotifications(int maxNotifications) {
		this.maxNotifications = maxNotifications;
	}

	/**
	 * Returns the background color as an array of 4 float
	 * color components in RGBA format.
	 * 
	 * @return bgColor
	 */
	public float[] getBgColor() {
		return bgColor;
	}
	
	/**
	 * Sets the background color which uses an array of 4
	 * float color components in RGBA format.
	 * 
	 * The alpha component will be multiplied by the local
	 * intensity variable so that the background fades out.
	 * 
	 * Setting the alpha to less than 1 allows the user to
	 * see through the notification window at all times.
	 * 
	 * @param bgColor
	 */
	public void setBgColor(float[] bgColor) {
		this.bgColor = bgColor;
	}
	
	/**
	 * Returns the foreground color as an array of 4 float
	 * color components in RGBA format.
	 * 
	 * @return fgColor
	 */
	public float[] getFgColor() {
		return fgColor;
	}
	
	/**
	 * Sets the foreground color which uses an array of 4
	 * float color components in RGBA format.
	 * 
	 * The alpha component will be multiplied by the local
	 * intensity variable so that the foreground fades out.
	 * 
	 * Setting the alpha to less than 1 allows the user to
	 * see through the notification text at all times. This
	 * may be undesirable for some users.
	 * 
	 * @param fgColor
	 */
	public void setFgColor(float[] fgColor) {
		this.fgColor = fgColor;
	}
	
	/**
	 * Returns the font used in the notification window
	 * 
	 * @return font
	 */
	public Font getFont(){
		return font;
	}

	/**
	 * Sets the font used in the notification window
	 * 
	 * @param font
	 */
	public void setFont(Font font){
		this.font = font;
	}
	
	/**
	 * This is the only constructor, use it.
	 */
	@SuppressWarnings("restriction")
	public HilightNotificationFrame(){
		super();
		com.sun.awt.AWTUtilities.setWindowOpaque(this, false);
		setSize(600,1);
		setAlwaysOnTop(true);
		setVisible(true);
		contents.setOpaque(false);
		contents.repaint();
		redrawThread = new RedrawThread(contents);
		this.add(contents);
		setRunning(true);
		redrawThread.start();
	}

	/*
	 * Adds a new notification to the window.
	 */
	public void newHighlightNotification(String channel, User user, String message){
		setVisible(true);
		intensity = maxIntensity;
		if(notifications.size()==maxNotifications ){
			notifications.remove(0);
		}
		notifications.add(new HighlightNotification(channel, user, message));
		if(metrics!=null){
			setSize(600,(metrics.getHeight()+3)*notifications.size());
			contents.setSize(600,(metrics.getHeight()+3)*notifications.size());
		}else{
			setSize(600,20);
			contents.setSize(600,20);	
		}
		redraw = true;
	}
	
	/**
	 * Returns the run state of the notification thread.
	 * 
	 * Note: Toggling this variable to true will not restart the thread,
	 * once running is false, a new notification window must be created
	 * to restart notifications.

	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Sets the run state of the notification thread. Should be used
	 * to cleanly stop the updating of the window.
	 * 
	 * Note: Toggling this variable to true will not restart the thread,
	 * once running is false, a new notification window must be created
	 * to restart notifications.
	 * 
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	/**
	 * This private class just repaints the notification window.
	 * 
	 * @author Jason
	 *
	 */
	private class RedrawThread extends Thread {
		Component target;
		public RedrawThread(Component target){
			this.target = target;
		}

		public void run(){
			while(running){
				if(redraw){
					target.repaint();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// This panel actually does the drawing. JPanels support double-buffering so we use them.
	private JPanel contents = new JPanel(true){	

		public void paint(Graphics g){
			// Clear anything that is still in the Graphics buffer from last frame
			g.clearRect(0, 0, getWidth(), getHeight());

			// Fill in the background so the text is readable, fading with intensity
			g.setColor(new Color(bgColor[0],bgColor[1],bgColor[2],bgColor[3]*intensity));
			g.fillRect(0, 0,getWidth(),getHeight());
			
			// Set the forground color using intensity to fade properly
			g.setColor(new Color(fgColor[0],fgColor[1],fgColor[2],fgColor[3]*intensity));

			// Set the font, I hate having to do this in the paint method
			g.setFont(font);

			// The font metrics will help us resize the window as needed and get line spacing
			metrics = g.getFontMetrics();

			// Draw all the notifications
			for(int index = 0;index<notifications.size();index++){
				HighlightNotification n = notifications.get(index);
				int y = this.getHeight()-(index*(metrics.getHeight()+3)+5);
				int xLoc = 10;
				g.drawString(n.getChannel(), xLoc, y);
				xLoc += 10 + metrics.stringWidth(n.getChannel());
				g.drawString(n.getUser().getNick(), xLoc, y);
				xLoc += 10 + metrics.stringWidth(n.getUser().getNick());
				g.drawString(n.getMessage(), xLoc, y);
			}
			
			// Adjust intensity
			intensity-=.02;

			// When intensity reaches 0, clear the buffer and stop redrawing, also clear the notifications list
			if(intensity<0){
				// reset intensity for the next time
				intensity=maxIntensity;
				g.clearRect(0, 0, getWidth(), getHeight());
				redraw=false;
				notifications.clear();
			}

		}
	};
}

