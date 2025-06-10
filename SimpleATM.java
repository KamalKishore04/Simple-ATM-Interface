import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * A simple console-based ATM simulator with deposit, withdrawal,
 * balance check, and transaction history functionality.
 */
public class SimpleATM {
    private static final String CORRECT_PIN = "1234";
    private static final int MAX_PIN_ATTEMPTS = 3;
    public static final BigDecimal MAX_TRANSACTION_LIMIT = new BigDecimal("10000.00");
    public static final BigDecimal MIN_TRANSACTION_AMOUNT = new BigDecimal("1.00");
    public static final int DECIMAL_PLACES = 2;

    private BigDecimal balance;
    private final List<Transaction> transactionHistory;

    public SimpleATM(BigDecimal initialBalance) {
        this.balance = initialBalance.setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
        this.transactionHistory = new ArrayList<>();
        recordTransaction(TransactionType.INITIAL, initialBalance, "Initial balance setup");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            SimpleATM atm = new SimpleATM(new BigDecimal("1000.00"));
            System.out.println("Welcome to the ATM!");

            if (authenticateUser(scanner)) {
                runATM(scanner, atm);
            } else {
                System.out.println("Too many failed attempts. Access denied. Please contact your bank.");
            }
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
        }
    }

    private static boolean authenticateUser(Scanner scanner) {
        for (int attempts = 0; attempts < MAX_PIN_ATTEMPTS; attempts++) {
            System.out.print("Enter your 4-digit PIN: ");
            String enteredPin = scanner.nextLine().trim();

            if (enteredPin.isEmpty()) {
                System.out.println("PIN cannot be empty.");
            } else if (!validatePinFormat(enteredPin)) {
                System.out.println("Invalid format. PIN must be exactly 4 digits.");
            } else if (enteredPin.equals(CORRECT_PIN)) {
                System.out.println("PIN accepted. Welcome!");
                return true;
            } else {
                System.out.printf("Incorrect PIN. %d attempt(s) remaining.%n", MAX_PIN_ATTEMPTS - 1 - attempts);
            }
        }
        return false;
    }

    private static void runATM(Scanner scanner, SimpleATM atm) {
        while (true) {
            printMenu();
            int choice = readValidatedIntInput(scanner, "Enter your choice: ", 1, 5);
            switch (choice) {
                case 1 -> atm.checkBalance();
                case 2 -> atm.performDeposit(scanner);
                case 3 -> atm.performWithdrawal(scanner);
                case 4 -> atm.viewTransactionHistory();
                case 5 -> {
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                }
            }
        }
    }

    private static int readValidatedIntInput(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String inputLine = scanner.nextLine().trim();

            if (inputLine.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number.");
                continue;
            }

            try {
                int value = Integer.parseInt(inputLine);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.printf("Invalid choice. Please enter a number between %d and %d.%n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a numeric value (e.g., 1, 2, 3).");
            }
        }
    }

    private static BigDecimal readValidatedBigDecimalInput(Scanner scanner, String prompt, BigDecimal min, BigDecimal max) {
        while (true) {
            System.out.print(prompt);
            String inputLine = scanner.nextLine().trim();

            if (inputLine.isEmpty()) {
                System.out.println("Amount cannot be empty.");
                continue;
            }

            try {
                BigDecimal value = new BigDecimal(inputLine).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
                if (value.compareTo(min) >= 0 && value.compareTo(max) <= 0) {
                    return value;
                } else {
                    System.out.printf("Invalid amount. Must be between $%.2f and $%.2f.%n", min, max);
                }
            } catch (NumberFormatException | ArithmeticException e) {
                System.out.println("Invalid amount format. Use numbers like 50.00.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- ATM MENU ---");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. View Transaction History");
        System.out.println("5. Exit");
    }

    private static boolean validatePinFormat(String pin) {
        return pin.matches("\\d{4}");
    }

    public void checkBalance() {
        System.out.printf("Current balance: $%.2f%n", balance);
        recordTransaction(TransactionType.BALANCE_INQUIRY, null, "Checked balance");
    }

    public void performDeposit(Scanner scanner) {
        BigDecimal amount = readValidatedBigDecimalInput(scanner, "Enter deposit amount: $", MIN_TRANSACTION_AMOUNT, MAX_TRANSACTION_LIMIT);
        deposit(amount);
    }

    public void performWithdrawal(Scanner scanner) {
        BigDecimal amount = readValidatedBigDecimalInput(scanner, "Enter withdrawal amount: $", MIN_TRANSACTION_AMOUNT, MAX_TRANSACTION_LIMIT);

        if (amount.compareTo(balance) > 0) {
            System.out.printf("Insufficient funds. Available: $%.2f%n", balance);
            recordTransaction(TransactionType.WITHDRAWAL_FAILED, amount, "Attempted withdrawal with insufficient funds");
        } else {
            withdraw(amount);
        }
    }

    private void deposit(BigDecimal amount) {
        balance = balance.add(amount);
        System.out.printf("$%.2f deposited successfully.%n", amount);
        recordTransaction(TransactionType.DEPOSIT, amount, "Deposit");
        checkBalance();
    }

    private void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
        System.out.printf("$%.2f withdrawn successfully.%n", amount);
        recordTransaction(TransactionType.WITHDRAWAL, amount, "Withdrawal");
        checkBalance();
    }

    public void viewTransactionHistory() {
        System.out.println("\n--- TRANSACTION HISTORY ---");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            transactionHistory.forEach(System.out::println);
        }
    }

    private void recordTransaction(TransactionType type, BigDecimal amount, String description) {
        transactionHistory.add(new Transaction(type, amount, description));
    }
}

/**
 * Enum to represent the type of each transaction.
 */
enum TransactionType {
    INITIAL,
    DEPOSIT,
    WITHDRAWAL,
    BALANCE_INQUIRY,
    WITHDRAWAL_FAILED
}

/**
 * Class representing a transaction entry.
 */
class Transaction {
    private final LocalDateTime timestamp;
    private final TransactionType type;
    private final BigDecimal amount;
    private final String description;

    public Transaction(TransactionType type, BigDecimal amount, String description) {
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = (amount != null) ? amount.setScale(SimpleATM.DECIMAL_PLACES, RoundingMode.HALF_UP) : null;
        this.description = description;
    }

    @Override
    public String toString() {
        String amountStr = (amount != null) ? String.format("$%.2f", amount) : "N/A";
        return String.format("[%s] %-18s %-10s %s",
                timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                type, amountStr, description);
    }
}
