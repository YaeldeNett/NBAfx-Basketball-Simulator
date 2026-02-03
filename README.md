# 🏀 JavaFX Basketball Season Simulator

A JavaFX desktop application designed to simulate a basketball season, allowing users to manage teams, update player statistics, and organize tournament rounds. The program features an automated game simulation system that calculates match results based on team metrics and tracks standings throughout the season.

## Features

- **Team Management**: Add new teams, view existing teams, and manage team details.
- **Player Management**: Add, update, and remove players from teams. Manage player attributes such as name, credit, age, and shirt number.
- **Player Filtering**: Filter players by name, performance level (Edge, Common, Core, All Star), and age range.
- **Season Simulation**: Arrange tournament rounds and match up teams against each other.
- **Game Logic**: Automated game results based on player statistics and team averages.
- **Standings & Records**: View season records, win/loss stats, and explore game history.
- **MVC Architecture**: Built using the Model-View-Controller pattern for clean code organization.

## Technologies Used

- **Language**: Java
- **Framework**: JavaFX (for GUI)
- **Architecture**: MVC (Model-View-Controller)

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher (with JavaFX support).
- If using JDK 11+, you must include the JavaFX SDK modules.

### How to Run

1.  Clone the repository:
    ```bash
    git clone https://github.com/yourusername/NBAfx-Basketball-Simulator.git
    ```
2.  Open the project in your preferred IDE (IntelliJ IDEA, Eclipse, VS Code).
3.  Ensure JavaFX libraries are configured in your project settings.
4.  Run the `NBAfxApp.java` file located in the root directory to start the application.

## Project Structure

- `model/`: Contains business logic and data representations (Team, Player, Season, Game).
- `view/`: Contains FXML files for the user interface.
- `controller/`: Contains logic for handling user interactions and updating views.
- `utils/`: Contains helper classes for View loading and Controller management.
