package branchManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import client.ClientUI;
import common.Request;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Account;
import logic.BusinessAccount;
import logic.PrivateAccount;
import logic.Restaurant;

public class EditPersonalInfoController implements Initializable {
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	@FXML
	private HBox Nav;

	@FXML
	private Label lableHello;
	@FXML
	private AnchorPane anchorPaneEditUsers;
	@FXML
	private AnchorPane anchorPaneChooseAccount;
	@FXML
	private Button btnEditPrivateAccount;

	@FXML
	private Button btnEditRestaurant;

	@FXML
	private Button btnEditBusinessAccount;
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
	private TextField textFiledSearchUsername;

	@FXML
	private JFXComboBox<String> coboBoxRole;
	@FXML
	private JFXComboBox<String> coboBoxStatus;
	@FXML
	private Label lableSlectedUser;

	@FXML
	private Label lblrequiredEnterid;

	@FXML
	private Label lableErrorMsg;

	@FXML
	private Label lableErrorMsgID;

	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	private ObservableList<Account> userList = FXCollections.observableArrayList();
	private ObservableList<Account> usersFilter = FXCollections.observableArrayList();

	private ObservableList<Account> userEditSelect;
	private HashMap<String, Account> allUsres = new HashMap<>();

//	void sentToJson() {
//		Request request = new Request();
//		request.setPath("/accounts");
//		request.setMethod("GET");
//		Gson gson = new Gson();
//		JsonElement jsonElem = gson.toJsonTree(new Object());
//		jsonElem.getAsJsonObject().addProperty("branchManagerID", BranchManagerController.branchManager.getUserID());
//		request.setBody(jsonElem);
//		String jsonUser = gson.toJson(request);
//		try {
//			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
//		} catch (NullPointerException e) {
//			System.out.println("Open Business account - new ClientController didn't work");
//		}
//	}

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
		coboBoxRole.getItems().setAll("All", "HR", "Supplier", "Client", "Not Assigned");
		coboBoxRole.setValue("All");

		anchorPaneChooseAccount.setVisible(false);
		anchorPaneEditUsers.setVisible(true);
		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);
	}

	@FXML
	void logout(ActionEvent event) {
		branchManagerFunctions.logout(event);
	}

	@FXML
	void homeAndback(ActionEvent event) {
		branchManagerFunctions.home(event);
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

	/*
	 * Edit the account, when given account from the table
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
	 * select id and edit the account. (businesses, private or supplier)
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
			else{
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

	private BusinessAccount businessAccount;
	private PrivateAccount privateAccount;
	private Restaurant restaurant;
	private Account account;

	@FXML
	void editBusinessAccount(ActionEvent event) {
		OpenBusinessAccountController.isEdit = true;
		OpenBusinessAccountController.businessAccount = businessAccount;
		OpenBusinessAccountController.account = account;
		branchManagerFunctions.reload(event, "/branchManager/OpenBusinessAccount.fxml",
				"Branch manager - Edit business account");
	}

	@FXML
	void editPrivateAccount(ActionEvent event) {
		OpenPrivateAccountController.isEdit = true;
		OpenPrivateAccountController.privateAccount = privateAccount;
		OpenPrivateAccountController.account = account;
		branchManagerFunctions.reload(event, "/branchManager/OpenPrivateAccount.fxml",
				"Branch manager - Edit private account");
	}

	@FXML
	void editRestaurant(ActionEvent event) {
		RegisterationApprovalSupplierController.isEdit = true;
		RegisterationApprovalSupplierController.restaurant = restaurant;
		RegisterationApprovalSupplierController.supplierAccount = account;
		branchManagerFunctions.reload(event, "/branchManager/RegisterationApprovalSupplier.fxml",
				"Branch manager - Edit restaurant Supplier");

	}
}
