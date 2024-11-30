package entity;

import interface_adapter.net.auth.RemoteAuth;

/**
 * Cloud user entity.
 */
public class CloudUser extends AbstractUser {
    private RemoteAuth remoteAuth;

    public CloudUser(
            String email,
            String password,
            AbstractVault vault,
            RemoteAuth auth
    ) {
        super(email, password, vault);
        this.remoteAuth = auth;
    }

    public RemoteAuth getRemoteAuth() {
        return this.remoteAuth;
    }

    public void setRemoteAuth(RemoteAuth auth) {
        this.remoteAuth = auth;
    }
}
