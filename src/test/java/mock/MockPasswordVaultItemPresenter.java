package mock;

import service.password_vault_item.PasswordVaultItemOutputBoundary;

import java.util.ArrayList;
import java.util.List;

public class MockPasswordVaultItemPresenter implements PasswordVaultItemOutputBoundary {
    public List<String> messages = new ArrayList<>();

    @Override
    public void displayHomeView() {
        messages.add("Display home view");
    }

    @Override
    public void updateDeleteItem() {

    }

    @Override
    public void displayDeleteMessage() {
        messages.add("Press delete again to confirm. Press copy button to reset");
    }

    public boolean hasMessage(String error) {
        return messages.stream().anyMatch(s -> s.startsWith(error));
    }
}
