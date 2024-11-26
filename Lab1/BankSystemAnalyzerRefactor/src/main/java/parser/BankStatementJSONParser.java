package parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import model.BankTransaction;
import util.LocalDateAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BankStatementJSONParser implements BankStatementParser {

    @Override
    public BankTransaction parseFrom(String jsonString) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        return gson.fromJson(jsonString, BankTransaction.class);
    }

    @Override
    public List<BankTransaction> parseLinesFrom(List<String> lines) {
        List<BankTransaction> transactions = new ArrayList<>();

        // Concatenate all lines to handle a complete JSON string
        StringBuilder jsonStringBuilder = new StringBuilder();
        for (String line : lines) {
            jsonStringBuilder.append(line);
        }
        String jsonString = jsonStringBuilder.toString();

        // Parse JSON array
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();

        for (JsonElement element : jsonArray) {
            BankTransaction transaction = gson.fromJson(element, BankTransaction.class);
            transactions.add(transaction);
        }

        return transactions;
    }

    // Additional helper method for handling entire JSON content
    // public List<BankTransaction> parseFromJSON(String jsonString) {
    //     JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
    //     List<BankTransaction> transactions = new ArrayList<>();
        
    //     for (JsonElement element : jsonArray) {
    //         transactions.add(parseFrom(element.toString()));
    //     }

    //     return transactions;
    // }
}
