package controller;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//scene builder gjør slik at vi ikke kan lage chatbobler.
import ai.api.examples.TextClientApplication;
import gui.GUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FxControl {
	
	public TextClientApplication dialogFlow;
	
	@FXML
	private Button sendBtn;
	public TextField userInput;
	public TextArea chatWindow;
	private MenuItem menuExit, aboutDF;
	private MenuBar menuBar;
	private Thread waitForMessage;
	
	public void initialize() {
	        dialogFlow = GUI.getDialogFlow();
	        userInput.textProperty().addListener(((o, oldString, newString) -> listenToUserInput(oldString, newString)));
	        sendBtn.setDisable(true);     
	 }

	//sender meldingen til bot APIen.
	public void sendMsg() {
	    waitForMessage = new Thread(this::startThread);
	    waitForMessage.start();
        dialogFlow.sendRequest(userInput.getText());
        userInput.clear();
	}
	
	//setter maks 70 tegn
	private void listenToUserInput(String oldMessage, String newString) {
		if(newString.length() == 0) {
			sendBtn.setDisable(true);
		}
		
		else 
			
		{
			sendBtn.setDisable(false);
		}
		
		if(newString.length() > 70) {
			userInput.setText(oldMessage);
		}
	}
	
	//Sleep frem til boten kommer tilbake med et svar, deretter kan du skrive igjen, dette er for å unngå spam.
	public void startThread() {
    	userInput.setDisable(true);
    	while(!waitForMessage.isInterrupted()) {
    		if(!waitForMessage.isInterrupted()) {
    	    	try {
    	    		System.out.println("SLEEPING");
    				Thread.sleep(100);
    			} catch (InterruptedException e) {
    				waitForMessage.interrupt();
    				System.out.println("the point is to interrupt");
    			}
    	    }
    	}
    	
    	System.out.println("done sleeping");

    	userInput.setDisable(false);
	}
	
	public void addText(String string) {
		if(string.startsWith("Denki") && waitForMessage.isAlive()) {
			waitForMessage.interrupt();
		}
	
		this.chatWindow.appendText(string + "\n");
	}
	
	//henter svar fra APIen (boten)
	public void getMessage(String message) {
	System.out.println(message);
	
	}
	public void setDialogFlow(TextClientApplication dialogFlow) {
		this.dialogFlow = dialogFlow;
	}
	
	//Lukker programmet ved hjelp av menyknappen "Exit Application"
	public void closeApp(ActionEvent event){
	  Platform.exit();
	  System.exit(0);
}


	//Tar brukeren til nettstedet for Dialogflow ved hjelp av menyknappen "About Dialogflow"
	public void webLink(ActionEvent e) {
        try {
            Desktop.getDesktop().browse(new URI("https://dialogflow.com/"));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
	}

}

