package logic;

public class Account {
	private int AccountID;
	private String UserName;
	private String firstName;
	private String lastName;
	private String email;
	private String type;
	private String phone;
	private boolean isBusiness;
	private String status;
	private String w4c_code;
	private int branch_manager_ID;
	private String area;
	
	public Account(int accountID, String userName, String firstName, String lastName, String email, String type,
			String phone, boolean isBusiness, String status, String w4c_code, int branch_manager_ID, String area) {
		AccountID = accountID;
		UserName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.type = type;
		this.phone = phone;
		this.isBusiness = isBusiness;
		this.status = status;
		this.w4c_code = w4c_code;
		this.branch_manager_ID = branch_manager_ID;
		this.area = area;
	}
	public int getAccountID() {
		return AccountID;
	}
	public void setAccountID(int accountID) {
		AccountID = accountID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isBusiness() {
		return isBusiness;
	}
	public void setBusiness(boolean isBusiness) {
		this.isBusiness = isBusiness;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getW4c_code() {
		return w4c_code;
	}
	public void setW4c_code(String w4c_code) {
		this.w4c_code = w4c_code;
	}
	public int getBranch_manager_ID() {
		return branch_manager_ID;
	}
	public void setBranch_manager_ID(int branch_manager_ID) {
		this.branch_manager_ID = branch_manager_ID;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
}
