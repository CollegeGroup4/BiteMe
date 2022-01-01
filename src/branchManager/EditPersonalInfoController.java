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

public class EditPersonalInfoController implements Initializable {
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	@FXML
	private HBox Nav;

	@FXML
	private Label lableHello;

	@FXML
	private TableView<Account> tableViewUsers;
	@FXML
	private TableColumn<Account, String> tableColRole;
	@FXML
	private TableColumn<Account, Integer> tableColID;
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
	private TextField textFiledSearchID;

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
	private HashMap<Integer, Account> allUsres = new HashMap<>();

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
				allUsres.put(a.getUserID(), a);
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
		tableColID.setCellValueFactory(new PropertyValueFactory<Account, Integer>("userID"));
		tableColFirstname.setCellValueFactory(new PropertyValueFactory<Account, String>("firstName"));
		tableColLastName.setCellValueFactory(new PropertyValueFactory<Account, String>("lastName"));
		tableColArea.setCellValueFactory(new PropertyValueFactory<Account, String>("area"));
		tableColPhonenum.setCellValueFactory(new PropertyValueFactory<Account, String>("phone"));
		tableColStatus.setCellValueFactory(new PropertyValueFactory<Account, String>("status"));
		usersFilter = userList;
		tableViewUsers.setItems(usersFilter);

		lableHello.setText("Hello, " + BranchManagerController.branchManager.getFirstName());
		coboBoxStatus.getItems().setAll("All", "Active", "Frozen", "Blocked");
		coboBoxStatus.setValue("All");
		coboBoxRole.getItems().setAll("All", "Client", "Supplier");
		coboBoxRole.setValue("All");

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
	void editTable(ActionEvent event) {
		int id = userEditSelect.get(0).getUserID();
		Account selecteduser = allUsres.get(id);
		System.out.println(selecteduser.getFirstName());
		branchManagerFunctions.sentToJson("/account/getAccount", "GET", selecteduser,
				"Edit personal info - new ClientController didn't work");
//		responseGetAccountID();
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
	void editByID(ActionEvent event) {
		int id;
		if (textFiledSearchID.getText() != null) {
			try {
				id = Integer.parseInt(textFiledSearchID.getText());
				Account selecteduser = allUsres.get(id);
				System.out.println(selecteduser.getFirstName());
				branchManagerFunctions.sentToJson("/accounts/getAccountType", "GET", selecteduser,
						"Edit personal info - new ClientController didn't work");
				responseGetAccountID();
			} catch (NumberFormatException e) {
				lblrequiredEnterid.setText("Enter only numbers");
			}

		}
	}

	void responseGetAccountID() {
		Gson gson = new Gson();
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201)
			lableErrorMsgID.setText(response.getDescription());
		else {
			System.out.println("-->>" + response.getDescription()); // Description from server
//			JsonElement jsonFile = gson.toJsonTree(response.getBody());
//			jsonFile.getAsJsonObject().get("id");
			Account account = EchoServer.gson.fromJson((String) response.getBody(), Account.class);
			// ?!?!?! what they are return a 1 account or 2 ? or both?
		}
	}
}
