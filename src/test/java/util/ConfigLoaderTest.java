package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigLoaderTest {

    private ConfigLoader configLoader;

    @BeforeEach
    public void setUp() throws IOException {
        configLoader = new ConfigLoader();
    }

    @Test
    public void testLoadPropertyFromFile() {
        String expectedValue = "someValue";
        assertEquals(expectedValue, configLoader.getProperty("someKey"));
    }

    @Test
    public void testLoadPropertyFromConstructor() {
        ConfigLoader configLoaderWithProperty = new ConfigLoader("testKey", "testValue");
        assertEquals("testValue", configLoaderWithProperty.getProperty("testKey"));
    }

    @Test
    public void testLoadNonExistentPropertyFromConstructor() {
        ConfigLoader configLoaderWithProperty = new ConfigLoader("key", "value");
        assertNull(configLoaderWithProperty.getProperty("nonexistent"));
    }

    @Test
    public void testMissingConfigFile() {
        Exception exception = assertThrows(IOException.class, () -> new ConfigLoader("nonexistent.properties"));
        assertEquals("Unable to find nonexistent.properties", exception.getMessage());
    }
}