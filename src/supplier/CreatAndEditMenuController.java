package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
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
import logic.Item;
import logic.Menu;
import logic.Order;

public class CreatAndEditMenuController implements Initializable
{

		   SupplierController sup=new SupplierController();

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
		    private Button LogOut;

		    @FXML
		    private Button back;
			private ObservableList<Item> ItemList = FXCollections.observableArrayList();
			private ObservableList<String> ItemList1 = FXCollections.observableArrayList();
			private ObservableList<String> Courselist = FXCollections.observableArrayList();
			private ObservableList<Item> Courselist1 = FXCollections.observableArrayList();
			
			   ///// **** in menu and items put resturant staff in pickmenu function****
		    
		     private Menu menu;
			  Item[] items= {new Item("Askanzi", null, 0, 0, "Poyka", 0, null, null, null, null, 0)} ;
	  @FXML
	  	void editname(ActionEvent event) {
		  	menutext.setPromptText("Enter menu name");
		  	menuchosentext.setText(menutext.getText());
			menu.setName(menutext.getText());
			    }
			
		@FXML 
		void TypeList1(ActionEvent action) {
	    	
	    	
	    	
	   
		    }
		  @FXML
		    void coursefromiteminmenu(MouseEvent event) {
		    	
		    	/// enter values to TableCourse from course string in item_in_menu (DB) /////
		    	
		    	
		    	
		    	
		    }
		@Override
		public void initialize(URL location, ResourceBundle resources) {
																							
			menutext.setPromptText("Enter menu name");
			// in here  only insert to  TypeList and CourseList by the category from DB // 
			
			
			TypeList.setPromptText("enter");
	    	ItemList.add(new Item("Cordi", null, 0, 0, "Kobana", 0, "taym moad", null, null, null, 0));
	    	ItemList.add(new Item("Askanzi", null, 0, 0, "Poyka", 0, "taym pachut", null, null, null, 0));
	    	ItemList1.add("Cordi");
	    	ItemList1.add("Askanzi");
	    	TypeList.setItems(ItemList1);
	    	
	    	// All of Table stuff will be in  on mouse action of the two picks in Combobox ////
	    	
	    	// in add and remove we will change the table /////////
	    	
	    	
	    	NameTableColum1.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
	    	TypeColum1.setCellValueFactory(new PropertyValueFactory<Item, String>("category"));
	    	TypeTable.setItems(ItemList);
	    	
	    	CourseList.setPromptText("pick a Course");
	    	Courselist.add("First Course");
	    	Courselist.add("Main Course");
	    	CourseList.setItems(Courselist);
	    	
	    	Courselist1.add(new Item("italina", null, 0, 0, "pizza", 0, "em ptryot", null, null, null, 0));
	    	
	    	NameTableColum2.setCellValueFactory(new PropertyValueFactory<Item, String>("Name"));
	    	TypeColum2.setCellValueFactory(new PropertyValueFactory<Item, String>("category"));
	    	TableCourse.setItems(Courselist1);
	    	
	   

		}
		    
	    @FXML 
	    void addtomenu(ActionEvent action) {
	    	ObservableList<Item>  Itemtoadd;
	    	
	    	Itemtoadd=TypeTable.getSelectionModel().getSelectedItems();
	    	
	    	for(Item  additem :Itemtoadd) {
	    		 Courselist1.add(additem);
	    		 ItemList.remove(additem) ;///////// not needed 
	    		 Itemadd(additem);
	    	}
	    	
	       
	    }
	    
	    void Itemadd(Item itemadd) {
	    	
	    	items[items.length-1]=itemadd;
	    	
	    }
	    @FXML 
	    void removefrommenu(ActionEvent action) {
	    	ObservableList<Item> ItemLremoveselect,Allitems;
	    	Allitems=TableCourse.getItems();
	    	
	    	ItemLremoveselect=TableCourse.getSelectionModel().getSelectedItems();
	    	for(Item  allitems :ItemLremoveselect) {
	    		Allitems.remove(allitems);
	    	}
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
				//CreatAndEditMenuController creatitemcontrol=loader.getController();

				Scene scene = new Scene(root);

				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				System.out.println("Erorr: Could not open Supplier Page");
				e.printStackTrace();
				
			}
	    }
	    @FXML 
	    void savemenu(ActionEvent action) {
	    	
	        //	menu.setItems( items);
	    	//// here i will  send to DB /////
	    	
	    	
	    	
	    }
	    @FXML
	    void SetAnchor(ActionEvent event) {
	    	
	    	ObservableList<Item> ItemselectType,Itemselectcourse;
	    	ItemselectType=TypeTable.getSelectionModel().getSelectedItems();
	    	Itemselectcourse=TableCourse.getSelectionModel().getSelectedItems();
//	    	Allitems=TypeTable.getItems();
	    	for(Item display: ItemselectType) {
	    		NameLabel.setText(display.getName());
	    		
	    		System.out.println(NameLabel);
	    		
	    		NameLabel.setVisible(true);
	    		descriptiontext.setText(display.getDescription());
	    		descriptiontext.setVisible(true);
//	    		imageshow.setImage(display.getPhoto());
//	    		.setVisible(true);.setVisible(true);
	    	}
	    	for(Item display: Itemselectcourse) {
	    		NameLabel.setText(display.getName());
	    		
	    		System.out.println(NameLabel);
	    		
	    		NameLabel.setVisible(true);
	    		descriptiontext.setText(display.getDescription());
	    		descriptiontext.setVisible(true);
//	    		imageshow.setImage(display.getPhoto());
//	    		.setVisible(true);.setVisible(true);
	    	}
	    	
	    	
	    	
	    }
	    
	    
	    
	    
	    @FXML 
	    void LogOut(ActionEvent action) {
	    	
	    }


		
	    
	    
}
