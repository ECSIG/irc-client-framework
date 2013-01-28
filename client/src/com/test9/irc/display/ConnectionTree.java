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
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class ConnectionTree extends JTree implements TreeSelectionListener {

	private static final long serialVersionUID = 8988928665652702491L;

	private static final DefaultTreeCellRenderer treeRenderer = new DefaultTreeCellRenderer();
	private static DefaultTreeModel model;
	private static DefaultMutableTreeNode root;
	private static Font font = new Font("Lucida Grande", Font.BOLD, 12);


	/**
	 * 
	 * @param initialServerName
	 */
	ConnectionTree(String initialServerName, ChatWindow chatWindow)
	{
		root = new DefaultMutableTreeNode("root");
		model = new DefaultTreeModel(root);
		treeRenderer.setClosedIcon(null);
		treeRenderer.setOpenIcon(null);
		treeRenderer.setLeafIcon(null);
		treeRenderer.setFont(font);
		treeRenderer.setBackgroundNonSelectionColor(Color.BLACK);
		treeRenderer.setTextNonSelectionColor(Color.LIGHT_GRAY);
		treeRenderer.setTextSelectionColor(Color.WHITE);
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

	/**
	 * Removes a channel from the JTree.
	 * @param server Name of the server.
	 * @param channel Name of the channel.
	 */
	@SuppressWarnings("unchecked")
	void removeChannelNode(String server, String channel)
	{
		for (Object node : Collections.list(root.children()))
		{
			DefaultMutableTreeNode serverNode = (DefaultMutableTreeNode) node;

			if(serverNode.getUserObject().toString().trim().equals(server.trim())) {
				for (Object channelNode : Collections.list(serverNode.children()))
				{
					DefaultMutableTreeNode checkChannelNode = (DefaultMutableTreeNode) channelNode;

					if(checkChannelNode.getUserObject().toString().trim().equals(channel.trim()))
					{
						checkChannelNode.removeFromParent();
					}
				}
			}
		}
		model.reload();
		expandTree();
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

		String activeChannel = this.getSelectionPath().getLastPathComponent().toString();
		String activeServer = this.getSelectionPath().getParentPath().getLastPathComponent().toString();

		if(activeServer.equals("root"))
			activeServer = activeChannel;

		ChatWindow.newActiveChannels(activeServer, activeChannel);
	}
}