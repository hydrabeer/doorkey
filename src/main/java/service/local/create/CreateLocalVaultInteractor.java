package service.local.create;

import java.io.IOException;

import javax.swing.JFileChooser;

import exception.AuthException;
import repository.RepositoryProvider;
import repository.UserRepository;

/**
 * The local vault creation interactor.
 */
public class CreateLocalVaultInteractor implements CreateLocalVaultInputBoundary {
    private final RepositoryProvider repositoryProvider;
    private final UserRepository localUserRepository;
    private final CreateLocalVaultOutputBoundary createLocalVaultPresenter;

    public CreateLocalVaultInteractor(
            RepositoryProvider repositoryProvider,
            UserRepository localUserRepository,
            CreateLocalVaultOutputBoundary outputPresenter
    ) {
        this.repositoryProvider = repositoryProvider;
        this.localUserRepository = localUserRepository;
        this.createLocalVaultPresenter = outputPresenter;
    }

    @Override
    public void createLocalVault(CreateLocalVaultInputData createLocalVaultInputData) {
        final JFileChooser saver = createLocalVaultInputData.getPath();
        if (saver.getSelectedFile() == null) {
            createLocalVaultPresenter.prepareErrorView("Please select a valid .doorkey file!");
        }
        else {
            final String path = saver.getSelectedFile().getAbsolutePath();
            if (path.isEmpty()) {
                createLocalVaultPresenter.prepareErrorView("Please select a valid .doorkey file!");
            }
            else {
                final String password = createLocalVaultInputData.getPassword();
                if (password.isEmpty()) {
                    createLocalVaultPresenter.prepareErrorView("Please enter a password");
                }
                else {
                    try {
                        localUserRepository.signupUser(path, password);
                        repositoryProvider.setRepository(localUserRepository);
                        final CreateLocalVaultOutputData outputData = new CreateLocalVaultOutputData(
                                localUserRepository
                        );
                        createLocalVaultPresenter.prepareSuccessView(outputData);
                    }
                    catch (AuthException | IOException exception) {
                        createLocalVaultPresenter.prepareErrorView(exception.getMessage());
                    }
                }
            }
        }

    }
}
