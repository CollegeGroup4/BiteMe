package logic;

public class PrivateAccount extends Account {
	private int userID;
	private String creditCardNumber;
	private String creditCardCVV;
	private String creditCardExpDate;

	public PrivateAccount(int userID, String userName, String password, String firstName, String lastName, String email,
			String role, String phone, String status, int branch_manager_ID, String area, int debt, String w4c_card,
			String creditCardNumber, String creditCardCVV, String creditCardExpDate) {
		super(userID, userName, password, firstName, lastName, email, role, phone, status, branch_manager_ID, area,
				debt, w4c_card);
		this.userID = userID;
		this.creditCardNumber = creditCardNumber;
		this.creditCardCVV = creditCardCVV;
		this.creditCardExpDate = creditCardExpDate;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getCreditCardCVV() {
		return creditCardCVV;
	}

	public void setCreditCardCVV(String creditCardCVV) {
		this.creditCardCVV = creditCardCVV;
	}

	public String getCreditCardExpDate() {
		return creditCardExpDate;
	}

	public void setCreditCardExpDate(String creditCardExpDate) {
		this.creditCardExpDate = creditCardExpDate;
	}

}
