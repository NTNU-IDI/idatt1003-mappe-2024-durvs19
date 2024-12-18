package edu.ntnu.iir.bidata.services;

import edu.ntnu.iir.bidata.model.Cookbook;
import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Smoothie;
import edu.ntnu.iir.bidata.utils.IngredientChecker;
import edu.ntnu.iir.bidata.utils.InputUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

/**
 * The RecipeService class provides methods to manage and interact with recipes, including adding
 * recipes, retrieving all recipes, and filtering recipes based on available grocery items.
 */
@NoArgsConstructor
public class RecipeService {
  private static Cookbook cookbookForRecipes = new Cookbook();
  private static GroceryService groceryService = new GroceryService();

  /**
   * Constructs a new {@code RecipeService} with the specified grocery service.
   *
   * @param mockGroceryService the grocery service to be used
   */
  public RecipeService(GroceryService mockGroceryService) {
    groceryService = mockGroceryService;
  }

  /**
   * Adds a new recipe to the cookbook.
   *
   * <p><strong>Example:</strong></p>
   * <pre><code>
   * Recipe newRecipe = new Recipe("Pasta", ingredientsMap);
   * recipeService.addRecipe(newRecipe);
   * </code></pre>
   *
   * @param recipe the recipe to be added
   */
  public static void addRecipe(Recipe recipe) {
    cookbookForRecipes.getRecipes().add(recipe);
  }


  /**
   * Retrieves the list of all recipes.
   *
   * <p><strong>Example:</strong></p>
   * <pre><code>
   * List&lt;Recipe&gt; allRecipes = recipeService.getRecipes();
   * </code></pre>
   *
   * @return the list of recipes
   */
  public static List<Recipe> getRecipes() {
    return cookbookForRecipes.getRecipes();
  }

  /**
   * Returns a list of possible recipes that can be made with the given fridge items.
   *
   * <p>Filters recipes based on the availability of ingredients. If expired groceries are included,
   * those ingredients are also considered.</p>
   *
   * @param fridgeItems the list of groceries available in the fridge
   * @param includeExpiredGrocery whether to include expired groceries ("y" for yes, otherwise no)
   * @return the list of possible recipes that can be made
   */
  public static List<Recipe> getPossibleRecipes(List<Grocery> fridgeItems,
      String includeExpiredGrocery) {
    // Normalize fridge item names to lowercase and group them
    Map<String, List<Grocery>> groceriesByName = fridgeItems.stream()
        .filter(grocery -> {
          if (includeExpiredGrocery.equalsIgnoreCase("y")) {
            return true; // Include all items
          } else {
            return !IngredientChecker.isExpired(grocery); // Exclude expired items
          }
        })
        .collect(Collectors.groupingBy(grocery -> grocery.getName().toLowerCase()));
    // Normalize to lowercase

    // Filter recipes by checking ingredient availability
    return cookbookForRecipes.getRecipes().stream()
        .filter(recipe -> recipe.getIngredients().entrySet().stream()
            .allMatch(entry -> {
              List<Grocery> availableGroceries = groceriesByName.get(entry.getKey().toLowerCase());
              double totalAvailableQuantity = Optional.ofNullable(availableGroceries)
                  .orElse(new ArrayList<>())
                  .stream()
                  .mapToDouble(Grocery::getQuantity)
                  .sum();
              return totalAvailableQuantity >= entry.getValue();
            }))
        .toList();
  }

  /**
   * Removes a recipe by its name from the cookbook.
   *
   * <p>If a recipe with the specified name exists, it is removed from the cookbook.</p>
   *
   * @param recipeName the name of the recipe to remove
   * @return {@code true} if the recipe was found and removed, {@code false} otherwise
   */
  public static boolean removeRecipe(String recipeName) {
    Optional<Recipe> recipeToRemove = cookbookForRecipes.getRecipes().stream()
        .filter(recipe -> recipe.getName().equalsIgnoreCase(recipeName))
        .findFirst();

    if (recipeToRemove.isPresent()) {
      cookbookForRecipes.getRecipes().remove(recipeToRemove.get());
      return true; // Successfully removed
    } else {
      return false; // Recipe not found
    }
  }

  /**
   * Creates a new smoothie recipe.
   *
   * <p>The user is prompted to enter the smoothie name, description, and ingredients. If an
   * ingredient is not found in the fridge, a placeholder ingredient is added.</p>
   *
   * @param scanner the scanner to read user input
   */
  public static void createSmoothie(Scanner scanner) {
    String smoothieName = InputUtils.readNonEmptyString(scanner, "Enter smoothie name: ");
    if (!smoothieName.toLowerCase().contains("smoothie")) {
      smoothieName += " Smoothie";
    }

    String smoothieDescription = InputUtils.readNonEmptyString(scanner, "Enter "
        + "smoothie description: ");
    Smoothie smoothie = new Smoothie(smoothieName, smoothieDescription,
        LocalDate.now().plusWeeks(2));

    while (true) {
      String ingredientName = InputUtils.readNonEmptyString(scanner, "Enter"
          + " ingredient name (or type 'done' to finish): ");
      if (ingredientName.equalsIgnoreCase("done")) {
        break;
      }

      Grocery matchingGrocery = FridgeService.getAllGroceries().stream()
          .filter(g -> g.getName().equalsIgnoreCase(ingredientName))
          .findFirst()
          .orElse(null);

      if (matchingGrocery == null) {
        System.out.println("Grocery not found. Adding a placeholder ingredient.");
        double quantity = InputUtils.readValidatedDouble(scanner, "Enter quantity to"
            + " use: ", 0.0, Double.MAX_VALUE);

        // Select predefined unit
        String unit = InputUtils.selectUnit(scanner);

        double pricePerUnit = InputUtils.readValidatedDouble(scanner, "Enter "
            + "price per unit (in NOK): ", 0.0, Double.MAX_VALUE);
        LocalDate expiryDate = LocalDate.now().plusMonths(1);

        Grocery placeholder = new Grocery(ingredientName, quantity, unit, pricePerUnit, expiryDate);
        smoothie.addIngredient(placeholder);
        FridgeService.addGrocery(placeholder);

        System.out.println("Placeholder ingredient added.");
        continue;
      }

      double maxQuantity = matchingGrocery.getQuantity();
      double quantity = InputUtils.readValidatedDouble(scanner, "Enter quantity "
          + "to use: ", 0.0, maxQuantity);
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

    addRecipe(new Recipe(
        smoothieName,
        smoothieDescription,
        "Blend all ingredients.",
        smoothie.getIngredientsMap(),
        1
    ));

    System.out.println("Smoothie created successfully:\n" + smoothie);
  }


  /**
   * Retrieves a list of all smoothie recipes.
   *
   * <p>Filters recipes that have "smoothie" or "milkshake" in their names.</p>
   *
   * @return the list of smoothie recipes
   */
  public static List<Recipe> getSmoothieRecipes() {
    return cookbookForRecipes.getRecipes().stream()
        .filter(recipe ->
            recipe.getName().toLowerCase().contains("smoothie")
                ||
                recipe.getName().toLowerCase().contains("milkshake")
        )
        .toList();
  }

}