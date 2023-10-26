/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.EspacePersonel.Update.UpdatePassword;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class NewPasswordInputController implements Initializable {

    @FXML
    private PasswordField entryPassword1;
    @FXML
    private PasswordField entryPassword2;
    @FXML
    private Button cancel;
    @FXML
    private Button confirm;
    
    Utilisateur current;
    @FXML
    private Label errorLabel;
    UtilisateurService service = UtilisateurService.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onCancel(ActionEvent event) {
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../EspacePersonel.fxml"));
            Parent root = loader.load();
            
            
            EspacePersonelController controller=loader.getController();
            controller.setter(current,0);
            
            Stage cStage= (Stage) this.cancel.getScene().getWindow();
            cStage.setWidth(710);
            cStage.setHeight(740);
              
            cancel.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    
    
    
    @FXML
    private void onConfirm(ActionEvent event) {
        String passOne= this.entryPassword1.getText();
        String passTwo= this.entryPassword2.getText();
        if(!passOne.equals(passTwo))
            errorLabel.setText("Passwords does not match !");
        else{
            Utilisateur nouveau = current;
            nouveau.setPassword(passTwo);
            service.modifier(current, nouveau);
             try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../EspacePersonel.fxml"));
            Parent root = loader.load();
            
            
            EspacePersonelController controller=loader.getController();
            controller.setter(nouveau,0);
            
            Stage cStage= (Stage) this.cancel.getScene().getWindow();
            cStage.setWidth(710);
            cStage.setHeight(740);
              
            cancel.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            
        }
    }
    
    
    
    
    
    
    
    public void setUtilisateur ( Utilisateur current){
        this.current=current;
    }
}
