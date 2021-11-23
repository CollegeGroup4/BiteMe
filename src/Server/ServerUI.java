package Server;

import java.util.Vector;

import gui.ServerPortFrameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	private static EchoServer echoServer; 
	public static ServerPortFrameController serverController; 
	
	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ServerPortFrameController aFrame = new ServerPortFrameController(); // create StudentFrame
		EchoServer.serverController = aFrame;
		aFrame.start(primaryStage);
	}

	public static void runServer(String p) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(p); // Set port to 5555

		} catch (Throwable t) {
			System.out.println("ERROR - Could not connect!");
		}

		EchoServer sv = new EchoServer(5555);
		echoServer = sv;
		echoServer.setController(serverController);
		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
	

	public static void closeServer() {

		try {
			echoServer.close(); // Stop listening
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

}