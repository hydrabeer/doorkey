package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class to load configuration properties.
 */
public class ConfigLoader {
    private final Properties properties;

    public ConfigLoader() throws IOException {
        this("config.properties");
    }

    public ConfigLoader(String filePath) throws IOException {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (input == null) {
                throw new IOException("Unable to find " + filePath);
            }
            properties.load(input);
        }
    }

    public ConfigLoader(String key, String value) {
        properties = new Properties();
        properties.setProperty(key, value);
    }

    /**
     * Retrieves the value associated with the given key.
     *
     * @param key The property key.
     * @return The property value.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
