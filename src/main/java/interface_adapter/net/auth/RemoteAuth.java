package interface_adapter.net.auth;

import java.util.Date;

import repository.AuthResponse;

/**
 * Stores minimal authentication details for a remote user.
 */
public class RemoteAuth {
    private final String idToken;
    private final String refreshToken;
    private final String localId;
    private final int expiresIn;
    private final Date createdAt;

    public RemoteAuth(String idToken, String refreshToken, String localId, int expiresIn) {
        this.idToken = idToken;
        this.refreshToken = refreshToken;
        this.localId = localId;
        this.expiresIn = expiresIn;
        this.createdAt = new Date();
    }

    public RemoteAuth(AuthResponse authResponse) {
        this.idToken = authResponse.getIdToken();
        this.refreshToken = authResponse.getRefreshToken();
        this.localId = authResponse.getLocalId();
        this.expiresIn = authResponse.getExpiresIn();
        this.createdAt = new Date();
    }

    public String getIdToken() {
        return idToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getLocalId() {
        return localId;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * Return if the refresh token is still valid.
     *
     * @return True if the refresh token is still valid, false otherwise.
     */
    public boolean isRefreshTokenValid() {
        // Note that expiresIn is in seconds, so converting it to MS:
        return new Date().getTime() < createdAt.getTime() + expiresIn * 1000L;
    }
}
