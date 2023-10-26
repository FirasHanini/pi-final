/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Inscription.MailVerification;

import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;
import gui.EspacePersonel.EspacePersonelController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class MailVerificationCodeController implements Initializable {

    @FXML
    private TextField codeVerif;
    
    private String code;
    @FXML
    private Label errorCode;
    
    private Utilisateur current;

    /**
     * Initializes the controller class.
     * @param code
     */
    
    
    public void setCode(String code){
        this.code=code;
    }
    
    public void setUtilisateur(Utilisateur user){
        this.current=user;
    }
            
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onConfirm(ActionEvent event) {
        String verifcode= codeVerif.getText();
        if(!verifcode.equals(code))
            errorCode.setText("Verification code does not match");
        else{
            UtilisateurService service= UtilisateurService.getInstance();
            service.ajouter(current);
            
            //////////////////////////////////////Naviguation/////////////////////////////////
           
            Utilisateur nouveau = service.chercher(current.getUserName());
        
          try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../EspacePersonel/EspacePersonel.fxml"));
            Parent root = loader.load();
              EspacePersonelController controller=loader.getController();
              controller.setter(nouveau,0);
              
            Stage cStage= (Stage) codeVerif.getScene().getWindow();
            cStage.setWidth(710);
            cStage.setHeight(740);
              
            codeVerif.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            
        }
        
    }
    
   
    
}
