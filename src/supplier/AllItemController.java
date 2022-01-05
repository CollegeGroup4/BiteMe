package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import supplier.SupplierFunction;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Category;
import logic.Item;
import logic.Menu;
import logic.Options;

public class AllItemController implements Initializable {
	/**
	 * This class made to show all of the restaurant items in table
	 *
	 * @author Or Biton
	 * @author Einan Choen
	 * @author Tal Yehoshua
	 * @author Moshe Pretze;
	 * @author Tal-chen Ben-Eliyahu
	 * @version January 2022
	 * 
	 */
	ObservableList<Item> ItemList = FXCollections.observableArrayList();

	@FXML
	private TableView<Item> Table;

	@FXML
	private TableColumn<Item, Integer> ItemID;

	@FXML
	private TableColumn<Item, Integer> resturantid;
	@FXML
	private TableColumn<Item, String> name;

	@FXML
	private TableColumn<Item, Double> Price;

	@FXML
	private TableColumn<Item, String> Description;

	@FXML
	private TableColumn<Item, String> Ingrediants;

	@FXML
	private TableColumn<Item, Options[]> Options;
	@FXML
	private TableColumn<Item, String> category;

	@FXML
	private TableColumn<Item, String> subcategory;

	@FXML
	private HBox Nav;

	@FXML
	private Button LogOut;

	@FXML
	private HBox Nav1;

	@FXML
	private Button backbutton;
	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;

	private SupplierFunction supplierfunction = new SupplierFunction();

	/**
	 * This Method made to give us the option to get back to supplier screen
	 * 
	 * @param event
	 */
	@FXML
	void Back(ActionEvent event) {
		System.out.println("Supplier Page");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/SupplierPage.fxml").openStream());

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open Supplier Page");
			e.printStackTrace();

		}
	}

	/**
	 * This Method made to allow us to get back to the home screen
	 * 
	 * @param action
	 */
	@FXML
	void Home(ActionEvent action) {
		supplierfunction.home(action);
	}

	/**
	 * This Method made to logout from system
	 * 
	 * @param action
	 */
	@FXML
	void LogOut(ActionEvent event) {
		supplierfunction.logout(event);
	}

	/**
	 * This Method made to initialize all the buttons and table requirements
	 *
	 */
	public void initialize(URL url, ResourceBundle db) {
		FromJson();

		supplierfunction.initializeNavigation_SidePanel(myHamburger, drawer);
		name.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
		category.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
		Ingrediants.setCellValueFactory(new PropertyValueFactory<Item, String>("Ingrediants"));

		subcategory.setCellValueFactory(new PropertyValueFactory<Item, String>("Subcategory"));
		Description.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
		Price.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));
		resturantid.setCellValueFactory(new PropertyValueFactory<Item, Integer>("restaurantID"));
		ItemID.setCellValueFactory(new PropertyValueFactory<Item, Integer>("ItemID"));
		Table.setItems(ItemList);

	}

	/**
	 * This Method made to get all the restaurant items from DB
	 * 
	 */
	void FromJson() {

		Request request = new Request();
		request.setPath("/restaurants/menus");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());

		body.getAsJsonObject().addProperty("restaurantID", SupplierController.resturant.getId());// String 2 is the
																									// current
																									// restaurant ID
																									// (!!!)

		request.setBody(gson.toJson(body));
		ClientUI.chat.accept(gson.toJson(request));

		body = gson.fromJson((String) ChatClient.serverAns.getBody(), JsonElement.class);

		Item[] items = gson.fromJson(body.getAsJsonObject().get("items"), Item[].class);/// All items from restaurant
		ItemList.addAll(items);
	}

}
