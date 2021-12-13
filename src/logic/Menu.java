package logic;

import java.util.ArrayList;

public class Menu {
	private String name;
	private int restaurantID;
	private ArrayList<item_in_menu> items;
	public Menu(String name, int restaurantID, ArrayList<item_in_menu> items) {
		this.name = name;
		this.restaurantID = restaurantID;
		this.items=items;
	}
	public ArrayList<item_in_menu>getItems() {
		return items;
	}
	public void setItems(ArrayList<item_in_menu> items) {
		this.items = items;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRestaurantID() {
		return restaurantID;
	}
	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}
	
}
