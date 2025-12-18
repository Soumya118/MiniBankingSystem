import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MiniBankingGUI {
    private Bank bankSystem;
    private JFrame frame;
    private Account activeAccount;

    public MiniBankingGUI() {
        bankSystem = new Bank();
        showMainMenu();
    }

    private void showMainMenu() {
        frame = new JFrame("My Mini Banking System");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));

        JButton createBtn = new JButton("Open New Account");
        JButton loginBtn = new JButton("Login to Account");

        createBtn.addActionListener(e -> {
            frame.dispose();
            showCreateAccount();
        });

        loginBtn.addActionListener(e -> {
            frame.dispose();
            showLogin();
        });

        frame.add(new JLabel("Welcome to My Mini Banking System", SwingConstants.CENTER));
        frame.add(createBtn);
        frame.add(loginBtn);

        frame.setVisible(true);
    }

    private void showCreateAccount() {
        JFrame createFrame = new JFrame("Open Account");
        createFrame.setSize(400, 300);
        createFrame.setLayout(new GridLayout(5, 2));

        JTextField nameField = new JTextField();
        JPasswordField pinField = new JPasswordField();
        JTextField depositField = new JTextField();

        createFrame.add(new JLabel("Full Name:"));
        createFrame.add(nameField);
        createFrame.add(new JLabel("Choose PIN:"));
        createFrame.add(pinField);
        createFrame.add(new JLabel("Initial Deposit:"));
        createFrame.add(depositField);

        JButton submitBtn = new JButton("Open Account");
        JButton backBtn = new JButton("Back to Main Menu");

        submitBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String pin = new String(pinField.getPassword());
                double deposit = Double.parseDouble(depositField.getText());

                int accNum = bankSystem.generateAccountNumber();
                Account acc = new Account(accNum, name, pin, deposit);
                bankSystem.addNewAccount(acc);

                JOptionPane.showMessageDialog(createFrame, "Account Created!\nAccount Number: " + accNum);
                createFrame.dispose();
                showMainMenu();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(createFrame, "Invalid input! Try again.");
            }
        });

        backBtn.addActionListener(e -> {
            createFrame.dispose();
            showMainMenu();
        });

        createFrame.add(submitBtn);
        createFrame.add(backBtn);
        createFrame.setVisible(true);
    }

    private void showLogin() {
        JFrame loginFrame = new JFrame("Account Login");
        loginFrame.setSize(400, 200);
        loginFrame.setLayout(new GridLayout(3, 2));

        JTextField accField = new JTextField();
        JPasswordField pinField = new JPasswordField();

        loginFrame.add(new JLabel("Account Number:"));
        loginFrame.add(accField);
        loginFrame.add(new JLabel("PIN:"));
        loginFrame.add(pinField);

        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Back");

        loginBtn.addActionListener(e -> {
            try {
                int accNum = Integer.parseInt(accField.getText());
                String pin = new String(pinField.getPassword());
                activeAccount = bankSystem.getAccount(accNum, pin);

                if (activeAccount != null) {
                    loginFrame.dispose();
                    showAccountDashboard();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Incorrect Account Number or PIN!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(loginFrame, "Invalid input! Try again.");
            }
        });

        backBtn.addActionListener(e -> {
            loginFrame.dispose();
            showMainMenu();
        });

        loginFrame.add(loginBtn);
        loginFrame.add(backBtn);
        loginFrame.setVisible(true);
    }

    private void showAccountDashboard() {
        JFrame dashFrame = new JFrame("Account Dashboard");
        dashFrame.setSize(400, 300);
        dashFrame.setLayout(new GridLayout(6, 1));

        JLabel welcomeLabel = new JLabel("Welcome, " + activeAccount.holderName, SwingConstants.CENTER);
        JButton depositBtn = new JButton("Deposit Money");
        JButton withdrawBtn = new JButton("Withdraw Money");
        JButton balanceBtn = new JButton("Check Balance");
        JButton historyBtn = new JButton("Transaction History");
        JButton logoutBtn = new JButton("Logout");

        depositBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(dashFrame, "Enter amount to deposit:");
            try {
                double amount = Double.parseDouble(input);
                activeAccount.depositMoney(amount);
                JOptionPane.showMessageDialog(dashFrame, "Deposit Successful!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dashFrame, "Invalid input!");
            }
        });

        withdrawBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(dashFrame, "Enter amount to withdraw:");
            try {
                double amount = Double.parseDouble(input);
                if (activeAccount.withdrawMoney(amount)) {
                    JOptionPane.showMessageDialog(dashFrame, "Withdrawal Successful!");
                } else {
                    JOptionPane.showMessageDialog(dashFrame, "Insufficient Balance!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dashFrame, "Invalid input!");
            }
        });

        balanceBtn.addActionListener(e -> JOptionPane.showMessageDialog(dashFrame, "Current Balance: " + activeAccount.currentBalance));
        historyBtn.addActionListener(e -> JOptionPane.showMessageDialog(dashFrame, activeAccount.getTransactionHistory()));
        logoutBtn.addActionListener(e -> {
            activeAccount = null;
            dashFrame.dispose();
            showMainMenu();
        });

        dashFrame.add(welcomeLabel);
        dashFrame.add(depositBtn);
        dashFrame.add(withdrawBtn);
        dashFrame.add(balanceBtn);
        dashFrame.add(historyBtn);
        dashFrame.add(logoutBtn);

        dashFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new MiniBankingGUI();
    }
}
