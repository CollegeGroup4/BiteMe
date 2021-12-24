package guiNew;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import CEO.CEOController;
import Server.EchoServer;
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
		System.out.println("Login page");
		flag = true;
		checkTextFiled(textFieldUsername, lableErrorMag);
		checkTextFiled(textFieldPassword, lableErrorMag);
		String username = textFieldUsername.getText();
		String password = textFieldPassword.getText();
		sentToJson(username, password);
		Account account = response();
		// need to get response from the server hear!
		if (flag && account != null ) {
			try {
				String role = account.getRole(); //= "CEO";
				if (role != "") {
					FXMLLoader loader = new FXMLLoader();
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
					Stage primaryStage = new Stage();
					AnchorPane root = null;
					switch (role) {
					case "CEO":
						System.out.println("go to CEO");
						CEOController.CEO = account;
						root = loader.load(getClass().getResource("/CEO/CEOPage.fxml").openStream());
						break;
					case "Branch Manager":
						System.out.println("go to barnch manager");
						BranchManagerController.branchManager = account;
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case "Supplier":
						System.out.println("go to supplier");
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case "Moderator":
						System.out.println("go to supplier moderator");
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case "HR":
						System.out.println("go to HR");
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case "Client":
						System.out.println("go to ordinary client");
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
		request.setBody(jsonElem);
		String jsonUser = gson.toJson(request);
		try {
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("new ClientController didn't work");
		}
	}

	private Account response() {
		Account account = null;
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201) 
			lableErrorMag.setText(response.getDescription());// error massage
		else {
		System.out.println("-->>"+response.getDescription()); // Description from server
		JsonElement j = EchoServer.gson.fromJson((String)response.getBody(), JsonElement.class);
		 account =EchoServer.gson.fromJson(j.getAsJsonObject().get("account"), Account.class);
		}
		return account;
	}

	void checkTextFiled(TextField textField, Label lblrequired) {
		if (textField.getText().equals("")) {
			lblrequired.setText("Please fill all the fields");
			flag = false;
		} else {
			lblrequired.setText("");
		}
	}

	@FXML
	void Exit(ActionEvent event) throws UnknownHostException {
		System.out.println("exit client Tool");
		String[] ipHostName = new String[3];
		ipHostName[0] = "EXIT";
		ipHostName[1] = InetAddress.getLocalHost().getHostName();
		ipHostName[2] = InetAddress.getLocalHost().getHostAddress();
		try {
			ClientUI.chat.accept(ipHostName);
		} catch (NullPointerException e) {
			System.out.println("new ClientController didn't work");
		}
		System.exit(0);
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
