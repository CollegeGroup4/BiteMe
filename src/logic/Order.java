package logic;

import java.sql.Time;

public class Order {
	private String resturant;
	private String orderAddress;
	private String phoneNumber;
	private int orderNum;
	private Time orderTime;
	private String orderType;

	public Order(String resturant, String orderAddress, String phoneNumber, Time orderTime,
			String orderType) {
		super();
		this.resturant = resturant;
		this.orderAddress = orderAddress;
		this.phoneNumber = phoneNumber;
		this.orderTime = orderTime;
		this.orderType = orderType;

	}

	public String getResturant() {
		return resturant;
	}

	public void setResturant(String resturant) {
		this.resturant = resturant;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public Time getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Time orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
//	@Override
//	public String toString() {
//		StringBuffer tostring = new StringBuffer();
//		tostring.append("[");
//		tostring.append(getResturant() + ", ");
//		tostring.append(getOrderAddress() + ", ");
//		tostring.append(getPhoneNumber() + ", ");
//		tostring.append(getOrderTime() + ", ");
//		tostring.append(getOrderType() + ", ");
//		tostring.append(getOrderNum() + "]");
//		return tostring.toString();
//	}
	@Override
	public String toString(){
		return String.format("%s %s %s %s %s %s\n",getResturant(),getOrderAddress(),getPhoneNumber(),getOrderTime(), getOrderType(), getOrderNum());
	}

}
