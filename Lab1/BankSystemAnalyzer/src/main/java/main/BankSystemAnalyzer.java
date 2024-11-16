package main.java.main;

import main.java.analyzer.*;
import main.java.model.Transaction;
import main.java.utility.FileReaderUtility;
import main.java.service.TransactionProcessor;

import java.util.*;

public class BankSystemAnalyzer {

    public static void main(String[] args) {
        String fileName = "transactions.csv";
        List<Transaction> transactions = FileReaderUtility.readTransactions(fileName);

        TransactionProcessor processor = new TransactionProcessor();
        processor.preprocessTransactions(transactions);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Bank System Analyzer ---");
            System.out.println("1. Display all transactions");
            System.out.println("2. Calculate total profit/loss");
            System.out.println("3. Count transactions in a specific month");
            System.out.println("4. Find top 10 expenses");
            System.out.println("5. Find category with the most spending");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    processor.displayAllTransactions();
                    break;
                case 2:
                    System.out.println("\nTotal Profit/Loss: " + ProfitLossCalculator.calculateTotalProfitLoss(transactions));
                    break;
                case 3:
                    System.out.print("Enter month (MM): ");
                    String month = scanner.nextLine().trim();
                    System.out.print("Enter year (YYYY): ");
                    String year = scanner.nextLine().trim();
                    String monthYear = month + "-" + year;
                    System.out.println("Number of transactions in " + monthYear + ": " +
                            TransactionCounter.countTransactionsForMonth(transactions, monthYear));
                    break;
                case 4:
                    ExpenseAnalyzer.displayTopExpenses(transactions);
                    break;
                case 5:
                    System.out.println("\nCategory with most spending: " +
                            CategoryAnalyzer.findCategoryWithMostSpending(transactions));
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
}
