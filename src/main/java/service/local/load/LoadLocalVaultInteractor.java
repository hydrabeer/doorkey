package service.local.load;

import java.io.IOException;

import javax.swing.JFileChooser;

import exception.AuthException;
import repository.RepositoryProvider;
import repository.UserRepository;

/**
 * The local vault loading interactor.
 */
public class LoadLocalVaultInteractor implements LoadLocalVaultInputBoundary {
    private final RepositoryProvider repositoryProvider;
    private final UserRepository localUserRepository;
    private final LoadLocalVaultOutputBoundary loadLocalVaultPresenter;

    public LoadLocalVaultInteractor(
            RepositoryProvider repositoryProvider,
            UserRepository localUserRepository,
            LoadLocalVaultOutputBoundary outputPresenter
    ) {
        this.repositoryProvider = repositoryProvider;
        this.localUserRepository = localUserRepository;
        this.loadLocalVaultPresenter = outputPresenter;
    }

    @Override
    public void loadLocalVault(LoadLocalVaultInputData loadLocalVaultInputData) {
        final JFileChooser saver = loadLocalVaultInputData.getPath();
        if (saver.getSelectedFile() == null) {
            loadLocalVaultPresenter.prepareErrorView("Please select a valid .doorkey file!");
        }
        else {
            final String path = saver.getSelectedFile().getAbsolutePath();
            if (path.isEmpty()) {
                loadLocalVaultPresenter.prepareErrorView("Please select a valid .doorkey file!");
            }
            else {
                final String password = loadLocalVaultInputData.getPassword();
                if (password.isEmpty()) {
                    loadLocalVaultPresenter.prepareErrorView("Please enter a password");
                }
                else {
                    try {
                        localUserRepository.signInUser(path, password);
                        repositoryProvider.setRepository(localUserRepository);
                        final LoadLocalVaultOutputData outputData = new LoadLocalVaultOutputData(
                                localUserRepository
                        );
                        loadLocalVaultPresenter.prepareSuccessView(outputData);
                    }
                    catch (AuthException | IOException exception) {
                        loadLocalVaultPresenter.prepareErrorView(exception.getMessage());
                    }
                }
            }
        }

    }
}
