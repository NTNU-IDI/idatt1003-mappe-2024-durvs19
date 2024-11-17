package edu.ntnu.iir.bidata;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Grocery {

  private String name;
  private double quantity;
  private String unit; // grams, litres, pcs, etc.
  private Date bestBefore;
  private double PricePerUnit; // so its easier to calculate price of different quantities

  // create a constructor for this
  public Grocery(String name, double quantity, String unit, Date bestBefore, double PricePerUnit) {

    //validate inputs in constructor
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than 0");
    }
    if (unit == null || unit.isEmpty()) {
      throw new IllegalArgumentException("Unit cannot be null or empty");
    }
    if (bestBefore == null) {
      throw new IllegalArgumentException("Best before cannot be null");
    }
    if (PricePerUnit <= 0) {
      throw new IllegalArgumentException("Price per unit must be greater than 0");
    }
    // reference variable used to access instance variables and methods of the current object
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.bestBefore = bestBefore;
    this.PricePerUnit = PricePerUnit;

  }

  public String getName() {
    return name;
  }

  public double getQuantity() {
    return quantity;
  }

  public String getUnit() {
    return unit;
  }

  public Date getBestBefore() {
    return bestBefore;
  }

  public double getPricePerUnit() {
    return PricePerUnit;
  }

  // add a toString method to return a string representation
  @Override
  public String toString() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String formattedDate = dateFormat.format(bestBefore);

    return String.format("%.1f %s of %s, best before: %s, price per unit: %.1f",
        quantity, unit, name, formattedDate, PricePerUnit);
  }

}
