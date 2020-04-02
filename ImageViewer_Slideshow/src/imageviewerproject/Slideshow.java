
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageviewerproject;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Tienesh
 */
public class Slideshow implements Runnable {
    private final long DELAY = 1;
    private int index = 0;
    private ImageView imageView;
    private final Label lblFileName;
    private final List<Image> images;
    private final List<String> filenames;
    

    
    public Slideshow(ImageView imageView, Label label, 
            List<Image> images, List<String> filenames){
        this.imageView = imageView;
        this.images = images;
        this.lblFileName = label;
        this.filenames = filenames;
    }
    
    /**
     *
     */
    @Override
    public void run(){
        if (!images.isEmpty()){
            try{
                while(true){
                   Platform.runLater( () ->{
                       imageView.setImage(images.get(index));
                    lblFileName.setText(filenames.get(index));
                   });
               index = (index + 1) % images.size();
               TimeUnit.SECONDS.sleep(DELAY);
            } 
        }   
            catch (InterruptedException ex) {
                System.out.println("Slideshow was stopped");
            }
    }
}
}
