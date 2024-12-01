package interface_adapter.vault_imports;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.AbstractVaultItem;
import entity.PasswordVaultItem;

/**
 * Adapter for 1Password, that converts 1Password JSON data into a list of AbstractVaultItem instances.
 */
public class OnePasswordAdapter {
    private static final String DESIGNATION_FIELD = "designation";

    /**
     * Convert the 1Password JSON input into a list of AbstractVaultItem instances.
     *
     * @param input The 1Password JSON formatted input, which is 1pux format.
     * @return A list of AbstractVaultItem objects.
     * @throws JSONException If there is an error parsing the JSON.
     * @see <a href="https://support.1password.com/1pux-format/">1PUX</a>
     */
    public List<AbstractVaultItem> convert(JSONObject input) throws JSONException {
        final List<AbstractVaultItem> vaultItems = new ArrayList<>();

        final JSONArray accounts = input.optJSONArray("accounts");
        if (accounts == null) {
            return vaultItems;
        }

        for (int i = 0; i < accounts.length(); i++) {
            final JSONObject account = accounts.getJSONObject(i);
            final JSONArray vaults = account.optJSONArray("vaults");
            if (vaults == null) {
                continue;
            }

            final JSONObject attrs = account.optJSONObject("attrs");
            final String email = attrs.optString("email", "no email");

            for (int j = 0; j < vaults.length(); j++) {
                final JSONObject vault = vaults.getJSONObject(j);
                final JSONArray items = vault.optJSONArray("items");
                if (items == null) {
                    continue;
                }

                for (int k = 0; k < items.length(); k++) {
                    final JSONObject item = items.getJSONObject(k);
                    final AbstractVaultItem vaultItem = parseItem(email, item);
                    if (vaultItem != null) {
                        vaultItems.add(vaultItem);
                    }
                }
            }
        }

        return vaultItems;
    }

    /**
     * Note that the 1Password JSON format lacks many examples. So, if the email/username field is missing,
     * we automatically fill it in with the account's email address.
     *
     * @param attrEmail The email address of the account.
     * @param item The JSON object representing the item.
     * @return An AbstractVaultItem instance, or null if the item could not be parsed.
     */
    private AbstractVaultItem parseItem(String attrEmail, JSONObject item) {
        final JSONObject details = item.optJSONObject("details");
        if (details == null) {
            return null;
        }

        final JSONArray loginFields = details.optJSONArray("loginFields");
        if (loginFields == null) {
            return null;
        }

        String usernameOrEmail = attrEmail;
        String password = null;
        for (int i = 0; i < loginFields.length(); i++) {
            final JSONObject loginField = loginFields.getJSONObject(i);
            if ("password".equals(loginField.optString(DESIGNATION_FIELD))) {
                password = loginField.optString("value");
            }
            else if ("username".equals(loginField.optString(DESIGNATION_FIELD))
                    || "email".equals(loginField.optString(DESIGNATION_FIELD))) {
                usernameOrEmail = loginField.optString("value");
            }
        }

        if (password == null) {
            return null;
        }

        final JSONObject overview = item.optJSONObject("overview");
        if (overview == null) {
            return null;
        }

        final String url = overview.optString("url");
        if (url == null) {
            return null;
        }

        final String title = overview.optString("title", url);

        return new PasswordVaultItem(title, usernameOrEmail, password, url);
    }
}
