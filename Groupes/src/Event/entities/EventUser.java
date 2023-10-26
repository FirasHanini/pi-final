/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Event.entities;

import java.time.LocalDate;

/**
 *
 * @author ASUS
 */
public class EventUser {
       private int id_u ;
    private String nom_u ;
    private LocalDate date_u ;
     private String lieu_u ;
    private String description_u ;
     private String image_u;
    private int prix_u;
    private String pathQR;

    public EventUser() {
    }

    public EventUser(int id_u, String nom_u, LocalDate date_u, String lieu_u, String description_u, String image_u, int prix_u) {
        this.id_u = id_u;
        this.nom_u = nom_u;
        this.date_u = date_u;
        this.lieu_u = lieu_u;
        this.description_u = description_u;
        this.image_u = image_u;
        this.prix_u = prix_u;
    }

    public EventUser(int id_u, String nom_u, LocalDate date_u, String lieu_u, String description_u, String image_u, int prix_u, String pathQR) {
        this.id_u = id_u;
        this.nom_u = nom_u;
        this.date_u = date_u;
        this.lieu_u = lieu_u;
        this.description_u = description_u;
        this.image_u = image_u;
        this.prix_u = prix_u;
        this.pathQR = pathQR;
    }

    public EventUser(String nom_u, LocalDate date_u, String lieu_u, String description_u, String image_u, int prix_u, String pathQR) {
        this.nom_u = nom_u;
        this.date_u = date_u;
        this.lieu_u = lieu_u;
        this.description_u = description_u;
        this.image_u = image_u;
        this.prix_u = prix_u;
        this.pathQR = pathQR;
    }
    
    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    public String getNom_u() {
        return nom_u;
    }

    public void setNom_u(String nom_u) {
        this.nom_u = nom_u;
    }

    public LocalDate getDate_u() {
        return date_u;
    }

    public void setDate_u(LocalDate date_u) {
        this.date_u = date_u;
    }

    public String getLieu_u() {
        return lieu_u;
    }

    public void setLieu_u(String lieu_u) {
        this.lieu_u = lieu_u;
    }

    public String getDescription_u() {
        return description_u;
    }

    public void setDescription_u(String description_u) {
        this.description_u = description_u;
    }

    public String getImage_u() {
        return image_u;
    }

    public void setImage_u(String image_u) {
        this.image_u = image_u;
    }

    public int getPrix_u() {
        return prix_u;
    }

    public void setPrix_u(int prix_u) {
        this.prix_u = prix_u;
    }

    public String getPathQR() {
        return pathQR;
    }

    public void setPathQR(String pathQR) {
        this.pathQR = pathQR;
    }

    @Override
    public String toString() {
        return "EventUser{" + "id_u=" + id_u + ", nom_u=" + nom_u + ", date_u=" + date_u + ", lieu_u=" + lieu_u + ", description_u=" + description_u + ", image_u=" + image_u + ", prix_u=" + prix_u + ", pathQR=" + pathQR + '}';
    }
    
    

   
    

  
    
}
