# Top 5 Issues and Solutions

## 1. Lack of Modularization
- **Issue**:
  - All classes were placed in a single package (`main.java`), violating the **Single Responsibility Principle** and making the project harder to maintain and scale.
- **Solution**:
  - Introduced a modular project structure with packages organized by responsibilities:
    ```
    src/main/java/
    ├── analyzer/       # Code for analyzing transactions
    ├── config/         # Configuration and properties handling
    ├── main/           # Entry point for the application
    ├── model/          # POJOs like Transaction
    ├── service/        # Core processing logic
    ├── utility/        # Utility classes like file readers
    ```

## 2. Hardcoded File Paths
- **Issue**:
  - File paths like `transactions.csv` and `application.properties` were hardcoded, reducing flexibility and portability.
- **Solution**:
  - Introduced a `Config` class to dynamically load properties from `application.properties` located in `src/main/resources/`:
    ```java
    properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
    ```

## 3. Inefficient and Repetitive Data Processing
- **Issue**:
  - Each analyzer (e.g., `CategoryAnalyzer`, `ExpenseAnalyzer`) processed the same dataset independently, causing redundancy and reduced performance.
- **Solution**:
  - Added a `TransactionProcessor` service to preprocess data and share reusable results across analyzers:
    ```java
    public class TransactionProcessor {
        private Map<String, Double> categoryTotals;

        public void preprocessTransactions(List<Transaction> transactions) {
            // Centralized data processing logic
        }

        public double getTotalForCategory(String category) {
            return categoryTotals.getOrDefault(category, 0.0);
        }
    }
    ```

## 4. No Resource Loading from Classpath
- **Issue**:
  - Files like `transactions.csv` were loaded using relative paths, making them environment-dependent and prone to errors.
- **Solution**:
  - Replaced `FileReader` with `getResourceAsStream()` to load resources from the classpath:
    ```java
    InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("transactions.csv");
    ```

## 5. Lack of Build Automation
- **Issue**:
  - The project lacked a build tool, making dependency management, builds, and deployments manual and error-prone.
- **Solution**:
  - Transitioned to **Maven**:
    - Added a `pom.xml` for dependency management and build automation.
    - Configured plugins for compiling and running the application:
      ```xml
      <plugin>
          <groupId>org.codehaus.mojo</groupId>
          <artifactId>exec-maven-plugin</artifactId>
          <version>3.0.0</version>
          <configuration>
              <mainClass>main.java.main.BankSystemAnalyzer</mainClass>
          </configuration>
      </plugin>
      ```
    - Commands to build and run:
      ```bash
      mvn clean package
      mvn exec:java
      ```
