package branchManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import Server.Response;
import client.ChatClient;
import client.ClientUI;
import common.Request;
import guiNew.Navigation_SidePanelController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.PrivateAccount;

public class OpenPrivateAccountController implements Initializable {
	public static Boolean isEdit = false;
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	@FXML
	private Label lblrequiredCardNum;

	@FXML
	private TextField textFieldCardNum;

	@FXML
	private Label lblrequiredExpDate;

	@FXML
	private JFXComboBox<String> comboBoxMonth;

	@FXML
	private JFXComboBox<String> comboBoxYear;

	@FXML
	private Label lblrequiredCVV;

	@FXML
	private TextField textFieldCVV;

	@FXML
	private Label labelTitle;

	@FXML
	private TextField textFieldUserNameUser;

	@FXML
	private Label lblrequiredUsername;

	@FXML
	private Label lblrequiredID;

	@FXML
	private TextField textFieldID;

	@FXML
	private AnchorPane componnentDebt;

	@FXML
	private Label lblrequiredDebt;

	@FXML
	private TextField textFieldDebt;

	@FXML
	private JFXComboBox<String> comboBoxStatus;

	@FXML
	private Label lableErrorMag;

	@FXML
	private AnchorPane componentExplain;

	@FXML
	private HBox Nav;

	@FXML
	private Label lableHello;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getFirstName());
		comboBoxStatus.getItems().setAll("Active", "Frozen", "Blocked");
		componentExplain.setVisible(false);
		String[] listMonth = new String[12];
		for (int i = 0; i < 12; i++) {
			if (i < 9)
				listMonth[i] = "0" + (i + 1);
			else
				listMonth[i] = "" + (i + 1);
		}
		comboBoxMonth.getItems().setAll(listMonth);
		String[] listYear = new String[20];
		for (int i = 0; i < 20; i++)
			listYear[i] = "20" + (i + 21);
		comboBoxYear.getItems().setAll(listYear);
		componnentDebt.setVisible(isEdit);
		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer); 
	}

	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	@FXML
	void home(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	@FXML
	void backOpenAccount(ActionEvent event) {
		branchManagerFunctions.reload(event, "/branchManager/OpenAccountPage.fxml", "Branch manager- Open account");
	}

	@FXML
	void onClickHelp(ActionEvent event) {
		componentExplain.setVisible(!componentExplain.isVisible());
	}

	private boolean flag, validField;

	@FXML
	void createAccount(ActionEvent event) {
		flag = true;
		checkTextFiled(textFieldUserNameUser, lblrequiredUsername);
		checkTextFiled(textFieldID, lblrequiredID);
		if (isEdit)
			checkTextFiled(textFieldDebt, lblrequiredID);

		if (flag) {
			validField = true;
			checkValidFields(textFieldID, lblrequiredID);
			if (validField) {

				String username = textFieldUserNameUser.getText();
				int id = Integer.parseInt(textFieldID.getText());
				String status = comboBoxStatus.getValue() == null ? "Active" : comboBoxStatus.getValue();

				String cardNum = null, cvv = null, expDate = null;
				if (!textFieldCardNum.getText().equals("")) {
					validField = true;
					flag = true;
					checkTextFiled(textFieldCardNum, lblrequiredCardNum);
					checkValidFields(textFieldCardNum, lblrequiredCardNum);
					checkTextFiled(textFieldCVV, lblrequiredCVV);
					checkValidFields(textFieldCVV, lblrequiredCVV);
					if (validField && flag) {
						String month = comboBoxMonth.getValue() == null ? "01" : comboBoxStatus.getValue();
						String year = comboBoxYear.getValue() == null ? "21" : comboBoxStatus.getValue();
						expDate = month + " / " + year;
					}

				}
				PrivateAccount privateAccount = new PrivateAccount(id, username, null, null, null, null, null, null,
						status, false, BranchManagerController.branchManager.getUserID(),
						BranchManagerController.branchManager.getArea(), 0, null, cardNum, cvv, expDate);
				sentToJson(privateAccount);
				response();
			}
		}
	}

	void sentToJson(PrivateAccount privateAccount) {
		Request request = new Request();
		request.setPath("/accounts/privateAccount");
		request.setMethod("POST");
		request.setBody(privateAccount);
		Gson gson = new Gson();
		String jsonUser = gson.toJson(request);
		try {
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("Open Private account - new ClientController didn't work");
		}
	}

	void response() {
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201)
			lableErrorMag.setText(response.getDescription());// error massage
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
