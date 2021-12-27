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
import logic.BusinessAccount;

public class OpenBusinessAccountController implements Initializable {
	public static Boolean isEdit = false;
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();
	@FXML
	private Label labelTitle;

	@FXML
	private TextField textFieldUsernamePersonal;

	@FXML
	private Label lblrequiredUsername;

	@FXML
	private Label lblrequiredIDPersonal;

	@FXML
	private TextField textFieldIDPersonal;

	@FXML
	private AnchorPane componnentDebt;

	@FXML
	private Label lblrequiredDebt;

	@FXML
	private TextField textFieldDebt;

	@FXML
	private TextField textFieldBusinessName;

	@FXML
	private Label lblrequiredBusinessName;

	@FXML
	private Label lblrequiredMonthBlling;

	@FXML
	private TextField textFieldMonthBlling;

	@FXML
	private JFXComboBox<String> comboBoxStatus;

	@FXML
	private Label lableErrorMag;

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

	private boolean flag, validField;

	@FXML
	void createAccount(ActionEvent event) {
		flag = true;
		checkTextFiled(textFieldUsernamePersonal, lblrequiredUsername);
		checkTextFiled(textFieldIDPersonal, lblrequiredIDPersonal);

		checkTextFiled(textFieldBusinessName, lblrequiredBusinessName);
		checkTextFiled(textFieldMonthBlling, lblrequiredMonthBlling);

		if (flag) {
			validField = true;
			checkValidFields(textFieldIDPersonal, lblrequiredIDPersonal);
			checkValidFields(textFieldMonthBlling, lblrequiredMonthBlling);
			if (validField) {

				String personalUsename = textFieldUsernamePersonal.getText();
				int personalID = Integer.parseInt(textFieldIDPersonal.getText());
				String status = comboBoxStatus.getValue() == null ? "Active" : comboBoxStatus.getValue();
				String businessName = textFieldBusinessName.getText();
				float monthBillingCeiling = Integer.parseInt(textFieldMonthBlling.getText());
				BusinessAccount businessAccount = new BusinessAccount(personalID, personalUsename, null, null, null,
						null, null, null, status, true, BranchManagerController.branchManager.getUserID(),
						BranchManagerController.branchManager.getArea(), 0, null, monthBillingCeiling, false,
						businessName, 0);
				sentToJson(businessAccount);
				response();
			}
		}
	}

	void sentToJson(BusinessAccount businessAccount) {
		Request request = new Request();
		request.setPath("/accounts/businessAccount");
		request.setMethod("POST");
		request.setBody(businessAccount);
		Gson gson = new Gson();
		String jsonUser = gson.toJson(request);
		try {
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("Open Business account - new ClientController didn't work");
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
