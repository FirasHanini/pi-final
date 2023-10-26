package groupes.gui;

import Utilisateur.Utilisateur;
import groupes.services.ChatbotFunctions;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class ChatbotController {
Utilisateur current;
     public void setUtilisateur(Utilisateur current){
        this.current=current;
    }
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextArea mainTextArea;
    @FXML
    private TextField messageArea;
    @FXML
    private Button sendButtonbtn;

    private boolean convoStarted = false;
ChatbotFunctions CBF=new ChatbotFunctions();
    public void initialize() {
        mainTextArea.getStyleClass().add("text-area");
        messageArea.getStyleClass().add("text-field");
        sendButtonbtn.getStyleClass().add("button");
        messageArea.getStyleClass().add("message-text");

        addToTextArea('b', "Welcome to the Art and Communication Application Chatbot. Do you need any help?");
    }

    public void addToTextArea(char who, String addText) {
        if (who == 'b') {
            mainTextArea.appendText("\n[Bot] -> " + addText);
        } else {
            mainTextArea.appendText("\n[You] -> " + addText);
        }
    }

    @FXML
    public void sendButton() {
        String userMessage = messageArea.getText();
        addToTextArea('y', userMessage);

        if (!convoStarted) {
            if (CBF.isAffirmative(userMessage)) {
                convoStarted = true;
                 CBF.playNotificationSound();
                addToTextArea('b', "Great! Let's explore the Art and Communication Application. Maybe start with asking for help!");
            } else {
                addToTextArea('b', "I didn't understand. Please say 'yes' to start.");
                 CBF.playNotificationSound();
            }
        } else {
            
            String botResponse = CBF.respondToUserInput(userMessage);
            CBF.playNotificationSound();
            addToTextArea('b', botResponse);
        }

        messageArea.clear();
    }

   

    
}
