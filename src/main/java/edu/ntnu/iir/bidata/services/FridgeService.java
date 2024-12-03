package edu.ntnu.iir.bidata.services;

import edu.ntnu.iir.bidata.model.Fridge;
import edu.ntnu.iir.bidata.model.Grocery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/** Service class for managing groceries and food storage in a fridge. */
public class FridgeService {
  private static Fridge fridge;

  private static GroceryService groceryService;

  /**
   * Constructs a new {@code FridgeService} with the specified fridge and grocery service.
   *
   * @param mockFridge the fridge to be used
   * @param mockGroceryService the grocery service to be used
   */
  public FridgeService(Fridge mockFridge, GroceryService mockGroceryService) {
    this.fridge = mockFridge;
    this.groceryService = mockGroceryService;
  }

  /**
   * Constructs a new {@code FridgeService} with a new fridge and grocery service.
   */
  public FridgeService() {
    fridge = new Fridge();
    groceryService = new GroceryService();
  }

  /**
   * Adds a grocery item to the food storage.
   *
   * <p>If the same grocery item is added multiple times, it is merged with an existing entry
   * based on the expiry date. Their quantities are combined if the expiry dates match.
   * Otherwise, they are treated as separate entries.</p>
   *
   * @param grocery the grocery item to be added
   */
  public static void addGrocery(Grocery grocery) {
    ArrayList<Grocery> groceryToBeAdded = new ArrayList<>();
    groceryToBeAdded.add(grocery);
    fridge
        .getGroceriesPerCategory()
        .merge(
            grocery.getName(),
            groceryToBeAdded,
            (existing, newGrocery) -> {
              Optional<Grocery> existingGrocery =
                  existing.stream()
                      .filter(g -> groceryService.areGroceriesClubbable(g, grocery))
                      .findFirst();

              if (existingGrocery.isPresent()) {
                existingGrocery
                    .get()
                    .setQuantity(existingGrocery.get().getQuantity() + grocery.getQuantity());
              } else {
                existing.addAll(newGrocery);
              }
              return existing;
            });
  }

  /**
   * Removes a specified quantity of a grocery item from the food storage.
   *
   * @param name the name of the grocery item to be removed
   * @param quantity the quantity to be removed
   * @return {@code true} if the specified quantity was removed successfully,{@code false}otherwise
   */
  public static boolean removeGrocery(String name, double quantity) {
    List<Grocery> groceries = fridge.getGroceriesPerCategory().get(name);
    if (groceries == null) {
      return false;
    }

    // Calculate total quantity of the groceries
    double totalQuantity = groceries.stream().map(Grocery::getQuantity).reduce(0.0, Double::sum);
    if (totalQuantity < quantity) {
      return false;
    }

    // Remove the specified quantity
    double remainingQuantity = quantity;
    Iterator<Grocery> iterator = groceries.iterator();
    while (iterator.hasNext() && remainingQuantity > 0) {
      Grocery grocery = iterator.next();
      double groceryQuantity = grocery.getQuantity();
      if (groceryQuantity <= remainingQuantity) {
        remainingQuantity -= groceryQuantity;
        iterator.remove();
      } else {
        grocery.setQuantity(groceryQuantity - remainingQuantity);
        remainingQuantity = 0;
      }
    }

    if (groceries.isEmpty()) {
      fridge.getGroceriesPerCategory().remove(name);
    }
    return true;
  }

  /**
   * Retrieves all groceries stored in the fridge.
   *
   * <p>Both expired and non-expired groceries are included to provide
   * a complete overview of the storage.</p>
   *
   * @return a list of all grocery items in the fridge
   */
  public static List<Grocery> getAllGroceries() {
    return new ArrayList<>(
        fridge.getGroceriesPerCategory().values().stream()
            .flatMap(Collection::stream)
            .toList());
  }

  /**
   * Retrieves all groceries stored in the storage. Expired groceries are not included.
   *
   * @return a list of all non-expired grocery items in the food storage
   */
  public static List<Grocery> getGroceriesSortedByName() {
    return fridge.getGroceriesPerCategory()
        .values()
        .stream()
        .flatMap(Collection::stream)
        .sorted(Comparator.comparing(Grocery::getName))
        .toList();
  }

  /**
   * Retrieves all groceries sorted by expiry date.
   *
   * @return a list of groceries sorted by expiry date
   */
  public static List<Grocery> getGroceriesSortedByExpiryDate() {
    return fridge.getGroceriesPerCategory()
        .values()
        .stream()
        .flatMap(Collection::stream)
        .sorted(Comparator.comparing(Grocery::getExpiryDate))
        .toList();
  }

  /**
   * Retrieves all expired groceries from the fridge.
   *
   * @return a list of expired grocery items
   */
  public static List<Grocery> getExpiredGroceries() {
    return fridge.getGroceriesPerCategory().values().stream()
        .flatMap(Collection::stream)
        .filter(GroceryService::isExpired)
        .toList();
  }

  /**
   * Calculates the total value of all groceries stored in the fridge.
   *
   * @return the total value of all grocery items
   */
  public static double calculateTotalValue() {
    return fridge.getGroceriesPerCategory().values().stream()
        .flatMap(Collection::stream)
        .mapToDouble(groceryService::calculateValue)
        .sum();
  }

  /**
   * Calculates the total value of all expired groceries stored in the fridge.
   *
   * @return the total value of all expired grocery items
   */
  public static double calculateTotalValueOfExpiredGroceries() {
    return fridge.getGroceriesPerCategory().values().stream()
        .flatMap(Collection::stream)
        .filter(GroceryService::isExpired)
        .mapToDouble(groceryService::calculateValue)
        .sum();
  }

  /**
   * Retrieves all groceries with the specified name.
   *
   * @param name the name of the grocery items to retrieve
   * @return a list of grocery items with the specified name
   * @throws IllegalArgumentException if the {@code name} parameter is null
   */
  public static List<Grocery> findGroceriesByName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("The name parameter cannot be null");
    }
    return getAllGroceries().stream()
        .filter(grocery -> grocery.getName().equalsIgnoreCase(name))
        .toList();
  }



}