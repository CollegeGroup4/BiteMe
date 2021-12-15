package logic;

public class BusinessAccount {
	
	private int userID;
	private float monthBillingCeiling;
	private Boolean isApproved;
	private String businessName;
	private float currentSpent;
	private String w4c_card;
	
	public BusinessAccount(int userID, float monthBillingCeiling, Boolean isApproved, String businessName,
			float currentSpent, String w4c_card) {
		super();
		this.userID = userID;
		this.monthBillingCeiling = monthBillingCeiling;
		this.isApproved = isApproved;
		this.businessName = businessName;
		this.currentSpent = currentSpent;
		this.w4c_card = w4c_card;
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
	public String getW4c_card() {
		return w4c_card;
	}
	public void setW4c_card(String w4c_card) {
		this.w4c_card = w4c_card;
	}
	
	
	
}
