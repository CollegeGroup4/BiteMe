package logic;

public class Employee {
	private String name;
	private int w4c_card;
	public Employee(String name, int w4c_card) {
		this.name = name;
		this.w4c_card = w4c_card;
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
}