package common;

import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import logic.Order;

public class DBControllerTest extends TestCase {
	Connection conn;
	Order o1, o2, o3,o4;

	@Before
	// public Order(String restaurant, String orderAddress, String phoneNumber, Time
	// orderTime, String orderType)
	public void setUp() throws Exception {
		conn = DBController.getMySQLConnection();
		o1 = new Order("PizzaLacha", "Avital 23", "050-9824463", Time.valueOf("19:05:11"), "order-in");
		o2 = new Order("Rimini", "Avital 11", "050-9824423", Time.valueOf("19:01:23"), "take away");
		o3 = new Order("McDonalds", "Avital 1", "050-1231231", Time.valueOf("19:07:14"), "order");
		o4 = new Order(null, "Avital 1", "050-1231231", Time.valueOf("19:07:14"), "order");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInsertOrder() {
		DBController.clearTable(conn);
		DBController.insertOrder(conn, o1);
		assertTrue(DBController.getNumberOfOrders(conn) == 1);
	}

	@Test
	public void testGetOrders() {
		DBController.clearTable(conn);
		DBController.insertOrder(conn, o1);
		DBController.insertOrder(conn, o2);
		DBController.insertOrder(conn, o3);

		o1.setOrderNum(DBController.getOrderNum(conn, o1));
		o2.setOrderNum(DBController.getOrderNum(conn, o2));
		o3.setOrderNum(DBController.getOrderNum(conn, o3));

		ArrayList<String> temp = DBController.getOrders(conn);

		assertTrue(o1.toString().equals(temp.get(2)));

		assertTrue(o2.toString().equals(temp.get(3)));

		assertTrue(o3.toString().equals(temp.get(4)));

	}

	@Test
	public void testGetOrder() {
		DBController.clearTable(conn);
		DBController.insertOrder(conn, o1);
		DBController.insertOrder(conn, o2);
		String o1Order, o2Order, o3Order;

		o1.setOrderNum(DBController.getOrderNum(conn, o1));
		o2.setOrderNum(DBController.getOrderNum(conn, o2));
		o3.setOrderNum(DBController.getOrderNum(conn, o3));

		o1Order = DBController.getOrder(conn, String.valueOf(o1.getOrderNum())).get(2);
		o2Order = DBController.getOrder(conn, String.valueOf(o2.getOrderNum())).get(2);
		o3Order = DBController.getOrder(conn, String.valueOf(o3.getOrderNum())).get(2);

		assertTrue(o1Order.equals(o1.toString()));
		assertTrue(o2Order.equals(o2.toString()));
		assertTrue(o3Order.equals("ERROR"));
	}

}
