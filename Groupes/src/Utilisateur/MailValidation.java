/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilisateur;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Utilisateur 2
 */
public class MailValidation {
    

    
    public static String generateVerificationCode() {
        Date date = new Date();
        
        
        SimpleDateFormat heure = new SimpleDateFormat("HH");
        SimpleDateFormat minu = new SimpleDateFormat("mm");
        
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
       
        String heureString = heure.format(date);
        String minuString = minu.format(date);
        String monthString = month.format(date);
        String dayString = day.format(date);
        
        
       
        int heureInt = Integer.parseInt(heureString);
        int minuInt = Integer.parseInt(minuString);
        int monthInt = Integer.parseInt(monthString);
        int dayInt = Integer.parseInt(dayString);
        
        
        
        return Integer.toString(heureInt*minuInt*360+dayInt*monthInt*5);
    }

    
    
    
    
    
    
   public static Properties createSmtpProperties() {
    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.starttls.enable", "true");
    return properties;
}



public static void sendVerificationCode(String recipientEmail, String subject, String message) {
    String host = "smtp.gmail.com";
    String username = "ecoartteampi@gmail.com";
    String password = "hoxb htnf agqp blhk";
    
    Properties properties = createSmtpProperties();

    Session session = Session.getDefaultInstance(properties, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });

    try {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(username));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(message);
        Transport.send(mimeMessage);
    } catch (MessagingException ex) {
        System.out.println(ex.getMessage());
    }
}

public static void sendEmailWithAttachment(String recipientEmail, String subject, String message, String attachmentPath) {
    String host = "smtp.gmail.com";
    String username = "ecoartteampi@gmail.com";
    String password = "hoxb htnf agqp blhk";
    
    Properties properties = createSmtpProperties();

    Session session = Session.getDefaultInstance(properties, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });

    try {
        //cree email
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(username));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        mimeMessage.setSubject(subject);
      //  mimeMessage.setText(message);

        BodyPart textPart = new MimeBodyPart();
        textPart.setText(message);
        //image ajout
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(attachmentPath);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("picture.jpg"); // Spécifiez le nom de l'image
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(textPart);
        mimeMessage.setContent(multipart);

        // Send the email
        Transport.send(mimeMessage);
    } catch (MessagingException ex) {
        System.out.println(ex.getMessage());
    }
}
    
    
    public static String newAccountEmailVerif(Utilisateur nouveau,String code){
        
        String retour="Hello "+nouveau.getNom()+" "+nouveau.getPrenom()+"\n Your verification code is:  "+code;
        
        
        return retour;
    }
    
    public static String passwordForgotEmail(Utilisateur nouveau, String code){
        
        String retour="Hello "+nouveau.getNom()+" "+nouveau.getPrenom()+"\n Your verification code is:  "+code +"\n Copy and paste this code so "
                + "you can change your password";
        return retour;
    }
    
    public static String  tooManyPassword(Utilisateur user){
        String retour ="Hello "+user.getNom()+" "+user.getPrenom()+",\n Someone is trying to connect to your account. \n Check the attachment ! ";
        return retour;
    }
    
}
