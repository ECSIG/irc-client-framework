package com.test9.irc.engine;
// Test test testy test

import com.test9.irc.display.*;

public class Engine {

    static final boolean DEBUGGING = true;

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        Server jircc = new Server(DEBUGGING, "jircc");
        ChatWindow chatWindow = new ChatWindow(jircc.getName(), jircc.getOutputManager());
    }
}
