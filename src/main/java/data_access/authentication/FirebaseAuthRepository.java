package data_access.authentication;

import java.util.HashMap;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import exception.AuthException;
import exception.HttpRequestException;
import interface_adapter.net.auth.AuthErrorReason;
import interface_adapter.net.http.HttpClient;
import interface_adapter.net.http.HttpResponse;
import repository.AuthRepository;
import repository.AuthResponse;

/**
 * Implements the AuthRepository interface for Firebase authentication.
 */
public class FirebaseAuthRepository implements AuthRepository {
    // NOTE: This is a PUBLICLY facing API key. It is not a security risk to expose this key.
    // Straight from the Firebase documentation:
    // "To store Firebase API keys (which are not secret), just embed them in code."
    private static final String API_KEY = "AIzaSyBWN4VN3kb6REo48e8RlSW3l1cruQtiBcI";
    private static final String SIGN_UP_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key="
            + API_KEY;
    private static final String SIGN_IN_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword"
            + "?key="
            + API_KEY;
    private static final String REFRESH_TOKEN_URL = "https://securetoken.googleapis.com/v1/token?key="
            + API_KEY;
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    private static final String JSON_CONTENT_TYPE_HEADER_VALUE = "application/json; charset=UTF-8";
    private static final String FORM_URLENCODED_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String REFRESH_TOKEN_BASE_URL = "grant_type=refresh_token&refresh_token=";
    private static final String ERROR_KEY = "error";
    private static final String MESSAGE_KEY = "message";

    private final HttpClient httpClient;

    public FirebaseAuthRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public AuthResponse signUp(String email, String password) throws AuthException {
        try {
            final HttpResponse response = httpClient.post(
                    SIGN_UP_URL,
                    createAuthRequestBody(email, password).toString(),
                    new HashMap<>() {{
                        put(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_HEADER_VALUE);
                    }}
            );
            final JSONObject jsonResponse = response.bodyToJsonObject();
            if (jsonResponse.has(ERROR_KEY)) {
                final Object error = jsonResponse.get(ERROR_KEY);
                if (error instanceof JSONObject errorObject) {
                    final String firebaseErrorResponse = errorObject.getString(MESSAGE_KEY);
                    if ("EMAIL_EXISTS".equals(firebaseErrorResponse)) {
                        throw new AuthException(AuthErrorReason.EMAIL_EXISTS, firebaseErrorResponse);
                    }
                    throw new AuthException(AuthErrorReason.UNKNOWN, errorObject.getString(MESSAGE_KEY));
                }
                throw new AuthException(AuthErrorReason.UNKNOWN, error.toString());
            }
            return buildAuthResponse(jsonResponse);

        }
        catch (HttpRequestException httpRequestException) {
            throw new AuthException(AuthErrorReason.REQUEST_ERROR, httpRequestException.getMessage());
        }
    }

    @Override
    public AuthResponse signIn(String email, String password) throws AuthException {
        try {
            final HttpResponse response = httpClient.post(
                    SIGN_IN_URL,
                    createAuthRequestBody(email, password).toString(),
                    new HashMap<>() {{
                        put(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_HEADER_VALUE);
                    }}
            );
            final JSONObject jsonResponse = response.bodyToJsonObject();
            if (jsonResponse.has(ERROR_KEY)) {
                final Object error = jsonResponse.get(ERROR_KEY);
                if (error instanceof JSONObject errorObject) {
                    throw new AuthException(AuthErrorReason.WRONG_CREDENTIALS, errorObject.getString(MESSAGE_KEY));
                }
                throw new AuthException(AuthErrorReason.WRONG_CREDENTIALS, error.toString());
            }
            return buildAuthResponse(jsonResponse);
        }
        catch (HttpRequestException httpRequestException) {
            throw new AuthException(AuthErrorReason.REQUEST_ERROR, httpRequestException.getMessage());
        }
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) throws AuthException {
        try {
            final HttpResponse response = httpClient.post(
                    REFRESH_TOKEN_URL,
                    REFRESH_TOKEN_BASE_URL + refreshToken,
                    new HashMap<>() {{
                        put(CONTENT_TYPE_HEADER_KEY, FORM_URLENCODED_CONTENT_TYPE);
                    }}
            );
            final JSONObject jsonResponse = response.bodyToJsonObject();
            return buildAuthResponse(jsonResponse);
        }
        catch (HttpRequestException httpRequestException) {
            throw new AuthException(AuthErrorReason.REQUEST_ERROR, httpRequestException.getMessage());
        }
    }

    /**
     * Builds an AuthResponse object from a JSON response.
     * Firebase occasionally returns snake case from certain API calls. Instead of
     * writing a bunch of if/else checks, we just use try/catch with the snake case
     * keys.
     * @param jsonResponse The JSON response.
     * @return The AuthResponse object.
     */
    private static AuthResponse buildAuthResponse(JSONObject jsonResponse) {
        AuthResponse authResponse;
        try {
            final String expiresIn = jsonResponse.getString("expiresIn");
            authResponse = new AuthResponse.AuthResponseBuilder()
                    .setIdToken(jsonResponse.getString("idToken"))
                    .setRefreshToken(jsonResponse.getString("refreshToken"))
                    .setLocalId(jsonResponse.getString("localId"))
                    .setExpiresIn(Integer.parseInt(expiresIn))
                    .build();
        }
        catch (JSONException jsonException) {
            final String expiresIn = jsonResponse.getString("expires_in");
            authResponse = new AuthResponse.AuthResponseBuilder()
                    .setIdToken(jsonResponse.getString("id_token"))
                    .setRefreshToken(jsonResponse.getString("refresh_token"))
                    .setLocalId(jsonResponse.getString("user_id"))
                    .setExpiresIn(Integer.parseInt(expiresIn))
                    .build();
        }

        return authResponse;
    }

    @NotNull
    private static JSONObject createAuthRequestBody(String email, String password) {
        final JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("password", password);
        json.put("returnSecureToken", true);
        return json;
    }
}
