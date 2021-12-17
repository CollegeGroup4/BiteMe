package logic;

public class BusinessAccount extends Account {

	private int monthlyBillingCeiling;
	private Boolean isApproved;
	private String businessName;
	private float currentSpent;
	
	public BusinessAccount(int userID, String userName, String password, String firstName, String lastName,
			String email, String role, String phone, String status, boolean isBusiness, int branch_manager_ID,
			String area, int debt, String w4c_card, int monthlyBillingCeiling, Boolean isApproved, String businessName,
			float currentSpent) {
		super(userID, userName, password, firstName, lastName, email, role, phone, status, isBusiness,
				branch_manager_ID, area, debt, w4c_card);
		this.monthlyBillingCeiling = monthlyBillingCeiling;
		this.isApproved = isApproved;
		this.businessName = businessName;
		this.currentSpent = currentSpent;
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
