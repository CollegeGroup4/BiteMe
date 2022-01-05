package client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Account;

public class CustomerPageController implements Initializable {
	public static Account client;
	CustomerFunctions customerFunctions=new CustomerFunctions();
	/**
	 * This is the main screen for the customer
	 * made for display all options to the customer
	 *
	 * @author Or Biton
	 * @author Einan Choen
	 * @author Tal Yehoshua
	 * @author Moshe Pretze;
	 * @author Tal-chen Ben-Eliyahu
	 * @version January 2022
	 * 
	 */
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
/**
 * 	Setting value for the welcome label
 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		welcomeLabel.setText("Welcome, " + CustomerPageController.client.getFirstName());
	}
/**
 * A function that opens a page to view all orders for the user
 * @param event
 * @throws Exception
 */
	@FXML
	void MyOrders(ActionEvent event) throws Exception {
		customerFunctions.reload(event, "ChooseADish.fxml", "Choose A Dish");
	}
	/**
	 * Beginning of the ordering process
	 * @param event
	 * @throws Exception
	 */
	@FXML
	void OrderFood(ActionEvent event) throws Exception {
		customerFunctions.reload(event, "CustomerPage.fxml", "Customer Page");
	}
	/**
	 * Disconnect the user from the system
	 * @param event
	 */
    @FXML
    void logout(ActionEvent event) {
    	customerFunctions.logout(event);

    }

}
