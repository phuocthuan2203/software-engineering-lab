package main.java.parser;

import main.java.model.BankTransaction;
import main.java.util.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BankStatementCSVParser implements BankStatementParser {
    @Override
    public BankTransaction parseFrom(final String line) {
        final String[] columns = line.split(",");
        final LocalDate date = DateUtils.parseDate(columns[0]);
        final double amount = Double.parseDouble(columns[1]);
        final String description = columns[2];
        return new BankTransaction(date, amount, description);
    }

    @Override
    public List<BankTransaction> parseLinesFrom(final List<String> lines) {
        final List<BankTransaction> bankTransactions = new ArrayList<>();
        for (final String line : lines) {
            bankTransactions.add(parseFrom(line));
        }
        return bankTransactions;
    }
}