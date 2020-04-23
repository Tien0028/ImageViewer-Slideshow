package imageviewerproject;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable
{
    private final List<Image> images = new ArrayList<>();
    private final List<String> filenames = new ArrayList<>();
    private int currentImageIndex = 0;
    private ExecutorService executor; // create ExecutorService to manage threads


    
    /*
    
    Multithreading is to run several threads of the same program at the same time.
    A thread is an execution context, which is all the information a CPU needs to execute a stream of instructions.
    */

    @FXML
    Parent root;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnPrevious;

    @FXML
    private Button btnNext;

    @FXML
    private ImageView imageView;
    
    @FXML
    private Button btnStartSlideshow;
    
    @FXML
    private Button btnStopSlideshow;
    
    @FXML
    private Label lblFileName;

    private void handleBtnLoadAction(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images", 
            "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));        
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
                
        if (!files.isEmpty())
        {
            files.forEach((File f) ->
            {
                filenames.add(f.getName());
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }
    }

    private void handleBtnPreviousAction(ActionEvent event)
    {
        if (!images.isEmpty())
        {
            currentImageIndex = 
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }

    private void handleBtnNextAction(ActionEvent event)
    {
        if (!images.isEmpty())
        {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }
    }

    private void handleBtnStartSlideshowAction(ActionEvent event)
    {
        Runnable slideshow = new Slideshow(imageView, lblFileName, images, filenames);
        executor = Executors.newSingleThreadExecutor();
        executor.submit(slideshow); // Start the task
    }

    private void handleBtnStopSlideshowAction(ActionEvent event)
    {
        executor.shutdownNow();
        //the executor.shutdown-method will interrupt the thread in the slideshow class. 
        // Shut down the thread pool when task or runnable stops
    }
    
    

    
    private void displayImage()
    {
        if (!images.isEmpty() || filenames.isEmpty())
        {
            lblFileName.setText(filenames.get(currentImageIndex));
            imageView.setImage(images.get(currentImageIndex));
        }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        btnLoad.setOnAction((ActionEvent event) ->
        {
            handleBtnLoadAction(event);
        });

        btnPrevious.setOnAction((ActionEvent event) ->
        {
            handleBtnPreviousAction(event);
        });
        
        btnNext.setOnAction((ActionEvent event) ->
        {
            handleBtnNextAction(event);
        });
        
        btnStartSlideshow.setOnAction((ActionEvent event) ->
        {
            handleBtnStartSlideshowAction(event);
        });
        
        btnStopSlideshow.setOnAction((ActionEvent event) ->
        {
            handleBtnStopSlideshowAction(event);
        });
    }

}
