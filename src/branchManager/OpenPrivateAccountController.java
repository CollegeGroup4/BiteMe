package branchManager;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OpenPrivateAccountController {

	@FXML
	private Label lblrequiredCardNum;

	@FXML
	private TextField textFieldCardNum;

	@FXML
	private Label lblrequiredExpDate;

	@FXML
	private DatePicker datePickerExpD;

	@FXML
	private Label lblrequiredCVV;

	@FXML
	private TextField textFieldCVV;

	@FXML
	private Button btnCreateAccount;

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
	private HBox Nav1;

	@FXML
	private Button btnBackBM;

	@FXML
	private HBox Nav;

	@FXML
	void backBM(ActionEvent event) {
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

	private boolean flag, validField;

	@FXML
	void createAccount(ActionEvent event) {
		flag = true;
		checkTextFiled(textFieldFirstNamePersonal, lblrequiredFname);
		checkTextFiled(textFieldLastNamePersonal, lblrequiredLname);
		checkTextFiled(textFieldIDPersonal, lblrequiredID);
		checkTextFiled(textFieldPhoneNumPersonal, lblrequiredPhonNum);
		checkTextFiled(textFieldEmailPersonal, lblrequiredEmail);

		if (flag) {
			validField = true;
			checkValidFields(textFieldIDPersonal, lblrequiredID);
			checkValidFields(textFieldPhoneNumPersonal, lblrequiredPhonNum);

			if (validField) {

				String personalName = textFieldFirstNamePersonal.getText() + " " + textFieldLastNamePersonal.getText();
				int personalID = Integer.parseInt(textFieldIDPersonal.getText());
				int personalPhoneNum = Integer.parseInt(textFieldPhoneNumPersonal.getText());
				String personalEmail = textFieldEmailPersonal.getText();
				int cardNum, cvv;
				String expDate;
				if (!textFieldCardNum.getText().equals("")) {
					flag = true;
					checkTextFiled(textFieldCardNum, lblrequiredCardNum);
					checkValidFields(textFieldCardNum, lblrequiredCardNum);
					if (flag) {
						checkTextFiled(textFieldCVV, lblrequiredCVV);
						checkValidFields(textFieldCVV, lblrequiredCVV);
						if (datePickerExpD.getValue()== null)
							lblrequiredExpDate.setText("required");
						else
							lblrequiredExpDate.setText("");
					}
				}
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
