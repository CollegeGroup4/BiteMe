package supplier;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import supplier.SupplierFunction;
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

public class CreatItems implements Initializable {
	@FXML
	private JFXHamburger myHamburger;

	@FXML
	private JFXDrawer drawer;
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
	private JFXComboBox<String> categorycombo;
	@FXML
	private TableColumn<OptionRow, TextField> pricecol;
	@FXML
	private JFXComboBox<String> subcombo;
	@FXML
	private TableView<OptionRow> OptionTable;

	@FXML
	private TableColumn<OptionRow, TextField> CategoryCol;

	@FXML
	private TableColumn<OptionRow, TextField> spcifyCOL;

	@FXML
	private TextField AddDiff2;
	@FXML
	private Button uplodi;

	@FXML
	private Button back;

	@FXML
	private HBox Nav;

	@FXML
	private Button LogOut;
	private SupplierFunction supplierfunction = new SupplierFunction();
	// in next values put item_in_menu values you get
	String photo;
	item_in_menu iteminmenu;

	private ArrayList<String> ingrediantslist = new ArrayList<String>();
	private ArrayList<Options> optionslist = new ArrayList<Options>();

	private Options[] optionalarry = new Options[6];
	private ObservableList<String> categorylist = FXCollections.observableArrayList();
	private ObservableList<String> subcategorylist = FXCollections.observableArrayList();
	IngrediantRow inrow = new IngrediantRow(ingrediantsText);
	OptionRow oprow = new OptionRow(categorytext, specifytext, pricetextop);
	Item itemtosave = new Item(null, null, 0, 0, null, 0, null, null, null, null, 0);

	ObservableList<IngrediantRow> ingrediantRow = FXCollections.observableArrayList();
	ObservableList<OptionRow> optionrow = FXCollections.observableArrayList();

	void sendtoserver() {

		Request request = new Request();
		request.setPath("/restaurants/items");
		request.setMethod("POST");
		Gson gson = new Gson();

		request.setBody(gson.toJson(itemtosave));
		ClientUI.chat.accept(gson.toJson(request));

		if (ChatClient.serverAns.getCode() != 200 && ChatClient.serverAns.getCode() != 201) {

			// Warning
		}

	

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		supplierfunction.initializeNavigation_SidePanel(myHamburger, drawer);
//		**** Category and sub part*****
		categorycombo.setPromptText("pick a category");
		categorylist.add("Brazilian");
		categorylist.add("French");
		categorylist.add("Other");
		categorycombo.setItems(categorylist);

		subcombo.setPromptText("pick a subcategory");
		subcategorylist.add("Pasta");
		subcategorylist.add("Salt");
		subcategorylist.add("Other");
		subcombo.setItems(subcategorylist);

//		***** Ingredients part*******

		ingrediantsadd.setVisible(true);
		removeinbutton.setVisible(true);
		ingrediantsText = new TextField();
		ingrediantsText.setPrefWidth(namecol.getPrefWidth());

		ingrediantsText.setPrefHeight(ingrediantsadd.getPrefHeight());
		ingrediantRow.add(new IngrediantRow(ingrediantsText));

		namecol.setCellValueFactory(new PropertyValueFactory<IngrediantRow, TextField>("text"));

		ingrediantsTable.setItems(ingrediantRow);

//		***** Option part*******		
		categorytext = new TextField();
		specifytext = new TextField();
		categorytext.setPrefWidth(CategoryCol.getPrefWidth());
		specifytext.setPrefWidth(spcifyCOL.getPrefWidth());
		CategoryCol.setCellValueFactory(new PropertyValueFactory<OptionRow, TextField>("catetext"));
		spcifyCOL.setCellValueFactory(new PropertyValueFactory<OptionRow, TextField>("spcitext2"));
		pricecol.setCellValueFactory(new PropertyValueFactory<OptionRow, TextField>("pricetextop"));

		OptionTable.setItems(optionrow);

	}

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

	public class IngrediantRow {

		TextField text;

		public IngrediantRow(TextField text) {

			this.text = text;
		}

		public TextField getText() {
			return text;
		}

	}

	@FXML

	void CreatNewItem(ActionEvent creatitem) {

		
		itemtosave.setRestaurantID(SupplierController.resturant.getId());
		itemtosave.setName(dishtext.getText());
		itemtosave.setDescription(DescriptionText.getText());
		itemtosave.setPrice(Float.valueOf(PriceText.getText()));

		if (subcombo.getValue().equals("Other"))
			itemtosave.setSubcategory(AddDiff2.getText());

		if (categorycombo.getValue().equals("Other"))
			itemtosave.setCategory(AddDiff.getText());
		
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

	@FXML
	void categorypress(ActionEvent event) {
		System.out.println(categorycombo.getValue());

		if (categorycombo.getValue().equals("Other")) {
			AddDiff.setVisible(true);

			System.out.println(AddDiff.getText());
		} else {
			itemtosave.setCategory(categorycombo.getValue());

		}
	}

	@FXML
	void subcategorypress(ActionEvent event) {
		System.out.println(subcombo.getValue());
		if (subcombo.getValue().equals("Other")) {

			AddDiff2.setVisible(true);

			System.out.println(AddDiff2.getText());
		} else {
			itemtosave.setSubcategory(subcombo.getValue());

		}
	}

	@FXML
	void LogOut(ActionEvent action) {
		supplierfunction.logout(action);
	}

	@FXML
	void Home(ActionEvent action) {
		supplierfunction.home(action);
	}

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

	@FXML
	private ListView<String> listView = new ListView<String>();

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

}
