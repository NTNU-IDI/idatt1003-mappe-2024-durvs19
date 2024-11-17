package edu.ntnu.iir.bidata;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the user interface for the application. This class provides methods to initialize
 * resources and start the application.
 */
public class UserInterface {

  //method to initialize recources?

  /**
   * Initializes resources needed for the application. This method is a placeholder for future setup
   * logic, such as initializing data structures or reading configuration files.
   */
  public void init() {
    System.out.println("Initializing application...");
    //placeholder for future setup logic (like initializing data structures / reading files)
  }

  //method to start the application

  /**
   * Starts the application. This method currently tests the functionality of the {@code Grocery}
   * class by creating sample grocery objects and displaying their details. In the future, this
   * method can be expanded to include user interaction logic or other application workflows.
   */
  public void start() {
    System.out.println("Starting application...");
    //placeholder for future logic to start the application

    //test grocery class functionality
    try {
      //f.ex creating grocery objects and testing their functionality
      SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
      Date bestBefore = date.parse("06/12/2024");

      Grocery orangeJuice = new Grocery("Orange Juice", 1.0, "litre", bestBefore, 32.0);
      Grocery flour = new Grocery("Flour", 500.0, "grams", bestBefore, 20.0);
      Grocery sugar = new Grocery("Sugar", 1.0, "kg", bestBefore, 15.0);

      //instead of having to call print multiple times
      printGroceryInformation(orangeJuice);
      printGroceryInformation(flour);
      printGroceryInformation(sugar);

    } catch (Exception e) {
      System.out.println("An error occurred: " + e.getMessage());
    }
  }

  //helper method to print grocery information
  private void printGroceryInformation(Grocery grocery) {
    System.out.println(grocery);
  }

}
