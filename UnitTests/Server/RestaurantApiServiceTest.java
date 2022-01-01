package Server;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;

import Server.EchoServer;
import Server.RestaurantApiService;
import common.DBController;
import common.Response;
import logic.Item;
import logic.Menu;
import logic.Options;
import logic.Restaurant;
import logic.item_in_menu;

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

//	@Test
//	public void testGetAllCategories() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testAllItems() {
//		int restaurantID = 10;
//		RestaurantApiService.allItems(restaurantID, response);
//		Item[] items = EchoServer.gson.fromJson((String)response.getBody(), Item[].class);
//		System.out.println(Arrays.toString(items));
//		assertEquals("Success in fetching all restaurant items -> restaurantID: 10",response.getDescription());
//	}

//	@Test
//	public void testCreateItem() {
//		Options[] options = new Options[2];
//		options[0] = new Options("size","XL", 10, 0, false);
//		options[1] = new Options("tona","yes", 5, 0, false);		
//		Item item = new Item("italian", "pasta", 0, 10, "carbonara", 34, "The best pasta in the world", "cheese, oil, macaroni, etc.", options, null, 0);
//		RestaurantApiService.createItem(item, response);
//		assertEquals("Success in creating a new item -> itemID: 9", response.getDescription());
//	}

//	@Test
//	public void testUpdateItem() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testItemsDelete() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void testAllMenues() {
//		int restaurantID = 10;
//		RestaurantApiService.allMenues(restaurantID, response);
//		JsonElement j = EchoServer.gson.fromJson((String) response.getBody(), JsonElement.class);
//		Menu[] menues = EchoServer.gson.fromJson(j.getAsJsonObject().get("menues"), Menu[].class);
//		Item[] items = EchoServer.gson.fromJson(j.getAsJsonObject().get("items"), Item[].class);
//		System.out.println(Arrays.toString(menues));
//		System.out.println(Arrays.toString(items));
//	}

	@Test
	public void testApproveOrder() {
		fail("Not yet implemented");
	}

//	@Test
//	public void testCreateMenu() {
//		item_in_menu[] items = new item_in_menu[2];
//		items[0] = new item_in_menu(8, 10, "Night", "first");
//		items[1] = new item_in_menu(9, 10, "Night", "first");
//		Menu menu = new Menu("Night", 10, items);
//		RestaurantApiService.createMenu(menu, response);
//		assertEquals("Success in creating menu -> menuName:  Night", response.getDescription());	
//	}

	@Test
	public void testEditMenu() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCredit() {
		fail("Not yet implemented");
	}

}
