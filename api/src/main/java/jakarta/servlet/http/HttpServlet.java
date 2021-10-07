/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates and others.
 * All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jakarta.servlet.http;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 *
 * Provides an abstract class to be subclassed to create an HTTP servlet suitable for a Web site. A subclass of
 * <code>HttpServlet</code> must override at least one method, usually one of these:
 *
 * <ul>
 * <li><code>doGet</code>, if the servlet supports HTTP GET requests
 * <li><code>doPost</code>, for HTTP POST requests
 * <li><code>doPut</code>, for HTTP PUT requests
 * <li><code>doDelete</code>, for HTTP DELETE requests
 * <li><code>init</code> and <code>destroy</code>, to manage resources that are held for the life of the servlet
 * <li><code>getServletInfo</code>, which the servlet uses to provide information about itself
 * </ul>
 *
 * <p>
 * There's almost no reason to override the <code>service</code> method. <code>service</code> handles standard HTTP
 * requests by dispatching them to the handler methods for each HTTP request type (the <code>do</code><i>XXX</i> methods
 * listed above).
 *
 * <p>
 * Likewise, there's almost no reason to override the <code>doOptions</code> and <code>doTrace</code> methods.
 * 
 * <p>
 * Servlets typically run on multithreaded servers, so be aware that a servlet must handle concurrent requests and be
 * careful to synchronize access to shared resources. Shared resources include in-memory data such as instance or class
 * variables and external objects such as files, database connections, and network connections. See the
 * <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/"> Java Tutorial on Multithreaded
 * Programming</a> for more information on handling multiple threads in a Java program.
 *
 * @author Various
 */
public abstract class HttpServlet extends GenericServlet {
    private static final long serialVersionUID = 8466325577512134784L;

    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_TRACE = "TRACE";

    private static final String HEADER_IFMODSINCE = "If-Modified-Since";
    private static final String HEADER_LASTMOD = "Last-Modified";

    /**
     * The parameter obtained {@link ServletConfig#getInitParameter(String)} to determine if legacy processing of
     * {@link #doHead(HttpServletRequest, HttpServletResponse)} is provided.
     * 
     * @deprecated may be removed in future releases
     * @since 6.0
     */
    @Deprecated
    public static final String LEGACY_DO_HEAD = "jakarta.servlet.http.legacyDoHead";

    private static final String LSTRING_FILE = "jakarta.servlet.http.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    private boolean legacyHeadHandling;

    /**
     * Does nothing, because this is an abstract class.
     * 
     */

    public HttpServlet() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        legacyHeadHandling = Boolean.parseBoolean(config.getInitParameter(LEGACY_DO_HEAD));
    }

    /**
     *
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a GET request.
     *
     * <p>
     * Overriding this method to support a GET request also automatically supports an HTTP HEAD request. A HEAD request is a
     * GET request that returns no body in the response, only the request header fields.
     *
     * <p>
     * When overriding this method, read the request data, write the response headers, get the response's writer or output
     * stream object, and finally, write the response data. It's best to include content type and encoding. When using a
     * <code>PrintWriter</code> object to return the response, set the content type before accessing the
     * <code>PrintWriter</code> object.
     *
     * <p>
     * The servlet container must write the headers before committing the response, because in HTTP the headers must be sent
     * before the response body.
     *
     * <p>
     * Where possible, set the Content-Length header (with the {@link jakarta.servlet.ServletResponse#setContentLength}
     * method), to allow the servlet container to use a persistent connection to return its response to the client,
     * improving performance. The content length is automatically set if the entire response fits inside the response
     * buffer.
     *
     * <p>
     * When using HTTP 1.1 chunked encoding (which means that the response has a Transfer-Encoding header), do not set the
     * Content-Length header.
     *
     * <p>
     * The GET method should be safe, that is, without any side effects for which users are held responsible. For example,
     * most form queries have no side effects. If a client request is intended to change stored data, the request should use
     * some other HTTP method.
     *
     * <p>
     * The GET method should also be idempotent, meaning that it can be safely repeated. Sometimes making a method safe also
     * makes it idempotent. For example, repeating queries is both safe and idempotent, but buying a product online or
     * modifying data is neither safe nor idempotent.
     *
     * <p>
     * If the request is incorrectly formatted, <code>doGet</code> returns an HTTP "Bad Request" message.
     * 
     * @param req an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     *
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * 
     * @throws IOException if an input or output error is detected when the servlet handles the GET request
     *
     * @throws ServletException if the request for the GET could not be handled
     *
     * @see jakarta.servlet.ServletResponse#setContentType
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_get_not_supported");
        resp.sendError(getMethodNotSupportedCode(protocol), msg);
    }

    /**
     *
     * Returns the time the <code>HttpServletRequest</code> object was last modified, in milliseconds since midnight January
     * 1, 1970 GMT. If the time is unknown, this method returns a negative number (the default).
     *
     * <p>
     * Servlets that support HTTP GET requests and can quickly determine their last modification time should override this
     * method. This makes browser and proxy caches work more effectively, reducing the load on server and network resources.
     *
     * @param req the <code>HttpServletRequest</code> object that is sent to the servlet
     *
     * @return a <code>long</code> integer specifying the time the <code>HttpServletRequest</code> object was last modified,
     * in milliseconds since midnight, January 1, 1970 GMT, or -1 if the time is not known
     */
    protected long getLastModified(HttpServletRequest req) {
        return -1;
    }

    /**
     * 
     *
     * <p>
     * Receives an HTTP HEAD request from the protected <code>service</code> method and handles the request. The client
     * sends a HEAD request when it wants to see only the headers of a response, such as Content-Type or Content-Length. The
     * HTTP HEAD method counts the output bytes in the response to set the Content-Length header accurately.
     *
     * <p>
     * If you override this method, you can avoid computing the response body and just set the response headers directly to
     * improve performance. Make sure that the <code>doHead</code> method you write is both safe and idempotent (that is,
     * protects itself from being called multiple times for one HTTP HEAD request).
     *
     * <p>
     * The default implementation calls {@link #doGet(HttpServletRequest, HttpServletResponse)}. If the
     * {@link ServletConfig} init parameter {@link #LEGACY_DO_HEAD} is set to "TRUE", then the response instance is wrapped
     * so that the response body is discarded.
     *
     * <p>
     * If the HTTP HEAD request is incorrectly formatted, <code>doHead</code> returns an HTTP "Bad Request" message.
     *
     * @param req the request object that is passed to the servlet
     * 
     * @param resp the response object that the servlet uses to return the headers to the clien
     *
     * @throws IOException if an input or output error occurs
     *
     * @throws ServletException if the request for the HEAD could not be handled
     */
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (legacyHeadHandling) {
            NoBodyResponse response = new NoBodyResponse(resp);
            doGet(req, response);
            response.setContentLength();
        } else {
            doGet(req, resp);
        }
    }

    /**
     *
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a POST request.
     *
     * The HTTP POST method allows the client to send data of unlimited length to the Web server a single time and is useful
     * when posting information such as credit card numbers.
     *
     * <p>
     * When overriding this method, read the request data, write the response headers, get the response's writer or output
     * stream object, and finally, write the response data. It's best to include content type and encoding. When using a
     * <code>PrintWriter</code> object to return the response, set the content type before accessing the
     * <code>PrintWriter</code> object.
     *
     * <p>
     * The servlet container must write the headers before committing the response, because in HTTP the headers must be sent
     * before the response body.
     *
     * <p>
     * Where possible, set the Content-Length header (with the {@link jakarta.servlet.ServletResponse#setContentLength}
     * method), to allow the servlet container to use a persistent connection to return its response to the client,
     * improving performance. The content length is automatically set if the entire response fits inside the response
     * buffer.
     *
     * <p>
     * When using HTTP 1.1 chunked encoding (which means that the response has a Transfer-Encoding header), do not set the
     * Content-Length header.
     *
     * <p>
     * This method does not need to be either safe or idempotent. Operations requested through POST can have side effects
     * for which the user can be held accountable, for example, updating stored data or buying items online.
     *
     * <p>
     * If the HTTP POST request is incorrectly formatted, <code>doPost</code> returns an HTTP "Bad Request" message.
     *
     *
     * @param req an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     *
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * 
     * @throws IOException if an input or output error is detected when the servlet handles the request
     *
     * @throws ServletException if the request for the POST could not be handled
     *
     * @see jakarta.servlet.ServletOutputStream
     * @see jakarta.servlet.ServletResponse#setContentType
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_post_not_supported");
        resp.sendError(getMethodNotSupportedCode(protocol), msg);
    }

    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a PUT request.
     *
     * The PUT operation allows a client to place a file on the server and is similar to sending a file by FTP.
     *
     * <p>
     * When overriding this method, leave intact any content headers sent with the request (including Content-Length,
     * Content-Type, Content-Transfer-Encoding, Content-Encoding, Content-Base, Content-Language, Content-Location,
     * Content-MD5, and Content-Range). If your method cannot handle a content header, it must issue an error message (HTTP
     * 501 - Not Implemented) and discard the request. For more information on HTTP 1.1, see RFC 2616
     * <a href="http://www.ietf.org/rfc/rfc2616.txt"></a>.
     *
     * <p>
     * This method does not need to be either safe or idempotent. Operations that <code>doPut</code> performs can have side
     * effects for which the user can be held accountable. When using this method, it may be useful to save a copy of the
     * affected URL in temporary storage.
     *
     * <p>
     * If the HTTP PUT request is incorrectly formatted, <code>doPut</code> returns an HTTP "Bad Request" message.
     *
     * @param req the {@link HttpServletRequest} object that contains the request the client made of the servlet
     *
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     *
     * @throws IOException if an input or output error occurs while the servlet is handling the PUT request
     *
     * @throws ServletException if the request for the PUT cannot be handled
     */
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_put_not_supported");
        resp.sendError(getMethodNotSupportedCode(protocol), msg);
    }

    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a DELETE request.
     *
     * The DELETE operation allows a client to remove a document or Web page from the server.
     * 
     * <p>
     * This method does not need to be either safe or idempotent. Operations requested through DELETE can have side effects
     * for which users can be held accountable. When using this method, it may be useful to save a copy of the affected URL
     * in temporary storage.
     *
     * <p>
     * If the HTTP DELETE request is incorrectly formatted, <code>doDelete</code> returns an HTTP "Bad Request" message.
     *
     * @param req the {@link HttpServletRequest} object that contains the request the client made of the servlet
     *
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     *
     * @throws IOException if an input or output error occurs while the servlet is handling the DELETE request
     *
     * @throws ServletException if the request for the DELETE cannot be handled
     */
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_delete_not_supported");
        resp.sendError(getMethodNotSupportedCode(protocol), msg);
    }

    private int getMethodNotSupportedCode(String protocol) {
        switch (protocol) {
        case "HTTP/0.9":
        case "HTTP/1.0":
            return HttpServletResponse.SC_BAD_REQUEST;
        default:
            return HttpServletResponse.SC_METHOD_NOT_ALLOWED;
        }
    }

    private Method[] getAllDeclaredMethods(Class<? extends HttpServlet> c) {

        Class<?> clazz = c;
        Method[] allMethods = null;

        while (!clazz.equals(HttpServlet.class)) {
            Method[] thisMethods = clazz.getDeclaredMethods();
            if (allMethods != null && allMethods.length > 0) {
                Method[] subClassMethods = allMethods;
                allMethods = new Method[thisMethods.length + subClassMethods.length];
                System.arraycopy(thisMethods, 0, allMethods, 0, thisMethods.length);
                System.arraycopy(subClassMethods, 0, allMethods, thisMethods.length, subClassMethods.length);
            } else {
                allMethods = thisMethods;
            }

            clazz = clazz.getSuperclass();
        }

        return ((allMethods != null) ? allMethods : new Method[0]);
    }

    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a OPTIONS request.
     *
     * The OPTIONS request determines which HTTP methods the server supports and returns an appropriate header. For example,
     * if a servlet overrides <code>doGet</code>, this method returns the following header:
     *
     * <p>
     * <code>Allow: GET, HEAD, TRACE, OPTIONS</code>
     *
     * <p>
     * There's no need to override this method unless the servlet implements new HTTP methods, beyond those implemented by
     * HTTP 1.1.
     *
     * @param req the {@link HttpServletRequest} object that contains the request the client made of the servlet
     *
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     *
     * @throws IOException if an input or output error occurs while the servlet is handling the OPTIONS request
     *
     * @throws ServletException if the request for the OPTIONS cannot be handled
     */
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Method[] methods = getAllDeclaredMethods(this.getClass());

        boolean ALLOW_GET = false;
        boolean ALLOW_HEAD = false;
        boolean ALLOW_POST = false;
        boolean ALLOW_PUT = false;
        boolean ALLOW_DELETE = false;
        boolean ALLOW_TRACE = true;
        boolean ALLOW_OPTIONS = true;

        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();

            if (methodName.equals("doGet")) {
                ALLOW_GET = true;
                ALLOW_HEAD = true;
            } else if (methodName.equals("doPost")) {
                ALLOW_POST = true;
            } else if (methodName.equals("doPut")) {
                ALLOW_PUT = true;
            } else if (methodName.equals("doDelete")) {
                ALLOW_DELETE = true;
            }

        }

        // we know "allow" is not null as ALLOW_OPTIONS = true
        // when this method is invoked
        StringBuilder allow = new StringBuilder();
        if (ALLOW_GET) {
            allow.append(METHOD_GET);
        }
        if (ALLOW_HEAD) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_HEAD);
        }
        if (ALLOW_POST) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_POST);
        }
        if (ALLOW_PUT) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_PUT);
        }
        if (ALLOW_DELETE) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_DELETE);
        }
        if (ALLOW_TRACE) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_TRACE);
        }
        if (ALLOW_OPTIONS) {
            if (allow.length() > 0) {
                allow.append(", ");
            }
            allow.append(METHOD_OPTIONS);
        }

        resp.setHeader("Allow", allow.toString());
    }

    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a TRACE request.
     *
     * A TRACE returns the headers sent with the TRACE request to the client, so that they can be used in debugging. There's
     * no need to override this method.
     *
     * @param req the {@link HttpServletRequest} object that contains the request the client made of the servlet
     *
     *
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     *
     * @throws IOException if an input or output error occurs while the servlet is handling the TRACE request
     *
     * @throws ServletException if the request for the TRACE cannot be handled
     */
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int responseLength;

        String CRLF = "\r\n";
        StringBuilder buffer = new StringBuilder("TRACE ").append(req.getRequestURI()).append(" ")
                .append(req.getProtocol());

        Enumeration<String> reqHeaderEnum = req.getHeaderNames();

        while (reqHeaderEnum.hasMoreElements()) {
            String headerName = reqHeaderEnum.nextElement();
            buffer.append(CRLF).append(headerName).append(": ").append(req.getHeader(headerName));
        }

        buffer.append(CRLF);

        responseLength = buffer.length();

        resp.setContentType("message/http");
        resp.setContentLength(responseLength);
        ServletOutputStream out = resp.getOutputStream();
        out.print(buffer.toString());
    }

    /**
     * Receives standard HTTP requests from the public <code>service</code> method and dispatches them to the
     * <code>do</code><i>XXX</i> methods defined in this class. This method is an HTTP-specific version of the
     * {@link jakarta.servlet.Servlet#service} method. There's no need to override this method.
     *
     * @param req the {@link HttpServletRequest} object that contains the request the client made of the servlet
     *
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     *
     * @throws IOException if an input or output error occurs while the servlet is handling the HTTP request
     *
     * @throws ServletException if the HTTP request cannot be handled
     * 
     * @see jakarta.servlet.Servlet#service
     */
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();

        if (method.equals(METHOD_GET)) {
            long lastModified = getLastModified(req);
            if (lastModified == -1) {
                // servlet doesn't support if-modified-since, no reason
                // to go through further expensive logic
                doGet(req, resp);
            } else {
                long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
                if (ifModifiedSince < lastModified) {
                    // If the servlet mod time is later, call doGet()
                    // Round down to the nearest second for a proper compare
                    // A ifModifiedSince of -1 will always be less
                    maybeSetLastModified(resp, lastModified);
                    doGet(req, resp);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                }
            }

        } else if (method.equals(METHOD_HEAD)) {
            long lastModified = getLastModified(req);
            maybeSetLastModified(resp, lastModified);
            doHead(req, resp);

        } else if (method.equals(METHOD_POST)) {
            doPost(req, resp);

        } else if (method.equals(METHOD_PUT)) {
            doPut(req, resp);

        } else if (method.equals(METHOD_DELETE)) {
            doDelete(req, resp);

        } else if (method.equals(METHOD_OPTIONS)) {
            doOptions(req, resp);

        } else if (method.equals(METHOD_TRACE)) {
            doTrace(req, resp);

        } else {
            //
            // Note that this means NO servlet supports whatever
            // method was requested, anywhere on this server.
            //

            String errMsg = lStrings.getString("http.method_not_implemented");
            Object[] errArgs = new Object[1];
            errArgs[0] = method;
            errMsg = MessageFormat.format(errMsg, errArgs);

            resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
        }
    }

    /*
     * Sets the Last-Modified entity header field, if it has not already been set and if the value is meaningful. Called
     * before doGet, to ensure that headers are set before response data is written. A subclass might have set this header
     * already, so we check.
     */
    private void maybeSetLastModified(HttpServletResponse resp, long lastModified) {
        if (resp.containsHeader(HEADER_LASTMOD))
            return;
        if (lastModified >= 0)
            resp.setDateHeader(HEADER_LASTMOD, lastModified);
    }

    /**
     * Dispatches client requests to the protected <code>service</code> method. There's no need to override this method.
     * 
     * @param req the {@link HttpServletRequest} object that contains the request the client made of the servlet
     *
     * @param res the {@link HttpServletResponse} object that contains the response the servlet returns to the client
     *
     * @throws IOException if an input or output error occurs while the servlet is handling the HTTP request
     *
     * @throws ServletException if the HTTP request cannot be handled or if either parameter is not an instance of its
     * respective {@link HttpServletRequest} or {@link HttpServletResponse} counterparts.
     * 
     * @see jakarta.servlet.Servlet#service
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request;
        HttpServletResponse response;

        if (!(req instanceof HttpServletRequest && res instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        request = (HttpServletRequest) req;
        response = (HttpServletResponse) res;

        service(request, response);
    }
}

/*
 * A response that includes no body, for use in (dumb) "HEAD" support. This just swallows that body, counting the bytes
 * in order to set the content length appropriately. All other methods delegate directly to the wrapped HTTP Servlet
 * Response object.
 */
// file private
@Deprecated
class NoBodyResponse extends HttpServletResponseWrapper {

    private static final ResourceBundle lStrings = ResourceBundle.getBundle("jakarta.servlet.http.LocalStrings");

    private NoBodyOutputStream noBody;
    private PrintWriter writer;
    private boolean didSetContentLength;
    private boolean usingOutputStream;

    // file private
    NoBodyResponse(HttpServletResponse r) {
        super(r);
        noBody = new NoBodyOutputStream();
    }

    // file private
    void setContentLength() {
        if (!didSetContentLength) {
            if (writer != null) {
                writer.flush();
            }
            setContentLength(noBody.getContentLength());
        }
    }

    @Override
    public void setContentLength(int len) {
        super.setContentLength(len);
        didSetContentLength = true;
    }

    @Override
    public void setContentLengthLong(long len) {
        super.setContentLengthLong(len);
        didSetContentLength = true;
    }

    @Override
    public void setHeader(String name, String value) {
        super.setHeader(name, value);
        checkHeader(name);
    }

    @Override
    public void addHeader(String name, String value) {
        super.addHeader(name, value);
        checkHeader(name);
    }

    @Override
    public void setIntHeader(String name, int value) {
        super.setIntHeader(name, value);
        checkHeader(name);
    }

    @Override
    public void addIntHeader(String name, int value) {
        super.addIntHeader(name, value);
        checkHeader(name);
    }

    private void checkHeader(String name) {
        if ("content-length".equalsIgnoreCase(name)) {
            didSetContentLength = true;
        }
    }

    @Override
    public void reset() {
        super.reset();
        noBody.reset();
        usingOutputStream = false;
        writer = null;
        didSetContentLength = false;
    }

    @Override
    public void resetBuffer() {
        super.resetBuffer();
        if (writer != null) {
            try {
                NoBodyOutputStream.disableFlush.set(Boolean.TRUE);
                writer.flush();
            } finally {
                NoBodyOutputStream.disableFlush.remove();
            }
        }
        noBody.reset();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {

        if (writer != null) {
            throw new IllegalStateException(lStrings.getString("err.ise.getOutputStream"));
        }
        usingOutputStream = true;

        return noBody;
    }

    @Override
    public PrintWriter getWriter() throws UnsupportedEncodingException {

        if (usingOutputStream) {
            throw new IllegalStateException(lStrings.getString("err.ise.getWriter"));
        }

        if (writer == null) {
            OutputStreamWriter w = new OutputStreamWriter(noBody, getCharacterEncoding());
            writer = new PrintWriter(w);
        }

        return writer;
    }
}

/*
 * Servlet output stream that gobbles up all its data.
 */
// file private
@Deprecated
class NoBodyOutputStream extends ServletOutputStream {

    private static final String LSTRING_FILE = "jakarta.servlet.http.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);
    static ThreadLocal<Boolean> disableFlush = new ThreadLocal<>();

    private int contentLength = 0;

    // file private
    NoBodyOutputStream() {
    }

    void reset() {
        contentLength = 0;
    }

    // file private
    int getContentLength() {
        return contentLength;
    }

    @Override
    public void write(int b) {
        contentLength++;
    }

    @Override
    public void write(byte buf[], int offset, int len) throws IOException {
        if (buf == null) {
            throw new NullPointerException(lStrings.getString("err.io.nullArray"));
        }

        if (offset < 0 || len < 0 || offset + len > buf.length) {
            String msg = lStrings.getString("err.io.indexOutOfBounds");
            Object[] msgArgs = new Object[3];
            msgArgs[0] = Integer.valueOf(offset);
            msgArgs[1] = Integer.valueOf(len);
            msgArgs[2] = Integer.valueOf(buf.length);
            msg = MessageFormat.format(msg, msgArgs);
            throw new IndexOutOfBoundsException(msg);
        }

        contentLength += len;
    }

    @Override
    public void flush() throws IOException {
        if (Boolean.TRUE.equals(disableFlush.get()))
            super.flush();
    }

    @Override
    public boolean isReady() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        throw new UnsupportedOperationException();
    }
}
