/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Event.services;

import Event.entities.EventAdmin;

import Event.utils.MyConnection;




import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;


    
/**
 *
 * @author ASUS
 */
public class EventAdminService {

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
public void ajoutEventAdmin(EventAdmin e, String path) throws IOException, SQLException {
    // Validation du nom
     
        
          if (!validerNom(e.getNom_a())) {
        // Afficher un message d'erreur si le nom est invalide
        // Vous pouvez utiliser une boîte de dialogue ou une autre méthode pour afficher l'erreur à l'utilisateur.
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
 
        alert.setContentText("Le nom doit contenir uniquement des lettres ");
        alert.showAndWait();
        return; // Sortir de la méthode si la validation échoue.
    }
    
    boolean isUnique = false; 
    
         String selectQuery = "SELECT * FROM eventadmin WHERE nom_a = ?";
        // Prepare the SQL INSERT query
        String insertQuery = "INSERT INTO eventadmin (id_a, nom_a, date_a, lieu_a, description_a, image_a, prix_a,path_qr) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        // Convert the JavaFX LocalDate to a SQL Date
        LocalDate localdate = e.getDate_a();
        Date sqlDate = Date.valueOf(localdate);

        /*// Load the image and convert it to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", baos);
        byte[] imageData = baos.toByteArray();*/
        
        
        PreparedStatement pst = myConx.prepareStatement(selectQuery);
        pst.setString(1, e.getNom_a());

        ResultSet resultSet = pst.executeQuery();
        
        
        if (resultSet.next()) {
            // An event with the same name already exists, set the flag to false
            isUnique = false;
            
               Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Event Name Already Exists");
            alert.setContentText("An event with the same name already exists.");
            alert.showAndWait();
            
        }    
        
        else {
        // Prepare the SQL statement
        pst = myConx.prepareStatement(insertQuery);
        pst.setInt(1, e.getId_a());
        pst.setString(2, e.getNom_a());
        pst.setDate(3, sqlDate);
        pst.setString(4, e.getLieu_a());
        pst.setString(5, e.getDescription_a());
        pst.setString(6, path);
        
        pst.setInt(7, e.getPrix_a());
pst.setString(8, e.getPathQR());
        // Execute the SQL statement to insert the event
        pst.executeUpdate();

        // Optionally, you can handle success or display a message here

}
       

}
    
    
    
public void modifEventAdmin(EventAdmin e, String path) throws IOException {
    try {
        // Prepare the SQL UPDATE query
        String updateQuery = "UPDATE eventadmin SET nom_a = ?, date_a = ?, lieu_a = ?, description_a = ?, image_a = ?, prix_a = ? WHERE id_a = ?";

        // Convert the JavaFX LocalDate to a SQL Date
        LocalDate localdate = e.getDate_a();
        Date sqlDate = Date.valueOf(localdate);

        /*// Load the image and convert it to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", baos);
        byte[] imageData = baos.toByteArray();*/

        // Prepare the SQL statement
        pst = myConx.prepareStatement(updateQuery);
        
                  

        
        pst.setString(1, e.getNom_a());
        pst.setDate(2, sqlDate);
        pst.setString(3,e.getLieu_a());
        pst.setString(4, e.getDescription_a());
        pst.setString(5, path);
        pst.setInt(6, e.getPrix_a());
        
        pst.setInt(7, e.getId_a());
        

        // Execute the SQL statement to update the event
        pst.executeUpdate();

       /* // Display a confirmation message using the event's name
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to UPDATE your event: " + e.getNom_a() + "?");
        Optional<ButtonType> option = alert.showAndWait();*/

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



    
    
    public int supprimerEventAdmin(EventAdmin e) {
    try {
        // Get the ID of the EventAdmin you want to delete
        int eventId = e.getId_a();

        Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDelete.setTitle("Confirm Deletion");
        confirmDelete.setHeaderText("Delete Event Confirmation");
        confirmDelete.setContentText("Are you sure you want to delete this event?");

        Optional<ButtonType> result = confirmDelete.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            String deleteQuery = "DELETE FROM eventadmin WHERE id_a = ?";

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


   

    
    
    
        public void ShowReservation (TableColumn<EventAdmin, String> colnom_a,TableColumn<EventAdmin,String>coldate_a,TableColumn<EventAdmin,String>collieu_a,
            TableColumn<EventAdmin,String>coldesc_a,TableColumn<EventAdmin,String> colprix_a,TableView<EventAdmin> tabResv_a) throws SQLException {
         
        
         ObservableList< EventAdmin> resvlist =getEventAdminlist();
        
     
          
         
        colnom_a.setCellValueFactory(new PropertyValueFactory<>("nom_a")); ///nom_a de clase eventadmin
        coldate_a.setCellValueFactory(new PropertyValueFactory<>("date_a"));
        collieu_a.setCellValueFactory(new PropertyValueFactory<>("lieu_a"));
        coldesc_a.setCellValueFactory(new PropertyValueFactory<>("description_a"));
       
        colprix_a.setCellValueFactory(new PropertyValueFactory<>("prix_a"));
        tabResv_a.setItems(resvlist);
}
         private ObservableList<EventAdmin> getEventAdminlist() throws SQLException {
         
          
     
     
   
     String query = "SELECT * FROM eventadmin";
 
 
            pst = myConx.prepareStatement(query);
              ResultSet rs =pst.executeQuery(query);
             ObservableList<EventAdmin> EventAdminlist =FXCollections.observableArrayList();
            while (rs.next()){
              EventAdminlist.add(new EventAdmin(
    rs.getInt("id_a"),         ///id_a de DB
    rs.getString("nom_a"),
    rs.getDate("date_a").toLocalDate(),
    rs.getString("lieu_a"),
    rs.getString("description_a"),
    rs.getString("image_a"),
    rs.getInt("prix_a"),
                      rs.getString("path_qr")
));
                      
                        } 
     
     
        return EventAdminlist;
         
     }
         
         
         
       /* private byte[] loadImageFromResultSet(ResultSet rs) throws SQLException {
    // Assuming the image is stored as a BLOB in the database
    InputStream inputStream = rs.getBinaryStream("image_a");
    
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

    
     
     public String imporeterImg (AnchorPane main,ImageView espaceImg_a){
        FileChooser open = new FileChooser();
         open.getExtensionFilters().add(new FileChooser.ExtensionFilter("open Image File","*png","*jpg"));
         
        File file = open.showOpenDialog(main.getScene().getWindow());

        if (file != null) {
           

             Image image = new Image(file.toURI().toString(), 101, 127, false, true);
              espaceImg_a.setImage(image);
             String temp=file.getAbsolutePath();
             
            
 return temp;
}
       return null;
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
    
  /*  private byte[] convertImageToByteArray(Image image) throws IOException {
    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, "png", baos);
    return baos.toByteArray();
}*/
   

