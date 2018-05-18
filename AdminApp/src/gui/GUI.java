package gui;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;


public class GUI extends Application {
	
	private MenuItem menuExit;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource("/gui/AdminGui.fxml"));
			Parent root = (Parent) fxml.load();
			Scene scene = new Scene(root,600,369);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	//Lukker programmet ved hjelp av menyknappen "Exit Application"
	public void CloseApp(ActionEvent event){
	  Platform.exit();
	  System.exit(0);
}
	
}
