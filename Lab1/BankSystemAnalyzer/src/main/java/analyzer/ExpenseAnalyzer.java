package main.java.analyzer;

import main.java.model.Transaction;

import java.util.Comparator;
import java.util.List;

public class ExpenseAnalyzer {

    public static void displayTopExpenses(List<Transaction> transactions) {
        transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparingDouble(Transaction::getAmount))
                .limit(10)
                .forEach(t -> System.out.println(t.getDate() + " | " + t.getAmount() + " | " + t.getCategory()));
    }
}
