package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import Server.Response;
import client.ChatClient;
import client.ClientUI;
import common.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Account;
import logic.Restaurant;

public class SupplierController implements Initializable {

	@FXML
	private HBox Nav;
	@FXML
	private Button UpdateItem;
	@FXML
	private Button LogOut;

	@FXML
	private Button creatandeditmenu;

	@FXML
	private Button creatitem;

	@FXML
	private Button allitems;

	@FXML
	private Button approveorder;

	@FXML
	private Button orderbutton;

	@FXML
	private Button UpdateButton;
	public static Account supplier;
	public static Restaurant resturant;

	Gson gson = new Gson();
	private SupplierFunction supplierfunction = new SupplierFunction();

	@FXML
	void UpdateItem(ActionEvent event) {
		System.out.println("Update Item Table");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/UpdateItemTable.fxml").openStream());

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open Update Item  Tablepage");
			e.printStackTrace();

		}
	}

	@FXML
	void Home(ActionEvent action) {
		supplierfunction.home(action);
	}

	@FXML
	void LogOut(ActionEvent event) {
		supplierfunction.logout(event);
	}

	@FXML
	void AllItems(ActionEvent event) {
		
		System.out.println("All Items");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/ALL_Items.fxml").openStream());
			

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open All Items page");
			e.printStackTrace();

		}
	}

	@FXML
	void Order(ActionEvent event) {
		
	}

	@FXML
	void CreatMenu(ActionEvent event) {
		System.out.println("Create and edit");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/CreatAndEditMenu.fxml").openStream());
			

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open edit menu  page");
			e.printStackTrace();

		}
	}

	@FXML
	void CreatItem(ActionEvent event) {
		
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();

		try {
			AnchorPane root;

			root = loader.load(getClass().getResource("/supplier/CreatItem.fxml").openStream());
			

			Scene scene = new Scene(root);

			primaryStage.setTitle("creat item");

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Erorr: Could not open CreatItem  page");
			e.printStackTrace();

		}
	}

	@FXML
	void UpdateMenu(ActionEvent event) {
		System.out.println("Uapdate");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/UpdateMenuPage.fxml").openStream());

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open update  page");
			e.printStackTrace();

		}
	}

	@FXML
	void ApproveOrder(ActionEvent event) {
		System.out.println("Approve");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/ApproveOrder.fxml").openStream());

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open ApproveOrder  page");
			e.printStackTrace();

		}
	}

	@FXML
	void Back(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		if (supplier.getRole().equals("Moderator")) {
			orderbutton.setDisable(true);
			approveorder.setDisable(true);
			creatitem.setDisable(true);
			allitems.setDisable(true);
			creatandeditmenu.setDisable(true);
			UpdateItem.setDisable(true);
		}
		
		

	}
	public void callrespone() {
		Response response = ChatClient.serverAns;
		if (response.getCode() != 200 && response.getCode() != 201) {
			System.out.println(response.getDescription());
		}
		JsonElement body = gson.fromJson((String) response.getBody(), JsonElement.class);

		resturant = gson.fromJson(body.getAsJsonObject().get("restaurant"), Restaurant.class);
		if (resturant == null) {
			System.out.println("resturant Is Null");
		}
		System.out.println(resturant);
		
	}
	void sentToJson() {

		System.out.println(supplier.getUserID());

		Request request = new Request();
		request.setPath("/accounts/getAccount");
		request.setMethod("GET");

		request.setBody(gson.toJson(supplier));

		ClientUI.chat.accept(gson.toJson(request));

	}

}
