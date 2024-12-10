package parser;

import model.BankTransaction;
import org.junit.jupiter.api.Test;
import util.DateUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankStatementCSVParserTest {

    @Test
    
    public void testParseFrom() {
        // Arrange
        final String line = "30-01-2017,-100,Deliveroo";
        final BankStatementCSVParser parser = new BankStatementCSVParser();

        // Act
        final BankTransaction result = parser.parseFrom(line);

        // Assert
        assertEquals(LocalDate.of(2017, 1, 30), result.getDate());
        assertEquals(-100.0, result.getAmount());
        assertEquals("Deliveroo", result.getDescription());
    }

    @Test
    public void testParseLinesFrom() {
        // Arrange
        final List<String> lines = Arrays.asList(
                "30-01-2017,-100,Deliveroo",
                "30-01-2017,-50,Tesco",
                "01-02-2017,6000,Salary",
                "02-02-2017,2000,Royalties",
                "02-02-2017,-4000,Rent",
                "03-02-2017,3000,Tesco"
        );
        final BankStatementCSVParser parser = new BankStatementCSVParser();

        // Act
        final List<BankTransaction> results = parser.parseLinesFrom(lines);

        // Assert
        assertEquals(6, results.size());

        // Validate first transaction
        BankTransaction transaction1 = results.get(0);
        assertEquals(LocalDate.of(2017, 1, 30), transaction1.getDate());
        assertEquals(-100.0, transaction1.getAmount());
        assertEquals("Deliveroo", transaction1.getDescription());

        // Validate last transaction
        BankTransaction transaction6 = results.get(5);
        assertEquals(LocalDate.of(2017, 2, 3), transaction6.getDate());
        assertEquals(3000.0, transaction6.getAmount());
        assertEquals("Tesco", transaction6.getDescription());
    }

    @Test
    public void testParseFullDataset() {
        // Arrange
        final List<String> lines = Arrays.asList(
                "30-01-2017,-100,Deliveroo",
                "30-01-2017,-50,Tesco",
                "01-02-2017,6000,Salary",
                "02-02-2017,2000,Royalties",
                "02-02-2017,-4000,Rent",
                "03-02-2017,3000,Tesco",
                "04-02-2017,-3000,Shopping",
                "05-02-2017,-30,Cinema",
                "15-03-2017,1500,Freelance",
                "25-03-2017,-1200,Car Insurance",
                "10-04-2017,-500,Internet Bill",
                "12-04-2017,4500,Salary",
                "01-05-2017,3000,Freelance",
                "15-05-2017,-1000,Groceries",
                "20-06-2017,-300,Utilities",
                "05-07-2017,5000,Salary",
                "10-07-2017,-1000,Shopping",
                "01-08-2017,-2000,Travel",
                "05-08-2017,2500,Freelance",
                "10-09-2017,-1500,Car Loan",
                "01-10-2017,7000,Salary",
                "10-11-2017,-400,Entertainment",
                "25-12-2017,-3000,Shopping"
        );
        final BankStatementCSVParser parser = new BankStatementCSVParser();

        // Act
        final List<BankTransaction> results = parser.parseLinesFrom(lines);

        // Assert
        assertEquals(23, results.size());

        // Validate specific transactions
        BankTransaction firstTransaction = results.get(0);
        assertEquals(LocalDate.of(2017, 1, 30), firstTransaction.getDate());
        assertEquals(-100.0, firstTransaction.getAmount());
        assertEquals("Deliveroo", firstTransaction.getDescription());

        BankTransaction lastTransaction = results.get(22);
        assertEquals(LocalDate.of(2017, 12, 25), lastTransaction.getDate());
        assertEquals(-3000.0, lastTransaction.getAmount());
        assertEquals("Shopping", lastTransaction.getDescription());
    }
}
