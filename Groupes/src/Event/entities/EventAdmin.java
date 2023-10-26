/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Event.entities;

import java.time.LocalDate;
import javafx.scene.image.Image;



/**
 *
 * @author ASUS
 */
  public class EventAdmin {
   private int id_a;
    private String nom_a;
    private LocalDate date_a;
    private String lieu_a;
    private String description_a;
    private String image_a; // Change the image data type to byte array
    private int prix_a;
    private String pathQR ;

    public String getPathQR() {
        return pathQR;
    }

    public void setPathQR(String pathQR) {
        this.pathQR = pathQR;
    }
    
    
    
    public EventAdmin() {
    }

    public EventAdmin(int id_a, String nom_a, LocalDate date_a, String lieu_a, String description_a, String image_a, int prix_a) {
        this.id_a = id_a;
        this.nom_a = nom_a;
        this.date_a = date_a;
        this.lieu_a = lieu_a;
        this.description_a = description_a;
        this.image_a = image_a;
        this.prix_a = prix_a;
    }

    public EventAdmin(String nom_a, LocalDate date_a, String lieu_a, String description_a, String image_a, int prix_a, String pathQR) {
        this.nom_a = nom_a;
        this.date_a = date_a;
        this.lieu_a = lieu_a;
        this.description_a = description_a;
        this.image_a = image_a;
        this.prix_a = prix_a;
        this.pathQR = pathQR;
    }

    public EventAdmin(int id_a, String nom_a, LocalDate date_a, String lieu_a, String description_a, String image_a, int prix_a, String pathQR) {
        this.id_a = id_a;
        this.nom_a = nom_a;
        this.date_a = date_a;
        this.lieu_a = lieu_a;
        this.description_a = description_a;
        this.image_a = image_a;
        this.prix_a = prix_a;
        this.pathQR = pathQR;
    }
    

    public int getId_a() {
        return id_a;
    }

    public void setId_a(int id_a) {
        this.id_a = id_a;
    }

    public String getNom_a() {
        return nom_a;
    }

    public void setNom_a(String nom_a) {
        this.nom_a = nom_a;
    }

    public LocalDate getDate_a() {
        return date_a;
    }

    public void setDate_a(LocalDate date_a) {
        this.date_a = date_a;
    }

    public String getLieu_a() {
        return lieu_a;
    }

    public void setLieu_a(String lieu_a) {
        this.lieu_a = lieu_a;
    }

    public String getDescription_a() {
        return description_a;
    }

    public void setDescription_a(String description_a) {
        this.description_a = description_a;
    }

    public String getImage_a() {
        return image_a;
    }

    public void setImage_a(String image_a) {
        this.image_a = image_a;
    }

    public int getPrix_a() {
        return prix_a;
    }

    public void setPrix_a(int prix_a) {
        this.prix_a = prix_a;
    }

    @Override
    public String toString() {
        return "EventAdmin{" + "id_a=" + id_a + ", nom_a=" + nom_a + ", date_a=" + date_a + ", lieu_a=" + lieu_a + ", description_a=" + description_a + ", image_a=" + image_a + ", prix_a=" + prix_a + ", pathQR=" + pathQR + '}';
    }

    
   
    

   

  
    
    
}
