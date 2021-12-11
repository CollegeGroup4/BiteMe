package client;

import gui.ClientMainScreenController;
import guiNew.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {
	public static ClientController chat; // only one instance

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage primaryStage) throws Exception {
		ClientMainScreenController aFrame = new ClientMainScreenController();
		aFrame.start(primaryStage);
	}
}
