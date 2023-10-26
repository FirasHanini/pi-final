/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commande;

import LineOrder.LineOrder;
import LineOrder.LineOrderService;
import Connection.MyConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author User
 */
public class commandeService {
    
    MyConnection cnx= MyConnection.getInstance();
    Connection mycnx = cnx.getConnection();
    
    
    
     public int ajouter (commande c) {
    if(this.chercher(c)!=null)
            return -1; 
    String req = "INSERT INTO commande (nomC,id_client,adresse,date,numTel,email,total)" + "VALUES (?,?,?,?,?,?,?);";
    try{
        
    PreparedStatement prepStat = mycnx.prepareStatement(req,Statement.RETURN_GENERATED_KEYS);
    
    prepStat.setString(1, c.getNomC());
    prepStat.setInt(2,c.getId_client());
    prepStat.setString (3,c.getAdresse());
    Date sqlDate = Date.valueOf(c.getDate());
      prepStat.setDate(4, sqlDate);
    prepStat.setLong(5,c.getNumTel());
    prepStat.setString(6,c.getEmail());
    prepStat.setDouble(7,c.getTotal());
    int rowsAffected =  prepStat.executeUpdate();
     long generatedIdC = -1;
                try (ResultSet generatedKeys = prepStat.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedIdC = generatedKeys.getLong(1); // Get the generated id_c
                    }
                }
                String updateLineOrderSQL = "UPDATE lineorder SET id_c = ? WHERE id_c IS NULL";
        try (PreparedStatement updateStatement = mycnx.prepareStatement(updateLineOrderSQL)) {
    updateStatement.setLong(1, generatedIdC); // Replace with the actual order ID
    updateStatement.executeUpdate();
}
          return 0;


      } catch (SQLException ex) {
            System.out.println(ex.getMessage());
             return -1; 
        }
        
        
       

}

public commande chercher (commande c){
  
        String req ="SELECT * FROM  commande WHERE nomC LIKE ? AND id_client LIKE ? AND adresse LIKE ? AND date LIKE ? AND numTel LIKE ? AND email LIKE ? AND total LIKE ? ; ";
        commande cTr = new commande();
      try {
        PreparedStatement prepStat = mycnx.prepareStatement(req);
          prepStat.setString(1, c.getNomC());
          prepStat.setInt(2,c.getId_client());
          prepStat.setString (3,c.getAdresse());
             Date sqlDate = Date.valueOf(c.getDate());
      prepStat.setDate(4, sqlDate);
          prepStat.setInt(5,c.getNumTel());
          prepStat.setString(6,c.getEmail());
          prepStat.setDouble(7,c.getTotal());
        
       // prepStat.setString(7, p.getDescription());
        
        
         ResultSet result = prepStat.executeQuery();
         
          if(!result.next())
                return null;
         cTr.setNomC(result.getString("nomC"));
         cTr.setId_client(result.getInt("id_client"));
         cTr.setAdresse(result.getString("adresse"));
         cTr.setDate(result.getObject("date",LocalDate.class));
         cTr.setNumTel(result.getInt("numTel"));
         cTr.setEmail(result.getString("email"));
         cTr.setTotal(result.getDouble("total"));
         //pTr.setDescription(result.getString("desc"));
         
        
    }  catch (SQLException ex) {
            System.out.println(ex.getMessage());
    }
       return cTr;
}
 

    public int supprimer(commande c) {
        
        String req="DELETE FROM commande WHERE id_c = ?;";
        try {
            PreparedStatement prepStat = mycnx.prepareStatement(req);
            prepStat.setLong(1, c.getId_c());
            int rowsAffected =  prepStat.executeUpdate();
            if(rowsAffected==0)
                return -1;
            
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }
    
    public commande modifier (commande c, commande c1) {
    
   String req = "UPDATE `commande` SET  `nomC` = ?, `id_client` = ?, `adresse` = ?, `date` = ?, `numTel` = ?, "
           + "`email` = ? WHERE `commande`.`id_c` = ? ;"; 
   try {
   
       PreparedStatement prepStat = mycnx.prepareStatement(req);
   
    
   // prepStat.setLong(1, p1.getId_pdts());
          prepStat.setString(1, c1.getNomC());
          prepStat.setInt(2,c1.getId_client());
          prepStat.setString (3,c1.getAdresse());
             Date sqlDate = Date.valueOf(c.getDate());
      prepStat.setDate(4, sqlDate);
          prepStat.setInt(5,c1.getNumTel());
          prepStat.setString(6,c1.getEmail());
          prepStat.setLong(7,c.getId_c());
        int rowsAffected =  prepStat.executeUpdate();
    

      } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return c1;
   
    }    
    
    public List<commande>getAllCommande(){
        
        List<commande> retour= new ArrayList();
        String req ="select * from `commande` ";
       try{
           
        PreparedStatement prepStat = mycnx.prepareStatement(req);
        ResultSet result = prepStat.executeQuery();
        while (result.next()){
            commande cTr =new commande();
         cTr.setNomC(result.getString("nomC"));
         cTr.setId_client(result.getInt("id_client"));
         cTr.setAdresse(result.getString("adresse"));
         LocalDate localDate = result.getDate("date").toLocalDate();
          
           cTr.setDate(localDate);

     
         cTr.setNumTel(result.getInt("numTel"));
         cTr.setEmail(result.getString("email"));
         cTr.setTotal(result.getDouble("total"));
           
            retour.add(cTr);
            
       }
       }
       catch (SQLException ex){
           System.out.println(ex.getMessage());
       }
       return retour ; 
    }
    
public int unicId_c(int id){
        String req="select * from commande where id_client = ?;";
        
        try {
            PreparedStatement prepStat = mycnx.prepareStatement(req);
            
            prepStat.setInt(1, id);
            
            ResultSet rS= prepStat.executeQuery();
            if(rS.next())
                return -1;
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }
public List<commande> getLastLine(){
    

    List<commande> retour= new ArrayList();
        String req ="SELECT nomC,id_c,adresse,numTel,total FROM commande ORDER BY id_c DESC LIMIT 1;";
      
        try{
           
        PreparedStatement prepStat = mycnx.prepareStatement(req);
        ResultSet result = prepStat.executeQuery();
        while (result.next()){
            commande cTr =new commande();
            cTr.setId_c(result.getLong("id_c"));
            cTr.setNomC(result.getString("nomC"));    
            cTr.setAdresse(result.getString("adresse")); 
         cTr.setNumTel(result.getInt("numTel"));
         cTr.setTotal(result.getDouble("total"));
           
            retour.add(cTr);
            
       }
       }
       catch (SQLException ex){
           System.out.println(ex.getMessage());
       }
       return retour ; 
    }
/*public List<commande> getOrderHistoryWithLineOrders() {
    List<commande> orderHistory = new ArrayList<>();
    Map<Integer, commande> ordersMap = new HashMap<>();

    String query = "INSERT INTO historique (SELECT commande.id_c, commande.date, lineorder.productName, lineorder.quantite, lineorder.prix " +
            "FROM commande " +
            "JOIN lineorder ON commande.id_c = lineorder.id_c " +
            "ORDER BY commande.date DESC )";

    try {
        PreparedStatement prepStat = mycnx.prepareStatement(query);
        ResultSet resultSet = prepStat.executeQuery();

        while (resultSet.next()) {
            int orderId = resultSet.getInt("id_c");
            LocalDate orderDate = resultSet.getDate("date").toLocalDate();
            String productName = resultSet.getString("productName");
            int quantity = resultSet.getInt("quantite");
            double price = resultSet.getDouble("prix");

            if (ordersMap.containsKey(orderId)) {
                commande order = ordersMap.get(orderId);
                LineOrder lineOrder = new LineOrder(productName, quantity, price);
                order.addLineOrder(lineOrder);
            } else {
                commande order = new commande(orderId, orderDate);
                LineOrder lineOrder = new LineOrder(productName, quantity, price);
                order.addLineOrder(lineOrder);

                ordersMap.put(orderId, order);
                orderHistory.add(order);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return orderHistory;
}*/
public List<orderItems> insertJoinResultIntoTableAndReturnList() {
    List<orderItems> insertedData = new ArrayList<>();

    String insertQuery = "INSERT INTO historique (numC, dateOrder, Product, quantite, prix) " +
            "SELECT commande.id_c, commande.date, lineorder.productName, lineorder.quantite, lineorder.prix " +
            "FROM commande " +
            "JOIN lineorder ON commande.id_c = lineorder.id_c " +
            "ORDER BY commande.date DESC";

    String selectQuery = "SELECT *FROM historique";

    try {
        // Insert data into target_table
        PreparedStatement insertStatement = mycnx.prepareStatement(insertQuery);
        insertStatement.executeUpdate();

        // Retrieve the inserted data
        PreparedStatement selectStatement = mycnx.prepareStatement(selectQuery);
        ResultSet resultSet = selectStatement.executeQuery();

        while (resultSet.next()) {
            long orderId = resultSet.getLong("numC");
            LocalDate orderDate = resultSet.getDate("dateOrder").toLocalDate();
            String productName = resultSet.getString("Product");
            int quantity = resultSet.getInt("quantite");
            double price = resultSet.getDouble("prix");

            orderItems orderItem = new orderItems(orderId, orderDate, productName, quantity, price);
            insertedData.add(orderItem);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return insertedData;
}

}


 



    

