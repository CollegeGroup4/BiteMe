package supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import Server.Response;
import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Account;
import logic.BusinessAccount;
import logic.Category;
import logic.Employee;
import logic.Order;

public class BusinessApproveController implements Initializable {
	

		@FXML
		private TableView<Account> tableViewMsg;

		@FXML
		private TableColumn<Account, String> tableColFrom;

		@FXML
		private TableColumn<Account, String> tableColRole;

		@FXML
		private TableColumn<Account, String> tableColStatus;

		@FXML
		private Button btnBackBM;

		@FXML
		private Label labelStatus;

		@FXML
		private Label labelFrom;

		@FXML
		private Label labelUserName;

		@FXML
		private Label labelUserID;

		@FXML
		private TextField newnametext;

		@FXML
		private Button btnDecline;

		@FXML
		private Button btnApprove;

	    @FXML
	    private Label lableSlectedUser;
		@FXML
		private HBox Nav;

		private ObservableList<Account> messagesList = FXCollections.observableArrayList();

		private ObservableList<Account> messagesEditSelect;
		
		private BusinessAccount [] businessaccount;

//		private HashMap<Integer, Messages> allMessages = new HashMap<>();
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			 FromJson();
			 
			 // should put account from businessaccount !!!
			 
			 
			Account account=new Account(2, "Shimi1", null, "Shimi","Tavory", null,"Singer", null, "Wating", false, 0, null, 0);
			Account account2=new Account(22, "Adi44", null, "Adi", "Askanazi", null,"Comedian", null, "Wating", false, 0, null, 0);
			messagesList.addAll(account,account2);
			
			tableColFrom.setCellValueFactory(new PropertyValueFactory<Account, String>("userName"));
			tableColRole.setCellValueFactory(new PropertyValueFactory<Account, String>("role"));
			tableColStatus.setCellValueFactory(new PropertyValueFactory<Account, String>("status"));
			tableViewMsg.setItems(messagesList);

		}

		@FXML
		void ClickOnTable(MouseEvent event) {
			messagesEditSelect = tableViewMsg.getSelectionModel().getSelectedItems();
			lableSlectedUser.setText("Name: " + messagesEditSelect.get(0).getFirstName() + " "
					+ messagesEditSelect.get(0).getLastName() + ",  Role: " + messagesEditSelect.get(0).getRole() + ",  Status: "
					+ messagesEditSelect.get(0).getStatus());
		}

		@FXML
		void approve(ActionEvent event) {
			labelStatus.setText("Approved");
			messagesEditSelect = tableViewMsg.getSelectionModel().getSelectedItems();
			
			BusinessAccount newbus=new BusinessAccount(messagesEditSelect.get(0).getUserID(), messagesEditSelect.get(0).getUserName(), messagesEditSelect.get(0).getPassword(),messagesEditSelect.get(0).getFirstName(), messagesEditSelect.get(0).getLastName(), messagesEditSelect.get(0).getEmail(), messagesEditSelect.get(0).getRole(), messagesEditSelect.get(0).getPhone(), messagesEditSelect.get(0).getStatus(), messagesEditSelect.get(0).isBusiness(), messagesEditSelect.get(0).getBranch_manager_ID(), messagesEditSelect.get(0).getArea(), messagesEditSelect.get(0).getDebt(), null, 0, null, null, 0) ;
			//newbus= messagesEditSelect.get(0);
			newbus.setBusiness(true);
			newbus.setBusinessName(newnametext.getText());
			newbus.setIsApproved(true);
			
			sentToJson(newbus.getIsApproved()); 

		}

		@FXML
		void decline(ActionEvent event) {
			labelStatus.setText("Decline");
		//	sentToJson(false);
//			response();
		}

		@FXML
		void backHM(ActionEvent event) {
			try {
				FXMLLoader loader = new FXMLLoader();
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				AnchorPane root;
				root = loader.load(getClass().getResource("/supplier/H.R.fxml").openStream());
				Scene scene = new Scene(root);
				primaryStage.setTitle("H.R");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		void sentToJson(boolean statusMsg) {    //////////change !!!!!!!!!!!!!!!
			Request request = new Request();
			request.setPath("/account/"); /// !?!?!?!?!?!?
			request.setMethod("POST");
			request.setBody(statusMsg);
			Gson gson = new Gson();
			JsonElement jsonUser = gson.toJsonTree(request);

			String jsonFile = gson.toJson(jsonUser);
	    	System.out.println("jsonFile : "+jsonFile);
	    	ClientUI.chat.accept(jsonFile); // in here will be DB ask for restaurant id

		}
		
		
		void FromJson() {
			
		/// change to Account path	
			
		Request request=new Request();
		request.setPath("/restaurants/items/categories"); /// should be account get
		request.setMethod("GET");
		Gson gson=new Gson();
		JsonElement body=gson.toJsonTree(new Object());
		
	
	
		body.getAsJsonObject().addProperty("restaurantID",2 );// String 2 is the current restaurant ID (!!!)
		
		
		
		request.setBody(gson.toJson(body));
		ClientUI.chat.accept(gson.toJson(request));
		businessaccount=gson.fromJson((String)ChatClient.serverAns.getBody(),BusinessAccount[].class);
		

		}
}
