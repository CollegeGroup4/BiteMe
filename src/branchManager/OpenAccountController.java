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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import logic.Account;
import logic.BusinessAccount;
import logic.PrivateAccount;
import logic.Restaurant;

/**
 * This class is for the branch manager to select the type of account to open
 * for a user. From here you can open account Only if the user is registered in
 * the database of the user management system then an account can be opened for
 * him
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */

public class OpenAccountController implements Initializable {
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	@FXML
	private HBox Nav;
	@FXML
	private Button btnPrivateAccount;
	@FXML
	private Button btnBusinessAccount;
	@FXML
	private Label lableHello;
	@FXML
	private JFXHamburger myHamburger;
	@FXML
	private JFXDrawer drawer;
	@FXML
	private BorderPane borderPane;
	@FXML
	private AnchorPane chooseTypeAccount;
	@FXML
	private AnchorPane chooseAccount;
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
	private Label lableErrorMsg;
	@FXML
	private Label lableSlectedUser;

	private ObservableList<Account> userList = FXCollections.observableArrayList();
	private ObservableList<Account> usersFilter = FXCollections.observableArrayList();

	private ObservableList<Account> userEditSelect;
	private HashMap<String, Account> allUsres = new HashMap<>();

	/**
	 * initialize the open account page- initialize the table for the account list and
	 * initialize the navigation side panel
	 * 
	 * @param URL            location
	 * @param ResourceBundle resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lableHello.setText("Hello, " + BranchManagerController.branchManager.getFirstName());

		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
		chooseAccount.setVisible(true);
		chooseTypeAccount.setVisible(false);
		userList.clear();
		Gson gson = new Gson();
		JsonElement jsonElem = gson.toJsonTree(new Object());
		jsonElem.getAsJsonObject().addProperty("branchManagerID", BranchManagerController.branchManager.getUserID());
		branchManagerFunctions.sentToJson("/accounts", "GET", jsonElem,
				"Edit personal info - new ClientController didn't work");

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
		coboBoxRole.getItems().setAll("All", "HR", "Client", "Supplier");
		coboBoxRole.setValue("All");
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
	 * A method that sends the account to the server to get all the accounts for the
	 * selected user , when clicking on account from the table
	 * 
	 * @param event
	 */
	@FXML
	void onClickcontinue(ActionEvent event) {
		chooseAccount.setVisible(false);
		chooseTypeAccount.setVisible(true);

		if (userEditSelect.get(0) != null) {
			branchManagerFunctions.sentToJson("/accounts/getAccount", "GET", userEditSelect.get(0),
					"Edit personal info - new ClientController didn't work");

			responseGetAccountUsername();
		}
	}

	/**
	 * A method that receives all the accounts from the server of the selected user
	 * and upload the choice of account type to open
	 * 
	 * @param event
	 */
	void responseGetAccountUsername() {
		Gson gson = new Gson();
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201)
			lableErrorMsg.setText(response.getDescription());
		else {
			JsonElement j = gson.fromJson((String) response.getBody(), JsonElement.class);
			Account account = gson.fromJson(j.getAsJsonObject().get("account"), Account.class);
			Restaurant restaurant = gson.fromJson(j.getAsJsonObject().get("restaurant"), Restaurant.class);
			BusinessAccount businessAccount = gson.fromJson(j.getAsJsonObject().get("businessAccount"),
					BusinessAccount.class);
			PrivateAccount privateAccount = gson.fromJson(j.getAsJsonObject().get("privateAccount"),
					PrivateAccount.class);

			if (privateAccount != null)
				btnPrivateAccount.setDisable(true);
			else
				btnPrivateAccount.setDisable(false);

			if (businessAccount != null)
				btnBusinessAccount.setDisable(true);
			else
				btnBusinessAccount.setDisable(false);

			OpenPrivateAccountController.account = account;
			OpenBusinessAccountController.account = account;
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
	void homeANDback(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	/**
	 * A method that loads the window to open a business account
	 * 
	 * @param event
	 */
	@FXML
	void openBusinessAccount(ActionEvent event) {
		OpenBusinessAccountController.isEdit = false;
		System.out.println("Open business account");
		branchManagerFunctions.reload(event, "/branchManager/OpenBusinessAccount.fxml",
				"Branch manager - open business account");
	}

	/**
	 * A method that loads the window to open a private account
	 * 
	 * @param event
	 */
	@FXML
	void openPrivateAccount(ActionEvent event) {
		OpenPrivateAccountController.isEdit = false;
		System.out.println("Open private account");
		branchManagerFunctions.reload(event, "/branchManager/OpenPrivateAccount.fxml",
				"Branch manager - open private account");
	}

}
