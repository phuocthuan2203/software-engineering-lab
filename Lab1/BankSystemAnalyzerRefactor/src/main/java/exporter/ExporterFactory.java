package exporter;

public class ExporterFactory {

    public static ResultExporter getExporter(String fileType) {
        switch (fileType.toLowerCase()) {
            case "csv":
                return new CSVResultExporter();
            case "json":
                return new JSONResultExporter();
            case "xml":
                return new XMLResultExporter();
            default:
                throw new IllegalArgumentException("Unsupported export file type: " + fileType);
        }
    }
}
