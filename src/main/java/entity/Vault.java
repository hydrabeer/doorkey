package entity;

import java.util.List;

public abstract class Vault {
    private List<VaultItem> items;

    public Vault(List<VaultItem> items) {
        this.items = items;
    }
}
