package com.test9.irc.engine;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import com.test9.irc.parser.*;

public class OutputManager implements Observer {

	private ServerSender sender;
	private Server server;
	private OutputFactory oF = new OutputFactory();

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
	public void update(Observable o, Object arg) {
		if (arg instanceof Message) {
			Message m = ((Message) arg);

			if (server.isConnected == true) {
				System.out.println("Server is connected!!!!!!!");

				joinChannel();
				return;
			}

			if (m.getCommand().equals("PING")) {
				pingPong(m.getContent());
			}
		} else if (!(o instanceof InputManager)) {
			if(arg instanceof String){
				String s = ((String) arg);
				sendMessage(s);
			}
		} 
	}

	private void pingPong(String server) {
		//String output = "PONG " + server + Server.RNtail;
		sender.setOutput(oF.formatMessage("/PONG " + server, null));
	}

	synchronized private void joinChannel() {
		// TODO: This currently only joins the initial channels.
		for (Channel c : server.getChannels()) {
			System.out.println("Attempting to JOIN " + c);
			sender.setOutput(oF.formatMessage("/JOIN " + c.getName(), null));
			sender.setOutput(oF.formatMessage("/JOIN " + "#help", null));
		}
		server.isConnected = false;
	}

	synchronized private void sendMessage(String message) {
		// TODO: Sends message to each channel in server.getChannels().
		for (@SuppressWarnings("unused") Channel c : server.getChannels()) {
			if (message != null) {
				sender.setOutput(oF.formatMessage(message, "#ecsig"));
			}
		}
	}

	private void sendMessage(String activeServer, String activeChannel, String message) {
		for (@SuppressWarnings("unused") Channel c : server.getChannels()) {
			if (message != null) {
				sender.setOutput(oF.formatMessage(message, "#ecsig"));
			}
		}
	}

}
