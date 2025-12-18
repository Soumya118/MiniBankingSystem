import java.util.ArrayList;

public class Account {
    int accNumber;                 // Unique account number
    String holderName;             // Name of the account holder
    String pinCode;                // PIN for login
    double currentBalance;         // Current balance
    ArrayList<String> transactionLog; // Stores all transaction details

    // Constructor to create a new account
    public Account(int accNumber, String holderName, String pinCode, double openingBalance) {
        this.accNumber = accNumber;
        this.holderName = holderName;
        this.pinCode = pinCode;
        this.currentBalance = openingBalance;
        transactionLog = new ArrayList<>();
        transactionLog.add("Account opened with balance: " + openingBalance);
    }

    // Deposit money into the account
    public void depositMoney(double amount) {
        currentBalance += amount;
        transactionLog.add("Deposited: " + amount);
    }

    // Withdraw money from the account
    public boolean withdrawMoney(double amount) {
        if (amount <= currentBalance) {
            currentBalance -= amount;
            transactionLog.add("Withdrawn: " + amount);
            return true;
        } else {
            transactionLog.add("Failed withdrawal attempt: " + amount + " (Insufficient balance)");
            return false;
        }
    }

    // Get full transaction history
    public String getTransactionHistory() {
        StringBuilder history = new StringBuilder();
        for (String entry : transactionLog) {
            history.append(entry).append("\n");
        }
        return history.toString();
    }

    // Optional: Quick summary of account info
    public String accountSummary() {
        return "Account Number: " + accNumber + "\n" +
                "Holder Name: " + holderName + "\n" +
                "Balance: " + currentBalance;
    }
}

