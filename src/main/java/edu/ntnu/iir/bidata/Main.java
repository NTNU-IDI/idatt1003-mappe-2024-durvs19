package edu.ntnu.iir.bidata;
import edu.ntnu.iir.bidata.UserInterface.UserInterface;
import edu.ntnu.iir.bidata.model.Recipe;

/**
 * The entry point of the application. This class initializes and starts the User Interface (UI).
 */
public class Main {

  /**
   * The main method of the application. It creates an instance of the {@code UserInterface} class
   * and calls its methods to initialize and start the application.
   *
   * @param args command-line arguments (not used in this application)
   */
  //@param args command-line arguments is a way to document the parameter of the main method in Java
  public static void main(String[] args) {
    UserInterface ui = new UserInterface(); // create a new UserInterface object
    ui.init(); // call the init method
    ui.start(); // call the start method
  }
}