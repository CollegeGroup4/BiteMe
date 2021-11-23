package Server;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.Faculty;
import logic.Order;

import java.util.Vector;
import gui.MainScreenController;
import gui.ServerPortFrameController;
import gui.AllOrdersController;
import Server.EchoServer;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
<<<<<<< HEAD
	private static EchoServer echoServer; 
=======
	//public static Vector<Student> students=new Vector<Student>();

	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
>>>>>>> master
	
	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ServerPortFrameController aFrame = new ServerPortFrameController(); // create StudentFrame

		aFrame.start(primaryStage);
	}

	public static void runServer(String p) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(p); // Set port to 5555

		} catch (Throwable t) {
			System.out.println("ERROR - Could not connect!");
		}

		EchoServer sv = new EchoServer(port);
		echoServer = sv;
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
