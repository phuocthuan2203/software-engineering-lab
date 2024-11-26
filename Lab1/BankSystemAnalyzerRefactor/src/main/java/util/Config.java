package util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Config {
    public static final String OUTPUT_DIRECTORY = "src/main/output/";
    public static final String INPUT_DIRECTORY = "src/main/resources/";

    // Ensure the output directory exists
    public static void ensureOutputDirectoryExists() {
        Path path = Paths.get(OUTPUT_DIRECTORY);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Output directory created at: " + OUTPUT_DIRECTORY);
            } catch (IOException e) {
                System.err.println("Failed to create output directory: " + e.getMessage());
            }
        }
    }

    // Ensure the input directory exists
    public static void ensureInputDirectoryExists() {
        Path path = Paths.get(INPUT_DIRECTORY);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Input directory created at: " + INPUT_DIRECTORY);
            } catch (IOException e) {
                System.err.println("Failed to create input directory: " + e.getMessage());
            }
        }
    }

    // Get the input file based on extension
    public static String getInputFilePath(String extension) {
        return INPUT_DIRECTORY + "transactions." + extension;
    }
}
