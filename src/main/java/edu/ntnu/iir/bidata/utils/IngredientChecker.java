package edu.ntnu.iir.bidata.utils;

import edu.ntnu.iir.bidata.model.Grocery;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IngredientChecker {

  /**
   * Checks if the given grocery item is expired.
   *
   * @param grocery the grocery item to check
   * @return true if the grocery item is expired, false otherwise
   */
  public static boolean isExpired(Grocery grocery) {
    return grocery.getExpiryDate().isBefore(java.time.LocalDate.now());
  }

  /**
   * Checks if the required ingredients for a recipe are available in sufficient quantities.
   *
   * @param requiredIngredients a map of ingredient names to required quantities
   * @param availableGroceries  a list of groceries available
   * @return true if all required ingredients are available in sufficient quantities, false otherwise
   */
  public static boolean areIngredientsAvailable(
      Map<String, Double> requiredIngredients, List<Grocery> availableGroceries) {
    // Aggregate available quantities of groceries by name
    Map<String, Double> availableQuantities = availableGroceries.stream()
        .collect(Collectors.toMap(
            Grocery::getName,
            Grocery::getQuantity,
            Double::sum
        ));
    // Check if all required ingredients are available
    return requiredIngredients.entrySet().stream()
        .allMatch(entry -> availableQuantities.getOrDefault(entry.getKey(),
            0.0) >= entry.getValue());
  }

}
