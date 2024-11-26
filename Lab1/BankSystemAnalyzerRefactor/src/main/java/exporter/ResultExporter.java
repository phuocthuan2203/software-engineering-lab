package exporter;

import model.BankTransaction;

import java.util.List;

public interface ResultExporter {
    void export(List<BankTransaction> transactions, String outputPath);
}
