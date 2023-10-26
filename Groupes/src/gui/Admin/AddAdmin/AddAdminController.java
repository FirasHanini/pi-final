/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Admin.AddAdmin;

import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;
import gui.Admin.AdminDashboardController;

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
public class AddAdminController implements Initializable {

    @FXML
    private Button btnSelect;
    @FXML
    private Label errorLabel;
    @FXML
    private Label userProp;
    @FXML
    private Button add;
    @FXML
    private Button cancel;
    Utilisateur current;
    Utilisateur found;
    
    
    UtilisateurService service = UtilisateurService.getInstance();
    @FXML
    private TextField usernameEntry;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onSelect(ActionEvent event) {
        String username = this.usernameEntry.getText();
         found = service.chercher(username);
        if(found!=null){
        this.userProp.setText(found.toString());
        }else{
            this.errorLabel.setText("User not found");
        }
        
    }

    @FXML
    private void onAdd(ActionEvent event) {
        if(found!=null){
         try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PasswordInput.fxml"));
            Parent root = loader.load();
            
            
              PasswordInputController controllerReclam =loader.getController();
              System.out.println(found);
              controllerReclam.setUtilisateur(current,found,0);
              
            Stage cStage= (Stage) this.add.getScene().getWindow();
            cStage.setWidth(340);
            cStage.setHeight(243);
              
            add.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        }else
            this.userProp.setText("Please select a user first");
    }

    @FXML
    private void onCancel(ActionEvent event) {
         try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AdminDashboard.fxml"));
            Parent root = loader.load();
            
            
              AdminDashboardController controllerAdmin =loader.getController();
              controllerAdmin.setUtilisateur(current);
              
            Stage cStage= (Stage) this.add.getScene().getWindow();
            cStage.setWidth(920);
            cStage.setHeight(425);
              
            add.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    
    public void setUtilisateur(Utilisateur current){
        this.current=current;
    }
    
}
