package main.java;

import java.util.List;

public class TransactionCounter {

    public static int countTransactionsForMonth(List<Transaction> transactions, String monthYear) {
        int count = 0;

        for (Transaction transaction : transactions) {
            String date = transaction.getDate();
            String[] dateParts = date.split("-");

            // Check if date has expected format "DD-MM-YYYY"
            if (dateParts.length == 3) {
                String transactionMonthYear = dateParts[1] + "-" + dateParts[2]; // Extract "MM-YYYY"
                
                if (transactionMonthYear.equals(monthYear)) {
                    count++;
                }
            }
        }

        return count;
    }
}


