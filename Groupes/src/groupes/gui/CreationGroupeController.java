/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package groupes.gui;

import MyConnection.MyConnection;
import Utilisateur.Utilisateur;
import com.jfoenix.controls.JFXButton;
import groupes.services.GroupFunctions;
import groupes.services.MiscFunctions;
import groupes.entities.TempUser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hammeda
 */
public class CreationGroupeController implements Initializable {
Utilisateur current;
     public void setUtilisateur(Utilisateur current){
        this.current=current;
         idOwner=this.current.getId();
    }
    @FXML
    private TableView<Utilisateur> GTable;
    @FXML
    private TableColumn<Utilisateur, String> TName;
    @FXML
    private TableColumn<Utilisateur, String> TPrenom;
    @FXML
    private TableView<TempUser> GmembersTable;
    @FXML
    private TableColumn<Utilisateur, String> TEmail;
    @FXML
    private TableColumn<Utilisateur, String> GNameCell;
    @FXML
    private TableColumn<Utilisateur, String> GEmailCell;
    @FXML
    private JFXButton AddGmember;
    @FXML
    private JFXButton DeleteGMbtn;
    @FXML
    private JFXButton ConfirmGAbtn;
    @FXML
    private TextField Gname;
long idOwner;
GroupFunctions GF=new GroupFunctions();
MiscFunctions MF=new MiscFunctions();
    @FXML
    private ImageView ReturnImg;
    @FXML
    private ImageView logo;
    @FXML
    private Button importbtn;
    @FXML
    private AnchorPane AnchorLogo;
    @FXML
    private Text GroupNError;
    @FXML
    private Text GGNameText;
    String path;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ShowUsersG();
    }    

    
    
    
    
    
    
   
    
    public void ShowUsersG() {
     
        try {
            ObservableList<Utilisateur> list=GF.GetUserList();
            TName.setCellValueFactory(new PropertyValueFactory<>("nom"));
            TPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            TEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            GTable.setItems(list);
            System.out.println("Setting items fil TableView jawou behi AAA");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
}

    @FXML
    private void AddGmember(MouseEvent event) {
  GF.AddGmember(GTable, TName, TPrenom, GmembersTable, TEmail, GNameCell, GEmailCell);
        
}

    @FXML
    private void DeleteGM(MouseEvent event) {
    

    TempUser selectedUser = GmembersTable.getSelectionModel().getSelectedItem();

if ( selectedUser!= null) {
    // Remove the selected item from the data source (ObservableList)
    ObservableList<TempUser> data = GmembersTable.getItems();
    data.remove(selectedUser);

    
    GmembersTable.refresh();
}
}

    @FXML
    private void ConfirmGA(MouseEvent event) throws SQLException, IOException {
        String GnameText = Gname.getText();
        
Image logoImage = logo.getImage();
if(logoImage == null){GroupNError.setText("Import a logo");} 



else if (!GnameText.isEmpty() && !GF.groupExists(GnameText) && !GnameText.matches("^[0-9].*")){
    String insertSql = "INSERT INTO groups (Gid, nom, size,logo) VALUES (0, ?, 0,?)";

     java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(insertSql);

preparedStatement.setString(1, GnameText);
preparedStatement.setString(2, path);
preparedStatement.executeUpdate();

String maxGidQuery = "SELECT MAX(Gid) FROM groups";

java.sql.PreparedStatement maxGidStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(maxGidQuery);
java.sql.ResultSet maxGidResult = maxGidStatement.executeQuery();
int index = 0;

if (maxGidResult.next()) {
    index = maxGidResult.getInt(1);
  
}
maxGidResult.close();
maxGidStatement.close();

ObservableList<TempUser> data = GmembersTable.getItems();

String insertMemQuery = "INSERT INTO membre (GroupID, UserID, Role) VALUES (?, ?, ?)";


java.sql.PreparedStatement InsertStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(insertMemQuery);
InsertStatement.setInt(1, index);
InsertStatement.setLong(2, idOwner);
InsertStatement.setString(3, "Owner");
InsertStatement.addBatch();

for (TempUser user : data) {
    String email = user.getEmail();
    String querySql = "SELECT * FROM utilisateur WHERE email = ?";
java.sql.PreparedStatement queryStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(querySql);   
    queryStatement.setString(1, email);
    java.sql.ResultSet resultSet2 = queryStatement.executeQuery();

    if (resultSet2.next()) {
        int userId = resultSet2.getInt("id");
        System.out.println("gid : " + index + "  id " + userId);
        InsertStatement.setInt(1, index);
        InsertStatement.setInt(2, userId);
        InsertStatement.setString(3, "Member");
        InsertStatement.addBatch();
    }
    resultSet2.close();
    queryStatement.close();
}

InsertStatement.executeBatch();
    GF.updateAllGroupsSize();
    
    Stage stage = (Stage) ConfirmGAbtn.getScene().getWindow();
            stage.close();
          FXMLLoader groupPanelLoader = new FXMLLoader(getClass().getResource("YourGroups.fxml"));
            Parent groupPanelRoot = groupPanelLoader.load();
            Scene groupPanelScene = new Scene(groupPanelRoot);
         Stage newStage = new Stage();
            newStage.setScene(groupPanelScene);
            newStage.show();
}
else if(GnameText.isEmpty()){GroupNError.setText("invalid name");
    }

else if(GF.groupExists(GnameText)){GroupNError.setText("Name Already Exists");}
else if(GnameText.matches("^[0-9].*")){GroupNError.setText("Names can't start with a number");}



    }

    @FXML
    private void Return(MouseEvent event) {
       try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("YourGroups.fxml"));
            Parent root = loader.load();
            
            
               YourGroupsController controllerGroupe =loader.getController();
              controllerGroupe.setUtilisateur(current);
              
            Stage cStage= (Stage) this.AddGmember.getScene().getWindow();
            cStage.setWidth(750);
            cStage.setHeight(600);
              
            AddGmember.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void importer(MouseEvent event) {
        GF.importerImg(AnchorLogo, logo,path);
         FileChooser open = new FileChooser();
         open.getExtensionFilters().add(new FileChooser.ExtensionFilter("open Image File","*png","*jpg"));
         
        File file = open.showOpenDialog(AnchorLogo.getScene().getWindow());

        if (file != null) {
           
path=file.getAbsolutePath();
             Image image = new Image(file.toURI().toString(), 101, 127, false, true);
              logo.setImage(image);
    
}
        System.out.println(path);
    }
    }
    



    
    

    
    
    
    
    
    
    
