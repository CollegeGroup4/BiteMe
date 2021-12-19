package branchManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import client.Request;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Account;
import logic.Employee;

public class ApprovalsController implements Initializable {

	@FXML
	private TableView<Employee> tableViewMsg;

	@FXML
	private TableColumn<Employee, String> tableColFrom;

	@FXML
	private TableColumn<Employee, String> tableColTopic;

	@FXML
	private TableColumn<Employee, String> tableColStatus;

	@FXML
	private Button btnBackBM;

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

	private ObservableList<Employee> messagesList = FXCollections.observableArrayList();

	private ObservableList<?> messagesEditSelect;

//	private HashMap<Integer, Messages> allMessages = new HashMap<>();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

	}

	@FXML
	void ClickOnTable(MouseEvent event) {
		messagesEditSelect = tableViewMsg.getSelectionModel().getSelectedItems();
//		lableSlectedUser.setText("Name: " + userEditSelect.get(0).getFirstName() + " "
//				+ userEditSelect.get(0).getLastName() + ",  Role: " + userEditSelect.get(0).getRole() + ",  Status: "
//				+ userEditSelect.get(0).getStatus());
	}

	@FXML
	void approve(ActionEvent event) {
		labelStatus.setText("Approved");
		sentToJson(true);
//		response();
	}

	@FXML
	void decline(ActionEvent event) {
		labelStatus.setText("Decline");
		sentToJson(false);
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

}
