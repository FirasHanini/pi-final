/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupes.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 *
 * @author hammeda
 */
public class ChatbotFunctions {
 private String botName = "ArtBot";
    
    public boolean isAffirmative(String userInput) {
        return (userInput.toLowerCase().contains("yes")||(userInput.toLowerCase().contains("hello"))||(userInput.toLowerCase().contains("hey"))||(userInput.toLowerCase().contains("hi")));
    }
private String getCurrentDateTime() {
    
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return now.format(formatter);
}
     public String respondToUserInput(String userInput) {
        userInput = userInput.toLowerCase();

       
        if (userInput.contains("upload")) {
            return "To upload your artwork, go to the 'Upload' section in the application and follow the instructions.";
        } else if (userInput.contains("share")) {
            return "You can share your artwork with friends by selecting the 'Share' option in the application.";
        } 
        else if(userInput.contains("help")){ return """
                                                   Certainly! Here's some information that might help you:
                                                   1. How can I share my artwork with other users on the platform?
                                                   2. Can you recommend some tutorials for beginners to improve their art skills?
                                                   3. Is there a chat or messaging feature to communicate with other artists?
                                                   4. How can I search for specific art styles or categories within the app?
                                                   5. How do I collaborate with other artists on joint projects within the application?
                                                   6. What's the process for purchasing or selling artwork on the platform?
                                                   7. Can I import images or references into the application for my artwork?""";}
        else if (userInput.contains("create groups")) {
            return "To create groups, navigate to the 'Groups' tab and click on 'Create New Group.'";
        } 
        else if (userInput.contains("date") || userInput.contains("time")) {
       
        return "The current date and time are " + getCurrentDateTime();}
        else if (userInput.contains("tutorial")) {
        return "You can find a variety of tutorials in the 'Tutorials' section of the application.";
    } else if (userInput.contains("contact support")) {
        return "To contact support, visit the 'Support' page in the app and send us a message.";
    }
    else if (userInput.toLowerCase().contains("hi") || userInput.toLowerCase().contains("hello") || userInput.toLowerCase().contains("hey")) {
        return "Hello, my name is " + botName + ". Do you need help?";
    }
    
    
    
    
        else {
            return "I'm here to assist you with the Art and Communication Application. Feel free to ask any specific questions.";
        }
    }
     public void playNotificationSound() {
    try {
        File soundFile = new File("C:/Users/hamad/OneDrive/Documents/NetBeansProjects/Groupes/src/Sounds/chatbot.wav"); 
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        
        clip.start();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

     
    
}
