package com.test9.irc.engine;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import com.test9.irc.parser.Message;
import com.test9.irc.parser.OutputFactory;

public class OutputManager implements Observer {

	private ServerSender sender;
	private Server server;

	public OutputManager(Server server) {
		this.server = server;
		
		try {
			this.sender = new ServerSender(server);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for " + "the connection to: "
					+ this.server);
			System.exit(1);
		}

		// Start the sender thread
		this.sender.start();

		System.out.println("Sender started.");
	}

	/**
	 * This method is used to send a message to the server.
	 */
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Message) {
			Message m = ((Message) arg1);

			if (server.isConnected == true) {
				System.out.println("Server is connected!!!!!!!");

				joinChannel();
				return;
			}

			if (m.getCommand().equals("PING")) {
				ping_pong(m.getContent());
			}
		} else if (!(arg0 instanceof InputManager)) {
			if(arg1 instanceof String){
				String s = ((String) arg1);
				sendMessage(s);
			}
		}
	}

	public void ping_pong(String server) {
		String output = "PONG " + server + Server.RNtail;
		sender.setOutput(output);
	}

	synchronized public void joinChannel() {
		// TODO: This currently only joins the initial channels.
		for (Channel c : server.getChannels()) {
			System.out.println("Attempting to JOIN " + c);

			String output = "JOIN " + c.getName() + Server.RNtail;

			sender.setOutput(output);
		}
		server.isConnected = false;
	}

	synchronized public void sendMessage(String message) {
		// TODO: Sends message to each channel in server.getChannels().
		for (Channel c : server.getChannels()) {
			if (message != null) {
				String output = "PRIVMSG " + "#ecsig" + " :" + message
						+ Server.RNtail;

				sender.setOutput(output);
			}
		}
	}

}
