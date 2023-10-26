/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupes.entities;

/**
 *
 * @author hammeda
 */
public class Groupe {
    private int id;
    private String name;
    private int size;
    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Groupe(int id, String name, int size, String logo) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.logo = logo;
    }

   
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int sizeM) {
        this.size = sizeM;
    }
    
    
    
}
