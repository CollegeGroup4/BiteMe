package logic;

public class PrivateAccount extends Account {
	private String creditCardNumber;
	private String creditCardCVV;
	private String creditCardExpDate;
	private String w4c_card;

	public PrivateAccount(int userID, String userName, String password, String firstName, String lastName, String email,
			String role, String phone, String status, boolean isBusiness, int branch_manager_ID, String area, int debt,
			String w4c_card, String creditCardNumber, String creditCardCVV, String creditCardExpDate) {
		super(userID, userName, password, firstName, lastName, email, role, phone, status, isBusiness,
				branch_manager_ID, area, debt);
		this.creditCardNumber = creditCardNumber;
		this.creditCardCVV = creditCardCVV;
		this.creditCardExpDate = creditCardExpDate;
		this.w4c_card = w4c_card;
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

	public String getW4c_card() {
		return w4c_card;
	}

	public void setW4c_card(String w4c_card) {
		this.w4c_card = w4c_card;
	}
}
