/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ReclamationClient.ReclamationAjout;

import Reclamation.Reclamation;
import Reclamation.ReclamationService;
import Reclamation.State;
import Utilisateur.Utilisateur;
import gui.ReclamationClient.PageReclamationsController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class ReclamationClientController implements Initializable {

    @FXML
    private TextArea entryContenu;
    @FXML
    private Button btnSend;
    
    ReclamationService service = ReclamationService.getInstance(); 
    
    Reclamation sent=new Reclamation();
    Utilisateur sender;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onSend(ActionEvent event) {
        String contenu = entryContenu.getText();
        sent.setContenu(contenu);
        sent.setEtat(State.WAITING);
        System.out.println(sender.getId());
        
        sent.setSender(sender);
        System.out.println(sent);
        service.ajouter(sent);
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../PageReclamations.fxml"));
            Parent root = loader.load();
            
            
             PageReclamationsController controller=loader.getController();
            controller.setUtilisateur(sender);
            
            Stage cStage= (Stage) this.btnSend.getScene().getWindow();
            cStage.setWidth(620);
            cStage.setHeight(550);
              
            btnSend.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }
    
    public void setUtilisateur(Utilisateur sender){
        this.sender=sender;
    }
    
}
