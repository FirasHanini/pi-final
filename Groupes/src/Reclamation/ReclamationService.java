/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reclamation;

import Connection.MyConnection;
import InterfaceCrud.MyCrud;
import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Utilisateur 2
 */
public class ReclamationService implements MyCrud<Reclamation> {
    
    MyConnection conx= MyConnection.getInstance();
    Connection myConx=conx.getConnection();

    private ReclamationService() {
    }
    
    
    
    private static ReclamationService instance ;
    
    
    public static ReclamationService getInstance(){
        if(instance==null)
            instance=new ReclamationService();
        
        return instance;      
    }
    UtilisateurService userService =UtilisateurService.getInstance();

    @Override
    public int ajouter(Reclamation t) {
        String req="INSERT INTO `reclamation` ( `contenu`, `etat`,`senderid`) VALUES (?, ?,?);";
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setString(1, t.getContenu());
            prepStat.setString(2, t.getEtat());
            prepStat.setLong(3, t.getSender().getId());
            int rowsAffected =  prepStat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    @Override
    public Reclamation chercher(Reclamation t) {
        String req="SELECT * FROM `reclamation` WHERE `id` = ?;";
        Reclamation found= new Reclamation();
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setLong(1, t.getId());
            
            
            ResultSet rS= prepStat.executeQuery();
            if (!rS.next())
                return null;
            found.setContenu(rS.getString("contenu"));
            found.setEtat(State.valueOf(rS.getString("etat")));
          //  found.setReponse(rS.getString("reponse"));
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return found;
    }

    @Override
    public int supprimer(Reclamation t) {
        if(this.chercher(t)==null)
            return -1;
        String req=" DELETE FROM reclamation WHERE `reclamation`.`id` = ?;";
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setFloat(1, t.getId());
            int rowsAffected =  prepStat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }
     
    public int supprimerParSender(Utilisateur sender) {
        if(this.retournerParUtilisateur(sender)==null)
            return -1;
        String req=" DELETE FROM reclamation WHERE `reclamation`.`senderid` = ?;";
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setFloat(1, sender.getId());
            int rowsAffected =  prepStat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return 0;
    }

    @Override
    public List<Reclamation> retournerTout() {
        String req="SELECT * FROM `reclamation`";
        List<Reclamation> retour = new ArrayList();
        Utilisateur temp=new Utilisateur();
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            ResultSet rS= prepStat.executeQuery();
            while(rS.next())
            {
            Reclamation found= new Reclamation();
            found.setContenu(rS.getString("contenu"));
            found.setId(rS.getLong("id"));
            found.setEtat(State.valueOf(rS.getString("etat")));
            temp.setId(rS.getLong("senderid"));
            found.setSender(userService.chercher(temp));
         
            retour.add(found);
                
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return retour;
    }

    @Override
    public Reclamation modifier(Reclamation t, Reclamation nouveau) {
        String req ="UPDATE `reclamation` SET `contenu` = ? , `etat` = ? , `reponse` = ? "
                + "WHERE `reclamation`.`id` = ?;";
        
        
        try {
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setString(1, nouveau.getContenu());
            prepStat.setString(2, nouveau.getEtat());
            prepStat.setString(3, nouveau.getReponse());
            prepStat.setLong(4, t.getId());
            int rowsAffected =  prepStat.executeUpdate();
        } catch (SQLException ex) {
                System.out.println(ex.getMessage());
        }
        
        
        return nouveau;
    }
    
      public List<Reclamation> retournerParUtilisateur( Utilisateur u) {
        String req="SELECT * FROM `reclamation` where senderid=?";
        List<Reclamation> retour = new ArrayList();
        Utilisateur temp=new Utilisateur();
        
        try {
            
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setFloat(1, u.getId());
            ResultSet rS= prepStat.executeQuery();
            while(rS.next())
            {
            Reclamation found= new Reclamation();
            found.setContenu(rS.getString("contenu"));
            found.setId(rS.getLong("id"));
            found.setEtat(State.valueOf(rS.getString("etat")));
            found.setReponse(rS.getString("reponse"));
            temp.setId(rS.getLong("senderid"));
            found.setSender(userService.chercher(temp));
         
            retour.add(found);
                
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return retour;
    }
      
      
      public List<Reclamation> retournerParUtilisateur( String username) {
        String req="SELECT * FROM reclamation r JOIN utilisateur u ON u.id = r.senderid WHERE u.username = ?;";
        List<Reclamation> retour = new ArrayList();
        Utilisateur temp=new Utilisateur();
        
        try {
            
            PreparedStatement prepStat = myConx.prepareStatement(req);
            prepStat.setString(1, username);
            ResultSet rS= prepStat.executeQuery();
            while(rS.next())
            {
            Reclamation found= new Reclamation();
            found.setContenu(rS.getString("contenu"));
            found.setId(rS.getLong("id"));
            found.setEtat(State.valueOf(rS.getString("etat")));
            found.setReponse(rS.getString("reponse"));
            temp.setId(rS.getLong("senderid"));
            found.setSender(userService.chercher(temp));
         
            retour.add(found);
                
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return retour;
    }
      
      
      
      
      
      public int repondreReclamation(Reclamation r, String reponse){
          Reclamation nouveau =r;
          nouveau.setReponse(reponse);
          this.modifier(r, nouveau);
          return 0;
      }
    
}
