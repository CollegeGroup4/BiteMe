package logic;

public class Employee {
	private String name;
	private boolean isApproved;

	public Employee(String name, int w4c_card, boolean isApproved) {
		this.name = name;
		this.isApproved = isApproved;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

}
