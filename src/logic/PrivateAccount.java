package logic;

public class PrivateAccount {
	private int userID;
	private String creditCardNumber;
	private String creditCardCVV;
	private String creditCardExpDate;
	private String w4c_card;
	public PrivateAccount(int userID, String creditCardNumber, String creditCardCVV, String creditCardExpDate,
			String w4c_card) {
		super();
		this.userID = userID;
		this.creditCardNumber = creditCardNumber;
		this.creditCardCVV = creditCardCVV;
		this.creditCardExpDate = creditCardExpDate;
		this.w4c_card = w4c_card;
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
	public String getW4c_card() {
		return w4c_card;
	}
	public void setW4c_card(String w4c_card) {
		this.w4c_card = w4c_card;
	}
	
	

}
