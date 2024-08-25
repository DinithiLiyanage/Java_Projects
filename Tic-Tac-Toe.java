package tic_tac_toe;

import java.util.Scanner;

public class Game {
    // 3x3 grid to represent the Tic Tac Toe board
    private char [][] grid = new char[3][3];
    
    // Variable to track whose turn it is ('x' or 'o')
    private char turn;
    
    // Flag to determine if the game is two-player or against the computer
    private boolean isTwoPlayer;
    
    // Count of free positions left on the grid
    private int freePositions;
    
    // Constructor that initializes the game based on the mode (two-player or single-player)
    public Game(boolean isTwo) {
        // Set all grid positions to '-'
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                grid[i][j] = '-';
            }
        }
        // 'x' starts the game
        turn = 'x';
        // All positions are initially free
        freePositions = 9;
        // Set the game mode
        isTwoPlayer = isTwo;
    }
    
    // Method to insert a symbol (either 'x' or 'o') at the specified grid position
    public int insertSymbol(char symbol, int row, int col) {
        // Check if the chosen position is free
        if(grid[row][col] == '-') {
            // Place the symbol and decrease the number of free positions
            grid[row][col] = symbol;
            freePositions--;
            // Check if this move results in a win
            return findWinner();
        } else {
            // If the position is already taken, prompt the user to try again
            System.out.println("Oops! Seems this spot is already filled. Let's try again.");
            return -1;
        }
    }

    // Method to print the current state of the grid
    public void printGrid() {
        for(int i = 0; i < 3; i++) {
            String row = "";
            for(int j = 0; j < 3; j++) {
                row += (grid[i][j] + " ");
            }
            System.out.println(row);
        }
    }
    
    // Method to generate a random position (0, 1, or 2) for the computer's move
    public int generateAutoPosition() {
        double num = Math.random();
        num = (num * 3);
        return (int) num;
    }
    
    // Method to switch turns between 'x' and 'o'
    private void switchTurn() {
        if(turn == 'x') {
            turn = 'o';
        } else {
            turn = 'x';
        }
    }
    
    // Method to check if there's a winner after each move
    private int findWinner() {
        // Check all rows
        for(int i = 0; i < 3; i++) {
            if(grid[i][0] == grid[i][1] && grid[i][0] == grid[i][2] && grid[i][0] != '-') {
                return 1;
            }
        }
        // Check all columns
        for(int i = 0; i < 3; i++) {
            if(grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i] && grid[1][i] != '-') {
                return 1;
            }
        }
        // Check the two diagonals
        if(grid[0][0] == grid[1][1] && grid[0][0] == grid[2][2] && grid[0][0] != '-') {
            return 1;
        }
        if(grid[0][2] == grid[1][1] && grid[0][2] == grid[2][0] && grid[0][2] != '-') {
            return 1;
        }
        // No winner yet
        return 0;
    }
    
    // Method to handle user input or generate computer's move
    public int getInput() {
        char input;
        int row, col, result;
        // Print the current grid before taking input
        printGrid();
        Scanner sc = new Scanner(System.in);
        // Handle input for two-player mode
        if(isTwoPlayer) {
            System.out.println("It's " + turn + "'s turn.");
            System.out.println("Enter row (0, 1, 2): ");
            row = sc.nextInt();
            System.out.println("Enter column (0, 1, 2): ");
            col = sc.nextInt();
        } else {
            // Handle input for single-player mode (against computer)
            if(turn == 'x') {
                System.out.println("It's your turn.");
                System.out.println("Enter row (0, 1, 2): ");
                row = sc.nextInt();
                System.out.println("Enter column (0, 1, 2): ");
                col = sc.nextInt();
            } else {
                // Computer's move
                System.out.println("Here I go");
                row = generateAutoPosition();
                col = generateAutoPosition();
            }
        }
        // Insert the symbol at the chosen position and check the result
        result = insertSymbol(turn, row, col);
        // If the position was already filled, ask for input again
        if (result == -1) {
            getInput();
        }
        return result;
    }

    // Main method to start the game
    public static void main(String[] args) {
        boolean isTwo;
        int insertResult = 0;
        // Ask the user if they want to play a two-player game
        System.out.println("Do you want to play a 2 player game? (y or n)");
        Scanner sc = new Scanner(System.in);
        char response = sc.next().charAt(0);
        if (response == 'y') {
            isTwo = true;
        } else {
            isTwo = false;
        }
        // Create a new game instance
        Game g1 = new Game(isTwo);
        
        // Game loop: keep playing until there's a winner or no free positions
        while(!(g1.freePositions == 0 || insertResult == 1)) {
            insertResult = g1.getInput();
            g1.switchTurn();
        }
        // Announce the result
        if(insertResult == 1) {
            System.out.println(g1.turn + " won \\'^'/ ");
        } else {
            System.out.println("Oops, it's a tie ^^/ ");
        }
    }
}
