package views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data_access.authentication.FireStoreUserDataAccessObject;
import entity.AbstractVaultItem;
import service.ViewManagerModel;
import service.home.interface_adapter.HomeController;
import service.home.interface_adapter.HomeState;
import service.home.interface_adapter.HomeViewModel;
import views.components.DoorkeyButton;
import views.components.DoorkeyFont;

/**
 * The main Home view that pops up after a success login.
 */
public class HomeView extends JPanel implements ActionListener, PropertyChangeListener {
    private final HomeController homeController;
    private final HomeViewModel homeViewModel;
    private final ViewManagerModel viewManagerModel;
    private final JLabel userInfo = new JLabel();
    private final JLabel userRepositoryInfo = new JLabel();
    private final JLabel itemsLabel = new JLabel();

    public HomeView(
            HomeViewModel homeViewModel,
            HomeController homeController,
            ViewManagerModel viewManagerModel
    ) {
        this.homeController = homeController;
        this.homeViewModel = homeViewModel;
        this.viewManagerModel = viewManagerModel;
        this.homeViewModel.addPropertyChangeListener(this);

        setUpMainPanel();

        addUserInfoPlaceholder();

        addBackButton();

        addVaultItems();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final HomeState homeState = (HomeState) evt.getNewValue();
        dispatchStates(homeState);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.getActionCommand());
    }

    private void dispatchStates(HomeState homeState) {
        if (homeState.getUser().isPresent()) {
            userInfo.setText(homeState.getUser().get().getEmail());
        }

        if (homeState.getUserRepository().isPresent()) {
            if (homeState.getUserRepository().get() instanceof FireStoreUserDataAccessObject) {
                userRepositoryInfo.setText("Firebase");
            }
            else {
                userRepositoryInfo.setText("Local");
            }
        }

        final StringBuilder vaultItems = new StringBuilder();
        if (homeViewModel.getState().getUser().isEmpty()) {
            return;
        }

        for (AbstractVaultItem vaultItem : homeViewModel.getState().getUser().get().getVault().getItems()) {
            // TODO: Decryption Strategy
            vaultItems.append(vaultItem.getTitle()).append("\n");
        }

        itemsLabel.setText(vaultItems.toString());
    }

    private void setUpMainPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ViewConstants.BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    }

    private void addUserInfoPlaceholder() {
        final BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        userInfo.setForeground(Color.WHITE);
        userInfo.setFont(new DoorkeyFont());
        userInfo.setAlignmentX(CENTER_ALIGNMENT);

        userRepositoryInfo.setForeground(Color.WHITE);
        userRepositoryInfo.setFont(new DoorkeyFont());
        userRepositoryInfo.setAlignmentX(CENTER_ALIGNMENT);

        add(userInfo);
        add(userRepositoryInfo);
    }

    private void addBackButton() {
        final DoorkeyButton back = new DoorkeyButton.DoorkeyButtonBuilder("< Back")
                .addListener(event -> {
                    viewManagerModel.setState(ViewConstants.LOGIN_VIEW);
                    viewManagerModel.onStateChanged();
                }).build();
        this.add(back);
    }

    private void addVaultItems() {
        itemsLabel.setForeground(Color.WHITE);
        itemsLabel.setFont(new DoorkeyFont());
        itemsLabel.setAlignmentX(CENTER_ALIGNMENT);

        add(itemsLabel);
    }
}
