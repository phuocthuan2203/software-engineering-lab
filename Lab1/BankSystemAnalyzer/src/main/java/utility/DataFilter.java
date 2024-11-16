package main.java.utility;

import java.io.*;
import java.util.*;

import main.java.model.Transaction;

public class DataFilter {

    public static List<Transaction> readTransactionsFromFile(String fileName) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String date = values[0].trim();
                double amount = Double.parseDouble(values[1].trim());
                String category = values[2].trim();
                transactions.add(new Transaction(date, amount, category));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}

