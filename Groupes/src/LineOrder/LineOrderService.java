/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LineOrder;

import commande.commande;
import Connection.MyConnection;
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
import product.product;

/**
 *
 * @author User
 */
public class LineOrderService {
    
    MyConnection cnx= MyConnection.getInstance();
    Connection mycnx = cnx.getConnection();
    
    
    
     public int ajouter (LineOrder L) {
         
    if(this.chercher(L)!=null)
            return -1; 
    
    String req = "INSERT INTO lineorder (productName ,quantite ,prix )" + "VALUES (?,?,?);";
    
    try{
        
    PreparedStatement prepStat = mycnx.prepareStatement(req);
    
   
    
    prepStat.setString (1,L.getProductName());
    prepStat.setInt (2,L.getQuantite());
    prepStat.setDouble(3,L.getPrix());
    int rowsAffected =  prepStat.executeUpdate();

      } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
         List<LineOrder> list = getAllOrders();
         
        return 0; 

}

public LineOrder chercher (LineOrder L){
  
        String req =" SELECT * FROM  lineorder WHERE productName LIKE ? AND quantite LIKE ? AND prix LIKE ? ";
        LineOrder LO = new LineOrder();
      try {
        PreparedStatement prepStat = mycnx.prepareStatement(req);
        prepStat.setString (1,L.getProductName());
        prepStat.setInt (2,L.getQuantite());
        prepStat.setDouble(3,L.getPrix());
        
       // prepStat.setString(7, p.getDescription());
        
        
         ResultSet result = prepStat.executeQuery();
         
          if(!result.next())
                return null;
         LO.setProductName(result.getString("productName"));
         LO.setQuantite(result.getInt("quantite"));
         LO.setPrix(result.getDouble("prix"));
       
         //pTr.setDescription(result.getString("desc"));
         
        
    }  catch (SQLException ex) {
            System.out.println(ex.getMessage());
    }
       return LO ;
}
 

    public int supprimer(LineOrder L) {
        
        String req="DELETE FROM lineorder WHERE productName = ? ;";
        try {
            PreparedStatement prepStat = mycnx.prepareStatement(req);
            prepStat.setString (1,L.getProductName());
            int rowsAffected =  prepStat.executeUpdate();
            if(rowsAffected==0)
                return -1;
            
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }
    
    public LineOrder modifier (LineOrder L ) {
         
   String req = "UPDATE `lineorder` SET `productName` = ?, `quantite` = ?, `prix` = ?, "
           + "` WHERE `commande`.`productName` = ? ;"; 
   try {
       
       PreparedStatement prepStat = mycnx.prepareStatement(req);
   
    
   // prepStat.setLong(1, p1.getId_pdts());
        prepStat.setString (1,L.getProductName());
        prepStat.setInt (2,L.getQuantite());
        prepStat.setDouble(3,L.getPrix());
        //prepStat.setInt(4, L.getId_o());
        prepStat.setString (4,L.getProductName());
          
        int rowsAffected =  prepStat.executeUpdate();
    

      } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return L;
   
    }    
    
    public List<LineOrder> getAllOrders() {
    List<LineOrder> retour = new ArrayList<>();
    String req = "SELECT * FROM lineorder";

    try {
        PreparedStatement prepStat = mycnx.prepareStatement(req);
        ResultSet result = prepStat.executeQuery();

        while (result.next()) {
            LineOrder lTr = new LineOrder();
            lTr.setId_o(result.getInt("id_o"));
            lTr.setProductName(result.getString("productName"));
            lTr.setQuantite(result.getInt("quantite"));
            lTr.setPrix(result.getDouble("prix"));
            retour.add(lTr);
        }
    } catch (SQLException ex) {
        Logger.getLogger(LineOrderService.class.getName()).log(Level.SEVERE, null, ex);
    }

    return retour;
}

 
   /* public void calculsubTotal(){
        product p = new product();
        LineOrder LO = new LineOrder();
        double subTotal= p.getPrix() * LO.getQuantite();
        
    }*/

 
   public void affiche(TableView<LineOrder> tableView, TableColumn<LineOrder, String> productNameColumn,
    TableColumn<LineOrder, Integer> quantiteColumn, TableColumn<LineOrder, Double> prixColumn) {
    List<LineOrder> allOrders = getAllOrders();
    if (allOrders != null) {
        ObservableList<LineOrder> orderList = FXCollections.observableArrayList(allOrders);
        tableView.setItems(orderList);
    }
    productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
    quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
    prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
    
     ObservableList<LineOrder> orderList = FXCollections.observableArrayList(allOrders);
     
        tableView.setItems(orderList);
        
    
    
}
   public double calculateTotalPrice() {
       
            double total = 0.0;
            String req = "SELECT prix, quantite FROM lineorder";
             try {
            PreparedStatement prepStat = mycnx.prepareStatement(req);
            ResultSet result = prepStat.executeQuery();

            while (result.next()) {
                double prix = result.getDouble("prix");
                int quantite = result.getInt("quantite");
                total += prix * quantite;
            }
            
            
            return total;
        } catch (SQLException ex) {
            Logger.getLogger(LineOrderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    
   }
 public int deleteAll() {
        
        String req="DELETE FROM lineorder ;";
        try {
            PreparedStatement prepStat = mycnx.prepareStatement(req);
           // prepStat.setString (1,L.getProductName());
            int rowsAffected =  prepStat.executeUpdate();
            if(rowsAffected==0)
                return -1;
            
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }
  public List<LineOrder> getAllOrdersForOrder(int orderId) throws SQLException {
        List<LineOrder> lineOrders = new ArrayList<>();
        String query = "SELECT productName, quantite, prix FROM lineorder WHERE id_c = ?";
    
        
        PreparedStatement prepStat = mycnx.prepareStatement(query);
         ResultSet resultSet = prepStat.executeQuery();
        // Your database connection

        try {
            // Your query execution with the orderId parameter

            while (resultSet.next()) {
                String productName = resultSet.getString("productName");
                int quantity = resultSet.getInt("quantite");
                double price = resultSet.getDouble("prix");

                lineOrders.add(new LineOrder(productName, quantity, price));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lineOrders;
    }

}  


