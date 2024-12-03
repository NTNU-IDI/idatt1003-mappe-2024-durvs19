import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Test class for {@link RecipeService}.
 * This class contains unit tests to validate the functionality of the RecipeService methods.
 */
public class RecipeServiceTest {

  /**
   * Clears all recipes from the RecipeService before each test execution.
   */
  @BeforeEach
  public void clearRecipes() {
    RecipeService.getRecipes().clear();
  }

  /**
   * Tests that a recipe can be successfully added to the RecipeService.
   */
  @Test
  public void testAddRecipe() {
    Recipe recipe = new Recipe(
        "Pancakes",
        "Delicious breakfast pancakes",
        "Mix and cook on a skillet.",
        Map.of("Milk", 1.0, "Eggs", 2.0, "Flour", 0.5),
        4
    );

    RecipeService.addRecipe(recipe);

    List<Recipe> recipes = RecipeService.getRecipes();
    Assertions.assertEquals(1, recipes.size());
    Assertions.assertEquals("Pancakes", recipes.get(0).getName());
  }

  /**
   * Tests that a recipe can be removed successfully if it exists in the RecipeService.
   */
  @Test
  public void testRemoveRecipe_Existing() {
    Recipe recipe = new Recipe(
        "Pancakes",
        "Delicious breakfast pancakes",
        "Mix and cook on a skillet.",
        Map.of("Milk", 1.0, "Eggs", 2.0, "Flour", 0.5),
        4
    );

    RecipeService.addRecipe(recipe);
    boolean result = RecipeService.removeRecipe("Pancakes");

    Assertions.assertTrue(result);
    Assertions.assertTrue(RecipeService.getRecipes().isEmpty());
  }

  /**
   * Tests that attempting to remove a non-existing recipe returns false.
   */

  @Test
  public void testRemoveRecipe_NonExisting() {
    boolean result = RecipeService.removeRecipe("NonExistentRecipe");
    Assertions.assertFalse(result);
  }

  /**
   * Tests that all recipes can be retrieved from the RecipeService.
   */
  @Test
  public void testGetRecipes() {
    Recipe recipe1 = new Recipe(
        "Pancakes",
        "Delicious breakfast pancakes",
        "Mix and cook on a skillet.",
        Map.of("Milk", 1.0, "Eggs", 2.0, "Flour", 0.5),
        4
    );

    Recipe recipe2 = new Recipe(
        "Smoothie",
        "Refreshing banana smoothie",
        "Blend all ingredients.",
        Map.of("Banana", 2.0, "Milk", 1.0),
        2
    );

    RecipeService.addRecipe(recipe1);
    RecipeService.addRecipe(recipe2);

    List<Recipe> recipes = RecipeService.getRecipes();
    Assertions.assertEquals(2, recipes.size());
  }

  /**
   * Tests that recipes can be retrieved based on available ingredients in the fridge.
   */
  @Test
  public void testGetPossibleRecipes_WithAvailableIngredients() {
    Recipe recipe = new Recipe(
        "Pancakes",
        "Delicious breakfast pancakes",
        "Mix and cook on a skillet.",
        Map.of("Milk", 1.0, "Eggs", 2.0, "Flour", 0.5),
        4
    );

    RecipeService.addRecipe(recipe);

    List<Grocery> fridgeItems = List.of(
        new Grocery("Milk", 1.0, "liters", 20.0, LocalDate.now().plusDays(10)),
        new Grocery("Eggs", 2.0, "pieces", 10.0, LocalDate.now().plusDays(10)),
        new Grocery("Flour", 0.5, "kg", 15.0, LocalDate.now().plusDays(20))
    );

    List<Recipe> possibleRecipes = RecipeService.getPossibleRecipes(fridgeItems, "n");
    Assertions.assertEquals(1, possibleRecipes.size());
    Assertions.assertEquals("Pancakes", possibleRecipes.get(0).getName());
  }

  /**
   * Tests that no recipes are returned when the required ingredients are not available in sufficient quantity.
   */
  @Test
  public void testGetPossibleRecipes_WithoutAvailableIngredients() {
    Recipe recipe = new Recipe(
        "Pancakes",
        "Delicious breakfast pancakes",
        "Mix and cook on a skillet.",
        Map.of("Milk", 1.0, "Eggs", 2.0, "Flour", 0.5),
        4
    );

    RecipeService.addRecipe(recipe);

    List<Grocery> fridgeItems = List.of(
        new Grocery("Milk", 0.5, "liters", 20.0, LocalDate.now().plusDays(10))
    );

    List<Recipe> possibleRecipes = RecipeService.getPossibleRecipes(fridgeItems, "n");
    Assertions.assertTrue(possibleRecipes.isEmpty());
  }

  /**
   * Tests that recipes can be retrieved when expired ingredients are allowed.
   */
  @Test
  public void testGetPossibleRecipes_WithExpiredIngredients() {
    Recipe recipe = new Recipe(
        "Pancakes",
        "Delicious breakfast pancakes",
        "Mix and cook on a skillet.",
        Map.of("Milk", 1.0, "Eggs", 2.0, "Flour", 0.5),
        4
    );

    RecipeService.addRecipe(recipe);

    List<Grocery> fridgeItems = List.of(
        new Grocery("Milk", 1.0, "liters", 20.0, LocalDate.now().minusDays(5)),
        new Grocery("Eggs", 2.0, "pieces", 10.0, LocalDate.now().plusDays(10)),
        new Grocery("Flour", 0.5, "kg", 15.0, LocalDate.now().plusDays(20))
    );

    List<Recipe> possibleRecipes = RecipeService.getPossibleRecipes(fridgeItems, "y");
    Assertions.assertEquals(1, possibleRecipes.size());
    Assertions.assertEquals("Pancakes", possibleRecipes.get(0).getName());
  }
}
