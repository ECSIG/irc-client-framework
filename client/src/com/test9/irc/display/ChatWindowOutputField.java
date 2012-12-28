package com.test9.irc.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatWindowOutputField extends JPanel {

    static private final long serialVersionUID = 1L;
    static private final int outputWBuffer = 0;
    static private final int outputHBuffer = 0;
    static private int outputWidth, outputHeight;
    static private Dimension dimension;
    static private boolean init = false;
    static private Rectangle bounds;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private String channel;
    static private int count = 0;

    public ChatWindowOutputField(String channel, int screenWidth,
            int screenHeight) {

        if (!init)
            dimension = sizeCalc(screenWidth, screenHeight);

        System.out.println("dimension..." + dimension);
        System.out.println("bounds" + bounds);
        this.channel = channel;

        textArea = new JTextArea();

        textArea.setEditable(false);
        textArea.setFocusable(true);
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setLineWrap(true);
        textArea.setMargin(new Insets(5, 5, 5, 5));
        textArea.setBounds(bounds);
        textArea.setPreferredSize(dimension);
        if (count > 0)
            textArea.setText("hello there");
        count++;

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(dimension);
        scrollPane.setBounds(bounds);

        add(scrollPane);
        setBounds(bounds);

        init = true;

    }

    private static Rectangle boundsCalc(int screenWidth, int screenHeight) {
        return new Rectangle(1, 0, outputWidth, outputHeight);
    }

    public static Dimension sizeCalc(int screenWidth, int screenHeight) {
        outputWidth = screenWidth - outputWBuffer;
        outputHeight = screenHeight - outputHBuffer;
        bounds = boundsCalc(outputWidth, outputHeight);
        dimension = new Dimension(outputWidth, outputHeight);
        return new Dimension(outputWidth, outputHeight);
    }

    public void resize() {
        textArea.setPreferredSize(dimension);
        scrollPane.setPreferredSize(dimension);
        setBounds(bounds);
    }

    public static int getOutputWidth() {
        return outputWidth;
    }

    public static void setOutputWidth(int outputWidth) {
        ChatWindowOutputField.outputWidth = outputWidth;
    }

    public static int getOutputHeight() {
        return outputHeight;
    }

    public static void setOutputHeight(int outputHeight) {
        ChatWindowOutputField.outputHeight = outputHeight;
    }

    public static Dimension getDimension() {
        return dimension;
    }

    public static void setDimension(Dimension outputDim) {
        ChatWindowOutputField.dimension = outputDim;
    }

    public static boolean isInit() {
        return init;
    }

    public static void setInit(boolean init) {
        ChatWindowOutputField.init = init;
    }

    public String getChannel() {
        return channel;
    }

    public void setName(String channel) {
        this.channel = channel;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
