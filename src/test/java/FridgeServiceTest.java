import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.services.FridgeService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FridgeServiceTest {

  @Test
  public void testAddGrocery_NewGrocery() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("Apple", 5.0, "kg", 2.0, LocalDate.now().plusDays(10));

    fridgeService.addGrocery(grocery);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals("Apple", groceries.get(0).getName());
    Assertions.assertEquals(5.0, groceries.get(0).getQuantity());
  }

  @Test
  public void testAddGrocery_ExistingGroceryWithSameExpiryDate() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery1 = new Grocery("Apple", 5.0, "kg", 2.0, LocalDate.now().plusDays(10));
    Grocery grocery2 = new Grocery("Apple", 3.0, "kg", 2.0, LocalDate.now().plusDays(10));

    fridgeService.addGrocery(grocery1);
    fridgeService.addGrocery(grocery2);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals("Apple", groceries.get(0).getName());
    Assertions.assertEquals(8.0, groceries.get(0).getQuantity());
  }

  @Test
  public void testAddGrocery_ExistingGroceryWithDifferentExpiryDate() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery1 = new Grocery("Apple", 5.0, "kg", 2.0, LocalDate.now().plusDays(10));
    Grocery grocery2 = new Grocery("Apple", 3.0, "kg", 2.0, LocalDate.now().plusDays(5));

    fridgeService.addGrocery(grocery1);
    fridgeService.addGrocery(grocery2);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    Assertions.assertEquals(2, groceries.size());
  }

  @Test
  public void testRemoveGrocery_PartialQuantity() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("Apple", 5.0, "kg", 2.0, LocalDate.now().plusDays(10));

    fridgeService.addGrocery(grocery);
    boolean result = fridgeService.removeGrocery("Apple", 3.0);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    Assertions.assertTrue(result);
    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals(2.0, groceries.get(0).getQuantity());
  }

  @Test
  public void testRemoveGrocery_CompleteQuantity() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("Apple", 5.0, "kg", 2.0, LocalDate.now().plusDays(10));

    fridgeService.addGrocery(grocery);
    boolean result = fridgeService.removeGrocery("Apple", 5.0);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    Assertions.assertTrue(result);
    Assertions.assertTrue(groceries.isEmpty());
  }

  @Test
  public void testRemoveGrocery_InsufficientQuantity() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("Apple", 3.0, "kg", 2.0, LocalDate.now().plusDays(10));

    fridgeService.addGrocery(grocery);
    boolean result = fridgeService.removeGrocery("Apple", 5.0);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    Assertions.assertFalse(result);
    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals(3.0, groceries.get(0).getQuantity());
  }

  @Test
  public void testRemoveGrocery_NonExistingGrocery() {
    FridgeService fridgeService = new FridgeService();

    boolean result = fridgeService.removeGrocery("Banana", 2.0);

    Assertions.assertFalse(result);
  }

  @Test
  public void testGetExpiredGroceries() {
    FridgeService fridgeService = new FridgeService();
    Grocery expiredGrocery = new Grocery("Milk", 1.0, "liter", 1.5, LocalDate.now().minusDays(1));
    Grocery freshGrocery = new Grocery("Milk", 1.0, "liter", 1.5, LocalDate.now().plusDays(1));

    fridgeService.addGrocery(expiredGrocery);
    fridgeService.addGrocery(freshGrocery);

    List<Grocery> expiredGroceries = fridgeService.getExpiredGroceries();

    Assertions.assertEquals(1, expiredGroceries.size());
    Assertions.assertEquals("Milk", expiredGroceries.get(0).getName());
    Assertions.assertEquals(1.0, expiredGroceries.get(0).getQuantity());
  }

  @Test
  public void testCalculateTotalValue() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery1 = new Grocery("Rice", 2.0, "kg", 3.0, LocalDate.now().plusDays(30));
    Grocery grocery2 = new Grocery("Beans", 1.0, "kg", 4.0, LocalDate.now().plusDays(30));

    fridgeService.addGrocery(grocery1);
    fridgeService.addGrocery(grocery2);

    double totalValue = fridgeService.calculateTotalValue();

    Assertions.assertEquals(10.0, totalValue, 0.001);
  }

  @Test
  public void testCalculateTotalValueOfExpiredGroceries() {
    FridgeService fridgeService = new FridgeService();
    Grocery expiredGrocery = new Grocery("Milk", 1.0, "liter", 1.5, LocalDate.now().minusDays(1));
    Grocery freshGrocery = new Grocery("Bread", 1.0, "loaf", 2.5, LocalDate.now().plusDays(3));

    fridgeService.addGrocery(expiredGrocery);
    fridgeService.addGrocery(freshGrocery);

    double totalExpiredValue = fridgeService.calculateTotalValueOfExpiredGroceries();

    Assertions.assertEquals(1.5, totalExpiredValue, 0.001);
  }
}
