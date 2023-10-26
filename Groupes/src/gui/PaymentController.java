package gui;

import LineOrder.LineOrder;
import LineOrder.LineOrderService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
//import com.stripe.param.AccountCreateParams.Company.Verification.Document;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import commande.commande;
import commande.commandeService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import java.net.URI;
import java.math.BigDecimal;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PaymentController implements Initializable {

    @FXML
    private TextField yourname;
    @FXML
    private TextField numCard;
   // private TextField CVC;
    @FXML
    private ComboBox<String> months;
    @FXML
    private ComboBox<Integer> years;
    @FXML
    private TextField email;
    @FXML
    private Button confirm;
    @FXML
    private Label totalAmount;
    @FXML
    private PasswordField cvc;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize Stripe API key
        Stripe.apiKey = "sk_test_51O3pkzAQca2hYJTxRSknLkZG3RL3DwhAB3xrtejyq0Nki7g97FBqgiHRO3RLzkQTXhEkViz37u42shsUO8LawKWv00d9OwyzOG";

        // Initialize ComboBoxes
        months.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11","12");
        years.getItems().addAll(2022, 2023, 2024, 2025, 2026);

        // Set some initial values or prompts
        months.setValue("month");
        years.setValue(2022);
         //WebEngine engine = webview.getEngine();
          commandeService cs = new commandeService();
                  List<commande> lastCommandes = cs.getLastLine(); // Call your function to get the last row
        if (!lastCommandes.isEmpty()) {
            commande lastCommande = lastCommandes.get(0);
            System.out.println();
             double total = lastCommande.getTotal();
        String totalString = String.valueOf(total); // Convert the double to a String
        totalAmount.setText(totalString);

   }

    }

 

    private void showPaymentSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Success");
        alert.setHeaderText("Payment was successful!");
        alert.showAndWait();
    }

    private void showPaymentFailureAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Payment Failure");
        alert.setHeaderText("Payment failed. Please try again.");
        alert.showAndWait();
    }


    @FXML
    private void confirmation(ActionEvent event) throws StripeException {
// Create the PaymentMethod
String cardNumber = numCard.getText();
String expirationMonth = months.getValue();
String expirationYear = years.getValue().toString();
 String CVC=cvc.getText();
 

Map<String, Object> card = new HashMap<>();
card.put("number", cardNumber);
card.put("exp_month", expirationMonth);
card.put("exp_year", expirationYear);
card.put("cvc", CVC);   
String cardToken = "tok_visa";
   

Map<String, Object> paymentMethodParams = new HashMap<>();
paymentMethodParams.put("type", "card");

   Map<String, Object> cardParams = new HashMap<>();
    cardParams.put("token", cardToken); // Use a test token here (e.g., "tok_visa")
    
    paymentMethodParams.put("card", cardParams);

try {
    PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);
            // Create a PaymentIntent
            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                    .setAmount(1000L) // Amount in cents (e.g., $10)
                    .setCurrency("usd")
                    .setDescription("Payment for Order")
                    .setPaymentMethod(paymentMethod.getId())
                    .setReturnUrl("https://yourwebsite.com/payment_success")
                     .setConfirm(true)
                    .build();

            PaymentIntent intent = PaymentIntent.create(createParams);
             System.out.println("Payment Intent ID: " + intent.getId());
            // Get the confirmation URL from the PaymentIntent
            String confirmationUrl = intent.getClientSecret();

       
                 if ("succeeded".equals(intent.getStatus())) {
            showPaymentSuccessAlert();

            String name = yourname.getText();
            String recipientEmail =email.getText();
            commandeService CS = new commandeService();
            List<commande> lastCommandes = CS.getLastLine();

            if (!lastCommandes.isEmpty()) {
                commande lastCommande = lastCommandes.get(0);
                String address = lastCommande.getAdresse();
                LineOrderService lS = new LineOrderService();
                List<LineOrder> lineOrders = lS.getAllOrders();
                long num = lastCommande.getId_c();
                String numC= String.valueOf(num);
                    System.out.println(name);
                    System.out.println(address);
                    System.out.println(lineOrders);
                   
                if (name != null && !name.isEmpty() && address != null && !lineOrders.isEmpty()) {
                    downloadInvoiceAlert(name, address, lineOrders);
         
                } else {
                    showDataErrorAlert();
                }
            } else {
                showDataErrorAlert();
            }
        } else {
            showPaymentFailureAlert();
        }
    } catch (StripeException e) {
            e.printStackTrace();
    }
    }


    private void downloadInvoiceAlert(String name, String address, List<LineOrder> lineOrders) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Download Invoice");
        alert.setHeaderText("Do you want to download the invoice?");
        alert.setContentText("Choose your option:");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            File invoiceFile = generateInvoice(name, address, lineOrders);

            if (invoiceFile != null) {
                showInvoiceDownloadedAlert(invoiceFile.getAbsolutePath());
            } else {
                showInvoiceGenerationErrorAlert();
            }
        }
    }

    private void showDataErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Data Error");
        alert.setHeaderText("Data retrieval error.");
        alert.setContentText("Please make sure all required data is available.");
        alert.showAndWait();
    }

    private void showInvoiceDownloadedAlert(String filePath) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invoice Downloaded");
        alert.setHeaderText("Invoice has been downloaded successfully.");
        alert.setContentText("You can find the invoice at:\n" + filePath);
        alert.showAndWait();
    }

    private void showInvoiceGenerationErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invoice Generation Error");
        alert.setHeaderText("An error occurred while generating the invoice.");
        alert.setContentText("Please try again later.");
        alert.showAndWait();
    }

 private File generateInvoice(String name, String address, List<LineOrder> lineOrders) {
    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
    Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
    Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
    PdfPTable table = new PdfPTable(3); // 3 columns
    table.setWidthPercentage(100);
    commande c = new commande();
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
    File pdfFile = fileChooser.showSaveDialog(null);

    if (pdfFile != null) {
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            // Title
            Paragraph title = new Paragraph("Your Facture" , titleFont);
            title.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(title);
            
             Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss "); // Define your desired date format
            String formattedDate = dateFormat.format(currentDate);
            document.add(new Paragraph("Date: " + formattedDate, headingFont));

            // Add invoice details
            //document.add(new Paragraph("numéro de facture" + num, headingFont));
            document.add(new Paragraph("Invoice for: " + name, headingFont));
            document.add(new Paragraph("Address: " + address, headingFont));
            
                 for (int i = 0; i < 3; i++) {
                    document.add(new Paragraph(" ")); // Add empty lines
                     }
          
       
            table.addCell(new PdfPCell(new Phrase("Product Name", headingFont)));
            table.addCell(new PdfPCell(new Phrase("Price", headingFont)));
            table.addCell(new PdfPCell(new Phrase("Quantity", headingFont)));
            
            for (LineOrder lineOrder : lineOrders) {
                table.addCell(new PdfPCell(new Phrase(lineOrder.getProductName(), normalFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(lineOrder.getPrix()), normalFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(lineOrder.getQuantite()), normalFont)));
            }

            document.add(table);

            // Add the total
            double total = lineOrders.stream()
                    .mapToDouble(lineOrder -> lineOrder.getPrix() * lineOrder.getQuantite())
                    .sum();
            document.add(new Paragraph("Total: " + total, headingFont));

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    return pdfFile;
}
 private void downloadInvoiceAlert() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Download Invoice");
    alert.setHeaderText("Do you want to download the invoice?");
    alert.setContentText("Choose your option:");

    ButtonType buttonTypeYes = new ButtonType("Yes");
    ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == buttonTypeYes) {
        String name = yourname.getText(); // Get the name from the form
        commandeService CS = new commandeService();
        List<commande> lastCommandes = CS.getLastLine(); // Call your function to get the last row
        if (!lastCommandes.isEmpty()) {
            commande lastCommande = lastCommandes.get(0); // Get the first (and only) element
        String address = lastCommande.getAdresse();
      // Retrieve the address from the database
          LineOrderService lS = new LineOrderService();
        List<LineOrder> lineOrders = FXCollections.observableArrayList(lS.getAllOrders());  // Retrieve the line orders from the database

        if (name != null && !name.isEmpty() && address != null && !lineOrders.isEmpty()) {
            File invoiceFile = generateInvoice(name, address, lineOrders);

            if (invoiceFile != null) {
                Alert downloadAlert = new Alert(Alert.AlertType.INFORMATION);
                downloadAlert.setTitle("Invoice Downloaded");
                downloadAlert.setHeaderText("Invoice has been downloaded successfully.");
                downloadAlert.setContentText("You can find the invoice at:\n" + invoiceFile.getAbsolutePath());
                downloadAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invoice Generation Error");
                errorAlert.setHeaderText("An error occurred while generating the invoice.");
                errorAlert.setContentText("Please try again later.");
                errorAlert.showAndWait();
            }
        }
    }
    }}


    private void sendEmail( String recipientEmail) {
    String to = recipientEmail;
    String subject = "Welcome to ";
    String body = "Dear "  + ",\n\n"
                  + "Welcome to "  + ". Thank you for your payment. Now you are on your way to new skills!";
    
    // Configure your email settings (SMTP server, username, password, etc.)
    String host = "smtp.gmail.com";
    String username = "ecoartteampi@gmail.com ";
    String password = "hoxb htnf agqp blhk";
    
    // Send the email using JavaMail API
    try {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587"); // Update the port as needed
        
        Session session;
        session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);
        
        Transport.send(message);
    } catch (MessagingException e) {
        e.printStackTrace();
    }
}
   /*  public static boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
    return email.matches(emailRegex);
}*/

}




/*public void sms (){
  // Find your Account Sid and Token at twilio.com/console
   String ACCOUNT_SID = "AC953fd44e8941949aab02e7bf21ef4f21";
  String AUTH_TOKEN = "3fc8c8c57b301f4eaaa1ae4559716f8d";
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
      new com.twilio.type.PhoneNumber("whatsapp:+21654917020"),
      new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
      "Mr/Mme"

    ).create();

    System.out.println(message.getSid());
  }
}*/
