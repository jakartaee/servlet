package jakarta.servlet;

import java.util.EventListener;

/**
 * A listener that allows an application to be notified of a problem with the underlying connection.
 *
 * @since Servlet 6.0
 */
public interface ConnectionFailureListener extends EventListener {

    /**
     * This method is invoked on a best effort basis if the underlying connection has gone away.
     *
     * This method will generally be invoked from a different thread to any other threads currently running in the
     * application, so any attempt to stop application processing as a result of this notification should be done in a
     * thread safe manner.
     *
     * @param details Information about the failure
     */
    void onConnectionFailure(ConnectionFailureDetails details);
}
