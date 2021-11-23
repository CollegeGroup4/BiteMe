package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import logic.Order;

public class DBController {

	public static final int ORDER_NUM = 1;
	public static final int RESTURANT = 2;
	public static final int ORDER_TIME = 3;
	public static final int PHONE_NUM = 4;
	public static final int TYPE_OF_ORDER = 5;
	public static final int ORDER_ADDRESS = 6;

	public static String[] msg;

	@SuppressWarnings("deprecation")
	public static Connection getMySQLConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST", "root", "Tal4EvEr");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}

	/*
	 * Function: insertOrder insert order to DataBase
	 */
	public static void insertOrder(Connection con, Order order) {
		try {
			PreparedStatement postOrder = con.prepareStatement(
					"INSERT INTO biteme.order (Resturant, OrderTime, PhoneNumber, TypeOfOrder, OrderAddress)"
							+ " VALUES (?,?,?,?,?);");
			postOrder.setString(1, order.getResturant());
			postOrder.setTime(2, order.getOrderTime());
			postOrder.setString(3, order.getPhoneNumber());
			postOrder.setString(4, order.getOrderType());
			postOrder.setString(5, order.getOrderAddress());
			postOrder.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Function: getOrders return all the orders in DB. when the DB is empty -
	 * return ArrayList of strings with {"GETALL", "ORDER", "ERROR"} else,
	 * {"GETALL", "ORDER", *List of orders*} so that the list of orders is toString
	 * of every order in each place
	 */
	public static ArrayList<String> getOrders(Connection con1) {
		Statement stmt;
		Order order;
		int amountOfOrders = 0;
		ArrayList<String> results = new ArrayList<String>();
		results.add("GETALL");
		results.add("ORDER");
		try {
			stmt = con1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("SELECT * FROM biteme.order");
			while (rs.next()) {
				order = new Order(rs.getString(RESTURANT), rs.getString(ORDER_ADDRESS), rs.getString(PHONE_NUM),
						rs.getTime(ORDER_TIME), rs.getString(TYPE_OF_ORDER));
				order.setOrderNum(rs.getInt(ORDER_NUM));
				results.add(order.toString());
				amountOfOrders++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (amountOfOrders == 0) {
			results.add("ERROR");
		}
		return results;
	}

	/*
	 * Function: getOrder return single order that agree in the given OrderID if no
	 * order was found, return ArrayList of {"GETONE", "ORDER", "ERROR"} else,
	 * {"GETALL", "ORDER", Order} Order is toString
	 */
	public static ArrayList<String> getOrder(Connection con1, String orderNum) {
		PreparedStatement stmt;
		Order order;
		ArrayList<String> result = new ArrayList<String>();
		try {
			stmt = con1.prepareStatement("SELECT * FROM biteme.order WHERE OrderNum = ?");
			stmt.setString(1, orderNum);
			result.add("GETONE");
			result.add("ORDER");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				order = new Order(rs.getString(RESTURANT), rs.getString(ORDER_ADDRESS), rs.getString(PHONE_NUM),
						rs.getTime(ORDER_TIME), rs.getString(TYPE_OF_ORDER));
				order.setOrderNum(rs.getInt(ORDER_NUM));
				result.add(order.toString());
			} else {
				result.add("ERROR");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * Function: updateOrder The order update the fields: OrderAddress and
	 * TypeOfOrder from the order in DB with the same Order ID of the given order
	 * return 0 if the order isn't in DB, 1 if the order was updated
	 */
	public static int updateOrder(Connection con, Order order) {
		PreparedStatement updateOrder;
		int flag = 0;
		try {
			updateOrder = con.prepareStatement(
					"UPDATE biteme.orders " + "SET OrderAddress = ? , TypeOfOrder = ? WHERE OrderNumber = ?");
			updateOrder.setString(1, order.getOrderAddress());
			updateOrder.setString(2, order.getOrderType());
			updateOrder.setInt(2, order.getOrderNum());
			flag = updateOrder.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static int getOrderNum(Connection con1, Order orderToSearch) {
		PreparedStatement stmt;
		int orderNumber = -1;
		try {
			stmt = con1.prepareStatement("SELECT OrderNum FROM biteme.order WHERE Resturant = ? AND "
					+ "OrderTime = ? AND PhoneNumber = ? AND TypeOfOrder = ? AND OrderAddress = ?");
			stmt.setString(1, orderToSearch.getResturant());
			stmt.setTime(2, orderToSearch.getOrderTime());
			stmt.setString(3, orderToSearch.getPhoneNumber());
			stmt.setString(4, orderToSearch.getOrderType());
			stmt.setString(5, orderToSearch.getOrderAddress());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				orderNumber = rs.getInt(1);
			} else {
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderNumber;
	}

	public static int getNumberOfOrders(Connection con) {
		Statement stmt;
		int amount = 0;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("SELECT * FROM biteme.order");
			while (rs.next()) {
				amount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amount;
	}
	
<<<<<<< HEAD
	
}
=======
	public static void clearTable(Connection con) {
		try {
			PreparedStatement stmt = con.prepareStatement("DELETE FROM biteme.order");
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
>>>>>>> master
