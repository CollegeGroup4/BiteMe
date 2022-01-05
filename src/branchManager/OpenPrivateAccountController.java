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
import logic.PrivateAccount;

/**
 * This class is for the branch manager main page.
 * From here you can get to all the functionality of the branch manager
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */

public class OpenPrivateAccountController implements Initializable {
	public static Boolean isEdit = false;
	public static PrivateAccount privateAccount;
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();
	public static Account account;

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
	private JFXComboBox<String> comboBoxStatus;

	@FXML
	private JFXComboBox<String> comboBoxRole;

	@FXML
	private Label lblrequiredCVV;

	@FXML
	private TextField textFieldCVV;

	@FXML
	private Label labelTitle;

	@FXML
	private TextField textFieldFirstName;

	@FXML
	private Label lblrequiredUsername;

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
	private Label lableErrorMsg;

	@FXML
	private AnchorPane componentExplain;

	@FXML
	private Button btnReturnHome;

	@FXML
	private Label lableSuccessMsg;

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

		componentExplain.setVisible(false);
		String[] listMonth = new String[12];
		for (int i = 0; i < 12; i++) {
			if (i < 9)
				listMonth[i] = "0" + (i + 1);
			else
				listMonth[i] = "" + (i + 1);
		}
		comboBoxMonth.getItems().setAll(listMonth);
		comboBoxMonth.setValue("01");
		String[] listYear = new String[20];
		for (int i = 0; i < 20; i++)
			listYear[i] = "20" + (i + 21);
		comboBoxYear.getItems().setAll(listYear);
		comboBoxYear.setValue("2021");
//		componnentDebt.setVisible(isEdit);
		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);

		comboBoxRole.getItems().setAll("Not Assigned", "CEO", "Branch Manager", "Supplier", "HR", "Client");

		if (isEdit) {
			btnUpdate.setVisible(true);
			btnCreateAccount.setVisible(false);
			labelTitle.setText("Edit Private Account");

			// ----set the initialize value of the credit card
			textFieldCardNum.setText(privateAccount.getCreditCardNumber());
			String expDate = privateAccount.getCreditCardExpDate();
			comboBoxMonth.setValue(expDate.substring(0, 2));
			comboBoxYear.setValue(expDate.substring(5, 9));
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
	void update(ActionEvent event) {
//		checkTextFiled(textFieldCardNum, lblrequiredCardNum);
		checkValidFields(textFieldCardNum, lblrequiredCardNum);
//		checkTextFiled(textFieldCVV, lblrequiredCVV);
		checkValidFields(textFieldCVV, lblrequiredCVV);
		String expDate = null, cardNum = null, cvv = null;
		String month = comboBoxMonth.getValue();
		String year = comboBoxYear.getValue();
		expDate = month + " / " + year;
		cardNum = textFieldCardNum.getText();
		cvv = textFieldCVV.getText();
		cvv = cvv.equals("") ? privateAccount.getCreditCardCVV() : cvv;

		String status = comboBoxStatus.getValue();

		PrivateAccount body = new PrivateAccount(privateAccount.getUserID(), privateAccount.getUserName(),
				privateAccount.getPassword(), privateAccount.getFirstName(), privateAccount.getLastName(),
				privateAccount.getEmail(), privateAccount.getRole(), privateAccount.getPhone(), status, false,
				BranchManagerController.branchManager.getUserID(), BranchManagerController.branchManager.getArea(),
				privateAccount.getDebt(), privateAccount.getW4C(), cardNum, cvv, expDate);

		branchManagerFunctions.sentToJson("/accounts/privateAccount", "PUT", body,
				"Edit Private account - new ClientController didn't work");

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

	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	@FXML
	void home(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	@FXML
	void back(ActionEvent event) {
		if (isEdit)
			branchManagerFunctions.reload(event, "/branchManager/EditPersonalInfo.fxml",
					"Branch manager - Edit Personal Info");
		else
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
		String cardNum = null, cvv = null, expDate = null;
		if (!textFieldCardNum.getText().equals("")) {
			validField = true;
			flag = true;
			checkTextFiled(textFieldCardNum, lblrequiredCardNum);
			checkValidFields(textFieldCardNum, lblrequiredCardNum);
			checkTextFiled(textFieldCVV, lblrequiredCVV);
			checkValidFields(textFieldCVV, lblrequiredCVV);
			if (validField && flag) {
				String month = comboBoxMonth.getValue();
				String year = comboBoxYear.getValue();
				expDate = month + " / " + year;
			}
			cardNum = textFieldCardNum.getText();
			cvv = textFieldCVV.getText();
		}
		PrivateAccount privateAccount = new PrivateAccount(account.getUserID(), account.getUserName(), null, null, null,
				null, account.getRole(), null, account.getStatus(), false,
				BranchManagerController.branchManager.getUserID(), BranchManagerController.branchManager.getArea(),
				account.getDebt(), null, cardNum, cvv, expDate);
		sentToJson(privateAccount);
		response();
	}
//		}
//	}

	void sentToJson(PrivateAccount privateAccount) {
		Gson gson = new Gson();
		Request request = new Request();
		request.setPath("/accounts/privateAccount");
		request.setMethod("POST");
		request.setBody(gson.toJson(privateAccount));
		try {
			ClientUI.chat.accept(gson.toJson(request)); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("Open Private account - new ClientController didn't work");
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
		if (!textField.getText().equals(""))
			try {
				Integer.parseInt(textField.getText());
			} catch (NumberFormatException e) {
				lblrequired.setText("Enter only numbers");
				validField = false;
			}
	}

}
