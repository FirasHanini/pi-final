/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Admin.Utilisateurs;

import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;
import gui.Admin.AdminDashboardController;
import gui.Admin.Reclamations.ReclamationsController;



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
public class UtilisateursController implements Initializable {

    @FXML
    private Button adminsButton;
    @FXML
    private Button utilisateurButton;
    @FXML
    private TextField searchEntry;
    @FXML
    private Label errorLabel;
    @FXML
    private Button reclamationbtn;
    Utilisateur current;
    @FXML
    private TableColumn<Utilisateur, Long> idColumn;
    @FXML
    private TableColumn<Utilisateur, String> usernameColumn;
    @FXML
    private TableColumn<Utilisateur, String> lastnameColumn;
    @FXML
    private TableColumn<Utilisateur, String> firstnameColumn;
    @FXML
    private TableColumn<Utilisateur, String> emailColumn;
    @FXML
    private TableView<Utilisateur> userTable;
    List <Utilisateur> utilisateurs=new ArrayList<>();
    UtilisateurService service = UtilisateurService.getInstance();

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
    }

    @FXML
    private void searched(ActionEvent event) {
        errorLabel.setText("");
         Utilisateur found=null;
        String searched = this.searchEntry.getText();
      
         found =service.chercher(searched);
        if (searched.length()!=0&&found==null ){
            errorLabel.setText("User not found");
            
        }else if (searched.length()!=0&&found!=null)
        {
            utilisateurs.clear();
            utilisateurs.add(found);
            this.setTable();
            
            
        }else{
            utilisateurs.clear();
            utilisateurs.addAll(service.retournerTout());
            this.setTable();
            
            
        }
    }

    
    
    
    @FXML
    private void onReclamation(ActionEvent event) {
        
         try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Reclamations/Reclamations.fxml"));
            Parent root = loader.load();
            
            
              ReclamationsController controllerReclam =loader.getController();
              controllerReclam.setUtilisateur(current);
              
            Stage cStage= (Stage) this.adminsButton.getScene().getWindow();
            cStage.setWidth(920);
            cStage.setHeight(425);
              
            adminsButton.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }

  

    @FXML
    private void onLigne(MouseEvent event) {
        Utilisateur selected = this.userTable.getSelectionModel().getSelectedItem();
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UtilisateurProperties.fxml"));
            Parent root = loader.load();
            
            
              UtilisateurPropertiesController controllerUtilisateur =loader.getController();
              controllerUtilisateur.setter(current,selected,0);
              
            Stage cStage= (Stage) this.adminsButton.getScene().getWindow();
            cStage.setWidth(720);
            cStage.setHeight(720);
              
            adminsButton.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    
    
    
    
    
      public void setUtilisateur(Utilisateur current) {
        this.current=current;
        utilisateurs.addAll(service.retournerTout());
        this.setTable();
    }
      
      private void setTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory("userName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory("nom"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory("type"));
        emailColumn.setCellValueFactory(new PropertyValueFactory("email"));
      
        this.userTable.setItems(FXCollections.observableArrayList(utilisateurs));
          
      }
    
}
