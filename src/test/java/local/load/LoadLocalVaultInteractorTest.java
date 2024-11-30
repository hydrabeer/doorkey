package local.load;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JFileChooser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mock.MockLoadLocalVaultPresenter;
import service.local.load.LoadLocalVaultInputData;
import service.local.load.LoadLocalVaultInteractor;


public class LoadLocalVaultInteractorTest {
    private MockLoadLocalVaultPresenter presenter;
    private LoadLocalVaultInteractor interactor;
    private LoadLocalVaultInputData inputData;

    @BeforeEach
    void setup() {
        presenter = new MockLoadLocalVaultPresenter();
        interactor = new LoadLocalVaultInteractor(presenter);
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
        interactor.loadLocalVault(inputData);
        assertEquals(1, presenter.successViews.size());
        assertEquals("password", presenter.successViews.get(0).getPassword());
        assertEquals("valid.doorkey", presenter.successViews.get(0).getPath());
    }
}
