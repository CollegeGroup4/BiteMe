package client;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import logic.Order;

public class ask {
	public static void main(String[] args) {
		Order o = new Order("asdasd", "asdasd", null, null, null);
		Order b = new Order("b", "b", null, null, null);
		Order[] i = new Order[2];
		i[0] = o;
		i[1] = b;
		Gson gson = new Gson();
		JsonElement j = gson.toJsonTree(o);
		j.getAsJsonObject().addProperty("path", "/orders");
		j.getAsJsonObject().addProperty("method", "get");
		j.getAsJsonObject().add("body", gson.toJsonTree(i));
		String p = gson.toJson(j);
		JsonObject m = gson.fromJson(p, JsonObject.class);
		Order[] l = gson.fromJson(m.get("body"), Order[].class);
		System.out.println("Order l   " + Arrays.toString(l));
		System.out.println("The jsonObject as toJson is: " + gson.toJson(m));
		Order[] a = gson.fromJson(j.getAsJsonObject().get("body"),Order[].class);
		System.out.println("Order a   " + Arrays.toString(a));
		
		System.out.println(j.toString());
		System.out.println(gson.toJson(j));	
	}
}
