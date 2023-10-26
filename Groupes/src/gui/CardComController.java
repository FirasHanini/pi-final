/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Connection.MyConnection;
import formations.formation;
import formations.formationServices;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ghmougui ons
 */
public class CardComController implements Initializable {

    @FXML
    private Label titreformation;
    @FXML
    private Label prixformation;
    @FXML
    private Button reserverbtn;
    @FXML
    private Button commenterbtn;
    @FXML
    private MediaView mediaView;
    formationServices fs=new formationServices();
    @FXML
    private Label moyenneEval;
    private long formationId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void reserver(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReservationFormGui.fxml"));
        Parent root = loader.load();
        ReservationFormGuiController rG = loader.getController();

        // Pass the price value to the ReservationForGuiController
        double price = Double.parseDouble(prixformation.getText());
        rG.setTotalAmount(price);
        
        String formationTitle = titreformation.getText();
        rG.setFormationTitle(formationTitle);

        Stage newStage = new Stage();

        // Set the scene for the new stage
        Scene scene = new Scene(root);
        newStage.setScene(scene);

        // Show the new stage
        newStage.show();

    } catch (IOException ex) {
        System.out.println("Error: " + ex.getMessage());
    }
       
    }

    @FXML
    private void commenter(ActionEvent event) {
        try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentaireGui.fxml"));

            Parent root = loader.load();
            CommentaireGuiController cG=loader.getController();
            cG.setFormationId(formationId);
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
    
    
    public void setData(long id ) throws SQLException{
        //this.formationId = id; // Stockez l'ID de la formation

    formation f = getFormationByID(id);
    titreformation.setText(f.getTitre());
    prixformation.setText(String.valueOf(f.getPrix()));
   
    
    Media media = new Media(f.getVideo());
    MediaPlayer mediaPlayer = new MediaPlayer(media);

    // Assuming Pvideo is the name of your VideoView or MediaView
    mediaView.setMediaPlayer(mediaPlayer);

    // Play the video (you can add play/stop/pause controls if needed).
    mediaPlayer.play();
}
    
    
    
public formation getFormationByID(long id) throws SQLException {
    MyConnection conx = MyConnection.getInstance();
    Connection myConx = conx.getConnection();
    String req = "SELECT * FROM formation WHERE id = ?";

    PreparedStatement prepStat = myConx.prepareStatement(req);
    prepStat.setLong(1, id);

    ResultSet resultSet = prepStat.executeQuery();

    formation formationResult = null;

    if (resultSet.next()) {
        String titre = resultSet.getString("titre");
        String categories = resultSet.getString("categories");
        double prix = resultSet.getDouble("prix");
        float remise = resultSet.getFloat("remise");
        String duree = resultSet.getString("duree");
        String description = resultSet.getString("description");
        String video = resultSet.getString("video");

        formationResult = new formation(titre, categories, prix, remise, duree, description, video);
    }

    resultSet.close();
    prepStat.close();
    return formationResult;
}

/*public void setCat(long formationId) throws SQLException {
    // Check if the formationId is valid (greater than 0)
    if (formationId > 0) {
        // Utilize the getFormationByID method to obtain the corresponding formation using its ID
        formation f = getFormationByID(formationId);

        if (f != null) {
            titreformation.setText(f.getTitre());
            prixformation.setText(String.valueOf(f.getPrix()));

            Media media = new Media(f.getVideo());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            // Assuming mediaView is the name of your MediaView
            mediaView.setMediaPlayer(mediaPlayer);

            // Play the video (you can add play/stop/pause controls if needed)
            mediaPlayer.play();
        } else {
            // Handle the case where the formation was not found
            // You might want to display an error message or take other appropriate action.
            System.out.println("Formation not found for ID: " + formationId);
        }
    } else {
        // Handle the case of an invalid formationId (e.g., display an error message)
        System.out.println("Invalid formation ID: " + formationId);
    }
}*/


 /*public void setCat(String categ) throws SQLException {
    List<formation> formations = fs.getformationsByCategorie(categ);

    // Check if there are formations in the list
    if (!formations.isEmpty()) {
        // For example, let's display the first formation from the list
        formation f = formations.get(0);

        titreformation.setText(f.getTitre());
        prixformation.setText(String.valueOf(f.getPrix()));

        Media media = new Media(f.getVideo());
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Assuming mediaView is the name of your MediaView
        mediaView.setMediaPlayer(mediaPlayer);

        // Play the video (you can add play/stop/pause controls if needed)
        mediaPlayer.play();
    }
}*/

    /*public List<formation> getformationsByCategorie(String categ) throws SQLException {
    MyConnection conx = MyConnection.getInstance();
    Connection myConx = conx.getConnection();
    String req = "SELECT * FROM formation WHERE categories = ?";

    PreparedStatement prepStat = myConx.prepareStatement(req);
    prepStat.setString(1, categ);

    ResultSet resultSet = prepStat.executeQuery();

    List<formation> formations = new ArrayList<>();

    while (resultSet.next()) {
        String titre = resultSet.getString("titre");
        String categories = resultSet.getString("categories");
        double prix = resultSet.getDouble("prix");
        float remise = resultSet.getFloat("remise");
        String duree = resultSet.getString("duree");
        String description = resultSet.getString("description");
        String video = resultSet.getString("video");

        formation formResult = new formation(titre, categories, prix, remise, duree, description, video);
        formations.add(formResult);
    }

    // Close the result set and the prepared statement
    resultSet.close();
    prepStat.close();

    return formations;
}*/
    

    
}
