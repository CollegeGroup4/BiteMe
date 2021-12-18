package client;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class ask {
	public static void main(String[] args) {
//		Order o = new Order(2, 0, null, null, 0, null, null, 0, null, 0, false, null, null);
//		Order r = new Order(2, 0, "steak", null, 0, null, null, 0, null, 0, false, null, null);
		        Map<String,String> map = new HashMap<>();
		        //You can convert any Object.
		        OrderC c = new OrderC(5, 7, "steak", "19:50", 5,
		                null, "takeAway", 1, null, 0,
		                false, "Second");
		        OrderC a = new OrderC(7, 7, "steak", "19:50", 5,
		                null, "delivery", 1, null, 0,
		                false, "Second");
		        OrderC h = new OrderC(5, 5, "steak", "19:50", 5,
		                null, "takeAway", 1, null, 0,
		                false, "Second");
		        OrderC i = new OrderC(7, 5, "steak", "19:50", 5,
		                null, "delivery", 1, null, 0,
		                false, "Second");
		        Gson gson = new Gson();
				OrderC[] y = new OrderC[2];
		        y[0] = c;
				y[1] = a;
		        OrderC[] p = new OrderC[2];
		        p[0] = h;
		        p[1] = i;
		        JsonElement o = gson.toJsonTree(y);
		        JsonElement k = gson.toJsonTree(p);
		        map.put("5", gson.toJson(o));
		        map.put("7", gson.toJson(k));
		        System.out.println(gson.toJson(map));
		        
		        Map<String,String> testi = gson.fromJson(gson.toJson(map), HashMap.class);
		        System.out.println(testi);
//		JsonElement v = gson.toJsonTree(c);
//		JsonElement j = gson.toJsonTree(new Object());
//		j.getAsJsonObject().addProperty("path", "/returants/menus");
//		j.getAsJsonObject().addProperty("method", "POST");
//		j.getAsJsonObject().add("body",v);
//		String p = gson.toJson(a);
//		JsonElement res = gson.toJsonTree(b);
//		JsonElement sup = gson.toJsonTree(c);
//		JsonElement mode = gson.toJsonTree(c);
//		JsonElement a = gson.toJsonTree(new Object());
//		a.getAsJsonObject().add("restaurant", res);
//		a.getAsJsonObject().add("supplier", sup);
//		a.getAsJsonObject().add("moderator", mode);
//		req.setBody(a);
//		System.out.println(gson.toJson(req));
		//		
//		Response k = gson.fromJson(p, Response.class);
//		JsonElement h = gson.toJsonTree(k.getBody());
//		System.out.println("restu name is " +gson.fromJson(h.getAsJsonObject().get("restaurantName"), String.class));
//		System.out.println("Body   " + k.getBody());
////		JsonObject m = gson.fromJson(p, JsonObject.class);
////		Order l = gson.fromJson(p, Order.class);
////		OrderB n = gson.fromJson(p, OrderB.class);
////		System.out.println("Order l   " + l.toString());
////		System.out.println("Order n   " + n.toString());
//		System.out.println(p);
//		System.out.println("The jsonObject as toJson is: " + gson.toJson(m));
//		Order[] a = gson.fromJson(j.getAsJsonObject().get("body"),Order[].class);
//		System.out.println("Order a   " + Arrays.toString(a));
//		
//		System.out.println(j.toString());
//		System.out.println(gson.toJson(j));	
		System.out.println(getRandomHexString(20));
	}
	
	 public static String getRandomHexString(int numchars){
	        Random r = new Random();
	        StringBuffer sb = new StringBuffer();
	        while(sb.length() < numchars){
	            sb.append(Integer.toHexString(r.nextInt()));
	        }

	        return sb.toString().substring(0, numchars);
	    }
}
