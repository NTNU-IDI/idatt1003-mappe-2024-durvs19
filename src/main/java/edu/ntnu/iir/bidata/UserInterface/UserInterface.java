package edu.ntnu.iir.bidata.UserInterface;

import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Smoothie;
import edu.ntnu.iir.bidata.services.FridgeService;
import edu.ntnu.iir.bidata.services.GroceryService;
import edu.ntnu.iir.bidata.services.RecipeService;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
    FridgeService.addGrocery(new Grocery("Milk", 1, "liters", 20, LocalDate.now().plusDays(9)));
    FridgeService.addGrocery(new Grocery("Eggs", 12, "pieces", 10, LocalDate.now().plusDays(10)));
    FridgeService.addGrocery(new Grocery("Flour", 1, "kg", 20, LocalDate.now().plusMonths(6)));
    FridgeService.addGrocery(new Grocery("Onion", 6, "pieces", 9, LocalDate.now().plusMonths(1)));
    FridgeService.addGrocery(new Grocery("Potato", 10, "pieces", 11, LocalDate.now().plusMonths(1)));
    FridgeService.addGrocery(new Grocery("Cheese", 0.5, "kg", 32, LocalDate.now().plusWeeks(3)));

    //adding some expired groceries:
    FridgeService.addGrocery(new Grocery("Bread", 1, "loaf", 29, LocalDate.now().minusDays(3))); // Expired 3 days ago
    FridgeService.addGrocery(new Grocery("Milk", 1, "litres", 20, LocalDate.now().minusDays(2)));
    FridgeService.addGrocery(new Grocery("Tomato", 3, "pieces", 8, LocalDate.now().minusDays(4)));
    FridgeService.addGrocery(new Grocery("Yoghurt", 0.25, "kg", 16, LocalDate.now().minusDays(3)));
    FridgeService.addGrocery(new Grocery("eggs", 2, "pieces", 10, LocalDate.now().minusDays(4)));

    //adding some groceries for smoothies:
    FridgeService.addGrocery(new Grocery("Strawberries", 2, "cups", 14, LocalDate.now().plusDays(7)));
    FridgeService.addGrocery(new Grocery("Banana", 6, "pieces", 8, LocalDate.now().plusDays(5)));
    FridgeService.addGrocery(new Grocery("Yoghurt", 0.5, "kg", 22, LocalDate.now().plusDays(10)));
    FridgeService.addGrocery(new Grocery("Avocado", 4, "pieces", 9, LocalDate.now().plusDays(12)));
    FridgeService.addGrocery(new Grocery("Ginger", 3, "pieces", 6, LocalDate.now().plusDays(20)));
    FridgeService.addGrocery(new Grocery("Lemon Juice", 0.5, "tablespoons", 4, LocalDate.now().plusMonths(6)));
    FridgeService.addGrocery(new Grocery("Mango", 4, "pieces", 10, LocalDate.now().plusDays(7)));

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
  Map<String, Double> MangoLassiIngredients = Map.of(
      "Mango", 1.0,       // pieces
      "Milk", 0.3,          // in litres
      "yoghurt", 0.5,       // in cups
      "sugar", 0.1            // tbsp
  );
  RecipeService.addRecipe(
      new Recipe(
          "Mango Lassi Milkshake",
          "A refreshing indian style mango milkshake.",
          "Blend all ingredients until smooth.",
          MangoLassiIngredients,
          2));
}


/**
   * Start the console menu for user interaction.
   *
   * <p>This method displays the main menu and processes user inputs to perform various actions.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.start();
   * }</pre>
   */
  public static void start() {
    Scanner scanner = new Scanner(System.in);
    boolean exit = false;

    while (!exit) {
      displayMenu();
      System.out.print("Select an option: ");
      int choice = -1;
      while (true) {
        System.out.print("Select an option: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
          System.out.println("Input cannot be empty. Please enter a number.");
          continue;
        }

        try {
          choice = Integer.parseInt(input);
          break; // Exit the loop if input is valid
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Please enter a valid number.");
        }
      }
      try {
        switch (choice) {
          case 1:
            addGrocery(scanner);
            break;
          case 2:
            removeGrocery(scanner);
            break;
          case 3:
            viewAllGroceries();
            break;
          case 4:
            viewExpiredGroceries();
            break;
          case 5:
            calculateTotalValue();
            break;
          case 6:
            calculateTotalValueOfExpiredItems();
            break;
          case 7:
            addRecipe(scanner);
            break;
          case 8:
            viewAllRecipes();
            break;
          case 9:
            viewPossibleRecipes(scanner);
            break;
          case 10:
            createSmoothie(scanner);
            break;
          case 11:
            viewAllSmoothieRecipes();
            break;
          case 12:
            System.out.println("Exiting application. Goodbye!");
            exit = true;
            break;
          default:
            System.out.println("Invalid choice. Please try again.");
            break;
        }
      } catch (Exception e) {
        System.out.println("An error occurred. Please try again.");
      }
    }

    scanner.close();
  }


  /**
   * Display the main menu options to the user.
   *
   * <p>This method prints the available actions the user can select from the console menu.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.displayMenu();
   * }</pre>
   */
  private static void displayMenu() {
    System.out.println("\n--- In-House Food Waste Management Menu ---");
    System.out.println("1. Add Grocery");
    System.out.println("2. Remove Grocery");
    System.out.println("3. View All Groceries");
    System.out.println("4. View Only Expired Groceries");
    System.out.println("5. Calculate Total Value of Groceries");
    System.out.println("6. Calculate Total Value of Expired Groceries");
    System.out.println("7. Add Recipe");
    System.out.println("8. View All Recipes");
    System.out.println("9. View Possible Recipes with Current Groceries");
    System.out.println("10. Create Smoothie");
    System.out.println("11. View All Smoothie Recipes");
    System.out.println("12. Exit");
  }
  /**
   * Add a new grocery item to the fridge.
   *
   * <p>This method prompts the user to enter details for a new grocery item and adds it to the
   * fridge.
   *
   * @param scanner Scanner object for user input
   *     <pre>{@code
   * Scanner scanner = new Scanner(System.in);
   * FoodWasteApp.addGrocery(scanner);
   * }</pre>
   */

  private static void addGrocery(Scanner scanner) {
    System.out.print("Enter grocery name: ");
    String name = scanner.nextLine();

    System.out.print("Enter quantity: ");
    double quantity = scanner.nextDouble();

    System.out.print("Enter unit (e.g., liters, kg, pieces): ");
    String unit = scanner.next();

    System.out.print("Enter price per unit (in NOK): ");
    double pricePerUnit = scanner.nextDouble();

    System.out.print("Enter expiry date (YYYY-MM-DD): ");
    String expiryDateInput = scanner.next();
    LocalDate expiryDate = LocalDate.parse(expiryDateInput);

    FridgeService.addGrocery(new Grocery(name, quantity, unit, pricePerUnit, expiryDate));
    System.out.println("Grocery added successfully.");
  }
  /**
   * Remove a grocery item from the fridge.
   *
   * <p>This method prompts the user to enter details for removing a grocery item from the fridge.
   *
   * @param scanner Scanner object for user input
   *     <pre>{@code
   * Scanner scanner = new Scanner(System.in);
   * FoodWasteApp.removeGrocery(scanner);
   * }</pre>
   */

  private static void removeGrocery(Scanner scanner) {
    try {
    System.out.print("Enter grocery name to remove: ");
    String name = scanner.nextLine();

    System.out.print("Enter quantity to remove: ");
    double quantity = scanner.nextDouble();
    scanner.nextLine();

    boolean success = FridgeService.removeGrocery(name, quantity);
    if (success) {
      System.out.println("Grocery removed successfully.");
    } else {
      System.out.println("Failed to remove grocery. Check the name and quantity.");
    }
    } catch (InputMismatchException e) {
      System.out.println("Invalid input. Please ensure you enter valid details.");
      scanner.nextLine(); // Clear invalid input
    }
  }
  /**
   * View all grocery items in the fridge.
   *
   * <p>This method displays all groceries sorted by expiry date, indicating if any are expired.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.viewAllGroceries();
   * }</pre>
   */

  private static void viewAllGroceries() {
    System.out.println("\n--- All Groceries ---");
    FridgeService.getAllGroceries().stream()
        .sorted(Comparator.comparing(Grocery::getExpiryDate))
        .forEach(
            x -> {
              if (GroceryService.isExpired(x)) {
                System.out.println(x + " (expired)");
              } else {
                System.out.println(x);
              }
            });
  }
  /**
   * View all expired grocery items in the fridge.
   *
   * <p>This method displays only the groceries that have passed their expiry date.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.viewExpiredGroceries();
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
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.calculateTotalValue();
   * }</pre>
   */

  private static void calculateTotalValue() {
    double totalValue = FridgeService.calculateTotalValue();
    System.out.printf("Total value of groceries: NOK %.2f%n", totalValue);
  }
  /**
   * Calculate the total value of all expired groceries in the fridge.
   *
   * <p>method computes and prints the total value of the expired groceries currently in the fridge.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.calculateTotalValueOfExpiredItems();
   * }</pre>
   */

  private static void calculateTotalValueOfExpiredItems() {
    double totalValue = FridgeService.calculateTotalValueOfExpiredGroceries();
    System.out.printf("Total value of expired groceries: NOK %.2f%n", totalValue);
  }
  /**
   * Add a new recipe to the recipe book.
   *
   * <p>This method prompts the user to enter details for a new recipe and adds it to the recipe
   * book.
   *
   * @param scanner Scanner object for user input
   *     <pre>{@code
   * Scanner scanner = new Scanner(System.in);
   * FoodWasteApp.addRecipe(scanner);
   * }</pre>
   */

  private static void addRecipe(Scanner scanner) {
    System.out.print("Enter recipe name: ");
    String name = scanner.nextLine();

    System.out.print("Enter recipe description: ");
    String description = scanner.nextLine();

    System.out.print("Enter procedure: ");
    String procedure = scanner.nextLine();

    Map<String, Double> ingredients = new HashMap<>();
    System.out.println("Enter ingredients (type 'done' to finish):");
    while (true) {
      System.out.print("Ingredient name: ");
      String ingredientName = scanner.nextLine();
      if (ingredientName.equalsIgnoreCase("done")) {
        break;
      }

      double quantity = 0.0;
      while (true) {
        try {
          System.out.print("Quantity: ");
          quantity = scanner.nextDouble();
          scanner.nextLine(); // Consume the newline
          break; // Exit the loop if input is valid
        } catch (InputMismatchException e) {
          System.out.println("Invalid input. Please enter a numeric value for quantity.");
          scanner.nextLine(); // Clear the invalid input
        }
      }

      ingredients.put(ingredientName, quantity);
    }

    int serves = 0;
    while (true) {
      try {
        System.out.print("Serves (number of people): ");
        serves = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        break; // Exit the loop if input is valid
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter an integer value for serves.");
        scanner.nextLine(); // Clear the invalid input
      }
    }

    RecipeService.addRecipe(new Recipe(name, description, procedure, ingredients, serves));
    System.out.println("Recipe added successfully.");
  }
  /**
   * View all recipes in the recipe book.
   *
   * <p>This method displays all recipes stored in the recipe book.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * FoodWasteApp.viewAllRecipes();
   * }</pre>
   */

  private static void viewAllRecipes() {
    System.out.println("\n--- All Recipes ---");
    RecipeService.getRecipes().forEach(System.out::println);
  }
  /**
   * View all possible recipes that can be made with current groceries.
   *
   * <p>This method prompts the user to indicate whether to include expired groceries and displays
   * recipes that can be made with the groceries available in the fridge.
   *
   * @param scanner Scanner object for user input
   *     <pre>{@code
   * Scanner scanner = new Scanner(System.in);
   * FoodWasteApp.viewPossibleRecipes(scanner);
   * }</pre>
   */

  private static void viewPossibleRecipes(Scanner scanner) {
    System.out.print("Do you want to see possible recipes including expired groceries? (y/n): ");
    String includeExpiredGrocery = scanner.nextLine();

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
   * Prompts the user to enter a double value with validation.
   *
   * @param scanner Scanner object for user input
   * @param prompt  The message to display to the user
   * @return A valid double value entered by the user
   */
  private static double readDouble(Scanner scanner, String prompt) {
    while (true) {
      System.out.print(prompt);
      String input = scanner.nextLine();
      try {
        return Double.parseDouble(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a numeric value.");
      }
    }
  }

  private static int readInt(Scanner scanner, String prompt) {
    while (true) {
      System.out.print(prompt);
      String input = scanner.nextLine();
      try {
        int value = Integer.parseInt(input);
        if (value <= 0) {
          System.out.println("Please enter a positive integer.");
          continue;
        }
        return value;
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter an integer value.");
      }
    }
  }

  private static void createSmoothie(Scanner scanner) {
    try {
      System.out.print("Enter smoothie name: ");
      String smoothieName = scanner.nextLine().trim();

      if (!smoothieName.toLowerCase().contains("smoothie")) {
        smoothieName += " Smoothie";
      }


      System.out.print("Enter smoothie description: ");
      String smoothieDescription = scanner.nextLine().trim();

      // Create Smoothie without unit and pricePerUnit
      Smoothie smoothie = new Smoothie(smoothieName, smoothieDescription, LocalDate.now().plusWeeks(2));

      String ingredientName;
      do {
        System.out.print("Enter ingredient name (or type 'done' to finish): ");
        ingredientName = scanner.nextLine().trim();

        if (ingredientName.equalsIgnoreCase("done")) {
          break;
        }

        // Find matching groceries with streams
        String finalIngredientName = ingredientName;
        Grocery matchingGrocery = FridgeService.getAllGroceries().stream()
            .filter(g -> g.getName().equalsIgnoreCase(finalIngredientName))
            .findFirst()
            .orElse(null);

        if (matchingGrocery == null) {
          System.out.println("Grocery not found. Adding a placeholder ingredient.");
          double quantity = readDouble(scanner, "Enter quantity to use: ");
          // Removed unit prompt
          double pricePerUnit = readDouble(scanner, "Enter price per unit (in NOK): ");
          LocalDate expiryDate = LocalDate.now().plusMonths(1);

          // Assign a default unit
          String defaultUnit = "units"; // Adjust as needed
          Grocery placeholder = new Grocery(ingredientName, quantity, defaultUnit, pricePerUnit, expiryDate);
          smoothie.addIngredient(placeholder);
          FridgeService.addGrocery(placeholder);

          System.out.println("Placeholder ingredient added.");
          continue;
        }

        // Ask for quantity and validate
        double quantity = readDouble(scanner, "Enter quantity to use: ");
        if (quantity > matchingGrocery.getQuantity()) {
          System.out.println("Not enough quantity available. Skipping this ingredient.");
          continue;
        }

        // Add valid ingredient and update the fridge
        Grocery ingredientToAdd = new Grocery(
            matchingGrocery.getName(),
            quantity,
            matchingGrocery.getUnit(),
            matchingGrocery.getPricePerUnit(),
            matchingGrocery.getExpiryDate()
        );
        smoothie.addIngredient(ingredientToAdd);
        FridgeService.removeGrocery(matchingGrocery.getName(), quantity);
      } while (!ingredientName.equalsIgnoreCase("done"));

      // Save smoothie as a recipe
      RecipeService.addRecipe(new Recipe(
          smoothieName,
          smoothieDescription,
          "Blend all ingredients.",
          smoothie.getIngredientsMap(),
          1 // Assuming serves one person
      ));

      System.out.println("Smoothie created successfully:\n" + smoothie);
    } catch (Exception e) {
      System.out.println("Error creating smoothie: " + e.getMessage());
    }
  }




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
      System.out.print("\nEnter the number of the smoothie to view details (or type '0' to return to the main menu): ");
      String input = scanner.nextLine().trim();

      int selection;
      try {
        selection = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid number.");
        continue;
      }

      if (selection == 0) {
        // Return to main menu
        break;
      }

      if (selection < 1 || selection > smoothieRecipes.size()) {
        System.out.println("Invalid selection. Please choose a number from the list.");
        continue;
      }
      Recipe selectedRecipe = smoothieRecipes.get(selection - 1);
      System.out.println("\n--- Smoothie Details ---");
      System.out.println(selectedRecipe.toString());
    }
  }
}
