package mock;

import repository.RepositoryProvider;
import repository.UserRepository;

import java.util.Optional;

public class MockRepositoryProvider implements RepositoryProvider {
    private UserRepository repository;

    public MockRepositoryProvider(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserRepository getRepositoryUnchecked() {
        return repository;
    }

    @Override
    public Optional<UserRepository> getRepository() {
        return Optional.ofNullable(repository);
    }

    @Override
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void clearRepository() {
        repository = null;
    }
}
