package main.java.service;

import main.java.model.Transaction;

import java.util.List;

public class TransactionProcessor {
    private List<Transaction> transactions;

    public void preprocessTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void displayAllTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getDate() + " | " + transaction.getAmount() + " | " + transaction.getCategory());
        }
    }
}
