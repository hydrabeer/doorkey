package service.url_redirect;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Desktop wrapper used by urlRedirectInteractor.
 */
public class RealDesktopWrapper implements DesktopWrapper {
    private final Desktop desktop;

    public RealDesktopWrapper() {
        this.desktop = Desktop.getDesktop();
    }

    @Override
    public boolean isSupported(Desktop.Action action) {
        return desktop.isSupported(action);
    }

    @Override
    public void browse(URI uri) throws IOException, SecurityException {
        desktop.browse(uri);
    }
}
