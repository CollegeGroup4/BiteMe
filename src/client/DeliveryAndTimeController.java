package client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logic.Item;
import logic.Shippment;

public class DeliveryAndTimeController implements Initializable {
	private CustomerFunctions customerFunctions = new CustomerFunctions();
	/**
	 * This class made for enter delivery informations
	 *
	 * @author Or Biton
	 * @author Einan Choen
	 * @author Tal Yehoshua
	 * @author Moshe Pretze;
	 * @author Tal-chen Ben-Eliyahu
	 * @version January 2022
	 * 
	 */
	public static Shippment shippment;

	public class OrderToSend {
		int orderId;
		int restaurantId;
		String time_taken;
		float check_out_price;
		String required_time;
		String type_of_order;
		int userID;
		String phone;
		int discount_for_early_order;
		boolean isBuisness;
		Item[] items;
		int num_of_people;

	}

	public static OrderToSend orderToSend;

	private boolean errorOccurred = false;

	@FXML
	private Button btnBack;

	@FXML
	private Button btnNext;

	@FXML
	private HBox Nav;

	@FXML
	private Label welcomeLabel;

	@FXML
	private DatePicker dpDate;

	@FXML
	private JFXComboBox<String> cbTime;

	@FXML
	private JFXComboBox<String> cbType;

	@FXML
	private TextField workplace;

	@FXML
	private TextField address;

	@FXML
	private TextField name;

	@FXML
	private TextField phoneNumber;

	@FXML
	private AnchorPane anchorSharedDelivery;

	@FXML
	private TextField numOfPeople;

	@FXML
	private Hyperlink btnHome;

	@FXML
	private Button btnLogout;

	@FXML
	private Label errorLabel;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	/**
	 * Check for valid inputs (empty fields, past date)
	 */
	private void checkValidInputes() {
		if (dpDate.getValue() == null) {
			errorLabelFunc("Please insert date");
			return;
		}
		int yearSelected = dpDate.getValue().getYear();
		int monthSelected = dpDate.getValue().getMonthValue();
		int daySelected = dpDate.getValue().getDayOfMonth();

		LocalDateTime now = LocalDateTime.now();
		int yearNow = now.getYear();
		int monthNow = now.getMonthValue();
		int dayNow = now.getDayOfMonth();

		if (yearSelected - yearNow < 0 || (yearSelected - yearNow >= 0 && monthSelected - monthNow < 0)
				|| (yearSelected - yearNow >= 0 && monthSelected - monthNow >= 0 && daySelected - dayNow < 0)) {

			errorLabelFunc("Please insert  valid  date");
			return;
		}
		if (cbTime.getValue() == null) {
			errorLabelFunc("Please insert time");
			return;
		}
		if (cbType.getValue() == null) {
			errorLabelFunc("Please insert type of order");
			return;
		}
		if (workplace.getText().equals("") && !workplace.isDisable()) {
			errorLabelFunc("Please insert workplace");
			return;
		}
		if (address.getText().equals("") && !address.isDisable()) {
			errorLabelFunc("Please insert address");
			return;
		}
		if (name.getText().equals("") && !name.isDisable()) {
			errorLabelFunc("Please insert name");
			return;
		}
		if (phoneNumber.getText().equals("") && !phoneNumber.isDisable()) {
			errorLabelFunc("Please insert phone number");
			return;
		}
	}

	/**
	 * Setting adaptable error message
	 * 
	 * @param String
	 */
	private void errorLabelFunc(String errorMsg) {
		errorLabel.setVisible(true);
		errorLabel.setText(errorMsg);
		errorOccurred = true;
	}

	/**
	 * Arranging the entered values
	 * 
	 * @throws ParseException
	 */
	private void insertValues() throws ParseException {
		shippment = new Shippment(0, workplace.getText(), address.getText(), name.getText(), cbType.getValue(),
				phoneNumber.getText());
		orderToSend = new OrderToSend();
		orderToSend.orderId = 0;
		orderToSend.restaurantId = ChooseADishController.itemSelected.getRestaurantID();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		orderToSend.time_taken = now.format(formatter);
		orderToSend.required_time = dpDate.getValue() + " " + cbTime.getValue();
		orderToSend.type_of_order = cbType.getValue();
		orderToSend.userID = CustomerPageController.client.getUserID();
		orderToSend.phone = shippment.getPhone();
		orderToSend.discount_for_early_order = calculateDiscount();
		orderToSend.check_out_price = calculateCheckOutPrice();
		orderToSend.items = castItems();
		if (cbType.getValue().equals("Shared Delivery"))
			orderToSend.num_of_people = Integer.valueOf(numOfPeople.getText());
	}

	/**
	 * Cast from ArrayList to an array of items
	 * 
	 * @return
	 */
	private Item[] castItems() {
		Item[] items = new Item[ChooseADishController.itemsSelectedArr.size()];
		for (int i = 0; i < items.length; i++) {
			items[i] = ChooseADishController.itemsSelectedArr.get(i);
		}
		return items;
	}

	/**
	 * Check if the user is allowed to discount because of the pre-order time
	 * 
	 * @return
	 * @throws ParseException
	 */
	private int calculateDiscount() throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime d1 = LocalDateTime.parse(orderToSend.required_time, formatter);
		LocalDateTime d2 = LocalDateTime.parse(orderToSend.time_taken, formatter);
		if (d2.until(d1, ChronoUnit.MINUTES) >= 120) {
			return 10;
		}
		return 0;
	}

	/**
	 * Calculate the order price including the discount (if needed)
	 * 
	 * @return
	 */
	private float calculateCheckOutPrice() {
		float sum = 0;
		for (int i = 0; i < ChooseADishController.itemsSelectedArr.size(); i++) {
			sum += ChooseADishController.itemsSelectedArr.get(i).getPrice();
		}
		sum = sum - sum * orderToSend.discount_for_early_order / 100;
		return sum;
	}

	/**
	 * Initialize display functionalities and values
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeLabel.setText("Welcome, " + CustomerPageController.client.getFirstName());
		customerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);

		errorLabel.setVisible(false);
		cbTime.getItems().addAll("00:00:00", "00:30:00", "01:00:00", "01:30:00", "02:00:00", "02:30:00", "03:00:00", "03:30:00", "04:00:00",
				"04:30:00", "05:00:00", "05:30:00", "06:00:00", "06:30:00", "07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00",
				"10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00",
				"15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00",
				"21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00");

		cbType.getItems().addAll("Take-Away", "Regular Delivery", "Shared Delivery", "Deliver By Robot");
		cbType.setOnAction(e -> typeFunc());
	}

	/**
	 * Function for selecting values of Type's ComboBox
	 */
	private void typeFunc() {
		if (cbType.getValue().equals("Shared Delivery")) {
			anchorSharedDelivery.setVisible(true);
		} else {
			anchorSharedDelivery.setVisible(false);
			numOfPeople.setText(null);
		}
		if (cbType.getValue().equals("Take-Away")) {
			workplace.setDisable(true);
			address.setDisable(true);
			name.setDisable(true);
			phoneNumber.setDisable(true);
		} else {
			workplace.setDisable(false);
			address.setDisable(false);
			name.setDisable(false);
			phoneNumber.setDisable(false);
		}
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
	 * Loading the previous screen (Choose A Dish)
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void goBack(ActionEvent event) throws IOException {
		customerFunctions.reload(event, "ChooseADish.fxml", "Choose A Dish");
	}

	/**
	 * Loading the next screen (Payment)
	 * 
	 * @param event
	 * @throws ParseException
	 */
	@FXML
	void next(ActionEvent event) throws ParseException {
		checkValidInputes();
		if (!errorOccurred)
			insertValues();
		else {
			errorOccurred = false;
			return;
		}
		customerFunctions.reload(event, "Payment.fxml", "Payment");
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
