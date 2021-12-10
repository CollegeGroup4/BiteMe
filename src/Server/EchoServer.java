// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import common.DBController;
import logic.*;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

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
	public static int DEFAULT_PORT;

	// Constructors ****************************************************
	public static String url, username, password;
	public static Map<String, String> clients = new HashMap<>();

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
	public static ArrayList<String> orders = new ArrayList<String>();
	public static Connection con;
	public Gson gson;
	private final String GET = "GET";
	private final String POST = "POST";
	private final String PUT = "PUT";
	private final String DELETE = "DELETE";

	public EchoServer(int port) {
		super(port);
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
		JsonObject m = gson.fromJson((String)msg, JsonObject.class);
		String method = gson.fromJson(m.get("method"), String.class);
		String path = gson.fromJson(m.get("path"), String.class);
		Response response = new Response();
		System.out.println("Message received: " + path + " "+ method + " from " + client);
		
		switch (path) {
			case "/orders":
				switch (method) {
					case GET:
							String resturantID = gson.fromJson(m.get("resturantID"), String.class);
							OrderApiService.allOrders(resturantID, response);
						break;
					case POST:
							Order addOrder = gson.fromJson(m.get("order"), Order.class);
							OrderApiService.addOrder(addOrder,response);
						break;
					}
				break;
			case "/orders/payment":
				switch (method) {
					case GET:
							Integer accountID = gson.fromJson(m.get("accountID"), Integer.class);
							OrderApiService.getPaymentApproval(accountID,response);
						break;
					}
				break;
			case "/orders/resturants":
				switch (method) {
					case GET:
							String area  = gson.fromJson(m.get("area"), String.class);
							OrderApiService.getResturants(area,response);
						break;	
				}
				break;
			case "/orders/getOrderById":
				switch (method) {
					case GET:
						Integer orderID  = gson.fromJson(m.get("orderId"), Integer.class);
						OrderApiService.getOrderById(orderID,response);
						break;
					case PUT:
						OrderApiService.updateOrderWithForm(orderId, address, delivery,response);
						break;
					case DELETE:
						orderID  = gson.fromJson(m.get("orderId"), Integer.class);
						OrderApiService.deleteOrder(orderID,response);
				}
				break;
			case "/branch_manager":
				break;
			case "/branch_manager/orders":
				break;
			case "/suppliers/menus":
				break;
			case "/suppliers/items":
				break;
			case "/suppliers/getItemsBeMenu":
				break;
			case "/suppliers/approveOrder":
				break;
			case "/accounts":
				break;
			case "/accounts/createWithArray":
				break;
			case "/accounts/createWithList":
				break;
			case "/accounts/login":
				break;
			case "/accounts/logout":
				break;
			case "/accounts/getUser":
				break;
	
			default:
				break;
		}
		client.sendToClient(gson.toJson(response));		
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
		con = DBController.getMySQLConnection(EchoServer.url, EchoServer.username, EchoServer.password);
		Gson gson = new Gson();
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
