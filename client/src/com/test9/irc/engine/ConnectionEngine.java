package com.test9.irc.engine;

import java.io.IOException;
import java.util.ArrayList;

import com.test9.irc.display.ChatWindow;
import com.test9.irc.display.EventAdapter;

public class ConnectionEngine {
	
	private ArrayList<IRCConnection> connections = new ArrayList<IRCConnection>();
	private ChatWindow cw;
	
	public ConnectionEngine() throws IOException {

		cw = new ChatWindow();
		cw.addChatWindowListener(new EventAdapter(cw, cw.getUtil()));
		
//		IRCConnection temp = new IRCConnection("jared.test9.us", 6667, "jircc", "jircc", 
//				"jircc", "jircc", "UTF-8");
		IRCConnection temp = new IRCConnection("irc.ecsig.com", 6667, "jar-bot", "jar-bot", 
				"jar-bot", "jar-bot", "UTF-8");
		connections.add(temp);

		//((SSLIRCConnection)connection).addTrustManager(new SSLTrustManager());

		cw.getListener().onJoinServer(temp.getHost());

		temp.addIRCEventListener(new IRCEventAdapter(this, temp));
		temp.connect();
		



		try {
			IRCConnection.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		connections.get(0).send("join #jircc");
//		
//		temp = new IRCConnection("chat.freenode.net", 6667, null, "jared", "jared", "jared", "UTF-8");
//		connections.add(temp);
//		cw.getListener().onJoinServer(temp.getHost());
//
//		temp.addIRCEventListener(new IRCEventAdapter(this, temp));
//		temp.connect();
		
//		temp = new IRCConnection("irc.mozilla.org", 6667, null, "jared", "jared", "jared", "UTF-8");
//		connections.add(temp);
//		cw.getListener().onJoinServer(temp.getHost());
//		temp.addIRCEventListener(new IRCEventAdapter(this, temp));
//		temp.connect();
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
