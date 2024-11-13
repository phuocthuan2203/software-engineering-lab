package main.java;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryAnalyzer {
    public static String findCategoryWithMostSpending(List<Transaction> transactions) {
        Map<String, Double> categorySpending = new HashMap<>();

        // Aggregate spending by category
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) { // Only consider expenses (negative amounts)
                String category = transaction.getCategory();
                double amount = Math.abs(transaction.getAmount()); // Make amount positive for easier sum

                categorySpending.put(category, categorySpending.getOrDefault(category, 0.0) + amount);
            }
        }

        // Find the category with the most spending
        String topCategory = null;
        double maxSpending = 0.0;

        for (Map.Entry<String, Double> entry : categorySpending.entrySet()) {
            if (entry.getValue() > maxSpending) {
                maxSpending = entry.getValue();
                topCategory = entry.getKey();
            }
        }

        return topCategory + " with " + maxSpending;
    }
}


