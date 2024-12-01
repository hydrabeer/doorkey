package mock;

import service.url_redirect.DesktopWrapper;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MockDesktopWrapper implements DesktopWrapper {
    private final Desktop desktop;
    private Boolean isSupported;
    private IOException ioException;
    private SecurityException securityException;

    public MockDesktopWrapper(Boolean isSupported) {
        this.desktop = Desktop.getDesktop();
        this.isSupported = isSupported;
        this.ioException = null;
        this.securityException = null;
    }

    public MockDesktopWrapper(Boolean isSupported, IOException ioException) {
        this.desktop = Desktop.getDesktop();
        this.isSupported = isSupported;
        this.ioException = ioException;
        this.securityException = null;
    }

    public MockDesktopWrapper(Boolean isSupported, SecurityException securityException) {
        this.desktop = Desktop.getDesktop();
        this.isSupported = isSupported;
        this.securityException = securityException;
        this.ioException = null;
    }


    @Override
    public boolean isSupported(Desktop.Action action) {
        return isSupported;
    }

    @Override
    public void browse(URI uri) throws IOException, SecurityException {
        if (this.ioException != null) {
            throw this.ioException;
        }
        else if (this.securityException != null) {
            throw securityException;
        }
        else {
            desktop.browse(uri);
        }
    }
}
