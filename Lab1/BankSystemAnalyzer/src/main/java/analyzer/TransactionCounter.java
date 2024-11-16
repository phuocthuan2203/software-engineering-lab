package main.java.analyzer;

import main.java.model.Transaction;

import java.util.List;

public class TransactionCounter {

    public static int countTransactionsForMonth(List<Transaction> transactions, String monthYear) {
        return (int) transactions.stream()
                .filter(t -> t.getDate().contains(monthYear))
                .count();
    }
}
