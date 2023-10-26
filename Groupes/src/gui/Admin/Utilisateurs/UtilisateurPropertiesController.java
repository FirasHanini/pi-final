/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Admin.Utilisateurs;

import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;
import gui.Admin.AddAdmin.PasswordInputController;
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
public class UtilisateurPropertiesController implements Initializable {

    @FXML
    private ImageView entryImage;
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
    @FXML
    private Label typeLabel;
    @FXML
    private Button btnBack;
    Utilisateur current ;
    Utilisateur selected ;
    UtilisateurService service = UtilisateurService.getInstance();
    int i;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    


    
    
    @FXML
    private void onDelete(ActionEvent event) {
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../AddAdmin/PasswordInput.fxml"));
            Parent root = loader.load();
            
            
              PasswordInputController controllerReclam =loader.getController();
              System.out.println(current);
              controllerReclam.setUtilisateur(current,selected,2);
              
            Stage cStage= (Stage) this.ageLabel.getScene().getWindow();
            cStage.setWidth(340);
            cStage.setHeight(243);
              
            ageLabel.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    
    
    
    
    @FXML
    private void onBack(ActionEvent event) {
        if(i==0){
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Utilisateurs.fxml"));
            Parent root = loader.load();
            
            
              UtilisateursController controller=loader.getController();
              controller.setUtilisateur(current);
              
            Stage cStage= (Stage) this.ageLabel.getScene().getWindow();
           cStage.setWidth(920);
            cStage.setHeight(425);
              
            ageLabel.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        }else {
            try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../homePage/hompage.fxml"));
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
        }
            
    }
    
    public void setter (Utilisateur current, Utilisateur selected,int i){
        this.i=i;
        if(i==1)
            this.btnDelete.setVisible(false);
        
        
        this.current=current;
        this.selected=selected;
        this.ageLabel.setText(String.valueOf(selected.getAge()));
        this.birthdayLabel.setText(selected.getDateNaissance());
        this.cinLabel.setText(selected.getCIN());
        this.emailLabel.setText(selected.getEmail());
        this.lastNameLabel.setText(selected.getNom());
        this.nameLabel.setText(selected.getPrenom());
        this.usernameLabel.setText(selected.getUserName());
        this.typeLabel.setText(selected.getType());
         String picPath = selected.getPic();
        
        
            
         if (picPath != null && !picPath.isEmpty()) {
             
    System.out.println("Chemin d'accès à l'image : " + picPath); 
    Image img = new Image("file:" + picPath);
    entryImage.setImage(img);
} else {
    // Charger une image par défaut si le chemin d'accès est vide
    Image img = new Image("../Images/R.jpg");
    entryImage.setImage(img);
}
    }
}
