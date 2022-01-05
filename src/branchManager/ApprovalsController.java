package branchManager;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import Server.Response;
import client.ChatClient;
import guiNew.Messages;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.Employer;

/**
 * This class is for receiving notifications for the branch manager.
 * Automatically updated with thread.
 *
 * @author Or Biton
 * @author Einan Choen
 * @author Tal Yehoshua
 * @author Moshe Pretze;
 * @author Tal-chen Ben-Eliyahu
 * @version January 2022
 * 
 */

public class ApprovalsController implements Initializable {
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	@FXML
	private TableView<Messages> tableViewMsg;
	@FXML
	private TableColumn<Messages, String> tableColFrom;
	@FXML
	private TableColumn<Messages, String> tableColTopic;
	@FXML
	private TableColumn<Messages, String> tableColStatus;
	@FXML
	private VBox vBoxMail;
	@FXML
	private Label labelStatus;
	@FXML
	private Label labelFrom;
	@FXML
	private Label labelBusinessName;
	@FXML
	private Button btnApprove;
	@FXML
	private Label lableErrorMsg;
	@FXML
	private Label lableSuccessMsg;
	@FXML
	private HBox Nav;
	@FXML
	private JFXHamburger myHamburger;
	@FXML
	private JFXDrawer drawer;

	private ObservableList<Messages> messagesList = FXCollections.observableArrayList();
	private ObservableList<Messages> messagesEditSelect;
	private Employer[] employers;

	/**
	 * initialize the Approval page- initialize the table for the employers list and
	 * initialize the navigation side panel
	 * 
	 * @param URL            location
	 * @param ResourceBundle resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sentToJson();
		response();

		branchManagerFunctions.initializeNavigation_SidePanel(myHamburger, drawer);

		tableColFrom.setCellValueFactory(new PropertyValueFactory<Messages, String>("from"));
		tableColTopic.setCellValueFactory(new PropertyValueFactory<Messages, String>("topic"));
		tableColStatus.setCellValueFactory(new PropertyValueFactory<Messages, String>("status"));

		tableViewMsg.setItems(messagesList);
		vBoxMail.setVisible(false);
	}

	/**
	 * A method that Sends to serve the id of the branch manager in order to receive
	 * from it the list of all unapproved employers
	 */
	private void sentToJson() {
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());
		body.getAsJsonObject().addProperty("branchManagerID", BranchManagerController.branchManager.getUserID());
		branchManagerFunctions.sentToJson("/branch_manager/employers", "GET", body,
				"Edit personal info - new ClientController didn't work");
	}

	/**
	 * A method that gets the list of all unapproved employers from server
	 */
	private void response() {
		Gson gson = new Gson();
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201)
			lableErrorMsg.setText(response.getDescription());// error massage
		else {
			employers = gson.fromJson((String) response.getBody(), Employer[].class);
			for (int i = 0; i < employers.length; i++) { // update the list of users to be the response from the DB
				messagesList.add(new Messages(employers[i].getHrUserName(), "waiting", employers[i]));
			}
		}
	}

	/**
	 * A method that updates the selected message by clicking on the table
	 * 
	 * @param event
	 */
	@FXML
	void ClickOnTable(MouseEvent event) {
		if (messagesList.size() != 0) {
			messagesEditSelect = tableViewMsg.getSelectionModel().getSelectedItems();
			vBoxMail.setVisible(true);
			labelStatus.setText(messagesEditSelect.get(0).getStatus());
			labelFrom.setText(messagesEditSelect.get(0).getFrom());
			labelBusinessName.setText(messagesEditSelect.get(0).getEmployer().getBusinessName());
		} else
			vBoxMail.setVisible(false);
	}

	/**
	 * A method that updates the employer to be approval
	 * 
	 * @param event
	 */
	@FXML
	void approve(ActionEvent event) {
		labelStatus.setText("Approved");
		int index = -1;
		for (int i = 0; i < messagesList.size(); i++) {
			if (messagesList.get(i).getEmployer().getBranchManagerID() == messagesEditSelect.get(0).getEmployer()
					.getBranchManagerID())
				if (messagesList.get(i).getEmployer().getHrUserName()
						.equals(messagesEditSelect.get(0).getEmployer().getHrUserName()))
					index = i;
		}
		if (index != -1) {
			Employer employer = messagesList.get(index).getEmployer();
			employer.setApproved(true);
			messagesList.remove(index);

			sentToServerForApproval(employer);

			responseFromApproval();
		}
		if (messagesList.size() == 0)
			vBoxMail.setVisible(false);
	}

	/**
	 * A method that sends the ID of the branch manager and the name of the business
	 * to the server so that he can update the DB
	 * 
	 * @param employer
	 */
	private void sentToServerForApproval(Employer employer) {
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());
		body.getAsJsonObject().addProperty("businessName", employer.getBusinessName());
		body.getAsJsonObject().addProperty("branchManagerID", BranchManagerController.branchManager.getUserID());
		branchManagerFunctions.sentToJson("/branch_manager/employers", "POST", body,
				"Edit personal info - new ClientController didn't work");
	}

	/**
	 * A method that receives the server's response if the employer's update
	 * successfully returns code 200 or 201
	 */
	private void responseFromApproval() {
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201) {
			lableErrorMsg.setText(response.getDescription());// error massage
		} else
			lableSuccessMsg.setText(response.getDescription());
	}

	/**
	 * A method that returns to the branch manager's home screen
	 * 
	 * @param event
	 */
	@FXML
	void backBM(ActionEvent event) {
		branchManagerFunctions.reload(event, "/branchManager/BranchManagerPage.fxml", "Branch manager");
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

}
