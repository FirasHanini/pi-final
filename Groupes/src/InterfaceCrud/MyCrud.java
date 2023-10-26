/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceCrud;

import java.util.List;


/**
 *
 * @author Utilisateur 2
 * @param <T>
 */
public interface MyCrud<T> {
    
    public int ajouter(T t);
    public T chercher(T t);
    public int supprimer(T t);
    public List<T> retournerTout ();
    public T modifier(T t ,T nouveau);
    
    
    
}
