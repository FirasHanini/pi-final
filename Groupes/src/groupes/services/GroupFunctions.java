/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupes.services;

import MyConnection.MyConnection;
import Utilisateur.Utilisateur;
import com.jfoenix.controls.JFXButton;
import groupes.entities.Groupe;
import groupes.entities.TempUser;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 *
 * @author hammeda
 */
public class GroupFunctions {
    
    int idOwner=1;
    
public void showOwnedGroups(TableView<Groupe> OwnedGtable, TableColumn<Groupe, String> nameColumn, TableColumn<Groupe, Integer> sizeColumn) throws SQLException {
    try {
        updateAllGroupsSize();
        List<Integer> groupIds = getGids(idOwner);
        ObservableList<Groupe> groupList = FXCollections.observableArrayList();

        if (groupIds.isEmpty()) {
            
        } else {
            for (int groupId : groupIds) {
                String query = "SELECT Gid, nom, Size,logo FROM groups WHERE Gid = ?"; 

               java.sql.PreparedStatement preparedStatement2 = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query); 
                preparedStatement2.setInt(1, groupId);

                java.sql.ResultSet resultSet2 = preparedStatement2.executeQuery();

                while (resultSet2.next()) {
                    Groupe Grp = new Groupe(
                        resultSet2.getInt("Gid"),
                        resultSet2.getString("nom"),
                        resultSet2.getInt("Size"),
                        resultSet2.getString("logo")
                            
                    );

                    groupList.add(Grp);
                }
            }

            ObservableList<Groupe> groupObservableList = FXCollections.observableArrayList(groupList);
            OwnedGtable.setItems(groupObservableList);

           
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}



public List<Integer> getGids(long userId) throws SQLException {
    List<Integer> groupIds = new ArrayList<>();

    String sql = "SELECT GroupID FROM membre WHERE userId = ? AND Role = ?";
   java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql); 
    preparedStatement.setLong(1, userId);
    preparedStatement.setString(2, "Owner");

    java.sql.ResultSet resultSet = preparedStatement.executeQuery();

    while (resultSet.next()) {
        int groupId = resultSet.getInt("GroupID");
        groupIds.add(groupId);
    }

    return groupIds;
}

public void navigateToFXML(String fxmlFileName,JFXButton btn) throws IOException {
 
       Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
          FXMLLoader groupPanelLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent groupPanelRoot = groupPanelLoader.load();
            Scene groupPanelScene = new Scene(groupPanelRoot);
         Stage newStage = new Stage();
            newStage.setScene(groupPanelScene);
            newStage.show();
}
public void navigateIMGToFXML(String fxmlFileName,ImageView img) throws IOException {
 
       Stage stage = (Stage) img.getScene().getWindow();
            stage.close();
          FXMLLoader groupPanelLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent groupPanelRoot = groupPanelLoader.load();
            Scene groupPanelScene = new Scene(groupPanelRoot);
         Stage newStage = new Stage();
            newStage.setScene(groupPanelScene);
            newStage.show();
}

    public void GetMemList(Text GPName,TableView<Utilisateur> MemGTable,TableColumn<Utilisateur, String> NomCol,TableColumn<Utilisateur, String> PrenomCol,TableColumn<Utilisateur, String> EmailCol,TableColumn<Utilisateur, String> DateNColl,TableColumn<Utilisateur, Integer> NumberCol) throws SQLException{
        
           updateAllGroupsSize();
         
 ObservableList<Utilisateur> UserList = FXCollections.observableArrayList();
   
        String query = "SELECT * FROM utilisateur, membre, groups WHERE Utilisateur.id = membre.UserID AND membre.GroupID = ? AND membre.Role != ? AND groups.Gid = membre.GroupID";
java.sql.PreparedStatement preparedStatement2 = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query); 
System.out.println(GetSelectedGID());
         preparedStatement2.setInt(1, GetSelectedGID());
         preparedStatement2.setString(2, "Owner");
     
     java.sql.ResultSet resultSet2 = preparedStatement2.executeQuery();
     
     
     resultSet2 = preparedStatement2.executeQuery(); 

        while (resultSet2.next()) {
            Utilisateur user1 = new Utilisateur(
                resultSet2.getInt("id"),
                resultSet2.getString("nom"),
                resultSet2.getString("prenom"),
                resultSet2.getString("email"),
                     resultSet2.getInt("age"),
                resultSet2.getString("date_naissance")
               
                   
                    
            );
            System.out.println(user1.getNom());
            UserList.add(user1);
        }
        
            NomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
            PrenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            DateNColl.setCellValueFactory(new PropertyValueFactory<>("DateNaissance"));
            NumberCol.setCellValueFactory(new PropertyValueFactory<>("age"));
            


            
            MemGTable.setItems(UserList);
              
            System.out.println("Setting items fil TableView jawou behi AAA");
    setGName(GPName);
    
    
     }
    
    public void setGName(Text GPName) throws SQLException{
    int Gid=GetSelectedGID();
        System.out.println(Gid);
    
    String query = "SELECT nom FROM groups WHERE Gid=?";
    String Gname="Gname isn't recieving data";
   
   java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query); 
            preparedStatement.setInt(1, Gid);
           java.sql.ResultSet resultSet = preparedStatement.executeQuery(); 
            if (resultSet.next()) {
           
            Gname = resultSet.getString("nom");}
           System.out.println(Gname);
           
     GPName.setText(Gname);
        
    }
 
   
   public void FetchSelectedUser(TableView<Groupe> OwnedGtable){
   
   Groupe selectedGroup = OwnedGtable.getSelectionModel().getSelectedItem();
       System.out.println(selectedGroup.getId());
if (selectedGroup != null) {
    String groupName = selectedGroup.getName(); 
    System.out.println(groupName);
int groupId = -1;  


String selectGroupQuery = "SELECT Gid FROM groups WHERE nom = ?";

try {
   java.sql.PreparedStatement selectGroupStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(selectGroupQuery); 
    selectGroupStatement.setString(1, groupName);
    java.sql.ResultSet resultSet = selectGroupStatement.executeQuery();

    if (resultSet.next()) {
        groupId = resultSet.getInt("Gid");
    }

    resultSet.close();
    selectGroupStatement.close();
    
} catch (SQLException ex) {
    System.out.println("Error: " + ex.getMessage());
}

   if (groupId != -1) {
   
    String updateTempMailQuery = "UPDATE tempmail SET idGroup = ? WHERE id = 1";

    try {
       java.sql.PreparedStatement updateTempMailStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(updateTempMailQuery); 
        updateTempMailStatement.setInt(1, groupId); 
        int rowsUpdated = updateTempMailStatement.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("tempmail table updated successfully");
        } else {
            System.out.println("No records were updated");
        }

        updateTempMailStatement.close();
       
    } catch (SQLException ex) {
        System.out.println("Error: " + ex.getMessage());
    }
}  
}
   }
   
   
   
   
   public int GetSelectedGID() throws SQLException{
   int  Gid=-1;
String sql = "SELECT idGroup FROM tempmail WHERE id = ?";
 java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql) ;

preparedStatement.setInt(1, 1); 

java.sql.ResultSet resultSet = preparedStatement.executeQuery();

if (resultSet.next()) {
  Gid = resultSet.getInt("idGroup");
}
resultSet.close();
    preparedStatement.close();
return Gid;
   }
  public void updateAllGroupsSize() throws SQLException {
    String updateQuery = "UPDATE groups AS g SET g.size = (SELECT COUNT(*) FROM membre AS m WHERE m.GroupID = g.Gid)";

   java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(updateQuery) ;
    int rowsUpdated = preparedStatement.executeUpdate();

   
}
 




public void DeleteMember(Text GPName,TableView<Utilisateur> MemGTable,TableColumn<Utilisateur, String> NomCol,TableColumn<Utilisateur, String> PrenomCol,TableColumn<Utilisateur, String> EmailCol,TableColumn<Utilisateur, String> DateNColl,TableColumn<Utilisateur, Integer> NumberCol) throws SQLException{
 
        int index = MemGTable.getSelectionModel().getSelectedIndex();
    
    if (index <= -1) {
        return;
    }
    int Gid=GetSelectedGID();
   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove the member : " + DateNColl.getCellData(index) + "?");
         Optional<ButtonType> result = alert.showAndWait();
            

if (result.isPresent() && result.get() == ButtonType.OK) {
   String sql = "SELECT GroupID FROM membre WHERE userId = ? AND Role = ?";
    java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql) ;
             
   
    preparedStatement.setInt(1, idOwner);
    preparedStatement.setString(2, "Owner");

               java.sql.ResultSet resultSet = preparedStatement.executeQuery();

    if (resultSet.next()) {
       
      
    }
    String email = DateNColl.getCellData(index);
    
      String sqlDelete = "DELETE FROM membre WHERE GroupID = ? AND UserID = (SELECT id FROM utilisateur WHERE email = ?)";
java.sql.PreparedStatement deleteStatement = MyConnection.getInstance().getCnx().prepareStatement(sqlDelete);
deleteStatement.setInt(1, Gid);
deleteStatement.setString(2, email);
int affectedRows = deleteStatement.executeUpdate();
        if (affectedRows > 0) {
        System.out.println("Group Member Deleted");
    } else {
        System.out.println("nothing happened ");
    }}
        updateAllGroupsSize();
 GetMemList(GPName,MemGTable,NomCol,PrenomCol,EmailCol,DateNColl,NumberCol);
 
}  






public void AddGmember(TableView<Utilisateur> GTable,TableColumn<Utilisateur, String> TName,TableColumn<Utilisateur, String> TPrenom,TableView<TempUser> GmembersTable,TableColumn<Utilisateur, String> TEmail,TableColumn<Utilisateur, String> GNameCell,TableColumn<Utilisateur, String> GEmailCell){

  int index = GTable.getSelectionModel().getSelectedIndex();
    
    if (index <= -1) {
        return;
    }
    
    String nom = TName.getCellData(index);
    String email = TEmail.getCellData(index);


    
    TempUser tempUser = new TempUser("","");
    tempUser.setName(nom);
    tempUser.setEmail(email);

      
        ObservableList<TempUser> existingData = GmembersTable.getItems();
boolean isDuplicate = false;

for (TempUser existingUser : existingData) {
    if (existingUser.getName().equals(nom) && existingUser.getEmail().equals(email)) {
        isDuplicate = true;
        break;
    }
}
if (!isDuplicate) {
    existingData.add(tempUser);
     GNameCell.setCellValueFactory(new PropertyValueFactory<>("name"));
     GEmailCell.setCellValueFactory(new PropertyValueFactory<>("Email"));


}
else System.out.println("already added ");
}

public int getGid(int ID) throws SQLException{
int Gid = 0 ;


    String sql = "SELECT GroupID FROM membre WHERE userId = ? AND Role = ?";
 java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql) ;
             
   
    preparedStatement.setInt(1, idOwner);
    preparedStatement.setString(2, "Owner");

               
   
   java.sql.ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
       
        Gid = resultSet.getInt("GroupiD"); // GroupID
    }
return Gid;


}
public void affichageMems(Text GGNameText,TableColumn<Utilisateur, String>GNameCell,TableColumn<Utilisateur, String>GEmailCell) throws SQLException{
    

setGName(GGNameText);
     
 ObservableList<Utilisateur> UserList = FXCollections.observableArrayList();

        String query = "SELECT * FROM utilisateur,membre,groups WHERE utilisateur.id=membre.UserID AND groups.Gid=? AND Membre.Role!=?";
      java.sql.PreparedStatement preparedStatement2 = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query) ;
         preparedStatement2.setInt(1, GetSelectedGID());
         preparedStatement2.setString(2, "Owner");
     
     java.sql.ResultSet resultSet2 = preparedStatement2.executeQuery();
     
     
     

        while (resultSet2.next()) {
            Utilisateur user1 = new Utilisateur(
                resultSet2.getLong("id"),
                resultSet2.getString("nom"),
                resultSet2.getString("prenom"),
                     resultSet2.getString("date_naissance"),
                resultSet2.getString("email")
               
            );
            System.out.println(user1.getNom());
            UserList.add(user1);
        }
          
           
                GNameCell.setCellValueFactory(new PropertyValueFactory<>("nom"));
                GEmailCell.setCellValueFactory(new PropertyValueFactory<>("email"));
                System.out.println("affichagemems() mrigl");
    }


public void confirm ( TableView<TempUser>GmembersTable,JFXButton ConfirmGAbtn) throws SQLException{

int test=0;
    
        
    ObservableList<TempUser> data = GmembersTable.getItems();
             int Gid=GetSelectedGID();
    
      String insertMem = "INSERT IGNORE INTO membre (GroupID, UserID,Role) VALUES (?, ?,?)";
   java.sql.PreparedStatement PreparedSInsertMem = MyConnection.getInstance().getCnx()
                                    .prepareStatement(insertMem) ;
     //fetch all the data mel Gmembers w hothom fil members + Gid()
     for (TempUser user : data) {
     
      String email = user.getEmail();
      String querySql = "SELECT * FROM Utilisateur WHERE email = ?";
       java.sql.PreparedStatement queryStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(querySql) ;
       queryStatement.setString(1, email);
          java.sql.ResultSet  resultSet2 = queryStatement.executeQuery();
            
          
             if (resultSet2.next()) {
            int userId = resultSet2.getInt("id");
            PreparedSInsertMem.setInt(1, Gid);
            PreparedSInsertMem.setInt(2, userId);
            PreparedSInsertMem.setString(3, "Member");
               PreparedSInsertMem.addBatch();
               test=1;
             }
              resultSet2.close();
        queryStatement.close();
    }
          PreparedSInsertMem.executeBatch();
updateAllGroupsSize();
     
        
   
         }





  
    public ObservableList<Utilisateur> GetUserList() throws SQLException{
        
           ObservableList<Utilisateur> UserList = FXCollections.observableArrayList();

    String query = "SELECT * FROM utilisateur WHERE id!=?";
    
        java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query) ;
            preparedStatement.setInt(1, idOwner);
           java.sql.ResultSet resultSet = preparedStatement.executeQuery(); 

        while (resultSet.next()) {
            Utilisateur user1 = new Utilisateur(
                resultSet.getLong("id"),
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
                     resultSet.getString("date_naissance"),
                     resultSet.getInt("age"),
               resultSet.getString("email")
               
                    
                    
            );
           
            UserList.add(user1);
            System.out.println(user1.getNom());
        }
    
    
    
    
    
    
    
    
    return UserList;
    
    
    }
    
    
    public void importerImg (AnchorPane main,ImageView img,String path){
    
}
          
    
    
    public void DeleteGroupF(TableView<Groupe>OwnedGtable,TableColumn<Groupe, String>nameColumn) throws SQLException{
    Groupe selectedGroup = OwnedGtable.getSelectionModel().getSelectedItem();

if (selectedGroup != null) {
    String groupName = nameColumn.getCellData(selectedGroup);
   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure you want to delete the group: " + groupName + "?");
            Optional<ButtonType> result = alert.showAndWait();
            

        java.sql.Connection connection = MyConnection.getInstance().getCnx();
if (result.isPresent() && result.get() == ButtonType.OK) {
        connection.setAutoCommit(false);

        String deleteMembersQuery = "DELETE FROM membre WHERE membre.GroupID IN (SELECT Gid FROM groups WHERE nom = ?)";
        java.sql.PreparedStatement deleteMembersStatement = connection.prepareStatement(deleteMembersQuery);
        deleteMembersStatement.setString(1, groupName);
        deleteMembersStatement.executeUpdate();


       
        String deleteGroupQuery = "DELETE FROM groups WHERE nom = ?";
        java.sql.PreparedStatement deleteGroupStatement = connection.prepareStatement(deleteGroupQuery);
        deleteGroupStatement.setString(1, groupName);
        deleteGroupStatement.executeUpdate();

        
        connection.commit();

        
        String deletedGroupName = groupName;

       
       

      
        System.out.println("Deleted group: " + deletedGroupName);

        try {
          
            connection.rollback();
        } catch (SQLException rollbackException) {
            rollbackException.printStackTrace();
        }
        } else {
    System.out.println("No item selected.");
}}else {
            
            System.out.println("Deletion canceled.");
    
}
}
    public boolean groupExists(String groupName) {
    boolean exists = false;

    try {

       
        String query = "SELECT COUNT(*) FROM groups WHERE nom = ?";
           java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query) ;
        preparedStatement.setString(1, groupName);

    
        java.sql.ResultSet resultSet = preparedStatement.executeQuery();

        
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            exists = count > 0;
        }

        resultSet.close();
        preparedStatement.close();
      
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return exists;
}
    
    
    
    
    
    public List<String> getEmailsOfGroupMembers() {
    List<String> emails = new ArrayList<>();
    String sql = "SELECT email FROM utilisateur u JOIN membre m ON u.id = m.UserID WHERE m.GroupID = ? AND m.Role != 'Owner'";

try {
          java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql) ;
        
            preparedStatement.setInt(1, GetSelectedGID());
       

        try (java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                emails.add(email);
            }
        }
         } catch (SQLException ex) {
           ex.getMessage();
        }

      
    return emails;
}
     public ObservableList<Utilisateur> GetAllUserList() throws SQLException{
        
           ObservableList<Utilisateur> UserList = FXCollections.observableArrayList();

    String query = "SELECT * FROM utilisateur ";
    
    try ( java.sql.PreparedStatement pst = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query);
           java.sql.ResultSet resultSet = pst.executeQuery()) {
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (resultSet.next()) {
            Utilisateur user1 = new Utilisateur(
                resultSet.getLong("id"),
                resultSet.getString("nom"),
                resultSet.getString("prenom"),
           resultSet.getString("date_naissance"),
                   resultSet.getInt("Age"),
                 resultSet.getString("email")
              
                    
                    
            );
           
            UserList.add(user1);
            System.out.println(user1.getNom());
        }
    
    
    
    
    
    
    }
    
    return UserList;
    
    
    }
     
     public String getOwnerName(int GroupId) throws SQLException {
  String ownerName=null;

   
        String sql = "SELECT utilisateur .nom FROM utilisateur  " +
                     "INNER JOIN membre ON utilisateur .id = membre.userId " +
                     "WHERE membre.GroupID = ? AND membre.Role = 'Owner'";
java.sql.PreparedStatement pst = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql);
        pst.setInt(1, GetSelectedGID());

         java.sql.ResultSet resultSet = pst.executeQuery();

        if (resultSet.next()) {
         ownerName = resultSet.getString("nom");
        }
   
   

    return ownerName;
}
     
     
     
     
public String getGroupName() throws SQLException {
    String groupName = null;
    String sql = "SELECT nom FROM groups WHERE Gid = ?";
    
    try (java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(sql)) {
        preparedStatement.setInt(1, GetSelectedGID());
        java.sql.ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            groupName = resultSet.getString("nom");
        }
    }

    return groupName;
}

}