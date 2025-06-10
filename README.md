## SimpleATM - Java Console Application

A basic command-line ATM simulation built in Java. This program supports user authentication, balance inquiry, deposit, and withdrawal functionality with error handling and input validation.



## Features:-
1. User Authentication: Secure 4-digit PIN authentication with limited attempts.
2. Balance Inquiry: View current account balance.
3. Deposit Money:Add funds to the account with input validation and limits.
4. Withdraw Money:Withdraw funds, ensuring sufficient balance and valid input.
5. Transaction History:View a detailed log of all transactions including deposits, withdrawals, failed attempts, and balance inquiries.



## Project Structure:-

1. SimpleATM.java      
2. README.md           


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
4. View Transaction History
5. Exit

 Enter your choice: 1                             
 Current balance: $1000.00


## Assumptions and Limitations
1. The initial balance is hardcoded to $1000.00 at startup.
2. The correct PIN is hardcoded as 1234.
3. Transactions are stored only in memory during runtime (no persistence).
4. Maximum transaction limit per deposit or withdrawal is $10,000.00.
5. Minimum transaction amount is $1.00.




## License:-
This project is free to use.


