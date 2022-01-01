package Server;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import common.Response;
import logic.*;

/**
 * BiteMe
 *
 * <p>
 * No description provided (generated by Swagger Codegen
 * https://github.com/swagger-api/swagger-codegen)
 *
 */
public class BranchManagerApiService {
	/**
	 * Get all the reports for the branch
	 *
	 * This can only be done by the logged in branch manager.
	 *
	 */
	public static void getBranchOrders(int branchManagerID, Response response) {
		PreparedStatement stmt;
		ResultSet rs;
		Map<String, String> ordersByRestaurantID = new HashMap<>();
		int restaurantID;
		try {
			stmt = EchoServer.con.prepareStatement("SELECT * FROM biteme.restaurant WHERE BranchManagerID = ?");
			stmt.setInt(1, branchManagerID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				restaurantID = rs.getInt(1);
				OrderApiService.AllOrdersByRestaurantID(restaurantID, response);
				ordersByRestaurantID.put(Integer.toString(restaurantID), (String) response.getBody());
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription(
					"Fail in fetching all branch orders -> branch manager id: " + Integer.toString(branchManagerID));
		}
		response.setCode(200);
		response.setDescription(
				"Success in fetching all branch orders -> branch manager id: " + Integer.toString(branchManagerID));
		response.setBody(EchoServer.gson.toJson(ordersByRestaurantID));
	}

	/**
	 * Get all the restaurants for the branch
	 *
	 * This can only be done by the logged in branch manager.
	 *
	 */
	public static void getBranchRestaurants(int branchManagerID, Response response) {
		PreparedStatement stmt;
		ResultSet rs;
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		try {
			stmt = EchoServer.con.prepareStatement(
					"SELECT * FROM biteme.restaurant WHERE BranchManagerID = ?");
			stmt.setInt(1, branchManagerID);
			rs = stmt.executeQuery();
			while (rs.next()) {
				restaurants.add(new Restaurant(rs.getInt(QueryConsts.RESTAURANT_ID),
						rs.getBoolean(QueryConsts.RESTAURANT_IS_APPROVED),
						rs.getInt(QueryConsts.RESTAURANT_BRANCH_MANAGER_ID), rs.getString(QueryConsts.RESTAURANT_NAME),
						rs.getString(QueryConsts.RESTAURANT_AREA), rs.getString(QueryConsts.RESTAURANT_TYPE),
						rs.getString(QueryConsts.RESTAURANT_USER_NAME), rs.getString(QueryConsts.RESTAURANT_PHOTO),
						rs.getString(QueryConsts.RESTAURANT_ADDRESS),
						rs.getString(QueryConsts.RESTAURANT_DESCRIPTION)));
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Fail in fetching all branch restaurants -> branch manager id: "
					+ Integer.toString(branchManagerID));
		}
		response.setCode(200);
		response.setDescription("Success in fetching all branch restaurants -> branch manager id: "
				+ Integer.toString(branchManagerID));
		response.setBody(EchoServer.gson.toJson(restaurants.toArray()));
	}

	/**
	 * Get the reports for the branch
	 *
	 * This can only be done by the logged in branch manager.
	 *
	 */
	public static void getBranchReports(List<String> reportType) {
		// TODO: Implement...

	}

	/**
	 * Register a resturant - mind that the supplier shcema isn&#x27;t finish
	 *
	 * This can only be done by the logged in branch manager.
	 *
	 */
	public static void registerSupplierModerator(String userName, int userID,String role, Restaurant restaurant,Response response) {
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = EchoServer.con.prepareStatement("SELECT * FROM "
					+ "biteme.restaurant WHERE UserName = ? AND RestaurantName = ?;");
			stmt.setString(1, userName);
			stmt.setString(2, restaurant.getName());
			rs = stmt.executeQuery();
			if (rs.next()) {
				throw new SQLException("Restaurant " + restaurant.getName() + " is already exist", "400", 400);
			}
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			return;
		}
		try {
			stmt = EchoServer.con.prepareStatement("UPDATE biteme.account SET Role = ? WHERE "
					+ "UserName = ? AND UserID = ?;");
			stmt.setString(1, role);
			stmt.setString(2, userName);
			stmt.setInt(3, userID);
			stmt.executeUpdate();
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("User "+userName+"does Not found");	
			return;
		}
		try {
			stmt = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.restaurant (RestaurantName, IsApproved, BranchManagerID, Area, Image, "
							+ "UserName, Type, Address, Description) VALUES (?,?,?,?,?,?,?,?,?);");
			stmt.setString(1, restaurant.getName());
			stmt.setBoolean(2, restaurant.isApproved());
			stmt.setInt(3, restaurant.getBranchManagerID());
			stmt.setString(4, restaurant.getArea());
			stmt.setString(5, restaurant.getPhoto());
			stmt.setString(6, restaurant.getUserName());
			stmt.setString(7, restaurant.getType());
			stmt.setString(8, restaurant.getAddress());
			stmt.setString(9, restaurant.getDescription());
			stmt.executeUpdate();
		} catch (SQLException ex) {
			response.setCode(400);
			response.setDescription("Fail in registering a new restaurant -> role: " + role + ", userName" + userName);
			try {
				stmt = EchoServer.con.prepareStatement("UPDATE biteme.account SET Role = 'Not Assigned' WHERE "
						+ "UserName = ? AND UserID = ?;");
				stmt.setString(1, userName);
				stmt.setInt(2, userID);
				stmt.executeUpdate();
			} catch (SQLException e) {
			}
			return;
		}
		response.setCode(200);
		response.setDescription("Success in registering a new restaurant -> role: " + role + ", restaurantName " + restaurant.getName());
	}
	
	/**
	 * edit a resturant - mind that the supplier shcema isn&#x27;t finish
	 *
	 * This can only be done by the logged in branch manager.
	 *
	 */
	public static void editRestaurant(String userName, Restaurant restaurant,Response response) {
		PreparedStatement stmt;
		int rowsUpdated;
		try {
			stmt = EchoServer.con.prepareStatement(
					"UPDATE biteme.restaurant SET RestaurantName = ?, IsApproved = ?, BranchManagerID = ?, Area = ?, Image = ?, "
							+ "UserName = ?, Type = ?, Address = ?, Description = ? WHERE RestaurantNum = ? AND UserName = ?;");
			//set
			stmt.setString(1, restaurant.getName());
			stmt.setBoolean(2, restaurant.isApproved());
			stmt.setInt(3, restaurant.getBranchManagerID());
			stmt.setString(4, restaurant.getArea());
			stmt.setString(5, restaurant.getPhoto());
			stmt.setString(6, restaurant.getUserName());
			stmt.setString(7, restaurant.getType());
			stmt.setString(8, restaurant.getAddress());
			stmt.setString(9, restaurant.getDescription());
			//where
			stmt.setInt(10, restaurant.getId());
			stmt.setString(11, userName);			
			rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated == 0) {
				throw new SQLException("Couldn't update " + restaurant.getName(), "400", 400);
			}
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setBody(e.getMessage());
			return;
		}
		response.setCode(200);
		response.setDescription("Success in updating a restaurant -> restaurantName: " + restaurant.getName());
	}
	
	/**
	 * delete a resturant and reset the user role- mind that the supplier shcema isn&#x27;t finish
	 *
	 * This can only be done by the logged in branch manager.
	 *
	 */
	public static void deleteRestaurant(String userName, Restaurant restaurant,Response response) {
		PreparedStatement stmt;
		int rowsUpdated;
		ResultSet rs;
		try {
			stmt = EchoServer.con
					.prepareStatement("DELETE FROM biteme.restaurant WHERE UserName = ? AND RestaurantNum = ? AND RestaurantName = ?;");
			//set
			stmt.setString(1, userName);
			stmt.setInt(2, restaurant.getId());
			stmt.setString(3, restaurant.getName());
			rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated == 0) {
				throw new SQLException("Couldn't delete " + restaurant.getName(), "400", 400);
			}
			stmt = EchoServer.con
					.prepareStatement("SELECT COUNT(RestaurantNum) FROM biteme.restaurant WHERE UserName = ?;");
			stmt.setString(1, userName);
			rs = stmt.executeQuery();
			//if the supplier has no restaurants anymore, reset his role
			rs.next();
			if(rs.getInt(1) == 0) {
				stmt = EchoServer.con
						.prepareStatement("UPDATE biteme.account SET Role = 'Not Assigned', Status = 'active' WHERE UserName = ?;");
				stmt.setString(1, userName);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			return;
		}
		response.setCode(200);
		response.setDescription("Success in deleting a restaurant -> restaurantName: " + restaurant.getName());
	}

	public static void getReportForRestaurantSales() {
		ResultSet rs;
		ArrayList<Integer> b_m_id = new ArrayList<>();
		ArrayList<Integer> res_id = new ArrayList<>();
		String header = new String();
		Double total = 0.0;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
		DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime orderTime;
		DefaultCategoryDataset dataset;
		try {

			PreparedStatement getBMId = EchoServer.con
					.prepareStatement("SELECT BranchManagerID FROM biteme.branch_manager;");
			getBMId.executeQuery();
			rs = getBMId.getResultSet();
			while (rs.next()) {
				b_m_id.add(rs.getInt(1));
			}
			for (Integer i : b_m_id) {
				rs.close();
				PreparedStatement getRestaurants = EchoServer.con.prepareStatement(
						"SELECT DISTINCT RestaurantID FROM biteme.restaurant WHERE BranchManagerID = ?;");
				getRestaurants.setInt(1, i);
				getRestaurants.executeQuery();
				rs = getRestaurants.getResultSet();
				while (rs.next()) {
					res_id.add(rs.getInt(1));
				}

				for (Integer j : res_id) {
					rs.close();
					dataset = new DefaultCategoryDataset();
					PreparedStatement getOrders = EchoServer.con.prepareStatement(
							"SELECT OrderTime, Check_out_price, RestaurantID, RestaurantName FROM biteme.order WHERE RestaurantID = ?;");
					getOrders.setInt(1, j);
					getOrders.executeQuery();
					rs = getOrders.getResultSet();

					while (rs.next()) {
						orderTime = LocalDateTime.parse(rs.getString(1), formatter);
						if (orderTime.format(year) == now.format(year))
							if (orderTime.format(month) == now.format(month)) {
								dataset.addValue(rs.getDouble(2), rs.getString(3) + rs.getString(4),
										orderTime.format(day));
								total += rs.getDouble(2);
							}
					}
					header = "Revenue Report for restaurant id:" + j + " Total sales: " + total;
					total = 0.0;
					fromDataSetToReportImage(dataset, "Sales", i, now.format(year) + "/" + now.format(month), j, "$",
							"DayOfTheMonth: " + now.format(month), header);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void getReportForRestaurantByCategory() {
		ResultSet rs, rs1;
		ArrayList<Integer> b_m_id = new ArrayList<>();
		ArrayList<Integer> res_id = new ArrayList<>();
		String header = new String();
		int total = 0;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime orderTime;
		DefaultCategoryDataset dataset;
		try {

			PreparedStatement getBMId = EchoServer.con
					.prepareStatement("SELECT BranchManagerID FROM biteme.branch_manager;");
			getBMId.executeQuery();
			rs = getBMId.getResultSet();
			while (rs.next()) {
				b_m_id.add(rs.getInt(1));
			}
			for (Integer i : b_m_id) {
				rs.close();
				PreparedStatement getRestaurants = EchoServer.con.prepareStatement(
						"SELECT DISTINCT RestaurantID FROM biteme.restaurant WHERE BranchManagerID = ?;");
				getRestaurants.setInt(1, i);
				getRestaurants.executeQuery();
				rs = getRestaurants.getResultSet();
				while (rs.next()) {
					res_id.add(rs.getInt(1));
				}

				for (Integer j : res_id) {
					rs.close();
					dataset = new DefaultCategoryDataset();
					PreparedStatement getOrders = EchoServer.con.prepareStatement(
							"SELECT OrderTime, Check_out_price, RestaurantID, RestaurantName, OrderNum FROM biteme.order WHERE RestaurantID = ?;");
					getOrders.setInt(1, j);
					getOrders.executeQuery();
					rs = getOrders.getResultSet();
					PreparedStatement getItems = EchoServer.con
							.prepareStatement("SELECT IIM.Course, I.Category FROM biteme.item_in_menu_in_order IIMIO, "
									+ "biteme.item_in_menu IIM, biteme.item I WHERE IIMIO.OrderNum = ? AND IIMIO.ItemID = IIM.ItemID AND IIM.RestaurantID = ?"
									+ "AND IIM.ItemID = I.ItemID AND I.RestaurantID = ?;");
					getItems.setInt(2, j);
					getItems.setInt(3, j);
					while (rs.next()) {
						orderTime = LocalDateTime.parse(rs.getString(1), formatter);
						if (orderTime.format(year) == now.format(year))
							if (orderTime.format(month) == now.format(month)) {

								getItems.setInt(1, rs.getInt(5));
								getItems.executeQuery();
								rs1 = getItems.getResultSet();
								while (rs1.next()) {
									dataset.addValue(1, rs1.getString(2), rs1.getString(1));
									total += 1;
								}
								rs1.close();
							}
					}
					header = "Items in orders Report for restaurant id:" + j + " Total sales: " + total;
					total = 0;
					fromDataSetToReportImage(dataset, "Items", i, now.format(year) + "/" + now.format(month), j, "Amount",
							"Course", header);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void getReportForRestaurantPerformence() {
		ResultSet rs, rs1;
		ArrayList<Integer> b_m_id = new ArrayList<>();
		String header = new String(), column = new String(), ResName = new String();
		int total = 0, ResID;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime orderApproved;
		DefaultCategoryDataset dataset;
		try {

			PreparedStatement getBMId = EchoServer.con
					.prepareStatement("SELECT BranchManagerID FROM biteme.branch_manager;");
			getBMId.executeQuery();
			rs = getBMId.getResultSet();
			while (rs.next()) {
				b_m_id.add(rs.getInt(1));
			}
			for (Integer i : b_m_id) {
				rs.close();
				PreparedStatement getRestaurants = EchoServer.con.prepareStatement(
						"SELECT DISTINCT RestaurantID, RestaurantName FROM biteme.restaurant WHERE BranchManagerID = ?;");
				getRestaurants.setInt(1, i);
				getRestaurants.executeQuery();
				rs = getRestaurants.getResultSet();
				dataset = new DefaultCategoryDataset();

				while (rs.next()) {
					ResID = rs.getInt(1);
					ResName = rs.getString(2);
					PreparedStatement getOrders = EchoServer.con
							.prepareStatement("SELECT * FROM biteme.delivery WHERE RestaurantID = ?;");
					getOrders.setInt(1, ResID);
					getOrders.executeQuery();
					rs1 = getOrders.getResultSet();
					while (rs1.next()) {
						orderApproved = LocalDateTime.parse(rs.getString(3), formatter);
						if (orderApproved.format(year) == now.format(year))
							if (orderApproved.format(month) == now.format(month)) {
								if (rs.getBoolean(4))
									column = "Late";
								else
									column = "On time";
								dataset.addValue(1, column, "ID:" + ResID + ";" + ResName);
								total += 1;
							}
					}

				}
				header = "Performence report for branch id:" + i + " total orders: " + total;
				total = 0;
				fromDataSetToReportImage(dataset, "Performence", i, now.format(year) + "/" + now.format(month), 0,
						"Amount of orders", "Restaurants", header);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void fromDataSetToReportImage(DefaultCategoryDataset dataset, String type, int BranchManagerID,
			String monthAndYear, int RestaurantID, String y, String x, String header) {
		String path = new String();
		JFreeChart barChart = ChartFactory.createBarChart(header, x, y, dataset, PlotOrientation.VERTICAL, true, true,
				false);
		path = ".\\savedReports" + type + BranchManagerID + monthAndYear + RestaurantID;
		try {
			ChartUtils.saveChartAsPNG(new File(path), barChart, 600, 400);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * Approve a business
	 *
	 * This can only be done by the logged in branch manager.
	 *
	 */
	public static void approveEmployer(String businessName, int branchManagerID, Response response) {
		PreparedStatement postEmployer;		
		try {
			postEmployer = EchoServer.con.prepareStatement(
					"UPDATE biteme.employer SET isApproved = 1 WHERE businessName = ? AND BranchManagerID = ?");
			postEmployer.setString(1, businessName);
			postEmployer.setInt(2, branchManagerID);
			postEmployer.executeUpdate();
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Couldn't approve a new employer -> BusinessName: " + businessName);
		}
		response.setDescription("Success in approving a new employer -> BusinessName: " + businessName);
	}
}
