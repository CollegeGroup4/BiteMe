package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import supplier.SupplierFunction;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import client.ChatClient;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Item;
import logic.Options;

public class UpdateItemTable implements Initializable {
	/**
	 * This class made for the Items Update process
	 *
	 * @author Or Biton
	 * @author Einan Choen
	 * @author Tal Yehoshua
	 * @author Moshe Pretze;
	 * @author Tal-chen Ben-Eliyahu
	 * @version January 2022
	 * 
	 */

	@FXML
	private AnchorPane Na1;

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
	private Button backbutton;

	@FXML
	private Button Remove;

	@FXML
	private Button update;

	@FXML
	private HBox Nav;

	@FXML
	private Button LogOut;
	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;
	private SupplierFunction supplierfunction = new SupplierFunction();
	static Item updateItem = new Item(null, null, 0, 0, null, 0, null, null, null, null, 0);

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
	 * This Method made to save the chosen item to update and to go to the next update screen
	 * @param event
	 */
	@FXML
	void GoToUpdate(ActionEvent event) {

		ObservableList<Item> ItemLremoveselect = Table.getSelectionModel().getSelectedItems();

		updateItem.setCategory(ItemLremoveselect.get(0).getCategory());
		updateItem.setSubcategory(ItemLremoveselect.get(0).getSubcategory());
		updateItem.setPrice(ItemLremoveselect.get(0).getPrice());
		updateItem.setName(ItemLremoveselect.get(0).getName());
		updateItem.setItemID(ItemLremoveselect.get(0).getItemID());
		updateItem.setDescription(ItemLremoveselect.get(0).getDescription());
		updateItem.setRestaurantID(ItemLremoveselect.get(0).getRestaurantID());
		updateItem.setIngrediants(ItemLremoveselect.get(0).getIngrediants());
		updateItem.setOptions(ItemLremoveselect.get(0).getOptions());
		updateItem.setItemImage(ItemLremoveselect.get(0).getItemImage());
		updateItem.setAmount(ItemLremoveselect.get(0).getAmount());

		System.out.println("Item for Update Page");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/UpdateItem.fxml").openStream());

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open Item for Update Page");
			e.printStackTrace();

		}

	}
/**
 * This Method made to allow us to get back to the home screen
 * @param action
 */
	@FXML
	void Home(ActionEvent action) {
		supplierfunction.home(action);
	}
	/**
	 * This Method made to logout from system
	 * @param event
	 */
	@FXML
	void LogOut(ActionEvent event) {
		supplierfunction.logout(event);
	}
	/**
	 * This Method made to remove items from table and send the item to DB remove
	 * @param event
	 */
	@FXML
	void removefrommenu(ActionEvent event) {
		ObservableList<Item> ItemLremoveselect, Allitems;
		Allitems = Table.getItems();
		ItemLremoveselect = Table.getSelectionModel().getSelectedItems();

		for (Item allitems : ItemLremoveselect) {
			JsonRemove(allitems);
			Allitems.remove(allitems);
		}

	}
	/**
	 * This Method made to remove the chosen item from DB
	 * @param allitems
	 */
	private void JsonRemove(Item allitems) {

		Request request = new Request();
		request.setPath("/restaurants/items");
		request.setMethod("DELETE");
		Gson gson = new Gson();

		request.setBody(gson.toJson(allitems.getItemID()));
		ClientUI.chat.accept(gson.toJson(request));

		if (ChatClient.serverAns.getCode() != 200 || ChatClient.serverAns.getCode() != 201) {

			// Warning
		}

	}
	/**
	 * This Method made to initialize all the buttons and table requirements
	 *@param location resources
	 */
	ObservableList<Item> ItemList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		supplierfunction.initializeNavigation_SidePanel(myHamburger, drawer);

		FromJson();
		name.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
		category.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
		Ingrediants.setCellValueFactory(new PropertyValueFactory<Item, String>("Ingrediants"));

		subcategory.setCellValueFactory(new PropertyValueFactory<Item, String>("Subcategory"));
		Description.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
		Price.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));
		resturantid.setCellValueFactory(new PropertyValueFactory<Item, Integer>("restaurantID"));
		ItemID.setCellValueFactory(new PropertyValueFactory<Item, Integer>("ItemID"));
		Table.setItems(ItemList);
		supplierfunction.initializeNavigation_SidePanel(myHamburger, drawer);
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

		body.getAsJsonObject().addProperty("restaurantID", SupplierController.resturant.getId());

		request.setBody(gson.toJson(body));
		ClientUI.chat.accept(gson.toJson(request));

		body = gson.fromJson((String) ChatClient.serverAns.getBody(), JsonElement.class);

		Item[] items = gson.fromJson(body.getAsJsonObject().get("items"), Item[].class);/// All items from restaurant
		ItemList.addAll(items);
	}

}
