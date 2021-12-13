package logic;

import java.util.ArrayList;

public class item_in_menu {
	private int itemID;
	private int restaurantID;
	private String menu_name;
	private String course;
	private int price;
	private String description;
	private ArrayList<String> ingrediants;
	public item_in_menu(int itemID, int restaurantID, String menu_name, String course, int price, String description, ArrayList<String> ingrediants) {
		this.itemID = itemID;
		this.restaurantID = restaurantID;
		this.menu_name = menu_name;
		this.course = course;
		this.price=price;
		this.description=description;
		this.ingrediants=ingrediants;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<String> getIngrediants() {
		return ingrediants;
	}
	public void setIngrediants(ArrayList<String> ingrediants) {
		this.ingrediants = ingrediants;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public int getRestaurantID() {
		return restaurantID;
	}
	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
}
