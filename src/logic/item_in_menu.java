package logic;

public class item_in_menu {
	private int itemID;
	private int restaurantNum;
	private String menu_name;
	private String course;
	
	public item_in_menu(int itemID, int restaurantNum, String menu_name, String course) {
		this.itemID = itemID;
		this.restaurantNum = restaurantNum;
		this.menu_name = menu_name;
		this.course = course;
	}
	
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public int getRestaurantNum() {
		return restaurantNum;
	}
	public void setRestaurantNum(int restaurantNum) {
		this.restaurantNum = restaurantNum;
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

	@Override
	public String toString() {
		return "item_in_menu [itemID=" + itemID + ", restaurantNum=" + restaurantNum + ", menu_name=" + menu_name
				+ ", course=" + course + "]";
	}
}