package repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthResponseTest {

    @Test
    public void testGettersAndSetters() {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setIdToken("idToken");
        authResponse.setRefreshToken("refreshToken");
        authResponse.setLocalId("localId");
        authResponse.setExpiresIn(3600);

        assertEquals("idToken", authResponse.getIdToken());
        assertEquals("refreshToken", authResponse.getRefreshToken());
        assertEquals("localId", authResponse.getLocalId());
        assertEquals(3600, authResponse.getExpiresIn());
    }

    @Test
    public void testBuilder() {
        AuthResponse authResponse = new AuthResponse.AuthResponseBuilder()
                .setIdToken("idToken")
                .setRefreshToken("refreshToken")
                .setLocalId("localId")
                .setExpiresIn(3600)
                .build();

        assertEquals("idToken", authResponse.getIdToken());
        assertEquals("refreshToken", authResponse.getRefreshToken());
        assertEquals("localId", authResponse.getLocalId());
        assertEquals(3600, authResponse.getExpiresIn());
    }
}