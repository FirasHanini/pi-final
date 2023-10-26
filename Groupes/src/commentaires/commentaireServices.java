/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commentaires;

import Connection.MyConnection;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author ghmougui ons
 */
public class commentaireServices {
    public commentaireServices(){
    }
    
   MyConnection conx= MyConnection.getInstance();
    Connection myConx=conx.getConnection();

    public int ajouter(commentaire c) {
       // if(this.chercher(c)!=null)
         //   return -1; 
        
        String req="INSERT INTO commentaire ( id, iduser,idformation,text,date,evaluation)"
                    + " VALUES ( ?,?, ?,?,?,?);";
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setLong(1, c.getId());
            prepStat.setLong(2, c.getIduser());
            prepStat.setLong(3, c.getIdformation());
            prepStat.setString(4, c.getText());
             Date sqlDate = Date.valueOf(c.getDate());
            prepStat.setDate(5, sqlDate);
              prepStat.setInt(6, c.getEvaluation());
            
            int executeUpdate = prepStat.executeUpdate();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        return 0;
        
    }

    
    public commentaire chercher(commentaire c ) {
        String req="SELECT * FROM `commentaire` WHERE `id` LIKE ? AND `text` LIKE ? AND `utilisateur` LIKE ? AND "
                + "`date` LIKE ?  ;";
        commentaire found = new commentaire();
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setLong(1, c.getId());
            prepStat.setString(2, c.getText());
            prepStat.setLong(1, c.getIduser());
            prepStat.setLong(1, c.getIdformation());
            Date sqlDate = Date.valueOf(c.getDate());
            prepStat.setDate(4, sqlDate);
           
            
                      
                      
            ResultSet rS= prepStat.executeQuery();
            if(!rS.next())
                return null;
            found.setId( rS.getLong("id"));
            found.setText(rS.getString("text"));
              found.setIduser(rS.getLong("iduser"));
             found.setIdformation(rS.getLong("idformation"));
       LocalDate localDate = rS.getObject("date", LocalDate.class);
          
            found.setDate(localDate);
            
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
       
        
        return found;
    }


    
    public int supprimer(commentaire c) {
        String req="DELETE FROM commentaire WHERE `commentaire`.`iduser` = ?;";
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setLong(1, c.getIduser());
            int rowsAffected =  prepStat.executeUpdate();
            if(rowsAffected==0)
                return -1;
            
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
        
    }

    
public commentaire modifier(commentaire c, commentaire n){
    String req = "UPDATE commentaire SET text = ?, date = ?, evaluation = ? WHERE commentaire.`iduser` = ?;";
        try{
       
            
            
              PreparedStatement prepStat = myConx.prepareStatement(req);
           
              prepStat.setString(1, n.getText());
              Date sqlDate = Date.valueOf(c.getDate());
              prepStat.setDate(2, sqlDate);
              prepStat.setInt(3, n.getEvaluation());
              prepStat.setLong(4, c.getIduser());
 
            int rowsAffected =  prepStat.executeUpdate();
           
              
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return n;
       
    
}
}
        
       


