package mywork;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.item_in_menu;
import temporaryDatabase.myOwnDatabase;

public class IngredientsAndQuantityController implements Initializable{

    @FXML
    private Label itemName;

    @FXML
    private Label priceOfItem;

    @FXML
    private TextField quantityOfItem;
    
	public void start(Stage stage) throws Exception {
		Parent root=FXMLLoader.load(getClass().getResource("IngredientsAnd.fxml"));
		Scene scene= new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	itemName.setText(ItemsFromMenuController.itemtSelected.getCourse());
	priceOfItem.setText(String.valueOf(ItemsFromMenuController.itemtSelected.getPrice()));
		
	}

}
