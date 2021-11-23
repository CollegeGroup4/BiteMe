// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayList;

import com.mysql.cj.admin.ServerController;

import common.DBController;
import gui.ServerPortFrameController;
import logic.Order;
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

public class EchoServer extends AbstractServer {
	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	// final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************
	public static String url, username, password;

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
	public static ArrayList<String> res;
	public static String[] port = new String[2];
	Connection con;
	public static ServerPortFrameController serverController;

	public EchoServer(int port) {
		super(port);
	}
	public void setController(ServerPortFrameController aFrame) {
		serverController = aFrame;
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

		System.out.println("Message received: " + msg + " from " + client);

		String[] temp = (String[]) msg;
		if (temp[0].equals("GETALL")) {
			if (temp[1].equals("ORDER")) {
				res = DBController.getOrders(con);
			}
		} else if (temp[0].equals("GET")) {
			if (temp[1].equals("ORDER")) {
				res = DBController.getOrder(con, temp[2]);
			}
		} else if (temp[0].equals("UPDATE")) {
			if (temp[1].equals("ORDER")) {
				res = DBController.updateOrder(con, stringToOrder(temp[2]));
			}
		} else if (temp[0].equals("PING")) {
			serverController.manageClientsList(client);
			res = new ArrayList<String>();
			res.add("PORT");
			res.add(serverController.getport());
		}

		try {
			client.sendToClient(res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Order stringToOrder(String orderString) {
		String[] result = orderString.split(",");
		Order temp = new Order(result[0], result[1], result[2], Time.valueOf(result[3]), result[4]);
		temp.setOrderNum(Integer.valueOf(result[5]));
		System.out.println(temp);
		return temp;
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
		con = DBController.getMySQLConnection(EchoServer.url, EchoServer.username, EchoServer.password);
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
