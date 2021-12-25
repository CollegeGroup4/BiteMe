package biteme.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;


import logic.Order;

public class CEOApiService {

	public static ArrayList<Order> getAllOrders(LocalDateTime from, LocalDateTime to) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ArrayList<Order> orders = new ArrayList<>();
		PreparedStatement getOrders;
		Order order;
		LocalDateTime orderTimeTaken;
		ResultSet rs;
		try {
			getOrders = EchoServer.con.prepareStatement("SELECT * FROM biteme.order;");
			getOrders.executeQuery();
			rs = getOrders.getResultSet();
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

	public static void getReportForOrdersCEO() {
		ArrayList<Order> orders;
		HashMap<LocalDateTime, Integer> datesToOrders = new HashMap<>();
		HashMap<LocalDateTime, Double> datesToIncome = new HashMap<>();
		ArrayList<String[]> ordersData = new ArrayList<>();
		ArrayList<String[]> incomeData = new ArrayList<>();
		DateTimeFormatter fullFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		int amountOFOrders;
		String[] stringToHelp = new String[2];
		Double amountOfIncome;
		LocalDateTime now = LocalDateTime.now(), orderTime;
		orders = getAllOrders(now.minusMonths(3), now);
		for(Order temp : orders) {
			orderTime = LocalDateTime.parse(temp.getTime_taken(), fullFormat);
			if(datesToOrders.containsKey(orderTime)) {
				amountOFOrders = datesToOrders.get(orderTime);
				datesToOrders.replace(orderTime, amountOFOrders+1);
				amountOfIncome = datesToIncome.get(orderTime);
				datesToIncome.replace(orderTime, amountOfIncome+temp.getCheck_out_price());
			} else {
				datesToOrders.put(orderTime, 1);
				datesToIncome.put(orderTime, temp.getCheck_out_price());
			}
		}
		for(LocalDateTime ot : datesToOrders.keySet()) {
			stringToHelp[0] = new String(ot.format(monthFormat));
			stringToHelp[1] = new String(datesToOrders.get(ot).toString());
			ordersData.add(stringToHelp);
		}
		for(LocalDateTime ot : datesToIncome.keySet()) {
			stringToHelp[0] = new String(ot.format(monthFormat));
			stringToHelp[1] = new String(datesToIncome.get(ot).toString());
			incomeData.add(stringToHelp);
		}
	}
}
