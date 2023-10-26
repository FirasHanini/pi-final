/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LineOrder;

import java.util.Objects;

/**
 *
 * @author User
 */
public class LineOrder {
    
private int id_o;
private String productName;
private int quantite;
private double prix;

    public LineOrder(int id_o,String productName, int quantite, double prix) {
        this.id_o=id_o;
        this.productName = productName;
        this.quantite = quantite;
        this.prix = prix;
    }

    public int getId_o() {
        return id_o;
    }

    public void setId_o(int id_o) {
        this.id_o = id_o;
    }

    public LineOrder() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
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
        final LineOrder other = (LineOrder) obj;
        if (this.quantite != other.quantite) {
            return false;
        }
        if (Double.doubleToLongBits(this.prix) != Double.doubleToLongBits(other.prix)) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        return true;
    }

    public LineOrder(String productName, int quantite, double prix) {
        this.productName = productName;
        this.quantite = quantite;
        this.prix = prix;
    }

 


    @Override
    public String toString() {
        return "LineOrder{" + "productName=" + productName + ", quantite=" + quantite + ", prix=" + prix + '}';
    }


    

    
}