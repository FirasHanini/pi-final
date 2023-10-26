/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupes.entities;
import Utilisateur.Type;
import Utilisateur.Utilisateur;

/**
 *
 * @author hammeda
 */
public class Member extends Utilisateur{
    private int groupID;
    private String Role;

    public Member(long id,String CIN, String nom, String prenom, String email, String DateNaissance, int age , String pic , String password,String userName,Type type) {
        super(id,CIN, nom, prenom, DateNaissance, age, pic, email,userName, password,type);
    }
    public Member(int groupID, String Role, int id, String nom, String prenom, String email, String DateNaissance,int age) {
       super(id,nom,prenom,DateNaissance,age,email);
        this.groupID = groupID;
        this.Role = Role;
        
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }
    
   

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
    
}
