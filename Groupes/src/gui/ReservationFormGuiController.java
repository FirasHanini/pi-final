/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;










import javax.mail.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;

import com.stripe.param.PaymentIntentCreateParams;

import formations.formationServices;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * FXML Controller class
 *
 * @author ghmougui ons
 */
public class ReservationFormGuiController implements Initializable {

    @FXML
    private TextField yourname;
    @FXML
    private TextField numCard;
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
    public void setTotalAmount(double amount) {
        totalAmount.setText(String.valueOf(amount));
    }
    
     private String formationTitle;

    public void setFormationTitle(String title) {
        formationTitle = title;
    }
    @FXML
    private PasswordField cvc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         // Initialize Stripe API key
        Stripe.apiKey = "sk_test_51O4vRiL7stSiLm16AVtyOh6bLG27G3Ff5mhdiKI5k18zsTcb0bhW2ekHIVJnpu5rS7SYoWcODQc8LeOULCfKAuBT005VH8TSSH";

        // Initialize ComboBoxes
        months.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11","12");
        years.getItems().addAll(2022, 2023, 2024, 2025, 2026);

        // Set some initial values or prompts
        months.setValue("month");
        years.setValue(2022);
        
         
        totalAmount.setText("0.00");

    }
    

    @FXML
    private void confirmation(ActionEvent event) throws StripeException {
    // Create the PaymentMethod
    
    String cardNumber = numCard.getText();
    String expirationMonth = months.getValue();
    String expirationYear = years.getValue().toString();
    String CVC=cvc.getText();
    String Email = email.getText();
    
     /*if (isValidEmail(Email)) {   

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
        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
        .setAmount(1000L) // Amount in cents (e.g., $10)
        .setCurrency("usd")
        .setDescription("Payment for Order")
        .setPaymentMethod(paymentMethod.getId()) // Set the payment method directly
        .setReturnUrl("https://yourwebsite.com/payment_success")
        .setConfirm(true)
        .build();
       
            PaymentIntent intent = PaymentIntent.create(createParams);
             System.out.println("Payment Intent ID: " + intent.getId());
            // Get the confirmation URL from the PaymentIntent
            String confirmationUrl = intent.getClientSecret();

       
            if ("succeeded".equals(intent.getStatus())) {
                showPaymentSuccessAlert();
                // Envoyer un e-mail personnalisé avec le nom du destinataire, le titre de la formation et un message de bienvenue
            String recipientName = yourname.getText();
            String recipientEmail = email.getText();
            
            sendEmail(recipientName, recipientEmail, formationTitle);
                

            
        } else {
            showPaymentFailureAlert();
        }
    } catch (StripeException e) {
            e.printStackTrace();
    }
    } else {
        // The email is not valid; display an error message or take appropriate action.
        showAlert("Invalid Email", "Please enter a valid email address.");
    }*/
      confirm.setOnAction(e -> showPaymentSuccessAlert());
      if (isValidEmail(Email)){
        String recipientName = yourname.getText();
            String recipientEmail = email.getText();
            
            sendEmail(recipientName, recipientEmail, formationTitle);}
      
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
    
    
    
    private void sendEmail(String recipientName, String recipientEmail, String formationTitle) {
    String to = recipientEmail;
    String subject = "Welcome to " + formationTitle;
    String body = "Dear " + recipientName + ",\n\n"
                  + "Welcome to " + formationTitle + ". Thank you for your payment. Now you are on your way to new skills!";
    
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
    public static boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
    return email.matches(emailRegex);
}
    private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}

}