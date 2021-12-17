package logic;

public class Employee {
	private String name;
	private int w4c_card;
	private boolean isApproved;
	
	public Employee(String name, int w4c_card, boolean isApproved) {
		this.name = name;
		this.w4c_card = w4c_card;
		this.isApproved = isApproved;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getW4c_card() {
		return w4c_card;
	}
	public void setW4c_card(int w4c_card) {
		this.w4c_card = w4c_card;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

}
