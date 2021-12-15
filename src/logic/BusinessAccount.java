package logic;

public class BusinessAccount extends Account {

	private int userID;
	private float monthBillingCeiling;
	private Boolean isApproved;
	private String businessName;
	private float currentSpent;

	public BusinessAccount(int userID, String userName, String password, String firstName, String lastName,
			String email, String role, String phone, String status, boolean isBusiness,int branch_manager_ID, String area, int debt,
			String w4c_card, float monthBillingCeiling, Boolean isApproved, String businessName, float currentSpent) {
		super(userID, userName, password, firstName, lastName, email, role, phone, status, isBusiness,branch_manager_ID, area,
				debt, w4c_card);
		this.userID = userID;
		this.monthBillingCeiling = monthBillingCeiling;
		this.isApproved = isApproved;
		this.businessName = businessName;
		this.currentSpent = currentSpent;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public float getMonthBillingCeiling() {
		return monthBillingCeiling;
	}

	public void setMonthBillingCeiling(float monthBillingCeiling) {
		this.monthBillingCeiling = monthBillingCeiling;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public float getCurrentSpent() {
		return currentSpent;
	}

	public void setCurrentSpent(float currentSpent) {
		this.currentSpent = currentSpent;
	}

}
