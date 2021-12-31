// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import common.DBController;
import common.Request;
import logic.Account;
import logic.BusinessAccount;
import logic.Item;
import logic.Menu;
import logic.Order;
import logic.PrivateAccount;
import logic.Restaurant;
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
	public static Gson gson = new Gson();
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
		JsonObject body = null;
		Request m = gson.fromJson((String) msg, Request.class);
		String method = m.getMethod();
		String path = m.getPath();
		if (m.getBody() != null) {
			body = (JsonObject) gson.toJsonTree(m.getBody());
		}
		Response response = new Response();
		System.out.println("Message received: " + path + " " + method + " from " + client);
		switch (path) {
		// TODO start reports generator with a thread
		case "/import":
		case "/ping":
			clients.put(client.getInetAddress().getHostName(), client.getInetAddress().getHostAddress());
			ServerPortFrameController.isAdded = true;
			response.setCode(200);
			response.setDescription("A connaction with the server has been established at port " + DEFAULT_PORT);
			break;
		case "/exit":
			clients.remove(client.getInetAddress().getHostName(), client.getInetAddress().getHostAddress());
			ServerPortFrameController.isAdded = true;
			response.setCode(200);
			response.setDescription("A connaction with the server has been established at port " + DEFAULT_PORT);
			break;
		case "/orders":
			switch (method) {
			case GET:
				int restaurantID = gson.fromJson(body.get("restaurantID"), Integer.class);
				OrderApiService.AllOrdersByRestaurantID(restaurantID, response);
				break;
			case POST:
				Order addOrder = gson.fromJson(body.get("order"), Order.class);
				OrderApiService.addOrder(addOrder, response);
				break;
			}
			break;
		case "/orders/paymentApproval/business":
			switch (method) {
			case GET:
				String userName = gson.fromJson(body.get("userName"), String.class);
				Float amountToPay = gson.fromJson(body.get("amountToPay"), Float.class);
				String businessName = gson.fromJson(body.get("businessName"), String.class);
				String businessW4C = gson.fromJson(body.get("businessW4C"), String.class);
				OrderApiService.getPaymentApproval(userName, amountToPay, businessW4C, businessName, response);
				break;
			}
			break;
		case "/orders/getOrderById":
			switch (method) {
			case GET:
				Integer orderID = gson.fromJson(body.get("orderId"), Integer.class);
				OrderApiService.getOrderById(orderID, response);
				break;
			case PUT:
				Order updateOrder = gson.fromJson(body, Order.class);
				OrderApiService.updateOrder(updateOrder, response);
				break;
			case DELETE:
				orderID = gson.fromJson(body.get("orderId"), Integer.class);
				OrderApiService.deleteOrder(orderID, response);
			}
			break;
		case "/orders/getOrderById/getItems":
			switch (method) {
			case GET:
				Integer orderID = gson.fromJson(body.get("orderId"), Integer.class);
				OrderApiService.getItemsByOrderID(orderID, response);
				break;
			}
			break;
		case "/branch_manager":
			break;
		case "/branch_managers/restaurants/suppliers":
			switch (method) {
			case GET:
				String userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
				Restaurant restaurant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
				BranchManagerApiService.registerSupplierModerator(userName, "Supplier", restaurant, response);
				break;
			}
		case "/branch_managers/restaurants/moderators":
			switch (method) {
			case GET:
				String userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
				Restaurant restaurant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
				BranchManagerApiService.registerSupplierModerator(userName, "Moderator", restaurant, response);
				break;
			}
		case "/branch_managers/restaurants":
			switch (method) {
			case POST:
				Restaurant restaurant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
				JsonElement supplier = gson.fromJson(body.getAsJsonObject().get("supplier"), JsonElement.class);
				String supplierUserName = gson.fromJson(supplier.getAsJsonObject().get("userName"), String.class);
				if(supplierUserName != null)
					BranchManagerApiService.registerSupplierModerator(supplierUserName, "Supplier", restaurant, response);
				JsonElement moderator = gson.fromJson(body.getAsJsonObject().get("moderator"), JsonElement.class);
				String moderatorUserName = gson.fromJson(moderator.getAsJsonObject().get("userName"), String.class);
				if(moderatorUserName != null)
					BranchManagerApiService.registerSupplierModerator(moderatorUserName, "Moderator", restaurant, response);
			case GET:
				Integer branchManagerID = gson.fromJson(body.get("branchManagerID"), Integer.class);
				BranchManagerApiService.getBranchRestaurants(branchManagerID, response);
				break;
			case PUT:
				String userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
				restaurant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
				BranchManagerApiService.editRestaurant(userName, restaurant, response);
				break;
			case DELETE:
				 userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
				 restaurant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
				 BranchManagerApiService.deleteRestaurant(userName, restaurant, response);
				break;
			}
			break;
		case "/branch_manager/orders":
			break;
		case "/restaurants/getCredit":
			switch (method) {
			case GET:
				Integer restaurantID = gson.fromJson(body.get("restaurantID"), Integer.class);
				String userName = gson.fromJson(body.get("userName"), String.class);
				RestaurantApiService.getCredit(userName, restaurantID, response);
				break;
			}
			break;
		case "/restaurants/areas":
			switch (method) {
			case GET:
				String area = gson.fromJson(body.get("area"), String.class);
				RestaurantApiService.getRestaurantsByArea(area, response);
				break;
			}
			break;
		case "/restaurants/areas/type":
			switch (method) {
			case GET:
				String area = gson.fromJson(body.get("area"), String.class);
				String type = gson.fromJson(body.get("type"), String.class);
				RestaurantApiService.getRestaurantsByTypeAndArea(area, type, response);
				break;
			}
			break;
		case "/restaurants/menus":
			switch (method) {
			case POST:
				Menu menu = gson.fromJson(body, Menu.class);
				RestaurantApiService.createMenu(menu, response);
				break;
			case GET:
				Integer restaurantID = gson.fromJson(body.get("restaurantID"), Integer.class);
				RestaurantApiService.allMenues(restaurantID, response);
				break;
			case PUT:
				Menu oldMenu = gson.fromJson(body.get("oldMenu"), Menu.class);
				Menu newMenu = gson.fromJson(body.get("newMenu"), Menu.class);
				RestaurantApiService.editMenu(oldMenu, newMenu, response);
				break;
			case DELETE:
				menu = gson.fromJson(body.get("oldMenu"), Menu.class);
				RestaurantApiService.editMenu(menu, null, response);
				break;
			}
			break;
		case "/restaurants/items":
			switch (method) {
			case PUT:
				Item item = gson.fromJson(body, Item.class);
				RestaurantApiService.updateItem(item, response);
			case POST:
				item = gson.fromJson(body, Item.class);
				RestaurantApiService.createItem(item, response);
				break;
			case DELETE:
				Integer itemID = gson.fromJson(body.get("itemID"), Integer.class);
				RestaurantApiService.itemsDelete(itemID, response);
				break;
			}
			break;
		case "/restaurants/items/categories":
			switch (method) {
			case GET:
				int restaurantNum = gson.fromJson(body.get("restaurantNum"), Integer.class);
				RestaurantApiService.getAllCategories(restaurantNum, response);
				break;
			}
			break;
		case "/restaurants/getItemsByMenu":
			break;
		case "/restaurants/menus/getItemsByType":
			break;
		case "/restaurants/menus/category/getItemsBySubCategory":
			break;
		case "/restaurants/approveOrder":
			switch (method) {
			case POST:
				Integer orderID = gson.fromJson(body.get("orderId"), Integer.class);
				RestaurantApiService.approveOrder(orderID, response);
				break;
			}
			break;
		case "/users/login":
			String userName = gson.fromJson(body.get("userName"), String.class);
			String password = gson.fromJson(body.get("password"), String.class);
			AccountApiService.loginAccount(userName, password, response);
			break;
		case "/users/loginW4c":
			String W4c = gson.fromJson(body.get("W4c"), String.class);
			AccountApiService.loginAccountW4C(W4c, response);

		case "/users/logout":
			userName = gson.fromJson(body.get("userName"), String.class);
			AccountApiService.logoutAccount(userName, response);
			break;
		case "/accounts/privateAccount":
			switch (method) {
			case POST:
				PrivateAccount account = gson.fromJson(body, PrivateAccount.class);
				AccountApiService.createPrivateAccount(account, response);
				break;
			case GET:
				Account account1 = gson.fromJson(body, Account.class);
				AccountApiService.getAccount(account1, response);
				break;
			case PUT:
				account = gson.fromJson(body, PrivateAccount.class);
				AccountApiService.updatePrivateAccount(account, response);
				break;
			}
			break;
		case "/accounts/businessAccount":
			switch (method) {
			case POST:
				BusinessAccount account = gson.fromJson(body, BusinessAccount.class);
				AccountApiService.createBusinessAccount(account, response);
				break;
			case GET:
				Account account1 = gson.fromJson(body, Account.class);
				AccountApiService.getAccount(account1, response);
				break;
			case PUT:
				account = gson.fromJson(body, BusinessAccount.class);
				AccountApiService.updateBusinessAccount(account, response);
				break;
			}
			break;
		case "/accounts":
			switch (method) {
			case GET:
				int branchManagerID = gson.fromJson(body.get("branchManagerID"), Integer.class);
				AccountApiService.getAllAccounts(branchManagerID, response);
				break;
			}
			break;
		case "/accounts/getAccount":
			switch (method) {
			case DELETE:
				userName = gson.fromJson(body.get("userName"), String.class);
				AccountApiService.deleteAccount(userName, response);
				break;
			}
			break;
		default:
			response.setCode(500);
			response.setDescription("Bad request");
			break;
		}
		try {
			client.sendToClient(gson.toJson(response));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
