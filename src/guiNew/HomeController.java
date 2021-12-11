package guiNew;

import java.io.IOException;

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
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private HBox Nav;

    @FXML
    private Hyperlink hyperLinkSignin;

    @FXML
    private Hyperlink hyperLinkLogin;

    @FXML
    private Hyperlink hyperLinkAbout;

    @FXML
    private Button btnJoin;

    @FXML
    private HBox Nav1;
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/guiNew/HomePage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Home");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

    @FXML
    void about(ActionEvent event) {

    }

    @FXML
    void login(ActionEvent event) {
    		try {FXMLLoader loader = new FXMLLoader();
    	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		AnchorPane root;
	
			root = loader.load(getClass().getResource("/guiNew/Login.fxml").openStream());
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();
	} catch (IOException e) {}
    }

    @FXML
    void signin(ActionEvent event) {

    }

}
