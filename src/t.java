import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import logic.Item;
import logic.Options;

public class t {
	public static void main(String[] args) {
		Gson gson = new Gson();
		JsonElement j = gson.toJsonTree(new Object());
		JsonElement a = gson.toJsonTree(new Object());
		JsonElement c = gson.toJsonTree(new Object());
		Options[] options = new Options[2];
		options[0] = new Options("size","XL", 10, 0, false);
		options[1] = new Options("tona","yes", 5, 0, false);		
		Item item = new Item("italian", "pasta", 0, 10, "pizzaWithSon", 34, "The best pasta in the world", "cheese, oil, macaroni, etc.", options, null, 0);
		JsonElement i = gson.toJsonTree(item);
		j.getAsJsonObject().add("item", i);
		String get = gson.toJson(j);
		
		JsonElement o = gson.fromJson(get, JsonElement.class);
		System.out.println(gson.fromJson(o.getAsJsonObject().get("item"), Item.class));
	}
}
