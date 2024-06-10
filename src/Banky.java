import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Banky implements Serializable {
    private Map<String, Double> accounts;
    private transient Scanner scanner;

    public Banky() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    // Account management methods
    public void createAccount(String accountNumber, double initialBalance) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account already exists!");
        } else {
            accounts.put(accountNumber, initialBalance);
            System.out.println("Account created successfully!");
        }
    }

    public void checkBalance(String accountNumber) {
        Double balance = accounts.get(accountNumber);
        if (balance != null) {
            System.out.println("Current Balance: " + balance);
        } else {
            System.out.println("Account not found!");
        }
    }

    public void deposit(String accountNumber, double amount) {
        if (amount > 0) {
            accounts.merge(accountNumber, amount, Double::sum);
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Double balance = accounts.get(accountNumber);
        if (balance != null) {
            if (amount > 0 && balance >= amount) {
                accounts.put(accountNumber, balance - amount);
                System.out.println("Withdrawn: " + amount);
            } else {
                System.out.println("Insufficient funds or invalid amount!");
            }
        } else {
            System.out.println("Account not found!");
        }
    }

    public void transfer(String fromAccount, String toAccount, double amount) {
        Double fromBalance = accounts.get(fromAccount);
        Double toBalance = accounts.get(toAccount);

        if (fromBalance != null && toBalance != null) {
            if (fromBalance >= amount && amount > 0) {
                accounts.put(fromAccount, fromBalance - amount);
                accounts.put(toAccount, toBalance + amount);
                System.out.println("Transfer successful!");
            } else {
                System.out.println("Insufficient funds or invalid amount!");
            }
        } else {
            System.out.println("One or both accounts not found!");
        }
    }

    // Data persistence methods
    public void saveData(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Data saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static Banky loadData(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Banky) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return null;
        }
    }

    // User interface methods
    public void showMenu() {
        System.out.println("******** WELCOME TO ATM ********");
        System.out.println("ATM Menu:");
        System.out.println("1. Create Account");
        System.out.println("2. Check Balance");
        System.out.println("3. Deposit Cash");
        System.out.println("4. Withdraw Cash");
        System.out.println("5. Transfer Funds");
        System.out.println("6. Save Data");
        System.out.println("7. Load Data");
        System.out.println("8. Exit");
    }

    public void start() {
        int choice;
        do {
            showMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    createAccountPrompt();
                    break;
                case 2:
                    checkBalancePrompt();
                    break;
                case 3:
                    depositPrompt();
                    break;
                case 4:
                    withdrawPrompt();
                    break;
                case 5:
                    transferPrompt();
                    break;
                case 6:
                    saveDataPrompt();
                    break;
                case 7:
                    loadDataPrompt();
                    break;
                case 8:
                    System.out.println("Exiting ATM. Thank You!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option!");
            }
        } while (choice != 8);
    }

    private void createAccountPrompt() {
        System.out.print("Enter new account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        createAccount(accountNumber, initialBalance);
    }

    private void checkBalancePrompt() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        checkBalance(accountNumber);
    }

    private void depositPrompt() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        deposit(accountNumber, amount);
    }

    private void withdrawPrompt() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        withdraw(accountNumber, amount);
    }

    private void transferPrompt() {
        System.out.print("Enter source account number: ");
        String fromAccount = scanner.nextLine();
        System.out.print("Enter destination account number: ");
        String toAccount = scanner.nextLine();
        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();
        transfer(fromAccount, toAccount, amount);
    }

    private void saveDataPrompt() {
        System.out.print("Enter file name to save data: ");
        String fileName = scanner.nextLine();
        saveData(fileName);
    }

    private void loadDataPrompt() {
        System.out.print("Enter file name to load data: ");
        String fileName = scanner.nextLine();
        Banky loadedBank = loadData(fileName);
        if (loadedBank != null) {
            this.accounts = loadedBank.accounts;
            System.out.println("Data loaded successfully.");
        }
    }

    public static void main(String[] args) {
        Banky bank = new Banky();
        bank.start();
    }
}
