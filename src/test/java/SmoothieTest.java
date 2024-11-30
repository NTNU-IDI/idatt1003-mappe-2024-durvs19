import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.model.Smoothie;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SmoothieTest {

  @Test
  void testConstructorValidInputs() {
    Smoothie smoothie = new Smoothie("Mango Dream", "A delightful mango smoothie", LocalDate.of(2024, 12, 31));
    Assertions.assertEquals("Mango Dream", smoothie.getName());
    Assertions.assertEquals("A delightful mango smoothie", smoothie.getDescription());
    Assertions.assertEquals(LocalDate.of(2024, 12, 31), smoothie.getExpiryDate());
    Assertions.assertTrue(smoothie.getIngredients().isEmpty(), "Ingredients list should be empty upon initialization.");
  }

  @Test
  void testConstructorInvalidName() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Smoothie("", "Description", LocalDate.of(2024, 12, 31)));
  }

  @Test
  void testConstructorNullName() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Smoothie(null, "Description", LocalDate.of(2024, 12, 31)));
  }

  @Test
  void testConstructorNullExpiryDate() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Smoothie("Berry Blast", "Delicious smoothie", null));
  }

  @Test
  void testAddIngredientValid() {
    Smoothie smoothie = new Smoothie("Berry Delight", "A delightful berry smoothie", LocalDate.of(2024, 12, 31));
    Grocery grocery = new Grocery("Strawberries", 0.5, "kg", 10.0, LocalDate.of(2024, 12, 25));

    smoothie.addIngredient(grocery);

    Assertions.assertTrue(smoothie.getIngredients().contains(grocery), "Ingredients should contain the added grocery.");
    Assertions.assertEquals(1, smoothie.getIngredients().size(), "Ingredients list should have exactly one item.");
  }

  @Test
  void testAddIngredientNull() {
    Smoothie smoothie = new Smoothie("Berry Delight", "A delightful berry smoothie", LocalDate.of(2024, 12, 31));
    Assertions.assertThrows(IllegalArgumentException.class, () -> smoothie.addIngredient(null));
  }

  @Test
  void testCalculateTotalPriceEmptyIngredients() {
    Smoothie smoothie = new Smoothie("Empty Smoothie", "No ingredients", LocalDate.of(2024, 12, 31));
    double expectedTotalPrice = 0.0;
    Assertions.assertEquals(expectedTotalPrice, smoothie.calculateTotalPrice(),
        "Total price should be 0.0 when there are no ingredients.");
  }

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
                Spinach: 0.20 kg, NOK 12.00/unit, Expiry: 2024-12-20
                Avocado: 0.30 kg, NOK 25.00/unit, Expiry: 2024-12-20
                Total Price: NOK 9.90
                """;

    Assertions.assertEquals(adjustedExpectedToString.trim(), smoothie.toString().trim(),
        "toString should return the correct string representation of the smoothie.");
  }

  @Test
  void testGetIngredientsMap() {
    Smoothie smoothie = new Smoothie("Fruity", "A fruity blend", LocalDate.of(2024, 12, 31));
    Grocery grocery1 = new Grocery("Banana", 1.0, "kg", 10.0, LocalDate.of(2024, 12, 20));
    Grocery grocery2 = new Grocery("Banana", 0.5, "kg", 10.0, LocalDate.of(2024, 12, 20));
    Grocery grocery3 = new Grocery("Mango", 1.0, "kg", 20.0, LocalDate.of(2024, 12, 20));

    smoothie.addIngredient(grocery1);
    smoothie.addIngredient(grocery2);
    smoothie.addIngredient(grocery3);

    Map<String, Double> ingredientsMap = smoothie.getIngredientsMap();
    Assertions.assertEquals(1.5, ingredientsMap.get("Banana"), 0.0001, "Banana quantity should be aggregated correctly.");
    Assertions.assertEquals(1.0, ingredientsMap.get("Mango"), 0.0001, "Mango quantity should be correctly represented.");
    Assertions.assertEquals(2, ingredientsMap.size(), "Ingredients map should contain exactly two entries.");
  }
}
