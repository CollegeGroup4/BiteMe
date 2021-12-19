package gui;

import java.net.InetAddress;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientMainScreenController {
	private ClientController client;

	private String port;

	@FXML
	private TextField portText;

	@FXML
	private Button bntConfirm;

	@FXML
	private Label labelIncorect;

	@FXML
	private Button btnEXE;
	
    @FXML
    private TextField serverId;


	@FXML
	void Exit(ActionEvent event) throws Exception {
		String[] ipHostName = new String[3];
		ipHostName[0] = "PING";
		ipHostName[1] = InetAddress.getLocalHost().getCanonicalHostName();
		ipHostName[2] = InetAddress.getLocalHost().getHostAddress();
		try {
			ClientUI.chat.accept(ipHostName);
		} catch (NullPointerException e) {
			System.out.println("new ClientController didn't work");
		}
		System.exit(0);
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root;
		root = FXMLLoader.load(getClass().getResource("/gui/ClientMainScreen.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/ClientMainScreen.css").toExternalForm());
		primaryStage.setTitle("Client Confirmation");
		primaryStage.setScene(scene);

		primaryStage.show();

	}

	@FXML
	void confirmClient(ActionEvent event) throws Exception {
		ClientUI.chat = new ClientController(serverId.getText(), Integer.valueOf(getportText()));
//		ClientUI.chat = new ClientController("192.168.56.1", 5555);

		FXMLLoader loader = new FXMLLoader();
		//Here the client send ping to server to check if it's alive
		sentToJson();
		
//		if (ChatClient.serverAns.get(0).equals("PORT"))
//			getportFromServer(ChatClient.serverAns.get(1));
//		
		
//		String p = getportText();
//		if (p.trim().isEmpty()) {
//			labelIncorect.setText("incorect");
//
//		} else {
//			if (p.equals(port)) {
//				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
//				Stage primaryStage = new Stage();
//				AnchorPane root = loader.load(getClass().getResource("/guiNew/Login.fxml").openStream());
//				Scene scene = new Scene(root);
//				primaryStage.setTitle("Login");
//				primaryStage.setScene(scene);
//				primaryStage.show();
//			} else
//				labelIncorect.setText("incorect");
//		}
	}
	private void sentToJson() {
		Gson gson = new Gson();
		Request request = new Request();
		request.setPath("/ping");
//		JsonElement jsonUser = gson.toJsonTree(request);
//		String jsonFile = gson.toJson(jsonUser);
		try {
			ClientUI.chat.accept(gson.toJson(request)); // in here will be DB ask for restaurant id
		} catch (NullPointerException e) {
			System.out.println("new ClientController didn't work");
		}
	}
	public String getportText() {
		return portText.getText();
	}

	public void getportFromServer(String serverPort) {
		port = serverPort;
	}

}
