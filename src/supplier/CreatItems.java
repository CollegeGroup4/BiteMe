package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import logic.Item;
import logic.Options;
import logic.item_in_menu;

public class CreatItems implements Initializable {

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
	private TableColumn<IngrediantRow, Button> addincol;
	@FXML
	private TableView<IngrediantRow> ingrediantsTable;
	@FXML
	private TableColumn<IngrediantRow, Button> removeincol;
	@FXML
	private JFXComboBox<String> categorycombo;

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
	private Button back;

	@FXML
	private HBox Nav;

	@FXML
	private Button LogOut;

	// in next values put item_in_menu values you get

	item_in_menu iteminmenu;

	private ArrayList<String> ingrediantslist = new ArrayList<String>();
	private ArrayList<Options> optionslist = new ArrayList<Options>();

    private Options [] optionalarry;
	private ObservableList<String> categorylist = FXCollections.observableArrayList();
	private ObservableList<String> subcategorylist = FXCollections.observableArrayList();
	IngrediantRow inrow = new IngrediantRow(ingrediantsText, ingrediantsadd, removeinbutton);
	OptionRow oprow = new OptionRow(categorytext, specifytext);
	Item itemtosave = new Item(null, null, 0, 0, null, 0, null, null, null, null, 0);

	ObservableList<IngrediantRow> ingrediantRow = FXCollections.observableArrayList();
	ObservableList<OptionRow> optionrow = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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
		ingrediantsadd.setPrefWidth(addincol.getPrefWidth());
		removeinbutton.setPrefWidth(removeincol.getPrefWidth());
		ingrediantsText.setPrefHeight(ingrediantsadd.getPrefHeight());
		ingrediantRow.add(new IngrediantRow(ingrediantsText, ingrediantsadd, removeinbutton));

		namecol.setCellValueFactory(new PropertyValueFactory<IngrediantRow, TextField>("text"));
		addincol.setCellValueFactory(new PropertyValueFactory<IngrediantRow, Button>("addb"));
		removeincol.setCellValueFactory(new PropertyValueFactory<IngrediantRow, Button>("remb"));
		ingrediantsTable.setItems(ingrediantRow);

//		***** Option part*******		
		categorytext = new TextField();
		specifytext = new TextField();
		categorytext.setPrefWidth(CategoryCol.getPrefWidth());
		specifytext.setPrefWidth(spcifyCOL.getPrefWidth());
		optionrow.add(new OptionRow(categorytext, specifytext));
		CategoryCol.setCellValueFactory(new PropertyValueFactory<OptionRow, TextField>("catetext"));
		spcifyCOL.setCellValueFactory(new PropertyValueFactory<OptionRow, TextField>("spcitext2"));

		OptionTable.setItems(optionrow);

	}

	public class OptionRow {
		TextField catetext;
		TextField spcitext2;

		public OptionRow(TextField catetext, TextField spcitext2) {

			this.catetext = catetext;
			this.spcitext2 = spcitext2;
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

	}

	public class IngrediantRow {

		TextField text;
		Button addb;
		Button remb;

		public IngrediantRow(TextField text, Button addb, Button remb) {
			this.addb = addb;
			this.remb = remb;
			this.text = text;
		}

		public TextField getText() {
			return text;
		}

		public void setText(TextField text) {
			this.text = text;
		}

		public Button getAddb() {
			return addb;
		}

		public void setAddb(Button addb) {
			this.addb = addb;
		}

		public Button getRemb() {
			return remb;
		}

		public void setRemb(Button remb) {
			this.remb = remb;
		}
	}

	@FXML

	void CreatNewItem(ActionEvent creatitem) {

		// until i now how to send to DB it will be in item i send

		itemtosave.setName(dishtext.getText());
		itemtosave.setDescription(DescriptionText.getText());
		itemtosave.setPrice(Float.valueOf(PriceText.getText()));

		String ingrediant = null;

		for (int i = 0; i < ingrediantslist.size(); i++) {
			ingrediant = ingrediantslist.get(i) + ",";
		}
		itemtosave.setIngrediants(ingrediant);
		
		optionalarry=(Options[]) optionslist.toArray();
		
		itemtosave.setOptions(optionalarry);

		//and send to database//

	}

	@FXML
	void RemoveIngrediants(ActionEvent action) {
		// IngrediantRow rowtoremove=new IngrediantRow();

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
				row.addb.setDisable(true);
			}
		}

		TextField newingr = new TextField();
		newingr.setPrefWidth(ingrediantsText.getPrefWidth());
		newingr.setPrefHeight(ingrediantsText.getPrefHeight());

		Button plus = new Button("+");
		plus.setPrefWidth(ingrediantsadd.getPrefWidth());
		plus.setPrefHeight(ingrediantsadd.getPrefHeight());
		plus.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> AddIngrediants(action));
		Button minus = new Button("-");
		;
		minus.setPrefWidth(removeinbutton.getPrefWidth());
		minus.setPrefHeight(removeinbutton.getPrefHeight());
		minus.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> RemoveIngrediants(action));
//    	HBox newhbox=new HBox();
//    	newhbox.setPrefWidth(ingrediantsHbox.getPrefWidth());
//    	newhbox.setPrefHeight(ingrediantsHbox.getPrefHeight());
//    	newhbox.getChildren().addAll(newingr,plus,minus);
		IngrediantRow row = new IngrediantRow(newingr, plus, minus);
//    	Ingrenianttoadd.add(row);
		ingrediantsTable.getItems().add(row);
//    	ingrediantsTable.setItems(Ingrenianttoadd);

//    	ingrediantsVbox.getChildren().add(newhbox);
//    	ingrediantsadd.setVisible(true);
//    	removeinbutton.setVisible(true);
//    	ingrediantsText.setVisible(true);

	}

	@FXML
	void AddOptional(ActionEvent action) {

		// to add item id in the new option

		Options option = new Options(categorytext.getText(), specifytext.getText(), 8, 0, false);
		optionslist.add(option);

		ObservableList<OptionRow> allrows = OptionTable.getItems();
		for (OptionRow row : allrows) {
			if (row.catetext.getText() != "" && row.spcitext2.getText() != "") {
				row.catetext.setDisable(true);
				row.spcitext2.setDisable(true);
			}
		}

		TextField newcat = new TextField();
		newcat.setPrefWidth(categorytext.getPrefWidth());
		newcat.setPrefHeight(categorytext.getPrefHeight());

		TextField newspec = new TextField();
		newspec.setPrefWidth(specifytext.getPrefWidth());
		newspec.setPrefHeight(specifytext.getPrefHeight());

		OptionRow row = new OptionRow(newcat, newspec);

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
			itemtosave.setCategory(AddDiff.getText());
		} else {
			itemtosave.setCategory(categorycombo.getValue());

		}
	}

	@FXML
	void subcategorypress(ActionEvent event) {
		System.out.println(subcombo.getValue());
		if (subcombo.getValue().equals("Other")) {
			AddDiff2.setVisible(true);
			itemtosave.setCategory(AddDiff2.getText());
		} else {
			itemtosave.setCategory(subcombo.getValue());

		}
	}

	@FXML
	void LogOut(ActionEvent action) {
	}

	@FXML
	void Home(ActionEvent action) {
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
			// CreatAndEditMenuController creatitemcontrol=loader.getController();

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open Supplier Page");
			e.printStackTrace();

		}
	}
}
