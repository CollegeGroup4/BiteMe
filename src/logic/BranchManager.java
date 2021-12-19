package logic;

public class BranchManager {
	private String name;
	private String userName;
	private String area;
	
	public BranchManager(String name, String userName, String area) {
		super();
		this.name = name;
		this.userName = userName;
		this.area = area;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	
}
