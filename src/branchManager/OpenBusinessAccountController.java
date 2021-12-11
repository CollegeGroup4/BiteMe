package branchManager;

import java.io.IOException;

import donotenterdrinksorfood.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OpenBusinessAccountController {

	@FXML
	private Button btnCreateAccount;

	@FXML
	private Hyperlink linkApprovals;

	@FXML
	private TextField textFieldFirstNamePersonal;

	@FXML
	private Label lblrequiredFname;

	@FXML
	private Label lblrequiredLname;

	@FXML
	private TextField textFieldLastNamePersonal;

	@FXML
	private Label lblrequiredID;

	@FXML
	private TextField textFieldIDPersonal;

	@FXML
	private Label lblrequiredPhonNum;

	@FXML
	private TextField textFieldPhoneNumPersonal;

	@FXML
	private Label lblrequiredEmail;

	@FXML
	private TextField textFieldEmailPersonal;

	@FXML
	private TextField textFieldFirstNameEmployee;

	@FXML
	private Label lblrequiredFnameE;

	@FXML
	private Label lblrequiredLnameE;

	@FXML
	private TextField textFieldLastNameEmployee;

	@FXML
	private Label lblrequiredIDE;

	@FXML
	private TextField textFieldIDEmployee;

	@FXML
	private Label lblrequiredPhonNumE;

	@FXML
	private TextField textFieldPhoneNumEmployee;

	@FXML
	private Label lblrequiredEmailE;

	@FXML
	private TextField textFieldEmailEmployee;

	@FXML
	private Label lblrequiredMonthBlling;

	@FXML
	private TextField textFieldMonthBlling;

	@FXML
	private HBox Nav;

	@FXML
	private HBox Nav1;

	@FXML
	private Button btnbackOpenBusinessAccount;

	@FXML
	void Approvals(ActionEvent event) {

	}

	@FXML
	void backOpenBusinessAccount(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/branchManager/OpenAccountPage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Branch manager- Open account");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean flag,validField;

	@FXML
	void createAccount(ActionEvent event) {
		flag = true;
		checkTextFiled(textFieldFirstNamePersonal, lblrequiredFname);
		checkTextFiled(textFieldLastNamePersonal, lblrequiredLname);
		checkTextFiled(textFieldIDPersonal, lblrequiredID);
		checkTextFiled(textFieldPhoneNumPersonal, lblrequiredPhonNum);
		checkTextFiled(textFieldEmailPersonal, lblrequiredEmail);

		checkTextFiled(textFieldFirstNameEmployee, lblrequiredFnameE);
		checkTextFiled(textFieldLastNameEmployee, lblrequiredLnameE);
		checkTextFiled(textFieldIDEmployee, lblrequiredIDE);
		checkTextFiled(textFieldPhoneNumEmployee, lblrequiredPhonNumE);
		checkTextFiled(textFieldEmailEmployee, lblrequiredEmailE);
		checkTextFiled(textFieldMonthBlling, lblrequiredMonthBlling);

		if (flag) {
			validField = true;
			checkValidFields(textFieldIDPersonal,lblrequiredID);
			checkValidFields(textFieldPhoneNumPersonal,lblrequiredPhonNum);
			checkValidFields(textFieldIDEmployee,lblrequiredIDE);
			checkValidFields(textFieldPhoneNumEmployee,lblrequiredPhonNumE);
			checkValidFields(textFieldMonthBlling,lblrequiredMonthBlling);
			if(validField) {
			
			String personalName = textFieldFirstNamePersonal.getText() + " " + textFieldLastNamePersonal.getText();
			int personalID = Integer.parseInt(textFieldIDPersonal.getText()); 
			int personalPhoneNum = Integer.parseInt(textFieldPhoneNumPersonal.getText());
			String personalEmail = textFieldEmailPersonal.getText();
			
			String employeeName = textFieldFirstNameEmployee.getText() + " " + textFieldLastNameEmployee.getText();
			int EmployeeID= Integer.parseInt(textFieldIDEmployee.getText()); 
			int EmployeePhoneNum = Integer.parseInt(textFieldPhoneNumEmployee.getText());
			String EmployeeEmail= textFieldEmailEmployee.getText();
			int monthBillingCeiling = Integer.parseInt(textFieldMonthBlling.getText()); 
			
			}
		}
	}

	void checkTextFiled(TextField textField, Label lblrequired) {
		if (textField.getText().equals("")) {
			lblrequired.setText("required");
			flag = false;
		} else {
			lblrequired.setText("");
		}
	}

	void checkValidFields(TextField textField, Label lblrequired) {
		try {
			Integer.parseInt(textField.getText());
		} catch (NumberFormatException e) {
			lblrequired.setText("Enter only numbers");
			validField = false;
		}
	}
}
