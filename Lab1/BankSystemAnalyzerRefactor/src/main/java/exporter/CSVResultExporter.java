package exporter;

import model.BankTransaction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVResultExporter implements ResultExporter {

    @Override
    public void export(List<BankTransaction> transactions, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("Date,Amount,Description\n");
            for (BankTransaction transaction : transactions) {
                writer.write(transaction.getDate() + "," + transaction.getAmount() + "," + transaction.getDescription() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
