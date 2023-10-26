/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openCV;

/**
 *
 * @author Utilisateur 2
 */
public class openCVPath {
    
    public static String getPath(){
        return System.clearProperty("user.dir");
    }
    
}
