/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import LineOrder.LineOrder;
import LineOrder.LineOrderService;
import Utilisateur.Utilisateur;
import com.itextpdf.text.BaseColor;
import commande.commande;
import commande.commandeService;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import commande.orderItems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;


import java.io.BufferedReader;
import static java.io.FileDescriptor.in;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static java.lang.System.in;

import java.net.URI;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.Base64;
import javafx.scene.control.Alert.AlertType;
import static javax.management.Query.in;

/**
 * FXML Controller class
 *
 * @author User
 */
public class CommandeController implements Initializable {
Utilisateur current;
     public void setUtilisateur(Utilisateur current){
        this.current=current;
        tfnom.setText(current.getNom());
        tfemail.setText(current.getEmail());
        
    }
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfid;
    @FXML
    private TextField tfadresse;
    @FXML
    private TextField tfnum;
    @FXML
    private TextField tfemail;
    @FXML
    private Button btnpayer;
    @FXML
    private TableView<orderItems> TableCommande;
   /* private TableColumn<commande,String> NomC;
    private TableColumn<commande,Long> id;
    private TableColumn<commande, String> adresse;
    private TableColumn<commande, Integer> numTel;
    private TableColumn<commande, String> email;
    private TableColumn<commande, LocalDate> date;*/
    commandeService cs=new commandeService();
    @FXML
    private Button gotobboutique;
    @FXML
    private TableView<LineOrder> tableorder;
    @FXML
    private TableColumn<LineOrder, String> productName;
    @FXML
    private TableColumn<LineOrder, Integer> quantity;
    @FXML
    private TableColumn<LineOrder, Double> price;
    @FXML
    private Button confirmer;
    @FXML
    private TableColumn<orderItems, Date> orderDate;
    @FXML
    private TableColumn<orderItems, String> product;
    @FXML
    private TableColumn<orderItems, Integer> qte;
    @FXML
    private TableColumn<orderItems, Double> prix;
    @FXML
    private TableColumn<orderItems,Long> numOrder;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       refresh();
       afficheOrder();
    }    


    @FXML
    private void Payer(ActionEvent event) {
        LineOrder L = new LineOrder();
    LineOrderService lS = new LineOrderService();
    ObservableList<LineOrder> OrderList = FXCollections.observableArrayList(lS.getAllOrders());
    String nom = tfnom.getText();
    int id = Integer.parseInt(tfid.getText());
    String adresse = tfadresse.getText();
    String email = tfemail.getText();
    LocalDate date = LocalDate.now();
    String phoneNumber = tfnum.getText();
    
    int numTel = 0; // Initialize numTel outside the if block
    if (isPhoneNumberValid(phoneNumber)) {
        numTel = Integer.parseInt(phoneNumber); // Assign value inside the if block
    } else {
        // Handle invalid phone number (optional)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Phone Number");
        alert.setHeaderText("Please enter a valid phone number.");
        alert.showAndWait();
        return; // Exit the method
    
}
     for (LineOrder lineOrder : OrderList) {
    System.out.println("Product: " + lineOrder.getProductName());
    System.out.println("Price: " + lineOrder.getPrix());
    System.out.println("Quantity: " + lineOrder.getQuantite());
    
    
}


  double total = OrderList.stream()
                    .mapToDouble(lineOrder -> lineOrder.getPrix() * lineOrder.getQuantite())
                    .sum();




  
    commandeService cS = new commandeService();
   
    if (cS.unicId_c(id) == -1) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("IdClient Not Unique");
        alert.setContentText("Please choose a different Id");
        alert.showAndWait();}
    else {
 
        commande c = new commande(nom, id, adresse, date, numTel, email,total);
        

        cS.ajouter(c);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add commande");
        alert.setHeaderText("Information");
        alert.setContentText("commande added successfully");
        alert.showAndWait();
    }
        refresh ();

     
    }
    public void refresh (){
           ObservableList<orderItems> order = FXCollections.observableArrayList(cs.insertJoinResultIntoTableAndReturnList());
        numOrder.setCellValueFactory(new PropertyValueFactory<orderItems, Long>("numC"));
        qte.setCellValueFactory(new PropertyValueFactory<orderItems, Integer>("quantite"));
        prix.setCellValueFactory(new PropertyValueFactory<orderItems, Double>("prix"));
        orderDate.setCellValueFactory(new PropertyValueFactory<orderItems, Date>("date"));
        product.setCellValueFactory(new PropertyValueFactory<orderItems, String>("Product"));
       
       // total.setCellValueFactory(new PropertyValueFactory<commande,Double>("total"));
        TableCommande.setItems(order);
        
    }

    @FXML
    private void GoBoutique(ActionEvent event) throws IOException {
         Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
    currentStage.close(); // Close the current stage

    // Load and open a new stage
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/boutique.fxml"));

    Parent root = loader.load();
    BoutiqueController BC = loader.getController();

    Stage newStage = new Stage();
    Scene scene = new Scene(root);
    newStage.setScene(scene);
    newStage.show();
    }
    
       public void afficheOrder(){
           LineOrderService lS= new LineOrderService();
        ObservableList<LineOrder> OrderList = FXCollections.observableArrayList(lS.getAllOrders());
          productName.setCellValueFactory(new PropertyValueFactory<LineOrder, String>("productName"));
        price.setCellValueFactory(new PropertyValueFactory<LineOrder, Double>("prix"));
        quantity.setCellValueFactory(new PropertyValueFactory<LineOrder, Integer>("quantite"));
        tableorder.setItems(OrderList);
    }

        

   // @FXML
   /* private void quantity(TableColumn.CellEditEvent<S, T> event) {
    }*/

    @FXML
    private void confirmer_Commande(ActionEvent event) {
        /*  try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
            currentStage.close(); // Close the current stage
            
            // Load and open a new stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("payment.fxml"));
            Parent root = loader.load();
            PaymentController PC = loader.getController();
            
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
            
        }*/
        confirmer.setOnAction(e -> showPaymentAlert());
        LineOrderService Ls = new LineOrderService();
        Ls.deleteAll();
        afficheOrder();
    }
        private void showPaymentAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Payment Status");
        alert.setHeaderText(null);
        alert.setContentText("Payment validated!");

        alert.showAndWait();
    }

    
    
    public boolean isPhoneNumberValid(String phoneNumber) {
    // Check if the phone number has exactly 8 digits and starts with 9, 2, 5, 7, or 4.
    String phoneNumberPattern = "^[2-5,7,9]{1}[0-9]{7}$";
    return phoneNumber.matches(phoneNumberPattern);
}

   @FXML
    private void valider(MouseEvent event) {
    }

   @FXML
    private void quantity(TableColumn.CellEditEvent<commande, Integer> event) {
    }}
/*public void sms() {
    commandeService CS = new commandeService();
    List<commande> lastCommandes = CS.getLastLine();

    if (!lastCommandes.isEmpty()) {
        commande lastCommande = lastCommandes.get(0);
        int phoneNumber = lastCommande.getNumTel();
        long numC = lastCommande.getId_c();
        

        // Find your Account SID and Token at twilio.com/console
        String ACCOUNT_SID = "AC953fd44e8941949aab02e7bf21ef4f21";
        String AUTH_TOKEN = "daae1432b5cb60dd9e72a9b288019fca";

        try {
            // Initialize the Twilio client
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            // Create a message
            Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+216" + String.valueOf(phoneNumber)),
                new com.twilio.type.PhoneNumber("whatsapp:+15153936843"),
                "Mr/Mme X les articles de la commande num " + numC + " vous seront livrés bientôt.\nMerci d'avoir shoppé avec nous.\nEcoArt"
            ).create();

            // Log a success message with Message SID
            System.out.println("Message SID: " + message.getSid());
        } catch (Exception e) {
            // Handle exceptions by logging the error
            System.err.println("Error sending WhatsApp message: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("No last command found.");
    }
}
*/


