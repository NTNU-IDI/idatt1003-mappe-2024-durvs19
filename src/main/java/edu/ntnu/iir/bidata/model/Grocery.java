package edu.ntnu.iir.bidata.model;

import java.time.LocalDate;
import lombok.Getter;

/**
 * Represents a grocery item in the inventory, including its name, quantity, unit of measurement,
 * price per unit, and expiry date.
 */
@Getter
public class Grocery {
  /** Name of the grocery item. */
  private final String name;
  /** Quantity of the grocery item (e.g., in liters or kilograms). */
  private double quantity;
  /** Unit of measurement for the quantity (e.g., "liters", "kg", "pieces"). */
  private final String unit;
  /** Price per unit of the grocery item (in NOK). */
  private final double pricePerUnit;
  /** Expiry date of the grocery item. */
  private final LocalDate expiryDate; // so its easier to calculate price of different quantities

  // creating a constructor for this

  /**
   * Constructs a new {@code Grocery} item.
   *
   * @param name the name of the grocery item
   * @param quantity the quantity of the grocery item
   * @param unit the unit of measurement for the quantity
   * @param pricePerUnit the price per unit of the grocery item
   * @param expiryDate the expiry date of the grocery item
   * @throws IllegalArgumentException if {@code name} is null or empty, {@code quantity}
   *                                  is negative,
   *                                  {@code unit} is null or empty, {@code pricePerUnit}
   *                                  is negative,
   *                                  or {@code expiryDate} is null
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
    this.name = name.toLowerCase();
    this.quantity = quantity;
    this.unit = unit;
    this.pricePerUnit = pricePerUnit;
    this.expiryDate = expiryDate;
  }

  /**
   * Updates the quantity of the grocery item.
   *
   * <p>For example, if additional units are added separately, this method updates
   * the total quantity.</p>
   *
   * @param quantity the new quantity to set
   * @throws IllegalArgumentException if {@code quantity} is negative
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
   * @return a formatted string containing the grocery's name, quantity, unit, price per unit,
   *         and expiry date
   */
  @Override
  public String toString() {
    return String.format(
        "%s: %.2f %s, NOK %.2f/unit, Expiry: %s", name, quantity, unit, pricePerUnit, expiryDate);
  }

}
