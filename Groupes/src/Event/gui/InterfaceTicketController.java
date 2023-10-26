/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Event.gui;

import Utilisateur.Utilisateur;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import gui.Admin.AdminDashboardController;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class InterfaceTicketController implements Initializable {

    @FXML
    private Label labeldate;
    @FXML
    private Label labellieu;
    @FXML
    private Label labelprix;
    @FXML
    private Label labelnom;
    @FXML
    private ImageView QRpath_a;
    private Image image1;
    @FXML
    private Button imprimepdf;
    @FXML
    private Label labelticketnom;
    @FXML
    private Label labelticketprenom;
    @FXML
    private Label labelticketcin;
      Utilisateur current;
    @FXML
    private Button backbtn;
    
    public void setUtilisateur(Utilisateur current){
        this.current=current;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
        // TODO
    //public void setEventInfo(String nom_t, String date, String lieu,  int prix  ,String nom,String prenom,int CIN, String Qr)
    public void setEventInfo(String nom, String date, String lieu,  int prix , String Qr) {
        labelnom.setText(nom);
        labeldate.setText(date);
        labellieu.setText(lieu);
    
        labelprix.setText(Integer.toString(prix));
        this.labelticketnom.setText(current.getNom());
        this.labelticketprenom.setText(current.getPrenom());
        this.labelticketcin.setText(current.getCIN());
        System.out.println(Qr);
      //  System.err.println("qr"+Qr);
                image1 = new Image(Qr);
           QRpath_a.setImage(image1);

    } 
   /* public void setnom_a (String nom_a){
        Eventnom =nom_a ;
        labelnom.setText(Eventnom);*/

    @FXML
    private void imprimeticket(MouseEvent event) throws FileNotFoundException, DocumentException  {
        
        
    String nom = labelnom.getText();
    String date = labeldate.getText();
    String lieu = labellieu.getText();
   String textePrix = labelprix.getText(); // Obtenez le texte de l'étiquette
   int prix = Integer.parseInt(textePrix);
   ImageView QRpath_a = new ImageView();
   
     genererPDF(nom, date, lieu, prix,QRpath_a);

        
    }
    
public void genererPDF(String nom, String date, String lieu, int prix, ImageView QRpath_a) {
    Document document = new Document();
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
    File fichierPDF = fileChooser.showSaveDialog(null);

    if (fichierPDF != null) {
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fichierPDF));
            document.open();

            

            // Créer un en-tête personnalisé
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            cb.setFontAndSize(bf, 12);
            cb.showTextAligned(PdfContentByte.ALIGN_LEFT, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ").format(new Date()), 30, 800, 0);

            // Créer un paragraphe pour le titre
            Paragraph titre = new Paragraph("Votre ticket");
            titre.setAlignment(Element.ALIGN_CENTER);
            Font fontTitre = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD | Font.UNDERLINE);
            titre.setFont(fontTitre);
            document.add(titre);

            // Ajouter un espace vertical entre le titre et les informations
            document.add(Chunk.NEWLINE);

           Font fontInfo = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
           String espace = "                                                    "; // Ajoutez des espaces ici pour créer l'espace désiré
            Paragraph infoNomDate = new Paragraph("Nom : " + nom + espace + "Date : " + date, fontInfo);
            infoNomDate.setAlignment(Element.ALIGN_LEFT);
            infoNomDate.setSpacingBefore(30); // Ajustez l'espacement entre les paires
            infoNomDate.setIndentationLeft(70);
            
            document.add(infoNomDate);

            // Créez un paragraphe pour la paire "Lieu" et "Prix"
            String espace2 = "                                                  "; // Ajoutez des espaces ici pour créer l'espace désiré
            Paragraph infoLieuPrix = new Paragraph("Lieu : " + lieu + espace2 + "Prix : " + prix, fontInfo);
            infoLieuPrix.setAlignment(Element.ALIGN_LEFT);
            infoLieuPrix.setSpacingBefore(30); // Ajustez l'espacement entre les paires
            infoLieuPrix.setIndentationLeft(70);

            document.add(infoLieuPrix);
            
            Paragraph qrCodeParagraph = new Paragraph();
            qrCodeParagraph.setAlignment(Element.ALIGN_CENTER);

           
            document.close();
            
            

            // Ouvrir le PDF avec un lecteur PDF par défaut
            try {
                Desktop.getDesktop().open(fichierPDF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}

    @FXML
    private void back(MouseEvent event) {
        try {
              
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InterfaceEvent.fxml"));
            Parent root = loader.load();
            
            
              InterfaceEventController controllerPage=loader.getController();
              controllerPage.setUtilisateur(current);
              
            Stage cStage= (Stage) this.imprimepdf.getScene().getWindow();
            cStage.setWidth(710);
            cStage.setHeight(740);
              
            imprimepdf.getScene().setRoot(root);
            
              
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    }







  /* private File imprimePDF(String name, LocalDate date, String lieu, int prix, ObservableList<EventAdmin> EventAdminlist) throws FileNotFoundException, DocumentException  {
         com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
        com.itextpdf.text.Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
        com.itextpdf.text.Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
    
        
        EventAdminService eve = new EventAdminService();
      
       
        FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
    File pdfFile = fileChooser.showSaveDialog(null);

    if (pdfFile != null) {
        Document document = new Document();
       
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            // Title
            Paragraph title = new Paragraph("Your ticket" , titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
        File outputFile = new File("ticket.pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        document.open();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("ticket.pdf"));
            document.open();
            document.add(new Paragraph("Nom : " + name));
            document.add(new Paragraph("Date : " + date));
            document.add(new Paragraph("Lieu : " + lieu));
            document.add(new Paragraph("Prix : " + prix));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        return null;
    

    }
}*/
    
    
    





    

