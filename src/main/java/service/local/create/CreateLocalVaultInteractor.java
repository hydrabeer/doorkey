package service.local.create;

import javax.swing.JFileChooser;

/**
 * The local vault creation interactor.
 */
public class CreateLocalVaultInteractor implements CreateLocalVaultInputBoundary {
    private final CreateLocalVaultOutputBoundary createLocalVaultPresenter;

    public CreateLocalVaultInteractor(CreateLocalVaultOutputBoundary outputPresenter) {
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
                    final CreateLocalVaultOutputData outputData = new CreateLocalVaultOutputData(
                        path,
                        password
                    );
                    createLocalVaultPresenter.prepareSuccessView(outputData);
                }
            }
        }

    }
}
