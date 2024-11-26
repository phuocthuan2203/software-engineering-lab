package parser;

public class ParserFactory {

    public static BankStatementParser getParser(String fileType) {
        switch (fileType.toLowerCase()) {
            case "csv":
                return new BankStatementCSVParser();
            case "json":
                return new BankStatementJSONParser();
            case "xml":
                return new BankStatementXMLParser();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
}
