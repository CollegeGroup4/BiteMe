package test;

import java.sql.Connection;
import java.sql.Time;

import common.DBController;
import logic.Order;
import logic.typeOfOrder;

public class SKDjgjhdfj {

	public static void main(String[] args) {
		Time t = Time.valueOf("19:05:23");
		Order order = new Order("Tal-Chen", "steakHouse", "055666",t , typeOfOrder.TAKE_OUT);
		Connection conn = DBController.getMySQLConnection();
		DBController.insertOrder(conn, order);
	}
}
