# Informatii Comunitati

## Overview
This Java-based application manages a social network-like structure where users can be added, removed, and connected through friendships. It allows users to view their details, print friendships, explore communities, and find the most social communities (i.e., the largest connected groups of users). The application operates through a simple text-based console interface.

## Features
- **Add User:** Add a new user to the system by specifying their first and last name.
- **Remove User:** Remove a user from the system by their ID.
- **Add Friendship:** Create a friendship between two users in the network.
- **Remove Friendship:** Remove an existing friendship between two users.
- **Print User:** Display the users.
- **Print Friendship:** Print all friendships in the system, showing the connected users.
- **Communities:** Identify and display all connected communities (groups of users that are interconnected).
- **Most Social Communities:** Identify and display the largest communities (the ones with the most connected users).
- **Console Menu:** Displays the menu and reads the user's input from the console.

## Technologies & Architecture
- **Programming Language:** Java
- **Database:** PostgreSQL
- **Persistence:** JDBC 
- **Architecture:** Layered architecture with the following layers:
  - **Domain Layer:** Defines core entities such as `User` and `Friendship`.
  - **Repository Layer:** Handles data persistence.
  - **Service Layer:** Implements business logic.
  - **UI Layer:** Provides a simple console interface for user interactions.

## Installation & Setup
1. Install **PostgreSQL** and create a database.
2. Create the `users` and `friendships` tables in the database.
3. Update the database connection details in the code (e.g., `UserDBRepository.java` with your PostgreSQL credentials).
4. Import the project into an IDE (IntelliJ IDEA or Eclipse).
5. Run the application using the main class.

## Usage
1. **Add a User:** Select option `1` from the console menu and input the user's first name and last name.
2. **Remove a User:** Select option `2` from the menu and specify the user ID to delete.
3. **Add a Friendship:** Select option `3` from the menu and provide two user IDs to create a friendship between them.
4. **Remove a Friendship:** Select option `4` from the menu and specify the user IDs to remove the friendship.
5. **Print User:** Select option `5` from the menu and enter the user ID to print user details.
6. **Print Friendship:** Select option `6` from the menu to print all friendships in the system.
7. **View Communities:** Select option `7` from the menu to view all connected communities.
8. **Most Social Communities:** Select option `8` from the menu to find and display the largest communities.
9. **Exit:** Select option `0` to exit the program.
