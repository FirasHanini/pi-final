/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Inscription;

import gui.Inscription.MailVerification.MailVerificationCodeController;
import Utilisateur.MailValidation;
import Utilisateur.Type;
import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;
import gui.SignIn.SignInController;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyEvent;

import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author Utilisateur 2
 */
public class InscriptionController implements Initializable {

    @FXML
    private TextField entryName ;
    @FXML
    private TextField entryLastName;
    @FXML
    private TextField entryEmail;
    @FXML
    private TextField entryUsername;
    @FXML
    private PasswordField entryPasswordOne;
    @FXML
    private PasswordField entryPasswordConfirm;
    @FXML
    private DatePicker entryBirthday;
    @FXML
    private TextField entryAge;
    @FXML
    private Button signUp;
    @FXML
    private Button uploadPcbtn;
    
    @FXML
    private TextField entryCin;

    /**
     * Initializes the controller class.
     */
    
    String picPath ;
    int j;
    
    
    UtilisateurService service= UtilisateurService.getInstance();
    @FXML
    private Label mdpError;
    @FXML
    private ChoiceBox<String> typechoiceBox=new ChoiceBox<>();
    @FXML
    private Button cancel;
    @FXML
    private Label passowrdStrenght;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typechoiceBox.getItems().addAll(Type.FORMATEUR.name(),Type.VENDEUR.name(),Type.VISITEUR.name());
        typechoiceBox.setValue(Type.VISITEUR.name());
        
    }    

    @FXML
    private void signUpBtn(ActionEvent event) {
        mdpError.setText("");
        Utilisateur user= new Utilisateur();
        int i=0;
        
        String name = entryName.getText();
        String lastname = entryLastName.getText();
        String email= entryEmail.getText();
        String username= entryUsername.getText();
        String passwd = entryPasswordOne.getText();
        String passwdConf = entryPasswordConfirm.getText();
        String pic =picPath;
        String cin = entryCin.getText();
        String dN = entryBirthday.getValue().toString();
        String age = entryAge.getText();
        String type = typechoiceBox.getValue();
        Utilisateur nouveau=new Utilisateur();
        
        
        /////////////////////////CONTROL/////////////////////////////////////////////
        if(name.matches(".*\\d.*")){
            i++;
            this.mdpError.setText("Name should not contain numbers !");
        }
            if(lastname.matches(".*\\d.*")){
            this.mdpError.setText("Lastname should not contain numbers !");
            i++;
            }
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
            this.mdpError.setText("Invalid email structure !");
        i++;
        }
       
        if(cin.length()>8|| cin.length()<8 || !cin.matches("\\d+"))
            this.mdpError.setText("Invalid CIN !");
            
            
            
            
        if(passwd.isEmpty()){
            this.mdpError.setText("Empty password area !");
            i++;
                    }
        
        if(passwdConf.isEmpty()){
            this.mdpError.setText("Empty password confirmation area !");
            i++;
        }
        
        
        if(!passwd.isEmpty()&&!passwdConf.isEmpty()&&!passwd.equals(passwdConf)){
            this.mdpError.setText("The entered passwords do not match !");
            i++;
        }
        
        if(service.unicUsername(username)!=0){
            this.mdpError.setText("Username already exist !");
            i++;
        }
        
       if(j==-2){
           this.mdpError.setText("Your password is weak");
           i++;
       }
       
       if(pic==null){
           String dir=System.getProperty("user.dir");
           pic=dir+"\\src\\images\\R.jpg";
       }
           
        
            
        
        //////////////////////////////////////////////////////////////////////////
        
        
        if(i==0){
        nouveau.setAge(Integer.parseInt(age));
        nouveau.setCIN(cin);
        nouveau.setDateNaissance(dN);
        nouveau.setNom(name);
        nouveau.setPrenom(lastname);
        nouveau.setUserName(username);
        nouveau.setPassword(passwd);
        nouveau.setPic(pic);
        nouveau.setEmail(email);
        nouveau.setType(Type.valueOf(type));
        
            
        String code = MailValidation.generateVerificationCode();
        String message=MailValidation.newAccountEmailVerif(nouveau, code);
         MailValidation.sendVerificationCode(email,"Inscription Code",message); 
        
        
        try {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MailVerification/MailVerificationCode.fxml"));
        Parent root = loader.load();
        MailVerificationCodeController mvcc=loader.getController();
        mvcc.setCode(code);
        mvcc.setUtilisateur(nouveau);
        
        Stage cStage=(Stage) entryEmail.getScene().getWindow();
        cStage.setWidth(540);
        cStage.setHeight(280);
        entryAge.getScene().setRoot(root);
        
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            
        
        }
        
    }

    @FXML
    private void onUpload(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f=chooser.getSelectedFile();
         picPath= f.getAbsolutePath();
        
         
        
        
    }

    @FXML
    private void dateArea(ActionEvent event) {
        String datN=entryBirthday.getValue().toString();
        try {
            entryAge.setText(this.service.calculeAge(datN));
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
       
    }

    @FXML
    private void onCancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../SignIn/SignIn.fxml"));
            Parent root = loader.load();
            SignInController ic=loader.getController();
            Stage cStage= (Stage) this.cancel.getScene().getWindow();
            cStage.setWidth(720);
            cStage.setHeight(520);
            cancel.getScene().setRoot(root);
            
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }

    @FXML
    private void onPasswordOne(KeyEvent event) {
        j=50;
        String passwd =this.entryPasswordOne.getText();
        switch (service.passwordStrength(passwd)){
            case 0:{
                
                this.passowrdStrenght.setText("Strong");
                j=0;
            }
            break;
            case -1:{
                
                this.passowrdStrenght.setText("Medium");
                j=-1;
            }
            break;
            case -2:{
                
                this.passowrdStrenght.setText("Weak");
                j=-2;
            }
            break;
        }
        
        
    }
    
}
