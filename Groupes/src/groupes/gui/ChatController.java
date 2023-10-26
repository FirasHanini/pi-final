/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package groupes.gui;
import Utilisateur.Utilisateur;
import groupes.services.ChatFunctions;
import com.jfoenix.controls.JFXButton;
import groupes.services.GroupFunctions;
import groupes.services.MiscFunctions;
import gui.homePage.HomPageController;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * FXML Controller class
 *
 * @author hammeda
 */
public class ChatController implements Initializable {

    @FXML
    private ListView<String> conversationListView;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Text EmailTXT;
    @FXML
    private JFXButton SendMessagebtn;
long idOwner;
private String selectedItem; 
ChatFunctions ChatF = new ChatFunctions();
    @FXML
    private ListView<String> conversationListView1;
    @FXML
    private ImageView ReturnImg;
    GroupFunctions groupF=new GroupFunctions();
    @FXML
    private Text you;
public void setSelectedItem(String selectedItem) {
    this.selectedItem = selectedItem;
}
int test=0;
Utilisateur current;
     public void setUtilisateur(Utilisateur current){
        this.current=current;
         idOwner=this.current.getId();
    }

@Override
public void initialize(URL url, ResourceBundle rb) {
  
        try {
            ChatF.loadConversation(conversationListView,conversationListView1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        you.setText(ChatF.getEmailbyID(idOwner));
    
}


    
   



  
    public void setEmail(String email) {
        EmailTXT.setText(email);
        
    }

    @FXML
    private void SendMessage(MouseEvent event) throws SQLException, IOException  {
         
  if (!messageTextArea.getText().isEmpty()||(messageTextArea.getText().length()>1)) {
        String messageText = messageTextArea.getText();

     
        boolean containsBadWords = ChatF.BadWordCheck(messageText);

        if (containsBadWords) {
            test++;
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("BE RESPECTFUL");
            alert.showAndWait();
           
        }
      MiscFunctions MF=new MiscFunctions();
        
      try {
          ChatF.Send(idOwner, EmailTXT, MF.Encrypt(messageTextArea.getText()));
      } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
          ex.getMessage();
      }
        messageTextArea.clear();
        ChatF.loadConversation(conversationListView, conversationListView1);
        
        if(test==3){ try {
            test=0;
        groupF.navigateIMGToFXML("../../gui/SignIn/SignIn.fxml", ReturnImg);
    } catch (IOException ex) {
        ex.getMessage();
    }
        }
    }    }

    @FXML
    private void Return(MouseEvent event) {
    
    
    
      try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Conversation.fxml"));
            Parent root = loader.load();
            
            
               ConversationController controllerGroupe =loader.getController();
              controllerGroupe.setUtilisateur(current);
              
            Stage cStage= (Stage) this.SendMessagebtn.getScene().getWindow();
            cStage.setWidth(720);
            cStage.setHeight(720);
              
            SendMessagebtn.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
    
    
    
    
}
