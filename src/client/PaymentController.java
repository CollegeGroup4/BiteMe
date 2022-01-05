package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
/**
 * This class made for enter payment method
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */
public class PaymentController implements Initializable {

	public static PaymentSaved payment; //
	public float priceOfShippment = 0;
	private boolean errorOccurred = false;
	public boolean isOpen = false;
	private CustomerFunctions customerFunctions = new CustomerFunctions();

	class PaymentSaved {
		String cardType;
		String cardNum;
		String cardSecurity;
		String expMonth;
		String expYear;

		@Override
		public String toString() {
			return cardType + ", " + cardNum + " exp: " + expMonth + ", " + expYear;
		}
	}

	@FXML
	private Button btnBack;

	@FXML
	private Button btnNext;

	@FXML
	private CheckBox w4c;

	@FXML
	private CheckBox creditCard;

	@FXML
	private CheckBox credit; // Not used because I do not receive this information

	@FXML
	private Label expLabel;

	@FXML
	private ComboBox<String> expMonth;

	@FXML
	private ComboBox<String> expYear;

	@FXML
	private TextField securityCode;

	@FXML
	private TextField cardNumber;

	@FXML
	private CheckBox visa;

	@FXML
	private CheckBox americanExpress;

	@FXML
	private CheckBox masterCard;

	@FXML
	private Label lblShippmentPrice;

	@FXML
	private Label lblDiscountPrice;

	@FXML
	private Label lblSubTotalPrice;

	@FXML
	private Label totalPrice;

	@FXML
	private HBox Nav;

	@FXML
	private BorderPane orderDetails;

	@FXML
	private Label welcomeLabel;

	@FXML
	private Button btnHideOrder;

	@FXML
	private ImageView shoppingCart;

	@FXML
	private Label lblDate;

	@FXML
	private Label lblOrderType;

	@FXML
	private Label lblWorkplace;

	@FXML
	private Label lblAddress;

	@FXML
	private Label lblID;

	@FXML
	private Label lblName;

	@FXML
	private Label lblPhone;

	@FXML
	private Label errorLabel;

	@FXML
	private Hyperlink btnHome;

	@FXML
	private Button btnLogout;
	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	/**
	 * Checks for valid inputs or empty fields
	 */
	private void checkValidInputes() {
		if (creditCard.isSelected()) {
			if (cardNumber.getText().equals("")) {
				errorLabelFunc("Please insert card number");
				return;
			}
			if (cardNumber.getText().length() < 4) {
				errorLabelFunc("Card number too short");
				return;
			}
			if (cardNumber.getText().length() > 16) {
				errorLabelFunc("Card number too long");
				return;
			}
			if (securityCode.getText().equals("")) {
				errorLabelFunc("Please insert security code");
				return;
			}
			if (!(visa.isSelected() || masterCard.isSelected() || americanExpress.isSelected())) {
				errorLabelFunc("Please select card type");
				return;
			}
			if (expMonth.getValue() == null || expYear.getValue() == null) {
				errorLabelFunc("Please select expire date");
				return;
			}
		}

	}

	/**
	 * Initialize display functionalities and values
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeLabel.setText("Welcome, " + CustomerPageController.client.getFirstName());
		customerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);

		payment = new PaymentSaved();
		errorLabel.setVisible(false);
		setPrices();
		creditCard.setOnAction(e -> turnOnOfCreditCard());
		shoppingCart.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> handeleClickShoppingCart(e));
		expMonth.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August",
				"September", "October", "November", "December");
		expYear.getItems().addAll("2022", "2023", "2024", "2025", "2026", "2027");
		visa.setOnAction(e -> visaSelected());
		masterCard.setOnAction(e -> masterCardSelected());
		americanExpress.setOnAction(e -> americanExpressSelected());
	}

	/**
	 * Save payment details from the fields
	 */
	private void insertValues() {
		payment.cardNum = cardNumber.getText();
		payment.expMonth = expMonth.getValue();
		payment.expYear = expYear.getValue();
		payment.cardSecurity = securityCode.getText();
	}

	/**
	 * Function to display Shopping cart
	 * 
	 * @param event
	 */
	public void handeleClickShoppingCart(MouseEvent event) {
		if (isOpen) {
			orderDetails.setCenter(null);
			orderDetails.toBack();
		} else {
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("Summary.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			orderDetails.setCenter(root);
			orderDetails.toFront();
		}
		isOpen = !isOpen;
	}

	/**
	 * Functionality when selected AmericanExpress Credit Card
	 */
	private void americanExpressSelected() {
		payment.cardType = "American Express";
		visa.setSelected(false);
		masterCard.setSelected(false);
	}

	/**
	 * Functionality when selected MasterCard Credit Card
	 */
	private void masterCardSelected() {
		payment.cardType = "Master Card";
		visa.setSelected(false);
		americanExpress.setSelected(false);
	}

	/**
	 * Functionality when selected visa Credit Card
	 */
	private void visaSelected() {
		payment.cardType = "Visa";
		masterCard.setSelected(false);
		americanExpress.setSelected(false);
	}

	/**
	 * Displays the prices that the user should pay (Sub Total price,
	 * Shipment,Discount, Total price)
	 */
	private void setPrices() {
		float discount = DeliveryAndTimeController.orderToSend.discount_for_early_order / (float) 100;
		float sumAfterDiscount = DeliveryAndTimeController.orderToSend.check_out_price;
		float sumBeforeDiscount = sumAfterDiscount / (1 - discount);
		lblSubTotalPrice.setText(String.format(" $ %.2f", sumBeforeDiscount));
		checkShippmentPrice();
		lblShippmentPrice.setText(String.format(" $ %.2f", priceOfShippment));
		lblDiscountPrice.setText(String.format(" $ %.2f", discount * sumBeforeDiscount));
		sumAfterDiscount += priceOfShippment;
		totalPrice.setText(String.format(" $ %.2f", sumAfterDiscount));
	}

	/**
	 * Setting adaptable error message
	 * 
	 * @param errorMsg
	 */
	private void errorLabelFunc(String errorMsg) {
		errorLabel.setVisible(true);
		errorLabel.setText(errorMsg);
		errorOccurred = true;
	}

	/**
	 * Calculate shipment price
	 */
	private void checkShippmentPrice() {
		if (DeliveryAndTimeController.orderToSend.type_of_order.equals("Take-Away"))
			priceOfShippment = 0;
		if (DeliveryAndTimeController.orderToSend.type_of_order.equals("Regular Delivery"))
			priceOfShippment = 25;
		if (DeliveryAndTimeController.orderToSend.type_of_order.equals("Shared Delivery")) {
			if (DeliveryAndTimeController.orderToSend.num_of_people > 2)
				priceOfShippment = 15;
			else
				priceOfShippment = 25 - 5 * DeliveryAndTimeController.orderToSend.num_of_people;
		}
		if (DeliveryAndTimeController.orderToSend.type_of_order.equals("Deliver By Robot"))
			priceOfShippment = 0;

	}

	/*
	 * Turn On/Off credit card options when selected
	 */
	private void turnOnOfCreditCard() {
		boolean b = true;
		if (creditCard.isSelected())
			b = false;
		visa.setDisable(b);
		masterCard.setDisable(b);
		americanExpress.setDisable(b);
		cardNumber.setDisable(b);
		securityCode.setDisable(b);
		expLabel.setDisable(b);
		expMonth.setDisable(b);
		expYear.setDisable(b);
	}

	/**
	 * Loading the previous screen (Delivery And Time)
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void goBack(ActionEvent event) throws IOException {
		customerFunctions.reload(event, "DeliveryAndTime.fxml", "Delivery And Time");
	}

	/**
	 * Loading the next screen (Final Approve)
	 * 
	 * @param event
	 */
	@FXML
	void next(ActionEvent event) {
		checkValidInputes();
		if (!errorOccurred)
			insertValues();
		else {
			errorOccurred = false;
			return;
		}
		customerFunctions.reload(event, "FinalApprove.fxml", "Final Approve");
	}

	/**
	 * Loading home page
	 * 
	 * @param event
	 */
	@FXML
	void home(ActionEvent event) {
		customerFunctions.home(event);
	}

	/**
	 * Disconnect the user from the system
	 * 
	 * @param event
	 */
	@FXML
	void logout(ActionEvent event) {
		customerFunctions.logout(event);
	}

}
