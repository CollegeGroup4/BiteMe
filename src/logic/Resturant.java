package logic;

public class Resturant {
	private int ID;
	private boolean isApproved;
	private int BranchManagerID;
	private String name;
	private String area;
	public byte[] photo;
	
	public Resturant(int ID, boolean isApproved, int branchManagerID, String name, String area, byte[] photo) {
		this.ID = ID;
		this.isApproved = isApproved;
		BranchManagerID = branchManagerID;
		this.name = name;
		this.area = area;
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
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
}
