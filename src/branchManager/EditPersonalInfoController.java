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

import client.Request;
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

public class EditPersonalInfoController implements Initializable {
	private Account acount;

	@FXML
	private HBox Nav;
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
	private Button btnViewAllUsers1;
	@FXML
	private Button btnEditTableView;
	@FXML
	private Button btnEditID;
	@FXML
	private Button btnBackBM;
	@FXML
	private Label lableHello;
	@FXML
	private Hyperlink btnHome;
	@FXML
	private Button btnLogout;
	@FXML
	private Label lableSlectedUser;
	@FXML
	private JFXHamburger myHamburger;
	@FXML
	private JFXDrawer drawer;
	@FXML
	private TextField textFiledSearchID;
	@FXML
	private Label lblrequiredEnterid;
	@FXML
	private JFXComboBox<String> coboBoxRole;
	@FXML
	private JFXComboBox<String> coboBoxStatus;
	@FXML
	private Button btnFilter;

	private ObservableList<Account> userList = FXCollections.observableArrayList();
	private ObservableList<Account> usersFilter = FXCollections.observableArrayList();

	private ObservableList<Account> userEditSelect;
	private HashMap<Integer, Account> allUsres = new HashMap<>();

	void initallUsres() {
		allUsres.put(0, new Account(0, "TalChen", "1", "Tal-chen", "Ben-eliyau", "TalChen@gmail.com", "Supplier",
				"054444444", "Active", true, 0, "N", 0, "w4c_card"));
		allUsres.put(4, new Account(4, "Moshe", "4", "Moshe", "peretz", "Moshe@gmail.com", "User", "05111", "Active",
				true, 0, "S", 0, "w4c_card"));
		allUsres.put(2, new Account(2, "Tal", "2", "Tal", "ye", "tal@gmail.com", "Supplier", "05555", "Active", false,
				0, "N", 0, "w4c_card"));
		allUsres.put(3, new Account(3, "Or", "3", "Or", "Biton", "or@gmail.com", "Supplier", "02222", "Active", false,
				0, "S", 0, "w4c_card"));
		allUsres.put(10, new Account(10, "Enain", "10", "Enain", "Enain", "Enain@gmail.com", "User", "053333", "Active",
				true, 0, "S", 100, "w4c_card"));
	}

	void inituserList() {
		userList.add(new Account(0, "TalChen", "1", "Tal-chen", "Ben-eliyau", "TalChen@gmail.com", "Supplier",
				"054444444", "Active", true, 0, "N", 0, "w4c_card"));
		userList.add(new Account(4, "Moshe", "4", "Moshe", "peretz", "Moshe@gmail.com", "User", "05111", "Active", true,
				0, "S", 0, "w4c_card"));
		userList.add(new Account(2, "Tal", "2", "Tal", "ye", "tal@gmail.com", "Supplier", "05555", "Active", false, 0,
				"N", 0, "w4c_card"));
		userList.add(new Account(3, "Or", "3", "Or", "Biton", "or@gmail.com", "Supplier", "02222", "Active", false, 0,
				"S", 0, "w4c_card"));
		userList.add(new Account(10, "Enain", "10", "Enain", "Enain", "Enain@gmail.com", "User", "053333", "Active",
				true, 0, "S", 100, "w4c_card"));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initallUsres();
		inituserList();

		tableColRole.setCellValueFactory(new PropertyValueFactory<Account, String>("role"));
		tableColID.setCellValueFactory(new PropertyValueFactory<Account, Integer>("userID"));
		tableColFirstname.setCellValueFactory(new PropertyValueFactory<Account, String>("firstName"));
		tableColLastName.setCellValueFactory(new PropertyValueFactory<Account, String>("lastName"));
		tableColArea.setCellValueFactory(new PropertyValueFactory<Account, String>("area"));
		tableColPhonenum.setCellValueFactory(new PropertyValueFactory<Account, String>("phone"));
		tableColStatus.setCellValueFactory(new PropertyValueFactory<Account, String>("status"));
		usersFilter = userList;
		tableViewUsers.setItems(usersFilter);

		lableHello.setText("Hello, " + BranchManagerController.branchManager.getUserName());
		coboBoxStatus.getItems().setAll("All", "Active", "Frozen", "Blocked");
		coboBoxRole.getItems().setAll("All", "User", "Supplier");

		try {
			AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/guiNew/Navigation_SidePanel.fxml"));
			drawer.setSidePane(anchorPane);
		} catch (IOException e) {
			Logger.getLogger(Navigation_SidePanelController.class.getName()).log(Level.SEVERE, null, e);
		}

		// transition animation of hamburger icon
		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(myHamburger);
		drawer.setVisible(false);
		transition.setRate(-1);

		// click event - mouse click
		myHamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (drawer.isOpened()) {
				drawer.setVisible(false);
				drawer.close(); // this will close slide pane
			} else {
				drawer.open(); // this will open slide pane
				drawer.setVisible(true);
			}
		});
	}


	@FXML
	void logout(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/guiNew/HomePage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Home");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void home(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Branch manager");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		sentToJson(selecteduser);
	}

	@FXML
	void filter(ActionEvent event) {
		String status = coboBoxStatus.getValue();
		String role = coboBoxRole.getValue();
		role = role == null ? "All" : role;
		status = status == null ? "All" : status;

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
	void editID(ActionEvent event) {
		int id;
		if (textFiledSearchID.getText() != null) {
			try {
				id = Integer.parseInt(textFiledSearchID.getText());
				Account selecteduser = allUsres.get(id);
				System.out.println(selecteduser.getFirstName());
				sentToJson(selecteduser);

			} catch (NumberFormatException e) {
				lblrequiredEnterid.setText("Enter only numbers");
			}

		}
	}

	void sentToJson(Account acount) {
		Request request = new Request();
		request.setPath("/account/getAccount");/////////////////////////////////////????????????
		request.setMethod("GET");
		request.setBody(acount);
		Gson gson = new Gson();
		JsonElement jsonUser = gson.toJsonTree(request);

		String jsonFile = gson.toJson(jsonUser);
//    	System.out.println("jsonFile : "+jsonFile);
		// client.accept(jsonFile); // in here will be DB ask for restaurant id

	}

//	JsonElement response() {
//	Gson gson = new Gson();
//	Response response = gson.fromJson(ChatClient.serverAns, Response.class);
//	if (response.getCode() != 200 && response.getCode() != 201) {
//		lableSlectedUser.setText(response.getDescription());// error massage
//		lableSlectedUser.setColor to be red!!!
//	}
//	System.out.println("-->>"+response.getDescription()); // Description from server
//	JsonElement jsonFile = gson.toJsonTree(response.getBody());
//	jsonFile.getAsJsonObject().get("id");
//	return jsonFile;
//}
	@FXML
	void backBM(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("Branch manager");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void viewAllUsers(ActionEvent event) {

	}
}
