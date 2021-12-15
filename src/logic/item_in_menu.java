package logic;

public class item_in_menu {
	private int itemID;
	private int restaurantID;
	private String menu_name;
	private String course;
	
	public item_in_menu(int itemID, int restaurantID, String menu_name, String course) {
		this.itemID = itemID;
		this.restaurantID = restaurantID;
		this.menu_name = menu_name;
		this.course = course;
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
