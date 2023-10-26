/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import commentaires.commentaire;
import commentaires.commentaireServices;
import Connection.MyConnection;
import formations.formation;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ghmougui ons
 */
public class CommentaireGuiController implements Initializable {

    @FXML
    private Button ajoutC;
    @FXML
    private Button modifC;
    @FXML
    private Button suppC;
    @FXML
    private TextField tfcommentaire;
    @FXML
    private TableView<commentaire> tableCommentaire;
    MyConnection conx= MyConnection.getInstance();
    Connection myConx=conx.getConnection();
  
    @FXML
    private TableColumn<commentaire, Long> idcol;
    @FXML
    private TableColumn<commentaire, String> textcol;
    @FXML
    private TableColumn<commentaire, LocalDate> datecol;
    @FXML
    private TableColumn<commentaire, Integer> evalcol;
    @FXML
    private Spinner<Integer> EvalSpinner;
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
    @FXML
    private Button publierbtn;
    
    
     private long formationId;
    @FXML
    private Button formation;

    public void setFormationId(long formationId) {
        this.formationId = formationId;
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       EvalSpinner.setValueFactory(valueFactory);
       afficherCom();
      
       
        
    }    

    @FXML
    private void ajouterC(ActionEvent event) {
        
        
      
        
        int selectedValue = EvalSpinner.getValue();
        String textFieldValue = tfcommentaire.getText();
        LocalDate localDate = LocalDate.now();
        if (!tfcommentaire.getText().isEmpty()||(tfcommentaire.getText().length()>1))
        {
           if (!textFieldValue.isEmpty() && textFieldValue.length() > 1) {
        String[] BadWords = {"merde", "shit", "israel", "mort", "bordel", "religion"};
        String lowerInput = textFieldValue.toLowerCase();
        boolean containsBadWords = false;

        for (String word : BadWords) {
            if (lowerInput.contains(word)) {
                // Replace bad words with asterisks
                textFieldValue = textFieldValue.replaceAll("(?i)" + word, "*****");
                containsBadWords = true;
            }
        }

        if (containsBadWords) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("BE RESPECTFUL");
            alert.showAndWait();
        }
    }
        }
     
            commentaire c = new commentaire(5,textFieldValue, localDate, selectedValue);
            commentaireServices cs = new commentaireServices();
            
            
            cs.ajouter(c);
            
            afficherCom();
            // Après avoir ajouté l'évaluation, calculez la moyenne et mettez à jour le label
            /*double moyenne = calculerMoyenneEvaluations();
            moyenneEval.setText(String.format("%.2f", moyenne)); // Affiche la moyenne avec deux décimales*/
       
        
    }

private void showInformationAlert(String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

private void showErrorAlert(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}



 
  void afficherCom(){


 String query = "SELECT * FROM commentaire";
                    
       
            PreparedStatement prepStat;
        try {
            prepStat = myConx.prepareStatement(query);
       
            ResultSet resultSet = prepStat.executeQuery(query);
            ObservableList<commentaire> data = FXCollections.observableArrayList();

             
             
             while (resultSet.next()) {
                Long idUser = resultSet.getLong("iduser");
                String text = resultSet.getString("text");
                LocalDate date = resultSet.getDate("date").toLocalDate();
               int evaluation= resultSet.getInt("evaluation");
               
                commentaire com = new commentaire(idUser, text, date,evaluation);
                data.add(com);
            }
        
      

        // Set the cell value factories for each column
        idcol.setCellValueFactory(new PropertyValueFactory<>("iduser"));
        textcol.setCellValueFactory(new PropertyValueFactory<>("text"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("date"));
        evalcol.setCellValueFactory(new PropertyValueFactory<>("evaluation"));
        tableCommentaire.setItems(data);


        
        
        
         } catch (SQLException ex) {
            Logger.getLogger(CommentaireGuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}




   
    @FXML
    private void modifierC(MouseEvent event) {
         commentaire c2 = tableCommentaire.getSelectionModel().getSelectedItem();

    if (c2 != null) {
        int eval = this.EvalSpinner.getValue();
        String com = this.tfcommentaire.getText();
        LocalDate localDate = LocalDate.now();

        // Create a new commentaire object and set its attributes
        commentaire nouveau = new commentaire(com,eval);
        nouveau.setText(com);
        nouveau.setEvaluation(eval);

        // Call the modifier method from commentaireServices
        commentaireServices cs = new commentaireServices();
        cs.modifier(c2,nouveau);

        // Show a success alert
        showInformationAlert("Commentaire updated successfully.");
    } else {
        // Show an error alert
        showErrorAlert("No item selected in tableCommentaire.");
    }
    afficherCom();
    }

    @FXML    
    private void supprimerC(ActionEvent event) {
    commentaire c = tableCommentaire.getSelectionModel().getSelectedItem();

    if (c != null) {
        // Create a confirmation dialog
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this commentaire?");
        confirmationAlert.setContentText("Click OK to delete the commentaire or Cancel to keep it.");

        // Show the confirmation dialog and wait for the user's response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK, proceed with the deletion
            ObservableList<commentaire> data = tableCommentaire.getItems();
            commentaireServices cS = new commentaireServices();
            int Result = cS.supprimer(c); // Modify this line to match your service method
            System.out.println(Result);

            if (Result > 0) {
                // Delete successful, remove the commentaire from the table and refresh
                data.remove(c);
             
           
        }
    }
    }
       afficherCom();
    }
    /*public boolean BadWordCheck(String input) {
  
    String[] BadWords = {"merde", "shit", "israel", "mort", "bordel", "religion"};


    String lowerInput = input.toLowerCase();

    for (String word : BadWords) {
        if (lowerInput.contains(word.toLowerCase())) {
            return true; 
        }
    }

    return false; 
}

    private String replaceBadWordsWithAsterisks(String input) {
    String[] BadWords = {"merde", "shit", "israel", "mort", "bordel", "religion"};
    String lowerInput = input.toLowerCase();

    for (String word : BadWords) {
        if (lowerInput.contains(word.toLowerCase())) {
            // Replace bad words with asterisks
            input = input.replaceAll("(?i)" + word, "*****");
        }
    }

    return input;
}*/

    @FXML
    private void Publier(ActionEvent event) {
        String commentaireText = tfcommentaire.getText();
        // Vérifiez si le texte du commentaire n'est pas vide
    if (commentaireText != null && !commentaireText.isEmpty()) {
        // Utilisez le texte du commentaire pour créer un objet de commentaire
        commentaire com = new commentaire();
        com.setText(commentaireText);
        FacebookClient facebookClient = new DefaultFacebookClient("EAADztaDMZA8wBO8FYrZAMzPr3cRQyuMFQYsx81MSycB3dxlCLZBa1DOHEoBqq2UfJ581BxLaKKWkoYx9MdNaIY9YggPDh7AQKpCjmXOKUD5q6TRD3oqZC8TccBHfTfsJ44D1ehmHJqf0xGmnY7Ymy1K3Dyqp0UJnQPnMTCe3HipjqAZAlO7NePC5cMkDas0UZD", Version.LATEST);
        try {
            // Remplacez "ID_DE_LA_PAGE" par l'identifiant de la page Facebook sur laquelle vous souhaitez publier le commentaire
            String pageId = "109919471909965";

            // Publiez le commentaire sur la page
            facebookClient.publish(pageId + "/feed", FacebookType.class, Parameter.with("message", com.getText()));

            // Le commentaire a été publié avec succès
            System.out.println("Commentaire publié avec succès.");
        } catch (FacebookException e) {
            // Gérez les erreurs, par exemple, si le jeton d'accès est expiré ou s'il y a une erreur de publication
            System.err.println("Erreur lors de la publication du commentaire : " + e.getMessage());
        }
    } else {
        // Affichez un message d'erreur si le champ de commentaire est vide
        System.err.println("Le champ de commentaire est vide.");
    }
    }
    
    
    /*public double calculerMoyenneEvaluations() {
    double sommeEvaluations = 0.0;
    int nombreEvaluations = 0;

    // Parcourez les évaluations et calculez la somme des évaluations et le nombre d'évaluations
    for (commentaire com : tableCommentaire.getItems()) {
        sommeEvaluations += com.getEvaluation();
        nombreEvaluations++;
    }

    // Calcul de la moyenne
    if (nombreEvaluations > 0) {
        return sommeEvaluations / nombreEvaluations;
    } else {
        return 0.0; // Aucune évaluation n'a été ajoutée
    }
}*/

    @FXML
    private void formation(ActionEvent event) {
    }
}