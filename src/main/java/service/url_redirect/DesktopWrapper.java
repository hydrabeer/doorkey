package service.url_redirect;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

/**
 * Interface that RealDesktopWrapper implements.
 * Desktop wrapper so mock testing can be implented.
 */
public interface DesktopWrapper {

    /**
     * Opens URL in web browser.
     * @param action is a specified desktop action.
     * @return returns boolean if action is supported.
     */
    boolean isSupported(Desktop.Action action);

    /**
     * Opens URL in web browser.
     * @param uri is a specified desktop action.
     * @throws IOException If there is a problem with opening the url.
     * @throws SecurityException If there is a security issue opening the url.
     */
    void browse(URI uri) throws IOException, SecurityException;
}
