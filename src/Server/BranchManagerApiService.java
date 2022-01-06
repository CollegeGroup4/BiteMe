package Server;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
//import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.aspose.pdf.Document;
import com.aspose.pdf.Image;
import com.aspose.pdf.Page;

import ClassForTests.DateTimeManager;
import ClassForTests.DbManager;
import ClassForTests.FileManager;
import ClassForTests.ImageManager;
import ClassForTests.PageManager;
import ClassForTests.iDateTimeManager;
import ClassForTests.iDbManager;
import ClassForTests.iFileManager;
import ClassForTests.iImageManager;
import ClassForTests.iPageManager;
import common.DBController;
import common.MyPhoto;
import common.Response;
import common.imageUtils;
import logic.Employer;
import logic.Restaurant;

/**
 * BiteMe
 *
 *
 *
 */
public class BranchManagerApiService {
	/**
	 * Get all the orders that in the in the same branch as the manager
	 *
	 * This can only be done by the logged in branch manager.
	 * 
	 * @param branchManagerID - id of the branch manager
	 * @param response        - response to get if success on the request and to
	 *                        return all of the order
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
	 * @param branchManagerID - id of the branch manager
	 * @param response        - response to get if success on the request and to
	 *                        return all of the restaurants
	 */
	public static void getBranchRestaurants(int branchManagerID, Response response) {
		PreparedStatement stmt;
		ResultSet rs;
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		try {
			stmt = EchoServer.con.prepareStatement("SELECT * FROM biteme.restaurant WHERE BranchManagerID = ?");
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
	 * @param BranchManagerID - id for the branch when
	 * @param response        - response for the request, return if success the
	 *                        reports
	 *
	 */
	public static void getBranchReports(int BranchManagerID, String type, Response response) {

		String imageDir = QueryConsts.FILE_PATH_REPORTS;
		File file = new File(imageDir);
		file.mkdir();
		String[] fileList = file.list();
		ArrayList<MyPhoto> selectedImages = new ArrayList<>();
//		ArrayList<String> mergedImages = new ArrayList<>();
		String[] FileNameCut;
		MyPhoto m;

		for (String fileName : fileList) {
			FileNameCut = fileName.split("_");
			if (!FileNameCut[0].equals("temp") && !FileNameCut[0].equals("quarter") && !FileNameCut[0].equals("pdf")
					&& FileNameCut[0].equals(type))
				if (FileNameCut[1].equals(String.valueOf(BranchManagerID))) {
					m = new MyPhoto(imageDir + fileName);
					imageUtils.sender(m);
					selectedImages.add(m);
				}
		}
		response.setBody(EchoServer.gson.toJson(selectedImages.toArray()));
		response.setDescription("Success fetching reports for branch manager id: " + BranchManagerID);
		response.setCode(200);
	}

	/**
	 * register new supplier
	 * 
	 * only can by done by branch manager in the same area as the supplier, TODO
	 * 
	 * @param userName   - the user name of
	 * @param userID
	 * @param role
	 * @param restaurant
	 * @param response
	 */
	public static void registerSupplier(String userName, int userID, String role, Restaurant restaurant,
			Response response) {
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = EchoServer.con.prepareStatement("SELECT * FROM " + "biteme.restaurant WHERE UserName = ?;");
			stmt.setString(1, userName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				throw new SQLException("UserName " + userName + " already has a restaurant", "400", 400);
			}
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			return;
		}
		try {
			stmt = EchoServer.con
					.prepareStatement("UPDATE biteme.account SET Role = ?, BranchManagerID = ?, Area = ? WHERE "
							+ "UserName = ? AND UserID = ?;");
			stmt.setString(1, role);
			stmt.setInt(2, restaurant.getBranchManagerID());
			stmt.setString(3, restaurant.getArea());
			stmt.setString(4, userName);
			stmt.setInt(5, userID);
			stmt.executeUpdate();
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Error in registering a new restaurant -> role: " + role + ", restaurantName "
					+ restaurant.getName());
			return;
		}
		try {
			stmt = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.restaurant (RestaurantName, IsApproved, BranchManagerID, Area, Image, "
							+ "UserName, Type, Address, Description) VALUES (?,?,?,?,?,?,?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, restaurant.getName());
			stmt.setBoolean(2, restaurant.isApproved());
			stmt.setInt(3, restaurant.getBranchManagerID());
			stmt.setString(4, restaurant.getArea());
			stmt.setString(5, restaurant.getPhoto());
			stmt.setString(6, userName);
			stmt.setString(7, restaurant.getType());
			stmt.setString(8, restaurant.getAddress());
			stmt.setString(9, restaurant.getDescription());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			int restaurantID = rs.getInt(1);
			if (restaurant.getRestaurantImage() != null) {
				String[] split = restaurant.getRestaurantImage().getFileName().split(".");
				String sufix = split[split.length - 1];
				String imageFileName = "restaurant_" + restaurantID + "." + sufix;
				restaurant.getRestaurantImage().setFileName(imageFileName);
				imageUtils.receiver(restaurant.getRestaurantImage(), QueryConsts.FILE_PATH_RESTAURANTS);
				stmt = EchoServer.con
						.prepareStatement("UPDATE biteme.restaurant SET Image = ? WHERE RestaurantNum = ?;");
				stmt.setString(1, imageFileName);
				stmt.setInt(2, restaurantID);
				stmt.executeUpdate();
			}
		} catch (SQLException ex) {
			response.setCode(400);
			response.setDescription("Fail in registering a new restaurant -> role: " + role + ", userName " + userName);
			try {
				stmt = EchoServer.con.prepareStatement(
						"UPDATE biteme.account SET Role = 'Not Assigned' WHERE " + "UserName = ? AND UserID = ?;");
				stmt.setString(1, userName);
				stmt.setInt(2, userID);
				stmt.executeUpdate();
			} catch (SQLException e) {
				response.setCode(400);
				response.setDescription("Error in registering a new restaurant -> role: " + role + ", restaurantName "
						+ restaurant.getName());
			}
			return;
		}
		response.setCode(200);
		response.setDescription("Success in registering a new restaurant -> role: " + role + ", restaurantName "
				+ restaurant.getName());
	}

	/**
	 * register new moderator
	 * 
	 * only can by done by branch manager in the same area as the supplier, TODO
	 * 
	 * @param userName   - the user name of
	 * @param userID
	 * @param role
	 * @param restaurant
	 * @param response
	 */
	public static void registerModerator(String userName, String supplierUserName, int userID, String role,
			Restaurant restaurant, Response response) {
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = EchoServer.con.prepareStatement("SELECT * FROM " + "biteme.restaurant WHERE ModeratorUserName = ?;");
			stmt.setString(1, userName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				throw new SQLException("UserName " + userName + " already has a restaurant", "400", 400);
			}
		} catch (SQLException e) {
			response.setCode(e.getErrorCode());
			response.setDescription(e.getMessage());
			return;
		}
		try {
			stmt = EchoServer.con
					.prepareStatement("UPDATE biteme.account SET Role = ?, BranchManagerID = ?, Area = ? WHERE "
							+ "UserName = ? AND UserID = ?;");
			stmt.setString(1, role);
			stmt.setInt(2, restaurant.getBranchManagerID());
			stmt.setString(3, restaurant.getArea());
			stmt.setString(4, userName);
			stmt.setInt(5, userID);
			stmt.executeUpdate();
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Error in registering a new restaurant -> role: " + role + ", restaurantName "
					+ restaurant.getName());
			return;
		}
		try {
			stmt = EchoServer.con.prepareStatement("UPDATE biteme.restaurant SET ModeratorUserName = ? WHERE "
					+ "UserName = ? AND RestaurantName = ?;");
			stmt.setString(1, userName);
			stmt.setString(2, supplierUserName);
			stmt.setString(3, restaurant.getName());
			stmt.executeUpdate();
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription("Error in registering a new restaurant -> role: " + role + ", restaurantName "
					+ restaurant.getName());
			return;
		}
		response.setCode(200);
		response.setDescription(
				"Success in registering a new moderator -> role: " + role + ", restaurantName " + restaurant.getName());
	}

	/**
	 * edit a resturant - mind that the supplier shcema isn&#x27;t finish
	 *
	 * This can only be done by the logged in branch manager.
	 *
	 */
	public static void editRestaurant(String userName, Restaurant restaurant, Response response) {
		PreparedStatement stmt;
		int rowsUpdated;
		try {
			stmt = EchoServer.con.prepareStatement(
					"UPDATE biteme.restaurant SET RestaurantName = ?, IsApproved = ?, BranchManagerID = ?, Area = ?, Image = ?, "
							+ "UserName = ?, Type = ?, Address = ?, Description = ? WHERE RestaurantNum = ? AND UserName = ?;");
			// set
			stmt.setString(1, restaurant.getName());
			stmt.setBoolean(2, restaurant.isApproved());
			stmt.setInt(3, restaurant.getBranchManagerID());
			stmt.setString(4, restaurant.getArea());
			stmt.setString(5, restaurant.getPhoto());
			stmt.setString(6, restaurant.getUserName());
			stmt.setString(7, restaurant.getType());
			stmt.setString(8, restaurant.getAddress());
			stmt.setString(9, restaurant.getDescription());
			// where
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
	 * delete a restaurant and reset the user role
	 * 
	 *
	 * This can only be done by the logged in branch manager.
	 *
	 */
	public static void deleteRestaurant(String userName, Restaurant restaurant, Response response) {
		PreparedStatement stmt;
		int rowsUpdated;
		ResultSet rs;
		try {
			stmt = EchoServer.con.prepareStatement(
					"DELETE FROM biteme.restaurant WHERE UserName = ? AND RestaurantNum = ? AND RestaurantName = ?;");
			// set
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
			// if the supplier has no restaurants anymore, reset his role
			rs.next();
			if (rs.getInt(1) == 0) {
				stmt = EchoServer.con.prepareStatement(
						"UPDATE biteme.account SET Role = 'Not Assigned', Status = 'active' WHERE UserName = ?;");
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

	/**
	 * generate sales report for each restaurant in biteme this method is
	 * inner-system method that work ones each month
	 */
	/**
	 * generate sales report for each restaurant in biteme this method is
	 * inner-system method that work ones each month
	 */

	public static void getReportForRestaurantSales() {
		ResultSet rs;
		ArrayList<Integer> b_m_id = new ArrayList<>();
		ArrayList<Integer> res_id = new ArrayList<>();
		String header = new String(), res;
		Double total = 0.0, orderPrice, tempPrice;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
		DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime orderTime;
		DefaultCategoryDataset dataset;
		HashMap<String, Double> dateToSales = new HashMap<>();
		Calendar c = Calendar.getInstance();
		int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		try {

			PreparedStatement getBMId = EchoServer.con
					.prepareStatement("SELECT BranchManagerID FROM biteme.branch_manager;");
			rs = getBMId.executeQuery();
			while (rs.next()) {
				b_m_id.add(rs.getInt(1));
			}
			for (Integer i : b_m_id) {
				rs.close();
				PreparedStatement getRestaurants = EchoServer.con.prepareStatement(
						"SELECT DISTINCT RestaurantNum FROM biteme.restaurant WHERE BranchManagerID = ?;");
				getRestaurants.setInt(1, i);
				rs = getRestaurants.executeQuery();
				res_id.clear();
				while (rs.next()) {
					res_id.add(rs.getInt(1));
				}

				for (Integer j : res_id) {
					dateToSales.clear();
					dataset = new DefaultCategoryDataset();

//					for (int k = 1; k < monthMaxDays; k++) {
//						if (k < 10)
//							dateToSales.put(String.format("0%s", k), 0.0);
//						else
//							dateToSales.put(String.format("%s", k), 0.0);
//					}

					PreparedStatement getOrders = EchoServer.con.prepareStatement(
							"SELECT OrderTime, Check_out_price, RestaurantID, RestaurantName FROM biteme.order WHERE RestaurantID = ?;");
					getOrders.setInt(1, j);
					rs.close();
					rs = getOrders.executeQuery();
					if (rs.next())
						res = rs.getString(3) + "_" + rs.getString(4);
					else
						continue;
					do {
						orderTime = LocalDateTime.parse(rs.getString(1), formatter);
						if (orderTime.format(year).equals(now.format(year)))
							if (orderTime.format(month).equals(now.format(month))) {
								orderPrice = rs.getDouble(2);
//								try {
//									dataset.incrementValue(orderPrice, res, orderTime.format(day));
//								} catch (Exception e) {
//									dataset.addValue(orderPrice, res, orderTime.format(day));
//								}
								if (dateToSales.containsKey(orderTime.format(day))) {
									tempPrice = dateToSales.get(orderTime.format(day));
									dateToSales.put(orderTime.format(day), tempPrice + orderPrice);
								} else
									dateToSales.put(orderTime.format(day), orderPrice);

								total += orderPrice;
							}
					} while (rs.next());

					for (String date : dateToSales.keySet()) {
						dataset.addValue(dateToSales.get(date), res, date);
					}

					header = "Income Report for restaurant id:" + j + " Total sales: " + String.format("%.2f$", total);
					total = 0.0;
					fromDataSetToReportImage(dataset, "Sales", i, now.format(year) + "_" + now.format(month), j, "$",
							"DayOfTheMonth: " + now.format(month), header);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */

	public static void getReportForRestaurantByCategory() {
		ResultSet rs, rs1;
		ArrayList<Integer> b_m_id = new ArrayList<>();
		ArrayList<Integer> res_id = new ArrayList<>();
		String header = new String();
		int total = 0;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime orderTime;
		DefaultCategoryDataset dataset;
		try {

			PreparedStatement getBMId = EchoServer.con
					.prepareStatement("SELECT BranchManagerID FROM biteme.branch_manager;");
			rs = getBMId.executeQuery();
			while (rs.next()) {
				b_m_id.add(rs.getInt(1));
			}
			for (Integer i : b_m_id) {
				rs.close();
				PreparedStatement getRestaurants = EchoServer.con.prepareStatement(
						"SELECT DISTINCT RestaurantNum FROM biteme.restaurant WHERE BranchManagerID = ?;");
				getRestaurants.setInt(1, i);
				rs = getRestaurants.executeQuery();
				res_id.clear();
				while (rs.next()) {
					res_id.add(rs.getInt(1));
				}

				for (Integer j : res_id) {
					rs.close();

					PreparedStatement getOrders = EchoServer.con.prepareStatement(
							"SELECT OrderTime, Check_out_price, RestaurantID, RestaurantName, OrderNum FROM biteme.order WHERE RestaurantID = ?;");
					getOrders.setInt(1, j);
					rs = getOrders.executeQuery();
					PreparedStatement getItems = EchoServer.con.prepareStatement(
							"SELECT DISTINCT IIM.Course, I.Category ,IIMIO.Amount FROM biteme.item_in_menu_in_order AS IIMIO, "
									+ "biteme.item_in_menu AS IIM, biteme.item AS I WHERE IIMIO.OrderNum = ? AND IIMIO.ItemID = IIM.ItemID AND IIM.RestaurantID = ? "
									+ "AND IIM.ItemID = I.ItemID AND I.RestaurantID = ?;");
					getItems.setInt(2, j);
					getItems.setInt(3, j);
					dataset = new DefaultCategoryDataset();
					while (rs.next()) {
						orderTime = LocalDateTime.parse(rs.getString(1), formatter);
						if (orderTime.format(year).equals(now.format(year)))
							if (orderTime.format(month).equals(now.format(month))) {

								getItems.setInt(1, rs.getInt(5));
								rs1 = getItems.executeQuery();

								while (rs1.next()) {
									try {
										dataset.incrementValue(rs1.getInt(3), rs1.getString(2), rs1.getString(1));
									} catch (Exception f) {
										dataset.addValue(rs1.getInt(3), rs1.getString(2), rs1.getString(1));
									}

									total += rs1.getInt(3);
								}
								rs1.close();
							}
					}
					header = "Items in orders Report for restaurant id: " + j + "\nTotal sold items: " + total;
					total = 0;
					fromDataSetToReportImage(dataset, "Items", i, now.format(year) + "_" + now.format(month), j,
							"Amount", "Course", header);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void getReportForRestaurantPerformence() {
		ResultSet rs, rs1;
		ArrayList<Integer> b_m_id = new ArrayList<>();
		String header = new String(), column = new String(), ResName = new String();
		int total = 0, ResID, late = 0;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
		DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime orderApproved;
		DefaultCategoryDataset dataset;
		LocalDateTime approveTime, prepTime;
		long time = 0, sec, min, hours;
		StringBuilder fulltime;
		try {

			PreparedStatement getBMId = EchoServer.con
					.prepareStatement("SELECT BranchManagerID FROM biteme.branch_manager;");
			rs = getBMId.executeQuery();
			while (rs.next()) {
				b_m_id.add(rs.getInt(1));
			}
			for (Integer i : b_m_id) {
				rs.close();
				PreparedStatement getRestaurants = EchoServer.con.prepareStatement(
						"SELECT DISTINCT RestaurantNum, RestaurantName FROM biteme.restaurant WHERE BranchManagerID = ?;");
				getRestaurants.setInt(1, i);
				rs = getRestaurants.executeQuery();
				dataset = new DefaultCategoryDataset();

				while (rs.next()) {
					ResID = rs.getInt(1);
					ResName = rs.getString(2);
					PreparedStatement getOrders = EchoServer.con.prepareStatement(
							"SELECT d.*, o.approve_time, o.prep_time FROM biteme.delivery AS d, biteme.order AS o "
									+ "WHERE d.RestaurantID = ? AND d.OrderNum = o.OrderNum;");
					getOrders.setInt(1, ResID);
					rs1 = getOrders.executeQuery();
					while (rs1.next()) {
						orderApproved = LocalDateTime.parse(rs1.getString(5), formatter);
						if (orderApproved.format(year).equals(now.format(year)))
							if (orderApproved.format(month).equals(now.format(month))) {

								// approveTime = LocalDateTime.parse(rs.getString(5), formatter);
								prepTime = LocalDateTime.parse(rs1.getString(6), formatter);

								time += orderApproved.until(prepTime, ChronoUnit.SECONDS);

								if (rs1.getBoolean(4)) {
									column = "Late";
									late++;
								} else
									column = "On time";
								try {
									dataset.incrementValue(1, column, "ID:" + ResID + ";" + ResName);
								} catch (Exception f) {
									dataset.addValue(1, column, "ID:" + ResID + ";" + ResName);
								}
								total += 1;
							}
					}
				}
				fulltime = new StringBuilder();
				if (total != 0) {
					time /= total;
					sec = time % 60;
					min = (time / 60) % 60;
					hours = (time / 60) / 60;

					fulltime.append((hours < 10) ? "0" + Long.toString(hours) : Long.toString(hours));
					fulltime.append(":");
					fulltime.append((min < 10) ? "0" + Long.toString(min) : Long.toString(min));
					fulltime.append(":");
					fulltime.append((sec < 10) ? "0" + sec : Long.toString(sec));
				} else {
					fulltime.append(0);
				}
				header = "Performence report for branch id:" + i + " total orders: " + total + "\n" + "Late: "
						+ String.format("%.2f%%", ((double) late / total) * 100.0) + ", Average preperation time: "
						+ fulltime.toString();
				total = 0;
				time = 0;
				late = 0;
				fromDataSetToReportImage(dataset, "Performence", i, now.format(year) + "_" + now.format(month), 0,
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
		String[] destDir = QueryConsts.FILE_PATH_REPORTS.split("\\\\");
		StringBuilder temp = new StringBuilder();
		File dir;
		for (int i = 0; i < destDir.length; i++) {
			for (int j = 0; j <= i; j++)
				temp.append(destDir[j] + "\\");
			dir = new File(temp.toString());
			dir.mkdir();
			temp = new StringBuilder();
		}
		path = QueryConsts.FILE_PATH_REPORTS + type + "_" + BranchManagerID + "_" + monthAndYear
				+ ((RestaurantID == 0) ? "" : "_" + RestaurantID) + ".png";
		try {
			ChartUtils.saveChartAsPNG(new File(path), barChart, 1000, 400);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Approve an Employer
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
		response.setCode(200);
		response.setDescription("Success in approving a new employer -> BusinessName: " + businessName);
	}

	/**
	 * Get All unapproved employer
	 *
	 * This can only be done by the logged in branch manager.
	 *
	 */
	public static void getAllUnapprovedEmployer(int branchManagerID, Response response) {
		PreparedStatement postEmployer;
		ResultSet rs;
		ArrayList<Employer> employers = new ArrayList<>();
		try {
			postEmployer = EchoServer.con
					.prepareStatement("SELECT * FROM biteme.employer WHERE BranchManagerID = ? AND isApproved = 0;");
			postEmployer.setInt(1, branchManagerID);
			rs = postEmployer.executeQuery();
			while (rs.next()) {
				employers.add(new Employer(rs.getString(QueryConsts.EMPLOYER_BUSINESS_NAME),
						rs.getBoolean(QueryConsts.EMPLOYER_IS_APPROVED), rs.getString(QueryConsts.EMPLOYER_HR_NAME),
						rs.getString(QueryConsts.EMPLOYER_HR_USER_NAME),
						rs.getInt(QueryConsts.EMPLOYER_BRANCH_MANAGER_ID)));
			}
		} catch (SQLException e) {
			response.setCode(400);
			response.setDescription(
					"Couldn't fetch all unapproved employers -> branchManagerID: " + Integer.toString(branchManagerID));
		}
		response.setCode(200);
		response.setDescription("Success in fetching all unapproved employers -> branchManagerID: "
				+ Integer.toString(branchManagerID));
		response.setBody(EchoServer.gson.toJson(employers.toArray()));
	}

	// TODO getter setter
	private static iDbManager idbManager = new DbManager();
	private static iFileManager ifileManager = new FileManager();

	private static LocalDateTime now = LocalDateTime.now();

	private static Document doc = new Document();

	private static Page page;

	private static Image image1 = new Image();

	private static FileInputStream fs;

	public static void setIdbManager(iDbManager idbManager) {
		BranchManagerApiService.idbManager = idbManager;
	}

	public static void setIfileManager(iFileManager ifileManager) {
		BranchManagerApiService.ifileManager = ifileManager;
	}

	public static void setPage(Page page) {
		BranchManagerApiService.page = page;
	}

	public static void setImage1(Image image1) {
		BranchManagerApiService.image1 = image1;
	}

	public static void setFs(FileInputStream fs) {
		BranchManagerApiService.fs = fs;
	}

	// For mock!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static void setDocument(Document doc) {
		BranchManagerApiService.doc = doc;
	}

	// For mock!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static void setLocalDateTime(LocalDateTime now) {
		BranchManagerApiService.now = now;
	}

	/**
	 * Approve a business
	 *
	 * This can only be done by the logged in branch manager.
	 *
	 */
	public static void sendPdfToCEO(int BranchManagerID) {

		// TODO setup to sql
		idbManager.setUp();

		// TODO mock?
		DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");

		String rep_month;

		int curMonth, repMonth;

		// Access image files in the folder
		String imageDir = QueryConsts.FILE_PATH_REPORTS;
		// TODO
		String[] fileList = ifileManager.getFileListFromDir(imageDir);

		ArrayList<String> selectedImages = new ArrayList<>();

		ArrayList<String> mergedImages = new ArrayList<>();

		String[] FileNameCut;

		for (String fileName : fileList) {

			FileNameCut = fileName.split("_");
			if (!FileNameCut[0].equals("temp") && !FileNameCut[0].equals("quarter") && !FileNameCut[0].equals("pdf"))
				if (FileNameCut[1].equals(String.valueOf(BranchManagerID))) {

					curMonth = Integer.parseInt(now.format(month));

					if (FileNameCut[0].equals("Performence"))
						repMonth = Integer.parseInt(FileNameCut[3].split("\\.")[0]);
					else
						repMonth = Integer.parseInt(FileNameCut[3]);

					if (curMonth - repMonth < 3 && curMonth - repMonth >= 0)
						selectedImages.add(fileName);
				}
		}

		boolean flag = false;

		String last = "";

		for (String temp : selectedImages) {
			if (!flag) {
				last = temp;
				flag = true;
			} else {
				// TODO
				mergedImages.add(mergeTwoImages(last, temp));

				flag = false;
				last = "";
			}
		}

		if (!last.equals(""))
			mergedImages.add(QueryConsts.FILE_PATH_REPORTS + last);

		for (String fileName : mergedImages) {

			// TODO mock!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			FileInputStream fs = null;
			try {
				fs = new FileInputStream(fileName);
			} catch (FileNotFoundException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}

			// Add a page to pages collection of document
			Page page = doc.getPages().add();
			// Set margins so image will fit, etc.
			page.getPageInfo().getMargin().setBottom(0);
			page.getPageInfo().getMargin().setTop(0);
			page.getPageInfo().getMargin().setLeft(0);
			page.getPageInfo().getMargin().setRight(0);
			page.setCropBox(new com.aspose.pdf.Rectangle(0, 0, 800, 600));

			// TODO mock?
			image1.setImageStream(fs);

			// TODO
			page.getParagraphs().add(image1);

			// Set the image file stream

		}
		int quarter = Integer.parseInt(now.format(month));
		if (quarter > 3) {
			if (quarter > 6) {
				if (quarter > 9)
					quarter = 4;
				else
					quarter = 3;
			} else
				quarter = 2;
		} else
			quarter = 1;

		// Save resultant PDF file
		doc.save(QueryConsts.FILE_PATH_REPORTS + "pdf_" + quarter + "_" + BranchManagerID + ".pdf");

		String absPath = System.getProperty("user.dir");
		absPath = absPath + "\\" + QueryConsts.FILE_PATH_REPORTS + "pdf_" + quarter + "_" + BranchManagerID + ".pdf";
		System.out.println(absPath);
		String email = null;

		// TODO
		email = idbManager.getCeoEmail(email);

		// sendCEO(email, absPath);
	}

	public static String mergeTwoImages(String first, String second) {
		try {
			// Loading an existing document
			
			BufferedImage img1 = ImageIO.read(new File(QueryConsts.FILE_PATH_REPORTS + first));
			BufferedImage img2 = ImageIO.read(new File(QueryConsts.FILE_PATH_REPORTS + second));
			BufferedImage joinedImg = joinBufferedImage(img1, img2);
			boolean success = ImageIO.write(joinedImg, "png", new File(
					QueryConsts.FILE_PATH_REPORTS + "temp_" + 7 * first.length() + 31 * second.length() + ".png"));
			System.out.println("saved success? " + success);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return QueryConsts.FILE_PATH_REPORTS + "temp_" + 7 * first.length() + 31 * second.length() + ".png";
	}

	public static BufferedImage joinBufferedImage(BufferedImage img1, BufferedImage img2) {

		// do some calculate first
		int offset = 5;
		int wid = Math.max(img1.getWidth(), img2.getWidth()) + offset;
		int height = img1.getHeight() + img2.getHeight() + offset;
		// int wid = img1.getWidth() + img2.getWidth() + offset;
		// int height = Math.max(img1.getHeight(), img2.getHeight()) + offset;
		// create a new buffer and draw two image into the new image
		BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		// fill background
		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, wid, height);
		// draw image
		g2.setColor(oldColor);
		g2.drawImage(img1, null, 0, 0);
		g2.drawImage(img2, null, 0, img1.getHeight() + offset);
		g2.dispose();
		return newImage;
	}

//	public static void sendCEO(String to, String filename) {
//		final String user = System.getenv("MyEmail");
//		;// change accordingly
//		final String password = System.getenv("MyPassEmail");// change accordingly
//
//		// 1) get the session object
//		Properties properties = System.getProperties();
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.starttls.enable", "true");
//		properties.put("mail.smtp.host", "smtp.gmail.com");
//		properties.put("mail.smtp.port", "587");
//
//		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(user, password);
//			}
//		});
//
//		try {
//			MimeMessage message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(user));
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//			message.setSubject("A PDF File with A quarter report");
//
//			BodyPart messageBodyPart1 = new MimeBodyPart();
//			messageBodyPart1.setText("This is message body");
//
//			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//
//			DataSource source = new FileDataSource(filename);
//			messageBodyPart2.setDataHandler(new DataHandler(source));
//			messageBodyPart2.setFileName(filename);
//
//			Multipart multipart = new MimeMultipart();
//			multipart.addBodyPart(messageBodyPart1);
//			multipart.addBodyPart(messageBodyPart2);
//
//			message.setContent(multipart);
//
//			Transport.send(message);
//
//			System.out.println("message sent....");
//		} catch (MessagingException ex) {
//			ex.printStackTrace();
//		}
//	}
}
