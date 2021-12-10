package logic;

public class Shippment {
	private String work_place;
	private String address;
	private String receiver_name;
	private String delivery;
	private String phone;
	public Shippment(String work_place, String address, String receiver_name, String delivery, String phone) {
		this.work_place = work_place;
		this.address = address;
		this.receiver_name = receiver_name;
		this.delivery = delivery;
		this.phone = phone;
	}
	public String getWork_place() {
		return work_place;
	}
	public void setWork_place(String work_place) {
		this.work_place = work_place;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReceiver_name() {
		return receiver_name;
	}
	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}
	public String getDelivery() {
		return delivery;
	}
	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
