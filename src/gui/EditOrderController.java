package gui;

import java.sql.Time;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import logic.Order;

public class EditOrderController {

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

	ObservableList<String> list;

	@FXML
	void Back(ActionEvent event) {

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
    	String[] reqToDB = new String[3];
    	reqToDB[0] = "UPDATE";
    	reqToDB[1] = "ORDER";
    	reqToDB[2] = temp.toString();
    	
    }

	public void insertOrder(String order) {
		String[] result = order.split(",");
		Order temp = new Order(result[0], result[1], result[2], Time.valueOf(result[3]), result[4]);
		String orderType=result[4];
		temp.setOrderNum(Integer.valueOf(result[5]));

		if(orderType.equals("order-in"))
			setType(orderType, "take-away", "delivery");
		else if(orderType.equals("take-away"))
			setType(orderType, "order-in", "delivery");
		else if(orderType.equals("delivery"))
			setType(orderType, "order-in", "take-away");

		txtOrderID.setText(String.valueOf(temp.getOrderNum()));
		txtAddress.setText(temp.getOrderAddress());
		txtPhone.setText(temp.getPhoneNumber());
		txtRestaurant.setText(temp.getResturant());
		txtTime.setText(temp.getOrderTime().toString());
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


