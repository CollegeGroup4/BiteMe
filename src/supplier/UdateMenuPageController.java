package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Category;
import logic.Item;
import logic.Menu;
import logic.item_in_menu;

public class UdateMenuPageController implements Initializable {

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
	private JFXComboBox<String> menulist;
	@FXML
	private JFXComboBox<String> subcombo;
	@FXML
	private Button backbutton;
	@FXML
	private Label NameLabel;

	@FXML
	private Label descriptiontext;

	@FXML
	private JFXButton details;
	@FXML
	private Label menuchosentext;

	@FXML
	private Button LogOut;

	///// **** in menu and items put restaurant staff in pick menu function****

	private Menu menu=new Menu(null, 0, null);

	private ObservableList<Item> ItemList = FXCollections.observableArrayList();
	private ObservableList<String> ItemList1 = FXCollections.observableArrayList();
	private ObservableList<String> Courselist = FXCollections.observableArrayList();
	private ObservableList<Item> Courselist1 = FXCollections.observableArrayList();
	private ObservableList<String> Menulist = FXCollections.observableArrayList();
	private ObservableList<String> subcombolist1 = FXCollections.observableArrayList();
	private Menu[] resturantmenu;
	private item_in_menu tosave;
	private ArrayList<item_in_menu> itemstosave = new ArrayList<item_in_menu>();

	@FXML
	void Back(ActionEvent event) {
		System.out.println("Supplier Page");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/SupplierPage.fxml").openStream());
			// CreatAndEditMenuController creatitemcontrol=loader.getController();

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open Supplier Page");
			e.printStackTrace();

		}
	}

	@FXML
	void LogOut(ActionEvent event) {

	}

	@FXML
	void PickMenu(ActionEvent event) {

		menuchosentext.setText(menulist.getValue());

		menu.setName(menulist.getValue());

		// System.out.print(menu.getName());

	}

	@FXML
	void coursefromiteminmenu(ActionEvent event) {

		/// enter values to TableCourse from course string in item_in_menu (DB) /////

		Request request = new Request();
		request.setPath("/restaurants/menus/categories/getItemsBySubCategory");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());

		body.getAsJsonObject().addProperty("restaurantID", 10);// String 2 is the current restaurant ID (!!!)

		body.getAsJsonObject().addProperty("itemCategory", TypeList.getValue());
		body.getAsJsonObject().addProperty("itemSubCategory", CourseList.getValue());
		request.setBody(gson.toJson(body));
		ClientUI.chat.accept(gson.toJson(request));
		Item[] ItemsBySubCataegory = gson.fromJson((String) ChatClient.serverAns.getBody(), Item[].class);

		for (int i = 0; i < ItemsBySubCataegory.length; i++) {
			ItemList.add(ItemsBySubCataegory[i]);
		}

		TableCourse.setItems(ItemList);
	}

	@FXML
	void TypeList1(ActionEvent action) {

		System.out.println(TypeList.getValue());

		Request request = new Request();
		request.setPath("/restaurants/menus/getItemsByCategory");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());

		body.getAsJsonObject().addProperty("restaurantID", 10);// String 2 is the current restaurant ID (!!!)

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

	@FXML
	void subcomboclick(ActionEvent event) {

		Request request = new Request();
		request.setPath("/restaurants/menus/category/getItemsBySubCategory");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());

		body.getAsJsonObject().addProperty("restaurantID", 10);// String 2 is the current restaurant ID (!!!)

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

	public void getmenus() {

		Request request = new Request();
		request.setPath("/restaurants/menus");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());

		body.getAsJsonObject().addProperty("restaurantID",10);// String 2 is the current restaurant ID (!!!)

		request.setBody(gson.toJson(body));
		ClientUI.chat.accept(gson.toJson(request));

		body = gson.fromJson((String) ChatClient.serverAns.getBody(), JsonElement.class);
		resturantmenu=gson.fromJson(body.getAsJsonObject().get("menues"), Menu[].class);
		Item [] items= gson.fromJson(body.getAsJsonObject().get("items"), Item[].class);///All items from restaurant 
	}


	@FXML
	void addtomenu(ActionEvent action) {
		ObservableList<Item> Itemtoadd;

		Itemtoadd = TypeTable.getSelectionModel().getSelectedItems();

		for (Item additem : Itemtoadd) {
			Courselist1.add(additem);
			// ItemList.remove(additem) ;///////// not needed
			Itemadd(additem);
		}
//		TableCourse.setItems(Courselist1);
	}

	void Itemadd(Item itemadd) {

		tosave.setItemID(itemadd.getItemID());
		tosave.setMenu_name(menu.getName());
		tosave.setRestaurantNum(itemadd.getRestaurantID());
		tosave.setCourse(CourseList.getValue());
		itemstosave.add(tosave);

	}

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

	@FXML
	void editname(ActionEvent event) {
		menutext.setVisible(true);
		menutext.setPromptText("Enter menu name");
		menuchosentext.setText(menutext.getText());
		menu.setName(menutext.getText());

	}

	@FXML
	void SetAnchor(ActionEvent event) {

		ObservableList<Item> ItemselectType, Itemselectcourse;
		ItemselectType = TypeTable.getSelectionModel().getSelectedItems();
		Itemselectcourse = TableCourse.getSelectionModel().getSelectedItems();
//    	Allitems=TypeTable.getItems();
		for (Item display : ItemselectType) {
			NameLabel.setText(display.getName());

			System.out.println(NameLabel);

			NameLabel.setVisible(true);
			descriptiontext.setText(display.getDescription());
			descriptiontext.setVisible(true);
//    		imageshow.setImage(display.getPhoto());
//    		.setVisible(true);.setVisible(true);
		}
		for (Item display : Itemselectcourse) {
			NameLabel.setText(display.getName());

			System.out.println(NameLabel);

			NameLabel.setVisible(true);
			descriptiontext.setText(display.getDescription());
			descriptiontext.setVisible(true);
//    		imageshow.setImage(display.getPhoto());
//    		.setVisible(true);.setVisible(true);
		}

	}

	@FXML
	void savemenu(ActionEvent event) {

		item_in_menu[] it = (item_in_menu[]) itemstosave.toArray();
		menu.setItems(it);
//    	menu.setRestaurantID(this.resurantID);  !!!!!!  MY Restaurant ID from Nevagitinon controller!!!!!!!!
		/// ****here i will save menu in DB *****///
		sendtoserver();
	}

	void sendtoserver() {

		//// change to menu send
		Request request = new Request();
		request.setPath("/restaurants/items");
		request.setMethod("POST");
		Gson gson = new Gson();

		request.setBody(gson.toJson(menu));
		ClientUI.chat.accept(gson.toJson(request));

		if (ChatClient.serverAns.getCode() != 200 || ChatClient.serverAns.getCode() != 201) {

			// Warning
		}

		// Category []
		// categories=gson.fromJson((String)ChatClient.serverAns.getBody(),Category[].class
		// );

	}

//	void subcategoryset() {
//
//		Request request = new Request();
//		request.setPath("/restaurants/items/categories");
//		request.setMethod("GET");
//		Gson gson = new Gson();
//		JsonElement body = gson.toJsonTree(new Object());
//
//		/// if i want to send to DB
//
//		body.getAsJsonObject().addProperty("restaurantID", 2);// String 2 is the current restaurant ID (!!!)
//
//		request.setBody(gson.toJson(body));
//		ClientController.accept(gson.toJson(request));
//		Category[] categories = gson.fromJson((String) ChatClient.serverAns.getBody(), Category[].class);
//		List<String> subcategory = null;
//
//		for (int i = 0; i < categories.length; i++) {
//			subcategory.addAll(categories[i].getSubCategory());
//		}
//
//	}

	void categoryset() {
		TypeList.setPromptText("enter");
		CourseList.setPromptText("pick a Course");
		subcombo.setPromptText("Choose Subcategory");

		Request request = new Request();
		request.setPath("/restaurants/items/categories");
		request.setMethod("GET");
		Gson gson = new Gson();
		JsonElement body = gson.toJsonTree(new Object());

		/// if i want to send to DB

		body.getAsJsonObject().addProperty("restaurantNum", 10);// String 2 is the current restaurant ID (!!!)

		request.setBody(gson.toJson(body));
		System.out.println(gson.toJson(body));
		ClientUI.chat.accept(gson.toJson(request));
		Category[] categories = gson.fromJson((String) ChatClient.serverAns.getBody(), Category[].class);

		for (int i = 0; i < categories.length; i++) {

			ItemList1.add(categories[i].getCategory());

			subcombolist1.addAll(categories[i].getSubCategory());

		}

		// System.out.println(subcategory);
		TypeList.setItems(ItemList1);
		subcombo.setItems(subcombolist1);
		CourseList.setItems(subcombolist1);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getmenus();
		// in here only insert to TypeList and CourseList by the category from DB //
		for(Menu menu : resturantmenu) {
		
		Menulist.add(menu.getName());
		}
		
		
		menulist.setItems(Menulist);
//		menulist.setPromptText("choose menu");
//		Menulist.add("night menu "); /// here will enter all menu of restaurant from DB
//		Menulist.add("Lanch");
//		Menulist.add("Specials");
//		menulist.setItems(Menulist);

//		TypeList.setPromptText("enter");
//		ItemList.add(new Item("Cordi", null, 0, 0, "Kobana", 0, "not a drink", null, null, null, 0));
//		ItemList.add(new Item("Askanzi", null, 0, 0, "Poyka", 0, "dont put cola", null, null, null, 0));
//		ItemList1.add("Cordi");
//		ItemList1.add("Askanzi");
//		TypeList.setItems(ItemList1);

//		subcombo.setPromptText("Choose Subcategory");
//		subcombolist1.add("Pasta");
//		subcombolist1.add("Salt");
//		subcombo.setItems(subcombolist1);

		categoryset(); /// here will be the left combo set all the above not needed

		// All of Table stuff will be in on mouse action of the two picks in Combobox
		// ////

		// in add and remove we will change the table /////////

		NameTableColum1.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
		TypeColum1.setCellValueFactory(new PropertyValueFactory<Item, String>("category"));
		TypeTable.setItems(ItemList);

		CourseList.setPromptText("pick a Course");
		Courselist.add("First Course");
		Courselist.add("Main Course");
		CourseList.setItems(Courselist);

		Courselist1.add(new Item("italina", null, 0, 0, "pizza", 0, null, null, null, null, 0));

		NameTableColum2.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
		TypeColum2.setCellValueFactory(new PropertyValueFactory<Item, String>("category"));
		TableCourse.setItems(Courselist1);

	}

}
