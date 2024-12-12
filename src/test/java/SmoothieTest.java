import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.model.Smoothie;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Smoothie} class.
 * <p>These tests validate the behavior of the {@code Smoothie} class,
 * including its constructor, methods for adding ingredients, calculating total price,
 * and generating a string representation.</p>
 */
public class SmoothieTest {

  /**
   * Tests the constructor with valid inputs.
   * <p>Verifies that the fields are correctly initialized and that the ingredients list is empty.</p>
   */
  @Test
  void testConstructorValidInputs() {
    Smoothie smoothie = new Smoothie("Mango Dream", "A delightful mango smoothie", LocalDate.of(2024, 12, 31));
    Assertions.assertEquals("Mango Dream", smoothie.getName());
    Assertions.assertEquals("A delightful mango smoothie", smoothie.getDescription());
    Assertions.assertEquals(LocalDate.of(2024, 12, 31), smoothie.getExpiryDate());
    Assertions.assertTrue(smoothie.getIngredients().isEmpty(), "Ingredients list should be empty upon initialization.");
  }

  /**
   * Tests the constructor with an invalid (empty) name.
   * <p>Expects an {@link IllegalArgumentException} to be thrown.</p>
   */
  @Test
  void testConstructorInvalidName() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Smoothie("", "Description", LocalDate.of(2024, 12, 31)));
  }

  /**
   * Tests the constructor with a null name.
   * <p>Expects an {@link IllegalArgumentException} to be thrown.</p>
   */
  @Test
  void testConstructorNullName() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Smoothie(null, "Description", LocalDate.of(2024, 12, 31)));
  }

  /**
   * Tests the constructor with a null expiry date.
   * <p>Expects an {@link IllegalArgumentException} to be thrown.</p>
   */
  @Test
  void testConstructorNullExpiryDate() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Smoothie("Berry Blast", "Delicious smoothie", null));
  }

  /**
   * Tests adding a valid ingredient to the smoothie.
   * <p>Verifies that the ingredient is added to the ingredients list.</p>
   */

  @Test
  void testAddIngredientValid() {
    Smoothie smoothie = new Smoothie("Berry Delight", "A delightful berry smoothie", LocalDate.of(2024, 12, 31));
    Grocery grocery = new Grocery("Strawberries", 0.5, "kg", 10.0, LocalDate.of(2024, 12, 25));

    smoothie.addIngredient(grocery);

    Assertions.assertTrue(smoothie.getIngredients().contains(grocery), "Ingredients should contain the added grocery.");
    Assertions.assertEquals(1, smoothie.getIngredients().size(), "Ingredients list should have exactly one item.");
  }

  /**
   * Tests adding a null ingredient to the smoothie.
   * <p>Expects an {@link IllegalArgumentException} to be thrown.</p>
   */
  @Test
  void testAddIngredientNull() {
    Smoothie smoothie = new Smoothie("Berry Delight", "A delightful berry smoothie", LocalDate.of(2024, 12, 31));
    Assertions.assertThrows(IllegalArgumentException.class, () -> smoothie.addIngredient(null));
  }

  /**
   * Tests calculating the total price when the smoothie has no ingredients.
   * <p>Verifies that the total price is 0.0.</p>
   */
  @Test
  void testCalculateTotalPriceEmptyIngredients() {
    Smoothie smoothie = new Smoothie("Empty Smoothie", "No ingredients", LocalDate.of(2024, 12, 31));
    double expectedTotalPrice = 0.0;
    Assertions.assertEquals(expectedTotalPrice, smoothie.calculateTotalPrice(),
        "Total price should be 0.0 when there are no ingredients.");
  }

  /**
   * Tests calculating the total price with ingredients.
   * <p>Verifies that the total price is correctly computed as the sum of all ingredient prices.</p>
   */
  @Test
  void testCalculateTotalPrice() {
    Smoothie smoothie = new Smoothie("Berry Mix", "A mix of berries", LocalDate.of(2024, 12, 31));
    Grocery grocery1 = new Grocery("Strawberries", 0.5, "kg", 10.0, LocalDate.of(2024, 12, 25));
    Grocery grocery2 = new Grocery("Blueberries", 0.3, "kg", 15.0, LocalDate.of(2024, 12, 25));

    smoothie.addIngredient(grocery1);
    smoothie.addIngredient(grocery2);

    double expectedTotalPrice = (0.5 * 10.0) + (0.3 * 15.0); // 5.0 + 4.5 = 9.5
    Assertions.assertEquals(expectedTotalPrice, smoothie.calculateTotalPrice(),
        "Total price should correctly sum the prices of all ingredients.");
  }

  /**
   * Tests the {@link Smoothie#toString()} method.
   * <p>Verifies that the string representation includes the smoothie name, expiry date,
   * ingredient details, and total price.</p>
   */
  @Test
  void testToString() {
    Smoothie smoothie = new Smoothie("Green Energy", "A green smoothie for energy", LocalDate.of(2024, 12, 31));
    Grocery grocery1 = new Grocery("Spinach", 0.2, "kg", 12.0, LocalDate.of(2024, 12, 20));
    Grocery grocery2 = new Grocery("Avocado", 0.3, "kg", 25.0, LocalDate.of(2024, 12, 20));

    smoothie.addIngredient(grocery1);
    smoothie.addIngredient(grocery2);

    // Expected string based on the updated toString method
    String expectedToString = """
                Smoothie: Green Energy
                Expiry date: 2024-12-31
                Ingredients:
                Spinach: 0.20 kg, NOK 12.00/unit, Expiry: 2024-12-20
                Avocado: 0.30 kg, NOK 25.00/unit, Expiry: 2024-12-20
                Total Price: NOK 11.00
                """;

    // Note: calculateTotalPrice = (0.2 * 12.0) + (0.3 * 25.0) = 2.4 + 7.5 = 9.9
    // But expectedToString shows 11.00, so let's correct that
    double expectedPrice = (0.2 * 12.0) + (0.3 * 25.0); // 2.4 + 7.5 = 9.9

    String adjustedExpectedToString = """
                Smoothie: Green Energy
                Expiry date: 2024-12-31
                Ingredients:
                spinach: 0.20 kg, NOK 12.00/unit, Expiry: 2024-12-20
                avocado: 0.30 kg, NOK 25.00/unit, Expiry: 2024-12-20
                Total Price: NOK 9.90
                """;

    Assertions.assertEquals(adjustedExpectedToString.trim(), smoothie.toString().trim(),
        "toString should return the correct string representation of the smoothie.");
  }

  /**
   * Tests the {@link Smoothie#getIngredientsMap()} method.
   * <p>Verifies that the ingredients map correctly aggregates quantities by ingredient name.</p>
   */
  @Test
  void testGetIngredientsMap() {
    Smoothie smoothie = new Smoothie("fruity", "A fruity blend", LocalDate.of(2024, 12, 31));
    Grocery grocery1 = new Grocery("banana", 1.0, "kg", 10.0, LocalDate.of(2024, 12, 20));
    Grocery grocery2 = new Grocery("banana", 0.5, "kg", 10.0, LocalDate.of(2024, 12, 20));
    Grocery grocery3 = new Grocery("mango", 1.0, "kg", 20.0, LocalDate.of(2024, 12, 20));

    smoothie.addIngredient(grocery1);
    smoothie.addIngredient(grocery2);
    smoothie.addIngredient(grocery3);

    Map<String, Double> ingredientsMap = smoothie.getIngredientsMap();
    Assertions.assertEquals(1.5, ingredientsMap.get("banana"), 0.0001, "Banana quantity should be aggregated correctly.");
    Assertions.assertEquals(1.0, ingredientsMap.get("mango"), 0.0001, "Mango quantity should be correctly represented.");
    Assertions.assertEquals(2, ingredientsMap.size(), "Ingredients map should contain exactly two entries.");
  }
}
