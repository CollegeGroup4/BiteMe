/**
 * 
 */
package Server;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aspose.pdf.internal.imaging.internal.bouncycastle.cert.ocsp.Req;
import com.google.gson.JsonElement;
import com.twilio.twiml.voice.Echo;

import Server.BranchManagerApiService;
import Server.EchoServer;
import common.DBController;
import common.Request;
import common.Response;
import logic.Account;
import logic.Order;
import logic.Restaurant;

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
				"MoshPe2969999");
		response = new Response();
	}


//	public void clearOrders() {
//		try {
//			PreparedStatement deleteOrders = EchoServer.con.prepareStatement("DELETE FROM biteme.order;");
//			deleteOrders.executeUpdate();
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * Test method for
//	 * {@link Server.BranchManagerApiService#getBranchOrders(int, Server.Response)}.
//	 */
//	@Test
//	public void testGetBranchOrders() {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//		LocalDateTime now = LocalDateTime.now();
//		try {
//			PreparedStatement addOrders = EchoServer.con.prepareStatement(
//					"INSERT INTO biteme.order (RestaurantID, UserName, OrderTime, PhoneNumber, TypeOfOrder, Check_out_price, isApproved, required_time, hasArrived) "
//							+ "VALUES (1,'a',?,'42534','orderin',100,true,?,false);");
//			for (int i = 0; i < 5; i++) {
//				addOrders.setString(1, now.format(formatter));
//				addOrders.setString(2, now.format(formatter));
//				addOrders.executeUpdate();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		BranchManagerApiService.getBranchOrders(1, response);
//		Map<String, String> ordersByRestaurantID = EchoServer.gson.fromJson((String) response.getBody(), Map.class);
//		assertEquals(ordersByRestaurantID.size(), 1);
//		Order[] otemp =EchoServer.gson.fromJson(ordersByRestaurantID.get("1"), Order[].class);
//		assertEquals(otemp.length, 5);
//		clearOrders();
//	}
//
//	/**
//	 * Test method for
//	 * {@link Server.BranchManagerApiService#getBranchRestaurants(int, Server.Response)}.
//	 */
//	@Test
//	public void testGetBranchRestaurants() {
//		try {
//			PreparedStatement addOrders = EchoServer.con.prepareStatement(
//					"INSERT INTO biteme.order (RestaurantID, UserName, OrderTime, PhoneNumber, TypeOfOrder, Check_out_price, isApproved, required_time, hasArrived) "
//							+ "VALUES (1,'a',?,'42534','orderin',100,true,?,false);");
//			for (int i = 0; i < 5; i++) {
////				addOrders.setString(1, now.format(formatter));
////				addOrders.setString(2, now.format(formatter));
//				addOrders.executeUpdate();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Test method for
	 * {@link Server.BranchManagerApiService#getBranchReports(java.util.List)}.
	 */
	@Test
	public void testGetBranchReports() {
		fail("Not yet implemented");
	}
	
//	/**
//	 * Test method for
//	 * {@link Server.BranchManagerApiService#getBranchReports(java.util.List)}.
//	 */
//	@Test
//	public void testApproveEmployer() {
//		String businessName = "intel";
//		int branchManagerID = 1;
//		BranchManagerApiService.approveEmployer(businessName, branchManagerID, response);
//		assertEquals("Success in adding a new employer -> BusinessName: " + businessName, response.getDescription());
//	}

	/**
	 * Test method for
	 * {@link Server.BranchManagerApiService#registerSupplier(int, java.lang.String, java.lang.String, logic.Restaurant, Server.Response)}.
	 *///TODO works perfectly
	@Test
	public void testRegisterSupplierModerator() {
		JsonElement j = EchoServer.gson.toJsonTree(new Object());
		JsonElement sup = EchoServer.gson.toJsonTree(new Object());
		JsonElement mod = EchoServer.gson.toJsonTree(new Object());
		
		Restaurant restaurant = new Restaurant(0, false, 2, "pizz", "south", "pizza", "d", null, "raines", "Only potatoes");
		j.getAsJsonObject().add("restaurant", EchoServer.gson.toJsonTree(restaurant));
		sup.getAsJsonObject().addProperty("userName", "d");
		sup.getAsJsonObject().addProperty("userID", 2);
		mod.getAsJsonObject().addProperty("userName", "e");
		mod.getAsJsonObject().addProperty("userID", 2);
		j.getAsJsonObject().add("moderator", mod);
		j.getAsJsonObject().add("supplier", sup);
		Request r = new Request();
		r.setPath("/branch_managers/restaurants");
		r.setMethod("POST");
		r.setBody(EchoServer.gson.toJson(j));
		EchoServer i = new EchoServer(5555);
		i.handleMessageFromClient(EchoServer.gson.toJson(r), null);
		//BranchManagerApiService.registerSupplierModerator("d", 2,"Supplier", restaurant, response);
		assertEquals("Success in registering a new restaurant -> role: Supplier, restaurantName piz", response.getDescription());
	}
//	
//	/**
//	 * Test method for
//	 * {@link Server.BranchManagerApiService#registerSupplier(int, java.lang.String, java.lang.String, logic.Restaurant, Server.Response)}.
//	 */
//	@Test
//	public void testRegisterModeratorModerator() {
//		Restaurant restaurant = new Restaurant(0, false, 1, "steak", "north", "shushi", "e", null, "raines", "this is carnivors restu");
//		BranchManagerApiService.registerSupplierModerator("e", "Moderator", restaurant, response);
//		assertEquals("Success in registering a new restaurant -> role: Moderator, restaurantName steak", response.getDescription());
//	}
//	/**
//	 * Test method for
//	 * {@link Server.BranchManagerApiService#getBranchReports(java.util.List)}.
//	 */
//	@Test
//	public void testEditRestaurant() {
//		Restaurant restaurant = new Restaurant(5, false, 1, "diary", "north", "pizzaaaa", "e", null, "raines", "l rly want a pizza");
//		BranchManagerApiService.editRestaurant("e", restaurant, response);
//		assertEquals("Success in updating a restaurant -> restaurantName: " + restaurant.getName(), response.getDescription());
//	}
//	
//	/**
//	 * Test method for
//	 * {@link Server.BranchManagerApiService#getBranchReports(java.util.List)}.
//	 */
//	@Test
//	public void testDeleteRestaurant() {
//		Restaurant restaurant = new Restaurant(5, false, 1, "diary", "north", "pizzaaaa", "e", null, "raines", "l rly want a pizza");
//		BranchManagerApiService.deleteRestaurant("e", restaurant, response);
//		assertEquals("Success in deleting a restaurant -> restaurantName: " + restaurant.getName(), response.getDescription());
//	}
//	
//	/**
//	 * Test method for
//	 * {@link Server.BranchManagerApiService#getBranchRestaurants(int, Server.Response)}.
//	 */
//	@Test
//	public void testGetBranchRestaurants() {
//		int branchManagerID = 1;
//		BranchManagerApiService.getBranchRestaurants(branchManagerID, response);
//		Restaurant[] res = EchoServer.gson.fromJson((String)response.getBody(), Restaurant[].class);
//		System.out.println(Arrays.toString(res));
//		assertEquals("Success in fetching all branch restaurants -> branch manager id: "+ Integer.toString(branchManagerID), response.getDescription());
//	}

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
