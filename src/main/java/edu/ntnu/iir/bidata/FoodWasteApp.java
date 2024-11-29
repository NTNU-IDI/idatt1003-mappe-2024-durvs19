package edu.ntnu.iir.bidata;

import static edu.ntnu.iir.bidata.UserInterface.UserInterface.init;
import static edu.ntnu.iir.bidata.UserInterface.UserInterface.start;

import edu.ntnu.iir.bidata.UserInterface.UserInterface;
import edu.ntnu.iir.bidata.services.FridgeService;
import edu.ntnu.iir.bidata.services.GroceryService;
import edu.ntnu.iir.bidata.services.RecipeService;

/**
 * Application for managing food waste by tracking groceries and recipes.
 *
 * <p>This application allows users to add, remove, and view groceries, as well as add and view
 * recipes. It also provides an option to view possible recipes that can be made with the current
 * groceries.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * FoodWasteApp.main(new String[]{});
 * }</pre>
 */
public class FoodWasteApp {

  public static final FridgeService fridgeService = new FridgeService();
  public static final RecipeService recipeService = new RecipeService();
  public static final GroceryService groceryService = new GroceryService();

  public static void main(String[] args) {
    UserInterface userInterface = new UserInterface();
    init(); // Initialize sample data
    start(); // Start the console menu
  }
}