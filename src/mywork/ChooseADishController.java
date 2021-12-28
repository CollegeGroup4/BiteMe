package mywork;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.Item;
import logic.Menu;
import logic.Options;
import logic.Restaurant;
import logic.item_in_menu;
import temporaryDatabase.myOwnDatabase;

public class ChooseADishController implements Initializable, EventHandler<ActionEvent> {

	public static ChooseADishController chooseADishController = null;
	public static Menu menuSelected;
	public static ArrayList<Menu> menusArr;
//	public static ArrayList<Item> itemsArr;
	public HashMap<String, Integer> categories;
	public HashMap<Integer, Item> allItems;
	public static ArrayList<Item> itemsSelectedArr;
	public static Item itemSelected;
	private ArrayList<Item> itemsInCategory;
	public static boolean isOpen = false;

	@FXML
	private Button btnBack;

	@FXML
	private BorderPane paneForSummary;

	@FXML
	private Button btnNext;

	@FXML
	private BorderPane paneForSelections;

	@FXML
	private ScrollPane scrolePaneListItems;

	@FXML
	private VBox ItemContainer;

	@FXML
	private JFXComboBox<String> comboBoxMenu;

	@FXML
	private JFXComboBox<String> comboBoxCourse;

	@FXML
	private JFXComboBox<String> comboBoxCatgory;

	@FXML
	private HBox Nav;

	@FXML
	private Label welcomeLabel;

	@FXML
	private Hyperlink btnHome;

	@FXML
	private Button btnLogout;
	@FXML
	private ImageView shoppingCart;

	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/mywork/ChooseADish.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Prepare A Dish Page");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		requstFromServer();
		if (itemsSelectedArr == null)
			itemsSelectedArr = new ArrayList<>();
		paneForSelections.setVisible(false);
		setComboBoxMenu();// ============ create menu list ============
		comboBoxMenu.setValue(menusArr.get(0).getName());

		setComboBoxCourses();// ============ create course list ============

		setComboBoxCategory();// ============ create category list ============
		setItemsInCategory();

		shoppingCart.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> handeleClickShoppingCart(e));

		try {
			setItemContainer();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public void handeleClickShoppingCart(MouseEvent event) {
		if (isOpen) {
			paneForSummary.setCenter(null);
			paneForSummary.toBack();
		}
		else {
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("Summary.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			paneForSummary.setCenter(root);
			paneForSummary.toFront();
		}
		isOpen = !isOpen;
	}

	private void setItemContainer() throws FileNotFoundException {
		ArrayList<VBox> itemList = new ArrayList<>();
		for (int i = 0; i < menuSelected.getItems().length; i++) {
			item_in_menu itemInMenu = menuSelected.getItems()[i];
			Item item = allItems.get(itemInMenu.getItemID());

			// ---get the img of the dish---//
			InputStream stream;
			stream = new FileInputStream(item.getPhoto());

			Image logo = new Image(stream);
			ImageView logoImage = new ImageView();
			logoImage.setImage(logo);
			logoImage.setFitWidth(80);
			logoImage.setFitHeight(80);

			// ---get the name of the dish---//
			Label name = new Label(item.getName());
			Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 15);
			name.setFont(font);

			// ---get the name of the dish---//
			String price = new DecimalFormat("##.##").format(item.getPrice()) + "$";
			Label priceLabel = new Label(price);
			priceLabel.setFont(font);

			// ---get the description of the dish---//
			JFXTextArea description = new JFXTextArea(item.getDescription());
			description.setEditable(false);
			description.setPrefHeight(50);
			description.setPrefWidth(300);

			VBox name_description = new VBox(5);
			name_description.getChildren().addAll(name, description);

			HBox itemInLine = new HBox(5);
			itemInLine.getChildren().addAll(logoImage, name_description, priceLabel);
			itemInLine.setSpacing(30);

			VBox dishes = new VBox(20);
			dishes.getChildren().add(itemInLine);
			dishes.setPadding(new Insets(10, 0, 50, 10));
			dishes.setCursor(Cursor.HAND);

			final int index = i;

			dishes.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> handeleClick(e, allItems.get(index)));
			itemList.add(dishes);
			ItemContainer.getChildren().clear();
			ItemContainer.getChildren().addAll(itemList);
		}
	}

	private void handeleClick(MouseEvent event, Item item) {

		setVisibleItemContainer(false);
		itemSelected = item;
		setItemsInCategory();
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Selections.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		paneForSelections.setCenter(root);

	}

	private void setComboBoxMenu() {
		String[] manuList = new String[menusArr.size()];
		for (int i = 0; i < menusArr.size(); i++)
			manuList[i] = (menusArr.get(i).getName());
		comboBoxMenu.getItems().setAll(manuList);
//		menuSelected.
	}

	private void setComboBoxCourses() {
		getMenuName();// ===== get the menu name =====
		HashMap<String, Integer> courseList = new HashMap<>();
		for (int i = 0; i < menuSelected.getItems().length; i++) {
			courseList.put(menuSelected.getItems()[i].getCourse(), null);
		}
		comboBoxCourse.getItems().clear();
		for (String course : courseList.keySet()) // enter courses to the comboBox
			comboBoxCourse.getItems().add(course);

	}

	private void getMenuName() {
		for (int i = 0; i < menusArr.size(); i++) {
			if (menusArr.get(i).getName().equals(comboBoxMenu.getValue()))
				menuSelected = menusArr.get(i);
		}
	}

	private void setComboBoxCategory() {
		setCategoriesInformation();
		comboBoxCatgory.getItems().clear();
		for (String category : categories.keySet()) {
			comboBoxCatgory.getItems().add(category);
		}
	}

	private void setCategoriesInformation() {// find all categories for a specific menu
		categories = new HashMap<>();
		item_in_menu[] itemsInMenu = menuSelected.getItems();

		for (int i = 0; i < itemsInMenu.length; i++)
			categories.put(allItems.get(itemsInMenu[i].getItemID()).getCategory(), null);
	}

	private void setItemsInCategory() {
		for (int i = 0; i < menuSelected.getItems().length; i++) {
			if (menuSelected.getItems()[i].getCourse().equals(comboBoxCourse.getValue())) {
				Item it = allItems.get(menuSelected.getItems()[i].getItemID());

				if (it.getCategory().equals(comboBoxCourse.getValue()))
					itemsInCategory.add(it);
			}
		}
	}

	void requstFromServer() {
//		itemsArr = new ArrayList<Item>();
		menusArr = new ArrayList<Menu>();
		allItems = new HashMap<>();
		// TODO - need to get menus and items list
		setTempDatabase();
	}

	@FXML
	void filter(ActionEvent event) {
		setComboBoxMenu();// ============ create menu list ============

		setComboBoxCourses();// ============ create course list ============

		setComboBoxCategory();// ============ create category list ============
		try {
			setItemContainer();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public void setVisibleItemContainer(boolean visible) {
		ItemContainer.setVisible(visible);
		scrolePaneListItems.setVisible(visible);
		paneForSelections.setVisible(!visible);
	}

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

	public void setName() {
		welcomeLabel.setText("Welcome, " + CustomerPageController.user.getName());

	}

	public void setRestName() {
//		displayRestName.setText("You chose to dine at: " + ChooseRestaurantController.restaurantSelected.getName());

	}

	private void designLabel(Label label) {
//		Font font = Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 16);
//		label.setFont(font);
	}

	private void designButton(JFXButton button) {
//		button.setPrefWidth(160);
////		button
//		BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
//		Background background = new Background(background_fill);
//		button.setBackground(background);
//		Font font = Font.font("Arial", FontWeight.BOLD, 15);
//		button.setFont(font);
	}

	@Override
	public void handle(ActionEvent event) {
//		String stringFromEvent = event.getSource().toString();
//		stringFromEvent = stringFromEvent.split("'", 2)[1];
//		stringFromEvent = stringFromEvent.substring(0, stringFromEvent.length() - 1);
//		for (int i = 0; i < menusArr.size(); i++) {
//			if (menusArr.get(i).getName().equals(stringFromEvent))
//				menuSelected = menusArr.get(i);
//		}
//
//		Parent root = null;
//		try {
//			root = FXMLLoader.load(getClass().getResource("Courses.fxml"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		// Menus.setVisible(false);
//		Label chooseCourse = new Label("Choose Course:");
//		designLabel(chooseCourse);
//		paneForCourses.setTop(chooseCourse);
//		paneForCourses.setCenter(root);
	}

	private void setTempDatabase() {

		Options op0[] = { new Options("Size", "Regular", 0, 0), new Options("Size", "Big", 15, 0),
				new Options("Select", "Not tomatoes", 0, 0), new Options("Select", "No onions", 0, 0) };

		Options op1[] = { new Options("Cook Size", "Medium", 0, 1), new Options("Cook Size", "Medium Well", 0, 1),
				new Options("Cook Size", "Well Done", 0, 1) };

		Item item0 = new Item("Italiano", null, 0, 0, "Regular Pizza", 47,
				"High-quality margherita pizza, comes with the addition of tomatoes, olives and onions.", null, op0,
				"C:\\Users\\talch\\OneDrive\\שולחן העבודה\\לימודים\\שיטות הנדסיות לפיתוח מערכות תוכנה\\פרוייקט\\חלק 2\\BiteMe\\src\\images\\margherita-pizza.jpg",
				5);

		Item item1 = new Item("Steaks", null, 1, 0, "Entrecote", 85, "A classic 300 gram slice of entrecote", null, op1,
				"C:\\Users\\talch\\OneDrive\\שולחן העבודה\\לימודים\\שיטות הנדסיות לפיתוח מערכות תוכנה\\פרוייקט\\חלק 2\\BiteMe\\src\\images\\M.png",
				3);
		allItems.put(item0.getItemID(), item0);
		allItems.put(item1.getItemID(), item1);

//		itemsArr.add(item0);
//		itemsArr.add(item1);

		item_in_menu item_in_menu0 = new item_in_menu(0, 0, "Night", "Second Course");
		item_in_menu item_in_menu1 = new item_in_menu(1, 0, "Day", "Second Course");

		// items_in_menuArr.add(item_in_menu0);
		// items_in_menuArr.add(item_in_menu1);

		Menu temp0 = new Menu("Day", 0, new item_in_menu[] { item_in_menu0 });
		Menu temp1 = new Menu("Night", 0, new item_in_menu[] { item_in_menu1 });
		menusArr = new ArrayList<Menu>();
		menusArr.add(temp0);
		menusArr.add(temp1);
	}

//
//	public AnchorPane getItemInfo() {
//		return itemInfo;
//	}
//
//	public void setItemInfo(AnchorPane itemInfo) {
//		this.itemInfo = itemInfo;
//	}
//
//	public Label getItemLabel() {
//		return itemLabel;
//	}
//
//	public ImageView getItemImage() {
//		return itemImage;
//	}
//
//	public void setItemImage(ImageView itemImage) {
//		this.itemImage = itemImage;
//	}
//
//	public JFXTextArea getItemDescription() {
//		return itemDescription;
//	}
//
//	public void setItemDescription(JFXTextArea itemDescription) {
//		this.itemDescription = itemDescription;
//	}
//
//	public ScrollPane getSpMenus() {
//		return spMenus;
//	}
//
//	public VBox getMenus() {
//		return Menus;
//	}
//
//	public Label getLblMenus() {
//		return lblMenus;
//	}
//
	public BorderPane getPaneForSummary() {
		return paneForSummary;
	}
	public BorderPane getPaneForSelections() {
		return paneForSelections;
	}
//
//	public void setPaneForSummary(BorderPane paneForSummary) {
//		this.paneForSummary = paneForSummary;
//	}
//
//	public BorderPane getPaneForItems() {
//		return paneForItems;
//	}
//
//	public void setPaneForItems(BorderPane paneForItems) {
//		this.paneForItems = paneForItems;
//	}
//
//	public BorderPane getPaneForCourses() {
//		return paneForCourses;
//	}
//
//	public void setPaneForCourses(BorderPane paneForCourses) {
//		this.paneForCourses = paneForCourses;
//	}
//
//	public BorderPane getPaneForSelections() {
//		return paneForSelections;
//	}
//
//	public void setPaneForSelections(BorderPane paneForSelections) {
//		this.paneForSelections = paneForSelections;
//	}

}
