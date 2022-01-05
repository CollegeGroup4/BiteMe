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
import branchManager.BranchManagerFunctions;
import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import client.CustomerPageController;
import common.Request;
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
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();

	public static String role;

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
		if (flag && account != null) {
			try {
				role = account.getRole(); // = "CEO";
				if (role != "") {
					FXMLLoader loader = new FXMLLoader();
					((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
					Stage primaryStage = new Stage();
					AnchorPane root = null;
					HomeController.role = role;
					switch (role) {
					case "CEO":
						System.out.println("go to CEO");
						CEOController.CEO = account;
						Navigation_SidePanelController.role = "CEO";
						root = loader.load(getClass().getResource("/CEO/CEOPage.fxml").openStream());
						break;
					case "Branch Manager":
						System.out.println("go to barnch manager");
						Navigation_SidePanelController.role = "Branch Manager";
						BranchManagerController.branchManager = account;
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case "Supplier":
						System.out.println("go to supplier");
						Navigation_SidePanelController.role = "Supplier";
						
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case "Moderator":
						System.out.println("go to supplier moderator");
						Navigation_SidePanelController.role = "Moderator";
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case "HR":
						System.out.println("go to HR");
						Navigation_SidePanelController.role = "HR";
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case "Client":
						System.out.println("go to ordinary client");
						Navigation_SidePanelController.role = "Client";
						CustomerPageController.client = account;
						root = loader
								.load(getClass().getResource("/client/CustomerPage.fxml").openStream());
						break;
					default:
						Navigation_SidePanelController.role = "";
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
		JsonElement body = gson.toJsonTree(new Object());
		body.getAsJsonObject().addProperty("userName", username);
		body.getAsJsonObject().addProperty("password", password);
		branchManagerFunctions.sentToJson("/users/login", "GET", body, "new ClientController didn't work");
	}

	private Account response() {
		Account account = null;
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201)
			lableErrorMag.setText(response.getDescription());// error massage
		else {
			System.out.println("-->>" + response.getDescription()); // Description from server
			JsonElement j = EchoServer.gson.fromJson((String) response.getBody(), JsonElement.class);
			account = EchoServer.gson.fromJson(j.getAsJsonObject().get("account"), Account.class);
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
		branchManagerFunctions.exit(event);
	}

	@FXML
	void home(ActionEvent event) {
		branchManagerFunctions.home(event);
	}
}
