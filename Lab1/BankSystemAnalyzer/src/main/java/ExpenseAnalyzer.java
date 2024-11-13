package main.java;

import java.util.*;
import java.util.stream.*;

public class ExpenseAnalyzer {

    public static List<Transaction> findTopExpenses(List<Transaction> transactions) {
        // Filter out all positive amounts, as we are only interested in expenses
        return transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .sorted(Comparator.comparingDouble(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }
}

