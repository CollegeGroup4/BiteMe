package mywork;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Item;
import logic.Menu;
import logic.Options;
import logic.item_in_menu;
import temporaryDatabase.myOwnDatabase;

public class ChooseADishController implements Initializable, EventHandler<ActionEvent> {

	public static ChooseADishController chooseADishController = null;
	public static Menu menuSelected;
	//public static ArrayList<item_in_menu> items_in_menuArr;
	public static ArrayList<Menu> menusArr;
	public static ArrayList<Item> itemsArr;
	

	@FXML
	private HBox Nav;

	@FXML
	private Label welcomeLabel;

	@FXML
	private Button btnLogout;

	@FXML
	private Hyperlink BtnHome;

	@FXML
	private ImageView imageBiteme1;

	@FXML
	private Label displayRestName;

	@FXML
	private VBox Menus;
	
    @FXML
    private ScrollPane spMenus;
    
	public ScrollPane getSpMenus() {
		return spMenus;
	}
    
    public VBox getMenus() {
		return Menus;
	}

	@FXML
    private Label lblMenus;

	public Label getLblMenus() {
		return lblMenus;
	}

	@FXML
	private BorderPane paneForItems;
	
    @FXML
    private BorderPane paneForSummary;

	public BorderPane getPaneForSummary() {
		return paneForSummary;
	}

	public void setPaneForSummary(BorderPane paneForSummary) {
		this.paneForSummary = paneForSummary;
	}

	public BorderPane getPaneForItems() {
		return paneForItems;
	}

	public void setPaneForItems(BorderPane paneForItems) {
		this.paneForItems = paneForItems;
	}

	@FXML
	private BorderPane paneForSelections;

	@FXML
	private BorderPane paneForCourses;

	public BorderPane getPaneForCourses() {
		return paneForCourses;
	}

	public void setPaneForCourses(BorderPane paneForCourses) {
		this.paneForCourses = paneForCourses;
	}

	public BorderPane getPaneForSelections() {
		return paneForSelections;
	}

	public void setPaneForSelections(BorderPane paneForSelections) {
		this.paneForSelections = paneForSelections;
	}

	@FXML
	private HBox Nav1;
	@FXML
	private Button btnBack;
	
    @FXML
    private Button btnNext;

	@FXML
	void goBack(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		ChooseRestaurantController aFrame = new ChooseRestaurantController();
		try {
			aFrame.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setName() {
		welcomeLabel.setText("Welcome, " + CustomerPageController.user.getName());

	}

	public void setRestName() {
		displayRestName.setText("You chose to dine at: " + ChooseRestaurantController.restaurantSelected.getName());

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Button> b = new ArrayList<Button>();
		//items_in_menuArr = new ArrayList<item_in_menu>();
		itemsArr = new ArrayList<Item>();
		menusArr = new ArrayList<Menu>();
		setTempDatabase();
		for (int i = 0; i < menusArr.size(); i++) {
			Button temp = new Button(menusArr.get(i).getName());
			temp.setOnAction(this);
			b.add(temp);

		}
		Menus.getChildren().addAll(b);
		paneForCourses.setCenter(spMenus);
	}



	@Override
	public void handle(ActionEvent event) {
		String stringFromEvent = event.getSource().toString();
		stringFromEvent = stringFromEvent.split("'", 2)[1];
		stringFromEvent = stringFromEvent.substring(0, stringFromEvent.length() - 1);
		for(int i=0;i<menusArr.size();i++) {
			if(menusArr.get(i).getName().equals(stringFromEvent))
				menuSelected=menusArr.get(i);
		}


		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Courses.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Menus.setVisible(false);
		paneForCourses.setTop(new Label("Choose Course"));
		paneForCourses.setCenter(root);
	}

	private void setTempDatabase() {

		Options op0[] = { new Options("Size", "Regular", 0,0),new Options("Size", "Big", 15,0),
				new Options("Select", "Not tomatoes", 0,0),new Options("Select", "No onions", 0,0) };
		
		Options op1[] = { new Options("Cook Size", "Medium", 0,1),new Options("Cook Size", "Medium Well", 0,1),
				new Options("Cook Size", "Well Done", 0,1)};
		
		Item item0 = new Item("Italiano", null, 0, 0, "Regular Pizza", 47,
				"High-quality mozzarella pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
				null, 5);
		
		Item item1 = new Item("Steaks", null, 1, 0, "Entrecote", 85,
				"A classic 300 gram slice of entrecote", null, op1,
				null, 3);
		
		itemsArr.add(item0);
		itemsArr.add(item1);

		item_in_menu item_in_menu0 = new item_in_menu(0, 0, "Night", "Second Course");
		item_in_menu item_in_menu1 = new item_in_menu(1, 0, "Day", "Second Course");
		
		//items_in_menuArr.add(item_in_menu0);
		//items_in_menuArr.add(item_in_menu1);


		Menu temp0 = new Menu("Day", 0,new item_in_menu[]{item_in_menu0});
		Menu temp1 = new Menu("Night", 0,new item_in_menu[]{item_in_menu1});
		menusArr = new ArrayList<Menu>();
		menusArr.add(temp0);
		menusArr.add(temp1);
	}

    @FXML
    void next(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
		DeliveryAndTimeController aFrame = new DeliveryAndTimeController();
		try {
			aFrame.start(new Stage());
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/mywork/ChooseADish.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Prepare A Dish Page");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
