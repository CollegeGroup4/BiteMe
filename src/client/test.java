package client;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) {
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
        map.put("5", gson.toJson(p));
        map.put("7", gson.toJson(y));
        System.out.println(gson.toJson(map));
    }
}
