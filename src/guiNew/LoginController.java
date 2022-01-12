package guiNew;

import java.io.IOException;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import common.Response;
import branchManager.BranchManagerController;
import branchManager.BranchManagerFunctions;
import client.ChatClient;
import client.ClientUI;
import client.CustomerPageController;
import client.PaymentController;
import common.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Account;
import logic.BusinessAccount;

public class LoginController {
	private BranchManagerFunctions branchManagerFunctions = new BranchManagerFunctions();
	private IsendToJson isendToJson;
	private IResponse iresponse;
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

	@FXML
	private Button btnImport;
	@FXML
	private Button btnLoginw4c;
	@FXML
	private ImageView QRcode;

    @FXML
    private AnchorPane RegularLogin;
    
	private static boolean isOpen = false;

	private boolean flag;
	
	public LoginController() {
		isendToJson = new BranchManagerFunctions();
	}
	
	public void setSendToJson(IsendToJson isendToJson) {
		this.isendToJson = isendToJson;
	}
	
	public IsendToJson getSendToJson() {
		return isendToJson;
	}
	
	public void setResponse(IResponse iresponse) {
		this.iresponse = iresponse;
	}
	
	public IResponse getResponse() {
		return iresponse;
	}

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
					case "Branch Manager":
						System.out.println("go to barnch manager");
						Navigation_SidePanelController.role = "Branch Manager";
						BranchManagerController.branchManager = account;
						root = loader
								.load(getClass().getResource("/branchManager/BranchManagerPage.fxml").openStream());
						break;
					case "Client":
						System.out.println("go to ordinary client");
						Navigation_SidePanelController.role = "Client";
						CustomerPageController.client = account;
						root = loader.load(getClass().getResource("/client/CustomerPage.fxml").openStream());
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
		else {
			lableErrorMag.setText(iresponse.getDescription());// error massage
		}
	}

	private void sentToJson(String username, String password) {
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());
		body.getAsJsonObject().addProperty("userName", username);
		body.getAsJsonObject().addProperty("password", password);
		isendToJson.sentToJson("/users/login", "GET", body, "new ClientController didn't work");
	}

	public Account response() {
		Account account = null;
		Gson gson = new Gson();
		if (iresponse.getCode() != 200 && iresponse.getCode() != 201)
			return null;
		else {
			System.out.println("-->>" + iresponse.getDescription()); // Description from server
			JsonElement j = gson.fromJson((String) iresponse.getBody(), JsonElement.class);
			account = gson.fromJson(j.getAsJsonObject().get("account"), Account.class);
			PaymentController.businessAccount = gson.fromJson(j.getAsJsonObject().get("businessAccount"), BusinessAccount.class);
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
		Request request = new Request();
		request.setPath("/exit");
		Gson gson = new Gson();
		String jsonUser = gson.toJson(request);
		try {
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("Exit - new ClientController didn't work");
		}
		System.exit(0);
	}

	@FXML
	void home(ActionEvent event) {
		branchManagerFunctions.home(event);
	}

	@FXML
	void ImportData(ActionEvent event) {
		Request req = new Request();
		req.setPath("/import");
		Gson gson = new Gson();
		String jsonUser = gson.toJson(req);
		try {
			ClientUI.chat.accept(jsonUser); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("Exit - new ClientController didn't work");
		}
	}

	@FXML
	void loginw4c(ActionEvent event) {
		if(isOpen) {
			RegularLogin.setVisible(true);
			QRcode.setVisible(false);
		}
		else {
			RegularLogin.setVisible(false);
			QRcode.setVisible(true);
		}
		isOpen = !isOpen;
	
	}
}
