/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Event.entities;

import java.time.LocalDate;

/**
 *
 * @author ASUS
 */
public class Event {
     private int id_a;
    private int id_u;
    

    public Event() {
    }

    public Event(int id_a, int id_u) {
        this.id_a = id_a;
        this.id_u = id_u;
    }

    public int getId_a() {
        return id_a;
    }

    public void setId_a(int id_a) {
        this.id_a = id_a;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    @Override
    public String toString() {
        return "Event{" + "id_a=" + id_a + ", id_u=" + id_u + '}';
    }

    
    


    
}


