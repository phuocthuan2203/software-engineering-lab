package main.java.analyzer;

import main.java.model.Transaction;

import java.util.List;

public class ProfitLossCalculator {

    public static String calculateTotalProfitLoss(List<Transaction> transactions) {
        double totalProfit = 0.0;
        double totalLoss = 0.0;

        for (Transaction transaction : transactions) {
            double amount = transaction.getAmount();
            if (amount > 0) totalProfit += amount;
            else totalLoss += amount;
        }

        double netBalance = totalProfit + totalLoss;
        if (netBalance > 0) return "Positive " + netBalance;
        else if (netBalance < 0) return "Negative " + netBalance;
        return "Zero Balance";
    }
}
