package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class to load configuration properties.
 */
public class ConfigLoader {
    private static final String CONFIG_FILE = "config.properties";
    private final Properties properties;

    public ConfigLoader() throws IOException {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IOException("Unable to find " + CONFIG_FILE);
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
