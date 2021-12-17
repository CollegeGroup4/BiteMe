package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import logic.Item;
import logic.Options;

public class AllItemController implements Initializable{
	   
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
    void LogOut(ActionEvent event) {
    	
    }

	
	public void initialize(URL url, ResourceBundle db) {
	    ItemList.add(new Item("Cordi","sdd", 0, 3, "Kobana", 4, null, "dan", null, null, 0));
    	ItemList.add(new Item("Askanzi", "s", 2, 0, "Poyka", 7, "taim", null, null, null, 0));
    	ItemList.add(new Item("Irqy", null, 7, 4,"Sabic", 2, null, "pita", null, null, 1));
		
    	
    	name.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
    	category.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
    	Ingrediants.setCellValueFactory(new PropertyValueFactory<Item, String>("Ingrediants"));
    	Options.setCellValueFactory(new PropertyValueFactory<Item, Options[]>("Options"));
    	subcategory.setCellValueFactory(new PropertyValueFactory<Item, String>("Subcategory"));
    	Description.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
    	Price.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));
    	resturantid.setCellValueFactory(new PropertyValueFactory<Item, Integer>("resturantid"));
    	ItemID.setCellValueFactory(new PropertyValueFactory<Item,Integer>("ItemID"));
    	Table.setItems(ItemList);
	

	
	}

	public void insertItemsToTbl(ObservableList<Item> itemList2) {
	}}
//ItemList.addAll(item,item2);
		
		
		
//		Gson gson = new Gson();
//		menuInRes y = new menuInRes();
		//JsonElement v = gson.toJsonTree(c);
		
//		j.getAsJsonObject().addProperty("path", "/returants/menus");
//		j.getAsJsonObject().addProperty("method", "POST");
//		j.getAsJsonObject().add("body",v);
//		String p = gson.toJson(j);
//		
//		JsonElement j = gson.toJsonTree(new Object());
//		String p = gson.toJson(j);
//		Response k = gson.fromJson(p, Response.class);
//		JsonElement h = gson.toJsonTree(k.getBody());
//		
//		String[] id = new String[2];
//		id[0] = new String("GETALL");
//		id[1] = new String("Item");
//		ClientUI.chat.accept(id);


		
	
