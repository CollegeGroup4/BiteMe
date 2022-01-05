package supplier;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import supplier.SupplierFunction;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.MyPhoto;
import common.Request;
import common.imageUtils;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.Category;
import logic.Item;
import logic.Options;
import logic.item_in_menu;

public class UpdateItem implements Initializable {
	/**
	 * This class made for supplier update item process
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
	private Label dishname;

	@FXML
	private Label price;

	@FXML
	private TextField ingrediantsText;

	@FXML
	private Button ingrediantsadd;

	@FXML
	private Button removeinbutton;

	@FXML
	private TextField categorytext;

	@FXML
	private TextField specifytext;
	@FXML
	private TextField pricetextop;

	@FXML
	private Button Optionaladd;

	@FXML
	private Button OptionalRemove;

	@FXML
	private TextArea DescriptionText;

	@FXML
	private Button creatitem;

	@FXML
	private TextField PriceText;

	@FXML
	private TextField AddDiff;

	@FXML
	private TextField dishtext;
	@FXML
	private TableColumn<IngrediantRow, TextField> namecol;

	@FXML
	private TableView<IngrediantRow> ingrediantsTable;

	@FXML
	private TableColumn<OptionRow, TextField> pricecol;

	@FXML
	private TableView<OptionRow> OptionTable;

	@FXML
	private TableColumn<OptionRow, TextField> CategoryCol;

	@FXML
	private TableColumn<OptionRow, TextField> spcifyCOL;

	@FXML
	private TextField AddDiff2;

	@FXML
	private Button back;

	@FXML
	private HBox Nav;

	@FXML
	private Button LogOut;
	@FXML
	private JFXHamburger myHamburger;
	@FXML
	private Button uploadi;
	@FXML
	private JFXDrawer drawer;
	
	private SupplierFunction supplierfunction = new SupplierFunction();
	item_in_menu iteminmenu;
	private ArrayList<String> ingrediantslist = new ArrayList<String>();
	private ArrayList<Options> optionslist = new ArrayList<Options>();
	IngrediantRow inrow = new IngrediantRow(ingrediantsText);
	OptionRow oprow = new OptionRow(categorytext, specifytext, pricetextop);
	Item itemtosave = new Item(null, null, 0, 0, null, 0, null, null, null, null, 0);
	ObservableList<IngrediantRow> ingrediantRow = FXCollections.observableArrayList();
	ObservableList<OptionRow> optionrow = FXCollections.observableArrayList();
	/**
	 * Method to send the new item in the DB
	 */
	void sendtoserver() {

		Request request = new Request();
		request.setPath("/restaurants/items");
		request.setMethod("PUT");
		Gson gson = new Gson();

		request.setBody(gson.toJson(itemtosave));
		ClientUI.chat.accept(gson.toJson(request));

		if (ChatClient.serverAns.getCode() != 200 && ChatClient.serverAns.getCode() != 201) {

			// Warning
		}

		
	}
	/**
	 * This Method made to initialize all the buttons and table requirements
	 * 
	 * @param location resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		AddDiff.setText(UpdateItemTable.updateItem.getCategory());
		AddDiff2.setText(UpdateItemTable.updateItem.getSubcategory());

		supplierfunction.initializeNavigation_SidePanel(myHamburger, drawer);

		itemtosave.setItemID(UpdateItemTable.updateItem.getItemID());

		itemtosave.setCategory(UpdateItemTable.updateItem.getCategory());
		itemtosave.setSubcategory(UpdateItemTable.updateItem.getSubcategory());

		DescriptionText.setText(UpdateItemTable.updateItem.getDescription());
		dishtext.setText(UpdateItemTable.updateItem.getName());
		PriceText.setText(String.valueOf(UpdateItemTable.updateItem.getName()));

//		***** Ingredients part*******

		ingrediantsadd.setVisible(true);
		removeinbutton.setVisible(true);
		ingrediantsText = new TextField();
		ingrediantsText.setPrefWidth(namecol.getPrefWidth());
		ingrediantsText.setPrefHeight(ingrediantsadd.getPrefHeight());
		String[] ingr = UpdateItemTable.updateItem.getIngrediants().split(",");
		System.out.println(Arrays.toString(ingr));
		for (String a : ingr) {
			TextField ingrd = new TextField();
			ingrd.setText(a.toString());
			ingrediantRow.add(new IngrediantRow(ingrd));

		}
		ingrediantRow.add(new IngrediantRow(ingrediantsText));

		namecol.setCellValueFactory(new PropertyValueFactory<IngrediantRow, TextField>("text"));

		ingrediantsTable.setItems(ingrediantRow);

//		***** Option part*******	
		
		categorytext = new TextField();
		specifytext = new TextField();
		categorytext.setPrefWidth(CategoryCol.getPrefWidth());
		specifytext.setPrefWidth(spcifyCOL.getPrefWidth());

		Options[] op = UpdateItemTable.updateItem.getOptions();

		for (Options o : op) {

			TextField cat = new TextField();
			cat.setText(o.getOption_category());

			TextField spe = new TextField();
			spe.setText(o.getSpecify_option());

			TextField pr = new TextField();
			pr.setText(String.valueOf(o.getPrice()));
			optionrow.add(new OptionRow(cat, spe, pr));

		}

		CategoryCol.setCellValueFactory(new PropertyValueFactory<OptionRow, TextField>("catetext"));
		spcifyCOL.setCellValueFactory(new PropertyValueFactory<OptionRow, TextField>("spcitext2"));
		pricecol.setCellValueFactory(new PropertyValueFactory<OptionRow, TextField>("pricetextop"));

		OptionTable.setItems(optionrow);
		supplierfunction.initializeNavigation_SidePanel(myHamburger, drawer);

	}
	/**
	 * Inner Class to insert rows into Option Table
	 * 
	 *
	 */
	public class OptionRow {
		TextField catetext;
		TextField spcitext2;
		TextField pricetextop;

		public OptionRow(TextField catetext, TextField spcitext2, TextField pricetextop) {

			this.catetext = catetext;
			this.spcitext2 = spcitext2;
			this.pricetextop = pricetextop;
		}

		public TextField getCatetext() {
			return catetext;
		}

		public void setCatetext(TextField catetext) {
			this.catetext = catetext;
		}

		public TextField getSpcitext2() {
			return spcitext2;
		}

		public void setSpcitext2(TextField spcitext2) {
			this.spcitext2 = spcitext2;
		}

		public TextField getPricetextop() {
			return pricetextop;
		}

		public void setPricetextop(TextField pricetextop) {
			this.pricetextop = pricetextop;
		}

	}
	/**
	 * Inner Class to insert rows into Ingredient Table
	 * 
	 *
	 */
	public class IngrediantRow {

		TextField text;

		public IngrediantRow(TextField text) {

			this.text = text;
		}

		public TextField getText() {
			return text;
		}

	}

	
	/**
	 * Set all reaming parameters from the new item and call to save function
	 * 
	 * @param creatitem
	 */
	@FXML
	void CreatNewItem(ActionEvent creatitem) {

		
		itemtosave.setRestaurantID(SupplierController.resturant.getId());
		itemtosave.setName(dishtext.getText());
		itemtosave.setDescription(DescriptionText.getText());
		itemtosave.setPrice(Float.valueOf(PriceText.getText()));

		String ingrediant = null;

		for (int i = 0; i < ingrediantslist.size(); i++) {
			ingrediant = ingrediantslist.get(i) + ",";
		}
		itemtosave.setIngrediants(ingrediant);
		Options[] optionalarry = new Options[optionslist.size()];

		optionslist.toArray(optionalarry);

		itemtosave.setOptions(optionalarry);
		sendtoserver();
		// and send to database//

	}
	/**
	 * remove row from Ingredient table action button
	 * 
	 * @param action
	 */
	@FXML
	void RemoveIngrediants(ActionEvent action) {
	

		ObservableList<IngrediantRow> allrows, selectedrow;
		allrows = ingrediantsTable.getItems();
		selectedrow = ingrediantsTable.getSelectionModel().getSelectedItems();
		System.out.println(selectedrow);
		for (IngrediantRow row : selectedrow) {
			allrows.remove(row);
			ingrediantslist.remove(row.text.getText());
		}
		ingrediantsTable.setItems(allrows);

	}
	/**
	 * add row to Ingredient table action button
	 * 
	 * @param action
	 */
	@FXML
	void AddIngrediants(ActionEvent action) {

		ingrediantslist.add(ingrediantsText.getText());

		ObservableList<IngrediantRow> allrows = ingrediantsTable.getItems();
		for (IngrediantRow row : allrows) {
			if (row.text.getText() != "") {
				row.text.setDisable(true);

			}
		}

		TextField newingr = new TextField();
		newingr.setPrefWidth(ingrediantsText.getPrefWidth());
		newingr.setPrefHeight(ingrediantsText.getPrefHeight());
		IngrediantRow row = new IngrediantRow(newingr);
		ingrediantsTable.getItems().add(row);

	}
	/**
	 * add row to Option table action button
	 * 
	 * @param action
	 */
	@FXML
	void AddOptional(ActionEvent action) {

		// to add item id in the new option

		ObservableList<OptionRow> allrows = OptionTable.getItems();
		for (OptionRow row : allrows) {
			if (row.catetext.getText() != "" && row.spcitext2.getText() != "" && row.pricetextop.getText() != "") {
				row.catetext.setDisable(true);
				row.spcitext2.setDisable(true);
				row.pricetextop.setDisable(true);
			}
		}

		TextField newcat = new TextField();
		newcat.setPrefWidth(categorytext.getPrefWidth());
		newcat.setPrefHeight(categorytext.getPrefHeight());

		TextField newspec = new TextField();
		newspec.setPrefWidth(specifytext.getPrefWidth());
		newspec.setPrefHeight(specifytext.getPrefHeight());

		TextField newprice = new TextField();
		newprice.setPrefWidth(specifytext.getPrefWidth());
		newprice.setPrefHeight(specifytext.getPrefHeight());

		OptionRow row = new OptionRow(newcat, newspec, newprice);

		OptionTable.getItems().add(row);

	}
	/**
	 * remove row from Option table action button
	 * 
	 * @param action
	 */
	@FXML
	void RemoveOptional(ActionEvent action) {
		ObservableList<OptionRow> allrows, selectedrow;
		allrows = OptionTable.getItems();
		selectedrow = OptionTable.getSelectionModel().getSelectedItems();
		System.out.println(selectedrow);
		for (OptionRow row : selectedrow) {
			allrows.remove(row);
			optionslist.remove(row);
		}
		OptionTable.setItems(allrows);

	}
	/**
	 * This Method made to logout from system
	 * 
	 * @param action
	 */
	@FXML
	void LogOut(ActionEvent action) {
		supplierfunction.logout(action);
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

	private String photo;
	@FXML
	private ListView<String> listView;

	/**
	 * Method to upload an item image
	 * @param event
	 */
	@FXML
	void selectFile(ActionEvent event) {

		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile != null) {
			photo = selectedFile.getName();
			String path = selectedFile.getAbsolutePath();
			itemtosave.setItemImage(new MyPhoto(path));

			imageUtils.sender(itemtosave.getItemImage());

			listView.getItems().add(selectedFile.getName());
			itemtosave.setPhoto(photo);
		} else
			System.out.println("File is not valid");
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
}
