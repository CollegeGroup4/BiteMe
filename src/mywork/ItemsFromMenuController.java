package mywork;

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
import temporaryDatabase.myOwnDatabase;

public class ItemsFromMenuController implements Initializable, EventHandler<ActionEvent> {

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
		int indexOfMenu = -1;
		for (int i = 0; i < myOwnDatabase.MenusArrayList.size(); i++) {// get index of the menu
			if (myOwnDatabase.MenusArrayList.get(i).getName().equals(PrepareADishController.menutName))
				indexOfMenu = i;
		}
		// Finds all the dishes for a particular menu and add button for each of them
		System.out.println(indexOfMenu); // ******** for test only ********
		if (indexOfMenu >= 0)
			for (int i = 0; i < myOwnDatabase.MenusArrayList.get(indexOfMenu).getItems().size(); i++) {
				Button temp = new Button(myOwnDatabase.MenusArrayList.get(indexOfMenu).getItems().get(i).getCourse());
				temp.setOnAction(this);
				b.add(temp);

			}  
		Items.getChildren().addAll(b);
	}

	private void setTempDatabase() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
