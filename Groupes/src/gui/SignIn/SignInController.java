/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.SignIn;

import Utilisateur.Capture;
import Utilisateur.MailValidation;
import Utilisateur.Type;
import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;
import gui.Admin.AdminDashboardController;
import gui.EspacePersonel.EspacePersonelController;
import gui.ForgotPassword.ForgotPasswordController;
import gui.Inscription.InscriptionController;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class SignInController implements Initializable {

    @FXML
    private TextField entryUsername;
    @FXML
    private PasswordField entryPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Hyperlink linkForgotPaswd;
    @FXML
    private Hyperlink linkNewAccount;
    
    UtilisateurService service=UtilisateurService.getInstance();
    @FXML
    private Label errorLabel;
    int i=0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void login(ActionEvent event) {
        
        
        String username=entryUsername.getText();
        String passwd = entryPassword.getText();
        errorLabel.setText("");
        if(i==5){
            Capture.captureAndSaveImage(username);
            Utilisateur found = service.chercher(username);
            String dir=System.getProperty("user.dir");
            String path= (dir+"\\src\\images\\");
            String image=path+username+".JPG";
            String message=MailValidation.tooManyPassword(found);
            
            MailValidation.sendEmailWithAttachment(found.getEmail(), "Too many password attempt", message, image);
        }
        
        
            switch(service.login(username, passwd)){
                case 0:
                    {
            Utilisateur u = new Utilisateur();
            u.setUserName(username);
            Utilisateur current = service.chercher(u.getUserName());
            System.out.println(current);
            current.setPassword(passwd);
            if(current.getType().equals(Type.ADMIN.name())){
                
                
                try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Admin/AdminDashboard.fxml"));
            Parent root = loader.load();
            
            
             AdminDashboardController controller=loader.getController();
              controller.setUtilisateur(current);
              
            Stage cStage= (Stage) entryUsername.getScene().getWindow();
            cStage.setWidth(920);
            cStage.setHeight(425);
              
            entryUsername.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
                
                
                
                
                
                
            }else{
                
                 try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../homePage/homPage.fxml"));
            Parent root = loader.load();
            
            
              HomPageController controller=loader.getController();
              controller.setUtilisateur(current);
              
            Stage cStage= (Stage) entryUsername.getScene().getWindow();
            cStage.setWidth(770);
            cStage.setHeight(600);
              
            entryUsername.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            
            
            
        /*
       */
        }
            }
                    break;
                case -1:
                {
                    errorLabel.setText("Username not found !");
                }
                break;
                case-2:{
                    errorLabel.setText("Wrong password !");
                    i++;
                }
                    
            }
        
        
        
        
        
        
    }

    
    
    
    
    
    
    
    @FXML
    private void forgotPassword(ActionEvent event) {
        Utilisateur current = service.chercher(entryUsername.getText());
            if(current!=null){
        try {
            String code= MailValidation.generateVerificationCode();
            String message = MailValidation.passwordForgotEmail(current, code);
            MailValidation.sendVerificationCode(current.getEmail(),"Forgot Password", message);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ForgotPassword/ForgotPassword.fxml"));
            Parent root = loader.load();
            ForgotPasswordController ic=loader.getController();
            ic.setter(current, code);
            Stage cStage= (Stage) entryUsername.getScene().getWindow();
            cStage.setWidth(420);
            cStage.setHeight(280);
            entryUsername.getScene().setRoot(root);
            
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    }

    @FXML
    private void createAccount(ActionEvent event) {
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Inscription/Inscription.fxml"));
            Parent root = loader.load();
            InscriptionController ic=loader.getController();
            Stage cStage= (Stage) entryUsername.getScene().getWindow();
            cStage.setWidth(710);
            cStage.setHeight(740);
            entryUsername.getScene().setRoot(root);
            
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
}
