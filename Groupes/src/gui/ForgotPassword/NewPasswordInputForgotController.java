/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Forgotpassword;

import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;
import gui.SignIn.SignInController;
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
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class NewPasswordInputForgotController implements Initializable {

    @FXML
    private PasswordField entryPassword1;
    @FXML
    private PasswordField entryPassword2;
    @FXML
    private Button cancel;
    @FXML
    private Button confirm;
    @FXML
    private Label errorLabel;
    Utilisateur current;
    UtilisateurService service = UtilisateurService.getInstance();
    @FXML
    private Label passwdStrenght;
    int j;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    
    @FXML
    private void onCancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../SignIn/SignIn.fxml"));
            Parent root = loader.load();
            SignInController ic=loader.getController();
            Stage cStage= (Stage) this.entryPassword1.getScene().getWindow();
            cStage.setWidth(720);
            cStage.setHeight(520);
            entryPassword1.getScene().setRoot(root);
            
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    
    
    @FXML
    private void onConfirm(ActionEvent event) {
         String passOne= this.entryPassword1.getText();
        String passTwo= this.entryPassword2.getText();
        if(!passOne.equals(passTwo))
            errorLabel.setText("Passwords does not match !");
        else if (j==-2){
        
            errorLabel.setText("Your password is weak !");
        }
        else{
            Utilisateur nouveau = current;
            nouveau.setPassword(passTwo);
            service.modifier(current, nouveau);
             try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../SignIn/SignIn.fxml"));
            Parent root = loader.load();
            SignInController ic=loader.getController();
            Stage cStage= (Stage) this.entryPassword1.getScene().getWindow();
            cStage.setWidth(720);
            cStage.setHeight(520);
            entryPassword1.getScene().setRoot(root);
            
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        }
        
        
    }
    
    
    public void setUtilisateur(Utilisateur current){
        this.current=current;
    }

    @FXML
    private void onPaasone(KeyEvent event) {
          j=50;
        String passwd =this.entryPassword1.getText();
        switch (service.passwordStrength(passwd)){
            case 0:{
                
                this.passwdStrenght.setText("Strong");
                j=0;
            }
            break;
            case -1:{
                
                this.passwdStrenght.setText("Medium");
                j=-1;
            }
            break;
            case -2:{
                
                this.passwdStrenght.setText("Weak");
                j=-2;
            }
            break;
        }
        
        
    }
    }

