package logic;

public class BusinessAccount extends Account {

	private float monthlyBillingCeiling;
	private Boolean isApproved;
	private String businessName;
	private float currentSpent;
	private String w4c_card;

	public BusinessAccount(int userID, String userName, String password, String firstName, String lastName,
			String email, String role, String phone, String status, boolean isBusiness, int branch_manager_ID,
			String area, int debt, String w4c_card, float monthlyBillingCeiling, Boolean isApproved,
			String businessName, float currentSpent) {
		super(userID, userName, password, firstName, lastName, email, role, phone, status, isBusiness,
				branch_manager_ID, area, debt);
		this.monthlyBillingCeiling = monthlyBillingCeiling;
		this.isApproved = isApproved;
		this.businessName = businessName;
		this.currentSpent = currentSpent;
		this.w4c_card = w4c_card;
	}

	public float getMonthlyBillingCeiling() {
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

	public String getW4c_card() {
		return w4c_card;
	}

	public void setW4c_card(String w4c_card) {
		this.w4c_card = w4c_card;
	}
}