package service.local.create.interface_adapter;

import javax.swing.JFileChooser;

import service.local.create.CreateLocalVaultInputBoundary;
import service.local.create.CreateLocalVaultInputData;

/**
 * Controller for creating a local .doorkey vault.
 */
public class CreateLocalVaultController {
    private final CreateLocalVaultInputBoundary interactor;

    public CreateLocalVaultController(CreateLocalVaultInputBoundary interactor) {
        this.interactor = interactor;
    }
    
    /**
     * Creates a local .doorkey vault.
     * @param path     The path to the .doorkey file
     * @param password The user supplied password
     */
    public void createLocalVault(JFileChooser path, String password) {
        final CreateLocalVaultInputData inputData = new CreateLocalVaultInputData(path, password);
        interactor.createLocalVault(inputData);
    }
}
