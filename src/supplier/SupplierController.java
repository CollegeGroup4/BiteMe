package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import Server.Response;
import client.ChatClient;
import client.ClientController;
import client.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Resturant;
import logic.User;

public class SupplierController {

	public static Resturant supplier;
	@FXML
    private HBox Nav;

    @FXML
    private Button LogOut;

    @FXML
    private Button creatandeditmenu;

    @FXML
    private Button creatitem;

    @FXML
    private Button allitems;

    @FXML
    private Button approveorder;

    @FXML
    private Button orderbutton;

    @FXML
    private Button UpdateButton;
    

   

	    Gson gson=new Gson();
	   // JsonElement j=gson.toJsonTree(Nav);
	    ClientController client;
	    Request req=new Request();
	    
	    
	    
	    
	    // ****************************jeson use by user name send to DB -
	    
	    
	    
	    void sentToJson(String username, String password) {
	    	
//			 Resturant user = new Resturant(0, true, 0, username, password, null);
//			Request request = new Request();
//			request.setPath("/users/login");
//			request.setMethod("GET");
//			
//			Gson gson = new Gson();
//			JsonElement jsonUser = gson.toJsonTree(new Object());
//			jsonUser.getAsJsonObject().addProperty("MenuName",spcefic menu);	//******get by menu name**********
//			jsonUser.getAsJsonObject().addProperty("ResturantID",spcefic Number);	//******get by resturantID*****
//	    	String jsonFile = gson.toJson(jsonUser);
//	    	request.setBody(jsonFile);
//	    	System.out.println("jsonFile : "+jsonFile);
//	    	client.accept(jsonFile); // in here will be DB ask for restaurant id
//			Response k = gson.fromJson(ChatClient.serverAns, Response.class);
//			if(k.getCode()!=200 && k.getCode()!=201) {
//				//error massage
//			}
//			
//			JsonElement h = gson.toJsonTree(k.getBody());
			
		}
	    
    public void start(Stage primaryStage) throws Exception {
    	
//    	req.setPath("/resturants/menus");
//    	req.setMethod("GET");
//    	
//    	
//    
//    	JsonElement j = gson.toJsonTree(req);
//    	String p = gson.toJson(j);
//    	client.accept(p); // in here will be DB ask for restaurant id
//    	
//    	
//    	

//		 this.resid=gson.fromJson(h.getAsJsonObject().get("restaurantName"), String.class);// you can do it by h and class
		
		
		Parent root = FXMLLoader.load(getClass().getResource("/supplier/SupplierPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Supplier");
		primaryStage.setScene(scene);

		primaryStage.show();
	
    }
	
	
    @FXML
    void LogOut(ActionEvent event) {

    }
    @FXML
    void AllItems(ActionEvent event) {
    	//AllItemController.resid=this.resid;
    	System.out.println("All Items");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/ALL_Items.fxml").openStream());
			//CreatAndEditMenuController editcontrol=loader.getController();

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open All Items page");
			e.printStackTrace();
			
		}
    }
    @FXML
    void Order(ActionEvent event) {
    	////in requirement write not to be implements
    }
    
    
    
   
    @FXML
    void CreatMenu(ActionEvent event) {
    	System.out.println("Create and edit");
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		Pane root;
		try {
			root = loader.load(getClass().getResource("/supplier/CreatAndEditMenu.fxml").openStream());
			//CreatAndEditMenuController editcontrol=loader.getController();

			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println("Erorr: Could not open edit menu  page");
			e.printStackTrace();
			
		}
    }
	
    
    	

  
	@FXML
    void CreatItem(ActionEvent event) {
     	//System.out.println("Create Item");
		FXMLLoader loader = new FXMLLoader();
  		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
  		Stage primaryStage = new Stage();
     	
    	
    		try {
    			AnchorPane root;
        		
    			
    			root =  loader.load(getClass().getResource("/supplier/CreatItem.fxml").openStream());
    			//.start(primaryStage);
//    			CreatItems creat=loader.getController();
    			
  			    Scene scene = new Scene(root);

    			primaryStage.setTitle("creat item");
    			
    			primaryStage.setScene(scene);
    			primaryStage.show();
    		} catch (Exception e) {
    			System.out.println("Erorr: Could not open CreatItem  page");
    			e.printStackTrace();
    			
    		}
        }
	   @FXML
	    void UpdateMenu(ActionEvent event) {
		   System.out.println("Uapdate");
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			Stage primaryStage = new Stage();
			Pane root;
			try {
				root = loader.load(getClass().getResource("/supplier/UpdateMenuPage.fxml").openStream());
			
				Scene scene = new Scene(root);

				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				System.out.println("Erorr: Could not open update  page");
				e.printStackTrace();
				
			}
	    }

    

    @FXML
    void ApproveOrder(ActionEvent event) {
    	
    }
    @FXML
    void Back(ActionEvent event) {

    }

	
	
	
}
