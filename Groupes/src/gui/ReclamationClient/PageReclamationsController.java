/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ReclamationClient;

import Reclamation.Reclamation;
import Reclamation.ReclamationService;
import Reclamation.State;
import Utilisateur.Utilisateur;
import gui.ReclamationClient.ReclamationAjout.ReclamationClientController;
import gui.ReclamationClient.ReclamationReponse.ReclamationReponseController;
import gui.homePage.HomPageController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class PageReclamationsController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Button addReclamation;
    
    @FXML
    private TableView<Reclamation> reclamationView=new TableView();
    
    
    ReclamationService service = ReclamationService.getInstance();
    @FXML
    private TableColumn<Reclamation, String> champcontenu;
    @FXML
    private TableColumn<Reclamation, State> champState;
    Utilisateur current;
    @FXML
    private TableColumn<Reclamation, Long> idcolumn;
     Reclamation selected;
    @FXML
    private Button btnDelete;
    @FXML
    private Label errorLabel;
   
    

    /**
     * Initializes the controller class.
     */
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void onBack(ActionEvent event) {
           try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../homePage/hompage.fxml"));
            Parent root = loader.load();
            
            
              HomPageController controller=loader.getController();
              controller.setUtilisateur(current);
              
            Stage cStage= (Stage) this.addReclamation.getScene().getWindow();
           cStage.setWidth(600);
            cStage.setHeight(500);
              
            addReclamation.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void onAddReclamation(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReclamationAjout/ReclamationClient.fxml"));
            Parent root = loader.load();
            
            
             ReclamationClientController controller=loader.getController();
            controller.setUtilisateur(current);
            
            Stage cStage= (Stage) this.addReclamation.getScene().getWindow();
            cStage.setWidth(620);
            cStage.setHeight(620);
              
            addReclamation.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public void setUtilisateur(Utilisateur current){
        this.current=current;
        System.out.println(this.current.getId());
        List<Reclamation> reclamations=new ArrayList<>();
        reclamations.addAll(service.retournerParUtilisateur(current));
        
        champcontenu.setCellValueFactory(new PropertyValueFactory("contenu"));
        champState.setCellValueFactory(new PropertyValueFactory("etat"));
        idcolumn.setCellValueFactory(new PropertyValueFactory("id"));
           
          
          
          this.reclamationView.setItems(FXCollections.observableArrayList(reclamations));
    }

    @FXML
    private void onLigne(MouseEvent event) {
         selected = reclamationView.getSelectionModel().getSelectedItem();
        if(event.getClickCount()==2){
            if(selected!=null){
              try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReclamationReponse/ReclamationReponse.fxml"));
            Parent root = loader.load();
            
            
              ReclamationReponseController controller=loader.getController();
            controller.setter(selected,current);
            
            Stage cStage= (Stage) this.backButton.getScene().getWindow();
            cStage.setWidth(620);
            cStage.setHeight(620);
              
            backButton.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            
        }
            
        
            
              
    }
    }

    @FXML
    private void onDelete(ActionEvent event) {
        errorLabel.setText("");
        if(selected!=null){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Reclamation?");
                ButtonType buttonTypeOK = new ButtonType("OK");
                ButtonType buttonTypeCancel = new ButtonType("Cancel");
                alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
                alert.initModality(Modality.APPLICATION_MODAL);
                ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
                if (result == buttonTypeOK) {
                    service.supprimer(selected);
                    reclamationView.getItems().remove(selected);
                } else {
                    System.out.println("Suppression annulée.");                

            }
        }else
            errorLabel.setText("Select an item first");
    }

   
}
