package branchManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import client.ClientUI;
import common.Request;
import guiNew.Messages;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Account;
import logic.Employee;

public class ApprovalsController implements Initializable {

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
	private Label labelEmployerName;

	@FXML
	private Label labelEmployerID;

	@FXML
	private Label labelBusinessName;

	@FXML
	private Button btnDecline;

	@FXML
	private Button btnApprove;

	@FXML
	private HBox Nav;

	private ObservableList<Messages> messagesList = FXCollections.observableArrayList();

	private ObservableList<Messages> messagesEditSelect;

	private Employee[] employee;

	private void init() {
		employee = new Employee[4];
		employee[0] = new Employee("0businessName", false, "0hrName", "0hrUserName", "0branchManagerUserName");
		employee[1] = new Employee("1businessName", false, "1hrName", "1hrUserName", "1branchManagerUserName");
		employee[2] = new Employee("2businessName", false, "2hrName", "2hrUserName", "2branchManagerUserName");
		employee[3] = new Employee("3businessName", false, "3hrName", "3hrUserName", "3branchManagerUserName");

		for (int i = 0; i < employee.length; i++) {
			messagesList.add(new Messages(employee[i].getHrName(), "waiting", employee[i]));
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		init();

		tableColFrom.setCellValueFactory(new PropertyValueFactory<Messages, String>("from"));
		tableColTopic.setCellValueFactory(new PropertyValueFactory<Messages, String>("topic"));
		tableColStatus.setCellValueFactory(new PropertyValueFactory<Messages, String>("status"));

		tableViewMsg.setItems(messagesList);
		vBoxMail.setVisible(false);
	}

	@FXML
	void ClickOnTable(MouseEvent event) {
		messagesEditSelect = tableViewMsg.getSelectionModel().getSelectedItems();
		vBoxMail.setVisible(true);
		labelStatus.setText(messagesEditSelect.get(0).getStatus());
		labelFrom.setText(messagesEditSelect.get(0).getFrom());
		labelEmployerName.setText(messagesEditSelect.get(0).getEmployee().getBranchManagerUserName());
//		labelEmployerID.setText(messagesEditSelect.get(0).getEmployee().get);
		labelBusinessName.setText(messagesEditSelect.get(0).getEmployee().getBusinessName());
	}

	@FXML
	void approve(ActionEvent event) {
		labelStatus.setText("Approved");
		int index = -1;
		for (int i = 0; i < messagesList.size(); i++) {
			if (messagesList.get(i).getEmployee().getBranchManagerUserName()
					.equals(messagesEditSelect.get(0).getEmployee().getBranchManagerUserName()))
				if (messagesList.get(i).getEmployee().getHrUserName()
						.equals(messagesEditSelect.get(0).getEmployee().getHrUserName()))
					index = i;
		}
		if (index != -1) {
			Employee employee = messagesList.get(index).getEmployee();
			employee.setApproved(true);
			messagesList.remove(index);
			// sentToJson(true);
		}
//		response();
	}

	@FXML
	void decline(ActionEvent event) {
		labelStatus.setText("Decline");
		int index = -1;
		for (int i = 0; i < messagesList.size(); i++) {
			if (messagesList.get(i).getEmployee().getBranchManagerUserName()
					.equals(messagesEditSelect.get(0).getEmployee().getBranchManagerUserName()))
				if (messagesList.get(i).getEmployee().getHrUserName()
						.equals(messagesEditSelect.get(0).getEmployee().getHrUserName()))
					index = i;
		}
		if (index != -1) {
			Employee employee = messagesList.get(index).getEmployee();
			employee.setApproved(false);
			messagesList.remove(index);
			// sentToJson(true);
		}
//		response();
	}

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

	void sentToJson(boolean statusMsg) {
		Request request = new Request();
		request.setPath("/account/"); /// !?!?!?!?!?!?
		request.setMethod("POST");
		request.setBody(statusMsg);
		Gson gson = new Gson();
		JsonElement jsonUser = gson.toJsonTree(request);

		String jsonFile = gson.toJson(jsonUser);
//    	System.out.println("jsonFile : "+jsonFile);
		try {
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("new ClientController didn't work");
		}
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

}
