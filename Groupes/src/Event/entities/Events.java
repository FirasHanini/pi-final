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
public class Events {
    private int id_ev;
    private String nom_ev;
    private LocalDate date_ev;
    private String lieu_ev;
    private String description_ev;
    private String image_ev; // Change the image data type to byte array
    private int prix_ev;

    public Events() {
    }

    public Events(int id_ev, String nom_ev, LocalDate date_ev, String lieu_ev, String description_ev, String image_ev, int prix_ev) {
        this.id_ev = id_ev;
        this.nom_ev = nom_ev;
        this.date_ev = date_ev;
        this.lieu_ev = lieu_ev;
        this.description_ev = description_ev;
        this.image_ev = image_ev;
        this.prix_ev = prix_ev;
    }

    public int getId_ev() {
        return id_ev;
    }

    public void setId_ev(int id_ev) {
        this.id_ev = id_ev;
    }

    public String getNom_ev() {
        return nom_ev;
    }

    public void setNom_ev(String nom_ev) {
        this.nom_ev = nom_ev;
    }

    public LocalDate getDate_ev() {
        return date_ev;
    }

    public void setDate_ev(LocalDate date_ev) {
        this.date_ev = date_ev;
    }

    public String getLieu_ev() {
        return lieu_ev;
    }

    public void setLieu_ev(String lieu_ev) {
        this.lieu_ev = lieu_ev;
    }

    public String getDescription_ev() {
        return description_ev;
    }

    public void setDescription_ev(String description_ev) {
        this.description_ev = description_ev;
    }

    public String getImage_ev() {
        return image_ev;
    }

    public void setImage_ev(String image_ev) {
        this.image_ev = image_ev;
    }

    public int getPrix_ev() {
        return prix_ev;
    }

    public void setPrix_ev(int prix_ev) {
        this.prix_ev = prix_ev;
    }

    @Override
    public String toString() {
        return "Events{" + "id_ev=" + id_ev + ", nom_ev=" + nom_ev + ", date_ev=" + date_ev + ", lieu_ev=" + lieu_ev + ", description_ev=" + description_ev + ", image_ev=" + image_ev + ", prix_ev=" + prix_ev + '}';
    }
    
    
    
}
