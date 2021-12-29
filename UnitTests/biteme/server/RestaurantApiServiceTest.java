package biteme.server;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import Server.EchoServer;
import Server.Response;
import Server.RestaurantApiService;
import common.DBController;
import logic.Restaurant;

public class RestaurantApiServiceTest {
	private Response response;

	@Before
	public void setUp() throws Exception {
		EchoServer.con = DBController.getMySQLConnection("jdbc:mysql://localhost/biteme?serverTimezone=IST", "root",
				"MoshPe2969999");
		response = new Response();
	}


//
//	@Test
//	public void testGetRestaurantsByArea() {
//		String area = "All";
//		RestaurantApiService.getRestaurantsByArea(area, response);
//		Restaurant[] res = EchoServer.gson.fromJson((String)response.getBody(), Restaurant[].class);
//		System.out.println(Arrays.toString(res));
//		assertEquals("Success in fetching restaurants -> Area: All, type: All", response.getDescription());
//	}
//	
//	@Test
//	public void testGetRestaurantsByAreaNorth() {
//		String area = "north";
//		RestaurantApiService.getRestaurantsByArea(area, response);
//		Restaurant[] res = EchoServer.gson.fromJson((String)response.getBody(), Restaurant[].class);
//		System.out.println(Arrays.toString(res));
//		assertEquals("Success in fetching restaurants -> Area: north, type: All", response.getDescription());
//	}
//	
//	@Test
//	public void testGetRestaurantsByAreaSouth() {
//		String area = "south";
//		RestaurantApiService.getRestaurantsByArea(area, response);
//		Restaurant[] res = EchoServer.gson.fromJson((String)response.getBody(), Restaurant[].class);
//		System.out.println(Arrays.toString(res));
//		assertEquals("Success in fetching restaurants -> Area: south, type: All", response.getDescription());
//	}
//
//	@Test
//	public void testGetRestaurantsByTypeAndArea() {
//		String area = "All";
//		String type = "shushi";
//		RestaurantApiService.getRestaurantsByTypeAndArea(area, type, response);
//		Restaurant[] res = EchoServer.gson.fromJson((String)response.getBody(), Restaurant[].class);
//		System.out.println(Arrays.toString(res));
//		assertEquals("Success in fetching restaurants -> Area: All, type: shushi", response.getDescription());
//	}
//	
//	@Test
//	public void testGetRestaurantsByTypeAndAreaNorth() {
//		String area = "north";
//		String type = "shushi";
//		RestaurantApiService.getRestaurantsByTypeAndArea(area, type, response);
//		Restaurant[] res = EchoServer.gson.fromJson((String)response.getBody(), Restaurant[].class);
//		System.out.println(Arrays.toString(res));
//		assertEquals("Success in fetching restaurants -> Area: north, type: shushi", response.getDescription());
//	}

	@Test
	public void testGetAllCategories() {
		fail("Not yet implemented");
	}

	@Test
	public void testAllItems() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testAllMenues() {
		fail("Not yet implemented");
	}

	@Test
	public void testApproveOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateMenu() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditMenu() {
		fail("Not yet implemented");
	}

	@Test
	public void testItemsDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCredit() {
		fail("Not yet implemented");
	}

}
