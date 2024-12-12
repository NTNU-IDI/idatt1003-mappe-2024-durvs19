[//]: # 

STUDENT NAME = Durva Parmar
STUDENT ID = 10015

## Project description

This project, In-House Food Waste Management, is a console-based program for tracking and managing shopping, recipes, and food waste. 
The software allows users to add groceries, track their expiration dates, calculate the total value of their purchases, and find recipes based on available ingredients.
The purpose is to reduce food waste by assisting customers in making better use of their supplies.

## Project structure

The project is structured into several packages, each serving a specific purpose. The structure ensures separation of concerns, modularity, and testability. Below is a description of the project's structure:

1. Packages
------------------------------------------------------------------------------------------------------------
   edu.ntnu.iir.bidata.model:

Contains the core model classes representing the main entities in the application:

Cookbook: A collection of recipes.
Fridge: Represents the storage of groceries.
Grocery: A single grocery item with attributes such as name, quantity, and expiry date.
Recipe: Represents a recipe with ingredients and preparation details.
Smoothie: A specialized recipe for smoothies.

------------------------------------------------------------------------------------------------------------
edu.ntnu.iir.bidata.services:

Contains service classes that handle business logic:

FridgeService: Manages groceries in the fridge.
GroceryService: Provides utility functions for groceries, such as checking expiry and calculating values.
RecipeService: Handles recipe management, including adding and filtering recipes.

------------------------------------------------------------------------------------------------------------
edu.ntnu.iir.bidata.userInterface:

Contains the UserInterface class, which manages user interactions and console-based input/output.
The UserInterface class provides a menu-driven system to interact with the application.

------------------------------------------------------------------------------------------------------------
edu.ntnu.iir.bidata.utils:

Provides utility classes for reusable functionality:

InputUtils: Handles user input validation and parsing.
IngredientChecker: Checks for expired groceries.

------------------------------------------------------------------------------------------------------------
2. File Locations
All source files are stored in their respective packages under the src/main/java/edu/ntnu/iir/bidata directory.

------------------------------------------------------------------------------------------------------------
3. Test Classes
JUnit test classes are stored in the src/test/java/edu/ntnu/iir/bidata directory. Each test class corresponds
to a class in the main source code and tests its functionality.

------------------------------------------------------------------------------------------------------------
5. Design Principles

Model classes represent data structures.
Service classes encapsulate business logic.
Utility classes provide reusable helper methods.

------------------------------------------------------------------------------------------------------------

## Link to repository

https://github.com/NTNU-IDI/idatt1003-mappe-2024-durvs19

## How to run the project

Ensure you have Java Development Kit (JDK) installed on your machine (version 8 or above).
Install Maven for dependency management and building the project.

Clone the repository from GitHub to your local machine.
Open the project in an IDE like IntelliJ IDEA or Eclipse.
Build the project using Maven (mvn clean install).
Navigate to the main class FoodWasteApp located in the src/main/java/edu/ntnu/iir/bidata directory.
Run the main method of the FoodWasteApp class to start the program.

(make sure the dependencies are installed or work as they should: lombok dependency, junit dependency, mockito dependency) 
all of them are added as dependencies in the pom.xml file.

The program starts with a console-based menu system. Users can select options to:
Add groceries to the fridge.
Remove items based on name and quantity.
View all groceries or those nearing expiration.
Add recipes to the cookbook and view recipes based on available ingredients.
The output is displayed in a user-friendly tabular format in the console.

## How to run the tests

Ensure JUnit 5 is included in the Maven dependencies (already configured in the pom.xml).
Open the project in your IDE.
Navigate to the test directory: src/test/java/edu/ntnu/iir/bidata.

Right-click on the test directory or individual test classes and choose Run All Tests or the equivalent option in your IDE.

Mocking is used in some tests to isolate specific functionalities.


## References

Barnes & Kölling. "Objects First With Java," Sixth Edition.
This book served as a reference for understanding and implementing object-oriented programming principles like encapsulation, inheritance, and modularity.
