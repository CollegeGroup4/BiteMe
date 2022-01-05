package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import supplier.SupplierFunction;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import Server.Response;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Category;
import logic.Item;
import logic.Menu;
import logic.Order;
import logic.item_in_menu;

public class CreatAndEditMenuController implements Initializable {
	/**
	 * This class made for supplier menu create process
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
	private TableView<Item> TableCourse;

	@FXML
	private TableColumn<Item, String> NameTableColum2;

	@FXML
	private TableColumn<Item, String> TypeColum2;
	@FXML
	private TextField menutext;

	@FXML
	private JFXButton additem;

	@FXML
	private JFXButton removeitem;

	@FXML
	private JFXComboBox<String> TypeList;

	@FXML
	private JFXComboBox<String> CourseList;

	@FXML
	private TableView<Item> TypeTable;

	@FXML
	private TableColumn<Item, String> NameTableColum1;

	@FXML
	private TableColumn<Item, String> TypeColum1;

	@FXML
	private Button savebutton;

	@FXML
	private Button editmenuname;

	@FXML
	private Button backbutton;

	@FXML
	private ImageView imageshow;

	@FXML
	private Label NameLabel;
	@FXML
	private Label menuchosentext;
	@FXML
	private Label descriptiontext;
	@FXML
	private JFXButton details;
	@FXML
	private JFXComboBox<String> subcombo;
	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;
	@FXML
	private Button LogOut;

	private SupplierFunction supplierfunction = new SupplierFunction();
	@FXML
	private Button back;

	private ObservableList<Item> ItemList = FXCollections.observableArrayList();
	private ObservableList<String> ItemList1 = FXCollections.observableArrayList();
	private ObservableList<String> Courselist = FXCollections.observableArrayList();
	private ObservableList<Item> Courselist1 = FXCollections.observableArrayList();
	private ObservableList<String> subcombolist1 = FXCollections.observableArrayList();
	private ObservableList<Item> Courselist2 = FXCollections.observableArrayList();
	private Menu[] resturantmenu;
	private item_in_menu tosave = new item_in_menu(0, 0, null, null);
	private ArrayList<item_in_menu> itemstosave = new ArrayList<item_in_menu>();

	private Menu menu = new Menu(null, 0, null);

	/**
	 * In this method we get the new menu name
	 * 
	 * @param event
	 */

	@FXML
	void editname(ActionEvent event) {

		menuchosentext.setText(menutext.getText());
		menu.setName(menutext.getText());
		menu.setRestaurantID(SupplierController.resturant.getId());
	}

	/**
	 * In this method we get all of the items by category from DB to insert them to
	 * table
	 * 
	 * @param action
	 */
	@FXML
	void TypeList1(ActionEvent action) {

		System.out.println(TypeList.getValue());

		Request request = new Request();
		request.setPath("/restaurants/menus/getItemsByCategory");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());

		body.getAsJsonObject().addProperty("restaurantID", SupplierController.resturant.getId());

		body.getAsJsonObject().addProperty("itemCategory", TypeList.getValue());
		request.setBody(gson.toJson(body));

		ClientUI.chat.accept(gson.toJson(request));
		Item[] ItemsByCataegory = gson.fromJson((String) ChatClient.serverAns.getBody(), Item[].class);

		for (int i = 0; i < ItemsByCataegory.length; i++) {
			ItemList.add(ItemsByCataegory[i]);
		}
		System.out.println(Arrays.toString(ItemsByCataegory));
		TypeTable.setItems(ItemList);

	}

	/**
	 * This Method get all of items sorted by subcategory of the restaurant from DB
	 * to insert to table
	 * 
	 * @param event
	 */
	@FXML
	void subcomboclick(ActionEvent event) {

		Request request = new Request();
		request.setPath("/restaurants/menus/category/getItemsBySubCategory");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());

		body.getAsJsonObject().addProperty("restaurantID", SupplierController.resturant.getId());
		body.getAsJsonObject().addProperty("itemCategory", TypeList.getValue());
		body.getAsJsonObject().addProperty("itemSubCategory", subcombo.getValue());
		request.setBody(gson.toJson(body));
		ClientUI.chat.accept(gson.toJson(request));
		Item[] ItemsBySubCataegory = gson.fromJson((String) ChatClient.serverAns.getBody(), Item[].class);

		for (int i = 0; i < ItemsBySubCataegory.length; i++) {
			ItemList.add(ItemsBySubCataegory[i]);
		}

		TypeTable.setItems(ItemList);

	}

	/**
	 * This method insert sub category values to the new menu
	 * 
	 * @param event
	 */
	@FXML
	void coursefromiteminmenu(ActionEvent event) {

		for (Item additem : Courselist1) {

			if (additem.getSubcategory() == CourseList.getValue()) {
				Courselist2.add(additem);
			}
		}
		TableCourse.setItems(Courselist2);

	}

	/**
	 * This method set the sub category and category comboboxs from DB
	 */
	void categoryset() {
		TypeList.setPromptText("enter");
		CourseList.setPromptText("pick a Course");
		subcombo.setPromptText("Choose Subcategory");

		Request request = new Request();
		request.setPath("/restaurants/items/categories");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());

		body.getAsJsonObject().addProperty("restaurantNum", SupplierController.resturant.getId());

		request.setBody(gson.toJson(body));

		ClientUI.chat.accept(gson.toJson(request));
		Category[] categories = gson.fromJson((String) ChatClient.serverAns.getBody(), Category[].class);

		for (int i = 0; i < categories.length; i++) {

			ItemList1.add(categories[i].getCategory());

			subcombolist1.addAll(categories[i].getSubCategory());

		}

		TypeList.setItems(ItemList1);
		subcombo.setItems(subcombolist1);
		CourseList.setItems(subcombolist1);
	}

	/**
	 * This method get all of the restaurant items and menus
	 */
	public void getmenus() {

		Request request = new Request();
		request.setPath("/restaurants/menus");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());

		body.getAsJsonObject().addProperty("restaurantID", SupplierController.resturant.getId());

		request.setBody(gson.toJson(body));
		ClientUI.chat.accept(gson.toJson(request));

		body = gson.fromJson((String) ChatClient.serverAns.getBody(), JsonElement.class);
		resturantmenu = gson.fromJson(body.getAsJsonObject().get("menues"), Menu[].class);
		Item[] items = gson.fromJson(body.getAsJsonObject().get("items"), Item[].class);/// All items from restaurant
	}

	/**
	 * This Method made to initialize all the buttons and table requirements
	 * 
	 * @param location resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		menutext.setPromptText("Enter menu name");

		categoryset();
		supplierfunction.initializeNavigation_SidePanel(myHamburger, drawer);

		getmenus();

		NameTableColum1.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
		TypeColum1.setCellValueFactory(new PropertyValueFactory<Item, String>("category"));

		NameTableColum2.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
		TypeColum2.setCellValueFactory(new PropertyValueFactory<Item, String>("category"));
		supplierfunction.initializeNavigation_SidePanel(myHamburger, drawer);
	}

	/**
	 * In this method we insert chosen item from left table to the new table in the
	 * right
	 * 
	 * @param action
	 */
	@FXML
	void addtomenu(ActionEvent action) {
		ObservableList<Item> Itemtoadd;

		Itemtoadd = TypeTable.getSelectionModel().getSelectedItems();

		for (Item additem : Itemtoadd) {
			Courselist1.add(additem);

			Itemadd(additem);
		}
		TableCourse.setItems(Courselist1);
	}

	/**
	 * setting items to save in the new menu
	 * 
	 * @param itemadd
	 */
	void Itemadd(Item itemadd) {

		tosave.setItemID(itemadd.getItemID());
		tosave.setMenu_name(menu.getName());
		tosave.setRestaurantNum(itemadd.getRestaurantID());
		tosave.setCourse(CourseList.getValue());
		itemstosave.add(tosave);

	}

	/**
	 * Remove from the right table and list
	 * 
	 * @param action
	 */
	@FXML
	void removefrommenu(ActionEvent action) {
		ObservableList<Item> ItemLremoveselect, Allitems;
		Allitems = TableCourse.getItems();
		int i = 0;
		ItemLremoveselect = TableCourse.getSelectionModel().getSelectedItems();

		for (Item allitems : ItemLremoveselect) {

			Allitems.remove(allitems);

			if (allitems.getItemID() == itemstosave.get(i).getItemID()) {
				itemstosave.remove(i);
			}
			i++;
		}

	}

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
	 * when pressed arrange the items to save and call send to DB
	 * 
	 * @param event
	 */
	@FXML
	void savemenu(ActionEvent event) {

		item_in_menu[] it = new item_in_menu[itemstosave.size()];
		itemstosave.toArray(it);

		menu.setItems(it);

		sendtoserver();
	}

	/**
	 * Save the new menu in DB
	 */
	void sendtoserver() {

		Request request = new Request();
		request.setPath("/restaurants/menus");
		request.setMethod("POST");
		Gson gson = new Gson();

		request.setBody(gson.toJson(menu));
		ClientUI.chat.accept(gson.toJson(request));

		if (ChatClient.serverAns.getCode() != 200 || ChatClient.serverAns.getCode() != 201) {

			// Warning
		}

	}

	/**
	 * anchor pane to add more of the table chosen item details
	 * 
	 * @param event
	 */
	@FXML
	void SetAnchor(ActionEvent event) {

		ObservableList<Item> ItemselectType, Itemselectcourse;
		ItemselectType = TypeTable.getSelectionModel().getSelectedItems();
		Itemselectcourse = TableCourse.getSelectionModel().getSelectedItems();

		for (Item display : ItemselectType) {
			NameLabel.setText(display.getName());

			System.out.println(NameLabel);

			NameLabel.setVisible(true);
			descriptiontext.setText(display.getDescription());
			descriptiontext.setVisible(true);

		}
		for (Item display : Itemselectcourse) {
			NameLabel.setText(display.getName());

			System.out.println(NameLabel);

			NameLabel.setVisible(true);
			descriptiontext.setText(display.getDescription());
			descriptiontext.setVisible(true);

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
	 * @param event
	 */
	@FXML
	void LogOut(ActionEvent action) {
		supplierfunction.logout(action);
	}

}