package edu.ntnu.iir.bidata.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a recipe with its details like name, description, procedure, ingredients, and the
 * number it serves.
 */
@Data
@AllArgsConstructor
public class Recipe {

  /** Name of the recipe. */
  private final String name;

  /** Brief description of the recipe. */
  private final String description;

  /** Detailed procedure to prepare the recipe. */
  private String procedure;

  /**
   * The ingredients required for the recipe.
   *
   * <p>Maps ingredient names to their required quantities.</p>
   * Example: {@code {"Flour": 500.0, "Sugar": 200.0}}
   */
  private Map<String, Double> ingredients; // Ingredient name -> Required quantity

  /** The number of people the recipe serves. */
  private int serves;

  /**
   * Returns a string representation of the recipe.
   *
   * @return the name and description of the recipe.
   */
  @Override
  public String toString() {
    return name + ": " + description + " , ingredients: " + ingredients + " , serves: " + serves;
  }

}