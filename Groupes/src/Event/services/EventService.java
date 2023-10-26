/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Event.services;

import Event.entities.Event;
import Event.entities.EventAdmin;

import Event.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author ASUS
 */
public class EventService {
     MyConnection conx= MyConnection.getInstance();
    Connection myConx=conx.getConnection();
   
    PreparedStatement pst = null;
    ResultSet rs = null;
    EventAdminService a =new EventAdminService();
    
    public void showevents(Event e, String path) throws SQLException{
    
           

    
  

       
}
    
    
        public void ShowReservation (TableColumn<EventAdmin, String> colnom_a,TableColumn<EventAdmin,String>coldate_a,TableColumn<EventAdmin,String>collieu_a,
            TableColumn<EventAdmin,String>coldesc_a,TableColumn<EventAdmin,Integer> colprix_a,TableView<EventAdmin> tabResv_a) throws SQLException {
         
        
         ObservableList< EventAdmin> resvlist =getEventlist();
        
     
          
         
        colnom_a.setCellValueFactory(new PropertyValueFactory<>("nom_a")); ///nom_a de clase eventadmin
        coldate_a.setCellValueFactory(new PropertyValueFactory<>("date_a"));
        collieu_a.setCellValueFactory(new PropertyValueFactory<>("lieu_a"));
        coldesc_a.setCellValueFactory(new PropertyValueFactory<>("description_a"));
       
        colprix_a.setCellValueFactory(new PropertyValueFactory<>("prix_a"));
        tabResv_a.setItems(resvlist);
}
         private ObservableList<EventAdmin> getEventlist() throws SQLException {
         
          
     
     
   
     String query = "select * from eventadmin";
 
 
            pst = myConx.prepareStatement(query);
              ResultSet rs =pst.executeQuery(query);
             ObservableList<EventAdmin> Eventlist =FXCollections.observableArrayList();
            while (rs.next()){
              Eventlist.add(new EventAdmin(
    rs.getInt("id_a"),         ///id_a de DB
    rs.getString("nom_a"),
    rs.getDate("date_a").toLocalDate(),
    rs.getString("lieu_a"),
    rs.getString("description_a"),
    rs.getString("image_a"),
    rs.getInt("prix_a"),
                      rs.getString("path_qr")
));
            }
              
     
        return Eventlist;
         
     
           
}
         
       
        
          }
          
          
          
         




                  /* String query2 = "select * from eventuser";
              pst = myConx.prepareStatement(query2);
               rs =pst.executeQuery(query2);
           
            while (rs.next()){
              Eventlist.add(new EventAdmin(
    rs.getInt("id"),         ///id_a de DB
    rs.getString("nom"),
    rs.getDate("date").toLocalDate(),
    rs.getString("lieu"),
    rs.getString("description"),
    rs.getString("image"),
    rs.getInt("prix")
));
              
                      
                        } */
     

