/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.EspacePersonel.Update.UpdatePassword;

import Utilisateur.Utilisateur;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class UpdatePasswordController implements Initializable {

    @FXML
    private TextField entryPassword;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;
    @FXML
    private Label errorLabel;
    Utilisateur current ;

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
            
            Stage cStage= (Stage) entryPassword.getScene().getWindow();
            cStage.setWidth(710);
            cStage.setHeight(740);
              
            entryPassword.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void onConfirm(ActionEvent event) {
        String passwd = entryPassword.getText();
        if(!current.getPassword().equals(passwd))
            errorLabel.setText("Wrong password !");
        else{
             try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewPasswordInput.fxml"));
            Parent root = loader.load();
            
            
            NewPasswordInputController controller=loader.getController();
            controller.setUtilisateur(current);
            
            Stage cStage= (Stage) this.entryPassword.getScene().getWindow();
            cStage.setWidth(420);
            cStage.setHeight(300);
              
            entryPassword.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            
        }
    }
    
    
    
    
    
    public void setUtilisateur(Utilisateur current){
        this.current=current;
    }
    
}
