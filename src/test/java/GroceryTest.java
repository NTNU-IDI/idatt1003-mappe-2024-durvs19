import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows; //assertThrows is used to verify that the expected exception is thrown
//If you don't import these above methods, you would need to fully qualify them every time you use them.

import edu.ntnu.iir.bidata.Grocery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;

//doing unit testing for the grocery class -- use junit
public class GroceryTest {

  @Test
  public void testGrocery()
      throws ParseException { //void to not return any value only to verify behaviour
    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
    Date bestBefore = date.parse("01/12/2024"); // IDK IF ITS CORRECT

    Grocery OrangeJuice = new Grocery("Orange Juice", 1.0, "litre", bestBefore, 32.0);

    //assertions
    assertEquals("Orange Juice", OrangeJuice.getName());
    assertEquals(1.0, OrangeJuice.getQuantity(), 0.001);
    assertEquals("litre", OrangeJuice.getUnit());
    assertEquals(bestBefore, OrangeJuice.getBestBefore());
    assertEquals(32.0, OrangeJuice.getPriceperUnit(), 0.001);
  }

  @Test
  public void testInvalidGroceryUnit() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
      Date bestBefore = date.parse("01-12-2024");

      Grocery OrangeJuice = new Grocery("Orange Juice", 1.0, "", bestBefore, 32.0);
    });

    assertEquals("Unit cannot be null or empty", exception.getMessage());
  }
}
