package edu.ntnu.iir.bidata.utils;

import edu.ntnu.iir.bidata.model.Grocery;

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

}
