## SimpleATM - Java Console Application

A basic command-line ATM simulation built in Java. This program supports user authentication, balance inquiry, deposit, and withdrawal functionality with error handling and input validation.



## Features:-
1. User PIN authentication (3 attempts)
2. Check account balance
3. Deposit money with validation
4. Withdraw money with balance checks
5. Input validation and error recovery
6. Simple, user-friendly console interface



## Project Structure:-

1. SimpleATM.java      # Main Java program
2. README.md           # Project documentation (this file)


## Requirements
1. Java Development Kit (JDK 11 or higher)
2. VS Code, IntelliJ, or any terminal with javac and java


## How to Run:-
1. Clone or Download the Repository
2. Compile the Code
3. Run the Program


## Sample Interaction:-
Welcome to the ATM!
Enter your 4-digit PIN: ****
--- ATM MENU ---
1. Check Balance
2. Deposit Money
3. Withdraw Money
4. Exit

5. Output:-
//5.1.Enter your choice: 1
//5.2.Current balance: $1000.00


## Configuration:-
You can update the initial balance or PIN directly in the SimpleATM.java file:
1. SimpleATM atm = new SimpleATM(1000.0);     // Set initial balance
2. private static final String CORRECT_PIN = "1234"; // Set custom PIN


## License:-
This project is free to use.


