package donotenterdrinksorfood;

public class item {
	private int itemID;
	private int restaurantID;
	private String Type;
	private String name;
	private float price;
	private String description;
	private String ingrediants;
	private Options[] options;
	public byte[] photo;
	public item(int itemID, int restaurantID, String type, String name, float price, String description,
			String ingrediants, Options[] options, byte[] photo) {
		this.itemID = itemID;
		this.restaurantID = restaurantID;
		Type = type;
		this.name = name;
		this.price = price;
		this.description = description;
		this.ingrediants = ingrediants;
		this.options = options;
		this.photo = photo;
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
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIngrediants() {
		return ingrediants;
	}
	public void setIngrediants(String ingrediants) {
		this.ingrediants = ingrediants;
	}
	public Options[] getOptions() {
		return options;
	}
	public void setOptions(Options[] options) {
		this.options = options;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	
}
