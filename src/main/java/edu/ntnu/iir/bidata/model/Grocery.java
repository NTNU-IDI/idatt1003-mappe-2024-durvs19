package edu.ntnu.iir.bidata.model;

import java.time.LocalDate;
import lombok.Getter;

/** Represents a grocery item in the inventory. */
@Getter
public class Grocery {
  /** Name of the grocery item. */
  private String name;
  /** Quantity of the grocery item (e.g., in liters or kilograms). */
  private double quantity;
  /** Unit of measurement for the quantity (e.g., "liters", "kg", "pieces"). */
  private String unit;
  /** Price per unit of the grocery item (in NOK). */
  private double pricePerUnit;
  /** Expiry date of the grocery item. */
  private LocalDate expiryDate; // so its easier to calculate price of different quantities

  // creating a constructor for this

  /**
   * Constructs a new Grocery item.
   *
   * @param name the name of the grocery item
   * @param quantity the quantity of the grocery item
   * @param unit the unit of measurement for the quantity
   * @param pricePerUnit the price per unit of the grocery item
   * @param expiryDate the expiry date of the grocery item
   */
  public Grocery(String name, double quantity, String unit,
      double pricePerUnit, LocalDate expiryDate) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }
    if (unit == null || unit.isEmpty()) {
      throw new IllegalArgumentException("Unit cannot be null or empty");
    }
    if (pricePerUnit < 0) {
      throw new IllegalArgumentException("Price per unit cannot be negative");
    }
    if (expiryDate == null) {
      throw new IllegalArgumentException("Expiry date cannot be null");
    }
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.pricePerUnit = pricePerUnit;
    this.expiryDate = expiryDate;
  }

  /**
   * Sets the quantity of the grocery item (if f.ex you added 2 eggs separately it updates to 2).
   *
   * @param quantity the quantity to set
   * @throws IllegalArgumentException if the quantity is negative
   */
  public void setQuantity(double quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }
    this.quantity = quantity;
  }

  /**
   * Returns a string representation of the grocery item.
   *
   * @return a string representation of the grocery item
   */
  @Override
  public String toString() {
    return String.format(
        "%s: %.2f %s, NOK %.2f/unit, Expiry: %s", name, quantity, unit, pricePerUnit, expiryDate);
  }

}
