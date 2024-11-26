import analyzer.BankStatementAnalyzer;
import analyzer.TransactionSearchService;
import exporter.ExporterFactory;
import exporter.ResultExporter;
import model.BankTransaction;
import model.SearchCriteria;
import parser.BankStatementParser;
import parser.ParserFactory;
import util.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MainApplication {
    public static void main(final String... args) throws IOException {
        final Scanner scanner = new Scanner(System.in);

        // Ensure input and output directories exist
        Config.ensureInputDirectoryExists();
        Config.ensureOutputDirectoryExists();

        try {
            // Step 1: Choose Input File Extension
            System.out.println("Please enter the input file format (csv, json, xml): ");
            String inputFormat = scanner.nextLine().trim().toLowerCase();

            // Get the input file path automatically
            String inputFilePath = Config.getInputFilePath(inputFormat);
            System.out.println("Input file being processed: " + inputFilePath);

            // Step 2: Parse the Input File
            final BankStatementParser parser = ParserFactory.getParser(inputFormat);
            final List<String> fileContent = Files.readAllLines(Paths.get(inputFilePath));
            final List<BankTransaction> transactions = parser.parseLinesFrom(fileContent);
            System.out.println("Transactions parsed successfully.");

            // Step 3: Get Filtering Criteria
            SearchCriteria criteria = new SearchCriteria();
            System.out.println("Do you want to filter transactions by start date? (yes/no)");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                System.out.println("Enter start date (dd-MM-yyyy): ");
                String startDateStr = scanner.nextLine().trim();
                LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                criteria.setStartDate(startDate);
            }

            System.out.println("Do you want to filter transactions by end date? (yes/no)");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                System.out.println("Enter end date (dd-MM-yyyy): ");
                String endDateStr = scanner.nextLine().trim();
                LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                criteria.setEndDate(endDate);
            }

            System.out.println("Do you want to filter transactions by category? (yes/no)");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                System.out.println("Enter category: ");
                String category = scanner.nextLine().trim();
                criteria.setCategory(category);
            }

            System.out.println("Do you want to filter transactions by minimum amount? (yes/no)");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                System.out.println("Enter minimum amount: ");
                try {
                    double minAmount = Double.parseDouble(scanner.nextLine().trim());
                    criteria.setMinAmount(minAmount);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid minimum amount entered. It should be a number.");
                }
            }

            System.out.println("Do you want to filter transactions by maximum amount? (yes/no)");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                System.out.println("Enter maximum amount: ");
                try {
                    double maxAmount = Double.parseDouble(scanner.nextLine().trim());
                    criteria.setMaxAmount(maxAmount);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid maximum amount entered. It should be a number.");
                }
            }

            // Step 4: Filter Transactions Based on Criteria
            TransactionSearchService searchService = new TransactionSearchService();
            List<BankTransaction> filteredTransactions = searchService.searchByConditions(transactions, criteria);
            System.out.println("Filtered transactions count: " + filteredTransactions.size());

            // Step 5: Perform Analysis on Filtered Transactions
            final BankStatementAnalyzer bankStatementAnalyzer = new BankStatementAnalyzer(filteredTransactions);
            bankStatementAnalyzer.collectSummary();

            // Step 6: Choose Output Format and Export Results
            System.out.println("Please enter the desired export format (csv, json, xml): ");
            String outputFormat = scanner.nextLine().trim().toLowerCase();

            String outputFileName = "filtered_transactions." + outputFormat;
            String outputFilePath = Config.OUTPUT_DIRECTORY + outputFileName;

            ResultExporter exporter = ExporterFactory.getExporter(outputFormat);
            exporter.export(filteredTransactions, outputFilePath);
            System.out.println("Filtered transactions exported successfully to: " + outputFilePath);

        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
