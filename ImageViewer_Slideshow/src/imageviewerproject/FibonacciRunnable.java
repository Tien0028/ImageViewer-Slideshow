package imageviewerproject;


import javafx.application.Platform;
import javafx.scene.control.Label;

// FibonacciRunnable.java
// Runnable class for calculating Fibonacci numbers in the background

public class FibonacciRunnable implements Runnable {
   private final int n; // Fibonacci number to calculate
   private Label label;

   // constructor
   public FibonacciRunnable(int n, Label label) {
      this.n = n;
      this.label = label;
   } 

    @Override
    public void run() {
      Long result = fibonacci(n);
      
      // Run code to update the GUI on the JavaFX application thread
      Platform.runLater( () -> 
          {
              label.setText(result.toString());
          }
      );
    }
    
    // recursive Fibonacci method
   private long fibonacci(long number) {
      if (number == 0 || number == 1) {
         return number;
      }
      else {
         return fibonacci(number - 1) + fibonacci(number - 2);
      }
   }

    
}
