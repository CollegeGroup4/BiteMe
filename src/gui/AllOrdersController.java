package gui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import logic.Order;

public class AllOrdersController {

    @FXML
    private TableView<?> tblID;
	
	@FXML
	private TableColumn<?, ?> OrderNum;

	@FXML
	private TableColumn<?, ?> Restaurant;

	@FXML
	private TableColumn<?, ?> OrderTime;

	@FXML
	private TableColumn<?, ?> PhoneNumber;

	@FXML
	private TableColumn<?, ?> OrderType;

	@FXML
	private TableColumn<?, ?> OrderAddress;

	public void setup() {
    	OrderNum = new TableColumn<Order, String>();
//    	Restaurant = new TableColumn<Order, String>();
//    	OrderTime = new TableColumn<Order, String>();
//    	PhoneNumber = new TableColumn<Order, String>();
//    	OrderType = new TableColumn<Order, String>();
//    	OrderAddress = new TableColumn<Order, String>();
		//OrderNum.setCellValueFactory(new PropertyValueFactory<Order, String>("firstName"));
	}

	public void insertOrders(ArrayList<String> Orders) {
		
	}


}
