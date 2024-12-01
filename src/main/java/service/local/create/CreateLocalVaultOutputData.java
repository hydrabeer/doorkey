package service.local.create;

import repository.UserRepository;

/**
 * Output data from creating a local .doorkey vault.
 */
public class CreateLocalVaultOutputData {
    private final UserRepository repository;

    public CreateLocalVaultOutputData(UserRepository repository) {
        this.repository = repository;
    }

    public UserRepository getRepository() {
        return repository;
    }
}
