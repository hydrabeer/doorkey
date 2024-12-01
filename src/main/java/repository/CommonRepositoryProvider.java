package repository;

import java.util.Optional;

/**
 * Provides repositories for the application.
 *
 * @see repository.RepositoryProvider
 */
public class CommonRepositoryProvider implements RepositoryProvider {
    private UserRepository userRepository;

    public CommonRepositoryProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CommonRepositoryProvider() {
        this.userRepository = null;
    }

    @Override
    public UserRepository getRepositoryUnchecked() {
        return userRepository;
    }

    @Override
    public Optional<UserRepository> getRepository() {
        return Optional.ofNullable(userRepository);
    }

    @Override
    public void setRepository(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public void clearRepository() {
        this.userRepository = null;
    }
}
