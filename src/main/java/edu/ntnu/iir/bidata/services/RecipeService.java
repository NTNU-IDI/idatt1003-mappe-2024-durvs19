package edu.ntnu.iir.bidata.services;

import edu.ntnu.iir.bidata.model.Cookbook;
import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.utils.IngredientChecker;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
   * Constructs a new RecipeService with the specified grocery service.
   *
   * @param mockGroceryService the grocery service to be used
   */
  public RecipeService(GroceryService mockGroceryService) {
    groceryService = mockGroceryService;
  }

  /**
   * Adds a new recipe to the list of recipes.
   *
   * @param recipe the recipe to be added
   * @example Recipe newRecipe = new Recipe("Pasta", ingredientsMap);
   *     recipeService.addRecipe(newRecipe);
   */
  public static void addRecipe(Recipe recipe) {
    cookbookForRecipes.getRecipes().add(recipe);
  }


  /**
   * Returns the list of all recipes.
   *
   * @return the list of recipes
   * @example List&lt;Recipe&gt; allRecipes = recipeService.getRecipes();
   */
  public static List<Recipe> getRecipes() {
    return cookbookForRecipes.getRecipes();
  }

  /**
   * Returns a list of possible recipes that can be made with the given fridge items.
   *
   * @param fridgeItems the list of groceries available in the fridge
   * @param includeExpiredGrocery whether to include expired groceries ("y" for yes, otherwise no)
   * @return the list of possible recipes that can be made
   */
  public static List<Recipe> getPossibleRecipes(List<Grocery> fridgeItems, String includeExpiredGrocery) {
    // Normalize fridge item names to lowercase and group them
    Map<String, List<Grocery>> groceriesByName = fridgeItems.stream()
        .filter(grocery -> {
          if (includeExpiredGrocery.equalsIgnoreCase("y")) {
            return true; // Include all items
          } else {
            return !IngredientChecker.isExpired(grocery); // Exclude expired items
          }
        })
        .collect(Collectors.groupingBy(grocery -> grocery.getName().toLowerCase())); // Normalize to lowercase

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
   * Returns a list of all smoothie recipes.
   *
   * @return the list of smoothie recipes
   */

  /**
   * Removes a recipe by its name from the cookbook.
   *
   * @param recipeName the name of the recipe to remove
   * @return true if the recipe was found and removed, false otherwise
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


  public static List<Recipe> getSmoothieRecipes() {
    return cookbookForRecipes.getRecipes().stream()
        .filter(recipe ->
            recipe.getName().toLowerCase().contains("smoothie") ||
                recipe.getName().toLowerCase().contains("milkshake")
        )
        .toList();
  }

}
