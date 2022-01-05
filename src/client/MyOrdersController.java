package client;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import logic.Order;

/**
 * This class made for showing all user orders
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */

public class MyOrdersController implements Initializable{
	CustomerFunctions customerFunctions = new CustomerFunctions();

	@FXML
	private TableView<Order> tableView;

	@FXML
	private TableColumn<Order, Integer> orderNum;

	@FXML
	private TableColumn<Order, String> restaurant;

	@FXML
	private TableColumn<Order, String> orderTime;

	@FXML
	private TableColumn<Order, String> orderType;

	@FXML
	private Label lableErrorMsg;

	@FXML
	private HBox Nav;

	@FXML
	private Label welcomeLabel;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private Button btnApprove;
	
	private ObservableList<Order> messagesList = FXCollections.observableArrayList();
	private ObservableList<Order> messagesEditSelect;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sentToJson();
		response();

		customerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);

		orderNum.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
		restaurant.setCellValueFactory(new PropertyValueFactory<Order, String>("restaurantName"));
		orderTime.setCellValueFactory(new PropertyValueFactory<Order, String>("time_taken"));
		orderType.setCellValueFactory(new PropertyValueFactory<Order, String>("type_of_order"));
		
		tableView.setItems(messagesList);
		
	}

	private void sentToJson() {
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());
		body.getAsJsonObject().addProperty("userName", CustomerPageController.client.getUserName());
		customerFunctions.sentToJson("/branch_manager/employers", "GET", body,
				"Edit personal info - new ClientController didn't work");
		
	}
	private void response() {
		// TODO Auto-generated method stub
		
	}

	@FXML
	void approve(ActionEvent event) {

	}

	@FXML
	void home(ActionEvent event) {

	}

	@FXML
	void logout(ActionEvent event) {

	}



}