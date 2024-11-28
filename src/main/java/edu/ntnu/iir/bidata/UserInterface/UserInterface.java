package edu.ntnu.iir.bidata.UserInterface;

import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.services.FridgeService;
import edu.ntnu.iir.bidata.services.RecipeService;
import java.time.LocalDate;
import java.util.Map;

/**
 * Represents the user interface for the application. This class provides methods to initialize
 * resources and start the application.
 */
public class UserInterface {

  /**
   * Initialize the application with sample groceries and recipes.
   *
   * <p>This method adds sample grocery items to the fridge and sample recipes to the recipe book.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.init();
   * }</pre>
   */
  public static void init() {
    // Add some sample groceries to the fridge
    FridgeService.addGrocery(new Grocery("Milk", 2, "liters", 15, LocalDate.now().plusDays(5)));
    FridgeService.addGrocery(new Grocery("Eggs", 12, "pieces", 2, LocalDate.now().plusDays(10)));
    FridgeService.addGrocery(new Grocery("Flour", 1, "kg", 20, LocalDate.now().plusMonths(6)));

    // Add some sample recipes to the cookbook
    Map<String, Double> pancakeIngredients = Map.of("Milk", 1.5, "Eggs", 2.0, "Flour", 0.5);
    RecipeService.addRecipe(
        new Recipe(
            "Pancakes",
            "Delicious breakfast",
            "Mix and cook on a skillet.",
            pancakeIngredients,
            4));
  }
  /**
   * Starts the application by displaying the main user interface and handling user interactions.
   */

  public void start() {
    System.out.println("Starting application...");
    //placeholder for future logic to start the application
  }

}
