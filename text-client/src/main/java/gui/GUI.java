package gui;
	


import ai.api.examples.TextClientApplication;
import controller.FxControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUI extends Application {
	
	String sendMsg = "";
	static TextClientApplication dialogFlow;
	
	static String[] apiInfo;
	
	public void start(Stage primaryStage){

		try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource("/gui/ClientGui.fxml"));
	        Parent root = (Parent) fxml.load();
			FxControl test = fxml.getController();	
			test.setDialogFlow(new TextClientApplication(apiInfo, test));
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Denkibot - Laget med Dialogflow");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	
	public static TextClientApplication getDialogFlow () {
		return dialogFlow;
	}
	

	public static void main(String[] args) {
		apiInfo = args;
		launch(args);
}


	public void addText(String newText) {
		// TODO Auto-generated method stub
		
	}
}
