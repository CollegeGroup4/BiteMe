package biteme.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import logic.Account;
import logic.Item;
import logic.Options;
import logic.Order;

/**
 * BiteMe
 *
 * <p>
 * No description provided (generated by Swagger Codegen
 * https://github.com/swagger-api/swagger-codegen)
 *
 */
public class OrderApiService {
	/**
	 * Add a new order
	 *
	 */
	public static void addOrder(Order order, Response response) {
		ResultSet rs;
		int orderID = 0, shipmentID, price;
		Options options = null;

		try {
			PreparedStatement postOrder = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.order (ResturantID, ResturantName, UserName, OrderTime, PhoneNumber, TypeOfOrder, Discount_for_early_order,"
							+ "Check_out_price, isApproved, required_time, approved_time, hasArraived)"
							+ " VALUES (?,?,?,?,?,?,?,?,?,?); SELECT last_insert_id();");
			postOrder.setInt(1, order.getRestaurantID());
			postOrder.setString(2, order.getRestaurantName());
			postOrder.setString(3, order.getUserName());
			postOrder.setString(4, order.getTime_taken());
			postOrder.setString(5, order.getPhone());
			postOrder.setString(6, order.getType_of_order());
			postOrder.setInt(7, order.getDiscount_for_early_order());
			postOrder.setDouble(8, order.getCheck_out_price());
			postOrder.setBoolean(9, order.isApproved());
			postOrder.setString(10, order.getRequired_time());
			postOrder.setString(11, order.getApproved_time());
			postOrder.setBoolean(12, order.getHasArrived());
			postOrder.execute();
			rs = postOrder.getResultSet();
			orderID = rs.getInt(1);

		} catch (SQLException e) {
			response.setCode(405);
			response.setDescription("Invalid input");
			return;
		}
		try {
			PreparedStatement postItem = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.item_in_menu_in_order (OrderNum, ItemID, Item_name, OptionalType, OptionalSpecify,"
							+ "Amount)" + " VALUES (?,?,?,?,?,?);");

			postItem.setInt(1, orderID);

			for (Item temp : order.getItems()) {
				postItem.setInt(2, temp.getItemID());
				postItem.setInt(6, temp.getAmount());
				postItem.setString(3, temp.getName());

				for (Options opt : temp.getOptions()) {
					postItem.setString(4, opt.getOption_category());
					postItem.setString(5, opt.getSpecify_option());
					options = opt;
					try {
						postItem.execute();
					} catch (SQLException e) {

						PreparedStatement getAmount = EchoServer.con.prepareStatement(
								"SELECT amount from biteme.item_in_menu_in_order WHRER OrderNum =? AND ItemID = ? AND OptionalType = ? AND OptionalSpecify = ?;");
						getAmount.setInt(1, orderID);
						getAmount.setInt(2, temp.getItemID());
						getAmount.setString(3, options.getOption_category());
						getAmount.setString(4, options.getSpecify_option());
						getAmount.execute();
						rs = getAmount.getResultSet();

						PreparedStatement updateOrder = EchoServer.con.prepareStatement(
								"UPDATE biteme.item_in_menu_in_order " + "SET Amount = ? WHERE OrderNum = ?"
										+ "ItemID = ? OptionalType = ? OptionalSpecify = ?");

						updateOrder.setInt(1, rs.getInt(1) + temp.getAmount());
						updateOrder.setInt(2, orderID);
						updateOrder.setInt(3, options.getItemID());
						updateOrder.setString(4, options.getOption_category());
						updateOrder.setString(4, options.getSpecify_option());
						updateOrder.execute();
					}
				}
			}
		} catch (SQLException e) {
			response.setBody(null);
			response.setCode(400);
			response.setDescription("Could not add item's to the order");
			try {
				PreparedStatement deleteOrder = EchoServer.con
						.prepareStatement("DELETE biteme.order WHERE OrderNum = ?;");
				deleteOrder.setInt(1, orderID);
			} catch (SQLException ex) {
				e.printStackTrace();
			}
			return;
		}
		if (order.getShippment() != null) {
			try {
				PreparedStatement setShipment = EchoServer.con.prepareStatement(
						"INSERT INTO biteme.shipment (workPlace, Address, receiver_name, receiver_phone_number"
								+ ", deliveryType) VALUES (?,?,?,?,?);");

				setShipment.setString(1, order.getShippment().getWork_place());
				setShipment.setString(2, order.getShippment().getAddress());
				setShipment.setString(3, order.getShippment().getReceiver_name());
				setShipment.setString(4, order.getShippment().getPhone());
				setShipment.setString(5, order.getShippment().getDelivery());
				setShipment.execute();

			} catch (SQLException e) {
				response.setBody(null);
				response.setCode(400);
				response.setDescription(e.getMessage());
				return;
			}
		}
	}

	/**
	 * Return all the orders
	 *
	 */
	public static void allOrders(String condition, Response response) {
		PreparedStatement stmt;
		ArrayList<Order> orders = new ArrayList<>();
		Order order = null;
		try {
			stmt = EchoServer.con.prepareStatement("SELECT * FROM biteme.order WHERE " + condition + ";");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				order = new Order(rs.getInt(QueryConsts.ORDER_ORDER_NUM), rs.getInt(QueryConsts.ORDER_RESTAURANT_ID),
						rs.getString(QueryConsts.ORDER_RESTAURANT_NAME), rs.getString(QueryConsts.ORDER_ORDER_TIME),
						rs.getFloat(QueryConsts.ORDER_CHECK_OUT_PRICE), rs.getString(QueryConsts.ORDER_REQUIRED_TIME),
						rs.getString(QueryConsts.ORDER_TYPE_OF_ORDER), rs.getString(QueryConsts.ORDER_USER_NAME),
						rs.getString(QueryConsts.ORDER_PHONE_NUM),
						rs.getInt(QueryConsts.ORDER_DISCOUNT_FOR_EARLY_ORDER), null, null,
						rs.getString(QueryConsts.ORDER_APPROVED_TIME), rs.getBoolean(QueryConsts.ORDER_HAS_ARRIVED),
						rs.getBoolean(QueryConsts.ORDER_IS_APPROVED));

				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.setCode(200);
		response.setDescription("Success in fetching orders");
		response.setBody(orders.toArray());
	}

	public static void AllOrdersByRestaurantID(int restaurantID, Response response) {
		allOrders("RestaurantID = " + restaurantID, response);
	}

	public static void AllOrdersByUserName(String UserName, Response response) {
		allOrders("UserName = " + UserName, response);
	}

	/**
	 * Deletes an order
	 *
	 */
	public static void deleteOrder(Integer orderId, Response response) {
		try {
			PreparedStatement postOrder = EchoServer.con
					.prepareStatement("DELETE FROM biteme.order WHERE OrderID = ?;");
			postOrder.setInt(1, orderId);
			postOrder.execute();
		} catch (SQLException e) {
			response.setCode(404);
			response.setDescription("Order not found");
			return;
		}
		response.setCode(200);
		response.setDescription("Success in deleting order: " + orderId.toString());
	}

	/**
	 * Find order by ID
	 *
	 * Returns a single order
	 *
	 */
	public static void getOrderById(Integer orderId, Response response) {
		PreparedStatement stmt;
		Order order = null;
		try {
			stmt = EchoServer.con.prepareStatement("SELECT * FROM biteme.order WHERE OrderNum = ?");
			stmt.setInt(1, orderId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				order = new Order(rs.getInt(QueryConsts.ORDER_ORDER_NUM), rs.getInt(QueryConsts.ORDER_RESTAURANT_ID),
						rs.getString(QueryConsts.ORDER_RESTAURANT_NAME), rs.getString(QueryConsts.ORDER_ORDER_TIME),
						rs.getFloat(QueryConsts.ORDER_CHECK_OUT_PRICE), rs.getString(QueryConsts.ORDER_REQUIRED_TIME),
						rs.getString(QueryConsts.ORDER_TYPE_OF_ORDER), rs.getString(QueryConsts.ORDER_USER_NAME),
						rs.getString(QueryConsts.ORDER_PHONE_NUM),
						rs.getInt(QueryConsts.ORDER_DISCOUNT_FOR_EARLY_ORDER), null, null,
						rs.getString(QueryConsts.ORDER_APPROVED_TIME), rs.getBoolean(QueryConsts.ORDER_HAS_ARRIVED),
						rs.getBoolean(QueryConsts.ORDER_IS_APPROVED));
			}
		} catch (SQLException e) {
			response.setCode(404);
			response.setDescription("Order not found");
			response.setBody(null);
			return;
		}
		response.setCode(200);
		response.setDescription("Success in fetching order" + orderId);
		response.setBody(order);
	}

	/**
	 * Get payment approval for monthly budget
	 *
	 */
	public static void getPaymentApproval(Integer UserID, float amount, Response response) {
		PreparedStatement getAccount, getBusinessAccount, updateCurrentSpend;
		ResultSet rs;
		float monthBillingCelling, currentSpent;
		try {
			getAccount = EchoServer.con.prepareStatement("SELECT * FROM biteme.account WHERE UserID = ?");
			getAccount.setInt(1, UserID);
			getAccount.execute();
			rs = getAccount.getResultSet();
			if (rs.next()) {
				getBusinessAccount = EchoServer.con
						.prepareStatement("SELECT * FROM biteme.business_account WHERE UserID = ?");
				getBusinessAccount.setInt(1, UserID);
				getBusinessAccount.execute();
				rs.close();
				rs = getBusinessAccount.getResultSet();
				if (rs.next()) {
					monthBillingCelling = rs.getFloat(2);
					currentSpent = rs.getFloat(5);
					if (monthBillingCelling < currentSpent + amount) {
						throw new SQLException("Account" + UserID + "will exceed monthlry billing celling", "400", 400);
					} else {
						updateCurrentSpend = EchoServer.con.prepareStatement(
								"UPDATE biteme.business_account SET CurrentSpend = ? WHERE UserID = ?;");
						updateCurrentSpend.setFloat(1, currentSpent + amount);
						updateCurrentSpend.setInt(2, UserID);
						updateCurrentSpend.execute();
					}
				}

			} else {
				throw new SQLException("Account" + UserID + "is not found in table", "401", 401);
			}
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			response.setBody(null);
		}
		response.setCode(200);
		response.setDescription("Payment approved");
		response.setBody(null);
	}

	/**
	 * Updates a order in the DB with form data
	 *
	 */
	// TODO Is there anything to update in order as client??
	public static void updateOrder(Order order, Response response) {
		ResultSet rs;
		int orderID = 0, shipmentID, price;
		Options options = null;
		try {
			PreparedStatement updateOrder = EchoServer.con.prepareStatement(
					"UPDATE biteme.order AS orders SET OrderNum = ?, RestaurantID = ?, RestaurantName = ?, PhoneNumber = ?, required_time = ?, approved_time=?, hasArraived=?"
							+ " WHERE OrderNum = ? AND UserName = ?;");
			updateOrder.setInt(1, order.getOrderID());
			updateOrder.setInt(2, order.getRestaurantID());
			updateOrder.setString(3, order.getRestaurantName());
			updateOrder.setString(4, order.getUserName());
			updateOrder.setString(5, order.getTime_taken());
			updateOrder.setString(6, order.getPhone());
			updateOrder.setString(7, order.getType_of_order());
			updateOrder.setInt(8, order.getDiscount_for_early_order());
			updateOrder.setDouble(9, order.getCheck_out_price());
			updateOrder.setBoolean(10, order.isApproved());
			updateOrder.setString(11, order.getRequired_time());
			updateOrder.setString(12, order.getApproved_time());
			updateOrder.setBoolean(13, order.getHasArrived());
			updateOrder.execute();
			rs = updateOrder.getResultSet();
			if (rs.rowUpdated() == false) {
				throw new SQLException("couldn't update the order -> orderID: " + Integer.toString(order.getOrderID()));
			}
		} catch (SQLException e) {
			response.setCode(405);
			response.setDescription("Invalid input");
			return;
		}
		response.setCode(200);
		response.setDescription("Success updating order -> orderID: " + Integer.toString(order.getOrderID()));
	}

	/**
	 * Get item for a specific orderID
	 *
	 */
	// TODO Is there anything to update in order as client??
	public static void getItemsByOrderID(int orderID, Response response) {
		ResultSet rs1, rs2;
		ArrayList<Item> items = new ArrayList<>();
		ArrayList<Options> options = new ArrayList<>();
		Item itemTemp;
		Options optionsTemp;
		try {
			PreparedStatement getItems = EchoServer.con.prepareStatement(
					"SELECT items.*, itemsOrder.amount FROM items biteme.items, itemsOrder biteme.item_int_menu_in_order "
							+ "WHERE items.ItemID = itemsOrder.ItemID AND itemsOrder.OrderNum = ?;");
			getItems.setInt(1, orderID);
			getItems.execute();
			rs1 = getItems.getResultSet();
			while (rs1.next()) {
				itemTemp = new Item(rs1.getString(QueryConsts.ITEM_CATEGORY),
						rs1.getString(QueryConsts.ITEM_SUB_CATEGORY), rs1.getInt(QueryConsts.ITEM_ID),
						rs1.getInt(QueryConsts.ITEM_RES_ID), rs1.getString(QueryConsts.ITEM_NAME),
						rs1.getFloat(QueryConsts.ITEM_PRICE), rs1.getString(QueryConsts.ITEM_DESCRIPTION),
						rs1.getString(QueryConsts.ITEM_INGREDIENTS), null, rs1.getString(QueryConsts.ITEM_IMAGE),
						rs1.getInt(10));
				items.add(itemTemp);

				PreparedStatement getOptions = EchoServer.con
						.prepareStatement("SELECT * FROM biteme.optional_category WHERE itemID = ?");
				getOptions.setInt(1, itemTemp.getItemID());
				getOptions.execute();
				rs2 = getOptions.getResultSet();
				while (rs2.next()) {
					optionsTemp = new Options(rs2.getString(QueryConsts.OPTIONAL_TYPE),
							rs2.getString(QueryConsts.OPTIONAL_SPECIFY), rs2.getDouble(QueryConsts.OPTIONAL_PRICE),
							itemTemp.getItemID());

					options.add(optionsTemp);
				}
				itemTemp.setOptions((Options[]) options.toArray());
				options.clear();
				rs2.close();
			}
		} catch (SQLException e) {
			response.setCode(405);
			response.setDescription("Invalid input");
			return;
		}
		response.setCode(200);
		response.setDescription("Success fetchin items for order -> orderID: " + Integer.toString(orderID));
		response.setBody(items.toArray());
	}

	/**
	 * Updates a order as delivered
	 *
	 */
	public static void deliveredOrder(Order order, Response response) {
		PreparedStatement deliveredOrder;
		Duration timeElapsed;
		ResultSet rs;
		try {
			deliveredOrder = EchoServer.con.prepareStatement("UPDATE biteme.order AS orders SET hasArrived = 1"
					+ " WHERE orders.OrderNum = ? AND orders.UserName = ?;");
			deliveredOrder.setInt(1, order.getOrderID());
			deliveredOrder.setString(2, order.getUserName());
			deliveredOrder.setInt(3, order.getOrderID());
			deliveredOrder.setString(4, order.getUserName());
			deliveredOrder.execute();
			rs = deliveredOrder.getResultSet();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime now = LocalDateTime.now();
			PreparedStatement insertToDelivery = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.delivery (OrderNum, RestaurantID, Date, isLate) VALUES(?,?,?,?);");
			insertToDelivery.setInt(1, order.getOrderID());
			insertToDelivery.setInt(2, order.getRestaurantID());
			insertToDelivery.setString(3, now.format(formatter));
			timeElapsed = Duration.between(LocalTime.parse(order.getRequired_time()),
					LocalTime.parse(order.getTime_taken()));
			if (timeElapsed.toMinutes() < 120) {

				if (Duration.between(now, LocalDateTime.parse(order.getApproved_time())).toMinutes() > 60) {
					insertCredit(order, response);
					insertToDelivery.setBoolean(4, true);
					insertToDelivery.execute();
					return;
				}
			} else if (Duration.between(now, LocalDateTime.parse(order.getApproved_time())).toMinutes() > 20) {
				insertCredit(order, response);
				insertToDelivery.setBoolean(4, true);
				insertToDelivery.execute();
				return;
			}
			insertToDelivery.setBoolean(4, false);
			insertToDelivery.execute();
		} catch (SQLException e) {
			response.setCode(405);
			response.setDescription(
					"Couldn't approve order as delivered -> orderID: " + Integer.toString(order.getOrderID()));
			return;
		}
		response.setCode(200);
		response.setDescription(
				"Success in approving order as delivered -> orderID: " + Integer.toString(order.getOrderID()));
	}

	private static void insertCredit(Order order, Response response) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		PreparedStatement insertCredit, insertToLateDelivery;
		try {
			insertCredit = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.credit (UserName , AmountInCredit, RestaurantID) VALUES (?,?,?);");
			insertCredit.setString(1, order.getUserName());
			insertCredit.setDouble(2, (order.getCheck_out_price() * 0.5));
			insertCredit.setInt(3, order.getRestaurantID());
			insertCredit.execute();

			insertToLateDelivery = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.late_delivery (OrderNum, UserName, RestaurantID, Date) VALUES(?,?,?,?)");
			insertToLateDelivery.setInt(1, order.getOrderID());
			insertToLateDelivery.setString(2, order.getUserName());
			insertToLateDelivery.setInt(3, order.getRestaurantID());
			insertToLateDelivery.setString(4, now.format(formatter));
			insertToLateDelivery.execute();
		} catch (SQLException e) {
			response.setCode(405);
			response.setDescription("Couldn't insert credit to a late delivered order -> orderID: "
					+ Integer.toString(order.getOrderID()));
			return;
		}
		response.setCode(200);
		response.setDescription("Success in inserting credit to a late delivered order -> orderID: "
				+ Integer.toString(order.getOrderID()));
		return;
	}

	public static void updateCredit(Float AmountInCredit, int restaurantID, String UserName, Response response) {
		PreparedStatement updateCredit;
		try {
			if (AmountInCredit <= 0.1) {
				updateCredit = EchoServer.con
						.prepareStatement("DELETE FROM biteme.credit WHERE UserName = ? AND RestaurantID = ?;");
				updateCredit.setString(1, UserName);
				updateCredit.setInt(2, restaurantID);
				updateCredit.execute();
			} else {
				updateCredit = EchoServer.con.prepareStatement(
						"UPDATE biteme.credit SET AmountInCredit = ? WHERE UserName = ? AND RestaurantID = ?;");
				updateCredit.setDouble(1, AmountInCredit);
				updateCredit.setString(2, UserName);
				updateCredit.setInt(3, restaurantID);
				updateCredit.execute();
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Couldn't update credit for: " + UserName);
			return;
		}
		response.setCode(200);
		response.setDescription("Success in updating credit for: " + UserName);
		return;
	}
	
	private static String invoiceParser(Order order, int orderID) {
		PreparedStatement getAccount;
		ResultSet rs;
		StringBuilder invoice = new StringBuilder();
		String temp;
		Account account = null;
		try {
			getAccount = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.account WHERE UserName = ?;");
			getAccount.setString(1, order.getUserName());
			getAccount.execute();
			rs = getAccount.getResultSet();
			if (rs.next()) {
				account = new Account(rs.getInt(QueryConsts.ACCOUNT_USER_ID),
						rs.getString(QueryConsts.ACCOUNT_USER_NAME), rs.getString(QueryConsts.ACCOUNT_PASSWORD),
						rs.getString(QueryConsts.ACCOUNT_FIRST_NAME), rs.getString(QueryConsts.ACCOUNT_LAST_NAME),
						rs.getString(QueryConsts.ACCOUNT_EMAIL), rs.getString(QueryConsts.ACCOUNT_ROLE),
						rs.getString(QueryConsts.ACCOUNT_PHONE), rs.getString(QueryConsts.ACCOUNT_STATUS),
						rs.getBoolean(QueryConsts.ACCOUNT_IS_BUSINESS),
						rs.getInt(QueryConsts.ACCOUNT_BRANCH_MANAGER_ID), rs.getString(QueryConsts.ACCOUNT_AREA),
						rs.getInt(QueryConsts.ACCOUNT_DEBT), rs.getString(QueryConsts.ACCOUNT_W4C));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//TODO Use the item toString for it
		temp = "Hi!" + account.getFirstName() + "\n\n\n Thank you for your purchase from BiteMe\n INVOICE ID: " + Integer.toString(orderID);
		invoice.append(temp);
		invoice.append("\n\nYour Order inforamation:\n\n");
		invoice.append("Bill To: " + account.getEmail() + "\nOrder ID: " + Integer.toString(orderID));
		invoice.append("\n\nRestaurant Name: " + order.getRestaurantName() +"\nOrder Date: " + order.getTime_taken()
						+"\nType of Order: " + order.getType_of_order());
		invoice.append("Here is what you ordered: \n\n");
		invoice.append("Name\t\tprice\t\tQuantity\t\tOptions");
		for (Item item : order.getItems()) {
			invoice.append(item.toString());
		}
		invoice.append("Total ->: "+ Double.toString(order.getCheck_out_price()));
		if(order.getShippment() != null) {
			invoice.append("\nShipment information: \n\n");
			invoice.append("\nwork place / address: " + order.getShippment().getWork_place() + order.getShippment().getAddress());
			invoice.append("\ndelivery type: " + order.getShippment().getDelivery());
			invoice.append("\nreceiver name: " + order.getShippment().getReceiver_name());
			invoice.append("\nreceiver phone number: " + order.getShippment().getPhone());
		}
		return invoice.toString();
	}
}
