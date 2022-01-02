// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import common.DBController;
import common.Request;
import common.Response;
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
	 * @param request The request received from the client.
	 * @param client  The connection from which the message originated.
	 * @param
	 */
	public void handleMessageFromClient(Object request, ConnectionToClient client) {
		JsonElement body = null;
		Request m = gson.fromJson((String) request, Request.class);
		String method = m.getMethod();
		String path = m.getPath();
		Response response = new Response();
		System.out.println("Message received: " + path + " " + method + " from " + client);
		switch (path) {
		// TODO start reports generator with a thread
		case "/import":
			AccountApiService.importSuper(response);
			break;
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
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				int restaurantID = gson.fromJson(body.getAsJsonObject().get("restaurantID"), Integer.class);
				OrderApiService.AllOrdersByRestaurantID(restaurantID, response);
				break;
			case POST:
				Order addOrder = gson.fromJson((String)m.getBody(), Order.class);
				OrderApiService.addOrder(addOrder, response);
				break;
			}
			break;
		case "/orders/paymentApproval/business":
			switch (method) {
			case GET:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				String userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
				Float amountToPay = gson.fromJson(body.getAsJsonObject().get("amountToPay"), Float.class);
				String businessName = gson.fromJson(body.getAsJsonObject().get("businessName"), String.class);
				String businessW4C = gson.fromJson(body.getAsJsonObject().get("businessW4C"), String.class);
				OrderApiService.getPaymentApproval(userName, amountToPay, businessW4C, businessName, response);
				break;
			}
			break;
		case "/orders/getOrderById":
			switch (method) {
			case GET:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				int orderID = gson.fromJson(body.getAsJsonObject().get("orderId"), Integer.class);
				OrderApiService.getOrderById(orderID, response);
				break;
			case PUT:
				Order updateOrder = gson.fromJson((String)m.getBody(), Order.class);
				OrderApiService.updateOrder(updateOrder, response);
				break;
			case DELETE:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				orderID = gson.fromJson(body.getAsJsonObject().get("orderId"), Integer.class);
				OrderApiService.deleteOrder(orderID, response);
			}
			break;
		case "/orders/getOrderById/getItems":
			switch (method) {
			case GET:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				Integer orderID = gson.fromJson(body.getAsJsonObject().get("orderId"), Integer.class);
				OrderApiService.getItemsByOrderID(orderID, response);
				break;
			}

			break;
		case "/branch_manager":
			break;
		case "/branch_managers/restaurants/suppliers":
			switch (method) {
			case GET:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				String userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
				int userID = gson.fromJson(body.getAsJsonObject().get("userID"), Integer.class);
				Restaurant restaurant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
				BranchManagerApiService.registerSupplierModerator(userName, userID, "Supplier", restaurant, response);
				break;
			}
		case "/branch_managers/restaurants/moderators":
			switch (method) {
			case GET:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				String userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
				int userID = gson.fromJson(body.getAsJsonObject().get("userID"), Integer.class);
				Restaurant restaurant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
				BranchManagerApiService.registerSupplierModerator(userName, userID, "Moderator", restaurant, response);
				break;
			}
		case "/branch_managers/restaurants":
			switch (method) {
			case POST:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				Restaurant restaurant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
				JsonElement supplier = gson.fromJson(body.getAsJsonObject().get("supplier"), JsonElement.class);
				if (supplier != null) {
					String supplierUserName = gson.fromJson(supplier.getAsJsonObject().get("userName"), String.class);
					int supplierUserID = gson.fromJson(supplier.getAsJsonObject().get("userID"), Integer.class);
					BranchManagerApiService.registerSupplierModerator(supplierUserName, supplierUserID, "Supplier",
							restaurant, response);
				}
				JsonElement moderator = gson.fromJson(body.getAsJsonObject().get("moderator"), JsonElement.class);
				if (moderator != null) {
					String moderatorUserName = gson.fromJson(moderator.getAsJsonObject().get("userName"), String.class);
					int moderatorUserID = gson.fromJson(supplier.getAsJsonObject().get("userID"), Integer.class);
					BranchManagerApiService.registerSupplierModerator(moderatorUserName, moderatorUserID,"Moderator", restaurant,
							response);
				}
			case GET:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				Integer branchManagerID = gson.fromJson(body.getAsJsonObject().get("branchManagerID"), Integer.class);
				BranchManagerApiService.getBranchRestaurants(branchManagerID, response);
				break;
			case PUT:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				String userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
				restaurant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
				BranchManagerApiService.editRestaurant(userName, restaurant, response);
				break;
			case DELETE:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
				restaurant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
				BranchManagerApiService.deleteRestaurant(userName, restaurant, response);
				break;
			}
			break;
		case "/branch_manager/orders":
			break;
		case "/branch_manager/employers":
			switch (method) {
			case POST:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				String businessName = gson.fromJson(body.getAsJsonObject().get("businessName"), String.class);
				int branchManagerID = gson.fromJson(body.getAsJsonObject().get("branchManagerID"), Integer.class);
				BranchManagerApiService.approveEmployer(businessName, branchManagerID, response);
				break;
			}
			break;
		case "/restaurants/getCredit":
			switch (method) {
			case GET:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				Integer restaurantID = gson.fromJson(body.getAsJsonObject().get("restaurantID"), Integer.class);
				String userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
				RestaurantApiService.getCredit(userName, restaurantID, response);
				break;
			}
			break;
		case "/restaurants/areas":
			switch (method) {
			case GET:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				String area = gson.fromJson(body.getAsJsonObject().get("area"), String.class);
				RestaurantApiService.getRestaurantsByArea(area, response);
				break;
			}
			break;
		case "/restaurants/areas/type":
			switch (method) {
			case GET:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				String area = gson.fromJson(body.getAsJsonObject().get("area"), String.class);
				String type = gson.fromJson(body.getAsJsonObject().get("type"), String.class);
				RestaurantApiService.getRestaurantsByTypeAndArea(area, type, response);
				break;
			}
			break;
		case "/restaurants/menus":
			switch (method) {
			case POST:
				Menu menu = gson.fromJson((String)m.getBody(), Menu.class);
				RestaurantApiService.createMenu(menu, response);
				break;
			case GET:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				Integer restaurantID = gson.fromJson(body.getAsJsonObject().get("restaurantID"), Integer.class);
				RestaurantApiService.allMenues(restaurantID, response);
				break;
			case PUT:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				Menu oldMenu = gson.fromJson(body.getAsJsonObject().get("oldMenu"), Menu.class);
				Menu newMenu = gson.fromJson(body.getAsJsonObject().get("newMenu"), Menu.class);
				RestaurantApiService.editMenu(oldMenu, newMenu, response);
				break;
			case DELETE:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				String menuName = gson.fromJson(body.getAsJsonObject().get("menuName"), String.class);
				restaurantID = gson.fromJson(body.getAsJsonObject().get("restaurantID"), Integer.class);
				RestaurantApiService.deleteMenu(menuName, DEFAULT_PORT, response);
				break;
			}
			break;
		case "/restaurants/items":
			switch (method) {
			case PUT:
				Item item = gson.fromJson((String)m.getBody(), Item.class);
				RestaurantApiService.updateItem(item, response);
			case POST:
				item = gson.fromJson((String)m.getBody(), Item.class);
				RestaurantApiService.createItem(item, response);
				break;
			case DELETE:
				body = gson.fromJson((String) m.getBody(), JsonElement.class);
				Integer itemID = gson.fromJson(body.getAsJsonObject().get("itemID"), Integer.class);
				RestaurantApiService.itemsDelete(itemID, response);
				break;
			}
			break;
		case "/restaurants/items/categories":
			switch (method) {
			case GET:
				body = gson.fromJson((String) m.getBody(), JsonElement.class);
				int restaurantNum = gson.fromJson(body.getAsJsonObject().get("restaurantNum"), Integer.class);
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
				body = gson.fromJson((String) m.getBody(), JsonElement.class);
				int orderID = gson.fromJson(body.getAsJsonObject().get("orderID"), Integer.class);
				RestaurantApiService.approveOrder(orderID, response);
				break;
			}
			break;
		case "/users/login":
			body = gson.fromJson((String) m.getBody(), JsonElement.class);
			String userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
			String password = gson.fromJson(body.getAsJsonObject().get("password"), String.class);
			AccountApiService.loginAccount(userName, password, response);
			break;
		case "/users/loginW4c":
			body = gson.fromJson((String) m.getBody(), JsonElement.class);
			String W4c = gson.fromJson(body.getAsJsonObject().get("W4C"), String.class);
			AccountApiService.loginAccountW4C(W4c, response);

		case "/users/logout":
			body = gson.fromJson((String) m.getBody(), JsonElement.class);
			userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
			AccountApiService.logoutAccount(userName, response);
			break;
		case "/accounts/privateAccount":
			switch (method) {
			case POST:
				PrivateAccount account = gson.fromJson((String) m.getBody(), PrivateAccount.class);
				AccountApiService.createPrivateAccount(account, response);
				break;
			case GET:
				Account account1 = gson.fromJson((String) m.getBody(), Account.class);
				AccountApiService.getAccount(account1, response);
				break;
			case PUT:
				account = gson.fromJson((String) m.getBody(), PrivateAccount.class);
				AccountApiService.updatePrivateAccount(account, response);
				break;
			}
			break;
		case "/accounts/businessAccount":
			switch (method) {
			case POST:
				BusinessAccount account = gson.fromJson((String) m.getBody(), BusinessAccount.class);
				AccountApiService.createBusinessAccount(account, response);
				break;
			case GET:
				Account account1 = gson.fromJson((String) m.getBody(), Account.class);
				AccountApiService.getAccount(account1, response);
				break;
			case PUT:
				account = gson.fromJson((String) m.getBody(), BusinessAccount.class);
				AccountApiService.updateBusinessAccount(account, response);
				break;
			}
			break;
		case "/accounts":
			switch (method) {
			case GET:
				body = gson.fromJson((String) m.getBody(), JsonElement.class);
				int branchManagerID = gson.fromJson(body.getAsJsonObject().get("branchManagerID"), Integer.class);
				AccountApiService.getAllAccounts(branchManagerID, response);
				break;
			}
			break;
		case "/accounts/getAccount":
			switch (method) {
			case DELETE:
				body = gson.fromJson((String)m.getBody(), JsonElement.class);
				userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
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
