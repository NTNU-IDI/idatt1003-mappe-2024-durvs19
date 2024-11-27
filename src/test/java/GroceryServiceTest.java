import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.services.GroceryService;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroceryServiceTest {

  @Test
  public void testIsExpired_GroceryExpired_ShouldReturnTrue() {
    Grocery expiredGrocery = new Grocery("Milk", 1.0, "liter", 1.5, LocalDate.now().minusDays(1));
    GroceryService groceryService = new GroceryService();

    Assertions.assertTrue(groceryService.isExpired(expiredGrocery));
  }

  @Test
  public void testIsExpired_GroceryNotExpired_ShouldReturnFalse() {
    Grocery freshGrocery = new Grocery("Milk", 1.0, "liter", 1.5, LocalDate.now().plusDays(1));
    GroceryService groceryService = new GroceryService();

    Assertions.assertFalse(groceryService.isExpired(freshGrocery));
  }

  @Test
  public void testCalculateValue() {
    Grocery grocery = new Grocery("Rice", 2.0, "kg", 3.0, LocalDate.of(2023, 12, 31));
    GroceryService groceryService = new GroceryService();

    Assertions.assertEquals(6.0, groceryService.calculateValue(grocery), 0.001);
  }

  @Test
  public void testAreGroceriesClubbable_SameGroceries_ShouldReturnTrue() {
    Grocery existingGrocery = new Grocery("Apple", 1.0, "kg", 2.5, LocalDate.of(2023, 12, 31));
    Grocery newlyAddedGrocery = new Grocery("Apple", 2.0, "kg", 2.5, LocalDate.of(2023, 12, 31));

    GroceryService groceryService = new GroceryService();

    Assertions.assertTrue(groceryService.areGroceriesClubbable(existingGrocery, newlyAddedGrocery));
  }

  @Test
  public void testAreGroceriesClubbable_DifferentNames_ShouldReturnFalse() {
    Grocery existingGrocery = new Grocery("Apple", 1.0, "kg", 2.5, LocalDate.of(2023, 12, 31));
    Grocery newlyAddedGrocery = new Grocery("Banana", 2.0, "kg", 2.5, LocalDate.of(2023, 12, 31));

    GroceryService groceryService = new GroceryService();

    Assertions.assertFalse(
        groceryService.areGroceriesClubbable(existingGrocery, newlyAddedGrocery));
  }

  @Test
  public void testAreGroceriesClubbable_DifferentUnits_ShouldReturnFalse() {
    Grocery existingGrocery = new Grocery("Apple", 1.0, "kg", 2.5, LocalDate.of(2023, 12, 31));
    Grocery newlyAddedGrocery = new Grocery("Apple", 2.0, "lb", 2.5, LocalDate.of(2023, 12, 31));

    GroceryService groceryService = new GroceryService();

    Assertions.assertFalse(
        groceryService.areGroceriesClubbable(existingGrocery, newlyAddedGrocery));
  }

  @Test
  public void testAreGroceriesClubbable_DifferentPricePerUnit_ShouldReturnFalse() {
    Grocery existingGrocery = new Grocery("Apple", 1.0, "kg", 2.5, LocalDate.of(2023, 12, 31));
    Grocery newlyAddedGrocery = new Grocery("Apple", 2.0, "kg", 2.0, LocalDate.of(2023, 12, 31));

    GroceryService groceryService = new GroceryService();

    Assertions.assertFalse(
        groceryService.areGroceriesClubbable(existingGrocery, newlyAddedGrocery));
  }

  @Test
  public void testAreGroceriesClubbable_DifferentExpiryDates_ShouldReturnFalse() {
    Grocery existingGrocery = new Grocery("Apple", 1.0, "kg", 2.5, LocalDate.of(2023, 12, 31));
    Grocery newlyAddedGrocery = new Grocery("Apple", 2.0, "kg", 2.5, LocalDate.of(2024, 1, 1));

    GroceryService groceryService = new GroceryService();

    Assertions.assertFalse(
        groceryService.areGroceriesClubbable(existingGrocery, newlyAddedGrocery));
  }
}
