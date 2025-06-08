import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * SimpleATM simulates a basic ATM interface with core functionalities:
 * check balance, deposit, and withdraw.
 */
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
        Scanner scanner = new Scanner(System.in);
        SimpleATM atm = new SimpleATM(1000.0);

        System.out.println("Welcome to the ATM!");

        if (authenticateUser(scanner)) {
            runATM(scanner, atm);
        } else {
            System.out.println("Too many failed attempts. Access denied.");
        }

        scanner.close();
    }

    private static boolean authenticateUser(Scanner scanner) {
        int attempts = 0;
        while (attempts < MAX_PIN_ATTEMPTS) {
            System.out.print("Enter your 4-digit PIN: ");
            String enteredPin = scanner.nextLine().trim();

            if (!validatePinFormat(enteredPin)) {
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

    private static void runATM(Scanner scanner, SimpleATM atm) {
        int choice = -1;
        while (choice != 4) {
            printMenu();
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1 -> atm.checkBalance();
                    case 2 -> atm.performDeposit(scanner);
                    case 3 -> atm.performWithdrawal(scanner);
                    case 4 -> System.out.println("Thank you for using the ATM. Goodbye!");
                    default -> System.out.println("Invalid choice. Please select from 1 to 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric choice.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- ATM MENU ---");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Exit");
    }

    private static boolean validatePinFormat(String pin) {
        return pin.matches("\\d{4}");
    }

    public void checkBalance() {
        System.out.printf("Current balance: $%.2f%n", balance);
    }

    public void performDeposit(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter amount to deposit: $");
                double amount = Double.parseDouble(scanner.nextLine().trim());
                if (isValidAmount(amount)) {
                    deposit(amount);
                    break;
                } else {
                    System.out.printf("Invalid deposit amount. Must be between $%.2f and $%.2f.%n",
                            MIN_TRANSACTION_AMOUNT, MAX_TRANSACTION_LIMIT);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    public void performWithdrawal(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Enter amount to withdraw: $");
                double amount = Double.parseDouble(scanner.nextLine().trim());
                if (!isValidAmount(amount)) {
                    System.out.printf("Invalid withdraw amount. Must be between $%.2f and $%.2f.%n",
                            MIN_TRANSACTION_AMOUNT, MAX_TRANSACTION_LIMIT);
                } else if (amount > balance) {
                    System.out.println("Insufficient funds.");
                } else {
                    withdraw(amount);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
    }

    private void deposit(double amount) {
        balance += amount;
        System.out.printf("$%.2f deposited successfully.%n", amount);
        checkBalance();
    }

    private void withdraw(double amount) {
        balance -= amount;
        System.out.printf("$%.2f withdrawn successfully.%n", amount);
        checkBalance();
    }

    private boolean isValidAmount(double amount) {
        return amount >= MIN_TRANSACTION_AMOUNT && amount <= MAX_TRANSACTION_LIMIT;
    }

    public double getBalance() {
        return balance;
    }
}
