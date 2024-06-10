import java.util.Random;
import java.util.Scanner;

class Game {
    private int number;
    private int inputNumber;
    private int noOfGuesses = 0;
    private final int maxGuesses = 10; // Maximum number of allowed guesses

    // Constructor to initialize the random number
    public Game() {
        Random rand = new Random();
        this.number = rand.nextInt(100) + 1; // Random number between 1 and 100
    }

    // Getter for the number of guesses
    public int getNoOfGuesses() {
        return noOfGuesses;
    }

    // Setter for the number of guesses
    public void setNoOfGuesses(int noOfGuesses) {
        this.noOfGuesses = noOfGuesses;
    }

    // Method to take user input
    public void takeUserInput() {
        System.out.print("Guess the number (between 1 and 100): ");
        Scanner sc = new Scanner(System.in);
        inputNumber = sc.nextInt();
    }

    // Method to check if the guessed number is correct
    public boolean isCorrectNumber() {
        noOfGuesses++;
        if (inputNumber == number) {
            System.out.format("Congratulations! You guessed the number %d correctly in %d attempts.\n", number, noOfGuesses);
            return true;
        } else if (inputNumber < number) {
            System.out.println("Your guess is too low.");
        } else if (inputNumber > number) {
            System.out.println("Your guess is too high.");
        }

        int guessesLeft = maxGuesses - noOfGuesses;
        if (guessesLeft > 0) {
            System.out.println("You have " + guessesLeft + " guesses left.");
        } else {
            System.out.println("You've run out of guesses! The correct number was " + number + ".");
            return true; // End the game if no guesses are left
        }
        return false;
    }

    // Method to reset the game for a new round
    public void resetGame() {
        Random rand = new Random();
        this.number = rand.nextInt(100) + 1; // New random number between 1 and 100
        this.noOfGuesses = 0;
    }
}

public class Guessing_Game {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean playAgain = true;
        int totalRounds = 0;
        int totalGuesses = 0;

        System.out.println("Welcome to the 'Guess the Number' game!");
        System.out.println("You need to guess a number between 1 and 100.");
        System.out.println("You have 10 attempts to guess the correct number. Good luck!\n");

        while (playAgain) {
            Game game = new Game();
            boolean guessedCorrectly = false;

            while (!guessedCorrectly) {
                game.takeUserInput();
                guessedCorrectly = game.isCorrectNumber();
                System.out.println();
            }

            totalRounds++;
            totalGuesses += game.getNoOfGuesses();

            System.out.print("Do you want to play another round? (yes/no): ");
            String response = sc.next();
            playAgain = response.equalsIgnoreCase("yes");

            if (playAgain) {
                game.resetGame();
            }
        }

        System.out.println("Thank you for playing the 'Guess the Number' game!");
        System.out.println("You played a total of " + totalRounds + " rounds.");
        System.out.println("You took a total of " + totalGuesses + " guesses.");
        System.out.printf("Your average guesses per round: %.2f\n", (double) totalGuesses / totalRounds);
        System.out.println("Have a great day!");
    }
}
