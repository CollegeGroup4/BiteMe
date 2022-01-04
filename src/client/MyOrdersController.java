package client;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Order;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MyOrdersController implements Initializable {

	@FXML
	private HBox Nav;

    @FXML
    private Label welcomeLabel;
    
	@FXML
	private Button btnLogout;

	@FXML
	private Hyperlink BtnHome;

	@FXML
	private ImageView imageBiteme1;

	@FXML
	private HBox Nav1;

	@FXML
	private ImageView imageBiteme2;

	@FXML
	private ImageView imageFacebook;

	@FXML
	private ImageView imageInstergram;

	@FXML
	private ImageView imageWhatsapp;

	@FXML
	private Button btnBack;

	@FXML
	private TableView<Order> tblID;

	@FXML
	private TableColumn<Order, Integer> OrderNum;

	@FXML
	private TableColumn<Order, String> Restaurant;

	@FXML
	private TableColumn<Order, Time> OrderTime;

	@FXML
	private TableColumn<Order, String> OrderType;

	@FXML
	private TableColumn<Order, String> OrderAddress;

	private ObservableList<Order> orderList = FXCollections.observableArrayList();

	@FXML
	void goBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		CustomerPageController aFrame = new CustomerPageController();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertOrdersToTbl() {
//		String[] id = new String[2];
//		id[0] = new String("GETALL");
//		id[1] = new String("ORDER");
//		ClientUI.chat.accept(id);
//
//		if (ChatClient.serverAns.get(2).equals("Error")) {
//			System.out.println("Can't find any orders");
//
//		} else {
//			ArrayList<String> orders = ChatClient.serverAns;
//			for (int i = 2; i < orders.size(); i++) {
// 				String[] result = orders.get(i).split(",");
//				Order temp = new Order(result[0], result[1], result[2], Time.valueOf(result[3]), result[4]);
//				temp.setOrderNum(Integer.valueOf(result[5]));
//				orderList.add(temp);
//			}
//		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcomeLabel.setText("Welcome, " + CustomerPageController.client.getFirstName());

		OrderNum.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNum"));
		Restaurant.setCellValueFactory(new PropertyValueFactory<Order, String>("resturant"));
		OrderTime.setCellValueFactory(new PropertyValueFactory<Order, Time>("orderTime"));
		OrderType.setCellValueFactory(new PropertyValueFactory<Order, String>("orderType"));
		OrderAddress.setCellValueFactory(new PropertyValueFactory<Order, String>("orderAddress"));
		tblID.setItems(orderList);

	}

}
