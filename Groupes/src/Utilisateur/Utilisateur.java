/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilisateur;


import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Utilisateur 2
 */
public class Utilisateur {
    
    private long id;
    private String CIN;
    private String nom;
    private String prenom;
    private String DateNaissance;
    private int age;
    private String pic;
    
    private String email;

    
    private String userName;
    private String password;
    
    private Type type;

   

    public String getType() {
        return type.name();
    }

    public void setType(Type type) {
        this.type = type;
    }
   
    
    
    
    
    
   
    public Utilisateur(long id, String nom, String prenom, String DateNaissance, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.DateNaissance = DateNaissance;
        this.email = email;
    }

    public Utilisateur(long id, String nom, String prenom, String DateNaissance, int age, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.DateNaissance = DateNaissance;
        this.age = age;
        this.email = email;
    }
       

    public Utilisateur(long id, String nom, String prenom, String DateNaissance, int age) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.DateNaissance = DateNaissance;
        this.age = age;
    }
    
    
    //association Reclamation
    
    //private List<Reclamation> reclamations;
    
    
    public Utilisateur(String CIN, String nom, String prenom, String DateNaissance, int age, String pic, String userName, String password, String email) {
        this.CIN = CIN;
        this.nom = nom;
        this.prenom = prenom;
        this.DateNaissance = DateNaissance;
        this.age = age;
        this.pic = pic;
        this.userName = userName;
        this.password = password;
        this.email=email;
       
    }

    public Utilisateur(long id, String CIN, String nom, String prenom, String DateNaissance, int age, String pic, String email, String userName, String password, Type type) {
        this.id = id;
        this.CIN = CIN;
        this.nom = nom;
        this.prenom = prenom;
        this.DateNaissance = DateNaissance;
        this.age = age;
        this.pic = pic;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.type = type;
    }
    
    
    

    public Utilisateur() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public void setId(long id) {
        this.id = id;
    }
    

    public long getId() {
        return id;
    }

 

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return DateNaissance;
    }

    public void setDateNaissance(String DateNaissance) {
        this.DateNaissance = DateNaissance;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return  "CIN=" + CIN + ", Lastname=" + nom + ", Firstname=" + prenom + ", Birthday=" + DateNaissance + ",\n Age=" + age + ", Email=" + email + ", Username=" + userName + ", type=" + type ;
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
        final Utilisateur other = (Utilisateur) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        if (!Objects.equals(this.CIN, other.CIN)) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.DateNaissance, other.DateNaissance)) {
            return false;
        }
        if (!Objects.equals(this.pic, other.pic)) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}

