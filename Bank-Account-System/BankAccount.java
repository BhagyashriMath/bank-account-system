import java.util.*;

class BankAccount {
    private String name;
    private int accountNumber;
    private double balance;
    private List<Transaction> transactions;

    public BankAccount(String name, int accountNumber) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = 0;
        transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount, balance));
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", -amount, balance));
        } else {
            System.out.println("Insufficient Balance!");
        }
    }

    public void transfer(BankAccount receiver, double amount) {
        if (amount <= balance) {
            balance -= amount;
            receiver.balance += amount;

            transactions.add(new Transaction("Transfer to " + receiver.accountNumber, -amount, balance));
            receiver.transactions.add(new Transaction("Received from " + this.accountNumber, amount, receiver.balance));
        } else {
            System.out.println("Transfer Failed!");
        }
    }

    public void showStatement() {
        System.out.println("\nAccount Holder: " + name);
        System.out.println("Account Number: " + accountNumber);
        System.out.printf("%-25s %-20s %-10s %-10s\n", "Date", "Description", "Amount", "Balance");

        for (Transaction t : transactions) {
            t.display();
        }

        System.out.println("Final Balance: " + balance);
    }
}