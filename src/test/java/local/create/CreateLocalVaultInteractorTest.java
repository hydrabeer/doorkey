package local.create;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import mock.MockCreateLocalVaultPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.local.create.CreateLocalVaultInputData;
import service.local.create.CreateLocalVaultInteractor;

import javax.swing.*;

public class CreateLocalVaultInteractorTest {
    private MockCreateLocalVaultPresenter presenter;
    private CreateLocalVaultInteractor interactor;
    private CreateLocalVaultInputData inputData;

    @BeforeEach
    void setup() {
        presenter = new MockCreateLocalVaultPresenter();
        interactor = new CreateLocalVaultInteractor(presenter);
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
        assertEquals("password", presenter.successViews.get(0).getPassword());
        assertEquals("valid.doorkey", presenter.successViews.get(0).getPath());
    }
}
