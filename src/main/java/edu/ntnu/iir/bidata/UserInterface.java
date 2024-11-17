package edu.ntnu.iir.bidata;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInterface {

  //method to initialize recources?
  public void init() {
    System.out.println("Initializing application...");
    //placeholder for future setup logic (like initializing data structures / reading files)
  }

  //method to start the application
  public void start() {
    System.out.println("Starting application...");
    //placeholder for future logic to start the application

    //test grocery class functionality
    try {
      //f.ex creating grocery objects and testing their functionality
      SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
      Date bestBefore = date.parse("06/12/2024");

      Grocery OrangeJuice = new Grocery("Orange Juice", 1.0, "litre", bestBefore, 32.0);
      Grocery flour = new Grocery("Flour", 500.0, "grams", bestBefore, 20.0);

      //print grocery information
      System.out.println(OrangeJuice);
      System.out.println(flour);

    } catch (Exception e) {
      System.out.println("An error occurred: " + e.getMessage());
    }
  }

}
