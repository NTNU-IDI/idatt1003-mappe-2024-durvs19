package edu.ntnu.iir.bidata.services;

import edu.ntnu.iir.bidata.model.Grocery;
import java.time.LocalDate;

/**
 * Provides services related to grocery items.
 */
public class GroceryService {

  // Utility

  /**
   * Checks if the given grocery item is expired.
   *
   * <p>A grocery item is considered expired if its expiry date is before the current date.</p>
   *
   * <p><strong>Example:</strong></p>
   * <pre><code>
   * Grocery grocery = new Grocery();
   * grocery.setExpiryDate(LocalDate.of(2021, 10, 1));
   * boolean expired = GroceryService.isExpired(grocery);
   * </code></pre>
   *
   * @param grocery the grocery item to check
   * @return {@code true} if the grocery item is expired, {@code false} otherwise
   */
  public static boolean isExpired(Grocery grocery) {
    return LocalDate.now().isAfter(grocery.getExpiryDate());
  }

  /**
   * Calculates the total value of the given grocery item.
   *
   * <p>The total value is calculated by multiplying the quantity of the grocery item
   * by its price per unit.
   * </p>
   *
   * <p><strong>Example:</strong></p>
   * <pre><code>
   * Grocery grocery = new Grocery();
   * grocery.setQuantity(10);
   * grocery.setPricePerUnit(2.5);
   * double value = new GroceryService().calculateValue(grocery);
   * </code></pre>
   *
   * @param grocery the grocery item to calculate the value for
   * @return the total value of the grocery item
   */
  public double calculateValue(Grocery grocery) {
    return grocery.getQuantity() * grocery.getPricePerUnit();
  }

  /**
   * Checks if two grocery items can be clubbed together.
   *
   * @param existingGrocery   the existing grocery item
   * @param newlyAddedGrocery the newly added grocery item
   * @return {@code true} if the two grocery items can be clubbed together, {@code false} otherwise
   *         {@code @example} Grocery grocery1 = new Grocery(); grocery1.setName("Apple");
   *        grocery1.setUnit("kg");
   *        grocery1.setPricePerUnit(3.0); grocery1.setExpiryDate(LocalDate.of(2023, 10, 1));
   *        Grocery grocery2 = new Grocery(); grocery2.setName("Apple"); grocery2.setUnit("kg");
   *        grocery2.setPricePerUnit(3.0); grocery2.setExpiryDate(LocalDate.of(2023, 10, 1));
   *        boolean clubbable = groceryService.areGroceriesClubbable(grocery1, grocery2);
   */
  public boolean areGroceriesClubbable(Grocery existingGrocery, Grocery newlyAddedGrocery) {
    return existingGrocery.getName().equals(newlyAddedGrocery.getName())
        && existingGrocery.getUnit().equals(newlyAddedGrocery.getUnit())
        && existingGrocery.getPricePerUnit() == newlyAddedGrocery.getPricePerUnit()
        && existingGrocery.getExpiryDate().isEqual(newlyAddedGrocery.getExpiryDate());
  }
}
