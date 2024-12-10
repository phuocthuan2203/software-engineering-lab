package analyzer;

import model.BankTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankStatementProcessorTest {

    private BankStatementProcessor processor;

    @BeforeEach
    public void setUp() {
        List<BankTransaction> transactions = Arrays.asList(
                new BankTransaction(LocalDate.of(2023, 1, 10), 1000, "Salary"),
                new BankTransaction(LocalDate.of(2023, 1, 15), -200, "Groceries"),
                new BankTransaction(LocalDate.of(2023, 2, 1), -500, "Rent"),
                new BankTransaction(LocalDate.of(2023, 2, 10), 1500, "Freelance"),
                new BankTransaction(LocalDate.of(2023, 3, 5), -100, "Utilities"),
                new BankTransaction(LocalDate.of(2023, 3, 15), -300, "Groceries")
        );
        processor = new BankStatementProcessor(transactions);
    }

    @Test
    public void testCalculateTotalAmount() {
        // Act
        double totalAmount = processor.calculateTotalAmount();

        // Assert
        assertEquals(1400.0, totalAmount, 0.01);
    }

    @Test
    public void testCalculateTotalInMonth() {
        // Act
        double januaryTotal = processor.calculateTotalInMonth(Month.JANUARY);
        double februaryTotal = processor.calculateTotalInMonth(Month.FEBRUARY);
        double marchTotal = processor.calculateTotalInMonth(Month.MARCH);

        // Assert
        assertEquals(800.0, januaryTotal, 0.01);
        assertEquals(1000.0, februaryTotal, 0.01);
        assertEquals(-400.0, marchTotal, 0.01);
    }

    @Test
    public void testCalculateTotalForCategory() {
        // Act
        double salaryTotal = processor.calculateTotalForCategory("Salary");
        double groceriesTotal = processor.calculateTotalForCategory("Groceries");
        double rentTotal = processor.calculateTotalForCategory("Rent");
        double utilitiesTotal = processor.calculateTotalForCategory("Utilities");

        // Assert
        assertEquals(1000.0, salaryTotal, 0.01);
        assertEquals(-500.0, groceriesTotal, 0.01);
        assertEquals(-500.0, rentTotal, 0.01);
        assertEquals(-100.0, utilitiesTotal, 0.01);
    }
}
