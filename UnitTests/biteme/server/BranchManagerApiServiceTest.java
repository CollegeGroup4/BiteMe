/**
 * 
 */
package biteme.server;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Server.BranchManagerApiService;
import Server.EchoServer;
import Server.Response;
import common.DBController;
import logic.Account;
import logic.Order;

/**
 * @author talye
 *
 */
public class BranchManagerApiServiceTest {
	private Response response;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		EchoServer.con = DBController.getMySQLConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST", "root",
				"Tal4EvEr");
		response = new Response();
	}

	/**
	 * Test method for
	 * {@link Server.BranchManagerApiService#getBranchOrders(int, Server.Response)}.
	 */
	@Test
	public void testGetBranchOrders() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		try {
			PreparedStatement addOrders = EchoServer.con.prepareStatement(
					"INSERT INTO biteme.order (RestaurantID, UserName, OrderTime, PhoneNumber, TypeOfOrder, Check_out_price, isApproved, required_time, hasArrived) "
							+ "VALUES (1,'a',?,'42534','orderin',100,true,?,false);");
			for (int i = 0; i < 5; i++) {
				addOrders.setString(1, now.format(formatter));
				addOrders.setString(2, now.format(formatter));
				addOrders.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		BranchManagerApiService.getBranchOrders(1, response);
		Map<String, String> ordersByRestaurantID = EchoServer.gson.fromJson((String) response.getBody(), Map.class);
		assertEquals(ordersByRestaurantID.size(), 1);

		for(String temp : ordersByRestaurantID.keySet()) {
			Order otemp =EchoServer.gson.fromJson(ordersByRestaurantID.get("temp"), Order.class);
			System.out.println(otemp);
		}
	}

	/**
	 * Test method for
	 * {@link Server.BranchManagerApiService#getBranchRestaurants(int, Server.Response)}.
	 */
	@Test
	public void testGetBranchRestaurants() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link Server.BranchManagerApiService#getBranchReports(java.util.List)}.
	 */
	@Test
	public void testGetBranchReports() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link Server.BranchManagerApiService#registerSupplier(int, java.lang.String, java.lang.String, logic.Restaurant, Server.Response)}.
	 */
	@Test
	public void testRegisterSupplier() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link Server.BranchManagerApiService#registerPrivateAccount(logic.PrivateAccount, Server.Response)}.
	 */
	@Test
	public void testRegisterPrivateAccount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link Server.BranchManagerApiService#registerBusinessAccount(logic.BusinessAccount, Server.Response)}.
	 */
	@Test
	public void testRegisterBusinessAccount() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link Server.BranchManagerApiService#getReportForRestaurantSales()}.
	 */
	@Test
	public void testGetReportForRestaurantSales() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link Server.BranchManagerApiService#getReportForRestaurantByCategory()}.
	 */
	@Test
	public void testGetReportForRestaurantByCategory() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link Server.BranchManagerApiService#getReportForRestaurantPerformence()}.
	 */
	@Test
	public void testGetReportForRestaurantPerformence() {
		fail("Not yet implemented");
	}

}
