package com.test9.irc.display;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.test9.irc.engine.ServerSender;
import com.test9.irc.engine.User;

/**
 * @author Jared Patton Class for the Chat Window/GUI.
 */
public class ChatWindow extends JFrame implements ComponentListener,
        KeyListener, WindowStateListener, WindowFocusListener {

    /***/
    private static final long serialVersionUID = 1L;
    private final Toolkit kit = Toolkit.getDefaultToolkit();
    private final int eastPanelWidth = 150;
    private JPanel inputPanel, northPanel, eastPanel;
    private JLayeredPane outputLayeredPane, userListPane;
    private JTextField inputField;
    private Dimension screenSize = kit.getScreenSize();
    private int screenWidth = (int) screenSize.getWidth() / 2;
    private int screenHeight = (int) screenSize.getHeight() / 2;
    private JScrollPane chanScrollPane;
    private JMenuBar menuBar;
    private ServerSender serverSender;
    private Dimension chanListDim;
    private JTree chanTree;
    private DefaultTreeCellRenderer treeRenderer = new DefaultTreeCellRenderer();
    private Icon openTreeIcon = new ImageIcon("images/downArrow.png");
    private Icon closedTreeIcon = new ImageIcon("images/rightArrow.png");
    private ArrayList<ChatWindowOutputField> outputFields;
    private ArrayList<ChatWindowUserListField> userLists;
    private int count = 0;

    public ChatWindow() {
        setLayout(new BorderLayout());
        addWindowStateListener(this);
        addComponentListener(this);
        addWindowFocusListener(this);

        setSize(screenWidth, screenHeight);

        inputPanel = new JPanel(new BorderLayout(1, 1));
        inputField = new JTextField();
        inputField.addKeyListener(this);
        inputPanel.add(inputField);

        outputLayeredPane = new JLayeredPane();

        outputFields = new ArrayList<ChatWindowOutputField>(1);
        outputFields.add(new ChatWindowOutputField("#jircc", screenWidth,
                screenHeight));
        outputLayeredPane.setPreferredSize(ChatWindowOutputField.sizeCalc(
                screenWidth, screenHeight));
        outputLayeredPane.add(outputFields.get(0));
        outputLayeredPane.moveToFront(outputFields.get(0));

        outputFields.add(new ChatWindowOutputField("#jared", screenWidth,
                screenHeight));
        outputLayeredPane.add(outputFields.get(1));
        outputLayeredPane.moveToFront(outputFields.get(1));

        eastPanel = new JPanel();

        userListPane = new JLayeredPane();
        userListPane.setPreferredSize(ChatWindowUserListField
                .sizeCalc(screenHeight));

        userLists = new ArrayList<ChatWindowUserListField>(1);
        userLists.add(new ChatWindowUserListField("#jircc", screenHeight));
        userListPane.add(userLists.get(0));

        menuBar = new JMenuBar();
        menuBar.add(new JMenu("File"));
        northPanel = new JPanel(new BorderLayout(1, 1));
        northPanel.add(menuBar);

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Root");
        createRootNodes(top);
        chanTree = new JTree(top);

        chanTree.setCellRenderer(treeRenderer);
        treeRenderer.setOpenIcon(openTreeIcon);
        treeRenderer.setClosedIcon(closedTreeIcon);
        treeRenderer.setLeafIcon(null);
        chanScrollPane = new JScrollPane(chanTree);
        chanListDim = new Dimension(eastPanelWidth, screenHeight / 2);
        chanScrollPane.setPreferredSize(chanListDim);

        eastPanel.setPreferredSize(new Dimension(eastPanelWidth, screenHeight));
        eastPanel.add(userListPane, BorderLayout.NORTH);
        eastPanel.add(chanScrollPane, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.SOUTH);
        add(northPanel, BorderLayout.NORTH);
        add(eastPanel, BorderLayout.EAST);
        add(outputLayeredPane, BorderLayout.CENTER);

        System.out.println("output layered pane preferred size..."
                + outputLayeredPane.getPreferredSize());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }

    synchronized public void updateUserList(ArrayList<User> userList) {
        userLists.get(0).getTextArea().setText("");
        for (User nick : userList)
            userLists.get(0).getTextArea().append(nick.getNick() + "\n");
    }

    synchronized private void createRootNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode category = null;

        category = new DefaultMutableTreeNode("Books");
        top.add(category);
    }

    synchronized public void createChannelNode(String channel) {

    }

    /**
     * @param output
     *            Appends the channel output to the output panel.
     */
    synchronized public void appendOutput(String output) {
        try {
            outputFields.get(0).getTextArea().append(output);
            outputFields
                    .get(0)
                    .getTextArea()
                    .setCaretPosition(
                            outputFields.get(0).getTextArea().getText()
                                    .length());
        } catch (Exception e) {
        }
    }

    /**
     * Will be used to manage the Channel JTree when a user joins a channel.
     */
    synchronized public void joinedChannel() {

    }

    /**
     * Will be used to manage the Channel JTree when a user parts from a
     * channel.
     */
    synchronized public void partedChannel() {

    }

    /**
     * Fixes the size of components in the frame when window is resized.
     */
    synchronized public void componentResized(ComponentEvent e) {
        System.out.println("component resized");
        screenWidth = this.getWidth();
        screenHeight = this.getHeight();
        try {
            outputLayeredPane.setPreferredSize(ChatWindowOutputField.sizeCalc(
                    screenWidth, screenHeight));
            for (ChatWindowOutputField temp : outputFields)
                temp.resize();

            userListPane.setPreferredSize(ChatWindowUserListField
                    .sizeCalc(screenHeight));
            chanListDim.setSize(eastPanelWidth, screenHeight / 2);
            outputLayeredPane.revalidate();
            eastPanel.revalidate();
        } catch (Exception e1) {
            System.out.println("componentResize could not be done yet.");
        }
    }

    /**
     * Handles a window maximization/resize.
     */
    synchronized public void windowStateChanged(WindowEvent arg0) {
        System.out.println("windowStateChanged");
    }

    /**
     * Calls certain methods after certain keystrokes.
     */
    synchronized public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
          //  serverSender.sendMessage(inputField.getText());
            outputFields.get(0).getTextArea()
                    .append("<Me> " + inputField.getText() + "\n");
            inputField.setText("");
            outputFields
                    .get(0)
                    .getTextArea()
                    .setCaretPosition(
                            outputFields.get(0).getTextArea().getText()
                                    .length());
        }
        if (arg0.getKeyCode() == 157)
            if (count % 2 == 0) {
                count++;
                outputLayeredPane.moveToBack(outputFields.get(0));
            } else {
                count++;
                outputLayeredPane.moveToBack(outputFields.get(1));
            }
    }

    /**
     * Gives focus to user input text field when the window gains focus.
     */
    synchronized public void windowGainedFocus(WindowEvent arg0) {
        inputField.requestFocus();
    }

    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void windowLostFocus(WindowEvent arg0) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent arg0) {

    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    synchronized public ServerSender getServerSender() {
        return serverSender;
    }

    synchronized public void setServerSender(ServerSender serverSender) {
        this.serverSender = serverSender;
    }

    synchronized public JTextField getInputField() {
        return inputField;
    }

    synchronized public void setInputField(JTextField inputField) {
        this.inputField = inputField;
    }
}
