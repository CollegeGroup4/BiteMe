package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
			conn = DriverManager.getConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST", "root",
					"MoshPe2969999");
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}

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

	public static ArrayList<String> getOrders(Connection con1) {
		Statement stmt;
		Order order;
		int amountOfOrders = 0, i;
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


	public static ArrayList<String> getOrder(Connection con1, String orderNum) {
		PreparedStatement stmt;
		Order order;
		ArrayList<String> result = new ArrayList<String>();
		try {
			stmt = con1.prepareStatement("SELECT * FROM biteme.order WHERE OrderNumber = ?");
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

	public static void updateOrder(Connection con, Order order) {
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
	
	
}