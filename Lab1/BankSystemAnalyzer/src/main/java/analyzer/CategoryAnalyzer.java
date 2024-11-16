package main.java.analyzer;

import main.java.model.Transaction;

import java.util.*;

public class CategoryAnalyzer {

    public static String findCategoryWithMostSpending(List<Transaction> transactions) {
        Map<String, Double> categorySpending = new HashMap<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                String category = transaction.getCategory();
                double amount = Math.abs(transaction.getAmount());
                categorySpending.put(category, categorySpending.getOrDefault(category, 0.0) + amount);
            }
        }

        return categorySpending.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey() + " with " + entry.getValue())
                .orElse("No expenses found");
    }
}
