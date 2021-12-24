package common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class test {
	public static void main(String[] args) {
		Gson gson = new Gson();
		JsonElement jsonElem = gson.toJsonTree(new Object());
		String userName = "a";
		String password = "a";
		jsonElem.getAsJsonObject().addProperty("userName", password);
		jsonElem.getAsJsonObject().addProperty("password", password);

		Request request = new Request();
		request.setPath("/users/login");
		request.setMethod("GET");
		System.out.println(jsonElem);
		request.setBody(jsonElem);
		String jsonUser = gson.toJson(request);
		
		
		Request m = gson.fromJson((String) jsonUser, Request.class);
		String method = m.getMethod();
		String path = m.getPath();
		System.out.println("m.getBody()" + m.getBody());
		JsonElement body = gson.toJsonTree(m.getBody());
//		String userName = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
//		String password = gson.fromJson(body.getAsJsonObject().get("password"), String.class);
//		System.out.println();
		//JsonObject body = gson.toJsonTree(m.getBody()).getAsJsonObject();
		
		System.out.println(gson.fromJson(body.getAsJsonObject().get("password"), String.class));
		
//		String userName1 = gson.fromJson(body.getAsJsonObject().get("userName"), String.class);
//		String password1 = gson.fromJson(body.getAsJsonObject().get("password"), String.class);
	
		//System.out.println(userName1);
		//System.out.println(password1);
	}
}
