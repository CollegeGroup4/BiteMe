package mywork;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.item_in_menu;
import temporaryDatabase.myOwnDatabase;

public class IngredientsAndQuantityController implements Initializable, EventHandler<ActionEvent> {

	@FXML
	private Label itemName;

	@FXML
	private Label priceOfItem;

	@FXML
	private TextField quantityOfItem;

	@FXML
	private HBox allIngredients;
	
    @FXML
    private HBox ingredientsSelected;
    
    @FXML
    private Button clearBtn;

    @FXML
    private Button submitBtn;
    
    public static ArrayList<String> ingredientsSelectedArr;

	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("IngredientsAnd.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Button> b = new ArrayList<Button>();
		itemName.setText(ItemsFromMenuController.itemtSelected.getCourse());
		priceOfItem.setText(String.valueOf(ItemsFromMenuController.itemtSelected.getPrice()));
		if (ItemsFromMenuController.itemtSelected.getIngrediants() != null)
			for (int i = 0; i < ItemsFromMenuController.itemtSelected.getIngrediants().size(); i++) {
				Button temp = new Button(ItemsFromMenuController.itemtSelected.getIngrediants().get(i));
				temp.setOnAction(this);
				b.add(temp);
			}
		allIngredients.getChildren().addAll(b);
	}

	@Override
	public void handle(ActionEvent event) {
		String stringFromEvent = buttonRecognize(event);
		if(ingredientsSelectedArr==null)
			ingredientsSelectedArr=new ArrayList<String>();
		ingredientsSelectedArr.add(stringFromEvent);
		ingredientsSelected.getChildren().add(new Button(stringFromEvent));
		//System.out.println(ingredientsSelectedArr.toString());

	}

	private String buttonRecognize(ActionEvent event) {
		String stringFromEvent = event.getSource().toString();
		stringFromEvent=stringFromEvent.split("'",2)[1];
		stringFromEvent = stringFromEvent.substring(0, stringFromEvent.length() - 1);
		return stringFromEvent;
	}
	
    @FXML
    void clear(ActionEvent event) {
    	ingredientsSelectedArr=null;
    	ingredientsSelected.getChildren().clear();

    }

    @FXML
    void submit(ActionEvent event) {
    	ItemsFromMenuController.itemtSelected.setIngrediants(ingredientsSelectedArr);
    	ingredientsSelectedArr=null;
    	PrepareADishController.itemsSelectedArr.add(ItemsFromMenuController.itemtSelected);
    	for(int i=0;i<PrepareADishController.itemsSelectedArr.size();i++) {
    		//System.out.println(PrepareADishController.itemsSelectedArr.get(i).getCourse());
    		
    	}

    }

}
