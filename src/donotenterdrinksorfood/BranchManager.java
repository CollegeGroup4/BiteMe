package donotenterdrinksorfood;

public class BranchManager {
	private String name;
	private String AccountID;
	private String area;

	public BranchManager(String name, String accountID, String area) {
		this.name = name;
		AccountID = accountID;
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountID() {
		return AccountID;
	}

	public void setAccountID(String accountID) {
		AccountID = accountID;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}
