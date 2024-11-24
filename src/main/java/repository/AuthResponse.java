package repository;

/**
 * Basic response returned from a third party Authentication provider (such as Firebase).
 */
public class AuthResponse {
    private String idToken;
    private String refreshToken;
    private String localId;
    private int expiresIn;

    /**
     * Gets the idToken.
     *
     * @return The idToken.
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * Gets the refreshToken.
     *
     * @return The refreshToken.
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Gets the localId.
     *
     * @return The localId.
     */
    public String getLocalId() {
        return localId;
    }

    /**
     * Gets the expiresIn.
     *
     * @return The expiresIn.
     */
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * Sets the refreshToken.
     *
     * @param idToken The ID token to set.
     */
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    /**
     * Sets the expiresIn.
     *
     * @param expiresIn The expiresIn to set.
     */
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * Sets the refreshToken.
     *
     * @param refreshToken The refreshToken to set.
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Sets the localId.
     *
     * @param localId The localId to set.
     */
    public void setLocalId(String localId) {
        this.localId = localId;
    }

    /**
     * Builder for the AuthResponse.
     */
    public static class AuthResponseBuilder {
        private final AuthResponse authResponse;

        /**
         * Constructor for the AuthResponseBuilder.
         */
        public AuthResponseBuilder() {
            authResponse = new AuthResponse();
        }

        /**
         * Sets the idToken for the AuthResponse.
         *
         * @param idToken The idToken to set.
         * @return The AuthResponseBuilder.
         */
        public AuthResponseBuilder setIdToken(String idToken) {
            authResponse.idToken = idToken;
            return this;
        }

        /**
         * Sets the refreshToken for the AuthResponse.
         *
         * @param refreshToken The refreshToken to set.
         * @return The AuthResponseBuilder.
         */
        public AuthResponseBuilder setRefreshToken(String refreshToken) {
            authResponse.refreshToken = refreshToken;
            return this;
        }

        /**
         * Sets the localId for the AuthResponse.
         *
         * @param localId The localId to set.
         * @return The AuthResponseBuilder.
         */
        public AuthResponseBuilder setLocalId(String localId) {
            authResponse.localId = localId;
            return this;
        }

        /**
         * Sets the expiresIn for the AuthResponse.
         *
         * @param expiresIn The expiresIn to set.
         * @return The AuthResponseBuilder.
         */
        public AuthResponseBuilder setExpiresIn(int expiresIn) {
            authResponse.expiresIn = expiresIn;
            return this;
        }

        /**
         * Builds the AuthResponse.
         *
         * @return The AuthResponse.
         */
        public AuthResponse build() {
            return authResponse;
        }
    }
}
