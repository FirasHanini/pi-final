/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product;

import java.sql.Blob;
import java.util.Objects;
/**
 *
 * @author User
 */
public class product {
    private long id_pdts;
   // private long id_u;
    private String nom;
    private double prix;
    private int qte ;
    private String categ;
    private String matiere;
    private String description;
    private String image ;

    public product(String nom, double prix, int qte, String categ, String matiere, String description, String image) {
        this.nom = nom;
        this.prix = prix;
        this.qte = qte;
        this.categ = categ;
        this.matiere = matiere;
        this.description = description;
        this.image = image;
    }

    public product(long id_pdts, String nom, double prix, int qte, String categ, String matiere, String description, String image) {
        this.id_pdts = id_pdts;
        this.nom = nom;
        this.prix = prix;
        this.qte = qte;
        this.categ = categ;
        this.matiere = matiere;
        this.description = description;
        this.image = image;
    }
    
   
    
    
    public product (long id_pdts,String nom ,double prix,int qte,String categ,String matiere,String description){
    this.id_pdts=id_pdts;
    this.nom=nom;
    this.prix=prix;
    this.qte=qte;
    this.categ=categ;
    this.matiere=matiere;
    this.description=description;
    
    }

    public product(long id_pdts,long id_u, String nom, double prix, int qte, String categ, String matiere, String description, String image) {
        this.id_pdts = id_pdts;
       // this.id_u=id_u;
        this.nom = nom;
        this.prix = prix;
        this.qte = qte;
        this.categ = categ;
        this.matiere = matiere;
        this.description = description;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage( String  image) {
        this.image = image;
    }
    

    public product() {
    }
    

    
    public long getId_pdts() {
        return id_pdts;
    }

    public void setId_pdts(long id_pdts) {
        this.id_pdts = id_pdts;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

   /* public long getId_u() {
        return id_u;
    }

    public void setId_u(long id_u) {
        this.id_u = id_u;
    }*/

   

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
        final product other = (product) obj;
        if (this.id_pdts != other.id_pdts) {
            return false;
        }
        if (Double.doubleToLongBits(this.prix) != Double.doubleToLongBits(other.prix)) {
            return false;
        }
        if (this.qte != other.qte) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.categ, other.categ)) {
            return false;
        }
        if (!Objects.equals(this.matiere, other.matiere)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return "product{" + ", nom=" + nom + ", categ=" + categ + ", prix=" + prix + ", matiere=" + matiere + ", desc=" + description + ", qte=" + qte + '}';
    }

    public product(String nom, double prix, int qte, String categ, String matiere, String description) {
        this.nom = nom;
        this.prix = prix;
        this.qte = qte;
        this.categ = categ;
        this.matiere = matiere;
        this.description = description;
    }
    

  
}
