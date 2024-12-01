package service.import_vault_item;

import entity.AbstractUser;
import repository.UserRepository;

/**
 * The input data for importing a vault item.
 */
public class ImportVaultItemInputData {
    private final String passwordManager;
    private final String importTextJson;
    private final AbstractUser user;
    private final UserRepository userRepository;

    public ImportVaultItemInputData(
            String passwordManager,
            String importTextJson,
            AbstractUser user,
            UserRepository userRepository
    ) {
        this.passwordManager = passwordManager;
        this.importTextJson = importTextJson;
        this.user = user;
        this.userRepository = userRepository;
    }

    public String getPasswordManager() {
        return passwordManager;
    }

    public String getImportTextJson() {
        return importTextJson;
    }

    public AbstractUser getUser() {
        return user;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
