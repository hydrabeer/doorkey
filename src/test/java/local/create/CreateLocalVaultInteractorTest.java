package local.create;

import exception.AuthException;
import mock.MockCreateLocalVaultPresenter;
import mock.MockRepositoryProvider;
import mock.MockUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.local.create.CreateLocalVaultInputData;
import service.local.create.CreateLocalVaultInteractor;
import service.local.create.CreateLocalVaultOutputData;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class CreateLocalVaultInteractorTest {
    private MockRepositoryProvider repositoryProvider;
    private MockUserRepository userRepository;
    private MockCreateLocalVaultPresenter presenter;
    private CreateLocalVaultInteractor interactor;
    private CreateLocalVaultInputData inputData;

    @BeforeEach
    void setup() {
        userRepository = new MockUserRepository();
        repositoryProvider = new MockRepositoryProvider(userRepository);
        presenter = new MockCreateLocalVaultPresenter();
        interactor = new CreateLocalVaultInteractor(repositoryProvider, userRepository, presenter);
        JFileChooser fileChooser = new JFileChooser();
        inputData = new CreateLocalVaultInputData(fileChooser, "password");
    }

    @Test
    public void createLocalVaultInteractorNullSaveFileTest() {
        interactor.createLocalVault(inputData);
        assertTrue(presenter.generalErrors.contains("Please select a valid .doorkey file!"));
    }

    @Test
    public void createLocalVaultInteractorPathEmptyTest() {
        JFileChooser fileChooser = new JFileChooser() {
            @Override
            public java.io.File getSelectedFile() {
                return new java.io.File("") {
                    @Override
                    public String getAbsolutePath() {
                        return "";
                    }
                };
            }
        };
        inputData = new CreateLocalVaultInputData(fileChooser, "password");
        interactor.createLocalVault(inputData);
        assertTrue(presenter.generalErrors.contains("Please select a valid .doorkey file!"));
    }

    @Test
    public void createLocalVaultInteractorPasswordEmptyTest() {
        JFileChooser fileChooser = new JFileChooser() {
            @Override
            public java.io.File getSelectedFile() {
                return new java.io.File("valid.doorkey") {
                    @Override
                    public String getAbsolutePath() {
                        return "valid.doorkey";
                    }
                };
            }
        };
        inputData = new CreateLocalVaultInputData(fileChooser, "");
        interactor.createLocalVault(inputData);
        assertTrue(presenter.generalErrors.contains("Please enter a password"));
    }

    @Test
    public void createLocalVaultInteractorSuccessTest() {
        JFileChooser fileChooser = new JFileChooser() {
            @Override
            public java.io.File getSelectedFile() {
                return new java.io.File("valid.doorkey") {
                    @Override
                    public String getAbsolutePath() {
                        return "valid.doorkey";
                    }
                };
            }
        };
        inputData = new CreateLocalVaultInputData(fileChooser, "password");
        interactor.createLocalVault(inputData);
        assertEquals(1, presenter.successViews.size());
        // Path and password removed from output data - handled in interactor
        // assertEquals("password", presenter.successViews.get(0).getPassword());
        // assertEquals("valid.doorkey", presenter.successViews.get(0).getPath());
    }

    @Test
    public void createLocalVaultDuplicateUserTest() {
        JFileChooser fileChooser = new JFileChooser() {
            @Override
            public java.io.File getSelectedFile() {
                return new java.io.File("valid.doorkey") {
                    @Override
                    public String getAbsolutePath() {
                        return "valid.doorkey";
                    }
                };
            }
        };
        inputData = new CreateLocalVaultInputData(fileChooser, "password");
        interactor.createLocalVault(inputData);
        interactor.createLocalVault(inputData); // duplicate call to cover throws branch
        assertFalse(presenter.generalErrors.isEmpty());
    }

    @Test
    public void createLocalVaultOutputDataTest() {
        CreateLocalVaultOutputData outputData = new CreateLocalVaultOutputData(userRepository);
        assertEquals(outputData.getRepository(), userRepository);
    }
}
