/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecoart;

import Reclamation.Reclamation;
import Reclamation.ReclamationService;
import Reclamation.State;
import Utilisateur.Capture;
import Utilisateur.MailValidation;
import Utilisateur.Type;
import Utilisateur.Utilisateur;
import Utilisateur.UtilisateurService;
import java.util.Date;
import java.util.Iterator;
import openCV.openCVPath;

/**
 *
 * @author Utilisateur 2
 */
public class EcoArt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        
        UtilisateurService ss=UtilisateurService.getInstance();
        Utilisateur u = new Utilisateur();
        u.setEmail("firashanini@gmail.com");
        u.setType(Type.FORMATEUR);
        u.setCIN("11400929");
        u.setNom("ff");
        u.setPrenom("ff");
        u.setDateNaissance("1997-05-20");
        u.setPic("54645646");
        u.setUserName("sqfqsdfqs");
        u.setPassword("sqfqsdfqs");
        
        /*ss.ajouter(u);
        
        System.out.println(ss.chercher("sqfqsdfqs"));
        
        
        ReclamationService service = ReclamationService.getInstance();
        Reclamation rec = new Reclamation();
        rec.setContenu("test");
        rec.setEtat(State.WAITING);
        rec.setSender(u);
        rec.setId(3);
        //service.supprimer(rec);
        System.out.println(service.chercher(rec));
        service.repondreReclamation(rec, "reponse");
     */
        
      //  MailValidation.sendVerificationCode("firashanini@outlook.fr", MailValidation.generateVerificationCode());
        
        
        
    //  Capture.captureAndSaveImage("firas");
   // MailValidation.sendEmailWithAttachment("firas.hanini@esprit.tn", "pic", "pic zeda trah", "C:\\Users\\Utilisateur 2\\Desktop\\project PI\\projectPi\\EcoArt\\src\\images\\firas.JPG");
   //  MailValidation.sendVerificationCode("firas.hanini@esprit.tn", "test", "message blyad");
   
   
    }
    
    
}
