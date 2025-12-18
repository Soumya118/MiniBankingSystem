import java.util.ArrayList;

public class Bank {
    ArrayList<Account> allAccounts;

    public Bank() {
        allAccounts = new ArrayList<>();
    }

    // Add a new account
    public void addNewAccount(Account acc) {
        allAccounts.add(acc);
    }

    // Find an account using account number and PIN
    public Account getAccount(int accNumber, String pinCode) {
        for (Account acc : allAccounts) {
            if (acc.accNumber == accNumber && acc.pinCode.equals(pinCode)) {
                return acc;
            }
        }
        return null;
    }

    // Generate unique account number
    public int generateAccountNumber() {
        return 1000 + allAccounts.size() + 1;
    }
}
