package client;

public class OrderB {
	private String restaurantName;
	private String time_taken;
	private double check_out_price;
	private String course;
	
	public OrderB(String restaurantName, String time_taken, double d, String course) {
		super();
		this.restaurantName = restaurantName;
		this.time_taken = time_taken;
		this.check_out_price = d;
		this.course = course;
	}
	
	public String getCourse() {
		return course;
	}

	@Override
	public String toString() {
		return "OrderB [restaurantName=" + restaurantName + ", time_taken=" + time_taken + ", check_out_price="
				+ check_out_price + ", course=" + course + "]";
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getTime_taken() {
		return time_taken;
	}
	public void setTime_taken(String time_taken) {
		this.time_taken = time_taken;
	}
	public double getCheck_out_price() {
		return check_out_price;
	}
	public void setCheck_out_price(float check_out_price) {
		this.check_out_price = check_out_price;
	}
}