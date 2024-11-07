package interface_adapter.random_generator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import interface_adapter.crypto.random_generator.RandomGenerator;
import interface_adapter.crypto.random_generator.SecureRandomGenerator;

public class RandomGeneratorTest {
    private static RandomGenerator randGen = new SecureRandomGenerator();

    @Test
    public void testGetRandomBytesLengthAndNonEmpty() {
        byte[] zeros = new byte[16];
        byte[] random = randGen.getRandomBytes(16);
        assertTrue(random.length == 16);
        assertFalse(Arrays.equals(zeros, random));
    }

    @Test
    public void testGetRandomBytesNonDeterministic() {
        byte[] random1 = randGen.getRandomBytes(16);
        byte[] random2 = randGen.getRandomBytes(16);
        assertFalse(Arrays.equals(random1, random2));
    }
}
