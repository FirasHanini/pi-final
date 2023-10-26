/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reclamation;

import Utilisateur.Utilisateur;
import java.util.Objects;

/**
 *
 * @author Utilisateur 2
 */
public class Reclamation {
    
    private long id;
    private String contenu;
    private State etat;
    private String reponse;
    
    
    //association utilisateur
    private Utilisateur sender;

    

    public Reclamation() {
        this.etat=State.WAITING;
    }

    public Reclamation(long id, String contenu,String reponse, Utilisateur sender) {
        this.id = id;
        this.contenu = contenu;
        this.etat=State.WAITING;
        this.reponse=reponse;
        this.sender=sender;
    }
    
    public Utilisateur getSender() {
        return sender;
    }

    public void setSender(Utilisateur sender) {
        this.sender = sender;
    }

    public String getEtat() {
        return etat.name();
    }

    public void setEtat(State etat) {
        this.etat = etat;
    }
    

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public long getId() {
        return id;
    }
    
    
    

    public void setId(long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    public Long getSenderId(){
       return  this.sender.getId();
    }
    
    public String getSenderUsername(){
        return this.sender.getUserName();
    }

    @Override
    public String toString() {
        return "Reclamation{" + "contenu=" + contenu + ", etat=" + etat + ", reponse=" + reponse + ", sender=" + sender + '}';
    }
    

   

   
    
   

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reclamation other = (Reclamation) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.contenu, other.contenu)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 47 * hash + Objects.hashCode(this.contenu);
        return hash;
    }

   
    
    
    
}
