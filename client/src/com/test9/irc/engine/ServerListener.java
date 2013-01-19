package com.test9.irc.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerListener extends Thread implements Runnable {
 
    private String input = null;
    private BufferedReader in = null;
//    private List<Channel> channels;
    private final Server server;

    ServerListener(Server server) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(server.getSocket()
                .getInputStream()));
        this.server = server;
//        this.channels = server.getChannels();

    }

    @Override
    public void run() {
        // Keep reading lines from the server.

        while (true) {
           // System.out.println("in serverlistener run()	");
            try {
                this.input = this.in.readLine();
               // System.out.println("Server Listener incoming: " + this.input);
                if (this.input != null) {
                	System.out.println("=========Received MESSAGE=========");
                    this.server.getInputManager().sendMessage(input);
                }
            } catch (IOException e) {
                e.getStackTrace();
            }

            Thread.yield();
        }
    }
}
