<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bank.model.BankAccount" %>
<%@ page import="com.bank.model.Transaction" %>
<%
    String message = (String) request.getAttribute("message");
    List<BankAccount> accounts = (List<BankAccount>) request.getAttribute("accounts");
    if (accounts == null) {
        accounts = (List<BankAccount>) session.getAttribute("accounts");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bank Account System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 24px; }
        fieldset { margin-bottom: 20px; padding: 16px; }
        label { display: inline-block; width: 150px; margin-bottom: 8px; }
        input, select { width: 220px; }
        table { border-collapse: collapse; width: 100%; margin-top: 12px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #f4f4f4; }
        .message { padding: 10px; border: 1px solid #999; background: #f9f9f9; margin-bottom: 18px; }
    </style>
</head>
<body>
    <h1>Bank Account Web App</h1>
    <p>Use the form below to create accounts and process deposits, withdrawals, or transfers.</p>

    <% if (message != null && !message.isEmpty()) { %>
        <div class="message"><%= message %></div>
    <% } %>

    <form method="post" action="bank">
        <fieldset>
            <legend>Account Setup</legend>
            <label for="name">Account Holder Name</label>
            <input type="text" id="name" name="name" required /> <br />
            <label for="number">Account Number</label>
            <input type="number" id="number" name="number" required /> <br />
            <label for="initialDeposit">Initial Deposit</label>
            <input type="number" id="initialDeposit" name="initialDeposit" step="0.01" value="0" />
        </fieldset>

        <fieldset>
            <legend>Transaction</legend>
            <label for="action">Action</label>
            <select id="action" name="action">
                <option value="create">Create / Update Account</option>
                <option value="deposit">Deposit</option>
                <option value="withdraw">Withdraw</option>
                <option value="transfer">Transfer</option>
            </select><br />
            <label for="amount">Amount</label>
            <input type="number" id="amount" name="amount" step="0.01" value="0" /> <br />
            <label for="receiver">Receiver Account # (transfer only)</label>
            <input type="number" id="receiver" name="receiver" />
        </fieldset>

        <button type="submit">Submit</button>
    </form>

    <% if (accounts != null && !accounts.isEmpty()) { %>
        <h2>Account Statements</h2>
        <% for (com.bank.model.BankAccount account : accounts) { %>
            <h3><%= account.getName() %> (Account #: <%= account.getAccountNumber() %>)</h3>
            <p>Balance: <strong><%= String.format("%.2f", account.getBalance()) %></strong></p>
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Amount</th>
                        <th>Balance</th>
                    </tr>
                </thead>
                <tbody>
                <% for (com.bank.model.Transaction tx : account.getTransactions()) { %>
                    <tr>
                        <td><%= tx.getDate() %></td>
                        <td><%= tx.getDescription() %></td>
                        <td><%= String.format("%.2f", tx.getAmount()) %></td>
                        <td><%= String.format("%.2f", tx.getBalance()) %></td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        <% } %>
    <% } %>
</body>
</html>
