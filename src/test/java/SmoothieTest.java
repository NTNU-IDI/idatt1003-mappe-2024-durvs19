import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.model.Smoothie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

public class SmoothieTest {

  @Test
  void testConstructorValidInputs() {
    Smoothie smoothie = new Smoothie("Mango dream", 1.0, "liter", 25.0, LocalDate.of(2023, 12, 31));
    Assertions.assertEquals("Mango dream", smoothie.getName());
    Assertions.assertEquals(1.0, smoothie.getQuantity());
    Assertions.assertEquals("liter", smoothie.getUnit());
    Assertions.assertEquals(25.0, smoothie.getPricePerUnit());
    Assertions.assertEquals(LocalDate.of(2023, 12, 31), smoothie.getExpiryDate());
  }

  @Test
  void testConstructorInvalidName() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Smoothie("", 1.0, "liter", 25.0, LocalDate.of(2023, 12, 31)));
  }

  @Test
  void testConstructorNegativeQuantity() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Smoothie("Berry Blast", -1.0, "liter", 20.0, LocalDate.of(2023, 12, 31)));
  }

  @Test
  void testConstructorInvalidUnit() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Smoothie("Berry Blast", 1.0, "", 20.0, LocalDate.of(2023, 12, 31)));
  }

  @Test
  void testConstructorNegativePricePerUnit() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Smoothie("Berry Blast", 1.0, "liter", -20.0, LocalDate.of(2023, 12, 31)));
  }

  @Test
  void testConstructorNullExpiryDate() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Smoothie("Berry Blast", 1.0, "liter", 20.0, null));
  }

  @Test
  void testAddIngredientValid() {
    Smoothie smoothie = new Smoothie("Berry Delight", 1.0, "liter", 20.0, LocalDate.of(2023, 12, 31));
    Grocery grocery = new Grocery("Strawberries", 0.5, "kg", 10.0, LocalDate.of(2023, 12, 25));

    smoothie.addIngredient(grocery);

    Assertions.assertTrue(smoothie.getIngredients().contains(grocery));
    Assertions.assertEquals(1, smoothie.getIngredients().size());
  }

  @Test
  void testAddIngredientNull() {
    Smoothie smoothie = new Smoothie("Berry Delight", 1.0, "liter", 20.0, LocalDate.of(2023, 12, 31));
    Assertions.assertThrows(IllegalArgumentException.class, () -> smoothie.addIngredient(null));
  }

  @Test
  void testSetQuantityValid() {
    Smoothie smoothie = new Smoothie("Berry Delight", 1.0, "liter", 20.0, LocalDate.of(2023, 12, 31));
    smoothie.setQuantity(2.0);
    Assertions.assertEquals(2.0, smoothie.getQuantity());
  }

  @Test
  void testSetQuantityNegative() {
    Smoothie smoothie = new Smoothie("Berry Delight", 1.0, "liter", 20.0, LocalDate.of(2023, 12, 31));
    Assertions.assertThrows(IllegalArgumentException.class, () -> smoothie.setQuantity(-2.0));
  }

  @Test
  void testCalculateTotalPrice() {
    Smoothie smoothie = new Smoothie("Berry Mix", 1.5, "liter", 30.0, LocalDate.of(2023, 12, 31));
    Grocery grocery1 = new Grocery("Strawberries", 0.5, "kg", 10.0, LocalDate.of(2023, 12, 25));
    Grocery grocery2 = new Grocery("Blueberries", 0.3, "kg", 15.0, LocalDate.of(2023, 12, 25));

    smoothie.addIngredient(grocery1);
    smoothie.addIngredient(grocery2);

    double expectedTotalPrice = (0.5 * 10.0) + (0.3 * 15.0); // 5.0 + 4.5 = 9.5
    Assertions.assertEquals(expectedTotalPrice, smoothie.calculateTotalPrice());
  }

  @Test
  void testToString() {
    Smoothie smoothie = new Smoothie("Green Energy", 2.0, "liter", 35.0, LocalDate.of(2023, 12, 31));
    Grocery grocery1 = new Grocery("Spinach", 0.2, "kg", 12.0, LocalDate.of(2023, 12, 20));
    Grocery grocery2 = new Grocery("Avocado", 0.3, "kg", 25.0, LocalDate.of(2023, 12, 20));

    smoothie.addIngredient(grocery1);
    smoothie.addIngredient(grocery2);

    // Match the exact expected string to the toString() method output
    String expectedToString = """
        Smoothie: Green Energy
        Quantity: 2.0 liter
        Price per unit: NOK 35.0
        Expiry date: 2023-12-31
        Ingredients:
        Spinach: 0.20 kg, NOK 12.00/unit, Expiry: 2023-12-20
        Avocado: 0.30 kg, NOK 25.00/unit, Expiry: 2023-12-20
        Total Price: NOK 11.10
        """;

    System.out.println("Actual toString() output:");
    System.out.println(smoothie.toString());

    // Compare the outputs
    Assertions.assertEquals(expectedToString.trim(), smoothie.toString().trim());
  }

  @Test
  void testGetIngredientsMap() {
    Smoothie smoothie = new Smoothie("Fruity", 1.0, "liter", 30.0, LocalDate.of(2023, 12, 31));
    Grocery grocery1 = new Grocery("Banana", 1.0, "kg", 10.0, LocalDate.of(2023, 12, 20));
    Grocery grocery2 = new Grocery("Banana", 0.5, "kg", 10.0, LocalDate.of(2023, 12, 20));
    Grocery grocery3 = new Grocery("Mango", 1.0, "kg", 20.0, LocalDate.of(2023, 12, 20));

    smoothie.addIngredient(grocery1);
    smoothie.addIngredient(grocery2);
    smoothie.addIngredient(grocery3);

    Map<String, Double> ingredientsMap = smoothie.getIngredientsMap();
    Assertions.assertEquals(1.5, ingredientsMap.get("Banana"));
    Assertions.assertEquals(1.0, ingredientsMap.get("Mango"));
    Assertions.assertEquals(2, ingredientsMap.size());
  }
}
