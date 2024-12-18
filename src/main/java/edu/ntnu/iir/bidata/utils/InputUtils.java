package edu.ntnu.iir.bidata.utils;

import edu.ntnu.iir.bidata.model.Grocery;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.services.GroceryService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for reading and validating user input.
 *
 * <p>This class provides methods to read and validate various types of user input, such as strings,
 * numbers, dates, and yes/no responses.</p>
 */
public class InputUtils {

  /**
   * Reads a non-empty string from the user.
   *
   * @param scanner the {@code Scanner} object for reading user input
   * @param prompt  The message to display to the user.
   * @return A non-empty string entered by the user.
   */
  public static String readNonEmptyString(Scanner scanner, String prompt) {
    while (true) {
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      if (input.isEmpty()) {
        System.out.println("Input cannot be empty. Please try again.");
      } else {
        return input;
      }
    }
  }

  /**
   * Reads a double within a specified range from the user.
   *
   * @param scanner the {@code Scanner} object for reading user input
   * @param prompt  The message to display to the user.
   * @param min     The minimum acceptable value.
   * @param max     The maximum acceptable value.
   * @return A valid double within the specified range.
   */
  public static double readValidatedDouble(Scanner scanner, String prompt, double min, double max) {
    while (true) {
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      try {
        double value = Double.parseDouble(input);
        if (value < min || value > max) {
          System.out.printf("Please enter a number between %.2f and %.2f.%n", min, max);
        } else {
          return value;
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid number.");
      }
    }
  }

  /**
   * Reads an integer within a specified range from the user.
   *
   * @param scanner the {@code Scanner} object for reading user input
   * @param prompt  The message to display to the user.
   * @param min     The minimum acceptable value.
   * @param max     The maximum acceptable value.
   * @return A valid integer within the specified range.
   */
  public static int readValidatedInt(Scanner scanner, String prompt, int min, int max) {
    while (true) {
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      try {
        int value = Integer.parseInt(input);
        if (value < min || value > max) {
          System.out.printf("Please enter a number between %d and %d.%n", min, max);
        } else {
          return value;
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid integer.");
      }
    }
  }


  /**
   * Reads a date from the user in the format YYYY-MM-DD.
   *
   * @param scanner the {@code Scanner} object for reading user input
   * @param prompt  The message to display to the user.
   * @return a valid {@code LocalDate} object entered by the user
   */
  public static LocalDate readDate(Scanner scanner, String prompt) {
    while (true) {
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      try {
        return LocalDate.parse(input);
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
      }
    }
  }

  // Predefined list of units
  private static final List<String> UNITS = List.of("kg", "liters", "pieces", "cups",
      "tablespoons");

  /**
  /**
   * Displays available units and allows the user to select one.
   *
   * @param scanner the {@code Scanner} object for reading user input
   * @return the selected unit
   */
  public static String selectUnit(Scanner scanner) {
    System.out.println("\nAvailable units:");
    for (int i = 0; i < UNITS.size(); i++) {
      System.out.printf("%d. %s%n", i + 1, UNITS.get(i));
    }

    int choice = readValidatedInt(scanner, "Choose a unit (1-" + UNITS.size() + "): ",
        1, UNITS.size());
    return UNITS.get(choice - 1);
  }

  /**
   * Displays the list of groceries.
   *
   * <p>If the list of groceries is empty, this method will print a message
   * indicating that no groceries are available. Otherwise, it will display
   * the details of each grocery item in the list.</p>
   *
   * @param groceries the list of grocery items to display. Must not be null.
   */
  public static void displayGroceries(List<Grocery> groceries) {
    if (groceries.isEmpty()) {
      System.out.println("No groceries available.");
      return;
    }

    // Print table headers
    System.out.println("\n+---------------------------------------------------------------------+");
    System.out.printf("| %-20s | %-10s | %-15s | %-15s | %-10s |\n",
        "Name", "Quantity", "Price per Unit (NOK)", "Expiry Date", "Status");
    System.out.println("+-----------------------------------------------------------------------+");

    // Print each grocery item
    for (Grocery grocery : groceries) {
      String status = GroceryService.isExpired(grocery) ? "Expired" : "Fresh";
      System.out.printf("| %-20s | %-10.2f | %-15.2f | %-15s | %-10s |\n",
          grocery.getName(), grocery.getQuantity(),
          grocery.getPricePerUnit(), grocery.getExpiryDate(), status);
    }

    System.out.println("+-----------------------------------------------------------------------+");
  }

  /**
   * Displays the recipes in a table format.
   *
   * @param recipes List of recipes to display.
   */
  public static void displayRecipes(List<Recipe> recipes) {
    if (recipes.isEmpty()) {
      System.out.println("No recipes available.");
      return;
    }

    // Print table headers
    System.out.println("\n+---------------------------------------------------------------------"
        + "---------------------------------------------------------------------+");
    System.out.printf("| %-25s | %-50s | %-55s | %-6s |\n", "Name", "Description", "Ingredients",
        "Serves");
    System.out.println("+-----------------------------------------------------------------------"
        + "-------------------------------------------------------------+");

    // Print each recipe
    for (Recipe recipe : recipes) {
      String ingredients = recipe.getIngredients().toString();
      System.out.printf("| %-25s | %-50s | %-45s | %-6d |\n",
          recipe.getName(),
          recipe.getDescription(),
          ingredients,
          recipe.getServes());
    }

    System.out.println("+--------------------------------------------------------------"
        + "----------------------------------------------------------------------------+");
  }






}
