package main.java.config;

import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration file: application.properties");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
