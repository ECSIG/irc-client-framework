package com.test9.irc.engine;

import java.io.IOException;
import java.util.ArrayList;

import com.test9.irc.display.ChatWindow;
import com.test9.irc.display.EventAdapter;
import com.test9.irc.engine.SSL.SSLDefaultTrustManager;
import com.test9.irc.engine.SSL.SSLIRCConnection;
import com.test9.irc.engine.SSL.SSLTrustManager;

public class ConnectionEngine {
	
	private ArrayList<IRCConnection> connections = new ArrayList<IRCConnection>();
	private ChatWindow cw;
	
	public ConnectionEngine() throws IOException {

		cw = new ChatWindow();
		cw.addChatWindowListener(new EventAdapter(cw, cw.getUtil()));
		
//		IRCConnection temp0 = new IRCConnection("jared.test9.us", 6667, "jircc", "jircc", 
//				"jircc", "jircc", "UTF-8");
//		connections.add(temp0);
//		cw.getListener().onJoinServer(temp0.getHost());
//		temp0.addIRCEventListener(new IRCEventAdapter(this, temp0));
//		temp0.connect();
//		
//		try {
//			IRCConnection.sleep(4000);
//		} catch(Exception e) {
//			
//		}
		
//		SSLIRCConnection temp = new SSLIRCConnection("irc.ecsig.com", 6697, "jar-bot", "jar-bot", 
//				"jar-bot", "jar-bot", "UTF-8");
//		connections.add(temp);
//		temp.addIRCEventListener(new IRCEventAdapter(this, temp));
//		temp.addTrustManager(new SSLDefaultTrustManager());
//		cw.getListener().onJoinServer(temp.getHost());
//
//		temp.connect();
		
//		try{
//			IRCConnection.sleep(4000);
//		} catch(Exception e) {
//			
//		}

		
//		try {
//			IRCConnection.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		connections.get(0).send("join #jircc");
//		
//		IRCConnection temp2 = new IRCConnection("chat.freenode.net", 6667, null, "jared", "jared", "jared", "UTF-8");
//		connections.add(temp2);
//		cw.getListener().onJoinServer(temp2.getHost());
//		temp2.addIRCEventListener(new IRCEventAdapter(this, temp2));
//		temp2.connect();
//		
//		try {
//			IRCConnection.sleep(2000);
//		} catch(Exception e) {
//			
//		}
//		
//		IRCConnection temp3 = new IRCConnection("irc.mozilla.org", 6667, null, "jared", "jared", "jared", "UTF-8");
//		connections.add(temp3);
//		cw.getListener().onJoinServer(temp3.getHost());
//		temp3.addIRCEventListener(new IRCEventAdapter(this, temp3));
//		temp3.connect();
	}

	/**
	 * @return the connection
	 */
	public IRCConnection getConnection(String host) {
		System.out.println("getconnectionshost:"+host);
		for(IRCConnection temp : connections) {
			if(temp.getHost().equals(host)) {
				System.out.println("hostmatch");
				return temp;
			}
		}
		return null;
	}

	/**
	 * @return the cw
	 */
	public ChatWindow getCw() {
		return cw;
	}

	/**
	 * @param cw the cw to set
	 */
	public void setCw(ChatWindow cw) {
		this.cw = cw;
	}
}
