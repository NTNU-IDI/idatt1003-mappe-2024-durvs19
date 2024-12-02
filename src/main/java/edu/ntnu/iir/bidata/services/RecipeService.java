package edu.ntnu.iir.bidata.services;

import edu.ntnu.iir.bidata.model.Cookbook;
import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.model.Recipe;
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
   * Returns a list of possible recipes that can be made with the given fridge items. This method
   * filters the recipes based on whether expired groceries should be included.
   *
   * @param fridgeItems the list of groceries available in the fridge
   * @param includeExpiredGrocery whether to include expired groceries ("y" for yes, otherwise no)
   * @return the list of possible recipes that can be made
   * @example List&lt;Grocery&gt; fridgeItems = Arrays.asList(new Grocery("Tomato", 2, false));
   *@example List&lt;Recipe&gt; possibleRecipes = recipeService.getPossibleRecipes(fridgeItems,"n");
   */
  public static List<Recipe> getPossibleRecipes(List<Grocery> fridgeItems,
      String includeExpiredGrocery) {

    Map<String, List<Grocery>> expiredItemsIncludedBasedOnFlag =
        fridgeItems.stream()
            .filter(
                grocery -> {
                  if (includeExpiredGrocery.equalsIgnoreCase("y")) {
                    return true;
                  } else {
                    return !GroceryService.isExpired(grocery);
                  }
                })
            .collect(Collectors.groupingBy(Grocery::getName));

    return cookbookForRecipes.getRecipes().stream()
        .filter(
            recipe ->
                recipe.getIngredients().entrySet().stream()
                    .allMatch(
                        entry -> {
                          List<Grocery> grocery =
                              expiredItemsIncludedBasedOnFlag.get(entry.getKey());
                          double availableQuantity =
                              Optional.ofNullable(grocery).orElse(new ArrayList<>()).stream()
                                  .mapToDouble(Grocery::getQuantity)
                                  .sum();
                          return grocery != null && availableQuantity >= entry.getValue();
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
