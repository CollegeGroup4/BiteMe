package logic;

public class Restaurant {
	private int ID;
	private boolean isApproved;
	private int BranchManagerID;
	private String name;
	private String area;
	private String type;
	private int userID;
	///////////////////Use fileStream sending and saving pngs
	public String photo;
	
	public Restaurant(int iD, boolean isApproved, int branchManagerID, String name, String area, String type,
			int userID, String photo) {
		super();
		ID = iD;
		this.isApproved = isApproved;
		BranchManagerID = branchManagerID;
		this.name = name;
		this.area = area;
		this.type = type;
		this.userID = userID;
		this.photo = photo;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public int getBranchManagerID() {
		return BranchManagerID;
	}
	public void setBranchManagerID(int branchManagerID) {
		BranchManagerID = branchManagerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
		
}
