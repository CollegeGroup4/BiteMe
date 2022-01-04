package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.tools.javac.jvm.Items;

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
		FromJson();
		
		//insert the items from json to table ****
		
//	    ItemList.add(new Item("Cordi","sdd", 0, 3, "Kobana", 4, null, "dan", null, null, 0));
//    	ItemList.add(new Item("Askanzi", "s", 2, 0, "Poyka", 7, "taim", null, null, null, 0));
//    	ItemList.add(new Item("Irqy", null, 7, 4,"Sabic", 2, null, "pita", null, null, 1));
		
    	
    	name.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
    	category.setCellValueFactory(new PropertyValueFactory<Item, String>("Category"));
    	Ingrediants.setCellValueFactory(new PropertyValueFactory<Item, String>("Ingrediants"));
    	Options.setCellValueFactory(new PropertyValueFactory<Item, Options[]>("Options"));
    	subcategory.setCellValueFactory(new PropertyValueFactory<Item, String>("Subcategory"));
    	Description.setCellValueFactory(new PropertyValueFactory<Item, String>("Description"));
    	Price.setCellValueFactory(new PropertyValueFactory<Item, Double>("Price"));
    	resturantid.setCellValueFactory(new PropertyValueFactory<Item, Integer>("restaurantID"));
    	ItemID.setCellValueFactory(new PropertyValueFactory<Item,Integer>("ItemID"));
    	Table.setItems(ItemList);
	

	
	}
	void FromJson() {
		
		///ask moshe on items of restaurant !!
		/// change to get items from this restaurant ***////
		
		Request request=new Request();
		request.setPath("/restaurants/getItemsByMenu");
		request.setMethod("GET");
		Gson gson=new Gson();
		JsonElement body=gson.toJsonTree(new Object());
		
		body.getAsJsonObject().addProperty("restaurantID",2 );// String 2 is the current restaurant ID (!!!)
		
		
		request.setBody(gson.toJson(body));
		ClientUI.chat.accept(gson.toJson(request));
		Item [] items=gson.fromJson((String)ChatClient.serverAns.getBody(),Item[].class);
		
		for(int i=0;i<items.length;i++) {
			ItemList.add(items[i]);
		}
		
		
	}

	
	
}