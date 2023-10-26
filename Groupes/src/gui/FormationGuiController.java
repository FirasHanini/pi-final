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
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.LongStringConverter;


/**
 * FXML Controller class
 *
 * @author ghmougui ons
 */
public class FormationGuiController implements Initializable { 
    
    formation Selecteditem;
    @FXML
    private Button menubtn;

    public formation getSelecteditem() {
        return Selecteditem;
    }

    public void setSelecteditem(formation Selecteditem) {
        this.Selecteditem = Selecteditem;
    }
    
    MyConnection conx= MyConnection.getInstance();
    Connection myConx=conx.getConnection();
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnAjouter;
    @FXML
    private TableColumn<formation, Long> idcolumn;
    @FXML
    private TableColumn<formation,String> titrecolumn;
    @FXML
    private TableColumn<formation,String> categoriecolumn;
    @FXML
    private TableColumn<formation,Double> prixcolumn;
    @FXML
    private TableColumn<formation,Float> remisecolumn;
    @FXML
    private TableColumn<formation,String> dureecolumn;
    @FXML
    private TableColumn<formation,String> descriptioncolumn;
    
    @FXML
    private TableView<formation> tableFormation;
@FXML
    private TextField tfsearch;
    @FXML
    private TableColumn<formation,String> videocolumn;
    formationServices fs= new formationServices();
    
   
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idcolumn.setCellValueFactory(new PropertyValueFactory<formation,Long>("id"));
        titrecolumn.setCellValueFactory(new PropertyValueFactory<formation,String>("titre"));
        categoriecolumn.setCellValueFactory(new PropertyValueFactory<formation,String>("categories"));
        prixcolumn.setCellValueFactory(new PropertyValueFactory<formation,Double>("prix"));
        remisecolumn.setCellValueFactory(new PropertyValueFactory<formation,Float>("remise"));
        dureecolumn.setCellValueFactory(new PropertyValueFactory<formation,String>("duree"));
        descriptioncolumn.setCellValueFactory(new PropertyValueFactory<formation,String>("description"));
        videocolumn.setCellValueFactory(new PropertyValueFactory<formation,String>("video"));
        

        formationServices fS= new formationServices();
        ObservableList <formation> formationList=FXCollections.observableArrayList(fS.retournerTout());
  
        
        idcolumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
            titrecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
            categoriecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
            prixcolumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            remisecolumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
            dureecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
            descriptioncolumn.setCellFactory(TextFieldTableCell.forTableColumn());
            videocolumn.setCellFactory(TextFieldTableCell.forTableColumn());

            tableFormation.setItems(formationList);
        //String path = new File("Source")
         FilteredList<formation> filteredData = new FilteredList<>(formationList, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		tfsearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(formation -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (formation.getTitre().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (formation.getCategories().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				else if (String.valueOf(formation.getPrix()).indexOf(lowerCaseFilter)!=-1){
				     return true;
                                }else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<formation> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableFormation.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tableFormation.setItems(sortedData);
            
    
       
    }

private void afficher (TableColumn<formation, Long> idcolumn,TableColumn<formation,String> titrecolumn,TableColumn<formation,String> categoriecolumn,TableColumn<formation,Double> prixcolumn,TableColumn<formation,Float> remisecolumn,TableColumn<formation,String> dureecolumn,TableColumn<formation,String> descriptioncolumn,TableColumn<formation,String> videocolumn){
}
    @FXML
    private void Ajout(ActionEvent event) {
           try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFormationGui.fxml"));

                Parent root = loader.load();
                AddFormationGuiController aGF=loader.getController();
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
    private void delete(ActionEvent event) {
         formation f =tableFormation.getSelectionModel().getSelectedItem();
        if (f!= null){
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Are you sure you want to remove this product?");
            confirmationAlert.setContentText("Click OK to remove the product or Cancel to keep it.");

            // Show the confirmation dialog and wait for the user's response
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

    
            formationServices fS= new formationServices();
            int deleteR = fS.supprimer(f);
            System.out.println(deleteR);
           
            
            
         idcolumn.setCellValueFactory(new PropertyValueFactory<formation,Long>("id"));
        titrecolumn.setCellValueFactory(new PropertyValueFactory<formation,String>("titre"));
        categoriecolumn.setCellValueFactory(new PropertyValueFactory<formation,String>("categories"));
        prixcolumn.setCellValueFactory(new PropertyValueFactory<formation,Double>("prix"));
        remisecolumn.setCellValueFactory(new PropertyValueFactory<formation,Float>("remise"));
        dureecolumn.setCellValueFactory(new PropertyValueFactory<formation,String>("duree"));
        descriptioncolumn.setCellValueFactory(new PropertyValueFactory<formation,String>("description"));
        videocolumn.setCellValueFactory(new PropertyValueFactory<formation,String>("video"));

    
        ObservableList <formation> formationList=FXCollections.observableArrayList(fS.retournerTout());
        tableFormation.setItems(formationList);
            }
        
        
        
    }
    }

    @FXML
    private void modif(ActionEvent event) throws SQLException {
        formation formationSelectionnee = tableFormation.getSelectionModel().getSelectedItem();

    if (formationSelectionnee != null) {
        try {
           
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFormationGui.fxml"));
            Parent root = loader.load();
            AddFormationGuiController addFormationController = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Maintenant, appelez la méthode sur l'instance du contrôleur
            addFormationController.remplirChamps(formationSelectionnee);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        setSelecteditem(formationSelectionnee);
     
    
    }
    






       /* formation selectedFormation = tableFormation.getSelectionModel().getSelectedItem();
       // Assuming you have a FXMLLoader set up for loading the "addformationgui.fxml" file
FXMLLoader loader = new FXMLLoader(getClass().getResource("AddFormationGui.fxml"));
Parent root = loader.load();

// Access the controller of the new interface
AddFormationGuiController controller = loader.getController();

// Fill the fields in the new interface using the selectedFormation variable
controller.tfTitre.setText(selectedFormation.getTitre());
controller.tfCategorie.setText(selectedFormation.getCategorie());
controller.tfPrix.setText(String.valueOf(selectedFormation.getPrix()));
controller.tfRemise.setText(String.valueOf(selectedFormation.getRemise()));
controller.tfDuree.setText(selectedFormation.getDuree());
controller.tfDescription.setText(selectedFormation.getDescription());

// Show the new interface
Stage stage = new Stage();
Scene scene = new Scene(root);
stage.setScene(scene);
stage.show();*/

        
        
        
        
        
        
        
        
    }

   

    

    @FXML
    private void menu(ActionEvent event) {
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
 @FXML
    private void titre(TableColumn.CellEditEvent<formation, String> event) {
           String newValue = event.getNewValue();
        formation editedFormation = event.getRowValue();
    
    if (editedFormation != null) {
        editedFormation.setTitre(newValue);
        // You can also save changes to the database if needed
    }
    fs.modifier(editedFormation);
    }
     @FXML
    private void categorie(TableColumn.CellEditEvent<formation, String> event) {
           String newValue = event.getNewValue();
        formation editedFormation = event.getRowValue();
    
    if (editedFormation != null) {
        editedFormation.setCategories(newValue);
        // You can also save changes to the database if needed
    }
    fs.modifier(editedFormation);
    }
     @FXML
    private void prix(TableColumn.CellEditEvent<formation, Double> event) {
           Double newValue = event.getNewValue();
        formation editedFormation = event.getRowValue();
    
    if (editedFormation != null) {
        editedFormation.setPrix(newValue);
        // You can also save changes to the database if needed
    }
    fs.modifier(editedFormation);
    }
     @FXML
    private void remise(TableColumn.CellEditEvent<formation, Float> event) {
           Float newValue = event.getNewValue();
        formation editedFormation = event.getRowValue();
    
    if (editedFormation != null) {
        editedFormation.setRemise(newValue);
        // You can also save changes to the database if needed
    }
    fs.modifier(editedFormation);
    }
     @FXML
    private void duree(TableColumn.CellEditEvent<formation, String> event) {
           String newValue = event.getNewValue();
        formation editedFormation = event.getRowValue();
    
    if (editedFormation != null) {
        editedFormation.setDuree(newValue);
        // You can also save changes to the database if needed
    }
    fs.modifier(editedFormation);
    }
     @FXML
    private void description(TableColumn.CellEditEvent<formation, String> event) {
        String newValue = event.getNewValue();
        formation editedFormation = event.getRowValue();
    
    if (editedFormation != null) {
        editedFormation.setDescription(newValue);
        // You can also save changes to the database if needed
    }
    fs.modifier(editedFormation);
    }
     @FXML
    private void video(TableColumn.CellEditEvent<formation, String> event) {
           String newValue = event.getNewValue();
        formation editedFormation = event.getRowValue();
    
    if (editedFormation != null) {
        editedFormation.setVideo(newValue);
        // You can also save changes to the database if needed
    }
    fs.modifier(editedFormation);
    }

    @FXML
    private void id(TableColumn.CellEditEvent<formation,Long> event) {
    }

  
    @FXML
    private void Ajout(MouseEvent event) {
    }

    
    

    

    

     
    

}


