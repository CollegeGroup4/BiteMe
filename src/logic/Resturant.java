package logic;

public class Resturant {
	private int ID;
	private boolean isApproved;
	private int BranchManagerID;
	private String name;
	private String area;
	private String type;
	private int accountID;
	public byte[] photo;
	public Resturant(int iD, boolean isApproved, int branchManagerID, String name, String area, String type,
			int accountID, byte[] photo) {
		super();
		ID = iD;
		this.isApproved = isApproved;
		BranchManagerID = branchManagerID;
		this.name = name;
		this.area = area;
		this.type = type;
		this.accountID = accountID;
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
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	
}