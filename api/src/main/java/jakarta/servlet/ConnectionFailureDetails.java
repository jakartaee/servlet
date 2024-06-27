package jakarta.servlet;

import java.io.IOException;
import java.util.Optional;

/**
 * An interface that carries information about why a connection has failed.
 *
 * @since Servlet 6.0
 */
public interface ConnectionFailureDetails {

    /**
     * Returns an optional cause of the underlying failure. If the failure was caused by a protocol level mechanism rather
     * than a failure then this may be missing.
     *
     * @return The cause of the failure
     */
    Optional<IOException> cause();

    /**
     * If this is {@code true} the stream was explicitly reset by the remote endpoint, if this is false then there has been
     * a connection level failure rather than the client simply closing this stream.
     *
     * @return {@code true} if the stream was explicitly reset by the remote endpoint
     */
    boolean isStreamReset();
}
