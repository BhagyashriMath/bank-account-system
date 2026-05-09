package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String name;
    private int accountNumber;
    private double balance;
    private List<Transaction> transactions;

    public BankAccount(String name, int accountNumber, double initialDeposit) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = 0;
        this.transactions = new ArrayList<>();
        if (initialDeposit > 0) {
            deposit(initialDeposit);
        }
    }

    public String getName() {
        return name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            return;
        }
        balance += amount;
        transactions.add(new Transaction("Deposit", amount, balance));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            return;
        }
        if (amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", -amount, balance));
        } else {
            transactions.add(new Transaction("Withdrawal failed", 0, balance));
        }
    }

    public void transfer(BankAccount receiver, double amount) {
        if (amount <= 0 || receiver == null) {
            return;
        }
        if (amount <= balance) {
            balance -= amount;
            receiver.balance += amount;
            transactions.add(new Transaction("Transfer to " + receiver.accountNumber, -amount, balance));
            receiver.transactions.add(new Transaction("Received from " + this.accountNumber, amount, receiver.balance));
        } else {
            transactions.add(new Transaction("Transfer failed", 0, balance));
        }
    }
}
