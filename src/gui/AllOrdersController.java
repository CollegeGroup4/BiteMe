package gui;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Order;

public class AllOrdersController implements Initializable {

	@FXML
	private TableView<Order> tblID;

	@FXML
	private TableColumn<Order, Integer> OrderNum;

	@FXML
	private TableColumn<Order, String> Restaurant;

	@FXML
	private TableColumn<Order, Time> OrderTime;

	@FXML
	private TableColumn<Order, String> PhoneNumber;

	@FXML
	private TableColumn<Order, String> OrderType;

	@FXML
	private TableColumn<Order, String> OrderAddress;
	
    @FXML
    private Button btnBack;

	private ObservableList<Order> orderList = FXCollections.observableArrayList();
	
    @FXML
    void Back(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		MainScreenController aFrame = new MainScreenController(); // create StudentFrame
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL url, ResourceBundle db) {
		OrderNum.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNum"));
		Restaurant.setCellValueFactory(new PropertyValueFactory<Order, String>("resturant"));
		OrderTime.setCellValueFactory(new PropertyValueFactory<Order, Time>("orderTime"));
		PhoneNumber.setCellValueFactory(new PropertyValueFactory<Order, String>("phoneNumber"));
		OrderType.setCellValueFactory(new PropertyValueFactory<Order, String>("orderType"));
		OrderAddress.setCellValueFactory(new PropertyValueFactory<Order, String>("orderAddress"));
		tblID.setItems(orderList);
		
	}

	public void insertOrdersToTbl(ArrayList<String> serverAns) {
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
}