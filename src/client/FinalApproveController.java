package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import Server.EchoServer;
import Server.Response;
import branchManager.BranchManagerFunctions;
import common.Request;
import guiNew.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import logic.Item;
import logic.Options;
import logic.Order;

/**
 * This class made for display all order details at the final of the process and
 * approve by the user
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */
public class FinalApproveController implements Initializable {
	private CustomerFunctions customerFunctions = new CustomerFunctions();

	@FXML
	private Button btnBack;

	@FXML
	private Button btnConfirm;

	@FXML
	private BorderPane orderDetails;

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
	private Label lblEmail;

	@FXML
	private Label lblCardNum;

	@FXML
	private Label lblExp;

	@FXML
	private Label lblTypeOfCard;

	@FXML
	private HBox Nav;

	@FXML
	private Label welcomeLabel;

	@FXML
	private BorderPane confirmation;

	@FXML
	private ImageView imageV;

	@FXML
	private Hyperlink btnHome;

	@FXML
	private Button btnLogout;

	@FXML
	private Label lableErrorMag;

	@FXML
	private Label orderNum;

	@FXML
	private Button returnHome;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	/**
	 * Initialize display functionalities and values
	 * 
	 * @param URL            arg0
	 * @param ResourceBundle arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeLabel.setText("Welcome, " + CustomerPageController.client.getFirstName());
		customerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);

		setOrderDetailsLabels();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Summary.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		orderDetails.setCenter(root);
	}

	/*
	 * Final order confirmation
	 */
	@FXML
	void confirmOrder(ActionEvent event) {
		orderDetails.setVisible(false);
		btnConfirm.setVisible(false);
		returnHome.setVisible(true);
		confirmation.setVisible(true);
		sentToServer();
		response();
	}

	/*
	 * Display to the screen all information about the order (items, personal info,
	 * delivery info, payment info)
	 */
	private void setOrderDetailsLabels() {
		lblDate.setText(lblDate.getText() + " " + DeliveryAndTimeController.orderToSend.required_time);
		lblOrderType.setText(lblOrderType.getText() + " " + DeliveryAndTimeController.orderToSend.type_of_order);
		lblAddress.setText(lblAddress.getText() + " " + DeliveryAndTimeController.shippment.getAddress());
		lblWorkplace.setText(lblWorkplace.getText() + " " + DeliveryAndTimeController.shippment.getWork_place());
		lblID.setText(lblID.getText() + " " + CustomerPageController.client.getUserID());
		lblName.setText(lblName.getText() + " " + CustomerPageController.client.getFirstName() + " "
				+ CustomerPageController.client.getLastName());
		lblPhone.setText(lblPhone.getText() + " " + CustomerPageController.client.getPhone());
		lblEmail.setText(lblEmail.getText() + " " + CustomerPageController.client.getEmail());
		if (!PaymentController.payment.cardNum.equals(""))
			lblCardNum.setText("***" + PaymentController.payment.cardNum.substring(
					PaymentController.payment.cardNum.length() - 4, PaymentController.payment.cardNum.length()));
		if (PaymentController.payment.expMonth != null && PaymentController.payment.expYear != null)
			lblExp.setText(
					lblExp.getText() + PaymentController.payment.expMonth + ", " + PaymentController.payment.expYear);
		if (PaymentController.payment.cardType != null)
			lblTypeOfCard.setText(lblTypeOfCard.getText() + " " + PaymentController.payment.cardType);
	}

	/**
	 * Sending new order to DataBase
	 * 
	 */
	void sentToServer() {
		for (Item item : DeliveryAndTimeController.orderToSend.items) {
			if(item.getOptions() == null) {
				Options[] option = new Options[1];
				option[0] = new Options("None", "None", 0, item.getItemID(), false);
				item.setOptions(option);
			}
		}
		Order order = new Order(DeliveryAndTimeController.orderToSend.orderId,
				DeliveryAndTimeController.orderToSend.restaurantId,
				ChooseRestaurantController.restaurantSelected.getName(),
				DeliveryAndTimeController.orderToSend.time_taken, DeliveryAndTimeController.orderToSend.check_out_price,
				DeliveryAndTimeController.orderToSend.required_time,
				DeliveryAndTimeController.orderToSend.type_of_order, CustomerPageController.client.getUserName(),
				CustomerPageController.client.getPhone(),
				DeliveryAndTimeController.orderToSend.discount_for_early_order,
				DeliveryAndTimeController.orderToSend.items, DeliveryAndTimeController.shippment, null, false, false);
		
		Gson gson = new Gson();
		Request request = new Request();
		request.setPath("/orders");
		request.setMethod("POST");

		request.setBody(gson.toJson(order));
		try {
			ClientUI.chat.accept(gson.toJson(request));
		} catch (NullPointerException e) {
			System.out.println("get menus by restaurand ID didn't work");
		}
	}

	/**
	 * Receiving from Data: Order number
	 */
	private void response() {
		Gson gson = new Gson();
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201) // if there was an error then need to print an
																	// ERORR!
			lableErrorMag.setText(response.getDescription()); // TODO- error massage
		else {
			JsonElement body = EchoServer.gson.fromJson((String) response.getBody(), JsonElement.class);
			int orderID = gson.fromJson(body.getAsJsonObject().get("orderID"), Integer.class);
			System.out.println("orderID: " + orderID);
			orderNum.setText(" " + orderID);
		}

	}

	/**
	 * Loading the previous screen (Payment)
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void goBack(ActionEvent event) throws IOException {
		customerFunctions.reload(event, "Payment.fxml", "Payment");
	}

	/**
	 * Loading home page
	 * 
	 * @param event
	 */
	@FXML
	void home(ActionEvent event) {
		String role = LoginController.role;
		switch (role) {
		case "Branch Manager":
			BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();
			branchManagerFunctions.home(event);
			break;
		case "Client":
			customerFunctions.home(event);
			break;
		}
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
