package edu.ntnu.iir.bidata.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

/**
 * Represents a storage or inventory, such as a refrigerator, for various food items.
 *
 * <p>Food items are organized in a map where the key is the name of the grocery item,
 * and the value is a list of {@code Grocery} objects grouped by their expiry dates.</p>
 */
@Getter
public class Fridge {
  /**
   * A map that holds the groceries.
   *
   * <p>The key is a string representing the type or name of the grocery item, and the value is
   * a list of {@link Grocery} objects corresponding to that type, grouped by their expiry dates.
   * </p>
   */
  private final Map<String, List<Grocery>> groceriesPerCategory = new HashMap<>();
}

