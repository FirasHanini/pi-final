/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilisateur;

import Connection.MyConnection;
import InterfaceCrud.MyCrud;
import Reclamation.ReclamationService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;



/**
 *
 * @author Utilisateur 2
 */
public class UtilisateurService implements MyCrud<Utilisateur> {

    
    
    private UtilisateurService() {
    }
    
    private static UtilisateurService instance ;
    
    public static UtilisateurService getInstance(){
        if(instance==null)
            instance=new UtilisateurService();
        
        return instance;
    }
    
    
    
    
    
    MyConnection conx= MyConnection.getInstance();
    Connection myConx=conx.getConnection();

    @Override
    public int ajouter(Utilisateur u) {
        if(this.chercher(u)!=null)
            return -1; 
        
        String req="INSERT INTO `utilisateur` ( `cin`, `nom`, `prenom`, `date_naissance`, `age`, `pic`, `username`, `password`,`email`,`type`)"
                    + " VALUES ( ?,?, ?,?,?, ?, ?, ?,?,?);";
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            
            prepStat.setString(1, u.getCIN());
            prepStat.setString(2, u.getNom());
            prepStat.setString(3, u.getPrenom());
            prepStat.setString(4, u.getDateNaissance());
            prepStat.setInt(5, u.getAge());
            prepStat.setString(6, u.getPic());
            prepStat.setString(7, u.getUserName());
            prepStat.setString(8, u.getPassword());
            prepStat.setString(9, u.getEmail());
            prepStat.setString(10, u.getType());
            int rowsAffected =  prepStat.executeUpdate();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        return 0;
    }

    @Override
    public Utilisateur chercher(Utilisateur u) {
       // String req="SELECT * FROM `utilisateur` WHERE `cin` LIKE ? AND `nom` LIKE ? AND `prenom` LIKE ? AND "
         //       + "`date_naissance` = ? AND `age` = ? AND  `username` LIKE ? ;";
         String req="SELECT * FROM `utilisateur` WHERE  `id` LIKE ? ;";
         
        Utilisateur found = new Utilisateur();
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            
         /*  prepStat.setString(1, u.getCIN());
            prepStat.setString(2, u.getNom());
            prepStat.setString(3, u.getPrenom());
            prepStat.setString(4, u.getDateNaissance());
            prepStat.setInt(5, u.getAge());
           
            prepStat.setString(6, u.getUserName());*/
         
         prepStat.setLong(1, u.getId());
            
            ResultSet rS= prepStat.executeQuery();
            if(!rS.next())
                return null;
            found.setId(rS.getLong("id"));
            found.setCIN(rS.getString("cin"));
            found.setNom(rS.getString("nom"));
            found.setPrenom(rS.getString("Prenom"));
            found.setDateNaissance(rS.getString("date_naissance"));
            found.setAge(rS.getInt("age"));
            found.setEmail(rS.getString("email"));
            found.setUserName(rS.getString("username"));
            found.setType(Type.valueOf(rS.getString("type")));
            found.setPic(rS.getString("pic"));
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
       
        
        return found;
    }

    @Override
    public int supprimer(Utilisateur u) {
    
        String req="DELETE FROM utilisateur WHERE `utilisateur`.`username` LIKE ?;";
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
           // prepStat.setLong(1, u.getId());
           prepStat.setString(1, u.getUserName());
            int rowsAffected =  prepStat.executeUpdate();
            if(rowsAffected==0)
                return -1;
            
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }

    @Override
    public List<Utilisateur> retournerTout() {
        List<Utilisateur> retour= new ArrayList();
        String req ="select * from `utilisateur` ";
        
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            ResultSet rS= prepStat.executeQuery();
            
            while(rS.next())
            {
            Utilisateur found= new Utilisateur();
            found.setCIN(rS.getString("cin"));
            found.setNom(rS.getString("nom"));
            found.setPrenom(rS.getString("Prenom"));
            found.setDateNaissance(rS.getString("date_naissance"));
            found.setAge(rS.getInt("age"));
            found.setPic(rS.getString("pic"));
            found.setEmail(rS.getString("email"));
            found.setType(Type.valueOf(rS.getString("type")));
            found.setUserName(rS.getString("username"));
            found.setId(rS.getLong("id"));
            retour.add(found);
                
            }
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return retour;
    }

    @Override
    public Utilisateur modifier(Utilisateur u, Utilisateur n) {
       
            
            String req ="UPDATE `utilisateur` SET `cin` = ?, `nom` = ?, `prenom` = ?, "
                    + "`date_naissance` = ?, `age` = ?, `pic` = ?, `username` = ?, `password` = ?, "
                    + "`type`= ? WHERE `utilisateur`.`id` = ?;";
        try {
            
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setString(1, n.getCIN());
            prepStat.setString(2, n.getNom());
            prepStat.setString(3, n.getPrenom());
            prepStat.setString(4, n.getDateNaissance());
            prepStat.setInt(5, n.getAge());
            prepStat.setString(6, n.getPic());
            prepStat.setString(7, n.getUserName());
            prepStat.setString(8, n.getPassword());
            prepStat.setString(9, n.getType());
            prepStat.setLong(10, u.getId());
            int rowsAffected =  prepStat.executeUpdate();
           
            
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return n;
    }
    
    
   
    public Utilisateur chercher(String username) {
       
         String req="SELECT * FROM `utilisateur` WHERE  `username` LIKE ? ;";
         
        Utilisateur found = new Utilisateur();
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            
       
         
         prepStat.setString(1, username);
            
            ResultSet rS= prepStat.executeQuery();
            if(!rS.next())
                return null;
            found.setId(rS.getLong("id"));
            found.setCIN(rS.getString("cin"));
            found.setNom(rS.getString("nom"));
            found.setPrenom(rS.getString("Prenom"));
            found.setDateNaissance(rS.getString("date_naissance"));
            found.setAge(rS.getInt("age"));
            found.setEmail(rS.getString("email"));
            found.setUserName(rS.getString("username"));
            found.setType(Type.valueOf(rS.getString("type")));
            found.setPic(rS.getString("pic"));
            found.setPassword(rS.getString("password"));
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
       
        
        return found;
    }
    
    
    
    
    
     public List<Utilisateur> retournerAdmin() {
        List<Utilisateur> retour= new ArrayList();
        String req ="select * from `utilisateur` where type like 'ADMIN' ";
        
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            ResultSet rS= prepStat.executeQuery();
            
            while(rS.next())
            {
            Utilisateur found= new Utilisateur();
             found.setId(rS.getLong("id"));
            found.setCIN(rS.getString("cin"));
            found.setNom(rS.getString("nom"));
            found.setPrenom(rS.getString("Prenom"));
            found.setDateNaissance(rS.getString("date_naissance"));
            found.setAge(rS.getInt("age"));
            found.setPic(rS.getString("pic"));
            found.setEmail(rS.getString("email"));
            found.setType(Type.valueOf(rS.getString("type")));
            found.setUserName(rS.getString("username"));
            retour.add(found);
                
            }
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return retour;
    }
    
    
    
    
    
    
    
    
    
    
    
    ///////////////////////////////////////////////////////LOGIN/////////////////////////////////////////////////
    public int login (String username , String password){
        String req="select password from utilisateur where username=?;";
       
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            
            prepStat.setString(1, username);
            
            ResultSet rS= prepStat.executeQuery();
            if(!rS.next())
                return -1;
            if(!password.equals(rS.getString("password")))
                return -2;
           
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        
        return 0;
    }
    
    public int getIfAdmin(String username){
        Utilisateur chercher=new Utilisateur();
        chercher.setUserName(username);
        Utilisateur found = this.chercher(chercher);
        
        if(found.getType()==Type.ADMIN.name())
            return 1 ;
        
        return 0;
    }
    
    
    public String calculeAge(String dateN) throws ParseException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate dateAnniversaire = LocalDate.parse(dateN, formatter);
    LocalDate dateCourante = LocalDate.now();

    Period difference = Period.between(dateAnniversaire, dateCourante);

    int age = difference.getYears();
    
    return String.valueOf(age);
}
    
    
    public int unicUsername(String username){
        String req="select * from utilisateur where username=?;";
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            
            prepStat.setString(1, username);
            
            ResultSet rS= prepStat.executeQuery();
            if(rS.next())
                return -1;
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }
    
    public int uniEmail (String email){
        String req="select * from utilisateur where email=?;";
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            
            prepStat.setString(1, email);
            
            ResultSet rS= prepStat.executeQuery();
            if(rS.next())
                return -1;
        
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
        
    }
    
         public int  passwordStrength(String password) {
        
        if (password.length() < 8) {
            return -2;
        }

        
        boolean hasDigit = false;
        boolean hasLower = false;
        boolean hasUpper = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else {
                hasSpecial = true;
            }
        }

        if (hasDigit && hasLower && hasUpper && hasSpecial) {
            return 0;
        } else if (hasDigit && (hasLower || hasUpper)) {
            return -1;
        } else {
            return -2;
        }
    }
        
        
    
    
    
}
