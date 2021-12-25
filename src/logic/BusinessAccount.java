package logic;

public class BusinessAccount extends Account {

	private int monthlyBillingCeiling;
	private Boolean isApproved;
	private String businessName;
	private float currentSpent;
	private String W4C;
	
	public BusinessAccount(int userID, String userName, String password, String firstName, String lastName,
			String email, String role, String phone, String status, boolean isBusiness, int branch_manager_ID,
			String area, int debt, int monthlyBillingCeiling, Boolean isApproved, String businessName,
			float currentSpent, String w4c) {
		super(userID, userName, password, firstName, lastName, email, role, phone, status, isBusiness,
				branch_manager_ID, area, debt);
		this.monthlyBillingCeiling = monthlyBillingCeiling;
		this.isApproved = isApproved;
		this.businessName = businessName;
		this.currentSpent = currentSpent;
		W4C = w4c;
	}
	public String getW4C() {
		return W4C;
	}
	public void setW4C(String w4c) {
		W4C = w4c;
	}
	public int getMonthlyBillingCeiling() {
		return monthlyBillingCeiling;
	}
	public void setMonthlyBillingCeiling(int monthlyBillingCeiling) {
		this.monthlyBillingCeiling = monthlyBillingCeiling;
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
