package local.load;

import javax.swing.JFileChooser;

import exception.AuthException;
import mock.MockRepositoryProvider;
import mock.MockUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mock.MockLoadLocalVaultPresenter;
import service.local.load.LoadLocalVaultInputData;
import service.local.load.LoadLocalVaultInteractor;
import service.local.load.LoadLocalVaultOutputData;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class LoadLocalVaultInteractorTest {
    private MockRepositoryProvider repositoryProvider;
    private MockUserRepository userRepository;
    private MockLoadLocalVaultPresenter presenter;
    private LoadLocalVaultInteractor interactor;
    private LoadLocalVaultInputData inputData;

    @BeforeEach
    void setup() {
        userRepository = new MockUserRepository();
        repositoryProvider = new MockRepositoryProvider(userRepository);
        presenter = new MockLoadLocalVaultPresenter();
        interactor = new LoadLocalVaultInteractor(repositoryProvider, userRepository, presenter);
        JFileChooser fileChooser = new JFileChooser();
        inputData = new LoadLocalVaultInputData(fileChooser, "password");
    }

    @Test
    public void loadLocalVaultInteractorNullSaveFileTest() {
        interactor.loadLocalVault(inputData);
        assertTrue(presenter.generalErrors.contains("Please select a valid .doorkey file!"));
    }

    @Test
    public void loadLocalVaultInteractorPathEmptyTest() {
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
        inputData = new LoadLocalVaultInputData(fileChooser, "password");
        interactor.loadLocalVault(inputData);
        assertTrue(presenter.generalErrors.contains("Please select a valid .doorkey file!"));
    }

    @Test
    public void loadLocalVaultInteractorPasswordEmptyTest() {
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
        inputData = new LoadLocalVaultInputData(fileChooser, "");
        interactor.loadLocalVault(inputData);
        assertTrue(presenter.generalErrors.contains("Please enter a password"));
    }

    @Test
    public void loadLocalVaultInteractorSuccessTest() {
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
        inputData = new LoadLocalVaultInputData(fileChooser, "password");
        try {
            userRepository.signupUser("valid.doorkey", "password");
        } catch (IOException | AuthException e) {
            fail("Exception thrown");
        }
        interactor.loadLocalVault(inputData);
        assertEquals(1, presenter.successViews.size());
        // Path and password removed from output data - handled in interactor
        // assertEquals("password", presenter.successViews.get(0).getPassword());
        // assertEquals("valid.doorkey", presenter.successViews.get(0).getRepository().getCurrentUser().getVault().getSavePath());
    }

    @Test
    public void loadLocalVaultInteractorDuplicateUserTest() {
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
        inputData = new LoadLocalVaultInputData(fileChooser, "password");
        try {
            userRepository.signupUser("valid.doorkey", "password");
        } catch (IOException | AuthException e) {
            fail("Exception thrown");
        }
        userRepository.setThrowIOExceptionSignin(true);
        interactor.loadLocalVault(inputData);
        assertEquals(1, presenter.generalErrors.size());
    }

    @Test
    public void loadLocalVaultOutputDataTest() {
        LoadLocalVaultOutputData outputData = new LoadLocalVaultOutputData(userRepository);
        assertEquals(outputData.getRepository(), userRepository);
    }

}
