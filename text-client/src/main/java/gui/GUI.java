package gui;
	


import java.io.IOException;

import ai.api.examples.TextClientApplication;
import controller.FxControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main classen lager objectene og blir callet når programmmet kjører
 * @author Lars, Truls og Marius
 *
 */

public class GUI extends Application {
	
	String sendMsg = "";
	static String[] apiInfo;
	/**
	 * Lager ett FxControl object som håndterer gui
	 * Og ett TextClientApplication som representer dialogflow komunikasjonnen
	 * FxControl objectet blir laget av FXML fila. Og FxControl er controlleren til fila.
	 * Starter også 
	 */
	
		@Override
		public void start(Stage primaryStage){
			try {
				FXMLLoader fxml = new FXMLLoader(getClass().getResource("/gui/ClientGui.fxml"));
		        Parent root = (Parent) fxml.load();
				FxControl test = fxml.getController();	
				test.setDialogFlow(new TextClientApplication(apiInfo, test));
				invokeScene(primaryStage, root);
			} catch(NullPointerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Starter scena med DenkiBot
		 * @param primaryStage er scena
		 * @param root er panen som er root for scena
		 */
	
		private void invokeScene(Stage primaryStage, Parent root) {
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Denkibot - Laget med Dialogflow");
		}
		
	public static void main(String[] args) {
		apiInfo = args;
		launch(args);
	}
}
