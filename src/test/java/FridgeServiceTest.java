import static org.junit.Assert.assertEquals;

import edu.ntnu.iir.bidata.model.Fridge;
import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.services.FridgeService;
import edu.ntnu.iir.bidata.services.GroceryService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FridgeServiceTest {

  @Test
  public void testAddGrocery_NewGrocery() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("Apple", 5, "kg", 2.0, LocalDate.now().plusDays(10));
    fridgeService.addGrocery(grocery);

    List<Grocery> groceries = fridgeService.getAllGroceries();
    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals("Apple", groceries.get(0).getName());
    Assertions.assertEquals(5, groceries.get(0).getQuantity());
  }

  @Test
  public void testAddGrocery_ExistingGrocery() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery1 = new Grocery("Apple", 5, "kg", 2.0, LocalDate.now().plusDays(10));
    Grocery grocery2 = new Grocery("Apple", 3, "kg", 2.0, LocalDate.now().plusDays(10));
    fridgeService.addGrocery(grocery1);
    fridgeService.addGrocery(grocery2);

    List<Grocery> groceries = fridgeService.getAllGroceries();
    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals("Apple", groceries.get(0).getName());
    Assertions.assertEquals(8, groceries.get(0).getQuantity());
  }

  @Test
  public void testAddGrocery_DifferentExpiryDate() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery1 = new Grocery("Apple", 5, "kg", 2.0, LocalDate.now().plusDays(10));
    Grocery grocery2 = new Grocery("Apple", 3, "kg", 2.0, LocalDate.now().plusDays(5));
    fridgeService.addGrocery(grocery1);
    fridgeService.addGrocery(grocery2);

    List<Grocery> groceries = fridgeService.getAllGroceries();
    Assertions.assertEquals(2, groceries.size());
  }

  @Test
  public void testAddGrocery_MockGroceryService() {
    Fridge mockFridge = mock(Fridge.class);
    GroceryService mockGroceryService = mock(GroceryService.class);
    FridgeService fridgeService = new FridgeService(mockFridge, mockGroceryService);

    Grocery grocery = new Grocery("Banana", 10, "kg", 1.5, LocalDate.now().plusDays(7));
    when(mockGroceryService.areGroceriesClubbable(any(Grocery.class), eq(grocery)))
        .thenReturn(true);

    fridgeService.addGrocery(grocery);

    verify(mockFridge, times(1)).getGroceriesPerCategory();
  }

  @Test
  public void testRemoveGrocery_ExistingGroceryWithSufficientQuantity() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("Apple", 5, "kg", 2.0, LocalDate.now().plusDays(10));
    fridgeService.addGrocery(grocery);

    boolean result = fridgeService.removeGrocery("Apple", 3);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    assertTrue(result);
    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals("Apple", groceries.get(0).getName());
    Assertions.assertEquals(2, groceries.get(0).getQuantity());
  }

  @Test
  public void testRemoveGrocery_ExistingGroceryWithInsufficientQuantity() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("Apple", 3, "kg", 2.0, LocalDate.now().plusDays(10));
    fridgeService.addGrocery(grocery);

    boolean result = fridgeService.removeGrocery("Apple", 5);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    assertFalse(result);
    Assertions.assertEquals(1, groceries.size());
    Assertions.assertEquals("Apple", groceries.get(0).getName());
    Assertions.assertEquals(3, groceries.get(0).getQuantity());
  }

  @Test
  public void testRemoveGrocery_NonExistingGrocery() {
    FridgeService fridgeService = new FridgeService();

    boolean result = fridgeService.removeGrocery("Banana", 5);

    assertFalse(result);
  }

  @Test
  public void testRemoveGrocery_RemovingAllQuantity() {
    FridgeService fridgeService = new FridgeService();
    Grocery grocery = new Grocery("Apple", 5, "kg", 2.0, LocalDate.now().plusDays(10));
    fridgeService.addGrocery(grocery);

    boolean result = fridgeService.removeGrocery("Apple", 5);
    List<Grocery> groceries = fridgeService.getAllGroceries();

    assertTrue(result);
    assertTrue(groceries.isEmpty());
  }

  @Test
  public void testRemoveGrocery_MockGroceryServiceInteraction() {
    Fridge mockFridge = mock(Fridge.class);
    GroceryService mockGroceryService = mock(GroceryService.class);
    FridgeService fridgeService = new FridgeService(mockFridge, mockGroceryService);

    Grocery grocery = new Grocery("Banana", 10, "kg", 1.5, LocalDate.now().plusDays(7));
    when(mockFridge.getGroceriesPerCategory())
        .thenReturn(
            new HashMap<>() {
              {
                put(
                    "Banana",
                    new ArrayList<>() {
                      {
                        add(grocery);
                      }
                    });
              }
            });

    fridgeService.removeGrocery("Banana", 5);

    verifyNoMoreInteractions(mockGroceryService);
  }
}