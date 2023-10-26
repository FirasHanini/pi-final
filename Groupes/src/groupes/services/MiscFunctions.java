/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupes.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import groupes.services.GroupFunctions;
import MyConnection.MyConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import groupes.entities.Member;
import java.io.FileOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import static java.lang.Math.round;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonValue;
/**
 *
 * @author hammeda
 */

public class MiscFunctions {
    private final String secretKey = "A26B773F41F90732D8497BDFB6F15021";
    GroupFunctions GF=new GroupFunctions();
  
    public void setLogoForGroup(int id,ImageView logo2) {
    try {
      
        String query = "SELECT logo FROM groups WHERE Gid = ?";

          java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(query) ;
        preparedStatement.setInt(1, id);

       
        java.sql.ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
           
            String path = resultSet.getString("logo");

            if (path != null) {
               
                  File imageFile = new File(path);

               if (imageFile.exists()) {
                     FileInputStream fileInputStream=null;
                    
                      try {
                          fileInputStream = new FileInputStream(imageFile);
                      } catch (FileNotFoundException ex) {
                          Logger.getLogger(MiscFunctions.class.getName()).log(Level.SEVERE, null, ex);
                      }
            Image logoImage = new Image(fileInputStream);

                    
                    logo2.setImage(logoImage);
                }
            }
        }

      
        resultSet.close();
        preparedStatement.close();
     
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        
    }
}

    
    public List<Member> getMembersByGid(int gid) throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM Utilisateur,membre WHERE Utilisateur.id=membre.UserId and membre.GroupId = ?";

         java.sql.PreparedStatement preparedStatement = MyConnection.getInstance().getCnx()
                                    .prepareStatement(sql) ;
            preparedStatement.setInt(1, gid);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int memberId = resultSet.getInt("id");
               int GroupId = resultSet.getInt("groupID");
               String Role = resultSet.getString("Role");
                String Nom = resultSet.getString("nom");
                String Prenom = resultSet.getString("prenom");
                String Email = resultSet.getString("email");
               String DateNaissance= resultSet.getString("date_naissance");
               int age = resultSet.getInt("age");
                
                
               
                
                Member member = new Member(GroupId,Role,memberId,Nom,Prenom,Email,DateNaissance,age);
                
                members.add(member);
            }
       
        
        

        return members;
    }
    
    
    
    public void Export(List<Member> members, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Members");
            int rowNum = 0;

        
            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nom");
            headerRow.createCell(2).setCellValue("Prenom");
            headerRow.createCell(3).setCellValue("Email");
            headerRow.createCell(4).setCellValue("Group ID");
            headerRow.createCell(5).setCellValue("Role");
            headerRow.createCell(6).setCellValue("Date de Naissance");
            headerRow.createCell(7).setCellValue("age");

           
            for (Member member : members) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(member.getId());
                row.createCell(1).setCellValue(member.getNom());
                row.createCell(2).setCellValue(member.getPrenom());
                row.createCell(3).setCellValue(member.getEmail());
                row.createCell(4).setCellValue(member.getGroupID());
                row.createCell(5).setCellValue(member.getRole());
              
                row.createCell(6).setCellValue(member.getDateNaissance());
                row.createCell(7).setCellValue(member.getAge());
            }

           
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
                 Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Export Success");
            alert.setHeaderText("Data Exported Successfully");
            alert.setContentText(null);
            alert.showAndWait();
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
    
    
    
    /*--------------------------------Email-----------------------------------------*/
    
    
   public void SendMail(String subject,String Mail) {
    
    final String username = "ecoartteampi@gmail.com ";
    final String password = "hoxb htnf agqp blhk";
    
     List<String> toEmails = GF.getEmailsOfGroupMembers();
       System.out.println(toEmails);
     
      Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Change this to your email service's SMTP server
        props.put("mail.smtp.port", "587");
     
      Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
     
     
           
            Message message = new MimeMessage(session);
 try {
          
            message.setFrom(new InternetAddress(username));

   
            for (String toEmail : toEmails) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            }
              message.setSubject(subject);
            message.setText(Mail);
       
            Transport.send(message);
        } catch (MessagingException ex) {
           ex.getMessage();
        }

            System.out.println("Email sent successfully.");


    }
   
   
   
   public String GetWeather() {
   
            String apiKey = "07b37dc5bf0427456ba8f735878830bb";
            String city = "Gouvernorat de l’Ariana, TN";
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
 try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection;
       
            connection = (HttpURLConnection) url.openConnection();
       

            if (connection.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject response = new Gson().fromJson(reader, JsonObject.class);

                double temperature = response.getAsJsonObject("main").get("temp").getAsDouble() - 273.15; // Convert from Kelvin to Celsius
                String condition = response.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();

                System.out.println("Current Temperature: " + round(temperature) + "°C");
                System.out.println("Weather Condition: " + condition);
          
     return round(temperature) + "°C" + "\n" + condition;
    }
      else {
                System.out.println("Error: Unable to fetch weather data.");
                return null;
            }
       
    } catch (IOException ex) {
            Logger.getLogger(MiscFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
   }
   
   
   public void encrypt(String password) throws NoSuchAlgorithmException{
   
    

        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Hash the password with the salt
        String hashedPassword = hashPassword(password, salt);

        System.out.println("Original Password: " + password);
       
        System.out.println("Hashed Password: " + hashedPassword);
   
   }
   public static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // Append the salt to the password and hash it
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes());

        // Convert the byte array to a hexadecimal string
        return bytesToHex(hashedPassword);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
    
    
    
    
    
    
    
    public String Encrypt(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
    
     Cipher cipher = Cipher.getInstance("AES");
        SecretKey key = new SecretKeySpec(secretKey.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return  Base64.getEncoder().encodeToString(encryptedBytes);
    
    }
 public static String decryptPassword(String encryptedPassword, String secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    SecretKey key = new SecretKeySpec(secretKey.getBytes(), "AES");
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
    byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
    return new String(decryptedBytes);
}
   
 
 
public String Translate(String text) throws IOException{
  String apiKey = "7d641441f81242ddaad7";
            
            String sourceLang = "en";
            String targetLang = "fr";

            String apiUrl = "https://mymemory.translated.net/api/get?q=" + text
                          + "&langpair=" + sourceLang + "|" + targetLang
                          + "&key=" + apiKey;

          
              URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            conn.disconnect();

            String translation = (response.toString());
            return translation;
      
  
}



 

    
}
    

    

