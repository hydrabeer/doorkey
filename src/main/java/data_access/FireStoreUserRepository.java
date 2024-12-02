package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.AbstractUser;
import entity.AbstractVaultItem;
import entity.CloudUser;
import entity.CloudVault;
import entity.PasswordVaultItem;
import exception.AuthException;
import exception.HttpRequestException;
import interface_adapter.crypto.Utils;
import interface_adapter.crypto.cipher.ChaCha20Cipher;
import interface_adapter.firestore.FirestoreJsonAdapter;
import interface_adapter.net.auth.AuthErrorReason;
import interface_adapter.net.auth.RemoteAuth;
import interface_adapter.net.http.HttpClient;
import interface_adapter.net.http.HttpResponse;
import interface_adapter.vault.VaultItemFactory;
import repository.AuthRepository;
import repository.AuthResponse;
import repository.UserRepository;

/**
 * Data access object for Firestore.
 */
public class FireStoreUserRepository implements UserRepository {
    // Firebase constants
    private static final String PROJECT_ID = "doorkey-csc207";
    private static final String FIRESTORE_BASE_URL = "https://firestore.googleapis.com/v1/projects/"
            + PROJECT_ID
            + "/databases/(default)/documents";
    private static final String USERS_COLLECTION = "users";

    // Other constants
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_WITH_SPACING = "Bearer ";
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    private static final String JSON_CONTENT_TYPE_HEADER_VALUE = "application/json";
    private static final String SLASH = "/";
    private static final String FS_MAP_VALUE = "mapValue";
    private static final String FS_FIELDS = "fields";
    private static final String FS_ARRAY_VALUE = "arrayValue";
    private static final String FS_VALUES = "values";
    private static final String FS_ITEMS = "items";
    private static final String FS_VAULT = "vault";

    private final HttpClient httpClient;

    private final AuthRepository authRepository;

    private CloudUser currentUser;

    public FireStoreUserRepository(AuthRepository authRepository, HttpClient httpClient) {
        this.authRepository = authRepository;
        this.httpClient = httpClient;
    }

    @Override
    public AbstractUser signInUser(String email, String password) throws AuthException {
        final AuthResponse loginResponse = this.authRepository.signIn(email, password);

        final String documentUrl = FIRESTORE_BASE_URL + SLASH + USERS_COLLECTION + SLASH + loginResponse.getLocalId();
        // It is assumed that the refresh token hasn't expired since we just signed in.
        try {
            final HttpResponse response = httpClient.get(
                    documentUrl,
                    new HashMap<>() {{
                        put(AUTHORIZATION_HEADER, BEARER_WITH_SPACING + loginResponse.getIdToken());
                    }}
            );

            if (response.getResponseCode() != 200) {
                throw new AuthException(AuthErrorReason.REQUEST_ERROR, response.getResponseBody());
            }

            final JSONObject jsonResponse = response.bodyToJsonObject();
            final CloudVault vault = getVault(jsonResponse);
            final RemoteAuth auth = new RemoteAuth(loginResponse);

            final CloudUser user = new CloudUser(email, password, vault, auth);
            this.currentUser = user;

            final List<AbstractVaultItem> vaultItems = currentUser.getVault().getItems();
            for (int i = 0; i < vaultItems.size(); i++) {
                final AbstractVaultItem item = vaultItems.get(i);
                try {
                    vaultItems.set(i, decryptVaultItem(item));
                }
                catch (InvalidCipherTextException exception) {
                    throw new AuthException(AuthErrorReason.WRONG_CREDENTIALS, "Invalid password");
                }
            }
            currentUser.getVault().setItems(vaultItems);

            return user;
        }
        catch (HttpRequestException httpRequestException) {
            throw new AuthException(AuthErrorReason.REQUEST_ERROR, httpRequestException.getMessage());
        }
    }

    @Override
    public void signOutUser() {
        this.currentUser = null;
    }

    @Override
    public AbstractUser getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public CloudUser signupUser(String email, String password) throws AuthException {
        final AuthResponse loginResponse = this.authRepository.signUp(email, password);

        final RemoteAuth auth = new RemoteAuth(loginResponse);
        createUserDocument(loginResponse);

        final CloudUser user = new CloudUser(email, password, new CloudVault(new ArrayList<>()), auth);
        this.currentUser = user;
        return user;
    }

    @Override
    public void addVaultItem(AbstractVaultItem item) throws AuthException {
        if (currentUser == null) {
            throw new RuntimeException("Attempted to perform operation when the user is not signed in.");
        }

        currentUser.getVault().addItem(item);

        addAllItemsToVault(currentUser);
    }

    @Override
    public void removeVaultItem(AbstractVaultItem item) throws AuthException {
        if (currentUser == null) {
            throw new RuntimeException("Attempted to perform operation when the user is not signed in.");
        }

        final List<AbstractVaultItem> vaultItems = currentUser.getVault().getItems();
        vaultItems.remove(item);
        currentUser.getVault().setItems(vaultItems);

        addAllItemsToVault(currentUser);
    }

    private void addAllItemsToVault(CloudUser cloudUser) throws AuthException {
        if (!cloudUser.getRemoteAuth().isRefreshTokenValid()) {
            final AuthResponse newAuth = authRepository.refreshToken(cloudUser.getRemoteAuth().getRefreshToken());
            cloudUser.setRemoteAuth(new RemoteAuth(newAuth));
        }

        final String documentUrl = FIRESTORE_BASE_URL
                + SLASH
                + USERS_COLLECTION
                + SLASH
                + cloudUser.getRemoteAuth().getLocalId()
                + "?updateMask.fieldPaths=vault.items";

        // Converts to firestore JSON format (inserting stringValue, etc.)
        final ArrayList<JSONObject> vaultItems = new ArrayList<>();
        for (AbstractVaultItem vaultItem : cloudUser.getVault().getItems()) {
            vaultItems.add(FirestoreJsonAdapter.toFirestoreJson(encryptVaultItem(vaultItem).toJSONObject()));
        }

        final String body = createItemsMap(vaultItems);

        try {
            final HttpResponse response = httpClient.patch(
                    documentUrl,
                    body,
                    new HashMap<>() {{
                        put(AUTHORIZATION_HEADER, BEARER_WITH_SPACING + cloudUser.getRemoteAuth().getIdToken());
                        put(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_HEADER_VALUE);
                    }}
            );

            if (response.getResponseCode() != 200) {
                throw new AuthException(AuthErrorReason.REQUEST_ERROR, response.getResponseBody());
            }
        }
        catch (HttpRequestException httpRequestException) {
            throw new AuthException(AuthErrorReason.REQUEST_ERROR, httpRequestException.getMessage());
        }
    }

    /**
     * Creates a JSON payload for a new item to be added to the vault.
     *
     * @param vaultItems The JSON representation of items.
     * @return The JSON payload.
     */
    private static String createItemsMap(ArrayList<JSONObject> vaultItems) {
        final JSONObject payload = new JSONObject();
        final JSONObject vault = new JSONObject();
        final JSONObject fields = new JSONObject();
        final JSONObject items = new JSONObject();
        final JSONArray values = new JSONArray();

        for (JSONObject item : vaultItems) {
            values.put(new JSONObject().put(FS_MAP_VALUE, new JSONObject().put(FS_FIELDS, item)));
        }

        items.put(FS_ARRAY_VALUE, new JSONObject().put(FS_VALUES, values));
        vault.put(FS_MAP_VALUE, new JSONObject().put(FS_FIELDS, new JSONObject().put(FS_ITEMS, items)));
        fields.put(FS_VAULT, vault);
        payload.put(FS_FIELDS, fields);

        return payload.toString();
    }

    /**
     * Extracts the vault from the JSON response.
     *
     * @param jsonResponse The JSON response from Firestore.
     * @return The CloudVault.
     * @throws RuntimeException if the vault item type is invalid.
     */
    @NotNull
    private static CloudVault getVault(JSONObject jsonResponse) {
        final JSONObject itemsJsonObject = jsonResponse
                .getJSONObject(FS_FIELDS)
                .getJSONObject(FS_VAULT)
                .getJSONObject(FS_MAP_VALUE)
                .getJSONObject(FS_FIELDS)
                .getJSONObject(FS_ITEMS);

        JSONArray items;
        try {
            items = itemsJsonObject
                    .getJSONObject(FS_ARRAY_VALUE)
                    .getJSONArray(FS_VALUES);
        }
        catch (JSONException jsonException) {
            items = new JSONArray();
        }

        final ArrayList<AbstractVaultItem> vaultItems = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            final JSONObject firebaseItem = items.getJSONObject(i).getJSONObject(FS_MAP_VALUE).getJSONObject(FS_FIELDS);
            final JSONObject item = FirestoreJsonAdapter.fromFirestoreJson(firebaseItem);
            final AbstractVaultItem vaultItem = VaultItemFactory.createVaultItemFromJson(item);
            if (vaultItem != null) {
                vaultItems.add(vaultItem);
            }
            else {
                throw new RuntimeException("Invalid vault item type: " + item.getString("type"));
            }
        }

        return new CloudVault(vaultItems);
    }

    /**
     * Creates a user document in Firestore.
     *
     * @param loginResponse The response from the authentication provider.
     * @throws AuthException If the request fails.
     */
    private void createUserDocument(AuthResponse loginResponse) throws AuthException {
        final String documentUrl = FIRESTORE_BASE_URL
                + SLASH
                + USERS_COLLECTION
                + "?documentId="
                + loginResponse.getLocalId();

        try {
            final HttpResponse response = httpClient.post(
                    documentUrl,
                    getEmptyDocumentStructure(),
                    new HashMap<>() {{
                        put(AUTHORIZATION_HEADER, BEARER_WITH_SPACING + loginResponse.getIdToken());
                        put(CONTENT_TYPE_HEADER_KEY, JSON_CONTENT_TYPE_HEADER_VALUE);
                    }}
            );
            if (!(200 <= response.getResponseCode() && response.getResponseCode() <= 299)) {
                throw new AuthException(AuthErrorReason.REQUEST_ERROR, response.getResponseBody());
            }
        }
        catch (HttpRequestException httpRequestException) {
            throw new AuthException(AuthErrorReason.REQUEST_ERROR, httpRequestException.getMessage());
        }
    }

    /**
     * Return a document template for an empty user in Firebase under
     * /users/{userId}/vault
     * with: { items: [], createdAt: timestamp, version: 1 }
     * Version is kept in case of future updates. Currently, DoorKey only has version 1.
     * The createdAt timestamp is the current time to track when the user was created, but has no
     * functional use in the current GUI of DoorKey.
     *
     * @return The document template.
     */
    private String getEmptyDocumentStructure() {
        final String currentTimestamp = java.time.Instant.now().toString();

        final JSONObject vaultFields = new JSONObject();
        vaultFields.put("items", new JSONObject().put(
                FS_ARRAY_VALUE, new JSONObject().put(FS_VALUES, new JSONArray()))
        );
        vaultFields.put("createdAt", new JSONObject().put("timestampValue", currentTimestamp));
        vaultFields.put("version", new JSONObject().put("integerValue", 1));

        final JSONObject vault = new JSONObject();
        vault.put(FS_MAP_VALUE, new JSONObject().put(FS_FIELDS, vaultFields));

        final JSONObject fields = new JSONObject();
        fields.put(FS_VAULT, vault);

        final JSONObject postData = new JSONObject();
        postData.put(FS_FIELDS, fields);

        return postData.toString();
    }

    
    private AbstractVaultItem encryptVaultItem(AbstractVaultItem item) {
        if (item instanceof PasswordVaultItem) {
            final PasswordVaultItem copy = new PasswordVaultItem(
                ((PasswordVaultItem) item).getTitle(),
                ((PasswordVaultItem) item).getUsername(),
                ((PasswordVaultItem) item).getPassword(),
                ((PasswordVaultItem) item).getUrl()
            );
            final String encrypted = Utils.encodeToBase64(
                new ChaCha20Cipher().encrypt(this.currentUser.getPassword(), copy.getPassword())
            );
            copy.setPassword(encrypted);
            return copy;
        }
        return item;
    }

    private AbstractVaultItem decryptVaultItem(AbstractVaultItem item) throws InvalidCipherTextException {
        if (item instanceof PasswordVaultItem) {
            final String encrypted = ((PasswordVaultItem) item).getPassword();
            final String decrypted = new ChaCha20Cipher().decrypt(
                this.currentUser.getPassword(), Utils.decodeFromBase64(encrypted)
            );
            ((PasswordVaultItem) item).setPassword(decrypted);
        }
        return item;
    }

}
