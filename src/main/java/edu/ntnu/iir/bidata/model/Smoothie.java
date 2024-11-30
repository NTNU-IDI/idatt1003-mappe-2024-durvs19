package edu.ntnu.iir.bidata.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * Represents a smoothie made from various grocery ingredients.
 */
@Getter
public class Smoothie {
  /** Name of the smoothie. */
  private final String name;
  /** Description of the smoothie. */
  private final String description;
  /** Quantity of the smoothie (e.g., in liters). */
  private double quantity;
  /** Unit of measurement for the quantity (e.g., "liters"). */
  private final String unit;
  /** Price per unit of the smoothie (in NOK). */
  private final double pricePerUnit;
  /** Expiry date of the smoothie. */
  private final LocalDate expiryDate;
  /** List of grocery ingredients used in the smoothie. */
  private final List<Grocery> ingredients;

  /**
   * Constructs a new Smoothie.
   *
   * @param name the name of the smoothie
   * @param quantity the quantity of the smoothie
   * @param unit the unit of the smoothie
   * @param pricePerUnit the price per unit of the smoothie
   * @param expiryDate the expiry date of the smoothie
   * @throws IllegalArgumentException if any of the parameters are invalid
   */
  public Smoothie(String name, double quantity, String unit, double pricePerUnit, LocalDate expiryDate) {
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
    this.description = "";
    this.quantity = quantity;
    this.unit = unit;
    this.pricePerUnit = pricePerUnit;
    this.expiryDate = expiryDate;
    this.ingredients = new ArrayList<>();
  }


  /**
   * Adds an ingredient to the smoothie.
   *
   * @param grocery the grocery item to add as an ingredient
   * @throws IllegalArgumentException if the grocery is null
   */
  public void addIngredient(Grocery grocery) {
    if (grocery == null) {
      throw new IllegalArgumentException("Ingredient cannot be null");
    }
    ingredients.add(grocery);
  }

  /**
   * Sets the quantity of the smoothie.
   *
   * @param quantity the new quantity to set
   * @throws IllegalArgumentException if the quantity is negative
   */
  public void setQuantity(double quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }
    this.quantity = quantity;
  }

  /**
   * Calculates the total price of the smoothie.
   *
   * @return the total price of the smoothie
   */
  public double calculateTotalPrice() {
    return ingredients.stream()
        .mapToDouble(ingredient -> ingredient.getQuantity() * ingredient.getPricePerUnit())
        .sum();
  }
  /**
   * Converts the smoothie ingredients to a map for recipe storage.
   *
   * @return a map of ingredient names to quantities
   */
  public Map<String, Double> getIngredientsMap() {
    return ingredients.stream()
        .collect(Collectors.groupingBy(
            Grocery::getName,
            Collectors.summingDouble(Grocery::getQuantity)
        ));
  }

  /**
   * Returns a string representation of the smoothie.
   *
   * @return a string representation of the smoothie
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Smoothie: ").append(name).append("\n");
    sb.append("Quantity: ").append(quantity).append(" ").append(unit).append("\n");
    sb.append("Price per unit: NOK ").append(pricePerUnit).append("\n");
    sb.append("Expiry date: ").append(expiryDate).append("\n");
    sb.append("Ingredients:\n");
    for (Grocery ingredient : ingredients) {
      sb.append(ingredient.toString()).append("\n");
    }
    sb.append("Total Price: NOK ").append(String.format("%.2f", calculateTotalPrice()));
    return sb.toString();
  }

}
