package gui;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.tools.javac.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ocsf.server.ConnectionToClient;
import Server.EchoServer;
import Server.ServerUI;
import common.DBController;

public class ServerPortFrameController implements Initializable {
//	private StudentFormController sfc;

	String temp = "";
	Client client;

	@FXML
	private TableView<Client> tblIP;

	@FXML
	private TableColumn<Client, String> ip;

	@FXML
	private TableColumn<Client, String> hostName;
	@FXML
	private Button btnExit = null;

	@FXML
	private Button btnDone = null;

	@FXML
	private TextField lblIP;// 192.168.56.1

	@FXML
	private TextField lblPort = new TextField("5555");// 5555

	@FXML
	private TextField lblDBUser;// root

	@FXML
	private TextField lblDBName;// jdbc:mysql://localhost:3306/biteme

	@FXML
	private TextField lblDBPassword;
	@FXML
	private Label lblmsgConnect;

	@FXML
	private Button btnDsconnect;

	public class Client {

		@Override
		public boolean equals(Object obj) {
			Client temp = (Client) obj;
			if(ip.equals(temp.getIp()))
				return true;
			return false;
		}

		private String ip;
		private String hostName;

		public Client(String hostName, String ip) {
			super();
			this.ip = ip;
			this.hostName = hostName;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getHostName() {
			return hostName;
		}

		public void setHostName(String hostName) {
			this.hostName = hostName;
		}

		private ServerPortFrameController getEnclosingInstance() {
			return ServerPortFrameController.this;
		}

	}

	ObservableList<Client> listClients = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle db) {

		ip.setCellValueFactory(new PropertyValueFactory<Client, String>("ip"));
		hostName.setCellValueFactory(new PropertyValueFactory<Client, String>("hostName"));

		tblIP.setItems(listClients);
		// this is us - we don't need it for the presentation
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//		try {
//			client = new Client(InetAddress.getLocalHost().getHostName(),
//					InetAddress.getLocalHost().getHostAddress());
//			listClients.add(client);
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
	}

	public void manageClientsList(ConnectionToClient ConnectionClient) {
		Client client = new Client(ConnectionClient.getInetAddress().getCanonicalHostName(),
				ConnectionClient.getInetAddress().getHostAddress());
		
		if (listClients.contains(client))
			listClients.remove(client);
		else
			listClients.add(client);
	}

	@FXML
	public void connect(ActionEvent event) throws Exception {
		String p;

		p = getport();
		if (p.trim().isEmpty()) {
			System.out.println("You must enter a port number");

		} else {
//			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
//			Stage primaryStage = new Stage();
//			FXMLLoader loader = new FXMLLoader();

			lblmsgConnect.setText("You're in! The connection was successful");
			EchoServer.url = getDBName();
			EchoServer.username = getlblDBUser();
			EchoServer.password = getDBPassword();
			ServerUI.runServer(p);
			// send the client name to DB

		}
	}

	@FXML
	void Disconnect(ActionEvent event) {
		ServerUI.closeServer();
		lblmsgConnect.setText("You're out! The connection close");

	}

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ServerPort.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/ServerPort.css").toExternalForm());
		ServerPortFrameController serverPortFrameController = loader.getController();
		setAllLables();

		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);

		primaryStage.show();

	}

	@FXML
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("exit Server Tool");
		System.exit(0);
	}

	private void setIP() {

		lblIP = new TextField("192.168.56.1");
		try {
			System.out.println("host name: " + InetAddress.getLocalHost().getHostName());
			System.out.println("ip: " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		lblIP.setPromptText("jnvjnfjnv");
//		lblIP.setFocusTraversable(false);
		// lblIP.setText("192.168.56.1");

	}

	private void setAllLables() {
		setIP();
		setport();
		setDBName();
		setlblDBUser();
		setDBPassword();
	}

	public String getIP() {
		return lblIP.getText();
	}

	private void setport() {
		lblPort = new TextField();
		lblPort.setText("5555");
	}

	public String getport() {
		return lblPort.getText();
	}

	private void setDBName() {
		lblDBName = new TextField();
		lblDBName.setText("jdbc:mysql://localhost/biteme?serverTimezone=IST");
	}

	public String getDBName() {
		return lblDBName.getText();
	}

	private void setlblDBUser() {
		lblDBUser = new TextField("root");
		lblDBUser.setText("root");
	}

	public String getlblDBUser() {
		return lblDBUser.getText();
	}

	private void setDBPassword() {
		lblDBPassword = new TextField("MoshPe2969999");
		lblDBPassword.setText("MoshPe2969999");
	}

	public String getDBPassword() {
		return lblDBPassword.getText();
	}

}