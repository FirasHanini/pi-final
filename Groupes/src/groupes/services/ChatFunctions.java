/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupes.services;

import MyConnection.MyConnection;
import Utilisateur.Utilisateur;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import groupes.services.MiscFunctions;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hammeda
 */
public class ChatFunctions {

    public ChatFunctions() {
    }
 
    int idOwner=1;
     public long getUserIDByEmail(String email) {
    long userId = -1; 

    try {
       
        String query = "SELECT id FROM Utilisateur WHERE email = ?";

       
    java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query); 

       
        preparedStatement.setString(1, email);

        
        java.sql.ResultSet resultSet = preparedStatement.executeQuery();

       
        if (resultSet.next()) {
            userId = resultSet.getInt("id");
        }

      
        resultSet.close();
        preparedStatement.close();
        
    } catch (SQLException ex) {
        System.out.println("Error: " + ex.getMessage());
    }

    return userId;
}
     
     
    public void Send(long idOwner,Text EmailTXT, String text) throws SQLException{
      LocalDateTime currentDateTime = LocalDateTime.now();
        String insertSql = "INSERT INTO Conversation (idUser1, idUser2, Date_MSG, Msg) VALUES (?, ?, ?, ?)";
        
       java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(insertSql); 
         preparedStatement.setLong(1, idOwner);
    preparedStatement.setLong(2, getUserIDByEmail( EmailTXT.getText())); 
    preparedStatement.setObject(3, currentDateTime);
    preparedStatement.setString(4, text);
        
        preparedStatement.executeUpdate();
            preparedStatement.close();
                

     
     
     
     }
     
    public void GetEmailTotalk(String mail) throws SQLException{
    
    String newEmail = mail; 
    String updateQuery = "UPDATE tempmail SET Email = ? WHERE id = 1";
    java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(updateQuery); 
    preparedStatement.setString(1, newEmail);
     int rowsUpdated = preparedStatement.executeUpdate();
     preparedStatement.close();
   
    }
    public void loadConversation(ListView<String> conversationListView,ListView<String> conversationListView1) throws SQLException {
    conversationListView.getItems().clear(); 
MiscFunctions MF = new MiscFunctions();
 String selectQuery = "SELECT Email FROM tempmail WHERE id = ?";
 int id=1;
 long userId;
  String email = null;
     java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(selectQuery); 
 preparedStatement.setLong(1, id);
 java.sql.ResultSet resultSet = preparedStatement.executeQuery();
  if (resultSet.next()) {
email = resultSet.getString("Email");

    }
  userId=getUserIDByEmail(email);
    try {
        
        String query = "SELECT Date_MSG, Msg FROM conversation WHERE idUser1 = ? AND idUser2 = ? ORDER BY Date_MSG";
conversationListView1.getItems().clear();
        
        preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query); 
        preparedStatement.setLong(1, idOwner);
        preparedStatement.setLong(2, userId);
        

        
         resultSet = preparedStatement.executeQuery();

       
        while (resultSet.next()) {
            java.sql.Timestamp timestamp = resultSet.getTimestamp("Date_MSG");
            String message = resultSet.getString("Msg");
            String formattedMessage;
            try {
                formattedMessage = formatMessage(timestamp, MF.decryptPassword(message, "A26B773F41F90732D8497BDFB6F15021"));
           
            
            conversationListView.getItems().add(formattedMessage);
             } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

       
    } catch (SQLException ex) {
        System.out.println("Error: " + ex.getMessage());
    }
    
    
    String query = "SELECT Date_MSG, Msg FROM conversation WHERE idUser1 = ? AND idUser2 = ? ORDER BY Date_MSG";
    preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query); 
    preparedStatement.setLong(1, userId);
    preparedStatement.setInt(2, idOwner);
   java.sql.ResultSet resultSet2 = preparedStatement.executeQuery();
    while (resultSet2.next()) {
            java.sql.Timestamp timestamp = resultSet2.getTimestamp("Date_MSG");
            String message = resultSet2.getString("Msg");
            String formattedMessage = formatMessage(timestamp, message);
            System.out.println(message);
            conversationListView1.getItems().add(formattedMessage);
        }
       resultSet.close();
        preparedStatement.close();
       
}
    
      private String formatMessage(java.sql.Timestamp timestamp, String message) {
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
    String formattedTimestamp = "[" + dateFormat.format(timestamp) + "] ";

    
    return formattedTimestamp + message;
    
    
}
      public ObservableList<Utilisateur> GetUserList() throws SQLException{
        
           ObservableList<Utilisateur> UserList = FXCollections.observableArrayList();

    String query = "SELECT * FROM utilisateur WHERE id!=?";
    
   
           java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query); 
          preparedStatement.setInt(1, idOwner);
           java.sql.ResultSet resultSet = preparedStatement.executeQuery() ;

        while (resultSet.next()) {
            Utilisateur user1 = new Utilisateur(
                resultSet.getInt("id"),
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
              resultSet.getString("date_naissance") ,
                    resultSet.getInt("age"),
                
                 resultSet.getString("email")
                    
                    
            );
           
            UserList.add(user1);
            System.out.println(user1.getNom());
        }
    
    
    
    
    
    
    
    
    return UserList;
    
    
    }
      public void ShowUsersG(TableView<Utilisateur> GTable,TableColumn<Utilisateur, String> TName,TableColumn<Utilisateur, String> TPrenom,TableColumn<Utilisateur, String> TEmail) {
     
        try {
            ObservableList<Utilisateur> list=GetUserList();
            TName.setCellValueFactory(new PropertyValueFactory<>("nom"));
            TPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            TEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            GTable.setItems(list);
            System.out.println("Setting items fil TableView jawou behi AAA");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
}
      public boolean BadWordCheck(String input) {
  
    String[] BadWords = {"bad word", "kelma khayba", "f u", "kys", "mout", "Muslim", "Islam", "Christian", "religion"};


    String lowerInput = input.toLowerCase();

    for (String word : BadWords) {
        if (lowerInput.contains(word.toLowerCase())) {
            return true; 
        }
    }

    return false; 
}
      
      public String getEmailbyID(long id){
       String email = null;
    String sql = "SELECT email FROM utilisateur WHERE id = ?";
  try {
    java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql); 
       
            preparedStatement.setLong(1, id);
      

       java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
         
               email = resultSet.getString("email");
                System.out.println("AAA"+email);
           }} catch (SQLException ex) {
               Logger.getLogger(ChatFunctions.class.getName()).log(Level.SEVERE, null, ex);
           }
            
   

    return email;
      
      }
      
      
      
}
