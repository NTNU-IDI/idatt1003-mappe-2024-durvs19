package edu.ntnu.iir.bidata;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a grocery item with details such as name, quantity, unit of measurement, best-before
 * date, and price per unit.
 */
public class Grocery {

  private String name;
  private double quantity;
  private String unit; // grams, litres, pcs, etc.
  private Date bestBefore;
  private double pricePerUnit; // so its easier to calculate price of different quantities

  // create a constructor for this

  /**
   * Constructs a new Grocery object with the specified attributes.
   *
   * @param name         the name of the grocery item; must not be null or empty
   * @param quantity     the quantity of the grocery item; must be greater than 0
   * @param unit         the unit of measurement for the quantity; must not be null or empty
   * @param bestBefore   the best-before date of the grocery item; must not be null
   * @param pricePerUnit the price per unit of the grocery item; must be greater than 0
   * @throws IllegalArgumentException if any argument is invalid
   */
  public Grocery(String name, double quantity, String unit, Date bestBefore, double pricePerUnit) {

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
    if (pricePerUnit <= 0) {
      throw new IllegalArgumentException("Price per unit must be greater than 0");
    }
    // reference variable used to access instance variables and methods of the current object
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.bestBefore = bestBefore;
    this.pricePerUnit = pricePerUnit;

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

  /**
   * Gets the price per unit of the grocery item.
   *
   * @return the price per unit of the grocery item
   */

  public double getpricePerUnit() {
    return pricePerUnit;
  }

  // add a toString method to return a string representation
  @Override
  public String toString() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String formattedDate = dateFormat.format(bestBefore);

    return String.format("%.1f %s of %s, best before: %s, price per unit: %.1f",
        quantity, unit, name, formattedDate, pricePerUnit);
  }

}
