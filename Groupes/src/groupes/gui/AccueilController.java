/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package groupes.gui;
import groupes.services.GroupFunctions;
import com.jfoenix.controls.JFXButton;
import groupes.services.MiscFunctions;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class AccueilController implements Initializable {

    @FXML
    private JFXButton Commbtn;
    @FXML
    private JFXButton Mygroups;
GroupFunctions GF=new GroupFunctions();
    MiscFunctions MF = new MiscFunctions();
    @FXML
    private ImageView ReturnImg;
    GroupFunctions groupF=new GroupFunctions();
    @FXML
    private ImageView ChatBotimg;
    @FXML
    private Text weather;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       weather.setText(MF.GetWeather());
        try {
            System.out.println(MF.Translate("Hello"));
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        try {
            System.out.println(MF.Encrypt("amine"));
           try {
               System.out.println(MF.decryptPassword(MF.Encrypt("amine"), "A26B773F41F90732D8497BDFB6F15021"));
           } catch (Exception ex) {
               Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
           }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
     
    }

    @FXML
    private void GotoComm(MouseEvent event) {
        try {
            GF.navigateToFXML("../gui/Conversation.fxml",Commbtn);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void GotoMyGrou(MouseEvent event) {
         try {
            GF.navigateToFXML("../gui/YourGroups.fxml",Commbtn);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void Return(MouseEvent event) {
       /* try {
        groupF.navigateIMGToFXML("../gui/Accueil.fxml", ReturnImg);
    } catch (IOException ex) {
        ex.getMessage();
    }
    */
    }

    @FXML
    private void OpenSuppBot(MouseEvent event) {
        
      
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatbot.fxml"));
        Parent root = loader.load();

        Stage chatBotStage = new Stage();
        chatBotStage.setTitle("ChatBot Window");

        Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        chatBotStage.setScene(scene);

        chatBotStage.show();

    } catch (Exception e) {
        e.getMessage();
    }
    }
}
