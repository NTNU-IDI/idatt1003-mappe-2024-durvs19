import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.assertEquals;

import edu.ntnu.iir.bidata.model.Grocery;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Grocery} class.
 * <p>These tests validate the correctness of the {@code Grocery} class's constructor,
 * methods, and behaviors under various conditions.</p>
 */
class GroceryTest {

  /**
   * Tests the {@link Grocery} constructor with valid inputs.
   * <p>Verifies that the fields are correctly initialized.</p>
   */
  @Test
  void testConstructorValidInputs() {
    //arrange
    Grocery grocery = new Grocery("milk", 2.0, "liters", 15.0, LocalDate.of(2023, 12, 31));
    //act
    // no explicit act step because constructor does the work

    //assert
    Assertions.assertEquals("milk", grocery.getName());
    Assertions.assertEquals(2.0, grocery.getQuantity());
    Assertions.assertEquals("liters", grocery.getUnit());
    Assertions.assertEquals(15.0, grocery.getPricePerUnit());
    Assertions.assertEquals(LocalDate.of(2023, 12, 31), grocery.getExpiryDate());
  }

  /**
   * Tests the {@link Grocery} constructor with an invalid (empty) name.
   * <p>Expects an {@link IllegalArgumentException} to be thrown.</p>
   */
  @Test
  void testConstructorInvalidName() {
    assertThrows(IllegalArgumentException.class,
        () -> new Grocery("", 2.0, "liters", 15.0, LocalDate.of(2023, 12, 31)));
  }

  /**
   * Tests the {@link Grocery} constructor with a negative quantity.
   * <p>Expects an {@link IllegalArgumentException} to be thrown.</p>
   */
  @Test
  void testConstructorNegativeQuantity() {
    assertThrows(IllegalArgumentException.class,
        () -> new Grocery("Milk", -1.0, "liters", 15.0, LocalDate.of(2023, 12, 31)));
  }

  /**
   * Tests the {@link Grocery} constructor with a negative price per unit.
   * <p>Expects an {@link IllegalArgumentException} to be thrown.</p>
   */
  @Test
  void testConstructorNegativePricePerUnit() {
    assertThrows(IllegalArgumentException.class,
        () -> new Grocery("Milk", 2.0, "liters", -15.0, LocalDate.of(2023, 12, 31)));
  }

  /**
   * Tests setting a valid quantity using {@link Grocery#setQuantity(double)}.
   * <p>Verifies that the quantity is updated correctly.</p>
   */
  @Test
  void testSetQuantityValid() {
    Grocery grocery = new Grocery("Milk", 2, "liters", 15.0, LocalDate.of(2023, 12, 31));
    grocery.setQuantity(3.0);
    Assertions.assertEquals(3.0, grocery.getQuantity());
  }

  /**
   * Tests setting a negative quantity using {@link Grocery#setQuantity(double)}.
   * <p>Expects an {@link IllegalArgumentException} to be thrown.</p>
   */
  @Test
  void testSetQuantityNegative() {
    Grocery grocery = new Grocery("Milk", 2.0, "liters", 15.0, LocalDate.of(2023, 12, 31));
    assertThrows(IllegalArgumentException.class, () -> grocery.setQuantity(-3.0));
  }

  /**
   * Tests the {@link Grocery#toString()} method.
   * <p>Verifies that the string representation of the {@code Grocery} object matches
   * the expected format.</p>
   */
  @Test
  void testToString() {
    Grocery grocery = new Grocery("milk", 2.0, "liters", 15.0, LocalDate.of(2023, 12, 31));
    String expected = "milk: 2.00 liters, NOK 15.00/unit, Expiry: 2023-12-31";
    assertEquals(expected, grocery.toString());
  }
}
