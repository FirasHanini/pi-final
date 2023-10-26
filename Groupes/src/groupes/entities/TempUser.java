/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupes.entities;

/**
 *
 * @author hammeda
 */
public class TempUser {
    private String name;
    private String Email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public TempUser(String name, String Email) {
        this.name = name;
        this.Email = Email;
    }
    
    
    
}
