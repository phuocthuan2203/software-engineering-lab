package main.java;

import java.util.*;

public class BankSystemAnalyzer {

    public static void main(String[] args) {
        // Load transactions
        String fileName = "resources/transactions.csv"; // Path to CSV file
        List<Transaction> transactions = DataFilter.readTransactionsFromFile(fileName);
        
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Main menu loop
        while (!exit) {
            // Display menu options
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
                    displayAllTransactions(transactions);
                    break;
                case 2:
                    calculateProfitLoss(transactions);
                    break;
                case 3:
                    countTransactionsInMonth(transactions, scanner);
                    break;
                case 4:
                    displayTopExpenses(transactions);
                    break;
                case 5:
                    displayCategoryWithMostSpending(transactions);
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void displayAllTransactions(List<Transaction> transactions) {
        System.out.println("\nAll Transactions:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getDate() + " | " + transaction.getAmount() + " | " + transaction.getCategory());
        }
    }

    private static void calculateProfitLoss(List<Transaction> transactions) {
        String profitLoss = ProfitLossCalculator.calculateTotalProfitLoss(transactions);
        System.out.println("\nTotal Profit/Loss: " + profitLoss);
    }

    private static void countTransactionsInMonth(List<Transaction> transactions, Scanner scanner) {
        System.out.print("Enter month (MM): ");
        String month = scanner.nextLine().trim();
        
        System.out.print("Enter year (YYYY): ");
        String year = scanner.nextLine().trim();
        
        String monthYear = month + "-" + year;
        int transactionCount = TransactionCounter.countTransactionsForMonth(transactions, monthYear);
        System.out.println("Number of transactions in " + monthYear + ": " + transactionCount);
    }

    private static void displayTopExpenses(List<Transaction> transactions) {
        List<Transaction> topExpenses = ExpenseAnalyzer.findTopExpenses(transactions);
        System.out.println("\nTop 10 Expenses:");
        for (Transaction t : topExpenses) {
            System.out.println(t.getDate() + " | " + t.getAmount() + " | " + t.getCategory());
        }
    }

    private static void displayCategoryWithMostSpending(List<Transaction> transactions) {
        String topCategory = CategoryAnalyzer.findCategoryWithMostSpending(transactions);
        System.out.println("\nCategory with most spending: " + topCategory);
    }
}
