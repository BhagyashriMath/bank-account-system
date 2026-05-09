package com.bank.servlet;

import com.bank.model.BankAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BankSystemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        List<BankAccount> accounts = (List<BankAccount>) session.getAttribute("accounts");
        if (accounts == null) {
            accounts = new ArrayList<>();
            session.setAttribute("accounts", accounts);
        }

        String name = request.getParameter("name");
        String numberText = request.getParameter("number");
        String initialDepositText = request.getParameter("initialDeposit");
        String action = request.getParameter("action");
        String amountText = request.getParameter("amount");
        String receiverText = request.getParameter("receiver");

        double initialDeposit = 0;
        double amount = 0;
        int number = 0;
        int receiverNumber = 0;

        String message = null;

        try {
            number = Integer.parseInt(numberText);
        } catch (NumberFormatException ignored) {
        }

        try {
            initialDeposit = Double.parseDouble(initialDepositText);
        } catch (NumberFormatException ignored) {
        }

        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException ignored) {
        }

        try {
            receiverNumber = Integer.parseInt(receiverText);
        } catch (NumberFormatException ignored) {
        }

        BankAccount account = findAccount(accounts, number);

        if (action == null || action.equals("create")) {
            if (account == null) {
                account = new BankAccount(name, number, initialDeposit);
                accounts.add(account);
                message = "Created account for " + name + ".";
            } else {
                if (initialDeposit > 0) {
                    account.deposit(initialDeposit);
                }
                message = "Updated account " + number + ".";
            }
        } else if (account == null) {
            message = "Account not found: " + number + ". Create it first.";
        } else {
            if (action.equals("deposit")) {
                account.deposit(amount);
                message = "Deposited " + amount + " to " + number + ".";
            } else if (action.equals("withdraw")) {
                account.withdraw(amount);
                message = "Processed withdrawal for " + number + ".";
            } else if (action.equals("transfer")) {
                BankAccount receiver = findAccount(accounts, receiverNumber);
                if (receiver == null) {
                    receiver = new BankAccount("Receiver", receiverNumber, 0);
                    accounts.add(receiver);
                }
                account.transfer(receiver, amount);
                message = "Transferred " + amount + " from " + number + " to " + receiverNumber + ".";
            }
        }

        request.setAttribute("message", message);
        request.setAttribute("accounts", accounts);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private BankAccount findAccount(List<BankAccount> accounts, int accountNumber) {
        if (accounts == null) {
            return null;
        }
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }
}
