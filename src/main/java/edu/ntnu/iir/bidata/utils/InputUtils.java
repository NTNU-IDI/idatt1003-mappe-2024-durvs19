package edu.ntnu.iir.bidata.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

  /**
   * Reads a yes/no response from the user.
   *
   * @param scanner the {@code Scanner} object for reading user input
   * @param prompt  The message to display to the user.
   * @return {@code true} if the user enters 'y' or 'yes', {@code false} if 'n' or 'no'
   */
  public static boolean readYesNo(Scanner scanner, String prompt) {
    while (true) {
      System.out.print(prompt + " (y/n): ");
      String input = scanner.nextLine().trim().toLowerCase();
      if (input.equals("y") || input.equals("yes")) {
        return true;
      } else if (input.equals("n") || input.equals("no")) {
        return false;
      } else {
        System.out.println("Invalid input. Please enter 'y' or 'n'.");
      }
    }
  }



}
