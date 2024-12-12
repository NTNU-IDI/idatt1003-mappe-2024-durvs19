import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import edu.ntnu.iir.bidata.model.Fridge;
import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.services.FridgeService;
import edu.ntnu.iir.bidata.services.GroceryService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link FridgeService} class.
 *
 * <p>This class tests various functionalities of the {@code FridgeService}, such as adding groceries,
 * removing groceries, and sorting or finding groceries.</p>
 */
public class FridgeServiceTest {

  /**
   * Tests adding a new grocery to the fridge.
   * <p>Verifies that the grocery is added successfully and the attributes are correctly stored.</p>
   */
  @Test
  public void testAddGrocery_NewGrocery() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("apple", 5, "kg", 2.0, LocalDate.now().plusDays(10));
    fridgeService.addGrocery(grocery);

    List<Grocery> groceries = fridgeService.getAllGroceries();
    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals("apple", groceries.get(0).getName().toLowerCase());
    Assertions.assertEquals(5, groceries.get(0).getQuantity());
  }

  /**
   * Tests adding an existing grocery to the fridge.
   * <p>Verifies that quantities are updated when the grocery already exists.</p>
   */
  @Test
  public void testAddGrocery_ExistingGrocery() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery1 = new Grocery("apple", 5, "kg", 2.0, LocalDate.now().plusDays(10));
    Grocery grocery2 = new Grocery("apple", 3, "kg", 2.0, LocalDate.now().plusDays(10));
    fridgeService.addGrocery(grocery1);
    fridgeService.addGrocery(grocery2);

    List<Grocery> groceries = fridgeService.getAllGroceries();
    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals("apple", groceries.get(0).getName().toLowerCase());
    Assertions.assertEquals(8, groceries.get(0).getQuantity());
  }

  /**
   * Tests adding a grocery with a different expiry date.
   * <p>Verifies that the groceries with different expiry dates are stored as separate entries.</p>
   */
  @Test
  public void testAddGrocery_DifferentExpiryDate() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery1 = new Grocery("apple", 5, "kg", 2.0, LocalDate.now().plusDays(10));
    Grocery grocery2 = new Grocery("apple", 3, "kg", 2.0, LocalDate.now().plusDays(5));
    fridgeService.addGrocery(grocery1);
    fridgeService.addGrocery(grocery2);

    List<Grocery> groceries = fridgeService.getAllGroceries();
    Assertions.assertEquals(2, groceries.size());
  }

  /**
   * Tests interaction with a mocked {@link GroceryService}.
   * <p>Verifies the correct interactions with a mocked service during the addition of a grocery.</p>
   */
  @Test
  public void testAddGrocery_MockGroceryService() {
    Fridge mockFridge = mock(Fridge.class);
    GroceryService mockGroceryService = mock(GroceryService.class);
    FridgeService fridgeService = new FridgeService(mockFridge, mockGroceryService);

    Grocery grocery = new Grocery("banana", 10, "kg", 1.5, LocalDate.now().plusDays(7));
    when(mockGroceryService.areGroceriesClubbable(any(Grocery.class), eq(grocery)))
        .thenReturn(true);

    fridgeService.addGrocery(grocery);

    verify(mockFridge, times(1)).getGroceriesPerCategory();
  }

  /**
   * Tests removing a grocery with sufficient quantity available.
   * <p>Verifies that the quantity is reduced correctly and the operation returns {@code true}.</p>
   */
  @Test
  public void testRemoveGrocery_ExistingGroceryWithSufficientQuantity() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("apple", 5, "kg", 2.0, LocalDate.now().plusDays(10));
    fridgeService.addGrocery(grocery);

    boolean result = fridgeService.removeGrocery("apple", 3);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    assertTrue(result);
    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals("apple", groceries.get(0).getName());
    Assertions.assertEquals(2, groceries.get(0).getQuantity());
  }

  /**
   * Tests removing a grocery with insufficient quantity available.
   * <p>Verifies that the operation returns {@code false} and the quantity remains unchanged.</p>
   */
  @Test
  public void testRemoveGrocery_ExistingGroceryWithInsufficientQuantity() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("apple", 3, "kg", 2.0, LocalDate.now().plusDays(10));
    fridgeService.addGrocery(grocery);

    boolean result = fridgeService.removeGrocery("apple", 5);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    assertFalse(result);
    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals("apple", groceries.get(0).getName());
    Assertions.assertEquals(3, groceries.get(0).getQuantity());
  }

  /**
   * Tests attempting to remove a grocery that does not exist in the fridge.
   * <p>Verifies that the operation returns {@code false}.</p>
   */
  @Test
  public void testRemoveGrocery_NonExistingGrocery() {
    FridgeService fridgeService = new FridgeService();

    boolean result = fridgeService.removeGrocery("banana", 5);

    assertFalse(result);
  }

  /**
   * Tests removing the entire quantity of a grocery.
   * <p>Verifies that the grocery is removed from the fridge when its quantity becomes zero.</p>
   */
  @Test
  public void testRemoveGrocery_RemovingAllQuantity() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("apple", 5, "kg", 2.0, LocalDate.now().plusDays(10));
    fridgeService.addGrocery(grocery);

    boolean result = fridgeService.removeGrocery("apple", 5);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    assertTrue(result);
    assertTrue(groceries.isEmpty());
  }

  /**
   * Tests interaction with mocked services during grocery removal.
   * <p>Verifies no unintended interactions occur with mocked dependencies.</p>
   */
  @Test
  public void testRemoveGrocery_MockGroceryServiceInteraction() {
    Fridge mockFridge = mock(Fridge.class);
    GroceryService mockGroceryService = mock(GroceryService.class);
    FridgeService fridgeService = new FridgeService(mockFridge, mockGroceryService);

    Grocery grocery = new Grocery("banana", 10, "kg", 1.5, LocalDate.now().plusDays(7));
    when(mockFridge.getGroceriesPerCategory())
        .thenReturn(
            new HashMap<>() {
              {
                put(
                    "banana",
                    new ArrayList<>() {
                      {
                        add(grocery);
                      }
                    });
              }
            });

    fridgeService.removeGrocery("banana", 5);
    verifyNoMoreInteractions(mockGroceryService);
  }

  /**
   * Tests sorting groceries by name.
   * <p>Verifies that the groceries are sorted alphabetically.</p>
   */
  @Test
  void testGetGroceriesSortedByName() {
    FridgeService fridgeService = new FridgeService();
    fridgeService.addGrocery(new Grocery("banana", 1, "kg", 10, LocalDate.now().plusDays(5)));
    fridgeService.addGrocery(new Grocery("apple", 2, "kg", 15, LocalDate.now().plusDays(10)));

    List<Grocery> sortedGroceries = fridgeService.getGroceriesSortedByName();

    Assertions.assertEquals("apple", sortedGroceries.get(0).getName());
    Assertions.assertEquals("banana", sortedGroceries.get(1).getName());
  }

  /**
   * Tests finding a grocery by its name when it exists.
   * <p>Verifies that the correct grocery is returned.</p>
   */
  @Test
  void testFindGroceryByName_Existing() {
    FridgeService fridgeService = new FridgeService();
    fridgeService.addGrocery(new Grocery("milk", 1, "liters", 20, LocalDate.now().plusDays(5)));

    List<Grocery> foundGroceries = FridgeService.findGroceriesByName(("milk").toLowerCase());
    Assertions.assertNotNull(foundGroceries);
    Assertions.assertEquals(1, foundGroceries.size()); // Ensure only one grocery is found
    Assertions.assertEquals("milk", foundGroceries.get(0).getName().toLowerCase()); // Verify the name
  }

  /**
   * Tests finding a grocery by its name when it does not exist.
   * <p>Verifies that the result is an empty list.</p>
   */
  @Test
  void testFindGroceryByName_NonExisting() {
    FridgeService fridgeService = new FridgeService();
    fridgeService.addGrocery(new Grocery("milk", 1, "liters", 20, LocalDate.now().plusDays(5)));

    List<Grocery> foundGroceries = FridgeService.findGroceriesByName("cheese");
    Assertions.assertTrue(foundGroceries.isEmpty()); // Ensure the list is empty
  }

  /**
   * Tests sorting groceries by their expiry date.
   * <p>Verifies that groceries are sorted in ascending order of expiry dates.</p>
   */
  @Test
  void testGetGroceriesSortedByExpiryDate() {
    FridgeService fridgeService = new FridgeService();
    fridgeService.addGrocery(new Grocery("banana", 1, "kg", 10, LocalDate.now().plusDays(5)));
    fridgeService.addGrocery(new Grocery("apple", 2, "kg", 15, LocalDate.now().plusDays(10)));

    List<Grocery> sortedGroceries = fridgeService.getGroceriesSortedByExpiryDate();

    Assertions.assertEquals("banana", sortedGroceries.get(0).getName());
    Assertions.assertEquals("apple", sortedGroceries.get(1).getName());
  }

  /**
   * Tests finding a grocery by name with a null input.
   * <p>Verifies that an {@link IllegalArgumentException} is thrown for null input.</p>
   */
  @Test
  void testFindGroceryByName_NullInput() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> FridgeService.findGroceriesByName(null));
  }

  /**
   * Tests adding a grocery with a negative quantity.
   * <p>Verifies that an {@link IllegalArgumentException} is thrown for invalid input.</p>
   */
  @Test
  void testAddGrocery_NegativeQuantity() {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Grocery("milk", -1, "liters", 20, LocalDate.now().plusDays(5)));
  }



}