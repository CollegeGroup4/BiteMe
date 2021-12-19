package logic;

public class Menu {
	private String name;
	private int restaurantID;
	private item_in_menu[] items;
	
	public Menu(String name, int restaurantID, item_in_menu[] items) {
		this.name = name;
		this.restaurantID = restaurantID;
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
	public item_in_menu[] getItems() {
		return items;
	}
	public void setItems(item_in_menu[] items) {
		this.items = items;
	}
	
	
}
