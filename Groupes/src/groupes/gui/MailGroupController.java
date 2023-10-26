/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package groupes.gui;

import Utilisateur.Utilisateur;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.sun.scenario.Settings;
import groupes.services.GroupFunctions;
import groupes.services.MiscFunctions;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author hammeda
 */
public class MailGroupController implements Initializable {
Utilisateur current;
     public void setUtilisateur(Utilisateur current){
        this.current=current;
    }
    @FXML
    private JFXButton sendMbtn;
    @FXML
    private TextArea MailMessage= new JFXTextArea();
    @FXML
    private TextField SubjectText= new TextField();
    @FXML
    private ImageView Sigimg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void SendMail(MouseEvent event) {
        if((MailMessage.getText().isEmpty())||(SubjectText.getText().isEmpty()))
        {}
        MiscFunctions MF=new MiscFunctions();
        MF.SendMail(SubjectText.getText(), MailMessage.getText());

    
    }

    @FXML
    private void Signature(MouseEvent event) {
        GroupFunctions GF= new GroupFunctions();
       try {
        SubjectText.setText("Subject: Important Update from "+GF.getOwnerName(GF.GetSelectedGID())+" , Owner of "+GF.getGroupName());
        
           MailMessage.setText("I hope this message finds you well. As the owner of "+GF.getGroupName()+"\nI wanted to share some important updates and reminders with all of you.\n");
        } catch (SQLException ex) {
            ex.getMessage();
        }
        
        
        
        
    }
    
    



}
