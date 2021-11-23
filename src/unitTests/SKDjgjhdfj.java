package unitTests;

import java.sql.Connection;
import java.sql.Time;

import common.DBController;
import logic.Order;

public class SKDjgjhdfj {

	public static void main(String[] args) {
		Time t = Time.valueOf("19:05:23");
		Order order = new Order("Tal-Chen", "steakHouse", "055666",t , "dine-in");
		Connection conn = DBController.getMySQLConnection();
		DBController.insertOrder(conn, order);
	}
}
