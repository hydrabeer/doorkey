package service.password_generation.interface_adapter;

import service.password_generation.PasswordGenerationInputBoundary;

/**
 * The controller for the password generation use case.
 */
public class PasswordGenerationController {
    private final PasswordGenerationInputBoundary interactor;

    public PasswordGenerationController(PasswordGenerationInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Initiates the password generation process.
     */
    public void generatePassword() {
        interactor.generate();
    }
}
