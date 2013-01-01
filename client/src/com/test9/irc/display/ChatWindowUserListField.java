package com.test9.irc.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatWindowUserListField extends JPanel {

    static private final long serialVersionUID = 1L;
    static private final int buffer = 150;
    static private boolean init = false;
    static private Dimension dimension;
    static private int height;
    static private final int width = 150;
    static private Rectangle bounds;
    private String channel;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public ChatWindowUserListField(String channel, int screenHeight) {
        if (!init)
            dimension = sizeCalc(screenHeight);

        this.channel = channel;

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setPreferredSize(dimension);
        textArea.setLineWrap(true);
        textArea.setPreferredSize(dimension);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(dimension);

        add(scrollPane);
        setBounds(bounds);

        init = true;

    }

    private static Rectangle boundsCalc(int screenHeight) {
        return new Rectangle(0, 0, width, height);
    }

    public static Dimension sizeCalc(int screenHeight) {
        height = screenHeight / 2;
        bounds = boundsCalc(screenHeight);
        return new Dimension(width, height);
    }

    public void resize() {

    }

    public static boolean isInit() {
        return init;
    }

    public static void setInit(boolean init) {
        ChatWindowUserListField.init = init;
    }

    public static Dimension getDimension() {
        return dimension;
    }

    public static void setDimension(Dimension dimension) {
        ChatWindowUserListField.dimension = dimension;
    }

    public int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        ChatWindowUserListField.height = height;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static int getBuffer() {
        return buffer;
    }

    public int getWidth() {
        return width;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
}
