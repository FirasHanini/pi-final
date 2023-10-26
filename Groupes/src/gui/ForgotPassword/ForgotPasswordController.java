/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ForgotPassword;

import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;

import gui.SignIn.SignInController;
import gui.Forgotpassword.NewPasswordInputForgotController;
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

import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class ForgotPasswordController implements Initializable {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField entryVerifCode;
    
    Utilisateur current;
    String code;
    UtilisateurService service = UtilisateurService.getInstance();

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
            Stage cStage= (Stage) this.btnCancel.getScene().getWindow();
            cStage.setWidth(720);
            cStage.setHeight(520);
            btnCancel.getScene().setRoot(root);
            
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    @FXML
    private void onConfirm(ActionEvent event) {
        String codeEnter=this.entryVerifCode.getText();
        if(codeEnter.equals(this.code)){
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewPasswordInputForgot.fxml"));
            Parent root = loader.load();
            NewPasswordInputForgotController controller=loader.getController();
            controller.setUtilisateur(current);
            
            Stage cStage= (Stage) this.btnCancel.getScene().getWindow();
            cStage.setWidth(420);
            cStage.setHeight(300);
            btnCancel.getScene().setRoot(root);
            
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            
        }
    }
    
    
    
    
    
    
    
    
    public void setter(Utilisateur current, String code){
        this.current=current;
        this.code=code;
    }
}
