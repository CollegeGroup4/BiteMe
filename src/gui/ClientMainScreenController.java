package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import client.UserClient;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import supplier.SupplierController;

public class ClientMainScreenController extends Application {

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
//		FXMLLoader loader = new FXMLLoader();
//		Parent root = FXMLLoader .load(getClass().getResource("/supplier/SupplierPage.fxml"));
//		SupplierController sup=FXMLLoader.getController();
//		sup.setName();
//		Scene scene = new Scene(root);
//	
//		primaryStage.setTitle("Supplier");
//		primaryStage.setScene(scene);
//
//		primaryStage.show();
	

	}
	

	@FXML
	void confirmClient(ActionEvent event) throws Exception {
		ClientUI.chat = new ClientController(serverId.getText(), Integer.valueOf(getportText()));
		FXMLLoader loader = new FXMLLoader();
		//Here the client send ping to server to check if it's alive
		String[] ipHostName = new String[3];
		ipHostName[0] = "PING";
		ipHostName[1] = InetAddress.getLocalHost().getHostName();
		ipHostName[2] = InetAddress.getLocalHost().getHostAddress();
		try {
			ClientUI.chat.accept(ipHostName);
		} catch (NullPointerException e) {
			System.out.println("new ClientController didn't work");
		}
		if (UserClient.serverAns.get(0).equals("PORT"))
			getportFromServer(UserClient.serverAns.get(1));
		String p = getportText();
		if (p.trim().isEmpty()) {
			labelIncorect.setText("incorect");

		} else {
			if (p.equals(port)) {
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				Pane root = loader.load(getClass().getResource("/gui/MainScreen.fxml").openStream());
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/gui/MainScreen.css").toExternalForm());
				primaryStage.setTitle("Order");

				primaryStage.setScene(scene);
				primaryStage.show();
			} else
				labelIncorect.setText("incorect");
		}
	}

	public String getportText() {
		return portText.getText();
	}

	public void getportFromServer(String serverPort) {
		port = serverPort;
	}
	public void initialize(URL arg0, ResourceBundle arg1) { // **** for development only *****
		serverId.setText("127.0.0.1");
		portText.setText("5555");
		
	}

}
