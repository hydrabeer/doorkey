package service.copy_credentials;

/**
 * DAO for the Copy Credentials Use Case.
 */
public interface CopyCredentialsDataAccessInterface {

    /**
     * Returns the Vault Item password.
     * @return the Vault Item password
     */
    String getVaultItemPassword();

    /**
     * Returns the Vault Item username.
     * @return the Vault Item username
     */
    String getVaultItemUsername();
}
