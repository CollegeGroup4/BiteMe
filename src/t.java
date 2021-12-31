import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class t {
	public static void main(String[] args) {
		Gson gson = new Gson();
		JsonElement j = gson.toJsonTree(new Object());
		JsonElement a = gson.toJsonTree(new Object());
		JsonElement c = gson.toJsonTree(new Object());
		j.getAsJsonObject().addProperty("userName", "hi");
		c.getAsJsonObject().add("supplier", j);
		c.getAsJsonObject().add("moderator", a);
		String get = gson.toJson(c);
		System.out.println(gson.fromJson(gson.fromJson(get, JsonElement.class).getAsJsonObject().get("moderator").getAsJsonObject().get("userName"),String.class));
	}
}
