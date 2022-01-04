package supplier;

import java.io.IOException;

import com.google.gson.Gson;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import logic.Employee;

public class EmployeeRegisterController {

    @FXML
    private Label dishname;

    @FXML
    private Label price;

    @FXML
    private Button createmployeebtn;

    @FXML
    private TextField BusinessNmaeText;

    @FXML
    private Button back;

    @FXML
    private TextField NameText;

    @FXML
    private TextField UserText;

    @FXML
    private TextField BranchName;

    @FXML
    private HBox Nav;

    @FXML
    private Button LogOut;

    @FXML
    void Back(ActionEvent event) {
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
    void sendtoserver(Employee employee) {
    	
    	
    	
    	//// change to employee send
		Request request=new Request();
		request.setPath("/restaurants/items"); ///// should be employee !!!
		request.setMethod("POST");
		Gson gson=new Gson();
	
		request.setBody(gson.toJson(employee));
		ClientUI.chat.accept(gson.toJson(request));
		
		if(ChatClient.serverAns.getCode()!= 200 ||ChatClient.serverAns.getCode()!= 201 ) {
			
			//Warning
		}
		
		//Category [] categories=gson.fromJson((String)ChatClient.serverAns.getBody(),Category[].class );
		
		
	}
    @FXML
    void LogOut(ActionEvent event) {

    }

    @FXML
    void createmployee(ActionEvent event) {
    	Employee employee=new Employee (null, false, null, null, null);
    	
    	
    	employee.setApproved(false);
    	employee.setBranchManagerUserName(BranchName.getText());
    	employee.setBusinessName(BusinessNmaeText.getText());
    	employee.setHrName(NameText.getText());
    	employee.setHrUserName(UserText.getText());
    	
    	System.out.println(employee.getBusinessName());
    	//*** and send to DB ***///
    	
    	sendtoserver(employee);
    	
    	
    }

}
