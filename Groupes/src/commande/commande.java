/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commande;

import LineOrder.LineOrder;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author User
 */
public class commande {
    long id_c;
    String nomC;
    int id_client;
    String adresse;
    LocalDate date ;
    int numTel;
    String email; 
    double total;
    private List<LineOrder> lineOrders;


    public commande(String nomC, int id_client, String adresse, LocalDate date, int numTel, String email, double total) {
        this.nomC = nomC;
        this.id_client = id_client;
        this.adresse = adresse;
        this.date = date;
        this.numTel = numTel;
        this.email = email;
        this.total=total;
    }
   

    public commande(String nomC, int id_client, String adresse, int numTel, String email) {
        this.nomC = nomC;
        this.id_client = id_client;
        this.adresse = adresse;
        this.numTel = numTel;
        this.email = email;
    }



   

    public commande() {
    }

    public List<LineOrder> getLineOrders() {
        return lineOrders;
    }

    public void setLineOrders(List<LineOrder> lineOrders) {
        this.lineOrders = lineOrders;
    }
    

    public long getId_c() {
        return id_c;
    }

    public void setId_c(long id_c) {
        this.id_c = id_c;
    }

    public String getNomC() {
        return nomC;
    }

    public void setNomC(String nomC) {
        this.nomC = nomC;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
        final commande other = (commande) obj;
        if (this.id_c != other.id_c) {
            return false;
        }
        if (this.id_client != other.id_client) {
            return false;
        }
        if (this.numTel != other.numTel) {
            return false;
        }
        if (!Objects.equals(this.nomC, other.nomC)) {
            return false;
        }
        if (!Objects.equals(this.adresse, other.adresse)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    public commande(long id_c, LocalDate date) {
        this.id_c = id_c;
        this.date = date;
        this.lineOrders=new ArrayList<>();
    }
     public void addLineOrder(LineOrder lineOrder) {
        lineOrders.add(lineOrder);
    }


    
    
}
