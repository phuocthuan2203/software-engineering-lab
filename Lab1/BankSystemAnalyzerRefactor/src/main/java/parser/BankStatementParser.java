package main.java.parser;

import java.util.List;

import main.java.model.BankTransaction;

public interface BankStatementParser {
    BankTransaction parseFrom(String line);
    List<BankTransaction> parseLinesFrom(List<String> lines);
}
