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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Account;
import logic.BusinessAccount;

public class OpenBusinessAccountController implements Initializable {
	public static Boolean isEdit = false;
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();
	public static Account account;
	public static BusinessAccount businessAccount;

	@FXML
	private Label labelTitle;

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
	private JFXComboBox<String> comboBoxRole;
	@FXML
	private Label lableErrorMsg;

	@FXML
	private Button btnReturnHome;

	@FXML
	private Label lableSuccessMsg;

	@FXML
	private TextField textFieldFirstName;

	@FXML
	private Label lblrequiredFirstName;

	@FXML
	private TextField textFieldLastName;

	@FXML
	private Label lblrequiredLastName;

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
	private HBox Nav;

	@FXML
	private Label lableHello;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private Button btnUpdate;
	@FXML
	private Button btnCreateAccount;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getFirstName());
		comboBoxStatus.getItems().setAll("Active", "Frozen", "Blocked");
//		comboBoxStatus.setValue("Active");
		componnentDebt.setVisible(isEdit);
		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
		comboBoxRole.getItems().setAll("Not Assigned", "CEO", "Branch Manager", "Supplier", "HR", "Client");
		labelTitle.setText("Edit Business Account");
		if (isEdit) {
			btnUpdate.setVisible(true);
			btnCreateAccount.setVisible(false);
			labelTitle.setText("Edit Business Account");
			textFieldBusinessName.setText(businessAccount.getBusinessName());
			textFieldMonthBlling.setText(businessAccount.getMonthlyBillingCeiling() + "");
		}	

			// ----set the initialize value of the user
			System.out.println("set account: " + account);
			comboBoxStatus.setValue(account.getStatus() + "");
			textFieldFirstName.setText(account.getFirstName());
			textFieldLastName.setText(account.getLastName());
			textFieldID.setText(account.getUserID() + "");
			comboBoxRole.setValue(account.getRole());			
			textFieldDebt.setText(account.getDebt() + "");
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
	void update(ActionEvent event) {
		String businessName = textFieldBusinessName.getText();
		float monthBillingCeiling = Integer.parseInt(textFieldMonthBlling.getText());
		String status = comboBoxStatus.getValue();
		BusinessAccount body = new BusinessAccount(businessAccount.getUserID(), businessAccount.getUserName(),
				businessAccount.getPassword(), businessAccount.getFirstName(), businessAccount.getLastName(), businessAccount.getEmail(),
				businessAccount.getRole(), businessAccount.getPhone(), status, true, BranchManagerController.branchManager.getUserID(),
				BranchManagerController.branchManager.getArea(), businessAccount.getDebt(), businessAccount.getW4c_card(), monthBillingCeiling, businessAccount.getIsApproved(),
				businessName, businessAccount.getCurrentSpent());
		
		
		branchManagerFunctions.sentToJson("/accounts/businessAccount", "PUT", body,
				"Edit Business account - new ClientController didn't work");
		
		
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201) {
			lableSuccessMsg.setText("");
			lableErrorMsg.setText(response.getDescription());// error massage
		} else {
			btnReturnHome.setVisible(true);
			lableErrorMsg.setText("");
			lableSuccessMsg.setText(response.getDescription());// error massage
		}
		
	}

	private boolean flag, validField;

	@FXML
	void createAccount(ActionEvent event) {
		String businessName = textFieldBusinessName.getText();
		float monthBillingCeiling = Integer.parseInt(textFieldMonthBlling.getText());
		BusinessAccount businessAccount = new BusinessAccount(account.getUserID(), account.getUserName(), null, null,
				null, null, "Not Assigned", null, account.getStatus(), true,
				BranchManagerController.branchManager.getUserID(), BranchManagerController.branchManager.getArea(), 0,
				null, monthBillingCeiling, false, businessName, 0);
		sentToJson(businessAccount);
		response();
	}

	void sentToJson(BusinessAccount businessAccount) {
		Gson gson = new Gson();
		Request request = new Request();
		request.setPath("/accounts/businessAccount");
		request.setMethod("POST");
		request.setBody(gson.toJson(businessAccount));
		String jsonUser = gson.toJson(request);
		try {
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("Open Business account - new ClientController didn't work");
		}
	}

	void response() {
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201) {
			lableSuccessMsg.setText("");
			lableErrorMsg.setText(response.getDescription());// error massage
		} else {
			btnReturnHome.setVisible(true);
			lableErrorMsg.setText("");
			lableSuccessMsg.setText(response.getDescription());// error massage
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
