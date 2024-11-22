import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import edu.ntnu.iir.bidata.Grocery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class GroceryTest {

  @Test
  void testConstructorValidInputs() {
    Grocery grocery = new Grocery("Milk", 2.0, "liters", 15.0, LocalDate.of(2023, 12, 31));
   //replaced assertEquals with Assertions.assertEquals bcs of junit5
    Assertions.assertEquals("Milk", grocery.getName());
    Assertions.assertEquals(2.0, grocery.getQuantity());
    Assertions.assertEquals("liters", grocery.getUnit());
    Assertions.assertEquals(15.0, grocery.getPricePerUnit());
    Assertions.assertEquals(LocalDate.of(2023, 12, 31), grocery.getExpiryDate());
  }

  @Test
  void testConstructorInvalidName() {
    assertThrows(IllegalArgumentException.class,
        () -> new Grocery("", 2.0, "liters", 15.0, LocalDate.of(2023, 12, 31)));
  }

  @Test
  void testConstructorNegativeQuantity() {
    assertThrows(IllegalArgumentException.class,
        () -> new Grocery("Milk", -1.0, "liters", 15.0, LocalDate.of(2023, 12, 31)));
  }

  @Test
  void testConstructorNegativePricePerUnit() {
    assertThrows(IllegalArgumentException.class,
        () -> new Grocery("Milk", 2.0, "liters", -15.0, LocalDate.of(2023, 12, 31)));
  }

  @Test
  void testSetQuantityValid() {
    Grocery grocery = new Grocery("Milk", 2, "liters", 15.0, LocalDate.of(2023, 12, 31));
    grocery.setQuantity(3.0);
    Assertions.assertEquals(3.0, grocery.getQuantity());
  }

  @Test
  void testSetQuantityNegative() {
    Grocery grocery = new Grocery("Milk", 2.0, "liters", 15.0, LocalDate.of(2023, 12, 31));
    assertThrows(IllegalArgumentException.class, () -> grocery.setQuantity(-3.0));
  }

  @Test
  void testToString() {
    Grocery grocery = new Grocery("Milk", 2.0, "liters", 15.0, LocalDate.of(2023, 12, 31));
    String expected = "Milk: 2.00 liters, NOK 15.00/unit, Expiry: 2023-12-31";
    assertEquals(expected, grocery.toString());
  }
}
