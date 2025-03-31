package interface_adapter.vault_imports;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordManagerTest {

    @Test
    void testPasswordManagerEnumValueOf() {
        assertEquals(PasswordManager.ONE_PASSWORD, PasswordManager.valueOf("ONE_PASSWORD"));
    }
}