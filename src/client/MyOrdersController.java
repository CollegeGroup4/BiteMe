package client;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import Server.EchoServer;
import Server.Response;
import guiNew.Messages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import logic.Account;
import logic.Employer;
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

public class MyOrdersController implements Initializable {
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
	@FXML
	private Label lableSlectedOrder;

	private ObservableList<Order> ordersList = FXCollections.observableArrayList();
	private ObservableList<Order> orderEditSelect;
	private Order[] orders;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sentToJson();
		response();

		customerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);

		orderNum.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
		restaurant.setCellValueFactory(new PropertyValueFactory<Order, String>("restaurantName"));
		orderTime.setCellValueFactory(new PropertyValueFactory<Order, String>("approved_time"));
		orderType.setCellValueFactory(new PropertyValueFactory<Order, String>("type_of_order"));

		tableView.setItems(ordersList);

	}

	private void sentToJson() {
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());
		body.getAsJsonObject().addProperty("userName", CustomerPageController.client.getUserName());
		customerFunctions.sentToJson("/orders/getOrderByUserName", "GET", body,
				"Edit personal info - new ClientController didn't work");

	}

	private void response() {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201)
			lableErrorMsg.setText(response.getDescription());// error massage
		else {
			orders = gson.fromJson((String) response.getBody(), Order[].class);
			for (Order o : orders) { // update the list of users to be the response from the DB
				ordersList.add(o);

			}
		}

	}

	/**
	 * A method that updates the selected user - userEditSelect by clicking on the
	 * table
	 * 
	 * @param event
	 */
	@FXML
	void ClickOnTable(MouseEvent event) {
		orderEditSelect = tableView.getSelectionModel().getSelectedItems();
		lableSlectedOrder.setText("Restaurant name: " + orderEditSelect.get(0).getRestaurantName() + ", order num:"
				+ orderEditSelect.get(0).getOrderID() + ""
				+ String.format(",  Price: $ %.2f", orderEditSelect.get(0).getCheck_out_price()));
	}

	@FXML
	void approve(ActionEvent event) {
		Order order = orderEditSelect.get(0);
		if (!lableSlectedOrder.getText().equals("")) {
			customerFunctions.sentToJson("/orders/approveOrder", "POST", order,
					"clint myOrder - new ClientController didn't work");


			Response response = ChatClient.serverAns;
			if (response.getCode() != 200 && response.getCode() != 201)
				lableSlectedOrder.setText(response.getDescription());
			else
				ordersList.remove(order);
		}

	}

	@FXML
	void home(ActionEvent event) {
		customerFunctions.home(event);
	}

	@FXML
	void logout(ActionEvent event) {
		customerFunctions.logout(event);
	}

}