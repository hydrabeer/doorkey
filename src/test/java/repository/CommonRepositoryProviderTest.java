package repository;

import mock.MockUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonRepositoryProviderTest {

    private CommonRepositoryProvider commonRepositoryProvider;
    private UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        mockUserRepository = new MockUserRepository();
        commonRepositoryProvider = new CommonRepositoryProvider();
    }

    @Test
    public void testGetRepositoryUnchecked() {
        commonRepositoryProvider.setRepository(mockUserRepository);
        assertEquals(mockUserRepository, commonRepositoryProvider.getRepositoryUnchecked());
    }

    @Test
    public void testGetRepository() {
        commonRepositoryProvider.setRepository(mockUserRepository);
        Optional<UserRepository> repository = commonRepositoryProvider.getRepository();
        assertTrue(repository.isPresent());
        assertEquals(mockUserRepository, repository.get());
    }

    @Test
    public void testGetRepositoryWhenNotSet() {
        Optional<UserRepository> repository = commonRepositoryProvider.getRepository();
        assertFalse(repository.isPresent());
    }

    @Test
    public void testSetRepository() {
        commonRepositoryProvider.setRepository(mockUserRepository);
        assertEquals(mockUserRepository, commonRepositoryProvider.getRepositoryUnchecked());
    }

    @Test
    public void testClearRepository() {
        commonRepositoryProvider.setRepository(mockUserRepository);
        commonRepositoryProvider.clearRepository();
        assertFalse(commonRepositoryProvider.getRepository().isPresent());
    }

    @Test
    public void testConstructorWithUserRepository() {
        UserRepository mockUserRepository = new MockUserRepository();
        CommonRepositoryProvider commonRepositoryProvider = new CommonRepositoryProvider(
                mockUserRepository
        );

        assertEquals(mockUserRepository, commonRepositoryProvider.getRepositoryUnchecked());
    }
}