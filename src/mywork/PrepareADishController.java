package mywork;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Menu;
import logic.item_in_menu;
import temporaryDatabase.myOwnDatabase;

public class PrepareADishController implements Initializable, EventHandler<ActionEvent> {
	
	public static PrepareADishController prepareADishController=null;

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
	private BorderPane paneForItems;
	
    @FXML
    private BorderPane paneForSelections;

	@FXML
	private HBox Nav1;

	@FXML
	private ImageView imageBiteme2;

	@FXML
	private ImageView imageFacebook;

	@FXML
	private ImageView imageInstergram;

	@FXML
	private ImageView imageWhatsapp;

	@FXML
	private Button btnBack;

	public static String menutName;

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
		displayRestName.setText("You chose to dine at: " + ChooseRestaurantController.restName);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Button> b = new ArrayList<Button>();
		setTempDatabase();
		for (int i = 0; i < myOwnDatabase.MenusArrayList.size(); i++) {
			Button temp = new Button(myOwnDatabase.MenusArrayList.get(i).getName());
			temp.setOnAction(this);
			b.add(temp);

		}
		Menus.getChildren().addAll(b);
	}

	@Override
	public void handle(ActionEvent event) {
		String stringFromEvent = event.getSource().toString();
		stringFromEvent = stringFromEvent.substring(35, stringFromEvent.length() - 1);
		menutName = stringFromEvent;
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("ItemsFromMenu.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		paneForItems.setCenter(root);
	}

	private void setTempDatabase() { // ***for development only!!******
		item_in_menu item0 = new item_in_menu(0, 0, "Drinks", "Coca-Cola", 7, null, null);
		item_in_menu item1 = new item_in_menu(1, 0, "Drinks", "Fanta", 7, null, null);
		item_in_menu item2 = new item_in_menu(2, 0, "Burgers", "BigMc", 35, "Regular Burger",
				new ArrayList<String>(Arrays.asList("Tomato", "Onion", "Pickels")));

		item_in_menu item3 = new item_in_menu(3, 0, "Salads", "Halumi", 40, "Leaf Salad with Halumi cheese",
				new ArrayList<String>(Arrays.asList("Tomato", "Onion", "Cucumber", "Herbs", "peanuts")));

		item_in_menu item4 = new item_in_menu(4, 0, "First course", "Fries", 18, "Plate of fries with sauces",
				new ArrayList<String>(Arrays.asList("Salt", "Mayonnaise", "Ketchup")));

		Menu temp0 = new Menu("First course", 0, new ArrayList<item_in_menu>(Arrays.asList(item4)));
		Menu temp1 = new Menu("Burgers", 1, new ArrayList<item_in_menu>(Arrays.asList(item2)));
		Menu temp2 = new Menu("Salad", 2, new ArrayList<item_in_menu>(Arrays.asList(item3)));
		Menu temp3 = new Menu("Drinks", 4, new ArrayList<item_in_menu>(Arrays.asList(item0, item1)));
		myOwnDatabase.MenusArrayList = new ArrayList<Menu>();
		myOwnDatabase.MenusArrayList.add(temp0);
		myOwnDatabase.MenusArrayList.add(temp1);
		myOwnDatabase.MenusArrayList.add(temp2);
		myOwnDatabase.MenusArrayList.add(temp3);
	}

}
