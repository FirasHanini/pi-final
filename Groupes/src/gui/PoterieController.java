/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Utilisateur.Utilisateur;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import product.product;
import product.productService;

/**
 * FXML Controller class
 *
 * @author User
 */
public class PoterieController implements Initializable {
    Utilisateur current;
     public void setUtilisateur(Utilisateur current){
        this.current=current;
        
    }
    @FXML
    private AnchorPane AnchorOrder;
    @FXML
    private GridPane menu_gridPane;
    @FXML
    private Button poterie;
    @FXML
    private Button tapisserie;
    @FXML
    private Button bijoux;
    @FXML
    private Button cuisine;

private ObservableList<product> cardListData = FXCollections.observableArrayList();
      productService pS=new productService();
    @FXML
    private Button home;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             try {
            menuDisplayCard();
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BoutiqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
    }    
    public void menuDisplayCard() throws SQLException, IOException {
    int column = 0;
    int row = 0;
    String categ = "Poterie";
    cardListData.clear();
    cardListData.addAll(pS.getproduitByCategorie(categ));
   

    for (product p : cardListData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cardProduct.fxml"));
            AnchorPane pane = loader.load();
            CardProductController CPC = loader.getController();

            // Assuming getId_pdts() is the method to retrieve the product ID
 CPC.setData(p.getId_pdts());
            if (column == 3) {
                column = 0;
                row += 1;
                
            }
            menu_gridPane.add(pane, column++, row);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
    @FXML
    private void poterie(MouseEvent event) {
           try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("poterie.fxml"));

                Parent root = loader.load();
                PoterieController pG=loader.getController();
            //btnAjouter.getScene().setRoot(root);
                Stage newStage = new Stage();
                newStage.setTitle("Poterie");

        // Set the scene for the new stage
                Scene scene = new Scene(root);
                newStage.setScene(scene);

        // Show the new stage
                newStage.show();
            
        }catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    @FXML
    private void bijoux(ActionEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bijoux.fxml"));

                Parent root = loader.load();
                BijouxController pG=loader.getController();
            //btnAjouter.getScene().setRoot(root);
                Stage newStage = new Stage();
                newStage.setTitle("Bijoux");

        // Set the scene for the new stage
                Scene scene = new Scene(root);
                newStage.setScene(scene);

        // Show the new stage
                newStage.show();
            
        }catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }

    @FXML
    private void cuisine(ActionEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("cuisine.fxml"));

                Parent root = loader.load();
                CuisineController pG=loader.getController();
            //btnAjouter.getScene().setRoot(root);
                Stage newStage = new Stage();
                newStage.setTitle("Cuisine");

        // Set the scene for the new stage
                Scene scene = new Scene(root);
                newStage.setScene(scene);

        // Show the new stage
                newStage.show();
            
        }catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }

   
    @FXML
    private void tapisserie(ActionEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("tapisserie.fxml"));

                Parent root = loader.load();
                TapisserieController pG=loader.getController();
            //btnAjouter.getScene().setRoot(root);
                Stage newStage = new Stage();

        // Set the scene for the new stage
                Scene scene = new Scene(root);
                newStage.setScene(scene);

        // Show the new stage
                newStage.show();
            
        }catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }

    @FXML
    private void home(ActionEvent event) {
             try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("boutique.fxml"));
            Parent root = loader.load();
            
            
              BoutiqueController controllerEvent=loader.getController();
            controllerEvent.setUtilisateur(current);

            Stage cStage= (Stage) this.home.getScene().getWindow();
            cStage.setWidth(1200);
            cStage.setHeight(840);
              
            home.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    }

