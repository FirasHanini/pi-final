/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ghmougui ons
 */
public class AddFormationGuiController implements Initializable {

    @FXML
    private TextField tfTitre;
    
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfRemise;
    @FXML
    private TextField tfDuree;
    @FXML
    private TextField tfDescription;
    @FXML
    private Button btnSauvegarder;
    @FXML
    private Button btnUpload;
    @FXML
    private MediaView mediaView;
    @FXML
    private ComboBox<String> combocateg;
    ObservableList<String> List = FXCollections.observableArrayList("Poterie","Tapiserie","Cuisine","Bijoux");
    @FXML
    private Button btnPublier;
    MyConnection conx= MyConnection.getInstance();
    Connection myConx=conx.getConnection();
    
    
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        combocateg.setItems(List);
    }    

    

    @FXML
    private void UplderVd(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers MP4", "*.mp4"));
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
        String selectedFilePath = selectedFile.toURI().toString();
        selectedFilePath = selectedFilePath.replace(" ", "%20"); // Remplace les espaces par %20
        Media media = new Media(selectedFilePath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Associez le MediaPlayer au MediaView pour afficher la vidéo.
        mediaView.setMediaPlayer(mediaPlayer);

        // Jouez la vidéo (vous pouvez ajouter des contrôles de lecture/arrêt/pause si nécessaire).
        mediaPlayer.play();
    }
    }

    @FXML
    
    private void SavePerson(MouseEvent event) {
        try {
            String titre = tfTitre.getText();
            String categorie = combocateg.getValue();
            double prix =Double.parseDouble(tfPrix.getText());
            float remise =Float.parseFloat(tfRemise.getText());
            String duree = tfDuree.getText();
            String description = tfDescription.getText();
            String video ;
            Media media = mediaView.getMediaPlayer().getMedia();
            String mediaSource = media.getSource();
            
            
            
            formationServices fs = new formationServices();
            if (fs.unicFormation(titre) == -1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Formation Title Not Unique");
            alert.setContentText("A Formation with the same name already exists. Please choose a different name.");
            alert.showAndWait();}
            else{
            formation f= new formation(titre, categorie, prix, remise, duree, description,mediaSource) ;
            fs.ajouter(f);}
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormationGui.fxml"));
            Parent root = loader.load();
            FormationGuiController GF = loader.getController();

        // Configurez la nouvelle interface en tant que racine de la scène
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.sizeToScene(); // Redimensionne la scène en fonction de son contenu
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
    
    
    
    
   
    public void remplirChamps(formation formation) {
        // Remplissez les champs avec les informations de la formation
        tfTitre.setText(formation.getTitre());
        combocateg.setValue(formation.getCategories());
        tfPrix.setText(String.valueOf(formation.getPrix()));
        tfRemise.setText(String.valueOf(formation.getRemise()));
        tfDuree.setText(formation.getDuree());
        tfDescription.setText(formation.getDescription());
        String video = formation.getVideo();
        Media media = new Media(video);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Associez le MediaPlayer à la MediaView pour l'affichage de la vidéo
        mediaView.setMediaPlayer(mediaPlayer);
         mediaPlayer.play();
        // Remplissez d'autres champs en conséquence
    }
    

    /*private void Modifier(MouseEvent event) throws SQLException, IOException {
      formationServices fs = new formationServices();
      
       
        
       String titre = tfTitre.getText();
            String categorie = combocateg.getValue();
            double prix =Double.parseDouble(tfPrix.getText());
            float remise =Float.parseFloat(tfRemise.getText());
            String duree = tfDuree.getText();
            String description = tfDescription.getText();
            String video ;
            Media media = mediaView.getMediaPlayer().getMedia();
            String mediaSource = media.getSource();
            formation f = new formation(titre, categorie, prix, remise, duree, description, mediaSource);
            formation nouveau = new formation(titre, categorie, prix, remise, duree, description, mediaSource);
            nouveau.setTitre(titre);
            nouveau.setCategories(categorie);
            nouveau.setPrix(prix);
            nouveau.setRemise(remise);
            nouveau.setDuree(duree);
            nouveau.setDescription(description);
            nouveau.setVideo(mediaSource);
            fs.modifier(f, nouveau);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormationGui.fxml"));
            Parent root = loader.load();
            FormationGuiController GF = loader.getController();

        // Configurez la nouvelle interface en tant que racine de la scène
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.sizeToScene(); // Redimensionne la scène en fonction de son contenu
    }*/

    @FXML
    private void publier(ActionEvent event) {
     
      
      
        String accessToken = "EAADztaDMZA8wBO8FYrZAMzPr3cRQyuMFQYsx81MSycB3dxlCLZBa1DOHEoBqq2UfJ581BxLaKKWkoYx9MdNaIY9YggPDh7AQKpCjmXOKUD5q6TRD3oqZC8TccBHfTfsJ44D1ehmHJqf0xGmnY7Ymy1K3Dyqp0UJnQPnMTCe3HipjqAZAlO7NePC5cMkDas0UZD";

        // Remplacez "PAGE_ID" par l'ID de la page sur laquelle vous souhaitez publier la vidéo.
        String pageId = "109919471909965";

        // Le texte de la publication sera le titre de la formation.
        String message = tfTitre.getText();

        // Le chemin vers la vidéo que vous souhaitez publier.
        String videoFilePath = mediaView.getMediaPlayer().getMedia().getSource();

        try {
            // Créez un client Facebook avec le jeton d'accès.
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);

            // Étape 1 : Téléchargez la vidéo sur Facebook et obtenez l'ID de la vidéo.
            String videoId = telechargerVideoSurFacebook(facebookClient, videoFilePath);

            if (videoId != null) {
                // Étape 2 : Créez la publication avec la vidéo téléchargée sur la page spécifiée.
                boolean publicationReussie = publierVideoSurPageFacebook(facebookClient, pageId, videoId, message);

                if (publicationReussie) {
                    System.out.println("La vidéo a été publiée avec succès sur la page Facebook !");
                } else {
                    System.err.println("Échec de la publication de la vidéo sur la page Facebook.");
                }
            } else {
                System.err.println("Échec du téléchargement de la vidéo sur Facebook.");
            }
        } catch (Exception e) {
            System.err.println("Une erreur s'est produite : " + e.getMessage());
        }
    }

    private String telechargerVideoSurFacebook(FacebookClient client, String videoFilePath) {
        // Téléchargez la vidéo sur Facebook et retournez l'ID de la vidéo.
        FacebookType response = client.publish("me/videos", FacebookType.class,
                Parameter.with("source", new File(videoFilePath)));

        return response.getId();
    }

    private boolean publierVideoSurPageFacebook(FacebookClient client, String pageId, String videoId, String message) {
        // Créez la publication avec la vidéo téléchargée sur la page spécifiée.
        FacebookType response = client.publish(pageId + "/feed", FacebookType.class,
                Parameter.with("message", message),
                Parameter.with("attached_media", "[{'media_fbid':'" + videoId + "'}]"));

        return response.getId() != null;
    }

    }

   

            
             

    
    

    

