package donotenterdrinksorfood;

public class Menu {
	private String name;
	private int restaurantID;
	public Menu(String name, int restaurantID) {
		super();
		this.name = name;
		this.restaurantID = restaurantID;
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
