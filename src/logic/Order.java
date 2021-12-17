package logic;

import java.util.Arrays;

public class Order {
	private int orderID;
	private int restaurantID;
	private String restaurantName;
	private String time_taken;
	private float check_out_price;
	private String required_time;
	private String type_of_order;
	private String userName;
	private String phone;
	private int discount_for_early_order;
	private boolean isApproved;
	private Item[] items;
	private Shippment shippment;
	private String approved_time;
	private boolean hasArrived;

	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", restaurantID=" + restaurantID + ", restaurantName=" + restaurantName
				+ ", time_taken=" + time_taken + ", check_out_price=" + check_out_price + ", required_time="
				+ required_time + ", type_of_order=" + type_of_order + ", userName=" + userName + ", phone=" + phone
				+ ", discount_for_early_order=" + discount_for_early_order + ", isBuisness=" + isApproved + ", items="
				+ Arrays.toString(items) + ", shippment=" + shippment + "]";
	}

	public Order(int orderID, int restaurantID, String restaurantName, String time_taken, float check_out_price, String required_time, String type_of_order, String userName, String phone,
			int discount_for_early_order, Item[] items, Shippment shippment, String approved_time, boolean hasArrived, boolean isApproved) {
		this.orderID = orderID;
		this.restaurantID = restaurantID;
		this.restaurantName = restaurantName;
		this.time_taken = time_taken;
		this.check_out_price = check_out_price;
		this.required_time = required_time;
		this.type_of_order = type_of_order;
		this.userName = userName;
		this.phone = phone;
		this.discount_for_early_order = discount_for_early_order;
		this.isApproved = isApproved;
		this.items = items;
		this.shippment = shippment;
		this.approved_time = approved_time;
		this.hasArrived = hasArrived;
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
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
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
	public boolean isApproved() {
		return isApproved;
	}
	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
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

	public String getApproved_time() {
		return approved_time;
	}

	public void setApproved_time(String approved_time) {
		this.approved_time = approved_time;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean getHasArrived() {
		return hasArrived;
	}

	public void setHasArrived(boolean hasArrived) {
		this.hasArrived = hasArrived;
	}
	

}
