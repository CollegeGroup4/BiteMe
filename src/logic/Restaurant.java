package logic;

public class Restaurant {
	private int id;
	private boolean isApproved;
	private int BranchManagerID;
	private String name;
	private String area;
	private String type;
	private String userName; /// Supplier or Moderator
	private String photo;
	private String address;
	private String description;

	public Restaurant(int id, boolean isApproved, int branchManagerID, String name,
					  String area, String type, String userName, String photo, String address, String description) {
		this.id = id;
		this.isApproved = isApproved;
		BranchManagerID = branchManagerID;
		this.name = name;
		this.area = area;
		this.type = type;
		this.userName = userName;
		this.photo = photo;
		this.address = address;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean approved) {
		isApproved = approved;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", isApproved=" + isApproved + ", BranchManagerID=" + BranchManagerID
				+ ", name=" + name + ", area=" + area + ", type=" + type + ", userName=" + userName + ", photo=" + photo
				+ ", address=" + address + ", description=" + description + "]";
	}
}
