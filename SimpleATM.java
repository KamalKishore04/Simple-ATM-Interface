import java.util.InputMismatchException;
import java.util.Scanner;

public class SimpleATM {
    private static final String CORRECT_PIN = "1234";
    private static final int MAX_PIN_ATTEMPTS = 3;
    private static final double MAX_TRANSACTION_LIMIT = 10000.0;
    private static final double MIN_TRANSACTION_AMOUNT = 1.0;

    private double balance;

    public SimpleATM(double initialBalance) {
        this.balance = initialBalance;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            SimpleATM atm = new SimpleATM(1000.0);
            System.out.println("Welcome to the ATM!");

            if (authenticateUser(scanner)) {
                atm.runEventLoop(scanner);
            } else {
                System.out.println("Too many failed attempts. Access denied.");
            }
        }
    }

    private static boolean authenticateUser(Scanner scanner) {
        int attempts = 0;
        while (attempts < MAX_PIN_ATTEMPTS) {
            System.out.print("Enter your 4-digit PIN: ");
            String enteredPin = scanner.nextLine();

            if (!enteredPin.matches("\\d{4}")) {
                System.out.println("Invalid PIN format. Must be exactly 4 digits.");
            } else if (enteredPin.equals(CORRECT_PIN)) {
                return true;
            } else {
                System.out.println("Incorrect PIN.");
            }
            attempts++;
        }
        return false;
    }

    private void runEventLoop(Scanner scanner) {
        int choice;
        do {
            printMenu();
            choice = getUserChoice(scanner);
            switch (choice) {
                case 1 -> handleCheckBalance();
                case 2 -> handleDeposit(scanner);
                case 3 -> handleWithdraw(scanner);
                case 4 -> System.out.println("Thank you for using the ATM. Goodbye!");
                default -> System.out.println("Invalid choice. Please select from 1 to 4.");
            }
        } while (choice != 4);
    }

    private static void printMenu() {
        System.out.println("\n--- ATM MENU ---");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Exit");
    }

    private static int getUserChoice(Scanner scanner) {
        System.out.print("Enter your choice: ");
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void handleCheckBalance() {
        System.out.printf("Current balance: $%.2f\n", balance);
    }

    private void handleDeposit(Scanner scanner) {
        System.out.print("Enter amount to deposit (or type 'cancel' to return): $");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("cancel")) {
            System.out.println("Deposit cancelled.");
            return;
        }

        try {
            double amount = Double.parseDouble(input);
            if (isValidAmount(amount)) {
                balance += amount;
                System.out.printf("$%.2f deposited successfully.\n", amount);
            } else {
                System.out.printf("Invalid deposit amount. Must be between $%.2f and $%.2f.\n",
                        MIN_TRANSACTION_AMOUNT, MAX_TRANSACTION_LIMIT);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric amount.");
        }
    }

    private void handleWithdraw(Scanner scanner) {
        System.out.print("Enter amount to withdraw (or type 'cancel' to return): $");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("cancel")) {
            System.out.println("Withdrawal cancelled.");
            return;
        }

        try {
            double amount = Double.parseDouble(input);
            if (!isValidAmount(amount)) {
                System.out.printf("Invalid withdraw amount. Must be between $%.2f and $%.2f.\n",
                        MIN_TRANSACTION_AMOUNT, MAX_TRANSACTION_LIMIT);
            } else if (amount > balance) {
                System.out.println("Insufficient funds.");
            } else {
                balance -= amount;
                System.out.printf("$%.2f withdrawn successfully.\n", amount);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric amount.");
        }
    }

    private boolean isValidAmount(double amount) {
        return amount >= MIN_TRANSACTION_AMOUNT && amount <= MAX_TRANSACTION_LIMIT;
    }
}


   
   
