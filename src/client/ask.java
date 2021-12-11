package client;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import Server.Response;
import logic.Order;

public class ask {
	public static void main(String[] args) {
		Order o = new Order(2, 0, null, null, 0, null, null, 0, null, 0, false, null, null);
		Order r = new Order(2, 0, "steak", null, 0, null, null, 0, null, 0, false, null, null);
		OrderB b = new OrderB("steak", "19:50", 7.5,"first");
		OrderC c = new OrderC(5, 7, "steak", "19:50", 5, null, "takeAway", 1, null, 0, false, "Second");
		Gson gson = new Gson();
		menuInRes y = new menuInRes();
		y[0] = o;
		y[1] = r;
		JsonElement v = gson.toJsonTree(c);
		JsonElement j = gson.toJsonTree(new Object());
		j.getAsJsonObject().addProperty("path", "/returants/menus");
		j.getAsJsonObject().addProperty("method", "POST");
		j.getAsJsonObject().add("body",v);
		String p = gson.toJson(j);
		
		Response k = gson.fromJson(p, Response.class);
		JsonElement h = gson.toJsonTree(k.getBody());
		System.out.println("restu name is " +gson.fromJson(h.getAsJsonObject().get("restaurantName"), String.class));
		System.out.println("Body   " + k.getBody());
//		JsonObject m = gson.fromJson(p, JsonObject.class);
//		Order l = gson.fromJson(p, Order.class);
//		OrderB n = gson.fromJson(p, OrderB.class);
//		System.out.println("Order l   " + l.toString());
//		System.out.println("Order n   " + n.toString());
		System.out.println(p);
//		System.out.println("The jsonObject as toJson is: " + gson.toJson(m));
//		Order[] a = gson.fromJson(j.getAsJsonObject().get("body"),Order[].class);
//		System.out.println("Order a   " + Arrays.toString(a));
//		
//		System.out.println(j.toString());
//		System.out.println(gson.toJson(j));	
	}
}
