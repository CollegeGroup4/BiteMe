package branchManager;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import Server.EchoServer;
import Server.Response;
import client.ChatClient;
import guiNew.Navigation_SidePanelController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Account;
import logic.BusinessAccount;
import logic.PrivateAccount;
import logic.Restaurant;

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

	@FXML
	void ClickOnTable(MouseEvent event) {
		userEditSelect = tableViewUsers.getSelectionModel().getSelectedItems();
		lableSlectedUser.setText("Name: " + userEditSelect.get(0).getFirstName() + " "
				+ userEditSelect.get(0).getLastName() + ",  Role: " + userEditSelect.get(0).getRole() + ",  Status: "
				+ userEditSelect.get(0).getStatus());

	}

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

			System.out.println("response.getBody(): " + response.getBody());
//			System.out.println("name: " + businessAccount.getBusinessName());
			System.out.println("account: " + account);
			System.out.println("restaurant: " + restaurant);
			System.out.println("businessAccount: " + businessAccount);
			System.out.println("privateAccount: " + privateAccount);
			
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
			

//			Account account = EchoServer.gson.fromJson((String) response.getBody(), Account.class);
			// ?!?!?! what they are return a 1 account or 2 ? or both?
		}
	}

	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	@FXML
	void homeANDback(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	@FXML
	void openBusinessAccount(ActionEvent event) {
		OpenBusinessAccountController.isEdit = false;
		System.out.println("Open business account");
		branchManagerFunctions.reload(event, "/branchManager/OpenBusinessAccount.fxml",
				"Branch manager - open business account");
	}

	@FXML
	void openPrivateAccount(ActionEvent event) {
		OpenPrivateAccountController.isEdit = false;
		System.out.println("Open private account");
		branchManagerFunctions.reload(event, "/branchManager/OpenPrivateAccount.fxml",
				"Branch manager - open private account");
	}

}
