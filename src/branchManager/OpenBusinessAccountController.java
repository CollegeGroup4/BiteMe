package branchManager;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import Server.Response;
import client.ChatClient;
import client.ClientUI;
import common.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logic.Account;
import logic.BusinessAccount;

/**
 * This class is for the branch manager to open and edit business account for a
 * user. From here you can open or edit account Only if the user is registered
 * in the database of the user management system then an account can be opened
 * for him for open OR if the user has an open business account for edit
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */

public class OpenBusinessAccountController implements Initializable {
	public static Boolean isEdit = false;
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();
	public static Account account;
	public static BusinessAccount businessAccount;
	private boolean flag, validField;

	@FXML
	private Button btnCreateAccount;
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
	private Button btnUpdate;
	@FXML
	private HBox Nav;
	@FXML
	private Label lableHello;
	@FXML
	private JFXHamburger myHamburger;
	@FXML
	private JFXDrawer drawer;
	@FXML
	private JFXComboBox<String> comboBoxStatus;
	@FXML
	private JFXComboBox<String> comboBoxRole;

	/**
	 * initialize the open business account page - initialize the navigation side
	 * panel - initialize the personal info - initialize the business info if this
	 * is edit account
	 * 
	 * @param URL            location
	 * @param ResourceBundle resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getFirstName());
		comboBoxStatus.getItems().setAll("Active", "Frozen", "Blocked");
		componnentDebt.setVisible(isEdit);
		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
		comboBoxRole.getItems().setAll("Not Assigned", "CEO", "Branch Manager", "Supplier", "HR", "Client");
		labelTitle.setText("Edit Business Account");
		if (isEdit) {
			// if we are editing an account then
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

	/**
	 * A method Allows the user to logout from the system.
	 * 
	 * @param event
	 */
	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	/**
	 * A method that returns to the branch manager's home screen
	 * 
	 * @param event
	 */
	@FXML
	void home(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	/**
	 * A method that returns to choose type of account to open - open account screen
	 * 
	 * @param event
	 */
	@FXML
	void backOpenAccount(ActionEvent event) {
		branchManagerFunctions.reload(event, "/branchManager/OpenAccountPage.fxml", "Branch manager- Open account");
	}

	/**
	 * ** If we are in a state of edit business account: ** We will update the
	 * account by sending it to a server that will update in DB and receive a
	 * response from the server to confirm that the information has indeed been
	 * updated
	 * 
	 * @param event
	 */
	@FXML
	void update(ActionEvent event) {
		String businessName = textFieldBusinessName.getText();
		float monthBillingCeiling = Integer.parseInt(textFieldMonthBlling.getText());
		String status = comboBoxStatus.getValue();
		BusinessAccount body = new BusinessAccount(businessAccount.getUserID(), businessAccount.getUserName(),
				businessAccount.getPassword(), businessAccount.getFirstName(), businessAccount.getLastName(),
				businessAccount.getEmail(), businessAccount.getRole(), businessAccount.getPhone(), status, true,
				BranchManagerController.branchManager.getUserID(), BranchManagerController.branchManager.getArea(),
				businessAccount.getDebt(), businessAccount.getW4c_card(), monthBillingCeiling,
				businessAccount.getIsApproved(), businessName, businessAccount.getCurrentSpent());
		// sent to the server
		branchManagerFunctions.sentToJson("/accounts/businessAccount", "PUT", body,
				"Edit Business account - new ClientController didn't work");
		// get the response from the server
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

	/**
	 * ** If we are in a state of open business account : ** We will create the
	 * account by sending it to the server that will create the account in DB and
	 * then receive a response from the server to confirm that the information has
	 * indeed been created
	 * 
	 * @param event
	 */
	@FXML
	void createAccount(ActionEvent event) {
		String businessName = textFieldBusinessName.getText();
		float monthBillingCeiling = Integer.parseInt(textFieldMonthBlling.getText());
		BusinessAccount businessAccount = new BusinessAccount(account.getUserID(), account.getUserName(), null, null,
				null, null, account.getRole(), null, account.getStatus(), true,
				BranchManagerController.branchManager.getUserID(), BranchManagerController.branchManager.getArea(), 0,
				null, monthBillingCeiling, false, businessName, 0);
		sentToJson(businessAccount);
		response();
	}

	/**
	 * A method for create an business account by sending it to the server that will
	 * create the account in DB
	 * 
	 * @param BusinessAccount businessAccount
	 */
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

	/**
	 * A method that receive a response from the server to confirm that the
	 * information has indeed been created
	 * 
	 */
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

	/**
	 * A method that checks that we got in a text field string if not then prints
	 * 'required'
	 * 
	 * @param textField
	 * @param lblrequired
	 */
	void checkTextFiled(TextField textField, Label lblrequired) {
		if (textField.getText().equals("")) {
			lblrequired.setText("required");
			flag = false;
		} else {
			lblrequired.setText("");
		}
	}

	/**
	 * A method that checks that we got in a text field a number if not then prints
	 * 'Enter only numbers'
	 * 
	 * @param textField
	 * @param lblrequired
	 */
	void checkValidFields(TextField textField, Label lblrequired) {
		try {
			Integer.parseInt(textField.getText());
		} catch (NumberFormatException e) {
			lblrequired.setText("Enter only numbers");
			validField = false;
		}
	}

}
