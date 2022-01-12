package Server;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import common.DBController;
import common.MyPhoto;
import common.Response;
import common.imageUtils;
import logic.Order;

public class CEOApiService {

	private static ArrayList<Order> getAllOrders(LocalDateTime from, LocalDateTime to, int BranchManagerID) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ArrayList<Order> orders = new ArrayList<>();
		PreparedStatement getOrders;
		Order order;
		LocalDateTime orderTimeTaken;
		ResultSet rs;
		try {
			getOrders = EchoServer.con.prepareStatement("SELECT * FROM biteme.order AS o, biteme.restaurant AS r "
					+ "WHERE o.RestaurantID = r.RestaurantNum AND r.BranchManagerID = ?;");
			getOrders.setInt(1, BranchManagerID);
			rs = getOrders.executeQuery();
			while (rs.next()) {
				order = new Order(rs.getInt(QueryConsts.ORDER_ORDER_NUM), rs.getInt(QueryConsts.ORDER_RESTAURANT_ID),
						rs.getString(QueryConsts.ORDER_RESTAURANT_NAME), rs.getString(QueryConsts.ORDER_ORDER_TIME),
						rs.getFloat(QueryConsts.ORDER_CHECK_OUT_PRICE), rs.getString(QueryConsts.ORDER_REQUIRED_TIME),
						rs.getString(QueryConsts.ORDER_TYPE_OF_ORDER), rs.getString(QueryConsts.ORDER_USER_NAME),
						rs.getString(QueryConsts.ORDER_PHONE_NUM),
						rs.getInt(QueryConsts.ORDER_DISCOUNT_FOR_EARLY_ORDER), null, null,
						rs.getString(QueryConsts.ORDER_APPROVED_TIME), rs.getBoolean(QueryConsts.ORDER_HAS_ARRIVED),
						rs.getBoolean(QueryConsts.ORDER_IS_APPROVED));

				orderTimeTaken = LocalDateTime.parse(order.getTime_taken(), formatter);
				if (orderTimeTaken.isAfter(from) && orderTimeTaken.isBefore(to))
					orders.add(order);
			}
		} catch (SQLException e) {
			return null;
		}
		return orders;
	}

	private static void getReportForOrdersCEO(int BranchManagerID) {
		ArrayList<Order> orders;
		HashMap<LocalDateTime, Integer> datesToOrders = new HashMap<>();
		HashMap<LocalDateTime, Double> datesToIncome = new HashMap<>();
		ArrayList<String[]> ordersData = new ArrayList<>();
		ArrayList<String[]> incomeData = new ArrayList<>();
		DateTimeFormatter fullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MM");
		int quarter = 0, month;
		int amountOFOrders;
		String[] stringToHelp = new String[2];
		Double amountOfIncome;
		LocalDateTime now = LocalDateTime.now(), orderTime;
		orders = getAllOrders(now.minusMonths(3), now, BranchManagerID);
		if (orders != null)
			for (Order temp : orders) {
				orderTime = LocalDateTime.parse(temp.getTime_taken(), fullFormat);
				if (datesToOrders.containsKey(orderTime)) {
					amountOFOrders = datesToOrders.get(orderTime);
					datesToOrders.replace(orderTime, amountOFOrders + 1);
					amountOfIncome = datesToIncome.get(orderTime);
					datesToIncome.replace(orderTime, amountOfIncome + temp.getCheck_out_price());
				} else {
					datesToOrders.put(orderTime, 1);
					datesToIncome.put(orderTime, temp.getCheck_out_price());
				}
			}
		for (LocalDateTime ot : datesToOrders.keySet()) {
			stringToHelp[0] = new String(ot.format(dateFormat));
			stringToHelp[1] = new String(datesToOrders.get(ot).toString());
			ordersData.add(stringToHelp);
		}
		for (LocalDateTime ot : datesToIncome.keySet()) {
			stringToHelp[0] = new String(ot.format(dateFormat));
			stringToHelp[1] = new String(datesToIncome.get(ot).toString());
			incomeData.add(stringToHelp);
		}
		month = Integer.parseInt(now.format(monthFormat));
		if (month > 3) {
			if (month > 6) {
				if (month > 9)
					quarter = 4;
				else
					quarter = 3;
			} else
				quarter = 2;
		} else
			quarter = 1;

		new PriceVolume(BranchManagerID, datesToOrders, datesToIncome, quarter);
	}

	public static void generateQuarterlyReports() {
		EchoServer.con = DBController.getMySQLConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST", "root",
				"MoshPe2969999");
		PreparedStatement getBR;
		ResultSet rs;
		try {
			getBR = EchoServer.con.prepareStatement("SELECT BranchManagerID FROM biteme.branch_manager");
			rs = getBR.executeQuery();
			while (rs.next()) {
				getReportForOrdersCEO(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void getTwoQuarterReports(int quarterFirst, String branchManagerAreaFirst, int quarterSecond,
			String branchManagerAreaSecond, int yearFirst, int yearSecond, Response response) {
		File file = new File(QueryConsts.FILE_PATH_REPORTS);
		String[] fileList = file.list();
		StringBuilder first = new StringBuilder(), second = new StringBuilder();
		ArrayList<MyPhoto> theTwoReports = new ArrayList<>();

		first.append("quarter_");
		first.append(quarterFirst);
		first.append("_");
		first.append(branchManagerAreaFirst);
		first.append("_");
		first.append(yearFirst);
		first.append(".png");

		second.append("quarter_");
		second.append(quarterSecond);
		second.append("_");
		second.append(branchManagerAreaSecond);
		second.append("_");
		second.append(yearSecond);
		second.append(".png");

		for (String fileName : fileList) {
			if (fileName.equals(first.toString())) {
				MyPhoto m = new MyPhoto(QueryConsts.FILE_PATH_REPORTS + fileName);
				imageUtils.sender(m);
				theTwoReports.add(m);
			}
			if (fileName.equals(second.toString())) {
				MyPhoto m = new MyPhoto(QueryConsts.FILE_PATH_REPORTS + fileName);
				imageUtils.sender(m);
				theTwoReports.add(m);
			}
		}

		for (int i = 0; i < theTwoReports.size(); i++)
			imageUtils.sender(theTwoReports.get(i));

		response.setBody(EchoServer.gson.toJson(theTwoReports.toArray()));
		response.setDescription("Success fetching two reports");
		response.setCode(200);
	}
}
