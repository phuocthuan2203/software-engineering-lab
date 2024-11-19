## Project Structure with UML
![PlantUML Diagram 1](https://www.planttext.com/api/plantuml/png/nLNFRzem5Bxdh_0nqoZQxXKgerIQLDQfeRjCFJmS0owsNsOxrLZL_tsEuu0DeNIdvH3Cy_U-z_lbobZGjfOYoyo6AwBHi2ohW6vWnOW4hePFy0n3K22sVvWcBnbn3nLW3Fa2QbDOi4moPSSfedakY6Etd98bTvUai9gh5RcV5dSFzvDfiSDz9C_8In9iv06tv89z0ya6gPtle8spiZYMVOW8Ws7FIr48HcrHImbwcwTi6Yap1Wy9Et7B-PgTYdgl7uTzouszR71p3Se0jHpLo5k812PojvypVnzF77U9zK8mGa7GMZY-ELeGOucrilbPt5SrGsNNkNyJsRpFwjoWdhZp2brcG-5e-1_h-aXcK0sdokhjPOiSa6jFEHgH5RDph3v_clwkc3BCv5tYN_kwKMB9n54hHZb9gu7EvMld52dTAxe80O5FJoGFSPJCKCshXk-WL96bF6KVfAI37guaBJjbbu3cjwz8xggdWe6uqTjpzo4ijeVaRe4e62Z2VjKWJ7wtU78JGZ26S6N96inwufBzTZcgTYJRd3irffT0MU_OlfpE9F4qDnfbrqY2AtRKH4VZuDLk7THuNGzg2KQUmPoRuKdn8tZ7PIMyw73A_x_plOclBK_7e6cX1oSqYQzX6UwRpLlrxTQrfpktnvubskrZr1BQ9arx_YqN4-zcJZgkAi7TXaY7D5tOZNRmxESZ0RsAiz3_qRcu69rOekzMy3NEJcpt3XuLBplHHFy8tPKiEqpFaOkTyzaLKsNppVuB)

## Problem

1. **Writing all functionalities within a single class leads to poor code quality.**
2. **Code Example:**

    ```csharp
    public class BankTransactionAnalyzerSimple {
        private static final String RESOURCES = "src/main/resources/";
        public static void main(final String... args) throws IOException {
            final Path path = Paths.get(RESOURCES + args[0]);
            
            final List<String> lines = Files.readAllLines(path);
            
            double total = 0d;
            for(final String line: lines) {
                final String[] columns = line.split(",");
                final double amount = Double.parseDouble(columns[1]);
                total += amount;
            }
            System.out.println("The total for all transactions is " + total);
        }
    }
    ```

## Consequences: Anti-Patterns

### 1. **God Class:**
  - A single class takes on too many responsibilities.
  - Reading the code makes it difficult to understand what the class does.
### 2. **Code Duplication:**
  - Performing the same task, such as reading a file and parsing lines.
  - If the code is hard-coded and copy-pasted across multiple classes, future changes require manual updates to all hard-coded places.
  - For example, if later you need to read XML or JSON files instead of CSV, hard-coding prevents flexibility and increases the need to modify the code when new requirements arise.

## Principles to Address Anti-Patterns

### 1. Single Responsibility Principle (SRP)

  - **Definition:** A class should have only one responsibility related to a single feature, and it should have only one reason to change.
    - **Example:** Separate the parsing logic into its own class.
    - If you need to use the function to parse each line in a CSV file within a class, you should only care about what it returns and whether you can use it. The implementation should reside in another class.
    - **Benefit:** Separation of concerns allows you to modify only one part of the code when requirements change.
  - **Code Example:**

    ```csharp
    public class BankStatementCSVParser : IBankStatementParser {
        @Override
        public BankTransaction ParseFrom(string line) {
            string[] columns = line.Split(",");
            DateTime date = DateUtils.ParseDate(columns[0]);
            double amount = double.Parse(columns[1]);
            string description = columns[2];
            return new BankTransaction(date, amount, description);
        }

        @Override
        public List<BankTransaction> ParseLinesFrom(List<string> lines) {
            List<BankTransaction> bankTransactions = new List<BankTransaction>();
            foreach (string line in lines) {
                bankTransactions.Add(ParseFrom(line));
            }
            return bankTransactions;
        }
    }
    ```

### 2. Cohesion

  - **Definition:** The way elements are related to each other, from class-level to method-level.
  
  - **Class-Level Cohesion:**
    - Grouping methods based on criteria such as functional, informational, or utility.
    - **Example:** Grouping related calculation operations into a class.

    ```csharp
    public class BankStatementProcessor {
        private readonly List<BankTransaction> bankTransactions;

        public BankStatementProcessor(List<BankTransaction> bankTransactions) {
            this.bankTransactions = bankTransactions;
        }

        public double CalculateTotalAmount() {
            double total = 0;
            foreach (var bankTransaction in bankTransactions) {
                total += bankTransaction.Amount;
            }
            return total;
        }

        public double CalculateTotalInMonth(Month month) {
            double total = 0;
            foreach (var bankTransaction in bankTransactions) {
                if (bankTransaction.Date.Month == month) {
                    total += bankTransaction.Amount;
                }
            }
            return total;
        }

        public double CalculateTotalForCategory(string category) {
            double total = 0;
            foreach (var bankTransaction in bankTransactions) {
                if (bankTransaction.Description.Equals(category)) {
                    total += bankTransaction.Amount;
                }
            }
            return total;
        }

        public List<KeyValuePair<string, double>> GetTop10Expenses() {
            Dictionary<string, double> categoryTotals = new Dictionary<string, double>();
            foreach (var bankTransaction in bankTransactions) {
                if (bankTransaction.Amount < 0) {
                    string category = bankTransaction.Description;
                    double amount = Math.Abs(bankTransaction.Amount);
                    if (categoryTotals.ContainsKey(category)) {
                        categoryTotals[category] += amount;
                    } else {
                        categoryTotals[category] = amount;
                    }
                }
            }
            return categoryTotals.OrderByDescending(entry => entry.Value)
                                 .Take(10)
                                 .ToList();
        }
    }
    ```

  - **Method-Level Cohesion:**
    - A method that performs too many different functionalities is hard to understand, indicating low cohesion.
    - **Example:** A method handling multiple concerns reduces clarity and maintainability.

### 3. Coupling

  - **Definition:** The dependency between modules or classes.
  - **Impact:**
    - **High Coupling:** Reduces flexibility when introducing changes.
    - **Low Coupling:** Enhances flexibility and makes the system easier to modify.
  - **Reducing Coupling:**
    - Use interfaces so that changes in one part (e.g., class A) do not affect others (e.g., class B).
    - **Example:** Use a `IBankStatementParser` interface with a `BankStatementCSVParser` implementation to minimize dependencies.

    ```csharp
    // Interface
    public interface IBankStatementParser {
        BankTransaction ParseFrom(string line);
        List<BankTransaction> ParseLinesFrom(List<string> lines);
    }

    // Implementation
    public class BankStatementCSVParser : IBankStatementParser {
        @Override
        public BankTransaction ParseFrom(string line) {
            string[] columns = line.Split(",");
            DateTime date = DateUtils.ParseDate(columns[0]);
            double amount = double.Parse(columns[1]);
            string description = columns[2];
            return new BankTransaction(date, amount, description);
        }

        @Override
        public List<BankTransaction> ParseLinesFrom(List<string> lines) {
            List<BankTransaction> bankTransactions = new List<BankTransaction>();
            foreach (string line in lines) {
                bankTransactions.Add(ParseFrom(line));
            }
            return bankTransactions;
        }
    }
    ```
