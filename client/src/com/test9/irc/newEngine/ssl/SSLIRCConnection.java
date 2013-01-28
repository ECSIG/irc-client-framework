/*
 * IRClib -- A Java Internet Relay Chat library -- class SSLIRCConnection
 * Copyright (C) 2002 - 2006 Christoph Schwering <schwering@gmail.com>
 * 
 * This library and the accompanying materials are made available under the
 * terms of the
 * 	- GNU Lesser General Public License,
 * 	- Apache License, Version 2.0 and
 * 	- Eclipse Public License v1.0.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY.
 */

package com.test9.irc.newEngine.ssl;

import java.io.IOException;
import java.net.SocketException;
import java.util.Vector;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import com.test9.irc.newEngine.IRCConnection;

public class SSLIRCConnection extends IRCConnection {

	/**
	 * The SSL protocol of choice. Values can be "TLS", "SSLv3" or "SSL".
	 * "SSL" is the default value.
	 */
	public static String protocol = "SSL";

	/**
	 * The list of <code>SSLTrustManager</code>s. 
	 */
	private Vector trustManagers = new Vector(1);

	// ------------------------------

	/**
	 * Creates a new IRC connection with secure sockets (SSL). <br />
	 * The difference to the other constructor is, that it transmits the ports in
	 * an <code>int[]</code>. Thus, also ports like 994, 6000 and 6697 can be 
	 * selected.<br /><br />
	 * The constructor prepares a new IRC connection with secure sockets which 
	 * can be really started by invoking the <code>connect</code> method. Before 
	 * invoking it, you should set the <code>IRCEventListener</code>, optionally
	 * the <code>SSLTrustManager</code>, if you don't want to use the 
	 * <code>SSLDefaultTrustManager</code> which accepts the X509 certificate 
	 * automatically, and other settings.<br />
	 * Note that you do not need to set a password to connect to the large public
	 * IRC networks like QuakeNet, EFNet etc. To use no password in your IRC
	 * connection, use <code>""</code> or <code>null</code> for the password
	 * argument in the constructor.
	 * @param host The hostname of the server we want to connect to.
	 * @param ports The portrange to which we want to connect.
	 * @param pass The password of the IRC server. If your server isn't 
	 *             secured by a password (that's normal), use 
	 *             <code>null</code> or <code>""</code>.
	 * @param nick The nickname for the connection. Is used to register the 
	 *             connection. 
	 * @param username The username. Is used to register the connection. 
	 * @param realname The realname. Is used to register the connection. 
	 * @throws IllegalArgumentException If the <code>host</code> or 
	 *                                  <code>ports</code> is <code>null</code> or
	 *                                  <code>ports</code>' length is 
	 *                                  <code>0</code>.
	 * @see #connect()
	 */
	public SSLIRCConnection(String host, int port, String pass, String nick, 
			String username, String realname, String encoding) {
		super(host, port, pass, nick, username, realname, encoding);
	}


	// ------------------------------

	/** 
	 * Establish a connection to the server. <br />
	 * This method must be invoked to start a connection; the constructor doesn't 
	 * do that!<br />
	 * It tries all set ports until one is open. If all ports fail it throws an 
	 * <code>IOException</code>. If anything SSL related fails (for example 
	 * conflicts with the algorithms or during the handshaking), a 
	 * <code>SSLException</code> is thrown. <br />
	 * You can invoke <code>connect</code> only one time.
	 * @throws NoClassDefFoundError If SSL is not supported. This is the case
	 *                              if neither JSSE nor J2SE 1.4 or later is 
	 *                              installed.
	 * @throws SSLNotSupportedException If SSL is not supported. This is the 
	 *                                  case if neither JSSE nor J2SE 1.4 or 
	 *                                  later is installed. This exception is 
	 *                                  thrown if no NoClassDefFoundError is 
	 *                                  thrown.
	 * @throws IOException If an I/O error occurs. 
	 * @throws SSLException If anything with the secure sockets fails. 
	 * @throws SocketException If the <code>connect</code> method was already
	 *                         invoked.
	 * @see #isConnected()
	 * @see #doQuit()
	 * @see #doQuit(String)
	 * @see #close()
	 */
	public void connect() throws IOException {
		if (regLevel != 0) // otherwise disconnected or connect
			throw new SocketException("Socket closed or already open ("+ regLevel +")");
		IOException exception = null;
		if (trustManagers.size() == 0)
			addTrustManager(new SSLDefaultTrustManager());
		SSLSocketFactory sf = null;
		SSLSocket s = null;
		try {
			if (sf == null)
				sf = SSLSocketFactoryFactory.createSSLSocketFactory(getTrustManagers());
			s = (SSLSocket)sf.createSocket(host, port);
			s.startHandshake();
			exception = null;
		} catch (SSLNotSupportedException exc) {
			if (s != null)
				s.close();
			s = null;
			throw exc;
		} catch (IOException exc) {
			if (s != null)
				s.close();
			s = null;
			exception = exc; 
		}
		if (exception != null)
			throw exception; // connection wasn't successful at any port

		prepare(s);
	}

	// ------------------------------

	/**
	 * Adds a new <code>SSLTrustManager</code>.
	 * @param trustManager The <code>SSLTrustManager</code> object which is to 
	 * add.
	 * @see #removeTrustManager(SSLTrustManager)
	 * @see #getTrustManagers()
	 */
	public void addTrustManager(SSLTrustManager trustManager) {
		trustManagers.add(trustManager);
	}

	// ------------------------------

	/**
	 * Removes one <code>SSLTrustManager</code>.
	 * @param trustManager The <code>SSLTrustManager</code> object which is to 
	 *                     remove.
	 * @return <code>true</code> if a <code>SSLTrustManager</code> was removed.
	 * @see #addTrustManager(SSLTrustManager)
	 * @see #getTrustManagers()
	 */
	public boolean removeTrustManager(SSLTrustManager trustManager) {
		return trustManagers.remove(trustManager);
	}

	// ------------------------------

	/**
	 * Returns the set <code>SSLTrustManager</code>s. The default 
	 * <code>SSLTrustManager</code> is an instance of 
	 * <code>SSLDefaultTrustManager</code>, which is set when you invoke the
	 * <code>connect</code> method without having set another 
	 * <code>SSLTrustManager</code>.
	 * @return The set <code>SSLTrustManager</code>s.
	 * @see #addTrustManager(SSLTrustManager)
	 * @see #removeTrustManager(SSLTrustManager)
	 */
	public SSLTrustManager[] getTrustManagers() {
		SSLTrustManager[] tm = new SSLTrustManager[trustManagers.size()];
		trustManagers.copyInto(tm);
		return tm;
	}
}
