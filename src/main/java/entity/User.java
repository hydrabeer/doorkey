package entity;

public abstract class User {
    String name;
    Vault vault;

    public User(String name, Vault vault) {
        this.name = name;
        this.vault = vault;
    }
}
