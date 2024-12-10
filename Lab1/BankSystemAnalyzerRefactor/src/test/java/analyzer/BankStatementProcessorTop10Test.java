package analyzer;

import model.BankTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankStatementProcessorTop10Test {

    private BankStatementProcessor processor;

    @BeforeEach
    public void setUp() {
        List<BankTransaction> transactions = Arrays.asList(
            new BankTransaction(LocalDate.of(2023, 1, 10), -100, "Groceries"),
            new BankTransaction(LocalDate.of(2023, 1, 15), -300, "Rent"),
            new BankTransaction(LocalDate.of(2023, 2, 1), -200, "Shopping"),
            new BankTransaction(LocalDate.of(2023, 2, 10), -50, "Utilities"),
            new BankTransaction(LocalDate.of(2023, 3, 5), -400, "Groceries"),
            new BankTransaction(LocalDate.of(2023, 3, 15), -150, "Entertainment"),
            new BankTransaction(LocalDate.of(2023, 4, 5), -120, "Transport"),
            new BankTransaction(LocalDate.of(2023, 4, 15), -80, "Groceries"),
            new BankTransaction(LocalDate.of(2023, 5, 10), -500, "Rent"),
            new BankTransaction(LocalDate.of(2023, 6, 1), -90, "Transport"),
            new BankTransaction(LocalDate.of(2023, 6, 15), -250, "Shopping"),
            new BankTransaction(LocalDate.of(2023, 7, 1), -300, "Rent")
        );
        processor = new BankStatementProcessor(transactions);
    }

    @Test
    public void testGetTop10Expenses() {
        // Act
        List<Map.Entry<String, Double>> top10Expenses = processor.getTop10Expenses();

        // Assert
        assertEquals(6, top10Expenses.size()); // Adjusted to match the dataset size
        assertEquals("Rent", top10Expenses.get(0).getKey()); // Highest expense should be "Rent"
        assertEquals(1100.0, top10Expenses.get(0).getValue(), 0.01); // Total for "Rent" across all transactions
    }

}
