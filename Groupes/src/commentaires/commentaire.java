/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commentaires;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author ghmougui ons
 */
public class commentaire {
    private long id;
    private String text;
    private long iduser;
    private long idformation;
    private LocalDate date;
    private int evaluation;

   

   
    public int getEvaluation() {
        return evaluation;
    }

    public commentaire(long iduser,String text,  LocalDate date, int evaluation) {
        this.text = text;
        this.iduser = iduser;
        this.date = date;
        this.evaluation = evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public commentaire(long id, String text, long iduser, long idformation, LocalDate date, int evaluation) {
        this.id = id;
        this.text = text;
        this.iduser = iduser;
        this.idformation = idformation;
        this.date = date;
        this.evaluation = evaluation;
    }
    public commentaire(){
        
    }
    

    public commentaire(long id, String text, long iduser, long idformation, LocalDate date) {
        this.id = id;
        this.text = text;
        this.iduser = iduser;
        this.idformation = idformation;
        this.date = date;
        
    }
    public commentaire(String text){
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getIduser() {
        return iduser;
    }

    public void setIduser(long iduser) {
        this.iduser = iduser;
    }

    public long getIdformation() {
        return idformation;
    }

    public void setIdformation(long idformation) {
        this.idformation = idformation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "commentaire{" + "text=" + text + ", iduser=" + iduser + ", idformation=" + idformation + ", date=" + date + '}';
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
        final commentaire other = (commentaire) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.iduser != other.iduser) {
            return false;
        }
        if (this.idformation != other.idformation) {
            return false;
        }
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        return true;
    }

    public commentaire(String text, int evaluation) {
        this.text = text;
        this.evaluation = evaluation;
    }
    

   
    

 

    
}
