package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import Server.EchoServer;
import Server.Response;
import common.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Item;
import logic.Menu;

public class FinalApproveController implements Initializable {

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

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/client/FinalApprove.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Final Approve");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void confirmOrder(ActionEvent event) {
		orderDetails.setVisible(false);
		confirmation.setVisible(true);
		

	}

	@FXML
	void goBack(ActionEvent event) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		PaymentController aFrame = new PaymentController();
		aFrame.start(primaryStage);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setOrderDetailsLabels();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Summary.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		orderDetails.setCenter(root);

	}

	private void setOrderDetailsLabels() {
		lblDate.setText(lblDate.getText() + " " + DeliveryAndTimeController.orderToSend.required_time);
		lblOrderType.setText(lblOrderType.getText() + " " + DeliveryAndTimeController.orderToSend.type_of_order);
		lblAddress.setText(lblAddress.getText() + " " + DeliveryAndTimeController.shippment.getAddress());
		lblWorkplace.setText(lblWorkplace.getText() + " " + DeliveryAndTimeController.shippment.getWork_place());
		// lblID.setText(lblID.getText() + " " + CustomerPageController.user.getId());
		// *** Implement when connect to the DB
		// lblName.setText(lblName.getText() + " " +
		// CustomerPageController.user.getName()); *** Implement when connect to the DB
		lblPhone.setText(lblPhone.getText() + " " + DeliveryAndTimeController.shippment.getPhone());
		if (! PaymentController.payment.cardNum.equals(""))
			lblCardNum.setText("***" + PaymentController.payment.cardNum.substring(
					PaymentController.payment.cardNum.length() - 4, PaymentController.payment.cardNum.length()));
		if (PaymentController.payment.expMonth != null && PaymentController.payment.expYear != null)
			lblExp.setText(
					lblExp.getText() + PaymentController.payment.expMonth + ", " + PaymentController.payment.expYear);
		if (PaymentController.payment.cardType != null)
			lblTypeOfCard.setText(lblTypeOfCard.getText() + " " + PaymentController.payment.cardType);
	}
	
	void sentToServer() {
		Item[] itemsToSend;
		Gson gson = new Gson();
		Request request = new Request();
		request.setPath("/orders");
		request.setMethod("POST");
		JsonElement body = gson.toJsonTree(new Object());
// TODO - need to sent the order, it is in the delivery and paymant class   
		body.getAsJsonObject().addProperty("items", CustomerPageController.client.isBusiness());
		
		request.setBody(gson.toJson(body));
		String jsonUser = gson.toJson(request);
		try {
			System.out.println(jsonUser);
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("get menus by restaurand ID didn't work");
		}
	}

	private void response() {
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201) // if there was an error then need to print an ERORR!
			 lableErrorMag.setText(response.getDescription()); // TODO- error massage
//			System.out.println(response.getDescription());
		else {
			JsonElement a= EchoServer.gson.fromJson((String) response.getBody(), JsonElement.class);
			Menu[] menuList = EchoServer.gson.fromJson(a.getAsJsonObject().get("menues"), Menu[].class);
			Item[] itemList = EchoServer.gson.fromJson(a.getAsJsonObject().get("items"), Item[].class);
			System.out.println("restaurantList " + menuList);

			}

		}
	

}
