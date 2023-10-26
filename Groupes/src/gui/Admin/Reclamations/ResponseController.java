/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Admin.Reclamations;

import Reclamation.Reclamation;
import Reclamation.ReclamationService;
import Reclamation.State;
import Utilisateur.Utilisateur;
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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class ResponseController implements Initializable {

    @FXML
    private TextArea contenuArea;
    @FXML
    private TextArea ResponseArea;
    @FXML
    private Button send;
    @FXML
    private Button back;
    Utilisateur current;
    Reclamation recieved;
    ReclamationService service = ReclamationService.getInstance();
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
    private void onSend(ActionEvent event) {
        Reclamation nouveau=this.recieved;
        String response = this.ResponseArea.getText();
        if(response.length()==0){
            errorLabel.setText("Write a response first");
        }else {
            nouveau.setReponse(response);
            nouveau.setEtat(State.ANSWERED);
            service.modifier(this.recieved, nouveau);
            try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Reclamations.fxml"));
            Parent root = loader.load();
            
            
              ReclamationsController controllerReclam =loader.getController();
              controllerReclam.setUtilisateur(current);
              
            Stage cStage= (Stage) this.ResponseArea.getScene().getWindow();
            cStage.setWidth(920);
            cStage.setHeight(425);
              
            ResponseArea.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        }
    }

    @FXML
    private void onBack(ActionEvent event) {
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Reclamations.fxml"));
            Parent root = loader.load();
            
            
              ReclamationsController controllerReclam =loader.getController();
              controllerReclam.setUtilisateur(current);
              
            Stage cStage= (Stage) this.ResponseArea.getScene().getWindow();
            cStage.setWidth(920);
            cStage.setHeight(425);
              
            ResponseArea.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    
    
    
    
    
    public void setter(Utilisateur current,Reclamation recieved){
        this.current=current;
        this.recieved=recieved;
        
        this.contenuArea.setText(recieved.getContenu());
        
    }
}
