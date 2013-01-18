package com.test9.irc.engine;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ServerSender extends Thread implements Runnable {

	private String output = null;
	BufferedWriter out = null;
	//	private BufferedReader in = null;
	private Server server;

	private static final String RNTAIL = "\r\n";
	ServerSender(Server server) throws IOException {
		this.server = server;

		this.out = new BufferedWriter(new OutputStreamWriter(server.getSocket()
				.getOutputStream()));
//		this.out.write("USER " + server.getLogin() + " 0 * JavaIRC"
//				+ RNTAIL);
//		this.out.write("NICK " + server.getNick() + RNTAIL);

		this.out.flush();
	}

	@Override
	public void run() {
		while (true) {
			if (server.isConnected == true) {
				System.out.println("Server is connected!!!!!!!");
				return;
			}

			try {

				if (this.output != null) {
					System.out.println("SENDING MESSAGE: " + output);
					this.out.write(output);
					this.out.flush();
					this.output = null;
				}
			} catch (IOException e) {
				e.getStackTrace();
			}

			try {
				this.out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread.yield();
		}
	}

	public void setOutput(String output){
		this.output = output;
	}
}
