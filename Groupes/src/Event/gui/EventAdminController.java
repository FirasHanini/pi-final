/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Event.gui;

import Utilisateur.Utilisateur;
import Event.entities.EventAdmin;
import Event.services.EventAdminService;
import Event.utils.MyConnection;
import java.awt.image.BufferedImage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import gui.Admin.AdminDashboardController;
import gui.homePage.HomPageController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
  

public class EventAdminController implements Initializable {

    @FXML
    private TableView<EventAdmin> tabResv_a;
    @FXML
    private TableColumn<EventAdmin, String> colnom_a;
    
    @FXML
    private TableColumn<EventAdmin, String> coldate_a;
      @FXML
    private TableColumn<EventAdmin, String> collieu_a;
    @FXML
    private TableColumn<EventAdmin, String> coldesc_a;
    
    @FXML
    private ComboBox<String> combolieu_a= new ComboBox<>();;
    
    @FXML
    private TextField tfnom_a;
    
    @FXML
    private DatePicker daterev_a;
    @FXML
    private Button btajoutResv_a;
    @FXML
    private Button btsupResv_a;
    @FXML
    private Button btmodifResv_a;

    @FXML
    private Button btimgResv_a;
    @FXML
    private ImageView espaceImg_a;
    @FXML
    private AnchorPane main;
    @FXML
    private TableColumn<EventAdmin, String> colprix_a;
    @FXML
    private TextField tfprix_a;
    @FXML
    private TextArea txtdesc_a;
    Utilisateur current;
    @FXML
    private ImageView back;
    
    public void setUtilisateur(Utilisateur current){
        this.current=current;
    }
    
       String path;
   MyConnection conx= MyConnection.getInstance();
    Connection myConx=conx.getConnection();
    String query = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Initializes the controller class.
     */
        EventAdminService a =new EventAdminService();
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
            supprimerLignesDepassees();
        } catch (SQLException ex) {
            Logger.getLogger(EventAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<String> items = FXCollections.observableArrayList("ariana","beja","ben arous","bizert","tatwin","touzeur","tunis","jandouba","zaghwen","silyana","sousse",
                "sidi bouzid","safax","gbeli","gasrin","gafsa","kerwan","keef","mednin","mounastir","manouba","mahdia","nabeul");
            
            
        combolieu_a.setItems(items);

       
        EventAdminService aa =new EventAdminService();
        try {
            aa.ShowReservation(colnom_a, coldate_a, collieu_a, coldesc_a,colprix_a,tabResv_a);
        } catch (SQLException ex) {
            Logger.getLogger(EventAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            // TODO
            a.ShowReservation(colnom_a, coldate_a, collieu_a, coldesc_a, colprix_a, tabResv_a);
        } catch (SQLException ex) {
            Logger.getLogger(EventAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }


    @FXML
  private void Ajoutresv(MouseEvent event) {
    try {
        supprimerLignesDepassees();
        int id = 0; 
        String nom = tfnom_a.getText();
        LocalDate date = daterev_a.getValue();
        String lieu = combolieu_a.getSelectionModel().getSelectedItem().toString();
        String description = txtdesc_a.getText();
        
        
        Image image = espaceImg_a.getImage(); // Get the image from the ImageView
        int prix = Integer.parseInt(tfprix_a.getText());
        
       String dataQr= "Event  nom_a=" + nom + ", date_a=" + date + ", lieu_a=" + lieu + ", description_a=" + description +  ", prix_a=" + prix  + '}';
                       String datanameQr = generateRandomString(4);
                String myQr = generateQRCodeAndSave(dataQr,datanameQr);

        // Create an EventAdmin object and call ajoutEventAdmin with it
        EventAdmin e = new EventAdmin(id, nom, date, lieu, description,path, prix,myQr);
        a.ajoutEventAdmin(e, path);

        a.ShowReservation(colnom_a, coldate_a, collieu_a, coldesc_a, colprix_a, tabResv_a);
        // Optionally, you can handle success or display a message here
    } catch (Exception ex) {
        ex.printStackTrace();
        // Handle errors or display an error message
    }
  }
    public void supprimerLignesDepassees() throws SQLException {
    LocalDate now = LocalDate.now();

           String query = "DELETE FROM EventAdmin WHERE (DATEDIFF(?, date_a)) > 1";
        PreparedStatement deleteStatement = myConx.prepareStatement(query);
        deleteStatement.setDate(1, java.sql.Date.valueOf(now));
        int a=deleteStatement.executeUpdate();
        
        System.out.println(a);
              String query2 = "DELETE FROM EventUser WHERE DATEDIFF(?, date) > 1";
        PreparedStatement deleteStatement2 = myConx.prepareStatement(query2);
        deleteStatement2.setDate(1, java.sql.Date.valueOf(now));
        int b=deleteStatement2.executeUpdate();
        

}

///////////////////QR/////////////////////
  
  public String generateQRCodeAndSave(String text, String fileName) throws WriterException {
        // Generate the QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Convert the BufferedImage to a JavaFX Image
        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);

        // Save the image to the specified directory
        String dir = System.getProperty("user.dir");
        String directoryPath = dir+"\\src\\Event\\images";
        Path directory = Paths.get(directoryPath);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String filePath = directoryPath + "/" + fileName + ".png";
        File file = new File(filePath);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(fxImage, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

/*private byte[] convertImageToByteArray(Image image) throws IOException {
    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, "png", baos);
    return baos.toByteArray();
}*/
  
  
  
  ///////////////////PDF///////////////////////
  


@FXML
private void Modresv(MouseEvent event) {

     try {
         
        int id = 0; // You can set the ID as needed, or let the database auto-generate it
        String nom = tfnom_a.getText();
        LocalDate date = daterev_a.getValue();
        String lieu = combolieu_a.getSelectionModel().getSelectedItem().toString();
        String description = txtdesc_a.getText();
        
        
       
        int prix = Integer.parseInt(tfprix_a.getText());
        

       
        // Create an EventAdmin object and call ajoutEventAdmin with it
         
        int selectedIndex = tabResv_a.getSelectionModel().getSelectedIndex();
        int selectedId = -1; // Initialize to a default value

if (selectedIndex >= 0) {
    // If a row is selected, retrieve the ID from the database
    EventAdmin selectedEvent = tabResv_a.getSelectionModel().getSelectedItem();
    selectedId = selectedEvent.getId_a();
    
}
            EventAdmin e = new EventAdmin(selectedId, nom, date, lieu, description,path, prix);
            a.modifEventAdmin(e, path);


        a.ShowReservation(colnom_a, coldate_a, collieu_a, coldesc_a, colprix_a, tabResv_a);
        // Optionally, you can handle success or display a message here
    } catch (Exception ex) {
        ex.printStackTrace();
        // Handle errors or display an error message
    }
}






    @FXML
    private void Supresv(MouseEvent event) throws SQLException {
         EventAdmin selectedEvent = tabResv_a.getSelectionModel().getSelectedItem();

    if (selectedEvent != null) {
        int result = a.supprimerEventAdmin(selectedEvent);

        if (result == 1) {
            // Deletion successful, you can update the TableView or take any other action
            a.ShowReservation(colnom_a, coldate_a, collieu_a, coldesc_a, colprix_a, tabResv_a);
        } else if (result == 0) {
            // No records were deleted, possibly the record does not exist
        } else {
            // An error occurred during deletion
        }
    }
    a.ShowReservation(colnom_a, coldate_a, collieu_a, coldesc_a, colprix_a, tabResv_a);
    }

  
    @FXML
    private void tab(MouseEvent event) {
        EventAdmin selectedEvent = tabResv_a.getSelectionModel().getSelectedItem();

if (selectedEvent != null) {
    String nom_a = selectedEvent.getNom_a();
    String lieu_a = selectedEvent.getLieu_a();   
    // Query the database to get the image data and other information based on nom_a and lieu_a
    String query = "SELECT image_a, date_a, prix_a, description_a FROM eventadmin WHERE nom_a = ? AND lieu_a = ?";
    try {
        pst = myConx.prepareStatement(query);
        pst.setString(1, nom_a);
        pst.setString(2, lieu_a);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
             path=rs.getString("image_a");
            File imageFile = new File(path);
            Image image = new Image(imageFile.toURI().toString());
            espaceImg_a.setImage(image);
            System.out.println(path);
            // Fill other JavaFX controls
            tfnom_a.setText(nom_a); // Fill TextField
            daterev_a.setValue(rs.getDate("date_a").toLocalDate()); // Fill DatePicker
            combolieu_a.setValue(lieu_a); // Fill ComboBox
            tfprix_a.setText(String.valueOf(rs.getInt("prix_a"))); // Fill TextField
            txtdesc_a.setText(rs.getString("description_a")); // Fill TextArea
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    }

    @FXML
    private void importerImg_a(MouseEvent event) {
        
        EventAdminService aa =new EventAdminService();
       path=aa.imporeterImg(main,espaceImg_a);
        System.out.println(path);
    }


    
   /* private void next_a(MouseEvent event) throws IOException {
    

   Object root = FXMLLoader.load(getClass().getResource("InterfaceEvent.fxml"));
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        Scene scene = new Scene((Parent) root);
          primaryStage.setScene(scene);
           primaryStage.show();
    }*/

    @FXML
    private void back(MouseEvent event) {
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../gui/Admin/AdminDashboard.fxml"));
            Parent root = loader.load();
            
            
              AdminDashboardController controllerPage=loader.getController();
              controllerPage.setUtilisateur(current);
              
            Stage cStage= (Stage) this.back.getScene().getWindow();
            cStage.setWidth(800);
            cStage.setHeight(740);
              
            back.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
