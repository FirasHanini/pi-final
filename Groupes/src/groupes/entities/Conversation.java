/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupes.entities;

import java.util.Date;

/**
 *
 * @author hammeda
 */
public class Conversation {
    int id;
    int User1ID;
     int User2ID;
     String Msg;
    Date dateChat ;

    public int getId() {
        return id;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        this.Msg = msg;
    }

    public Conversation(int id, int User1ID, int User2ID, String msg, Date dateChat) {
        this.id = id;
        this.User1ID = User1ID;
        this.User2ID = User2ID;
        this.Msg = msg;
        this.dateChat = dateChat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser1ID() {
        return User1ID;
    }

    public void setUser1ID(int User1ID) {
        this.User1ID = User1ID;
    }

    public int getUser2ID() {
        return User2ID;
    }

    public void setUser2ID(int User2ID) {
        this.User2ID = User2ID;
    }

    public Date getDateChat() {
        return dateChat;
    }

    public void setDateChat(Date dateChat) {
        this.dateChat = dateChat;
    }

    public Conversation(int id, int User1ID, int User2ID, Date dateChat) {
        this.id = id;
        this.User1ID = User1ID;
        this.User2ID = User2ID;
        this.dateChat = dateChat;
    }
    
    
    
}
