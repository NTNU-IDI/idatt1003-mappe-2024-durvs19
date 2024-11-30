package edu.ntnu.iir.bidata.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a smoothie made from various grocery ingredients.
 */
public class Smoothie {
  private String name;
  private String description;
  private double quantity;
  private String unit;
  private double pricePerUnit;
  private LocalDate expiryDate;
  private List<Grocery> ingredients;

  /**
   * Constructs a new Smoothie.
   *
   * @param name the name of the smoothie
   * @param quantity the quantity of the smoothie
   * @param unit the unit of the smoothie
   * @param pricePerUnit the price per unit of the smoothie
   * @param expiryDate the expiry date of the smoothie
   */
  public Smoothie(String name, double quantity, String unit, double pricePerUnit, LocalDate expiryDate) {
    this.name = name;
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
   */
  public void addIngredient(Grocery grocery) {
    ingredients.add(grocery);
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
}
