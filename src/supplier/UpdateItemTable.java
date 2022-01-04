package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import supplier.SupplierFunction;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

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

	@FXML
	void LogOut(ActionEvent event) {

	}

	@FXML
	void removefrommenu(ActionEvent event) {
		ObservableList<Item> ItemLremoveselect, Allitems;
		Allitems = Table.getItems();
		ItemLremoveselect = Table.getSelectionModel().getSelectedItems();

		for (Item allitems : ItemLremoveselect) {

			Allitems.remove(allitems);
		}

//		removefromDB();

	}

	ObservableList<Item> ItemList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		supplierfunction.initializeNavigation_SidePanel(myHamburger, drawer);
		ArrayList<Options> options = new ArrayList<Options>();
		options.add(new Options("tomato", "remove", 0.0, 0, false));
		Options[] options1 = options.toArray(new Options[0]);

		ItemList.add(new Item("Cordi", "Arabi", 0, 0, "Kobana", 0, "not a drink", "soup,tomato", options1, null, 0));
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

}
