/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Utilisateur.Utilisateur;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import product.product;
import product.productService;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ProduitsController implements Initializable {
Utilisateur current;
    @FXML
    private Button Home;
     public void setUtilisateur3(Utilisateur current){
        this.current=current;
     
    }
    @FXML
    private TableView<product> tableProduit;
    @FXML
    private TableColumn<product, String> nom;
    @FXML
    private TableColumn<product, Double> prix;
    @FXML
    private TableColumn<product, Integer> qte;
    @FXML
    private TableColumn<product, String> categ;
    @FXML
    private TableColumn<product, String> matiere;
    @FXML
    private TableColumn<product, String> description;
    @FXML
    private Button supprimer;
    // private Button ajouter;
    @FXML
    private Button btnAjouter;
  
   // private GridPane menu_gridPane;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    productService pS = new productService();
    @FXML
    private TableColumn<product, Integer> idPdts;
    @FXML
    private Button menu;

    @Override

    public void initialize(URL url, ResourceBundle rb) {
       refreshTable();
        tableProduit.setEditable(true);
        nom.setOnEditCommit(this::nomP);
        prix.setOnEditCommit(this::prixP);
        qte.setOnEditCommit(this::qteP);
        categ.setOnEditCommit(this::categP);
        matiere.setOnEditCommit(this::matP);
        description.setOnEditCommit(this::descP);
        //  pS.refresh(tableProduit, nom, prix, qte, categ, matiere, description);
        
        //menuDisplayCard();
    }


    @FXML
    private void ajouterP(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjoutProduit.fxml"));
            Parent root = loader.load();
            AjoutProduitController APC = loader.getController();

            // Create a new stage for the new scene
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

    public void refreshTable() {
        // Implement your code to refresh the TableView here
        
        ObservableList<product> productList = FXCollections.observableArrayList(pS.getAllProducts());

        nom.setCellValueFactory(new PropertyValueFactory<product, String>("nom"));
        prix.setCellValueFactory(new PropertyValueFactory<product, Double>("prix"));
        qte.setCellValueFactory(new PropertyValueFactory<product, Integer>("qte"));
        categ.setCellValueFactory(new PropertyValueFactory<product, String>("categ"));
        matiere.setCellValueFactory(new PropertyValueFactory<product, String>("matiere"));
        description.setCellValueFactory(new PropertyValueFactory<product, String>("description"));
        idPdts.setCellValueFactory(new PropertyValueFactory<product, Integer>("idPdts"));
        
        //edit collumns
             nom.setCellFactory(TextFieldTableCell.forTableColumn());
            prix.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            qte.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            categ.setCellFactory(TextFieldTableCell.forTableColumn());
            matiere.setCellFactory(TextFieldTableCell.forTableColumn());
            description.setCellFactory(TextFieldTableCell.forTableColumn());

        tableProduit.setItems(productList);
    }

    @FXML
    private void supprimerP(ActionEvent event) {
      
    product p = tableProduit.getSelectionModel().getSelectedItem();
    if (p != null) {
        // Create a confirmation dialog
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to remove this product?");
        confirmationAlert.setContentText("Click OK to remove the product or Cancel to keep it.");

        // Show the confirmation dialog and wait for the user's response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK, proceed with the deletion
            ObservableList<product> data = tableProduit.getItems();
            int deleteResult = pS.supprimer(p);
            System.out.println(deleteResult);
            data.remove(p);
            refreshTable();
        }
    }

    }

   /* public void menuDisplayCard() {
        int column =0;
        int row=0;
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();
         cardListData.clear();
        cardListData.addAll(pS.getAllProducts());
        for (int q = 0; q < cardListData.size(); q++) {
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(load.getClass().getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                CardProductController CPC = load.getController();

                CPC.setData(cardListData.get(q));
                 if (column==3){
                column=0;
                row+=1;
            }
                 menu_gridPane.add(pane,column++,row);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
           

        }
    }*/

    @FXML
private void Menu(MouseEvent event) throws IOException {
      try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("boutique.fxml"));
            Parent root = loader.load();
            
            
              BoutiqueController controllerEvent=loader.getController();
            controllerEvent.setUtilisateur(current);

            Stage cStage= (Stage) this.menu.getScene().getWindow();
            cStage.setWidth(1200);
            cStage.setHeight(840);
              
            menu.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
}
 @FXML
private void nomP(TableColumn.CellEditEvent<product, String> event) {
    String newValue = event.getNewValue();
    product editedProduct = event.getRowValue();

    if (editedProduct != null) {
        String oldValue = editedProduct.getNom(); // Get the original value before editing
        product originalProduct = new product(); // Create a copy of the product
        originalProduct.setNom(oldValue); // Set the original value on the copy
        editedProduct.setNom(newValue);
        //pS.modifier(originalProduct, editedProduct);
        // Pass the original and edited products to the modifier method
    }
     pS.modifier(editedProduct);
}
 @FXML
private void prixP(TableColumn.CellEditEvent<product, Double> event) {
    Double newValue = event.getNewValue();
    product editedProduct = event.getRowValue();
    if (editedProduct != null && newValue != null) {
        Double oldValue = editedProduct.getPrix(); // Get the original value before editing
        product originalProduct = new product(); // Create a copy of the product
        originalProduct.setPrix(oldValue); // Set the original value on the copy
        editedProduct.setPrix(newValue);
       // pS.modifier(originalProduct, editedProduct); // Pass the original and edited products to the modifier method
    }
     pS.modifier(editedProduct);
}


// Repeat the same pattern for other columns...

 @FXML
private void qteP(TableColumn.CellEditEvent<product, Integer> event) {
   product editedProduct = event.getRowValue();
    
    if (editedProduct != null) {
        Integer newValue = event.getNewValue();
        if (newValue != null) {
            editedProduct.setQte(newValue);
            
        }}
    pS.modifier(editedProduct);
}
 @FXML
private void categP(TableColumn.CellEditEvent<product, String> event) {
    String newValue = event.getNewValue();
    product editedProduct = event.getRowValue();
    
    if (editedProduct != null) {
        editedProduct.setCateg(newValue);
        // You can also save changes to the database if needed
    }
     pS.modifier(editedProduct);
     //refreshTable();
  
}
 @FXML
private void matP(TableColumn.CellEditEvent<product, String> event) {
    String newValue = event.getNewValue();
    product editedProduct = event.getRowValue();
    
    if (editedProduct != null) {
        editedProduct.setMatiere(newValue);
        
        // You can also save changes to the database if needed
    }
    pS.modifier(editedProduct);
}
 @FXML
private void descP(TableColumn.CellEditEvent<product, String> event) {
    String newValue = event.getNewValue();
    product editedProduct = event.getRowValue();
    
    if (editedProduct != null) {
        editedProduct.setDescription(newValue);
        // You can also save changes to the database if needed
    }
    pS.modifier(editedProduct);

}

   
    @FXML
    private void Home(ActionEvent event) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage/homPage.fxml"));
            Parent root = loader.load();
            
            
              BoutiqueController controllerEvent=loader.getController();
            controllerEvent.setUtilisateur(current);

            Stage cStage= (Stage) this.Home.getScene().getWindow();
            cStage.setWidth(1200);
            cStage.setHeight(540);
              
            Home.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void boutiqueLoad(ActionEvent event) {
    }
}

