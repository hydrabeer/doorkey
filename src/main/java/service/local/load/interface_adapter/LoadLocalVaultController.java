package service.local.load.interface_adapter;

import javax.swing.JFileChooser;

import service.local.load.LoadLocalVaultInputBoundary;
import service.local.load.LoadLocalVaultInputData;

/**
 * Controller for loading a local .doorkey vault.
 */
public class LoadLocalVaultController {
    private final LoadLocalVaultInputBoundary interactor;

    public LoadLocalVaultController(LoadLocalVaultInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Loads a local .doorkey vault.
     * @param path     The path to the .doorkey file
     * @param password The user supplied password
     */
    public void loadLocalVault(JFileChooser path, String password) {
        final LoadLocalVaultInputData inputData = new LoadLocalVaultInputData(path, password);
        interactor.loadLocalVault(inputData);
    }
}
