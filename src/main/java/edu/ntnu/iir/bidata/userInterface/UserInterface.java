package edu.ntnu.iir.bidata.userInterface;

import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Smoothie;
import edu.ntnu.iir.bidata.services.FridgeService;
import edu.ntnu.iir.bidata.services.GroceryService;
import edu.ntnu.iir.bidata.services.RecipeService;
import edu.ntnu.iir.bidata.utils.InputUtils;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents the user interface for the application.
 *
 * <p>This class provides methods to initialize resources,interact with the user via a console menu,
 * and perform various application operations like adding groceries, managing recipes,
 * and creating smoothies.</p>
 */
public class UserInterface {

  /**
   * Initializes the application with sample groceries and recipes.
   *
   * <p>This method populates the fridge with sample grocery items and adds
   * sample recipes to the recipe book.</p>
   */
  public static void init() {
    // Add some sample groceries to the fridge
    FridgeService.addGrocery(new Grocery("Milk", 1, "liters",
        20, LocalDate.now().plusDays(9)));
    FridgeService.addGrocery(new Grocery("Eggs", 12, "pieces",
        10, LocalDate.now().plusDays(10)));
    FridgeService.addGrocery(new Grocery("Flour", 1, "kg",
        20, LocalDate.now().plusMonths(6)));
    FridgeService.addGrocery(new Grocery("Onion", 6, "pieces",
        9, LocalDate.now().plusMonths(1)));
    FridgeService.addGrocery(new Grocery("Potato", 10, "pieces",
        11, LocalDate.now().plusMonths(1)));
    FridgeService.addGrocery(new Grocery("Cheese", 0.5, "kg",
        32, LocalDate.now().plusWeeks(3)));

    //adding some expired groceries:
    FridgeService.addGrocery(new Grocery("Bread", 1, "loaf", 29,
        LocalDate.now().minusDays(3))); // Expired 3 days ago
    FridgeService.addGrocery(new Grocery("Milk", 1, "litres", 20,
        LocalDate.now().minusDays(2)));
    FridgeService.addGrocery(new Grocery("Tomato", 3, "pieces", 8,
        LocalDate.now().minusDays(4)));
    FridgeService.addGrocery(new Grocery("Yoghurt", 0.25, "kg", 16,
        LocalDate.now().minusDays(3)));
    FridgeService.addGrocery(new Grocery("eggs", 2, "pieces", 10,
        LocalDate.now().minusDays(4)));

    //adding some groceries for smoothies:
    FridgeService.addGrocery(new Grocery("Strawberries", 2, "cups",
        14, LocalDate.now().plusDays(7)));
    FridgeService.addGrocery(new Grocery("Banana", 6, "pieces",
        8, LocalDate.now().plusDays(5)));
    FridgeService.addGrocery(new Grocery("Yoghurt", 0.5, "kg",
        22, LocalDate.now().plusDays(10)));
    FridgeService.addGrocery(new Grocery("Avocado", 4, "pieces",
        9, LocalDate.now().plusDays(12)));
    FridgeService.addGrocery(new Grocery("lemon Juice", 0.5, "tablespoons",
        4, LocalDate.now().plusMonths(3)));
    FridgeService.addGrocery(new Grocery("Mango", 4, "pieces",
        10, LocalDate.now().plusDays(7)));

    // Add some sample recipes to the cookbook
    Map<String, Double> pancakeIngredients = Map.of("Milk", 1.5, "Eggs", 2.0, "Flour", 0.5);
    RecipeService.addRecipe(
        new Recipe(
            "Pancakes",
            "Delicious breakfast with 4 eggs, 500g flour and 0.5L milk",
            "Mix and cook on a skillet.",
            pancakeIngredients,
            4));
    Map<String, Double> paneerIngreidents = Map.of("Milk", 2.0, "lemon juice", 0.5);
    RecipeService.addRecipe(
        new Recipe(
            "Paneer (Indian Cheese)",
            "Delicious indian cheese made from milk and lemon juice, saves expired milk",
            "heat milk to just below boiling point, turn stove off and add lemon juice"
                + "and stir. milk will begin to curdle. strain curdled milk through a cheesecloth"
                + "and rinse with cold water. hang the cheesecloth for 30 minutes "
                + "to drain excess water",
            paneerIngreidents,
            5));

    addSampleSmoothieRecipes();
  }

  /**
   * Adds predefined smoothie recipes to the recipe book.
   *
   * <p>This method creates and adds sample smoothie recipes to the RecipeService.
   */
  private static void addSampleSmoothieRecipes() {
    // Sample Smoothie 1: Strawberry Banana Smoothie
    Map<String, Double> strawberryBananaIngredients = Map.of(
        "Strawberries", 1.0, // in cups
        "Banana", 1.0,       // whole
        "Yogurt", 0.5,        // in cups
        "Honey", 0.2          // in tablespoons
    );
    RecipeService.addRecipe(
        new Recipe(
            "Strawberry Banana Smoothie",
            "A sweet smoothie with strawberries and bananas.",
            "Blend all ingredients until smooth.",
            strawberryBananaIngredients,
            2));

    // Sample Smoothie 2: Avo shake
    Map<String, Double> avoShakeIngredients = Map.of(
        "avocado", 2.0,    // pieces
        "banana", 1.0,        // pieces
        "milk", 0.3,          // in litres
        "cinnamon", 1.0         // tsp
    );
    RecipeService.addRecipe(
        new Recipe(
            "Avo shake smoothie",
            "A creamy green smoothie to delight your taste buds.",
            "Blend all ingredients until smooth.",
            avoShakeIngredients,
            2));

    // Sample Smoothie 3: Tropical Mango Smoothie
    Map<String, Double> mangoLassiIngredients = Map.of(
        "Mango", 1.0,       // pieces
        "Milk", 0.3,          // in litres
        "yoghurt", 0.5,       // in cups
        "sugar", 0.1            // tbsp
    );

    RecipeService.addRecipe(new Recipe("Mango Lassi Milkshake",
        "A refreshing indian style mango milkshake.",
        "Blend all ingredients until smooth.",
        mangoLassiIngredients,
        2));
  }


  /**
   * Starts the console menu for user interaction.
   *
   * <p>This method displays the main menu and processes user inputs to perform various actions.</p>
   */
  public static void start() {
    Scanner scanner = new Scanner(System.in);
    boolean exit = false;

    while (!exit) {
      displayMenu();
      int choice = InputUtils.readValidatedInt(scanner, "Select an option: ",
          1, 14);
      try {
        switch (choice) {
          case 1:
            addGrocery(scanner);
            break;
          case 2:
            removeGrocery(scanner);
            break;
          case 3:
            findGroceryByName(scanner);
            break;
          case 4:
            viewAllGroceries(scanner);
            break;
          case 5:
            viewExpiredGroceries();
            break;
          case 6:
            calculateTotalValue();
            break;
          case 7:
            calculateTotalValueOfExpiredItems();
            break;
          case 8:
            addRecipe(scanner);
            break;
          case 9:
            viewAllRecipes();
            break;
          case 10:
            removeRecipe(scanner);
            break;
          case 11:
            viewPossibleRecipes(scanner);
            break;
          case 12:
            createSmoothie(scanner);
            break;
          case 13:
            viewAllSmoothieRecipes();
            break;
          case 14:
            System.out.println("Exiting application. Goodbye!");
            exit = true;
            break;
          default:
            System.out.println("Invalid choice. Please try again.");
            break;
        }
      } catch (Exception e) {
        System.out.println("An error occurred. Please try again.");
        e.printStackTrace(); // Print the stack trace for debugging
      }
    }

    scanner.close();
  }

  /**
   * Displays the main menu options to the user.
   */
  private static void displayMenu() {
    System.out.println("\n=================== In-House Food Waste Management ===================");
    System.out.println(" 1. Add Grocery                             |  8. Add Recipe");
    System.out.println(" 2. Remove Grocery                          |  9. View All Recipes");
    System.out.println(" 3. Find Grocery by Name                    | 10. Remove Recipe");
    System.out.println(" 4. View All Groceries                      | 11. View Possible Recipes");
    System.out.println(" 5. Expired Groceries                       |     with Current Groceries");
    System.out.println(" 6. Total Value/Price (All Groceries)       | 12. Create Smoothie");
    System.out.println(" 7. Total Value/Price (Expired Groceries)   | 13. View Smoothie Recipes");
    System.out.println("                                            | 14. Exit");
    System.out.println("=================================================================");
    System.out.println("Choose between (1-14): ");
  }


  /**
   * Add a new grocery item to the fridge.
   *
   * <p>This method prompts the user to enter details for a new grocery item and adds it to the
   * fridge.
   *
   * @param scanner the {@code Scanner} object for reading user input
   */
  private static void addGrocery(Scanner scanner) {
    String name = InputUtils.readNonEmptyString(scanner, "Enter grocery name: ").toLowerCase();
    double quantity = InputUtils.readValidatedDouble(scanner, "Enter quantity: ",
        0.0, Double.MAX_VALUE);
    String unit = InputUtils.readNonEmptyString(scanner, "Enter unit (e.g., liters,"
        + " kg, pieces): ");
    double pricePerUnit = InputUtils.readValidatedDouble(scanner, "Enter price per"
        + " unit (in NOK): ", 0.0, Double.MAX_VALUE);
    LocalDate expiryDate = InputUtils.readDate(scanner, "Enter expiry date (YYYY-MM-DD): ");

    FridgeService.addGrocery(new Grocery(name, quantity, unit, pricePerUnit, expiryDate));
    System.out.println("Grocery added successfully.");
  }

  /**
   * Remove a specified quantity of a grocery item from the fridge.
   *
   * <p>This method prompts the user to enter the name and quantity of a grocery item to remove
   * from
   * the fridge.
   *
   * @param scanner the {@code Scanner} object for reading user input
   */
  private static void removeGrocery(Scanner scanner) {
    String name = InputUtils.readNonEmptyString(scanner, "Enter grocery name to remove: ");
    double quantity = InputUtils.readValidatedDouble(scanner, "Enter quantity to remove: ",
        0.0, Double.MAX_VALUE);

    boolean success = FridgeService.removeGrocery(name, quantity);
    if (success) {
      System.out.println("Grocery removed successfully.");
    } else {
      System.out.println("Failed to remove grocery. Check the name and quantity.");
    }
  }

  /**
   * View all grocery items in the fridge.
   *
   * <p>This method displays all groceries sorted by expiry date, indicating if any are expired.
   *
   * @param scanner the {@code Scanner} object for reading user input
   */
  private static void findGroceryByName(Scanner scanner) {
    String name = InputUtils.readNonEmptyString(scanner, "Enter the name of the "
        + "grocery to find: ");
    List<Grocery> matchingGroceries = FridgeService.findGroceriesByName(name);

    if (!matchingGroceries.isEmpty()) {
      System.out.println("\n--- Grocery Found ---");
      matchingGroceries.forEach(System.out::println);
    } else {
      System.out.println("\nNo grocery found with the name: " + name);
    }
  }

  /**
   * Views all groceries, either sorted by name or by expiry date.
   *
   * @param scanner the {@code Scanner} object for reading user input
   */
  private static void viewAllGroceries(Scanner scanner) {
    System.out.println("How would you like to view the groceries?");
    System.out.println("1. Sorted by Name");
    System.out.println("2. Sorted by Expiry Date");
    int choice = InputUtils.readValidatedInt(scanner, "Enter choice (1 or 2): ", 1, 2);

    List<Grocery> groceries;
    if (choice == 1) {
      groceries = FridgeService.getGroceriesSortedByName();
    } else {
      groceries = FridgeService.getGroceriesSortedByExpiryDate();
    }

    System.out.println("\n--- Groceries ---");
    groceries.forEach(
        grocery -> {
          if (GroceryService.isExpired(grocery)) {
            System.out.println(grocery + " (expired)");
          } else {
            System.out.println(grocery);
          }
        }
    );
  }

  /**
   * View all expired grocery items in the fridge.
   *
   * <p>This method displays only the groceries that have passed their expiry date.
   *
   * <p><strong>Example usage:</strong></p>
   * <pre>{@code
   * UserInterface.viewExpiredGroceries();
   * }</pre>
   */
  private static void viewExpiredGroceries() {
    System.out.println("\n--- Expired Groceries ---");
    List<Grocery> expiredGroceries = FridgeService.getExpiredGroceries();
    if (expiredGroceries.isEmpty()) {
      System.out.println("No expired groceries found.");
    } else {
      expiredGroceries.forEach(System.out::println);
    }
  }

  /**
   * Calculate the total value of all groceries in the fridge.
   *
   * <p>This method computes and prints the total value of the groceries currently in the fridge.
   *
   * <p><strong>Example usage:</strong></p>
   * <pre>{@code
   * UserInterface.calculateTotalValue();
   * }</pre>
   */
  private static void calculateTotalValue() {
    double totalValue = FridgeService.calculateTotalValue();
    System.out.printf("Total value of groceries: NOK %.2f%n", totalValue);
  }

  /**
   * Calculates and displays the total value of all expired groceries in the fridge.
   *
   * <p>This method is useful for evaluating waste or loss due to expired groceries.</p>
   *
   * <p><strong>Example usage:</strong></p>
   * <pre>{@code
   * UserInterface.calculateTotalValueOfExpiredItems();
   * }</pre>
   */
  private static void calculateTotalValueOfExpiredItems() {
    double totalValue = FridgeService.calculateTotalValueOfExpiredGroceries();
    System.out.printf("Total value of expired groceries: NOK %.2f%n", totalValue);
  }

  /**
   * Prompts the user to add a new recipe to the recipe book.
   *
   * <p>Users can specify recipe details, including ingredients and quantities.</p>
   *
   * @param scanner the {@code Scanner} object for reading user input
   */
  private static void addRecipe(Scanner scanner) {
    String name = InputUtils.readNonEmptyString(scanner, "Enter recipe name: ").toLowerCase();
    String description = InputUtils.readNonEmptyString(scanner, "Enter recipe description:"
        + " ");
    String procedure = InputUtils.readNonEmptyString(scanner, "Enter procedure: ");

    Map<String, Double> ingredients = new HashMap<>();
    System.out.println("Enter ingredients (type 'done' to finish):");
    while (true) {
      String ingredientName = InputUtils.readNonEmptyString(scanner, "Ingredient name: ");
      if (ingredientName.equalsIgnoreCase("done")) {
        break;
      }

      double quantity = InputUtils.readValidatedDouble(scanner, "Quantity: ", 0.0,
          Double.MAX_VALUE);
      ingredients.put(ingredientName, quantity);
    }

    int serves = InputUtils.readValidatedInt(scanner, "Serves (number of people): ", 1,
        Integer.MAX_VALUE);

    RecipeService.addRecipe(new Recipe(name, description, procedure, ingredients, serves));
    System.out.println("Recipe added successfully.");
  }

  /**
   * Removes a recipe by name from the recipe book.
   *
   * @param scanner the {@code Scanner} object for reading user input
   */
  private static void removeRecipe(Scanner scanner) {
    String recipeName = InputUtils.readNonEmptyString(scanner, "Enter the name "
        + "of the recipe to remove: ");
    boolean success = RecipeService.removeRecipe(recipeName);

    if (success) {
      System.out.println("Recipe \"" + recipeName + "\" has been removed successfully.");
    } else {
      System.out.println("Recipe \"" + recipeName + "\" does not exist and cannot be removed.");
    }
  }

  /**
   * View all recipes in the recipe book.
   *
   * <p>This method displays all recipes stored in the recipe book.
   */
  private static void viewAllRecipes() {
    System.out.println("\n--- All Recipes ---");
    RecipeService.getRecipes().forEach(System.out::println);
  }

  /**
   * Displays all recipes that can be made with the current groceries in the fridge.
   *
   * <p>Users can choose whether to include expired groceries in the calculation.</p>
   *
   * @param scanner the {@code Scanner} object for reading user input
   */
  private static void viewPossibleRecipes(Scanner scanner) {
    String includeExpiredGrocery;
    while (true) {
      includeExpiredGrocery = InputUtils.readNonEmptyString(scanner,
          "Do you want to see possible recipes including expired groceries? "
              + "(y/n): ").toLowerCase();
      if (includeExpiredGrocery.equals("y") || includeExpiredGrocery.equals("n")) {
        break;
      }
      System.out.println("Invalid input. Please enter 'y' or 'n'.");
    }

    System.out.println("\n--- Possible Recipes with Current Groceries ---");
    List<Recipe> possibleRecipes =
        RecipeService.getPossibleRecipes(FridgeService.getAllGroceries(), includeExpiredGrocery);
    if (possibleRecipes.isEmpty()) {
      System.out.println("No recipes can be made with the current groceries.");
    } else {
      possibleRecipes.forEach(System.out::println);
    }
  }

  /**
   * Prompts the user to create a new smoothie recipe.
   *
   * <p>This method allows users to specify ingredients, check availability,
   * and update the fridge inventory.</p>
   *
   * @param scanner the {@code Scanner} object for reading user input
   */
  private static void createSmoothie(Scanner scanner) {
    try {
      String smoothieName = InputUtils.readNonEmptyString(scanner, "Enter smoothie name: ");
      if (!smoothieName.toLowerCase().contains("smoothie")) {
        smoothieName += " Smoothie";
      }

      String smoothieDescription = InputUtils.readNonEmptyString(scanner, "Enter smoothie"
          + " description: ");

      Smoothie smoothie = new Smoothie(smoothieName, smoothieDescription,
          LocalDate.now().plusWeeks(2));

      while (true) {
        String ingredientName = InputUtils.readNonEmptyString(scanner, "Enter ingredient"
            + " name (or type 'done' to finish): ");
        if (ingredientName.equalsIgnoreCase("done")) {
          break;
        }

        Grocery matchingGrocery = FridgeService.getAllGroceries().stream()
            .filter(g -> g.getName().equalsIgnoreCase(ingredientName))
            .findFirst()
            .orElse(null);

        if (matchingGrocery == null) {
          System.out.println("Grocery not found. Adding a placeholder ingredient.");
          double quantity = InputUtils.readValidatedDouble(scanner, "Enter quantity to use:"
              + " ", 0.0, Double.MAX_VALUE);
          double pricePerUnit = InputUtils.readValidatedDouble(scanner, "Enter price "
              + "per unit (in NOK): ", 0.0, Double.MAX_VALUE);
          LocalDate expiryDate = LocalDate.now().plusMonths(1);

          String defaultUnit = "units";
          Grocery placeholder = new Grocery(ingredientName, quantity, defaultUnit,
              pricePerUnit, expiryDate);
          smoothie.addIngredient(placeholder);
          FridgeService.addGrocery(placeholder);

          System.out.println("Placeholder ingredient added.");
          continue;
        }

        double maxQuantity = matchingGrocery.getQuantity();
        double quantity = InputUtils.readValidatedDouble(scanner, "Enter quantity to use: ",
            0.0, maxQuantity);
        if (quantity > maxQuantity) {
          System.out.println("Not enough quantity available. Skipping this ingredient.");
          continue;
        }

        Grocery ingredientToAdd = new Grocery(
            matchingGrocery.getName(),
            quantity,
            matchingGrocery.getUnit(),
            matchingGrocery.getPricePerUnit(),
            matchingGrocery.getExpiryDate()
        );
        smoothie.addIngredient(ingredientToAdd);
        FridgeService.removeGrocery(matchingGrocery.getName(), quantity);
      }

      RecipeService.addRecipe(new Recipe(
          smoothieName,
          smoothieDescription,
          "Blend all ingredients.",
          smoothie.getIngredientsMap(),
          1
      ));

      System.out.println("Smoothie created successfully:\n" + smoothie);
    } catch (Exception e) {
      System.out.println("Error creating smoothie: " + e.getMessage());
      e.printStackTrace(); // for debugging
    }
  }

  /**
   * Displays all smoothie recipes in the recipe book.
   */
  private static void viewAllSmoothieRecipes() {
    System.out.println("\n--- All Smoothie Recipes ---");
    List<Recipe> smoothieRecipes = RecipeService.getSmoothieRecipes();
    if (smoothieRecipes.isEmpty()) {
      System.out.println("No smoothie recipes found.");
      return;
    }

    for (int i = 0; i < smoothieRecipes.size(); i++) {
      Recipe recipe = smoothieRecipes.get(i);
      System.out.printf("%d. %s: %s%n", i + 1, recipe.getName(), recipe.getDescription());
    }

    Scanner scanner = new Scanner(System.in);
    while (true) {
      int selection = InputUtils.readValidatedInt(scanner,
          "\nEnter the number of the smoothie to view details (or type '0' to"
              + " return to the main menu): ",
          0, smoothieRecipes.size());

      if (selection == 0) {
        // Return to main menu
        break;
      }

      Recipe selectedRecipe = smoothieRecipes.get(selection - 1);
      System.out.println("\n--- Smoothie Details ---");
      System.out.println(selectedRecipe.toString());
    }
  }
}