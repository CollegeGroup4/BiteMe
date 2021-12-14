package logic;

public class Account {
	private int accountID;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String role;
	private String phone;
	private String status;
	private int branch_manager_ID;
	private String area;
	private int debt;
	public Account(int accountID, String userName, String password, String firstName, String lastName, String email,
			String role, String phone, String status, int branch_manager_ID, String area, int debt) {
		this.accountID = accountID;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.phone = phone;
		this.status = status;
		this.branch_manager_ID = branch_manager_ID;
		this.area = area;
		this.debt = debt;
	}
	public int getUserID() {
		return accountID;
	}
	public void setUserID(int userID) {
		this.accountID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public int getDebt() {
		return debt;
	}
	public void setDebt(int debt) {
		this.debt = debt;
	}

	
	
}
