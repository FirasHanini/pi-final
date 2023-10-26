/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import LineOrder.LineOrder;
import LineOrder.LineOrderService;
import Connection.MyConnection;
import java.io.File;
import java.io.IOException;
import static java.lang.System.load;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import static javafx.fxml.FXMLLoader.load;
import static javafx.fxml.FXMLLoader.load;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import product.product;

/**
 * FXML Controller class
 *
 * @author User
 */


public class CardProductController implements Initializable {

    @FXML
    private Label prodName;
    @FXML
    private Label Pprice;
    @FXML
    private ImageView Pimage;
    @FXML
    private Spinner<Integer> Pqte;
    SpinnerValueFactory<Integer>valueFactory= new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1);
    @FXML
    private Button btnPajouter;

    /**
     * Initializes the controller class.
     */
    LineOrder L = new LineOrder();
    private product p ;
   
    
    public product getProductByID(long id) throws SQLException{
         MyConnection cnx= MyConnection.getInstance();
    Connection mycnx = cnx.getConnection();
    String req="SELECT * FROM product WHERE idPdts= ?";
    
    
 PreparedStatement prepStat = mycnx.prepareStatement(req);
         prepStat.setLong(1, id);
         
        ResultSet resultSet = prepStat.executeQuery();

product productResult = null;

if (resultSet.next()) {
  
    String nom = resultSet.getString("nom");
    double prix = resultSet.getDouble("prix");
    int quantite = resultSet.getInt("qte");
    String categ = resultSet.getString("categ");
    String matiere = resultSet.getString("matiere");
    String description = resultSet.getString("description");
    String image = resultSet.getString("image");
  
    productResult = new product(nom,  prix,  quantite, categ, matiere, description, image) ;
}

// Close the result set and the prepared statement
resultSet.close();
prepStat.close();

return productResult;
    
    
    }
    
    

      
   public void setData(long id ) throws SQLException{
      
       product p = getProductByID(id);
        prodName.setText(p.getNom());
        Pprice.setText(String.valueOf(p.getPrix()));
         Pqte.setValueFactory(valueFactory);
      

            File imageFile = new File(p.getImage());
            Image image = new Image(imageFile.toURI().toString(),90,94,false,true);
            
            Pimage.setImage(image);


    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        
    }

        
        
public List<Long> getAllProductIds() {
    List<Long> productIds = new ArrayList<>();
    try {
        // Establish a database connection and execute a query to fetch all IDs
        Connection connection = MyConnection.getInstance().getConnection();
        String query = "SELECT idPdts FROM product"; 
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            Long id = resultSet.getLong("idPdts");
            productIds.add(id);
        }

        // Close resources
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return productIds;
}

    @FXML
    private void addProduct(MouseEvent event) throws IOException {
        int id = L.getId_o();
        
        int qte = Pqte.getValue();
        String productName =  prodName.getText();
        double prix = Double.parseDouble(Pprice.getText());
        if( qte>0){
        LineOrder LO = new LineOrder( id,productName,qte, prix);
        LineOrderService lS= new LineOrderService();
        int result=lS.ajouter(LO);
       
  FXMLLoader loader = new FXMLLoader(getClass().getResource("boutique.fxml"));
            Parent root = loader.load();
            
            
              BoutiqueController controllerEvent=loader.getController();
            controllerEvent.refreshOrder();
        if (result==0){
              showAlertDialog("Success", "Line Order Added", "The Line Order has been added successfully.");
              } else if (result == -1) {
            // Line Order already exists
            showAlertDialog("Error", "Line Order Already Exists", "The Line Order you are trying to add already exists.");
        } else {
            // Other error occurred
            showAlertDialog("Error", "Failed to Add Line Order", "An error occurred while adding the Line Order.");
        }
    } else {
        showAlertDialog("Error", "Invalid Quantity", "Quantity must be greater than 0.");
    }
}

private void showAlertDialog(String title, String header, String content) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.showAndWait();
        
    }
    

    
    }
    

