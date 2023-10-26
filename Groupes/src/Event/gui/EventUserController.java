/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Event.gui;


import Utilisateur.Utilisateur;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import Event.entities.EventUser;

import Event.services.EventUserService;
import Event.utils.MyConnection;
import gui.homePage.HomPageController;
import java.awt.image.BufferedImage;
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
import javafx.event.ActionEvent;
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
public class EventUserController implements Initializable {

    @FXML
    private DatePicker daterev_u;
    @FXML
    private Button btajoutResv_u;
    @FXML
    private Button btsupResv_u;
    @FXML
    private Button btmodifResv_u;
    @FXML
    private TableView<EventUser> tabResv_u;
    @FXML
    private TableColumn<EventUser, String> colnom_u;
    @FXML
    private TableColumn<EventUser, String> coldate_u;
    @FXML
    private TableColumn<EventUser, String> collieu_u;
    @FXML
    private TableColumn<EventUser, String> coldesc_u;
    @FXML
    private ComboBox<String> combolieu_u;
    @FXML
    private Button btimgResv_u;
    @FXML
    private AnchorPane main_u;
    @FXML
    private ImageView espaceImg_u;
    @FXML
    private TextField tfnom_u;
    
    @FXML
    private TableColumn<EventUser, String> colprix_u;
    @FXML
    private TextField tfprix_u;
    @FXML
    private TextArea txtdesc_u;
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
        EventUserService a =new EventUserService();
    @FXML
    private ImageView btnext_u;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
         ObservableList<String> items = FXCollections.observableArrayList("ariana","beja","ben arous","bizert","tatwin","touzeur","tunis","jandouba","zaghwen","silyana","sousse",
                "sidi bouzid","safax","gbeli","gasrin","gafsa","kerwan","keef","mednin","mounastir","manouba","mahdia","nabeul");
           
        combolieu_u.setItems(items);

       
       
        try {
            a.ShowReservation(colnom_u, coldate_u, collieu_u, coldesc_u,colprix_u,tabResv_u);
        } catch (SQLException ex) {
            Logger.getLogger(EventUserController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void Ajoutresv_u(MouseEvent event) {
        
        try {
        
        int id = 0; // You can set the ID as needed, or let the database auto-generate it
        String nom = tfnom_u.getText();
        LocalDate date = daterev_u.getValue();
        String lieu = combolieu_u.getSelectionModel().getSelectedItem().toString();
        String description = txtdesc_u.getText();
        
        
        Image image = espaceImg_u.getImage(); // Get the image from the ImageView
        int prix = Integer.parseInt(tfprix_u.getText());
         String dataQr= "Event  nom=" + nom + ", date=" + date + ", lieu=" + lieu + ", description=" + description +  ", prix=" + prix  + '}';
                       String datanameQr = generateRandomString(4);
                String myQr = generateQRCodeAndSave(dataQr,datanameQr);
        
  
       
        // Create an EventUser object and call ajoutEventUser with it
        EventUser e = new EventUser(id, nom, date, lieu, description,path, prix,myQr);
        a.ajoutEventUser(e,path);

        a.ShowReservation(colnom_u, coldate_u, collieu_u, coldesc_u, colprix_u, tabResv_u);
        // Optionally, you can handle success or display a message here
    } catch (Exception ex) {
        ex.printStackTrace();
        // Handle errors or display an error message
    }
    }

    /////////QR///////////////
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
    
    
    @FXML
    private void Supresv_u(MouseEvent event) throws SQLException {
        
        
         EventUser selectedEvent = tabResv_u.getSelectionModel().getSelectedItem();

    if (selectedEvent != null) {
        int result = a.supprimerEventUser(selectedEvent);

        if (result == 1) {
            // Deletion successful, you can update the TableView or take any other action
            a.ShowReservation(colnom_u, coldate_u, collieu_u, coldesc_u, colprix_u, tabResv_u);
        } else if (result == 0) {
            // No records were deleted, possibly the record does not exist
        } else {
            // An error occurred during deletion
        }
    }
    a.ShowReservation(colnom_u, coldate_u, collieu_u, coldesc_u, colprix_u, tabResv_u);
    }

    @FXML
    private void Modresv_u(MouseEvent event) {
        
             try {
         
        int id = 0; // You can set the ID as needed, or let the database auto-generate it
        String nom = tfnom_u.getText();
        LocalDate date = daterev_u.getValue();
        String lieu = combolieu_u.getSelectionModel().getSelectedItem().toString();
        String description = txtdesc_u.getText();
       
         // Get the image from the ImageView
        int prix = Integer.parseInt(tfprix_u.getText());
        // Create an EventUser object and call ajoutEventUser with it
         
        int selectedIndex = tabResv_u.getSelectionModel().getSelectedIndex();
        int selectedId = -1; // Initialize to a default value

if (selectedIndex >= 0) {
    // If a row is selected, retrieve the ID from the database
    EventUser selectedEvent = tabResv_u.getItems().get(selectedIndex);
    selectedId = selectedEvent.getId_u();
}
            EventUser e = new EventUser(selectedId, nom, date, lieu, description,path, prix);
                
            a.modifEventUser(e, path);


        a.ShowReservation(colnom_u, coldate_u, collieu_u, coldesc_u, colprix_u, tabResv_u);
        // Optionally, you can handle success or display a message here
    } catch (Exception ex) {
        ex.printStackTrace();
        // Handle errors or display an error message
    }
    }

    @FXML
    private void tab_u(MouseEvent event) {
        
         EventUser selectedEvent = tabResv_u.getSelectionModel().getSelectedItem();
if (selectedEvent != null) {
    String nom_u = selectedEvent.getNom_u();
    String lieu_u = selectedEvent.getLieu_u();   
    // Query the database to get the image data and other information based on nom_a and lieu_a
    String query = "SELECT image, date, prix, description FROM eventuser WHERE nom = ? AND lieu = ?";
    try {
        pst = myConx.prepareStatement(query);
        pst.setString(1, nom_u);
        pst.setString(2, lieu_u);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            path=rs.getString("image");
            File imageFile = new File(path);
            Image image = new Image(imageFile.toURI().toString());
            espaceImg_u.setImage(image);
            System.out.println(path);
            // Fill other JavaFX controls
            tfnom_u.setText(nom_u); // Fill TextField
            daterev_u.setValue(rs.getDate("date").toLocalDate()); // Fill DatePicker
            combolieu_u.setValue(lieu_u); // Fill ComboBox
            tfprix_u.setText(String.valueOf(rs.getInt("prix"))); // Fill TextField
            txtdesc_u.setText(rs.getString("description")); // Fill TextArea
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
    }

    @FXML
    private void importerImg_u(MouseEvent event) {
        EventUserService a =new EventUserService();
       path=a.imporeterImg(main_u,espaceImg_u);
    }


    @FXML
    private void combolieu_u(ActionEvent event) {
    }

    @FXML
    private void next_u(MouseEvent event)  { //InterfaceEvent.fxml
        
         try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InterfaceEvent.fxml"));
            Parent root = loader.load();
            
            
              InterfaceEventController controllerPage=loader.getController();
              controllerPage.setUtilisateur(current);
              
            Stage cStage= (Stage) this.back.getScene().getWindow();
            cStage.setWidth(1300);
            cStage.setHeight(760);
              
            back.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void onBack(MouseEvent event) {
          try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../gui/homePage/homPage.fxml"));
            Parent root = loader.load();
            
            
              HomPageController controllerPage=loader.getController();
              controllerPage.setUtilisateur(current);
              
            Stage cStage= (Stage) this.back.getScene().getWindow();
            cStage.setWidth(710);
            cStage.setHeight(740);
              
            back.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

   
    
}
