 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product;


import Connection.MyConnection;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author User
 */
public class productService {
    
public productService () {}

    MyConnection cnx= MyConnection.getInstance();
    Connection mycnx = cnx.getConnection();



    public int ajouter (product p){
    if(this.chercher(p)!=null)
            return -1; 
    String req = "INSERT INTO Product (idPdts,nom,prix,qte,categ,matiere,description,image)" + "VALUES (?,?,?,?,?,?,?,?);";
    try{
        
    PreparedStatement prepStat = mycnx.prepareStatement(req);
    
    prepStat.setLong(1, p.getId_pdts());
    prepStat.setString(2,p.getNom());
    prepStat.setDouble (3,p.getPrix());
    prepStat.setInt (4,p.getQte());
    prepStat.setString(5,p.getCateg() );
    prepStat.setString(6,p.getMatiere());
    prepStat.setString(7, p.getDescription());
    prepStat.setString(8, p.getImage());
    int rowsAffected =  prepStat.executeUpdate();

      } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        return 0; 

}


public product chercher (product p){
  
        String req ="SELECT * FROM product WHERE idPdts LIKE ? AND nom LIKE ? AND prix LIKE ? AND qte LIKE ? AND categ LIKE ? AND matiere LIKE ? ; ";
        product pTr = new product();
      try {
        PreparedStatement prepStat = mycnx.prepareStatement(req);
        
       prepStat.setLong(1, p.getId_pdts());
       // prepStat.setLong(1,p.getId_u());
        prepStat.setString(2,p.getNom());
        prepStat.setDouble (3,p.getPrix());
        prepStat.setInt (4,p.getQte());
        prepStat.setString(5,p.getCateg() );
        prepStat.setString(6, p.getMatiere());
       // prepStat.setString(7, p.getDescription());
        
        
         ResultSet result = prepStat.executeQuery();
         
          if(!result.next())
                return null;
       //  pTr.setId_u(result.getLong("idU"));
         pTr.setNom(result.getString("nom"));
         pTr.setPrix(result.getDouble("prix"));
         pTr.setQte(result.getInt("qte"));
         pTr.setCateg(result.getString("categ"));
         pTr.setMatiere(result.getString("matiere"));
         //pTr.setDescription(result.getString("desc"));
         
        
    }  catch (SQLException ex) {
            System.out.println(ex.getMessage());
    }
       return pTr;
}
 

    public int supprimer(product p) {
        
        String req="DELETE FROM product WHERE `product`.`idPdts` = ? ;";
        try {
            PreparedStatement prepStat = mycnx.prepareStatement(req);
            prepStat.setLong(1,p.getId_pdts());
            int rowsAffected =  prepStat.executeUpdate();
           
            if(rowsAffected==0)
                return -1;    
            } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }
//@Override
   /* public product modifier (product p, product p1) {
    
   String req = "UPDATE `product` SET `nom` = ?, `prix` = ?, `qte` = ?, `categ` = ?, `matiere` = ?, "
           + "`description` = ? WHERE `product`.`idPdts` = ? ;"; 
   try {
   
       PreparedStatement prepStat = mycnx.prepareStatement(req);
   
    
    //prepStat.setLong(1, p1.getId_u());
    prepStat.setString(1,p1.getNom());
    prepStat.setDouble(2,p1.getPrix());
    prepStat.setInt(3,p1.getQte());
    prepStat.setString(4,p1.getCateg() );
    prepStat.setString(5, p1.getMatiere());
    prepStat.setString(6,p1.getDescription());
    prepStat.setLong(7, p.getId_pdts());
    
    int rowsAffected =  prepStat.executeUpdate();
    

      } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return p1;
   
    }
*/
    public product modifier(product editedProduct) {
    // Assuming you have a database connection and prepared statements
    try {
        // Replace these lines with your database update logic
        String updateQuery = "UPDATE product SET nom = ?, prix = ?, qte = ?, categ = ?, matiere = ?, description = ? WHERE idPdts = ?";
        PreparedStatement preparedStatement = mycnx.prepareStatement(updateQuery);

        preparedStatement.setString(1, editedProduct.getNom());
        preparedStatement.setDouble(2, editedProduct.getPrix());
        preparedStatement.setInt(3, editedProduct.getQte());
        preparedStatement.setString(4, editedProduct.getCateg());
        preparedStatement.setString(5, editedProduct.getMatiere());
        preparedStatement.setString(6, editedProduct.getDescription());
        preparedStatement.setLong(7, editedProduct.getId_pdts()); // Assuming you have an ID field for the record

        preparedStatement.executeUpdate();

        // Close resources and handle exceptions as needed
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
        
    }
    return editedProduct;
    }



    public List<product>getAllProducts(){
        
        List<product> retour= new ArrayList();
        String req ="SELECT * FROM `product` ";
       try{
           
        PreparedStatement prepStat = mycnx.prepareStatement(req);
        ResultSet result = prepStat.executeQuery();
        while (result.next()){
            product pTr =new product();
            pTr.setId_pdts(result.getLong("idPdts"));
            pTr.setNom(result.getString("nom"));
            pTr.setPrix(result.getDouble("prix"));
            pTr.setQte(result.getInt("qte"));
            pTr.setCateg(result.getString("categ"));
            pTr.setMatiere(result.getString("matiere"));
            pTr.setDescription(result.getString("description"));
            retour.add(pTr);
            
       }
       }
       catch (SQLException ex){
           System.out.println(ex.getMessage());
       }
       return retour ; 
    }
    /* public void insertImageIntoDatabase(File imageFile) {
      String req = "INSERT INTO images (image_data) VALUES (?)";
        try{
            FileInputStream inputStream = new FileInputStream(imageFile);
            PreparedStatement prepStat = mycnx.prepareStatement(req);
                // PreparedStatement preparedStatement = connection.prepareStatement(insertSQL))
                prepStat.setBinaryStream(1, inputStream, (int) imageFile.length());
                prepStat.executeUpdate();
                System.out.println("Image inserted into the database.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
         /* public void refresh (TableView<product>tableProduit,TableColumn<product,String> nom,TableColumn<product, Double> prix,TableColumn<product, Integer> qte,
            TableColumn<product,String> categ,TableColumn<product,String> matiere,TableColumn<product,String> description) {
            nom.setCellValueFactory(new PropertyValueFactory<product,String>("nom"));
            prix.setCellValueFactory(new PropertyValueFactory<product,Double>("prix"));
             qte.setCellValueFactory(new PropertyValueFactory<product,Integer>("qte"));
             categ.setCellValueFactory(new PropertyValueFactory<product,String>("categ"));
             matiere.setCellValueFactory(new PropertyValueFactory<product,String>("matiere"));
             description.setCellValueFactory(new PropertyValueFactory<product,String>("description"));
             productService pS= new productService();
             ObservableList <product> productList=FXCollections.observableArrayList(pS.getAllProducts());
             tableProduit.setItems(productList);
         
    }*/
  
    
    public int unicProduct(String nom){
        String req="select * from product where nom= ?;";
        
        try {
            PreparedStatement prepStat = mycnx.prepareStatement(req);
            
            prepStat.setString(1, nom);
            
            ResultSet rS= prepStat.executeQuery();
            if(rS.next())
                return -1;
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }
    public List<product> getproduitByCategorie(String categorie) throws SQLException {
    List<product> retour = new ArrayList<>();
    String req = "SELECT * FROM product WHERE categ = ?";

    try {
        PreparedStatement prepStat = mycnx.prepareStatement(req);
        prepStat.setString(1, categorie);  // Set the category parameter
        ResultSet result = prepStat.executeQuery();
        while (result.next()){
            product pTr =new product();
            pTr.setId_pdts(result.getLong("idPdts"));
            pTr.setNom(result.getString("nom"));
            pTr.setPrix(result.getDouble("prix"));
            pTr.setQte(result.getInt("qte"));
            pTr.setCateg(result.getString("categ"));
            pTr.setMatiere(result.getString("matiere"));
            pTr.setDescription(result.getString("description"));
            retour.add(pTr);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return retour;
}
}

  
/*TableView<product>tableProduit,TableColumn<product,String> nom,TableColumn<product, Double> prix,TableColumn<product, Integer> qte,
            TableColumn<product,String> categ,TableColumn<product,String> matiere,TableColumn<product,String> description*/