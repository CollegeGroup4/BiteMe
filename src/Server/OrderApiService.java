package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
					"INSERT INTO biteme.order (ResturantID, ResturantName, OrderTime, PhoneNumber, TypeOfOrder, Discount_for_early_order,"
							+ "Check_out_price, isBusiness, required_time)"
							+ " VALUES (?,?,?,?,?,?,?,?,?);SELECT last_insert_id();");
			postOrder.setInt(1, order.getRestaurantID());
			postOrder.setString(2, order.getRestaurantName());
			postOrder.setString(3, order.getTime_taken());
			postOrder.setString(4, order.getPhone());
			postOrder.setString(5, order.getType_of_order());
			postOrder.setInt(6, order.getDiscount_for_early_order());
			postOrder.setFloat(7, order.getCheck_out_price());
			postOrder.setBoolean(8, order.isBuisness());
			postOrder.setString(9, order.getRequired_time());
			postOrder.execute();
			rs = postOrder.getResultSet();
		} catch (SQLException e) {
			response.setCode(405);
			response.setDescription("Invalid input");
			return;
		}
		try {
			PreparedStatement postItem = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.item_in_menu_in_order (OrderNum, ItemID, Item_name, OptionalType, OptionalSpecify,"
							+ "Amount)"
							+ " VALUES (?,?,?,?,?,?);Select amount from biteme.item_in_menu_in_order WHRER OrderNum =?"
							+ "AND ItemID = ? AND OptionalType = ? AND OptionalSpecify = ?");
			postItem.setInt(1, rs.getInt(1));
			postItem.setInt(7, rs.getInt(1));
			orderID = rs.getInt(1);
			for (Item temp : order.getItems()) {
				postItem.setInt(2, temp.getItemID());
				postItem.setInt(6, temp.getAmount());
				postItem.setInt(8, temp.getItemID());
				postItem.setString(3, temp.getName());

				for (Options opt : temp.getOptions()) {
					postItem.setString(4, opt.getOption_category());
					postItem.setString(9, opt.getOption_category());
					postItem.setString(5, opt.getSpecify_option());
					postItem.setString(10, opt.getSpecify_option());
					options = opt;
					postItem.execute();
					rs = postItem.getResultSet();
				}
			}
		} catch (SQLException e) {

			// item already exist, +1 to amount
			if (e.getErrorCode() == 1062) {
				try {
					PreparedStatement updateOrder = EchoServer.con.prepareStatement(
							"UPDATE biteme.item_in_menu_in_order " + "SET Amount = ? WHERE OrderNum = ?"
									+ "ItemID = ? OptionalType = ? OptionalSpecify = ?");
					updateOrder.setInt(1, rs.getInt(1) + 1);
					updateOrder.setInt(2, orderID);
					updateOrder.setInt(3, options.getItemID());
					updateOrder.setString(4, options.getOption_category());
					updateOrder.setString(4, options.getSpecify_option());
					updateOrder.execute();
				} catch (SQLException ex) {
					System.out.println(ex.toString());
				}
			}
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

			} catch (SQLException ex) {
				if (ex.getErrorCode() == 1062) {
					try {
						PreparedStatement getShipID = EchoServer.con
								.prepareStatement("SELECT ShipmentID FROM biteme.shipment WHERE "
										+ "workPlace = ? AND Address = ? AND receiver_name = ? AND receiver_phone_number = ? "
										+ "AND deliveryType = ?;");
						getShipID.setString(1, order.getShippment().getWork_place());
						getShipID.setString(2, order.getShippment().getAddress());
						getShipID.setString(3, order.getShippment().getReceiver_name());
						getShipID.setString(4, order.getShippment().getPhone());
						getShipID.setString(5, order.getShippment().getDelivery());
						getShipID.execute();
						rs = getShipID.getResultSet();

						if (rs.next()) {
							shipmentID = rs.getInt(1);

							PreparedStatement setInShipment = EchoServer.con.prepareStatement(
									"INSERT INTO biteme.orders_in_shipment (ShipmentID, orderID, UserID, Price)"
											+ "VALUES (?,?,?,?);");
							setInShipment.setInt(1, rs.getInt(1));
							setInShipment.setInt(2, orderID);
							setInShipment.setInt(3, order.getUserID());
							setInShipment.setFloat(4, 25);

							// calculate shipping price
							PreparedStatement getAmountOfOrdersInShipment = EchoServer.con.prepareStatement(
									"SELECT orderID, UserID, COUNT(*) FROM biteme.orders_in_shipment"
											+ " WHERE ShipmentID = ? GROUP BY orderID, UserID;");

							getAmountOfOrdersInShipment.setInt(1, shipmentID);
							getAmountOfOrdersInShipment.execute();
							rs = getAmountOfOrdersInShipment.getResultSet();

							if (rs.next()) {
								if (rs.getInt(1) >= 3)
									price = 15;
								else if (rs.getInt(1) == 2)
									price = 20;
								else
									price = 25;

								PreparedStatement updatePriceOrderInShipment = EchoServer.con.prepareStatement(
										"UPDATE biteme.order_in_shipment " + "SET Price = ? WHERE ShipmentID = ?;");
								updatePriceOrderInShipment.setInt(1, price);
								updatePriceOrderInShipment.setInt(2, shipmentID);
								updatePriceOrderInShipment.execute();
							}
						}
					} catch (SQLException e) {
						// TODO: handle exception
					}
				}
			}
		}
	}

	/**
	 * Return all the orders
	 *
	 */
	public static void allOrders(String restaurantID, Response response) {
		PreparedStatement stmt;
		ArrayList<Order> orders = new ArrayList<>();
		Order order = null;
		try {
			stmt = EchoServer.con.prepareStatement("SELECT * FROM biteme.order WHERE RestaurantID = ?");
			stmt.setString(1, restaurantID);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				order = new Order(rs.getInt(QueryConsts.ORDER_NUM), rs.getInt(QueryConsts.RESTAURANT_ID),
						rs.getString(QueryConsts.RESTAURANT_NAME), rs.getString(QueryConsts.ORDER_TIME),
						rs.getFloat(QueryConsts.CHECK_OUT_PRICE), rs.getString(QueryConsts.REQUIRED_TIME),
						rs.getString(QueryConsts.TYPE_OF_ORDER), rs.getInt(QueryConsts.ACCOUNT_ID),
						rs.getString(QueryConsts.PHONE_NUM), rs.getInt(QueryConsts.DISCOUNT_FOR_EARLY_ORDER),
						rs.getBoolean(QueryConsts.IS_BUISNESS), null, null);
				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.setCode(200);
		response.setDescription("Success in fetching orders");
		response.setBody(orders.toArray());
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
				order = new Order(rs.getInt(QueryConsts.ORDER_NUM), rs.getInt(QueryConsts.RESTAURANT_ID),
						rs.getString(QueryConsts.RESTAURANT_NAME), rs.getString(QueryConsts.ORDER_TIME),
						rs.getFloat(QueryConsts.CHECK_OUT_PRICE), rs.getString(QueryConsts.REQUIRED_TIME),
						rs.getString(QueryConsts.TYPE_OF_ORDER), rs.getInt(QueryConsts.ACCOUNT_ID),
						rs.getString(QueryConsts.PHONE_NUM), rs.getInt(QueryConsts.DISCOUNT_FOR_EARLY_ORDER),
						rs.getBoolean(QueryConsts.IS_BUISNESS), null, null);
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
						throw new SQLException("Account" + UserID + "will exceed monthlry billing celling", "400",
								400);
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
	public static void updateOrderWithForm(int orderId, String address, String delivery, Response response) {
		PreparedStatement getOrder;
		ResultSet rs;
		
		try {
			 
			
			
			
		}catch(SQLException e) {
			
		}
		
		

	}

}