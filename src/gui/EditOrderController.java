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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Order;

public class EditOrderController implements Initializable{

    @FXML
    private ComboBox cmbTypeOfOrder;

    @FXML
    private TextField txtOrderID;

    @FXML
    private TextField txtRestaurant;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtTime;

    @FXML
    private TextField txtPhone;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnBack;
    
    @FXML
    private Label txtStatus;

	ObservableList<String> list;

	@FXML
	void Back(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		MainScreenController aFrame = new MainScreenController(); // create StudentFrame
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/*
 * 
 * 		tostring.append(getResturant() + ",");
		tostring.append(getOrderAddress() + ",");
		tostring.append(getPhoneNumber() + ",");
		tostring.append(getOrderTime() + ",");
		tostring.append(getOrderType() + ",");
		tostring.append(getOrderNum());
		return tostring.toString();
 * 
 */
	@FXML
    void UpdateOrder(ActionEvent event) {
    	Order temp = new Order(txtRestaurant.getText(), txtAddress.getText(), txtPhone.getText(),
    							Time.valueOf(txtTime.getText()), cmbTypeOfOrder.getValue().toString());
    	temp.setOrderNum(Integer.valueOf(txtOrderID.getText()));
    	String[] reqToDB = new String[3];
    	reqToDB[0] = "UPDATE";
    	reqToDB[1] = "ORDER";
    	reqToDB[2] = temp.toString();
    	ClientUI.chat.accept(reqToDB);
    	if(ChatClient.serverAns.get(2).equals("ERROR")) {
    		txtStatus.setText("ERROR");
    		txtStatus.setTextFill(Color.web("#a30000"));
    	} else {
    		txtStatus.setText("SUCCESS");
    		txtStatus.setTextFill(Color.web("#0047a3"));
    	}
    	
    }

	public void insertOrder(String order) {
		String[] result = order.split(",");
		Order temp = new Order(result[0], result[1], result[2], Time.valueOf(result[3]), result[4]);
		String orderType=result[4];
		temp.setOrderNum(Integer.valueOf(result[5]));

		txtOrderID.setText(String.valueOf(temp.getOrderNum()));
		txtAddress.setText(temp.getOrderAddress());
		txtPhone.setText(temp.getPhoneNumber());
		txtRestaurant.setText(temp.getResturant());
		txtTime.setText(temp.getOrderTime().toString());
		this.cmbTypeOfOrder.setValue(temp.getOrderType());
	}
	
	// creating list of Faculties
	private void setFacultyComboBox() {
		ArrayList<String> al = new ArrayList<String>();
		al.add("order-in");
		al.add("take-away");
		al.add("delivery");

		list = FXCollections.observableArrayList(al);
		cmbTypeOfOrder.setItems(list);
	}

	// @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setFacultyComboBox();
		txtOrderID.setEditable(false);
		txtPhone.setEditable(false);
		txtRestaurant.setEditable(false);
		txtTime.setEditable(false);
		
	}

	private void setType(String type1, String type2, String type3) {
		ArrayList<String> al = new ArrayList<String>();
		al.add(type1);
		al.add(type2);
		al.add(type3);
		list = FXCollections.observableArrayList(al);
		cmbTypeOfOrder.setItems(list);
	}

}


