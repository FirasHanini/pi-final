/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package groupes.gui;

import Utilisateur.Utilisateur;
import com.jfoenix.controls.JFXButton;

import groupes.services.ChatFunctions;
import groupes.services.GroupFunctions;
import gui.homePage.HomPageController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hammeda
 */

public class ConversationController implements Initializable {
long idOwner;
Utilisateur current;
     public void setUtilisateur(Utilisateur current){
        this.current=current;
         idOwner=this.current.getId();
    }
    GroupFunctions GF=new GroupFunctions();
ChatFunctions ChatF=new ChatFunctions();
    @FXML
    private TableView<Utilisateur> GTable;
    @FXML
    private TableColumn<Utilisateur, String> TName;
    @FXML
    private TableColumn<Utilisateur, String> TPrenom;
    @FXML
    private TableColumn<Utilisateur, String> TEmail;
    @FXML
    private JFXButton OpenConvbtn;
    @FXML
    private ImageView ReturnImg;
    @FXML
    private Text GGNameText;

    /**
     * Initializes the controller class.
     */
 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
ChatF.ShowUsersG(GTable,TName,TPrenom,TEmail);

    }





    
    

    @FXML
    private void OpenConv(MouseEvent event) throws SQLException {
        Utilisateur selectedUser = GTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedUser.getId());       
        ChatFunctions CF=new ChatFunctions();
        CF.GetEmailTotalk(selectedUser.getEmail());
       
        
         try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
            Parent root = loader.load();
            
            
               ChatController controllerGroupe =loader.getController();
              controllerGroupe.setUtilisateur(current);
              controllerGroupe.setEmail(selectedUser.getEmail());
              
            Stage cStage= (Stage) this.OpenConvbtn.getScene().getWindow();
            cStage.setWidth(720);
            cStage.setHeight(720);
              
            OpenConvbtn.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        
        
        
        
        
        
        
    }

    @FXML
    private void Return(MouseEvent event) {
     
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../gui/homePage/homPage.fxml"));
            Parent root = loader.load();
            
            
               HomPageController controllerGroupe =loader.getController();
              controllerGroupe.setUtilisateur(current);
              
            Stage cStage= (Stage) this.GTable.getScene().getWindow();
            cStage.setWidth(720);
            cStage.setHeight(720);
              
            GTable.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }




    }    
    




