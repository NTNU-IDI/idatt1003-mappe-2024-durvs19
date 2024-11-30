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
  private String description;
  /** Expiry date of the smoothie. */
  private final LocalDate expiryDate;
  /** List of grocery ingredients used in the smoothie. */
  private final List<Grocery> ingredients;

  /**
   * Constructs a new Smoothie.
   *
   * @param name the name of the smoothie
   * @param description the description of the smoothie
   * @param expiryDate the expiry date of the smoothie
   * @throws IllegalArgumentException if any of the parameters are invalid
   */
  public Smoothie(String name, String description, LocalDate expiryDate) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
    if (expiryDate == null) {
      throw new IllegalArgumentException("Expiry date cannot be null");
    }
    this.name = name;
    this.description = description != null ? description : "";
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
   * Calculates the total price of the smoothie based on its ingredients.
   *
   * @return the total price of the smoothie
   */
  public double calculateTotalPrice() {
    return ingredients.stream()
        .mapToDouble(ingredient -> ingredient.getQuantity() * ingredient.getPricePerUnit())
        .sum();
  }

  public Map<String, Double> getIngredientsMap() {
    return ingredients.stream()
        .collect(Collectors.toMap(
            Grocery::getName,
            Grocery::getQuantity,
            Double::sum
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
    sb.append("Expiry date: ").append(expiryDate).append("\n");
    sb.append("Ingredients:\n");
    for (Grocery ingredient : ingredients) {
      sb.append(ingredient.toString()).append("\n");
    }
    sb.append("Total Price: NOK ").append(String.format("%.2f", calculateTotalPrice()));
    return sb.toString();
  }
}
