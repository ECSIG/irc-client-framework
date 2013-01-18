package com.test9.irc.engine;	
// Test test testy test

import com.test9.irc.display.*;
//import org.schwering.irc.lib.*;

public class Engine {

    static final boolean DEBUGGING = true;

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        Server jircc = new Server("irc.ecsig.com", 6667, null, "jared-test", "jared-test", "jared-test");
       // ChatWindow chatWindow = new ChatWindow(jircc.getHost());
    }
}
