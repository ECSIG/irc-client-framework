package com.test9.irc.engine;

import java.util.Arrays;

import com.test9.irc.parser.Message;

/**
 * 
 * @author Jared Patton
 *
 */
public class IRCEventAdapter implements IRCEventListener {

	/**
	 * Reference to the connection the adapter belongs to.
	 */
	private IRCConnection connection;

	/**
	 * Reference to the chat window to access the listener.
	 */
	private com.test9.irc.display.Listener cw;

	public IRCEventAdapter(ConnectionEngine connectionEngine, IRCConnection connection) {
		this.connection = connection;
		cw = connectionEngine.getCw().getListener();
	}

	@Override
	public void onConnect(Message m) {


	}

	@Override
	public void onDisconnect() {

	}

	@Override
	public void onError(Message m) {
		int numCode = Integer.valueOf(m.getCommand());
		if(numCode == IRCConstants.ERR_NICKNAMEINUSE) {
			System.out.println("ERR_NICKNAMEINUSE");
			connection.setNick(connection.getNick()+"_");
			connection.send("NICK "+ connection.getNick());
		} else {
			cw.onNewMessage(connection.getHost(), connection.getHost(),
					"Error("+m.getCommand()+")"+m.getContent(), "ERROR");
		}
	}

	@Override
	public void onInvite() {

	}

	@Override
	public void onJoin(String host, Message m) {

		if(m.getUser().equals(connection.getUserName())) {
			if(!(m.getContent().equals("")))
			{
				System.out.println("i am joining a channel myself.");
				cw.onJoinChannel(host, m.getContent());
			}

		} else if(m.getUser().equals(connection.getNick())) {
			if(!(m.getContent().equals("")))
			{
				System.out.println("i am joining a channel myself.");
				cw.onJoinChannel(host, m.getContent());
			}
		}else {
			if(!(m.getParams()[0].equals(""))) {
				System.out.println("no params[0]");
				cw.onUserJoin(host, m.getParams()[0], m.getNickname(), false);
			} else if(!(m.getContent().equals(""))){
				System.out.println("no content");
				cw.onUserJoin(host, m.getContent(), m.getNickname(), false);
			}
			if(!(connection.getUsers().contains(m.getNickname())))
			{
				connection.getUsers().add(new User(m.getNickname(), false));
			}
		}
	}

	@Override
	public void onKick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMode(Message m) {

	}

	@Override
	public void onMode(int two) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNick(Message m) {
		// Set the user's nick in the conneciton user list
		connection.getUser(m.getNickname()).setNick(m.getContent());

		// Tell the chat window listener there was a nick change
		cw.onNickChange(connection.getHost(), m.getNickname(), m.getContent());

		// If it is me
		// Set my change my nick in the connection
		if(m.getNickname().equals(connection.getNick()))
			connection.setNick(m.getContent());

	}

	@Override	
	public void onNotice(Message m) {
		// Notify the listener of a new NoticeMessage
		cw.onNotice(connection.getHost(), m.getParams().toString(), m.getContent());
	}

	@Override
	public void onPart(Message m) {
		// If i parted a channel, tell the listener that i parted
		if(m.getNickname().equals(connection.getNick())) {
			cw.onPartChannel(connection.getHost(), m.getParams()[0]);
		} else /* If someone else parted, notify of a user part*/ {
			cw.onUserPart(connection.getHost(), m.getParams()[0], m.getNickname());
		}
	}

	@Override
	public void onPing(String line) {
		// Respond to pings
		connection.send("PONG " + line);
	}

	@Override
	public void onPrivmsg(String host, Message m) {
		// If my name was mentioned in a message, notify the window listener of a highlight
		if(m.getContent().contains(connection.getNick())) {

			cw.onNewHighlight(host, m.getParams()[0], m.getNickname(), m.getContent());

		} else { // Otherwise...

			// If the message came from a user not in the channel (a ghost)
			if(connection.getUser(m.getNickname())==null) {
				// Add the user
				connection.getUsers().add(new User(m.getNickname(), false));
			}

			// There is a new private message from some nickanem for host at channel
			// It is not a local ChatWindow event
			cw.onNewPrivMessage(
					connection.getUser(m.getNickname()), host, m.getParams()[0], 
					m.getNickname(), m.getContent(), false);		
		}
	}

	@Override
	public void onQuit(Message m) {
		// Someone quit, notify the ChatWindow listener
		cw.onUserQuit(connection.getHost(), m.getNickname(), m.getContent());
	}

	@Override
	public void onReply(Message m) {
		// Get the numerical code of the server reply
		int numCode = Integer.valueOf(m.getCommand());

		// If it is a welcome message 001
		if(numCode == IRCConstants.RPL_WELCOME) {
			// Notify the chat window listener of a new irc connection
			cw.onNewIRCConnection(connection);

			// If the numCode is the name list of a channel
		} else if(numCode == IRCConstants.RPL_NAMREPLY) {
			//Split up the nicks list
			String[] nicks = m.getContent().split(" ");

			// Add nicks 1 at a time
			for(String n : nicks)
			{
				// The nick has joined on a certain server at a certain channel
				// It was a user reply
				cw.onUserJoin(connection.getHost(), m.getParams()[2], n, true);
				// If the nick was not me
				if(!n.equals(connection.getNick()))
					//Add a new user to the Connections user list, false - not me
					connection.getUsers().add(new User(n,false));
				else
					//Add myself ot the Connections user list, true - it was me
					connection.getUsers().add(new User(n,true));
			}
			// If there is a topic message
		} else if(numCode == IRCConstants.RPL_TOPIC) {
			// Notify the chat window listener of a new topic
			cw.onNewTopic(connection.getHost(), m.getParams()[1], m.getContent());
			// Notify the chat window listener that there is a new topic to show the user
			cw.onNewMessage(connection.getHost(), m.getParams()[1],
					"<Topic> " + m.getContent(), "TOPIC");
			// Someone went away
		} else if(numCode == IRCConstants.RPL_NOWAWAY) {

			// Services supported by the server
		} else if(numCode == IRCConstants.RPL_ISUPPORT) {
			cw.onNewMessage(connection.getHost(), connection.getHost(), 
					Arrays.toString(m.getParams())+" "+m.getContent(), "REPLY");
			//List of channels available
		} else if(numCode == IRCConstants.RPL_LIST) {
			cw.onNewMessage(connection.getHost(), 
					connection.getHost(), Arrays.toString(m.getParams()), "REPLY");
		} else if(numCode == IRCConstants.RPL_MOTD) {
			cw.onNewMessage(connection.getHost(), connection.getHost(), m.getContent(), "REPLY");
		}else {
			cw.onNewMessage(connection.getHost(), connection.getHost(),
					"Reply("+m.getCommand()+")"+m.getContent(), "REPLY");
		}
	}

	@Override
	public void onTopic(String host, Message m) {
		// There is a topic message from the server
		cw.onNewTopic(host, m.getParams()[0], m.getContent());
	}

	@Override
	public void onUnknown(String host, Message m) {
		try{
			cw.onNewMessage(host, host, m.getContent(), "REPLY");	
		}catch(Exception e){}
	}
}
