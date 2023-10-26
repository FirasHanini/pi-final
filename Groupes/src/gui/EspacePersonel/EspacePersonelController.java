/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.EspacePersonel;

import Utilisateur.Utilisateur;
import gui.Admin.AdminDashboardController;
import gui.EspacePersonel.Delete.DeleteController;
import gui.EspacePersonel.Update.UpdateController;

import gui.homePage.HomPageController;
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
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class EspacePersonelController implements Initializable {

    @FXML
    private ImageView entryImage;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label cinLabel;
    
    Utilisateur current ;
    @FXML
    private Label typeLabel;
    @FXML
    private Button rec;
    int i;

    /**
     * Initializes the controller class.
     */
    
    
    public void setter (Utilisateur current , int i){
        
        this.i=i;
        
        
        this.ageLabel.setText(String.valueOf(current.getAge()));
        this.birthdayLabel.setText(current.getDateNaissance());
        this.cinLabel.setText(current.getCIN());
        this.emailLabel.setText(current.getEmail());
        this.lastNameLabel.setText(current.getNom());
        this.nameLabel.setText(current.getPrenom());
        this.usernameLabel.setText(current.getUserName());
        this.typeLabel.setText(current.getType());
         String picPath = current.getPic();
        
        
            
         if (picPath != null && !picPath.isEmpty()) {
             
    System.out.println("Chemin d'accès à l'image : " + picPath); 
    Image img = new Image("file:" + picPath);
    entryImage.setImage(img);
} else {
    // Charger une image par défaut si le chemin d'accès est vide
    Image img = new Image("../Images/R.jpg");
    entryImage.setImage(img);
}
        this.current=current;
         
    }
    
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
       
    }    

    @FXML
    private void onUpdate(ActionEvent event) {
        
        
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Update/Update.fxml"));
            Parent root = loader.load();
            
            
            UpdateController controller=loader.getController();
            
            controller.setter(current);
             
            Stage cStage= (Stage) ageLabel.getScene().getWindow();
            cStage.setWidth(700);
            cStage.setHeight(740);
              
            ageLabel.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
            
        
        
      
      
        
    }

    @FXML
    private void onDelete(ActionEvent event) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Delete/Delete.fxml"));
            Parent root = loader.load();
            
            
            DeleteController controller=loader.getController();
            controller.setUtilisateur(current);
            
            Stage cStage= (Stage) ageLabel.getScene().getWindow();
            cStage.setWidth(425);
            cStage.setHeight(350);
              
            ageLabel.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void onRec(ActionEvent event) {
        if(i==0){ 
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../homePage/hompage.fxml"));
            Parent root = loader.load();
            
            
              HomPageController controller=loader.getController();
              controller.setUtilisateur(current);
              
            Stage cStage= (Stage) this.ageLabel.getScene().getWindow();
           cStage.setWidth(600);
            cStage.setHeight(500);
              
            ageLabel.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
         }else{
             try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Admin/AdminDashboard.fxml"));
            Parent root = loader.load();
            
            
              AdminDashboardController controller =loader.getController();
              controller.setUtilisateur(current);
              
            Stage cStage= (Stage) this.ageLabel.getScene().getWindow();
            cStage.setWidth(920);
            cStage.setHeight(425);
              
            ageLabel.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        }
        
        
    }

 
    
    
    
    
    
    
    
    
    
    
    
}
