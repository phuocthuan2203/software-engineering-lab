package main.java;

public class Transaction {
    private String date;
    private double amount;
    private String category;

    public Transaction(String date, double amount, String category) {
        this.date = date;
        this.amount = amount;
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }
}

