/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilisateur;

import java.io.File;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author Utilisateur 2
 */
public class Capture {

    public Capture() {
    }
    
    
    
    public static void captureAndSaveImage(String username){
        String dir=System.getProperty("user.dir");
        System.load(dir+"\\src\\Utilisateur\\opencv_java455.dll");

        VideoCapture camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            System.out.println("Webcam couldn't be opened. Make sure it's connected.");
            System.exit(-1);
        }

        Mat frame = new Mat();
        camera.read(frame);

        if (!frame.empty()) {
            String capName=username+".jpg";
            String outputFileName = dir+"\\src\\images\\"+capName;
            Imgcodecs.imwrite(outputFileName, frame);
            System.out.println("Image captured and saved as " + outputFileName);
        } else {
            System.out.println("No image captured.");
        }

        camera.release();
    }
   }
    

