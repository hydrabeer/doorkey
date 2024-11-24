package service.local.load;

import javax.swing.JFileChooser;

/**
 * The local vault loading interactor.
 */
public class LoadLocalVaultInteractor implements LoadLocalVaultInputBoundary {
    private final LoadLocalVaultOutputBoundary loadLocalVaultPresenter;

    public LoadLocalVaultInteractor(LoadLocalVaultOutputBoundary outputPresenter) {
        this.loadLocalVaultPresenter = outputPresenter;
    }

    @Override
    public void loadLocalVault(LoadLocalVaultInputData loadLocalVaultInputData) {
        JFileChooser saver = loadLocalVaultInputData.getPath();
        if (saver.getSelectedFile() == null) {
            loadLocalVaultPresenter.prepareErrorView("Please select a valid .doorkey file!");
        } else {
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
                    final LoadLocalVaultOutputData outputData = new LoadLocalVaultOutputData(
                        "", // TODO @Evan
                        path
                    );
                    loadLocalVaultPresenter.prepareSuccessView(outputData);
                }
            }
        }

    }
}
