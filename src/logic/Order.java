package logic;

public class Order {
	private int orderID;
	private int restaurantID;
	private String restaurantName;
	private String time_taken;
	private float check_out_price;
	private String required_time;
	private String type_of_order;
	private int accountID;
	private String phone;
	private int discount_for_early_order;
	private boolean isBuisness;
	private Item[] items;
	private Shippment shippment;

	public Order(int orderID, int restaurantID, String restaurantName, String time_taken, float check_out_price, String required_time, String type_of_order, int accountID, String phone, int discount_for_early_order, boolean isBuisness, Item[] items, Shippment shippment) {
		this.orderID = orderID;
		this.restaurantID = restaurantID;
		this.restaurantName = restaurantName;
		this.time_taken = time_taken;
		this.check_out_price = check_out_price;
		this.required_time = required_time;
		this.type_of_order = type_of_order;
		this.accountID = accountID;
		this.phone = phone;
		this.discount_for_early_order = discount_for_early_order;
		this.isBuisness = isBuisness;
		this.items = items;
		this.shippment = shippment;
	}

	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getRestaurantID() {
		return restaurantID;
	}
	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}
	public String getTime_taken() {
		return time_taken;
	}
	public void setTime_taken(String time_taken) {
		this.time_taken = time_taken;
	}
	public float getCheck_out_price() {
		return check_out_price;
	}
	public void setCheck_out_price(float check_out_price) {
		this.check_out_price = check_out_price;
	}
	public String getRequired_time() {
		return required_time;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public void setRequired_time(String required_time) {
		this.required_time = required_time;
	}
	public String getType_of_order() {
		return type_of_order;
	}
	public void setType_of_order(String type_of_order) {
		this.type_of_order = type_of_order;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getDiscount_for_early_order() {
		return discount_for_early_order;
	}
	public void setDiscount_for_early_order(int discount_for_early_order) {
		this.discount_for_early_order = discount_for_early_order;
	}
	public boolean isBuisness() {
		return isBuisness;
	}
	public void setBuisness(boolean isBuisness) {
		this.isBuisness = isBuisness;
	}
	public Item[] getItems() {
		return items;
	}
	public void setItems(Item[] items) {
		this.items = items;
	}

	public Shippment getShippment() {
		return shippment;
	}

	public void setShippment(Shippment shippment) {
		this.shippment = shippment;
	}

}
