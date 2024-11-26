package exporter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.BankTransaction;
import util.LocalDateAdapter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class JSONResultExporter implements ResultExporter {

    @Override
    public void export(List<BankTransaction> transactions, String outputPath) {
        // Register the LocalDate adapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter(outputPath)) {
            gson.toJson(transactions, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
