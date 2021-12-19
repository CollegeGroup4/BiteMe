package guiNew;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import Server.Response;
import branchManager.BranchManagerController;
import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import client.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Account;
import logic.BranchManager;


public class LoginController {
	// private DB db = new DB(5555);
//	private ClientController client;
	@FXML
	private TextField textFieldUsername;

	@FXML
	private TextField textFieldPassword;

	@FXML
	private Button btnLogin;

	@FXML
	private Label lableErrorMag;

	@FXML
	private HBox Nav1;

	@FXML
	private HBox Nav;
	private boolean flag;

	@FXML
	void Login(ActionEvent event) {
		System.out.println("Logged in");
		flag = true;
		checkTextFiled(textFieldUsername, lableErrorMag);
		checkTextFiled(textFieldPassword, lableErrorMag);
		String username = textFieldUsername.getText();
		String password = textFieldPassword.getText();
		sentToJson(username, password);
		Account account=null;// = response();
		// need to get response from the server hear!
		if (flag) {
			try {
				String role ="branch manager"; //account.getRole();
				if (role != "") {
					FXMLLoader loader = new FXMLLoader();
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
					Stage primaryStage = new Stage();
					AnchorPane root = null;
					switch (role) {
					case "branch manager":
						System.out.println("go to barnch manager");
						BranchManagerController.branchManager = account;
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case "supplier":
						System.out.println("go to supplier");
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case "user":
						System.out.println("go to ordinary user");
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					default:
						System.out.println("defult - error DB the user does not have a file");
					}
					lableErrorMag.setText("");
					Scene scene = new Scene(root);
					primaryStage.setTitle("Home");
					primaryStage.setScene(scene);
					primaryStage.show();
				} else
					lableErrorMag.setText("username or password incorrect");
			} catch (IOException e) {
			}
		}
	}

	private void sentToJson(String username, String password) {
		Gson gson = new Gson();
		JsonElement jsonElem = gson.toJsonTree(new Object());
		jsonElem.getAsJsonObject().addProperty("userName", username);
		jsonElem.getAsJsonObject().addProperty("password", password);

		Request request = new Request();
		request.setPath("/users/login");
		request.setMethod("GET");
		request.setBody(gson.toJson(jsonElem));
		JsonElement jsonUser = gson.toJsonTree(request);

		String jsonFile = gson.toJson(jsonUser);
//    	System.out.println("jsonFile : "+jsonFile);
		try {
			ClientUI.chat.accept(jsonFile); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("new ClientController didn't work");
		}
	}

//	private Account response() {
//		Gson gson = new Gson();
//		Response response = gson.fromJson(ChatClient.serverAns, Response.class);
//		if (response.getCode() != 200 && response.getCode() != 201) 
//			lableErrorMag.setText(response.getDescription());// error massage
//		
//		System.out.println("-->>"+response.getDescription()); // Description from server
//		JsonElement jsonFile = gson.toJsonTree(response.getBody());
//		Account account = gson.fromJson(jsonFile, Account.class);
//		return account;
//	}

	void checkTextFiled(TextField textField, Label lblrequired) {
		if (textField.getText().equals("")) {
			lblrequired.setText("Please fill all the fields");
			flag = false;
		} else {
			lblrequired.setText("");
		}
	}

}
