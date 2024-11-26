package analyzer;

import java.time.Month;
import java.util.List;
import java.util.Map;

import model.BankTransaction;

public class BankStatementAnalyzer {

    private final List<BankTransaction> bankTransactions;

    public BankStatementAnalyzer(List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public void collectSummary() {
        if (bankTransactions == null || bankTransactions.isEmpty()) {
            System.out.println("No transactions available for analysis.");
            return;
        }

        BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);

        System.out.println("The total for all transactions is " + bankStatementProcessor.calculateTotalAmount());
        System.out.println("The total for transactions in January is " 
                + bankStatementProcessor.calculateTotalInMonth(Month.JANUARY));
        System.out.println("The total for transactions in February is " 
                + bankStatementProcessor.calculateTotalInMonth(Month.FEBRUARY));
        System.out.println("The total salary received is " 
                + bankStatementProcessor.calculateTotalForCategory("Salary"));
        System.out.println("Top 10 categories where the most money is spent:");
        
        List<Map.Entry<String, Double>> topCategories = bankStatementProcessor.getTop10Expenses();
        for (Map.Entry<String, Double> entry : topCategories) {
            System.out.println(entry.getKey() + ": -" + entry.getValue());
        }
    }
}
