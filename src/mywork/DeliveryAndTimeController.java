package mywork;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXComboBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Item;
import logic.Shippment;

public class DeliveryAndTimeController implements Initializable {

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
	void goBack(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		ChooseADishController aFrame = new ChooseADishController();
		aFrame.start(primaryStage);
	}

	@FXML
	void next(ActionEvent event) throws ParseException {
		insertValues();
		((Node) event.getSource()).getScene().getWindow().hide();
		PaymentController aFrame = new PaymentController();
		try {
			aFrame.start(new Stage());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void insertValues() throws ParseException {
		shippment = new Shippment(0, workplace.getText(), address.getText(), name.getText(), cbType.getValue(),
				phoneNumber.getText());
		orderToSend = new OrderToSend();
		orderToSend.orderId = 0;
		orderToSend.restaurantId = ChooseADishController.itemSelected.getRestaurantID();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime now = LocalDateTime.now();
		orderToSend.time_taken = now.format(formatter);
		orderToSend.required_time = dpDate.getValue() + " " + cbTime.getValue();
		orderToSend.type_of_order = cbType.getValue();
		// orderToSend.userID=CustomerPageController.user.getId(); ** implement when
		// connected to database
		orderToSend.phone = shippment.getPhone();
		orderToSend.discount_for_early_order = calculateDiscount();
		orderToSend.check_out_price = calculateCheckOutPrice();
		System.out.println(orderToSend.check_out_price);
		orderToSend.items = castItems();
		if (cbType.getValue().equals("Shared Delivery"))
			orderToSend.num_of_people = Integer.valueOf(numOfPeople.getText());

	}

	private Item[] castItems() {

		Item[] items = new Item[ChooseADishController.itemsSelectedArr.size()];
		for (int i = 0; i < items.length; i++) {
			items[i] = ChooseADishController.itemsSelectedArr.get(i);
		}

		return items;
	}

	private int calculateDiscount() throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime d1 = LocalDateTime.parse(orderToSend.required_time, formatter);
		LocalDateTime d2 = LocalDateTime.parse(orderToSend.time_taken, formatter);
		if (d2.until(d1, ChronoUnit.MINUTES) >= 120) {
			return 10;
		}
		return 0;
	}

	private float calculateCheckOutPrice() {
		float sum = 0;
		for (int i = 0; i < ChooseADishController.itemsSelectedArr.size(); i++) {
			sum += ChooseADishController.itemsSelectedArr.get(i).getPrice();
		}
		sum = sum - sum * orderToSend.discount_for_early_order / 100;
		return sum;
	}

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/mywork/DeliveryAndTime.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Delivery And Time Page");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbTime.getItems().addAll("00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00",
				"04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30",
				"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00",
				"15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30",
				"21:00", "21:30", "22:00", "22:30", "23:00", "23:30");

		cbType.getItems().addAll("Take-Away", "Regular Delivery", "Shared Delivery", "Deliver By Robot");
		cbType.setOnAction(e -> sharedTypeFunc());
	}

	private void sharedTypeFunc() {
		if (cbType.getValue().equals("Shared Delivery")) {
			anchorSharedDelivery.setVisible(true);
		} else {
			anchorSharedDelivery.setVisible(false);
			numOfPeople.setText(null);
		}

	}

}