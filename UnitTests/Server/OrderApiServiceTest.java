package Server;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import common.DBController;
import common.Response;
import logic.Order;

public class OrderApiServiceTest {
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

//	@Test
//	public void testAddOrder() {
//		Options[] options = new Options[2];
//		Item[] items = new Item[2];
//		options[0] = new Options("size","XL", 10, 0, false);
//		options[1] = new Options("tona","yes", 5, 0, false);		
//		items[0] = new Item("italian", "pasta", 8, 10,"carbonara", 34, "The best pasta in the world", "cheese, oil, macaroni, etc.", options, null, 1);
//		items[1] = new Item("italian", "pizza", 9, 10,"Margaritta", 34, "The best pasta in the world", "cheese, tomatoes, etc.", options, null, 1);
//		Shippment shi = new Shippment(0, "intel", null, "Tal", "regular", "1-800-400-400-AIG");
//		Order order = new Order(0, 10, "piz", "2021-12-31 11:30", 52.9,"2021-12-31 15:30", "delivery", "b", "055", 0, items, shi, null, false, false);
//		OrderApiService.addOrder(order, response);
//		JsonElement j =  EchoServer.gson.fromJson((String)response.getBody(), JsonElement.class);
//		int orderID = EchoServer.gson.fromJson(j.getAsJsonObject().get("orderID"), Integer.class);
//		assertEquals("A new order has been successfuly added -> orderID: 16", response.getDescription());
//	}
 
	@Test
	public void testAllOrders() {
		fail("Not yet implemented");
	}

	@Test
	public void testAllOrdersByRestaurantID() {
		fail("Not yet implemented");
	}

	@Test
	public void testAllOrdersByUserName() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOrderById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPaymentApproval() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetOredersByUserName() {
		String userName = "b";
		OrderApiService.getOrderByUserName(userName, response);
		Order[] orders = EchoServer.gson.fromJson((String)response.getBody(), Order[].class);
		System.out.println(Arrays.toString(orders));
		
	}
	@Test
	public void testUpdateOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetItemsByOrderID() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeliveredOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCredit() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendMail() {
		fail("Not yet implemented");
	}

}
