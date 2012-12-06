package com.test9.irc.engine;

public class Engine {

    static final boolean DEBUGGING = true;

    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        Server jircc = new Server(DEBUGGING, "jircc");
    }
}
