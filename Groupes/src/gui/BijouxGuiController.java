/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Connection.MyConnection;
import formations.formation;
import formations.formationServices;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

/**
 * FXML Controller class
 *
 * @author ghmougui ons
 */
public class BijouxGuiController implements Initializable {

    @FXML
    private AnchorPane AnchorOrder;
    @FXML
    private GridPane menu_gridPane;
    @FXML
    private Button poteriebtn;
    @FXML
    private Button tapisseriebtn;
    @FXML
    private Button bijouxbtn;
    @FXML
    private Button cuisinebtn;
    private ObservableList<formation> cardListData=FXCollections.observableArrayList();
    formationServices fs=new formationServices();
    @FXML
    private Button home;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //refreshOrder();
        try {
            menuDisplayCard();
        } catch (SQLException ex) {
            Logger.getLogger(BijouxGuiController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BijouxGuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
  public void menuDisplayCard() throws SQLException, IOException {
    int column = 0;
    int row = 0;
    String categ = "bijoux";
    cardListData.clear();
    cardListData.addAll(fs.getformationsByCategorie(categ));

    for (formation f : cardListData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cardCom.fxml"));
            AnchorPane pane = loader.load();
            CardComController CCC = loader.getController();

            // Assuming getId_pdts() is the method to retrieve the product ID
            CCC.setData(f.getId());
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
    /*public formation getformationByCategorie(String categ) throws SQLException{
         MyConnection conx= MyConnection.getInstance();
        Connection myConx=conx.getConnection();
        String req="SELECT * FROM formation WHERE categories= ?";
    
    
        PreparedStatement prepStat = myConx.prepareStatement(req);
         prepStat.setString(1, categ);
         
        ResultSet resultSet = prepStat.executeQuery();

    formation formResult = null;

    if (resultSet.next()) {
  
        String titre = resultSet.getString("titre");
        String categories = resultSet.getString("categories");
        double prix = resultSet.getDouble("prix");
        float remise = resultSet.getFloat("remise");
        String duree = resultSet.getString("duree");
        String description = resultSet.getString("description");
        String video = resultSet.getString("video");
  
    formResult = new formation(titre, categories, prix, remise, duree, description, video);
}

// Close the result set and the prepared statement
resultSet.close();
prepStat.close();

return formResult;
    
    
    }*/
    
    
    
    
   
    
    
    

    private void poterie(ActionEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("PoterieGui.fxml"));

                Parent root = loader.load();
                PoterieGuiController pG=loader.getController();
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
        /*try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("BijouxGui.fxml"));

                Parent root = loader.load();
                PoterieGuiController pG=loader.getController();
            //btnAjouter.getScene().setRoot(root);
                Stage newStage = new Stage();

        // Set the scene for the new stage
                Scene scene = new Scene(root);
                newStage.setScene(scene);

        // Show the new stage
                newStage.show();
            
        }catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }*/
    }

    @FXML
    private void cuisine(ActionEvent event) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CuisineGui.fxml"));

                Parent root = loader.load();
                CuisineGuiController pG=loader.getController();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("TapisserieGui.fxml"));

                Parent root = loader.load();
                TapisserieGuiController pG=loader.getController();
            //btnAjouter.getScene().setRoot(root);
                Stage newStage = new Stage();
                newStage.setTitle("Tapisserie");

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
    private void poterie(MouseEvent event) {
    }

    @FXML
    private void home(ActionEvent event) {
         try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AffichageFormationGui.fxml"));

                Parent root = loader.load();
                AffichageFormationGuiController aFG=loader.getController();
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
    
}
