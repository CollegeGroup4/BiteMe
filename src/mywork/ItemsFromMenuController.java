package mywork;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Item;
import logic.item_in_menu;
import temporaryDatabase.myOwnDatabase;

public class ItemsFromMenuController implements Initializable, EventHandler<ActionEvent> {

	public static Item itemSelected;
	public static ArrayList<Item> itemsSelectedArr;
	private ArrayList<Item> itemsInCategory;
	@FXML
	private VBox Items;

	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("ItemsFromMenu.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Button> b = new ArrayList<Button>();
		itemsInCategory = new ArrayList<>();
		getInformation();
		for (int i = 0; i < itemsInCategory.size(); i++) {
			Button temp = new Button(itemsInCategory.get(i).getName());
			temp.setOnAction(this);
			b.add(temp);
		}
		Items.getChildren().addAll(b);
	}

	private void getInformation() {
		for (int i = 0; i < ChooseADishController.menuSelected.getItems().length; i++) {
			if (ChooseADishController.menuSelected.getItems()[i].getCourse().equals(CoursesController.courseSelected)) {
				for (int j = 0; j < ChooseADishController.itemsArr.size(); j++) {
					if (ChooseADishController.menuSelected.getItems()[i].getItemID() == ChooseADishController.itemsArr
							.get(j).getItemID()) {
						if (ChooseADishController.itemsArr.get(j).getCategory()
								.equals(CategoriesController.categorySelected)) {
							itemsInCategory.add(ChooseADishController.itemsArr.get(j));
						}

					}
				}
			}
		}

	}

	@Override
	public void handle(ActionEvent event) {
		String stringFromEvent = event.getSource().toString();
		stringFromEvent = stringFromEvent.split("'", 2)[1];
		stringFromEvent = stringFromEvent.substring(0, stringFromEvent.length() - 1);
		for (int i = 0; i < itemsInCategory.size(); i++) {
			if (itemsInCategory.get(i).getName().equals(stringFromEvent)) {
				// itemSelected = itemsInCourse.get(i);
				itemSelected = new Item(itemsInCategory.get(i).getCategory(), itemsInCategory.get(i).getSubcategory(),
						itemsInCategory.get(i).getItemID(), itemsInCategory.get(i).getRestaurantID(),
						itemsInCategory.get(i).getName(), itemsInCategory.get(i).getPrice(),
						itemsInCategory.get(i).getDescription(), itemsInCategory.get(i).getIngrediants(),
						itemsInCategory.get(i).getOptions(), itemsInCategory.get(i).getPhoto(),
						itemsInCategory.get(i).getAmount());
			}
		}
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Selections.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ChooseADishController.chooseADishController.getPaneForSelections().setCenter(root);
		ChooseADishController.chooseADishController.getItemInfo().setVisible(true);
		ChooseADishController.chooseADishController.getItemLabel().setText(itemSelected.getName());
		ChooseADishController.chooseADishController.getItemImage().setImage(null); //implement later
		ChooseADishController.chooseADishController.getItemDescription().setText(itemSelected.getDescription());

	}
}
