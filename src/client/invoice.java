package client;

import logic.Item;
import logic.Options;
import logic.Order;
import logic.Shippment;

public class invoice {
	public static void main(String[] args) {
		Options[] options = new Options[2];
		options[0] = new Options("Size", "xl", 5, 0);
		options[1] = new Options("Olives", "extra", 5, 0);
		Item item = new Item("Italian", "Pizza", 10, 5, "Spicy Margaritta", 20, null, null, options, null, 2);
		Item item1 = new Item("Italian", "Pasta", 10, 5, "Tona and lemon spagetti", 20, null, null, options, null, 2);
		Shippment ship = new Shippment(5, "Intel", null, "Tal", "regular", "0556668511");
		Item[] items = new Item[2];
		items[0] = item;
		items[1] = item1;		
		Order order = new Order(0, 5, "Papardelle", null, 40, null, "delivery", null, null, 0, items, ship, null, false, false);
//		System.out.println("Name\t\t\t\tprice\t\tQuantity\tOptions");
//		System.out.println(item.toString());
//		System.out.println(item.toString());
		System.out.println(invoiceCheck(order, 5));
	}
	
	public static String invoiceCheck(Order order, int orderID) {
		StringBuilder invoice = new StringBuilder();
		String temp;
		temp = "Hi!" + "Tal" + "\n\n\nThank you for your purchase from BiteMe\nINVOICE ID: " + Integer.toString(orderID);
		invoice.append(temp);
		invoice.append("\n\nYour Order inforamation:\n");
		invoice.append("\t\tBill To: " + "fghghf98@gmail.com" + "\n\t\tOrder ID: " + Integer.toString(orderID));
		invoice.append("\n\t\tRestaurant Name: " + order.getRestaurantName() +"\n\t\tOrder Date: " + order.getTime_taken()
						+"\n\t\tType of Order: " + order.getType_of_order());
		invoice.append("\n-------------------------------\n");
		invoice.append("\nHere is what you ordered: \n\n");
		invoice.append("Name\t\t\t\tprice\t\tQuantity\tOptions\n");
		for (Item item : order.getItems()) {
			invoice.append(item.toString() + "\n");
		}
		invoice.append("Total ->: "+ Double.toString(order.getCheck_out_price()));
		invoice.append("\n-------------------------------\n");
		if(order.getShippment() != null) {
			invoice.append("\nShipment information: ");
			invoice.append("\n\t\twork place / address: " + order.getShippment().getWork_place() + order.getShippment().getAddress());
			invoice.append("\n\t\tdelivery type: " + order.getShippment().getDelivery());
			invoice.append("\n\t\treceiver name: " + order.getShippment().getReceiver_name());
			invoice.append("\n\t\treceiver phone number: " + order.getShippment().getPhone());
		}
		invoice.append("\n\n------------Thank you for ordering from us, BiteMe :)-------------------\n");
		return invoice.toString();
	}
}
