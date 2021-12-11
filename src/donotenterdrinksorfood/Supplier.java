package donotenterdrinksorfood;

public class Supplier {
	private int ID;
	private boolean isApproved;
	private int BranchManagerID;
	private String supplierName;
	private String area;
	public String photo; // change from byte[] to String!
	public Supplier(int ID, boolean isApproved, int branchManagerID, String supplierName, String area, String photo) {
		this.ID = ID;
		this.isApproved = isApproved;
		BranchManagerID = branchManagerID;
		this.supplierName = supplierName;
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
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
