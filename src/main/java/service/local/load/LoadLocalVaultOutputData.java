package service.local.load;

import repository.UserRepository;

/**
 * Output data from loading a local .doorkey vault.
 */
public class LoadLocalVaultOutputData {
    private final UserRepository repository;

    public LoadLocalVaultOutputData(UserRepository userRepository) {
        this.repository = userRepository;
    }

    public UserRepository getRepository() {
        return repository;
    }
}
