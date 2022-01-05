package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import Server.HrApiService;
import Server.Response;
import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Account;
import logic.BusinessAccount;
import logic.Category;

import logic.Order;

public class BusinessApproveController implements Initializable {
	/**
	 * This class made for H.R to approve Business account process
	 *
	 * @author Or Biton
	 * @author Einan Choen
	 * @author Tal Yehoshua
	 * @author Moshe Pretze;
	 * @author Tal-chen Ben-Eliyahu
	 * @version January 2022
	 * 
	 */
	@FXML
	private TableView<Account> tableViewMsg;

	@FXML
	private TableColumn<Account, String> tableColFrom;

	@FXML
	private TableColumn<Account, String> tableColRole;

	@FXML
	private TableColumn<Account, String> tableColStatus;

	@FXML
	private Button btnBackBM;

	@FXML
	private Label labelStatus;

	@FXML
	private Label labelFrom;

	@FXML
	private Label labelUserName;

	@FXML
	private Label labelUserID;

	@FXML
	private TextField newnametext;

	@FXML
	private Button btnDecline;

	@FXML
	private Button btnApprove;

	@FXML
	private Label lableSlectedUser;
	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;
	@FXML
	private HBox Nav;
	private HRFunction HRF = new HRFunction();
	private ObservableList<Account> messagesList = FXCollections.observableArrayList();
	private ObservableList<Account> messagesEditSelect;
	private BusinessAccount[] businessaccount;

	/**
	 * This Method made to initialize all the buttons and table requirements
	 * 
	 * @param location resources
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HRF.initializeNavigation_SidePanel(myHamburger, drawer);

		FromJson();

		for (BusinessAccount busac : businessaccount) {

			messagesList.add(busac);
		}

		tableColFrom.setCellValueFactory(new PropertyValueFactory<Account, String>("userName"));
		tableColRole.setCellValueFactory(new PropertyValueFactory<Account, String>("role"));
		tableColStatus.setCellValueFactory(new PropertyValueFactory<Account, String>("status"));
		tableViewMsg.setItems(messagesList);
		HRF.initializeNavigation_SidePanel(myHamburger, drawer);
	}

	/**
	 * When clicked on table we will show additional information
	 * 
	 * @param event
	 */
	@FXML
	void ClickOnTable(MouseEvent event) {
		messagesEditSelect = tableViewMsg.getSelectionModel().getSelectedItems();
		lableSlectedUser.setText("Name: " + messagesEditSelect.get(0).getFirstName() + " "
				+ messagesEditSelect.get(0).getLastName() + ",  Role: " + messagesEditSelect.get(0).getRole()
				+ ",  Status: " + messagesEditSelect.get(0).getStatus());
	}

	/**
	 * When clicked on approve we will send to server the chosen account
	 * 
	 * @param event
	 */
	@FXML
	void approve(ActionEvent event) {
		labelStatus.setText("Approved");
		messagesEditSelect = tableViewMsg.getSelectionModel().getSelectedItems();

		BusinessAccount newbus = new BusinessAccount(messagesEditSelect.get(0).getUserID(),
				messagesEditSelect.get(0).getUserName(), messagesEditSelect.get(0).getPassword(),
				messagesEditSelect.get(0).getFirstName(), messagesEditSelect.get(0).getLastName(),
				messagesEditSelect.get(0).getEmail(), messagesEditSelect.get(0).getRole(),
				messagesEditSelect.get(0).getPhone(), messagesEditSelect.get(0).getStatus(),
				messagesEditSelect.get(0).isBusiness(), messagesEditSelect.get(0).getBranch_manager_ID(),
				messagesEditSelect.get(0).getArea(), messagesEditSelect.get(0).getDebt(), null, 0, null, null, 0);

		newbus.setBusiness(true);
		newbus.setBusinessName(newnametext.getText());
		newbus.setIsApproved(true);
		sentoserver(newbus);

	}

	/**
	 * This method send and update the account approval
	 * 
	 * @param newbus
	 */
	private void sentoserver(BusinessAccount newbus) {

		Request request = new Request();
		request.setPath("/hr/approveBusinessAccount");
		request.setMethod("POST");
		Gson gson = new Gson();

		request.setBody(gson.toJson(newbus));
		ClientUI.chat.accept(gson.toJson(request));

		if (ChatClient.serverAns.getCode() != 200 && ChatClient.serverAns.getCode() != 201) {

			// Warning
		}

	}

	/**
	 * This method set the label to decline
	 * 
	 * @param event
	 */
	@FXML
	void decline(ActionEvent event) {
		labelStatus.setText("Decline");

	}

	/**
	 * This method send back to H.R main screen
	 * 
	 * @param event
	 */
	@FXML
	void backHM(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			AnchorPane root;
			root = loader.load(getClass().getResource("/supplier/H.R.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setTitle("H.R");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This Method made to get all theBusines sAccounts that not approved from DB
	 * 
	 */
	void FromJson() {

		Request request = new Request();
		request.setPath("/hr/businessAccount");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());
		System.out.println(HRPageController.Hmanger.getBranch_manager_ID());
		body.getAsJsonObject().addProperty("branchManagerID", HRPageController.Hmanger.getBranch_manager_ID());

		request.setBody(gson.toJson(body));
		ClientUI.chat.accept(gson.toJson(request));
		businessaccount = gson.fromJson((String) ChatClient.serverAns.getBody(), BusinessAccount[].class);

	}

	@FXML
	void home(ActionEvent event) {
		HRF.home(event);
	}

	@FXML
	void logout(ActionEvent event) {
		HRF.logout(event);
	}
}
