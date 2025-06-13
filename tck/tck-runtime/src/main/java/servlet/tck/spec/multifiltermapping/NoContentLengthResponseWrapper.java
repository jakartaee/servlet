package servlet.tck.spec.multifiltermapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

/*
 * Response wrapper that blocks the setting of the content length.
 */
public class NoContentLengthResponseWrapper extends HttpServletResponseWrapper {

    public NoContentLengthResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void setContentLength(int len) {
        // NO-OP
    }

    @Override
    public void setContentLengthLong(long len) {
        // NO-OP
    }


    @Override
    public void setHeader(String name, String value) {
        if (isContentLength(name)) {
            // NO-OP
            return;
        }
        super.setHeader(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        if (isContentLength(name)) {
            // NO-OP
            return;
        }
        super.addHeader(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        if (isContentLength(name)) {
            // NO-OP
            return;
        }
        super.setIntHeader(name, value);
    }

    @Override
    public void addIntHeader(String name, int value) {
        if (isContentLength(name)) {
            // NO-OP
            return;
        }
        super.addIntHeader(name, value);
    }

    private boolean isContentLength(String headerName) {
        return "content-length".equalsIgnoreCase(headerName);
    }
}
