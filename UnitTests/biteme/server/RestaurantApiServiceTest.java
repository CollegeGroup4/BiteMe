package biteme.server;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import Server.EchoServer;
import Server.Response;
import Server.RestaurantApiService;
import common.DBController;
import logic.Item;
import logic.Options;
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
		int restaurantID = 10;
		RestaurantApiService.allItems(restaurantID, response);
		Item[] items = EchoServer.gson.fromJson((String)response.getBody(), Item[].class);
		System.out.println(Arrays.toString(items));
		assertEquals("Success in fetching all restaurant items -> restaurantID: 10",response.getDescription());
	}

//	@Test
//	public void testCreateItem() {
//		Options[] options = new Options[2];
//		options[0] = new Options("size","XL", 10, 0, false);
//		options[1] = new Options("tona","yes", 5, 0, false);		
//		Item item = new Item("italian", "pasta", 0, 10, "carbonara", 34, "The best pasta in the world", "cheese, oil, macaroni, etc.", options, null, 0);
//		RestaurantApiService.createItem(item, response);
//		assertEquals("Success in creating a new item -> itemID: 9", response.getDescription());
//	}

	@Test
	public void testUpdateItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testItemsDelete() {
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
	public void testCreateMenu() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditMenu() {
		fail("Not yet implemented");
	}


	@Test
	public void testGetCredit() {
		fail("Not yet implemented");
	}

}
