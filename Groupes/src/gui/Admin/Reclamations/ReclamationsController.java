/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Admin.Reclamations;

import Reclamation.Reclamation;
import Reclamation.ReclamationService;
import Utilisateur.Utilisateur;
import gui.Admin.AdminDashboardController;
import gui.Admin.Utilisateurs.UtilisateursController;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class ReclamationsController implements Initializable {

    @FXML
    private Button adminsButton;
    @FXML
    private Button utilisateurButton;
    @FXML
    private Button reclamationbtn;
    @FXML
    private TextField searchEntry;
    @FXML
    private Label errorLabel;
    Utilisateur current;
    @FXML
    private TableView<Reclamation> reclamationTable;
    @FXML
    private TableColumn<Reclamation, Long> idColumn;
    @FXML
    private TableColumn<Reclamation, Long> senderIdColumn;
    @FXML
    private TableColumn<Reclamation, String> StateColumn;
    
    List<Reclamation> reclamations=new ArrayList<>();
    ReclamationService service = ReclamationService.getInstance();
    @FXML
    private TableColumn<Reclamation, String> senderUsername;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onAdmins(ActionEvent event) {
         try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AdminDashboard.fxml"));
            Parent root = loader.load();
            
            
              AdminDashboardController controller =loader.getController();
              controller.setUtilisateur(current);
              
            Stage cStage= (Stage) this.adminsButton.getScene().getWindow();
            cStage.setWidth(920);
            cStage.setHeight(425);
              
            adminsButton.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void onUtilisateur(ActionEvent event) {
         try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Utilisateurs/Utilisateurs.fxml"));
            Parent root = loader.load();
            
            
              UtilisateursController controllerUtilisateur =loader.getController();
              controllerUtilisateur.setUtilisateur(current);
              
            Stage cStage= (Stage) this.adminsButton.getScene().getWindow();
            cStage.setWidth(920);
            cStage.setHeight(425);
              
            adminsButton.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    @FXML
    private void onReclamation(ActionEvent event) {
        
    }

    @FXML
    private void searched(ActionEvent event) {
        errorLabel.setText(""); 
        List<Reclamation> found = new ArrayList<>();
        String searched = this.searchEntry.getText();
        found.addAll(service.retournerParUtilisateur(searched));
        if(searched.length()!=0&&found.size()==0){
            errorLabel.setText("Reclamations not found"); 
        } else if (searched.length()!=0&& found.size()!=0){
            reclamations.clear();
            reclamations.addAll(found);
            this.setTable();
            
        }else{
            reclamations.clear();
            reclamations.addAll(service.retournerTout());
            this.setTable();
        }
    }

    
    
    

    @FXML
    private void onLigne(MouseEvent event) {
        Reclamation selected =this.reclamationTable.getSelectionModel().getSelectedItem();
        if(event.getClickCount()==2){
            if(selected!=null){
             try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Response.fxml"));
            Parent root = loader.load();
            
            
              ResponseController controllerResponse =loader.getController();
              controllerResponse.setter(current,selected);
              
            Stage cStage= (Stage) this.adminsButton.getScene().getWindow();
            cStage.setWidth(620);
            cStage.setHeight(450);
              
            adminsButton.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            
        }
        }
    }
    
    
    
    
    
    
      public void setUtilisateur(Utilisateur current) {
        this.current=current;
        reclamations.addAll(service.retournerTout());
        setTable();
    }
      
      private void  setTable(){
          
          idColumn.setCellValueFactory(new PropertyValueFactory("id"));
          this.StateColumn.setCellValueFactory(new PropertyValueFactory("etat"));
          this.senderIdColumn.setCellValueFactory(new PropertyValueFactory("senderId"));
          this.senderUsername.setCellValueFactory(new PropertyValueFactory("senderUsername"));
        
      
        this.reclamationTable.setItems(FXCollections.observableArrayList(reclamations));
          
      }
   
    
}
