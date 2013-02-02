package com.test9.irc.display;

import java.awt.Color;
import java.awt.Font;
import java.util.Collections;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Position;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class ConnectionTree extends JTree implements TreeSelectionListener {

	private static final long serialVersionUID = 8988928665652702491L;

	private final CustomDTCR treeRenderer = new CustomDTCR();
	private DefaultTreeModel model;
	private DefaultMutableTreeNode root;
	private Font font = new Font("Lucida Grande", Font.BOLD, 12);
	private ChatWindow owner;


	/**
	 * 
	 * @param initialServerName
	 */
	ConnectionTree(ChatWindow chatWindow)
	{
		owner = chatWindow;
		root = new DefaultMutableTreeNode("root");
		model = new DefaultTreeModel(root);
		treeRenderer.setClosedIcon(null);
		treeRenderer.setOpenIcon(null);
		treeRenderer.setLeafIcon(null);
		treeRenderer.setFont(font);
		treeRenderer.setBackgroundNonSelectionColor(Color.BLACK);
		treeRenderer.setTextNonSelectionColor(Color.LIGHT_GRAY);
		treeRenderer.setTextSelectionColor(Color.WHITE);
		putClientProperty("JTree.lineStyle", "None");
		setBackground(Color.BLACK);
		setModel(model);
		setRootVisible(false);
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		addTreeSelectionListener(this);
		setShowsRootHandles(true);
		setCellRenderer(treeRenderer);
		expandTree();


	}

	private void expandTree()
	{
		for (int i = 0; i < this.getRowCount(); i++) {
			this.expandRow(i);
		}
	}

	/**
	 * Used to add the new channel node to the JTree channelTree.
	 * @param server Name of the new server.
	 * @param channel Name of the new channel.
	 */
	void newChannelNode(String server, String channel)
	{
		expandTree();
		DefaultMutableTreeNode newChannelNode = new DefaultMutableTreeNode(channel);
		newChannelNode.setAllowsChildren(false);
		TreePath path = this.getNextMatch(server, 0, Position.Bias.Forward);
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) path.getLastPathComponent();
		model.insertNodeInto(newChannelNode, parentNode, parentNode.getChildCount());
		expandPath(path);
		expandTree();
		selectNode(newChannelNode.getUserObject().toString());

	}

	/**
	 * Selects a node in the tree.
	 * @param id
	 */
	private void selectNode(String id) {
		@SuppressWarnings("rawtypes")
		Enumeration e = root.breadthFirstEnumeration();
		while(e.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();
			if(node.getUserObject().equals(id)) {
				TreePath path = new TreePath(node.getPath());
				this.setSelectionPath(path);
				break;
			}
		}		
	}

	@SuppressWarnings("unchecked")
	DefaultMutableTreeNode findChannelNode(String server, String channel) {
		for (Object node : Collections.list(root.children()))
		{
			DefaultMutableTreeNode serverNode = (DefaultMutableTreeNode) node;

			if(serverNode.getUserObject().toString().trim().equals(server.trim())) {
				for (Object channelNode : Collections.list(serverNode.children()))
				{
					DefaultMutableTreeNode checkChannelNode = (DefaultMutableTreeNode) channelNode;

					if(checkChannelNode.getUserObject().toString().trim().equals(channel.trim()))
					{
						return checkChannelNode;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Removes a channel from the JTree.
	 * @param server Name of the server.
	 * @param channel Name of the channel.
	 */
	void removeChannelNode(String server, String channel)
	{
		try {
			findChannelNode(server, channel).removeFromParent();
		} catch (NullPointerException e) {
			System.err.println("Cound not find the node to remove from the connection tree.");
		}
		model.reload();
		expandTree();
	}

	void hightlightNode(String server, String channel)
	{
		//		for (Object node : Collections.list(root.children()))
		//		{
		//			DefaultMutableTreeNode serverNode = (DefaultMutableTreeNode) node;
		//
		//			if(serverNode.getUserObject().toString().trim().equals(server.trim())) {
		//				for (Object channelNode : Collections.list(serverNode.children()))
		//				{
		//					DefaultMutableTreeNode checkChannelNode = (DefaultMutableTreeNode) channelNode;
		//
		//					if(checkChannelNode.getUserObject().toString().trim().equals(channel.trim()))
		//					{
		//						checkChannelNode.removeFromParent();
		//					}
		//				}
		//			}
		//		}
		//		model.reload();
		//		expandTree();
	}

	/**
	 * Used to add a new servers parent node.
	 * @param server Name of the server.
	 */
	void newServerNode(String server)
	{
		DefaultMutableTreeNode newServerNode = new DefaultMutableTreeNode(server.trim());
		newServerNode.setAllowsChildren(true);
		root.add(newServerNode);
		model.reload();
		expandTree();
		selectNode(newServerNode.getUserObject().toString());



	}

	/**
	 * Removes a server form the JTree.
	 * @param server Name of the server.
	 */
	void removeServerNode(String server)
	{
		System.out.println("removeServerNode");
		TreePath path = this.getNextMatch(server, 0, Position.Bias.Forward);
		System.out.println("pathserver"+path);
		DefaultMutableTreeNode removeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
		model.removeNodeFromParent(removeNode);
		model.reload();
		expandTree();
	}

	/**
	 * Used by the tree to listen for when the user 
	 * changes the channel that is selected.
	 */
	public void valueChanged(TreeSelectionEvent e) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				this.getLastSelectedPathComponent();

		if(node==null){
			String root = owner.getRootConnection();
			owner.newActiveChannels(root, root);
		}else{
			String activeServer = node.getParent().toString();
			String activeChannel = node.toString();

			if(activeServer.equals("root"))
				activeServer = activeChannel;

			owner.newActiveChannels(activeServer, activeChannel);
		}
	}
}