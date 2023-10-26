/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.EspacePersonel.Delete;

import Reclamation.ReclamationService;
import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;
import gui.EspacePersonel.EspacePersonelController;
import gui.SignIn.SignInController;
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
public class DeleteController implements Initializable {

    @FXML
    private TextField entryPassword;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;
    
    Utilisateur current; 
    UtilisateurService service= UtilisateurService.getInstance();
    ReclamationService serviceReclamation = ReclamationService.getInstance();
    @FXML
    private Label errorLabel;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../EspacePersonel.fxml"));
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
        
        String pass = entryPassword.getText();
        System.out.println(pass+" "+current.getPassword());
        
        if(pass.equals(current.getPassword())){
            serviceReclamation.supprimerParSender(current);
            service.supprimer(current);
            
            try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../SignIn/SignIn.fxml"));
            Parent root = loader.load();
            
            
             SignInController controller=loader.getController();
              
              
            Stage cStage= (Stage) this.btnCancel.getScene().getWindow();
            cStage.setWidth(720);
            cStage.setHeight(520);
              
            btnCancel.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            
            
        }
        else 
            errorLabel.setText("Wrong password try again !");
       
            
            
    }
    
    
    public void setUtilisateur(Utilisateur current){
        this.current=current;
    }
    
}
