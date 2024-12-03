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


[//]: # (TODO: Describe the structure of your project here. How have you used packages in your structure. Where are all sourcefiles stored. Where are all JUnit-test classes stored. etc.)

## Link to repository

https://github.com/NTNU-IDI/idatt1003-mappe-2024-durvs19

## How to run the project

[//]: # (TODO: Describe how to run your project here. What is the main class? What is the main method?
What is the input and output of the program? What is the expected behaviour of the program?)

## How to run the tests

[//]: # (TODO: Describe how to run the tests here.)

## References

[//]: # (TODO: Include references here, if any. For example, if you have used code from the course book, include a reference to the chapter.
Or if you have used code from a website or other source, include a link to the source.)
