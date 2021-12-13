package mywork;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class IngredientsAndQuantityController implements Initializable{

    @FXML
    private Label itemName;

    @FXML
    private Label priceOfItem;

    @FXML
    private Label quantityOfItem;
    
	public void start(Stage stage) throws Exception {
		Parent root=FXMLLoader.load(getClass().getResource("IngredientsAnd.fxml"));
		Scene scene= new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
