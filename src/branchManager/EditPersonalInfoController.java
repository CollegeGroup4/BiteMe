package branchManager;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import Server.EchoServer;
import Server.Response;
import client.ChatClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logic.Account;
import logic.BusinessAccount;
import logic.PrivateAccount;
import logic.Restaurant;

/**
 * This class is for the branch manager edit personal info main page. Here the
 * branch manager can change any existing personal information detail related to
 * the operation of the system
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */

public class EditPersonalInfoController implements Initializable {
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();
	private BusinessAccount businessAccount;
	private PrivateAccount privateAccount;
	private Restaurant restaurant;
	private Account account;

	@FXML
	private TableView<Account> tableViewUsers;
	@FXML
	private TableColumn<Account, String> tableColRole;
	@FXML
	private TableColumn<Account, String> tableColID;
	@FXML
	private TableColumn<Account, String> tableColFirstname;
	@FXML
	private TableColumn<Account, String> tableColLastName;
	@FXML
	private TableColumn<Account, String> tableColArea;
	@FXML
	private TableColumn<Account, String> tableColPhonenum;
	@FXML
	private TableColumn<Account, String> tableColStatus;
	@FXML
	private JFXComboBox<String> coboBoxRole;
	@FXML
	private JFXComboBox<String> coboBoxStatus;
	@FXML
	private HBox Nav;
	@FXML
	private Label lableHello;
	@FXML
	private AnchorPane anchorPaneEditUsers;
	@FXML
	private TextField textFiledSearchUsername;
	@FXML
	private Label lableSlectedUser;
	@FXML
	private Label lblrequiredEnterid;
	@FXML
	private Label lableErrorMsg;
	@FXML
	private Label lableErrorMsgID;
	@FXML
	private AnchorPane anchorPaneChooseAccount;
	@FXML
	private Button btnEditPrivateAccount;
	@FXML
	private Button btnEditRestaurant;
	@FXML
	private Button btnEditBusinessAccount;
	@FXML
	private JFXHamburger myHamburger;
	@FXML
	private JFXDrawer drawer;

	private ObservableList<Account> userList = FXCollections.observableArrayList();
	private ObservableList<Account> usersFilter = FXCollections.observableArrayList();

	private ObservableList<Account> userEditSelect;
	private HashMap<String, Account> allUsres = new HashMap<>();

	/**
	 * initialize the Approval page- initialize the table for the account list and
	 * initialize the navigation side panel
	 * 
	 * @param URL            location
	 * @param ResourceBundle resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userList.clear();
		sentToJson();
		response();

		// create the table
		tableColRole.setCellValueFactory(new PropertyValueFactory<Account, String>("role"));
		tableColID.setCellValueFactory(new PropertyValueFactory<Account, String>("userName"));
		tableColFirstname.setCellValueFactory(new PropertyValueFactory<Account, String>("firstName"));
		tableColLastName.setCellValueFactory(new PropertyValueFactory<Account, String>("lastName"));
		tableColArea.setCellValueFactory(new PropertyValueFactory<Account, String>("area"));
		tableColPhonenum.setCellValueFactory(new PropertyValueFactory<Account, String>("phone"));
		tableColStatus.setCellValueFactory(new PropertyValueFactory<Account, String>("status"));
		usersFilter = userList;
		tableViewUsers.setItems(usersFilter);

		lableHello.setText("Hello, " + BranchManagerController.branchManager.getFirstName());
		coboBoxStatus.getItems().setAll("All", "active", "frozen", "blocked");
		coboBoxStatus.setValue("All");
		coboBoxRole.getItems().setAll("All", "HR", "Supplier", "Client", "Not Assigned");
		coboBoxRole.setValue("All");

		anchorPaneChooseAccount.setVisible(false);
		anchorPaneEditUsers.setVisible(true);
		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
	}

	/**
	 * A method that Sends to serve the id of the branch manager in order to receive
	 * from it the list of all accounts that the branch manager can edit
	 */
	void sentToJson() {
		Gson gson = new Gson();
		JsonElement jsonElem = gson.toJsonTree(new Object());
		jsonElem.getAsJsonObject().addProperty("branchManagerID", BranchManagerController.branchManager.getUserID());
		branchManagerFunctions.sentToJson("/accounts", "GET", jsonElem,
				"Edit personal info - new ClientController didn't work");
	}

	/**
	 * A method that receives from the serve the list of all accounts that the
	 * branch manager can edit
	 */
	void response() {
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201)
			lableErrorMsg.setText(response.getDescription());// error massage
		else {
			Account[] account = EchoServer.gson.fromJson((String) response.getBody(), Account[].class);
			for (Account a : account) { // update the list of users to be the response from the DB
				userList.add(a);
				allUsres.put(a.getUserName(), a);
			}
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
	void homeAndback(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	/**
	 * A method that updates the selected user - userEditSelect by clicking on the
	 * table
	 * 
	 * @param event
	 */
	@FXML
	void ClickOnTable(MouseEvent event) {
		userEditSelect = tableViewUsers.getSelectionModel().getSelectedItems();
		lableSlectedUser.setText("Name: " + userEditSelect.get(0).getFirstName() + " "
				+ userEditSelect.get(0).getLastName() + ",  Role: " + userEditSelect.get(0).getRole() + ",  Status: "
				+ userEditSelect.get(0).getStatus());
	}

	/**
	 * A method that updates the table of all users according to the selected role
	 * in the combo box
	 * 
	 * @param event
	 */
	@FXML
	void onChengedRole(ActionEvent event) {
		String status = coboBoxStatus.getValue();
		String role = coboBoxRole.getValue();

		usersFilter = FXCollections.observableArrayList();
		if (status.equals("All") && role.equals("All"))
			usersFilter = userList;
		else
			for (Account user : userList) {
				if (status.equals("All") && (user.getRole()).equals(role))
					usersFilter.add(user);
				if (user.getStatus().equals(status) && role.equals("All"))
					usersFilter.add(user);
				if (user.getStatus().equals(status) && user.getRole().equals(role))
					usersFilter.add(user);
			}

		tableViewUsers.setItems(usersFilter);
	}

	/**
	 * A method that updates the table of all users according to the selected status
	 * in the combo box
	 * 
	 * @param event
	 */
	@FXML
	void onChengedStatus(ActionEvent event) {
		String status = coboBoxStatus.getValue();
		String role = coboBoxRole.getValue();

		usersFilter = FXCollections.observableArrayList();
		if (status.equals("All") && role.equals("All"))
			usersFilter = userList;
		else
			for (Account user : userList) {
				if (status.equals("All") && (user.getRole()).equals(role))
					usersFilter.add(user);
				if (user.getStatus().equals(status) && role.equals("All"))
					usersFilter.add(user);
				if (user.getStatus().equals(status) && user.getRole().equals(role))
					usersFilter.add(user);
			}

		tableViewUsers.setItems(usersFilter);
	}

	/**
	 * A method that edit the account, when clicking on account from the table
	 * 
	 * @param event
	 */
	@FXML
	void editTable(ActionEvent event) {
		String username = userEditSelect.get(0).getUserName();
		Account selecteduser = allUsres.get(username);
		lableErrorMsg.setText("");

		if (selecteduser != null) {
			branchManagerFunctions.sentToJson("/accounts/getAccount", "GET", selecteduser,
					"Edit personal info - new ClientController didn't work");
			responseGetAccountUsername(event, lableErrorMsg);
		}
	}

	/**
	 * A method that edit the account, when insert user name of a user and edit the
	 * account. (businesses, private or supplier)
	 * 
	 * @param event
	 */
	@FXML
	void editByUsername(ActionEvent event) {
		String username;
		if (!textFiledSearchUsername.getText().equals("")) {
			try {
				username = textFiledSearchUsername.getText();
				Account selecteduser = allUsres.get(username);
				branchManagerFunctions.sentToJson("/accounts/getAccount", "GET", selecteduser,
						"Edit personal info - new ClientController didn't work");

				responseGetAccountUsername(event, lableErrorMsgID);
			} catch (NumberFormatException e) {
				lblrequiredEnterid.setText("Enter only numbers");
			}

		}
	}

	/**
	 * A method that receives the server's response ïf the user has an account
	 * (private, business or restaurant) then the server will return it else it will
	 * return null in these fields
	 * 
	 * @param event
	 * @param lableError
	 */
	void responseGetAccountUsername(ActionEvent event, Label lableError) {
		Gson gson = new Gson();
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201)
			lableError.setText(response.getDescription());
		else {
			System.out.println("-->>" + response.getDescription()); // Description from server

			JsonElement body = gson.fromJson((String) response.getBody(), JsonElement.class);
			account = gson.fromJson(body.getAsJsonObject().get("account"), Account.class);
			restaurant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
			businessAccount = gson.fromJson(body.getAsJsonObject().get("businessAccount"), BusinessAccount.class);
			privateAccount = gson.fromJson(body.getAsJsonObject().get("privateAccount"), PrivateAccount.class);

			int amoutOfAccount = (businessAccount != null) ? 1 : 0;
			amoutOfAccount += (privateAccount != null) ? 1 : 0;
			amoutOfAccount += (restaurant != null) ? 1 : 0;

			if (amoutOfAccount == 0)
				lableErrorMsg.setText("This user has no account to edit");
			else {
				if (amoutOfAccount == 1) {
					if (businessAccount != null)
						editBusinessAccount(event);
					else
						OpenBusinessAccountController.isEdit = false;

					if (privateAccount != null)
						editPrivateAccount(event);
					else
						OpenPrivateAccountController.isEdit = false;

					if (restaurant != null)
						editRestaurant(event);
					else
						RegisterationApprovalSupplierController.isEdit = false;
				} else {
					anchorPaneChooseAccount.setVisible(true);
					anchorPaneEditUsers.setVisible(false);

				}
			}
			System.out.println("businessAccount: " + businessAccount);
			System.out.println("privateAccount: " + privateAccount);
			System.out.println("restaurant: " + restaurant);
			btnEditBusinessAccount.setDisable(OpenBusinessAccountController.isEdit);
			btnEditPrivateAccount.setDisable(OpenPrivateAccountController.isEdit);
			btnEditRestaurant.setDisable(RegisterationApprovalSupplierController.isEdit);
		}
	}

	/**
	 * A method for edit the business account
	 * 
	 * @param event
	 */
	@FXML
	void editBusinessAccount(ActionEvent event) {
		OpenBusinessAccountController.isEdit = true;
		OpenBusinessAccountController.businessAccount = businessAccount;
		OpenBusinessAccountController.account = account;
		branchManagerFunctions.reload(event, "/branchManager/OpenBusinessAccount.fxml",
				"Branch manager - Edit business account");
	}

	/**
	 * A method for edit the private account
	 * 
	 * @param event
	 */
	@FXML
	void editPrivateAccount(ActionEvent event) {
		OpenPrivateAccountController.isEdit = true;
		OpenPrivateAccountController.privateAccount = privateAccount;
		OpenPrivateAccountController.account = account;
		branchManagerFunctions.reload(event, "/branchManager/OpenPrivateAccount.fxml",
				"Branch manager - Edit private account");
	}

	/**
	 * A method for edit the restaurant
	 * 
	 * @param event
	 */
	@FXML
	void editRestaurant(ActionEvent event) {
		RegisterationApprovalSupplierController.isEdit = true;
		RegisterationApprovalSupplierController.restaurant = restaurant;
		RegisterationApprovalSupplierController.supplierAccount = account;
		branchManagerFunctions.reload(event, "/branchManager/RegisterationApprovalSupplier.fxml",
				"Branch manager - Edit restaurant Supplier");

	}
}
