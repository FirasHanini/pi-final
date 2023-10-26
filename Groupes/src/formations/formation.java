/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formations;

import java.util.Objects;

/**
 *
 * @author ghmougui ons
 */
public class formation {
    private long id;
    private String titre;
    private String categories;
    private double prix;
    private float remise;
    private String duree;
    private int evaluation;

    public formation(long id, String titre, String categories, double prix, float remise, String duree, String description, String video) {
        this.id = id;
        this.titre = titre;
        this.categories = categories;
        this.prix = prix;
        this.remise = remise;
        this.duree = duree;
        this.description = description;
        this.video = video;
    }
    private String description;
    private String video;

    public formation(String titre, String categories, double prix, float remise, String duree, String description) {
        this.titre = titre;
        this.categories = categories;
        this.prix = prix;
        this.remise = remise;
        this.duree = duree;
        this.description = description;
    }
    public formation (String titre,String categories,double prix, float remise,String duree,String description,String video){
        this.titre = titre;
        this.categories = categories;
        this.prix = prix;
        this.remise = remise;
        this.duree = duree;
        this.description = description;
        this.video=video;
    
    }

   

    public formation(long id, String titre, String categories, double prix, float remise, String duree, String description) {
        this.id = id;
        this.titre = titre;
        this.categories = categories;
        this.prix = prix;
        this.remise = remise;
        this.duree = duree;
        this.description = description;
    }

   
    @Override
    public String toString() {
        return "formation{" + "id=" + id + ", titre=" + titre + ", categories=" + categories + ", prix=" + prix + ", remise=" + remise + ", duree=" + duree + ", evaluation=" + evaluation + ", description=" + description + ", video=" + video + '}';
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
        final formation other = (formation) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.prix) != Double.doubleToLongBits(other.prix)) {
            return false;
        }
        if (Float.floatToIntBits(this.remise) != Float.floatToIntBits(other.remise)) {
            return false;
        }
        if (this.evaluation != other.evaluation) {
            return false;
        }
        if (!Objects.equals(this.titre, other.titre)) {
            return false;
        }
        if (!Objects.equals(this.categories, other.categories)) {
            return false;
        }
        if (!Objects.equals(this.duree, other.duree)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.video, other.video)) {
            return false;
        }
        return true;
    }

    

    

    
    
    
    public formation(long id,String titre,String categories,double prix,float remise,String duree,int evaluation,String description,String video ){
        this.id=id;
        this.titre=titre;
        this.categories=categories;
        this.prix=prix;
        this.remise=remise;
        this.duree=duree;
        this.evaluation=evaluation;
        this.description=description;
        this.video=video;
    }
    
    public formation(){
        
    }

    public formation(int i, String poterie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public float getRemise() {
        return remise;
    }

    public void setRemise(float remise) {
        this.remise = remise;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    
}
    
    

