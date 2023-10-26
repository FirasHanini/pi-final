/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Admin;

import Event.gui.EventAdminController;
import Utilisateur.Type;
import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;
import gui.Admin.AddAdmin.AddAdminController;
import gui.Admin.AddAdmin.PasswordInputController;
import gui.Admin.Reclamations.ReclamationsController;
import gui.Admin.Utilisateurs.UtilisateursController;
import gui.EspacePersonel.EspacePersonelController;

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
import javafx.scene.control.Hyperlink;
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
public class AdminDashboardController implements Initializable {

    @FXML
    private Button adminsButton;
    @FXML
    private Button utilisateurButton;
    @FXML
    private TextField searchEntry;
    @FXML
    private Button addAdmin;
    @FXML
    private Label errorLabel;
    
    Utilisateur current;
    @FXML
    private Button reclamation;
    @FXML
    private TableView<Utilisateur> tableAdmin= new TableView();
    @FXML
    private TableColumn<Utilisateur, Long> idColmun;
    @FXML
    private TableColumn<Utilisateur, String> usernameColmun;
    @FXML
    private TableColumn<Utilisateur, String> lastnameColmun;
    @FXML
    private TableColumn<Utilisateur, String> firstNameColmun;
    @FXML
    private TableColumn<Utilisateur, String> emailColmun;
    
    UtilisateurService service=UtilisateurService.getInstance();
     List <Utilisateur> admins=new ArrayList<>();;
    @FXML
    private Hyperlink profilHyper;
    @FXML
    private Hyperlink eventHyper;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onAdmins(ActionEvent event) {
    }

    @FXML
    private void onUtilisateur(ActionEvent event) {
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Utilisateurs/Utilisateurs.fxml"));
            Parent root = loader.load();
            
            
              UtilisateursController controller=loader.getController();
              controller.setUtilisateur(current);
              
            Stage cStage= (Stage) this.addAdmin.getScene().getWindow();
           cStage.setWidth(920);
            cStage.setHeight(425);
              
            addAdmin.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void searched(ActionEvent event) {
        errorLabel.setText("");
         Utilisateur found=null;
        String searched = this.searchEntry.getText();
      
         found =service.chercher(searched);
        if ((searched.length()!=0&&found==null) ||(found!=null && !found.getType().equals(Type.ADMIN.name()))){
            errorLabel.setText("Admin not found");
            
        }else if (searched.length()!=0&&found!=null&&found.getType().equals(Type.ADMIN.name()))
        {
            admins.clear();
            admins.add(found);
            this.setTable();
            
            
        }else{
            admins.clear();
            admins.addAll(service.retournerAdmin());
            this.setTable();
            
            
        }
        
    }

    @FXML
    private void onAddAdmin(ActionEvent event) {
         try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAdmin/AddAdmin.fxml"));
            Parent root = loader.load();
            
            
              AddAdminController controllerReclam =loader.getController();
              controllerReclam.setUtilisateur(current);
              
            Stage cStage= (Stage) this.adminsButton.getScene().getWindow();
            cStage.setWidth(420);
            cStage.setHeight(380);
              
            adminsButton.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    
    
    
  

    @FXML
    private void onReclamations(ActionEvent event) {
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Reclamations/Reclamations.fxml"));
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
        Utilisateur selected = this.tableAdmin.getSelectionModel().getSelectedItem();
        if(event.getClickCount()==2){
            
        if(selected!=null && !selected.getUserName().equals(current.getUserName())){
            Utilisateur found = service.chercher(selected.getUserName());
            try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAdmin/PasswordInput.fxml"));
            Parent root = loader.load();
            
            
              PasswordInputController controllerReclam =loader.getController();
              
              controllerReclam.setUtilisateur(current,found,1);
              
            Stage cStage= (Stage) this.addAdmin.getScene().getWindow();
            cStage.setWidth(340);
            cStage.setHeight(243);
              
            addAdmin.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        }
        }
        
    }

    
    
    
    
    
      public void setUtilisateur( Utilisateur current){
        this.current=current;
        admins.addAll(service.retournerAdmin());
        setTable();
        
    }
      
      private void  setTable(){
        
        
        
        idColmun.setCellValueFactory(new PropertyValueFactory("id"));
        usernameColmun.setCellValueFactory(new PropertyValueFactory("userName"));
        firstNameColmun.setCellValueFactory(new PropertyValueFactory("prenom"));
        lastnameColmun.setCellValueFactory(new PropertyValueFactory("nom"));
        emailColmun.setCellValueFactory(new PropertyValueFactory("email"));
      
        this.tableAdmin.setItems(FXCollections.observableArrayList(admins));
          
      }

    @FXML
    private void onProfil(ActionEvent event) {
        
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../EspacePersonel/EspacePersonel.fxml"));
            Parent root = loader.load();
            
            
              EspacePersonelController controller=loader.getController();
              controller.setter(current,1);
              
            Stage cStage= (Stage) this.addAdmin.getScene().getWindow();
            cStage.setWidth(710);
            cStage.setHeight(740);
              
            addAdmin.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void onEvent(ActionEvent event) {
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../Event/gui/EventAdmin.fxml"));
            Parent root = loader.load();
            
            
              EventAdminController controllerAd=loader.getController();
              controllerAd.setUtilisateur(current);
              
            Stage cStage= (Stage) this.addAdmin.getScene().getWindow();
            cStage.setWidth(1130);
            cStage.setHeight(840);
              
            addAdmin.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
   
    
}
