package supplier;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HRPageController {

	    @FXML
	    private HBox Nav;

	    @FXML
	    private Button LogOut;

	    @FXML
	    private Button approve;

	    @FXML
	    private Button employeebutton;

	    @FXML
	    void Approveclick(ActionEvent event) {
	    	System.out.println("Approve");
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			Pane root;
			try {
				root = loader.load(getClass().getResource("/supplier/BusinessApprove.fxml").openStream());
				//CreatAndEditMenuController editcontrol=loader.getController();

				Scene scene = new Scene(root);

				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				System.out.println("Erorr: Could not open eBusinessApprove page");
				e.printStackTrace();
				
			}
	    }

	    @FXML
	    void LogOut(ActionEvent event) {

	    }

	    @FXML
	    void employeeclick(ActionEvent event) {
	    	System.out.println("EmployeeRegister");
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			Pane root;
			try {
				root = loader.load(getClass().getResource("/supplier/EmployeeRegister.fxml").openStream());
				//CreatAndEditMenuController editcontrol=loader.getController();

				Scene scene = new Scene(root);

				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				System.out.println("Erorr: Could not open EmployeeRegister page");
				e.printStackTrace();
				
			}
	    }

	
	  public void start(Stage primaryStage) throws Exception {
	    	
//	    	req.setPath("/resturants/menus");
//	    	req.setMethod("GET");
//	    	
//	    	
	//    
//	    	JsonElement j = gson.toJsonTree(req);
//	    	String p = gson.toJson(j);
//	    	client.accept(p); // in here will be DB ask for restaurant id
//	    	
//	    	
//	    	

//			 this.resid=gson.fromJson(h.getAsJsonObject().get("restaurantName"), String.class);// you can do it by h and class
			
			
			Parent root = FXMLLoader.load(getClass().getResource("/supplier/H.R.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("H.R Page");
			primaryStage.setScene(scene);

			primaryStage.show();
		
	    }
}
