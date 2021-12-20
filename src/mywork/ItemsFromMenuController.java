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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Item;
import logic.item_in_menu;
import temporaryDatabase.myOwnDatabase;

public class ItemsFromMenuController implements Initializable, EventHandler<ActionEvent> {

	public static Item itemSelected;
	public static ArrayList<Item> itemsSelectedArr;
	private ArrayList<Item> itemsInCourse;
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
		itemsInCourse = new ArrayList<>();
		getInformation();
		for (int i = 0; i < itemsInCourse.size(); i++) {
			Button temp = new Button(itemsInCourse.get(i).getName());
			temp.setOnAction(this);
			b.add(temp);

		}
		Items.getChildren().addAll(b);
	}

	private void getInformation() {
		for (int i = 0; i < PrepareADishController.itemsArr.size(); i++) {
			if (PrepareADishController.itemsArr.get(i).getSubcategory().equals(CoursesController.courseSelected)) {
				itemsInCourse.add(PrepareADishController.itemsArr.get(i));
			}
		}

	}

	@Override
	public void handle(ActionEvent event) {
		String stringFromEvent = event.getSource().toString();
		stringFromEvent = stringFromEvent.split("'", 2)[1];
		stringFromEvent = stringFromEvent.substring(0, stringFromEvent.length() - 1);
		for (int i = 0; i < itemsInCourse.size(); i++) {
			if (itemsInCourse.get(i).getName().equals(stringFromEvent)) {
				//itemSelected = itemsInCourse.get(i);
				itemSelected=new Item(itemsInCourse.get(i).getCategory(),itemsInCourse.get(i).getSubcategory()
						,itemsInCourse.get(i).getItemID(),itemsInCourse.get(i).getRestaurantID()
						,itemsInCourse.get(i).getName(),itemsInCourse.get(i).getPrice()
						,itemsInCourse.get(i).getDescription(),itemsInCourse.get(i).getIngrediants()
						,itemsInCourse.get(i).getOptions(),itemsInCourse.get(i).getPhoto()
						,itemsInCourse.get(i).getAmount());
			}
		}
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Selections.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrepareADishController.prepareADishController.getPaneForSelections().setCenter(root);
	}

}
