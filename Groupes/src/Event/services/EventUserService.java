/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Event.services;


import Event.entities.EventUser;
import Event.utils.MyConnection;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 *
 * @author ASUS
 */
public class EventUserService {
     MyConnection conx= MyConnection.getInstance();
    Connection myConx=conx.getConnection();
    String query = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
  
    public boolean validerNom(String nom) {
        String regex = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nom);

        return matcher.matches();
    }
public void ajoutEventUser(EventUser e, String path) throws IOException, SQLException {
    boolean isUnique = false;
    
    String selectQuery = "SELECT * FROM eventadmin WHERE nom_a = ?";
    String insertQuery = "INSERT INTO eventuser (id, nom, date, lieu, description, image, prix,path_qr) VALUES (?, ?, ?, ?, ?, ?,? ,?)";
    String insertQuery2 = "INSERT INTO eventadmin (id_a, nom_a, date_a, lieu_a, description_a, image_a, prix_a,path_qr) VALUES (?, ?, ?, ?, ?, ?,? ,?)";
    
    LocalDate localdate = e.getDate_u();
    Date sqlDate = Date.valueOf(localdate);
    
    PreparedStatement pst = myConx.prepareStatement(selectQuery);
    pst.setString(1, e.getNom_u());
    ResultSet resultSet = pst.executeQuery();
    
    if (resultSet.next()) {
        isUnique = false;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Event Name Already Exists");
        alert.setContentText("An event with the same name already exists.");
        alert.showAndWait();
    } else {
        pst = myConx.prepareStatement(insertQuery);
        pst.setInt(1, e.getId_u());
        pst.setString(2, e.getNom_u());
        pst.setDate(3, sqlDate);
        pst.setString(4, e.getLieu_u());
        pst.setString(5, e.getDescription_u());
        pst.setString(6, path);
        pst.setInt(7, e.getPrix_u());
        pst.setString(8,e.getPathQR());
        pst.executeUpdate();
        
        // Insert into the eventadmin table with the same data
        PreparedStatement pst2 = myConx.prepareStatement(insertQuery2);
        pst2.setInt(1, e.getId_u());
        pst2.setString(2, e.getNom_u());
        pst2.setDate(3, sqlDate);
        pst2.setString(4, e.getLieu_u());
        pst2.setString(5, e.getDescription_u());
        pst2.setString(6, path);
        pst2.setInt(7, e.getPrix_u());
             pst2.setString(8,e.getPathQR());

        pst2.executeUpdate();
    }
}



    
    
        public void modifEventUser(EventUser e, String path) throws IOException {
    try {
        // Prepare the SQL UPDATE query
        String updateQuery = "UPDATE eventuser SET nom = ?, date = ?, lieu = ?, description = ?, image = ?, prix = ? WHERE id = ?";

        // Convert the JavaFX LocalDate to a SQL Date
        LocalDate localdate = e.getDate_u();
        Date sqlDate = Date.valueOf(localdate);

        // Prepare the SQL statement
        pst = myConx.prepareStatement(updateQuery);
 
        pst.setString(1, e.getNom_u());
        pst.setDate(2, sqlDate);
        pst.setString(3,e.getLieu_u() );
        pst.setString(4, e.getDescription_u());
        pst.setString(5, path);
        pst.setInt(6, e.getPrix_u());
        
        pst.setInt(7, e.getId_u());
        


        // Execute the SQL statement to update the event
        pst.executeUpdate();

        // Display a confirmation message using the event's name
      

        // Optionally, you can handle success or display a message here
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Handle errors or display an error message
    }
}



 /*Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE your event: " + tfnom_a.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();*/



    
    
    public int supprimerEventUser(EventUser e) {
    try {
        // Get the ID of the EventAdmin you want to delete
        int eventId = e.getId_u();

        Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDelete.setTitle("Confirm Deletion");
        confirmDelete.setHeaderText("Delete Event Confirmation");
        confirmDelete.setContentText("Are you sure you want to delete this event?");

        Optional<ButtonType> result = confirmDelete.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            String deleteQuery = "DELETE FROM eventuser WHERE id = ?";

            pst = myConx.prepareStatement(deleteQuery);
            pst.setInt(1, eventId);
            int rowsAffected = pst.executeUpdate();

            // Check the number of rows affected to determine if the deletion was successful
            if (rowsAffected > 0) {
                return 1; // Deletion successful
            } else {
                return 0; // No rows affected, possibly the record with the provided ID does not exist
            }
        } else {
            return 0; // Deletion canceled
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        return -1; // An error occurred during deletion
    }
}


   

    
    
    
        public void ShowReservation (TableColumn<EventUser, String> colnom_u,TableColumn<EventUser,String>coldate_u,TableColumn<EventUser,String>collieu_u,
            TableColumn<EventUser,String>coldesc_u,TableColumn<EventUser,String> colprix_u,TableView<EventUser> tabResv_u) throws SQLException {
         
        
         ObservableList< EventUser> resvlist =getEventUserList();
        
     
          
         
        colnom_u.setCellValueFactory(new PropertyValueFactory<>("nom_u"));
        coldate_u.setCellValueFactory(new PropertyValueFactory<>("date_u"));
        collieu_u.setCellValueFactory(new PropertyValueFactory<>("lieu_u"));
        coldesc_u.setCellValueFactory(new PropertyValueFactory<>("description_u"));
       
        colprix_u.setCellValueFactory(new PropertyValueFactory<>("prix_u"));
        tabResv_u.setItems(resvlist);
}
         private ObservableList<EventUser> getEventUserList() throws SQLException {
         
          
     
     
   
     String query = "SELECT * FROM eventuser";
 
 
            pst = myConx.prepareStatement(query);
              ResultSet rs =pst.executeQuery(query);
             ObservableList<EventUser> EventUserlist =FXCollections.observableArrayList();
            while (rs.next()){
              EventUserlist.add(new EventUser(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getDate("date").toLocalDate(),
                rs.getString("lieu"),
                rs.getString("description"),
                rs.getString("image"),
                rs.getInt("prix")
            ));
                      
                        } 
     
     
        return EventUserlist;
         
     }
      /*  private byte[] loadImageFromResultSet(ResultSet rs) throws SQLException {
    // Assuming the image is stored as a BLOB in the database
    InputStream inputStream = rs.getBinaryStream("image");
    
    if (inputStream != null) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    return null; // Return null if no image data was found
}*/

    
     
     public String imporeterImg (AnchorPane mainAnchorPane,ImageView espaceImg_u){
        FileChooser open = new FileChooser();
         open.getExtensionFilters().add(new FileChooser.ExtensionFilter("open Image File","*png","*jpg"));
         
        File file = open.showOpenDialog(mainAnchorPane.getScene().getWindow());

        if (file != null) {
            String paths = file.getAbsolutePath();

             Image image = new Image(file.toURI().toString(), 101, 127, false, true);
              espaceImg_u.setImage(image);
              return paths;
}
     else   return null;
}
     
     
    private String[] typeList ={"aaaa" ,"bbbbb","ccccccccc"}; 
     public void Combolieu(ComboBox combolieu_a){
        List<String> listp =new ArrayList<>();
        for (String data : typeList) {
            listp.add(data);
         
     }
         ObservableList listData = FXCollections.observableArrayList(listp);
        combolieu_a.setItems(listData);
}
}
