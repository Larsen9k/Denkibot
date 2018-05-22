package controller;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import ai.api.examples.TextClientApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Er controlleren for FXML filen.
 * @author Lars. Truls og Marius
 *
 */

public class FxControl {
	
	private TextClientApplication dialogFlow;
	
	@FXML
	private Button sendBtn;
	public TextField userInput;
	public TextArea chatWindow;
	private MenuItem menuExit, aboutDF;
	private MenuBar menuBar;
	private Thread waitForMessage;
	
	public void initialize() {
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
	
	//setter maks 300 tegn og at man må skrive noe før man får lov til å sende en melding
	private void listenToUserInput(String oldMessage, String newString) {
		if(newString.length() == 0) {
			sendBtn.setDisable(true);
		}
		
		else 
			
		{
			sendBtn.setDisable(false);
		}
		
		if(newString.length() > 300) {
			userInput.setText(oldMessage);
		}
	}
	
	/*lager en unil thread for å behandle informasjonen av agenten. 
	Threaden vill sjekke hvert 10ms For å se om det har kommet en respons.
	Dette er også en metode for å unngå spam.
	*/
	
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
	
	/*
	 * getMessage henter meldingen fra agenten og så bruker 
	 * addText metoden til å legge det i applikasjonen.
	 */

	public void getMessage(String message) {
	System.out.println(message);
	
	}
	
	public void addText(String string) {
		if(string.startsWith("Denki") && waitForMessage.isAlive()) {
			waitForMessage.interrupt();
		}
	
		this.chatWindow.appendText(string + "\n");
		chatWindow.setWrapText(true);
	}
	
	
	/**
	 * setter TextClientApplication objectet så denne classen kan kalle på metoder.
	 * @param dialogFlow
	 */
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
        } catch (IOException |URISyntaxException e1 ) {
            e1.printStackTrace();
        } 
	}

}

