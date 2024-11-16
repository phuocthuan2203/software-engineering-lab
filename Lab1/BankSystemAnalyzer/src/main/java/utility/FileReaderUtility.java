package main.java.utility;

import main.java.model.Transaction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtility {

    public static List<Transaction> readTransactions(String fileName) {
        List<Transaction> transactions = new ArrayList<>();

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new FileNotFoundException("File not found in classpath: " + fileName);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            // Process each line of the CSV
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                String date = values[0].trim();
                double amount = Double.parseDouble(values[1].trim());
                String category = values[2].trim();

                // Create and add a Transaction object
                transactions.add(new Transaction(date, amount, category));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load transactions from file: " + fileName);
        }

        return transactions;
    }
}
