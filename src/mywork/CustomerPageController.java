package mywork;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import temporaryDatabase.UserTemp;
import temporaryDatabase.myOwnDatabase;

public class CustomerPageController /* implements Initializable */ {

	@FXML
	private HBox Nav;

	@FXML
	private Button btnLogout;

	@FXML
	private Hyperlink btnHome;

    @FXML
    private Label welcomeLabel;

	@FXML
	private Button btnMyOrders;

	@FXML
	private Button btnOrderFood;

	@FXML
	private HBox Nav1;

	@FXML
	private ImageView imageBiteme1;

	@FXML
	private ImageView imageBiteme2;

	@FXML
	private ImageView imageFacebook;

	@FXML
	private ImageView imageInstergram;

	@FXML
	private ImageView imageWhatsapp;

	FXMLLoader loader = new FXMLLoader();
	public static UserTemp user;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/mywork/CustomerPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Customer Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void MyOrders(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/mywork/MyOrders.fxml").openStream());
		MyOrdersController myOrdersController = loader.getController();
		myOrdersController.insertOrdersToTbl();
		myOrdersController.setName();

		Scene scene = new Scene(root);
		primaryStage.setTitle("My Orders Page");

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void OrderFood(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/mywork/ChooseRestaurant.fxml").openStream());
		ChooseRestaurantController chooseRestaurantController = loader.getController();
		chooseRestaurantController.setName();

		Scene scene = new Scene(root);
		primaryStage.setTitle("Choose Restaurant Page");

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void setUser(UserTemp user) {
		CustomerPageController.user = user;
		welcomeLabel.setText("Welcome, " + CustomerPageController.user.getName());
	}

}
