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
import logic.PrivateAccount;

/**
 * This class is for the branch manager to open and edit private account for a
 * user. From here you can open or edit account Only if the user is registered
 * in the database of the user management system then an account can be opened
 * for him for open OR if the user has an open private account for edit
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
	private boolean flag, validField;

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

	/**
	 * initialize the open private account page - initialize the navigation side
	 * panel - initialize the personal info - initialize the private info if this is
	 * edit account
	 * 
	 * @param URL            location
	 * @param ResourceBundle resources
	 */
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
		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);

		comboBoxRole.getItems().setAll("Not Assigned", "CEO", "Branch Manager", "Supplier", "HR", "Client");

		if (isEdit) {
			// if we are editing an account then
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

	/**
	 * ** If we are in a state of edit private account: ** We will update the
	 * account by sending it to a server that will update in DB and receive a
	 * response from the server to confirm that the information has indeed been
	 * updated
	 * 
	 * @param event
	 */
	@FXML
	void update(ActionEvent event) {
		checkValidFields(textFieldCardNum, lblrequiredCardNum);
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
		// sent to the server
		branchManagerFunctions.sentToJson("/accounts/privateAccount", "PUT", body,
				"Edit Private account - new ClientController didn't work");
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
	void back(ActionEvent event) {
		if (isEdit)
			branchManagerFunctions.reload(event, "/branchManager/EditPersonalInfo.fxml",
					"Branch manager - Edit Personal Info");
		else
			branchManagerFunctions.reload(event, "/branchManager/OpenAccountPage.fxml", "Branch manager- Open account");
	}

	/**
	 * A method that shows what CVV number means by clicking on it will open an
	 * explanation window Clicking on it again will close this window
	 * 
	 * @param event
	 */
	@FXML
	void onClickHelp(ActionEvent event) {
		componentExplain.setVisible(!componentExplain.isVisible());
	}

	/**
	 * ** If we are in a state of open private account : ** We will create the
	 * account by sending it to the server that will create the account in DB and
	 * then receive a response from the server to confirm that the information has
	 * indeed been created
	 * 
	 * @param event
	 */
	@FXML
	void createAccount(ActionEvent event) {
		flag = true;
		String cardNum = null, cvv = null, expDate = null;
		if (!textFieldCardNum.getText().equals("")) {
			validField = true;
			flag = true;
			// check if the fields are valid
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

	/**
	 * A method for create an private account by sending it to the server that will
	 * create the account in DB
	 * 
	 * @param PrivateAccount privateAccount
	 */
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
		if (!textField.getText().equals(""))
			try {
				Integer.parseInt(textField.getText());
			} catch (NumberFormatException e) {
				lblrequired.setText("Enter only numbers");
				validField = false;
			}
	}

}
