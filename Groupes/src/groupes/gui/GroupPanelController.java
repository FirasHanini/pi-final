/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package groupes.gui;

import MyConnection.MyConnection;
import Utilisateur.Utilisateur;
import com.jfoenix.controls.JFXButton;
import groupes.services.GroupFunctions;
import groupes.entities.Member;
import groupes.services.MiscFunctions;
import gui.homePage.HomPageController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hammeda
 */
public class GroupPanelController implements Initializable {

MiscFunctions MF=new MiscFunctions();
Utilisateur current;
     public void setUtilisateur(Utilisateur current){
        this.current=current;
         OwnerID=this.current.getId();
         System.out.println("AAAAAAAAAA" + current);
    }
    @FXML
    private TableView<Utilisateur> MemGTable;
    @FXML
    private JFXButton AddMbtn;
    @FXML
    private JFXButton DeleteMembtn;
    @FXML
    private TableColumn<Utilisateur, String> NomCol;
    @FXML
    private TableColumn<Utilisateur, String> PrenomCol;
    @FXML
    private TableColumn<Utilisateur, String> EmailCol;
    @FXML
    private TableColumn<Utilisateur, String> DateNColl;
    @FXML
    private TableColumn<Utilisateur, Integer> NumberCol;
    @FXML
    private Text GPName=new Text();
    @FXML
    private TextField NomSearch;
    @FXML
    private ComboBox<String> YearCB= new ComboBox<>();
    @FXML
    private JFXButton SearchGPbtn;
    @FXML
    private Text FilterText=new Text();
    @FXML
    private ImageView Resetimg;
    @FXML
    private Text GroupPanelTXT=new Text();
    @FXML
    private Text NameTXT=new Text();
    @FXML
    private Text YearTXT=new Text();
    @FXML
    private Text LanguageText=new Text();
    long OwnerID;
    @FXML
    private TextField GNChange;
    @FXML
    private ImageView ConfirmImg;
       GroupFunctions GF=new GroupFunctions();
       
    @FXML
    private ImageView ReturnImg;
    @FXML
    private AnchorPane LogoAnchor2;
    @FXML
    private ImageView logo2;
    @FXML
    private Text GGNameText;
    @FXML
    private Text GroupNError;
    @FXML
    private ImageView Excelimg;
    @FXML
    private ImageView Mail;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    Platform.runLater(() -> {
        try {
            GF.GetMemList(GPName, MemGTable, NomCol, PrenomCol, EmailCol, DateNColl, NumberCol);
             MF.setLogoForGroup(GF.GetSelectedGID(), logo2);
        } catch (SQLException ex) {
            Logger.getLogger(GroupPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int year = 1920; year <= 2005; year++) {
            YearCB.getItems().add(String.valueOf(year));
        }
    });
}
 

    @FXML
    private void AddMember(MouseEvent event) throws IOException {
       try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddMem.fxml"));
            Parent root = loader.load();
            
            
               AddMemController controllerGroupe =loader.getController();
              controllerGroupe.setUtilisateur(current);
              
            Stage cStage= (Stage) this.AddMbtn.getScene().getWindow();
            cStage.setWidth(860);
            cStage.setHeight(650);
              
            AddMbtn.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void DeleteMem(MouseEvent event) throws SQLException {
        
        GF.DeleteMember(GPName,MemGTable,NomCol,PrenomCol,EmailCol,DateNColl,NumberCol) ;

    }
    
    
     
    
   
    public int getGid(int ID) throws SQLException{
int Gid = 0 ;


    String sql = "SELECT GroupID FROM membre WHERE userId = ? AND Role = ?";
 java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql);   
   
    preparedStatement.setLong(1, OwnerID);
    preparedStatement.setString(2, "Owner");

               
   
   java.sql.ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
       
        Gid = resultSet.getInt("GroupiD"); 
    }
return Gid;


}
     
     
     
     
     
     
     
     
     
     
     
    private void setGName() throws SQLException{
    int Gid=GF.GetSelectedGID();
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

    @FXML
    private void SearchGP(MouseEvent event) throws SQLException {
       
        String textFieldValue = NomSearch.getText();
        int Gid=GF.GetSelectedGID();
         ObservableList<Utilisateur> UserList = FXCollections.observableArrayList();

       String selectedYearString = YearCB.getValue();
       
        java.sql.PreparedStatement preparedStatement;
    if(("".equals(textFieldValue))&&((YearCB.getValue() == null))){
    FilterText.setText("No Filters Were Applied");
    }
    else{FilterText.setText("");
    if((!"".equals(textFieldValue))&&((YearCB.getValue() == null))){
    String sql = "SELECT * FROM Utilisateur JOIN membre ON Utilisateur.id = membre.UserID WHERE Utilisateur.nom = ? AND membre.GroupID = ? AND membre.Role != 'Owner'";
  
    preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql); 
   preparedStatement.setString(1, textFieldValue);
   preparedStatement.setInt(2, Gid);
   
   }
    else if(("".equals(textFieldValue))&&((YearCB.getValue() != null))){
    String sql = "SELECT * FROM Utilisateur JOIN membre ON Utilisateur.id = membre.UserID WHERE YEAR(date_naissance) = ? AND membre.GroupID = ? AND membre.Role != 'Owner'";
  Integer.parseInt(selectedYearString);
 preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql); 
   preparedStatement.setString(1, selectedYearString);
   preparedStatement.setInt(2, Gid);
   
    }
    else{
     String sql = "SELECT * FROM Utilisateur JOIN membre ON Utilisateur.id = membre.UserID WHERE YEAR(date_naissance) = ? AND user.nom=? AND user.id=membre.UserID AND membre.GroupID=? AND Membre.Role!=?";
  Integer.parseInt(selectedYearString);
    preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql); 
   preparedStatement.setString(1, selectedYearString);
   preparedStatement.setString(2, textFieldValue);
   preparedStatement.setInt(3, Gid);
   preparedStatement.setString(4, "Owner");
    }
    
        java.sql.ResultSet resultSet2 = preparedStatement.executeQuery(); 
 
        
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
        setGName();
    
    
    }
    
    
        
        
        
    }

    @FXML
    private void ResetFilters(MouseEvent event) throws SQLException {
        NomSearch.setText("");
        YearCB.setValue(null);
         GF.GetMemList(GPName,MemGTable,NomCol,PrenomCol,EmailCol,DateNColl,NumberCol);
    }
private boolean isFrench = false;
    @FXML
    private void SwitchLanguage(MouseEvent event) {
        isFrench = !isFrench;

        GroupPanelTXT.setText("panneau de controle");
        NameTXT.setText("Nom :");
        YearTXT.setText("Annee :");
        LanguageText.setText("fr");
        SearchGPbtn.setText("Rechercher");
        AddMbtn.setText("Ajouter");
        DeleteMembtn.setText("Effacer");
        
        if (isFrench) {
            
            GroupPanelTXT.setText("panneau de controle");
            NameTXT.setText("Nom :");
            YearTXT.setText("Annee :");
            LanguageText.setText("fr");
            SearchGPbtn.setText("Rechercher");
            AddMbtn.setText("Ajouter");
            DeleteMembtn.setText("Effacer");
        } else {
          
            GroupPanelTXT.setText("Control Panel");
            NameTXT.setText("Name:");
            YearTXT.setText("Year:");
            LanguageText.setText("en");
            SearchGPbtn.setText("Search");
            AddMbtn.setText("Add");
            DeleteMembtn.setText("Delete");
        }
    }
       
    
    
    
    

    @FXML
    private void ConfirmGNAME(MouseEvent event) {
        String NewName=GNChange.getText();
        
        
        if(NewName.isEmpty()){GroupNError.setText("invalid name");}

else if(GF.groupExists(NewName)){GroupNError.setText("Name Already Exists");}
else if(NewName.matches("^[0-9].*")){GroupNError.setText("Names can't start with a number");}

        
    
        else {
             try {
         String UpdateGNQuery="UPDATE groups SET nom = ? WHERE Gid = ?;";
    java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(UpdateGNQuery); 
   preparedStatement.setString(1, NewName);
   preparedStatement.setInt(2, GF.GetSelectedGID());
   int rowsUpdated;
           
                rowsUpdated = preparedStatement.executeUpdate();
            
   if (rowsUpdated > 0) {
        System.out.println("Group Name updated successfully");
        setGName();
    } else {
        System.out.println("No updates");
    }
   } catch (SQLException ex) {
             System.out.println(ex.getMessage());            }
}
        

    }

    @FXML
    private void Return(MouseEvent event) {
     try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("YourGroups.fxml"));
            Parent root = loader.load();
            
            
               YourGroupsController controllerGroupe =loader.getController();
              controllerGroupe.setUtilisateur(current);
              
            Stage cStage= (Stage) this.AddMbtn.getScene().getWindow();
            cStage.setWidth(750);
            cStage.setHeight(600);
              
            AddMbtn.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void Export(MouseEvent event) {
         try {
        int a = GF.GetSelectedGID();
   
        List<Member> list =MF.getMembersByGid(a);
   MF.Export(list, "C:\\Users\\hamad\\OneDrive\\Desktop\\groups\\"+GPName.getText()+".xls");
      
        
         } catch (SQLException ex) {
        ex.getMessage();
    }
    }

    @FXML
    private void SendMail(MouseEvent event) {
     try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/MailGroup.fxml"));
        Parent root = loader.load();
        Stage mailGroupStage = new Stage();
        mailGroupStage.setScene(new Scene(root));
        mailGroupStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    

}



