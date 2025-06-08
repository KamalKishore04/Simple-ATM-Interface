# SimpleATM

A simple console-based Java application that simulates the core functionalities of an ATM (Automated Teller Machine).

## 🚀 Features

- ✅ Secure 4-digit PIN authentication (with 3 attempts)
- 💰 Balance inquiry
- ➕ Deposit money (with validation and max limit)
- ➖ Withdraw money (with validation and balance check)
- 🔁 Robust error handling and graceful user interaction
- ❌ Action cancellation (e.g., deposit or withdrawal)

## 🧰 Technologies Used

- Java (JDK 8 or later)
- Console I/O using `Scanner`
- Exception handling for robust user interaction

## 📋 Usage

1. **Compile the code:**
   ```bash
   javac SimpleATM.java
   ```

2. **Run the application:**
   ```bash
   java SimpleATM
   ```

3. **Default PIN:** `1234`

## 📦 Structure

- `SimpleATM.java` - Contains all logic including authentication, balance operations, and user event handling.

## 🛡️ Validation & Error Handling

- PIN must be exactly 4 digits.
- Transaction amounts must be between $1.00 and $10,000.00.
- Handles invalid inputs using `try-catch` blocks.
- Graceful exit on invalid attempts or user cancellation.

## 📌 Guidelines Followed

- ✔ Core Feature Implementation
- ✔ Error Handling & Robustness
- ✔ Integration of Components
- ✔ Event Handling & Processing
- ✔ Data Validation
- ✔ Code Quality & Innovation

## 📄 License

This project is open source and free to use.
