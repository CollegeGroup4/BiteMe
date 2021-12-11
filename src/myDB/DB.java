// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package myDB;

import java.io.*;
import java.util.Iterator;
import java.util.Vector;

import donotenterdrinksorfood.Users;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */

public class DB extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	// final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
	public static Users[] users = new Users[3];

	public DB(int port) {
		super(port);
		serverStarted();
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		int flag = 0;
		System.out.println("Message received: " + msg + " from " + client);
		for (int i = 0; i < 3; i++) {
			if (users[i].getId().equals(msg)) {
				System.out.println("Server Found");
				this.sendToAllClients(users[i].toString());
				flag = 1;
			}

		}
		if (flag != 1) {
			System.out.println("Not Found");
			this.sendToAllClients("Error");
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
		users[0] = new Users("Branchi", "1", "a", 0);
		users[1] = new Users("Suppi", "2","b", 1);
		users[2] = new Users("Usi", "3", "c", 2);

	}

	public int isin(String s1, String s2) {
		for (int i = 0; i < users.length; i++) {
			if ((users[i].getUsername()).equals(s1) && (users[i].getId()).equals(s2))
				return users[i].getRole();
		}
		return -1;
	}

	public Users getUser(String id) {
		for (int i = 0; i < users.length; i++) {
			if ((users[i].getId()).equals(id))
				return users[i];
		}
		return null;

	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}
}
//End of EchoServer class
