/*
 * Copyright (c) 1997, 2024 Oracle and/or its affiliates and others.
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

import jakarta.servlet.ServletResponseWrapper;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 * Provides a convenient implementation of the HttpServletResponse interface that can be subclassed by developers
 * wishing to adapt the response from a Servlet. This class implements the Wrapper or Decorator pattern. Methods default
 * to calling through to the wrapped response object.
 *
 * @author Various
 * @since Servlet 2.3
 *
 * @see jakarta.servlet.http.HttpServletResponse
 */
public class HttpServletResponseWrapper extends ServletResponseWrapper implements HttpServletResponse {

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response the {@link HttpServletResponse} to be wrapped.
     *
     * @throws java.lang.IllegalArgumentException if the response is null
     */
    public HttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    private HttpServletResponse _getHttpServletResponse() {
        return (HttpServletResponse) super.getResponse();
    }

    /**
     * The default behavior of this method is to call addCookie(Cookie cookie) on the wrapped response object.
     */
    @Override
    public void addCookie(Cookie cookie) {
        this._getHttpServletResponse().addCookie(cookie);
    }

    /**
     * The default behavior of this method is to call containsHeader(String name) on the wrapped response object.
     */
    @Override
    public boolean containsHeader(String name) {
        return this._getHttpServletResponse().containsHeader(name);
    }

    /**
     * The default behavior of this method is to call encodeURL(String url) on the wrapped response object.
     */
    @Override
    public String encodeURL(String url) {
        return this._getHttpServletResponse().encodeURL(url);
    }

    /**
     * The default behavior of this method is to return encodeRedirectURL(String url) on the wrapped response object.
     */
    @Override
    public String encodeRedirectURL(String url) {
        return this._getHttpServletResponse().encodeRedirectURL(url);
    }

    /**
     * The default behavior of this method is to call sendError(int sc, String msg) on the wrapped response object.
     */
    @Override
    public void sendError(int sc, String msg) throws IOException {
        this._getHttpServletResponse().sendError(sc, msg);
    }

    /**
     * The default behavior of this method is to call sendError(int sc) on the wrapped response object.
     */
    @Override
    public void sendError(int sc) throws IOException {
        this._getHttpServletResponse().sendError(sc);
    }

    /**
     * The default behavior of this method is to return sendRedirect(String location) on the wrapped response object.
     */
    @Override
    public void sendRedirect(String location) throws IOException {
        this._getHttpServletResponse().sendRedirect(location);
    }

    /**
     * The default behavior of this method is to return sendRedirect(String location, int sc) on the wrapped response
     * object.
     *
     * @since Servlet 6.1
     */
    @Override
    public void sendRedirect(String location, int sc) throws IOException {
        this._getHttpServletResponse().sendRedirect(location, sc);
    }

    /**
     * The default behavior of this method is to return sendRedirect(String location, boolean clearBuffer) on the wrapped
     * response object.
     *
     * @since Servlet 6.1
     */
    @Override
    public void sendRedirect(String location, boolean clearBuffer) throws IOException {
        this._getHttpServletResponse().sendRedirect(location, clearBuffer);
    }

    /**
     * The default behavior of this method is to return sendRedirect(String location, int sc, boolean clearBuffer) on the
     * wrapped response object.
     *
     * @since Servlet 6.1
     */
    @Override
    public void sendRedirect(String location, int sc, boolean clearBuffer) throws IOException {
        this._getHttpServletResponse().sendRedirect(location, sc, clearBuffer);
    }

    /**
     * The default behavior of this method is to call sendEarlyHints() on the wrapped response object.
     *
     * @since Servlet 6.2
     */
    @Override
    public void sendEarlyHints() {
        this._getHttpServletResponse().sendEarlyHints();
    }

    /**
     * The default behavior of this method is to call setDateHeader(String name, long date) on the wrapped response object.
     */
    @Override
    public void setDateHeader(String name, long date) {
        this._getHttpServletResponse().setDateHeader(name, date);
    }

    /**
     * The default behavior of this method is to call addDateHeader(String name, long date) on the wrapped response object.
     */
    @Override
    public void addDateHeader(String name, long date) {
        this._getHttpServletResponse().addDateHeader(name, date);
    }

    /**
     * The default behavior of this method is to return setHeader(String name, String value) on the wrapped response object.
     */
    @Override
    public void setHeader(String name, String value) {
        this._getHttpServletResponse().setHeader(name, value);
    }

    /**
     * The default behavior of this method is to return addHeader(String name, String value) on the wrapped response object.
     */
    @Override
    public void addHeader(String name, String value) {
        this._getHttpServletResponse().addHeader(name, value);
    }

    /**
     * The default behavior of this method is to call setIntHeader(String name, int value) on the wrapped response object.
     */
    @Override
    public void setIntHeader(String name, int value) {
        this._getHttpServletResponse().setIntHeader(name, value);
    }

    /**
     * The default behavior of this method is to call addIntHeader(String name, int value) on the wrapped response object.
     */
    @Override
    public void addIntHeader(String name, int value) {
        this._getHttpServletResponse().addIntHeader(name, value);
    }

    /**
     * The default behavior of this method is to call setStatus(int sc) on the wrapped response object.
     */
    @Override
    public void setStatus(int sc) {
        this._getHttpServletResponse().setStatus(sc);
    }

    /**
     * The default behaviour of this method is to call {@link HttpServletResponse#getStatus} on the wrapped response object.
     *
     * @return the current status code of the wrapped response
     *
     * @since Servlet 3.0
     */
    @Override
    public int getStatus() {
        return _getHttpServletResponse().getStatus();
    }

    /**
     * The default behaviour of this method is to call {@link HttpServletResponse#getHeader} on the wrapped response object.
     *
     * @param name the name of the response header whose value to return
     *
     * @return the value of the response header with the given name, or <tt>null</tt> if no header with the given name has
     * been set on the wrapped response
     *
     * @since Servlet 3.0
     */
    @Override
    public String getHeader(String name) {
        return _getHttpServletResponse().getHeader(name);
    }

    /**
     * The default behaviour of this method is to call {@link HttpServletResponse#getHeaders} on the wrapped response
     * object.
     *
     * <p>
     * Any changes to the returned <code>Collection</code> must not affect this <code>HttpServletResponseWrapper</code>.
     *
     * @param name the name of the response header whose values to return
     *
     * @return a (possibly empty) <code>Collection</code> of the values of the response header with the given name
     *
     * @since Servlet 3.0
     */
    @Override
    public Collection<String> getHeaders(String name) {
        return _getHttpServletResponse().getHeaders(name);
    }

    /**
     * The default behaviour of this method is to call {@link HttpServletResponse#getHeaderNames} on the wrapped response
     * object.
     *
     * <p>
     * Any changes to the returned <code>Collection</code> must not affect this <code>HttpServletResponseWrapper</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the names of the response headers
     *
     * @since Servlet 3.0
     */
    @Override
    public Collection<String> getHeaderNames() {
        return _getHttpServletResponse().getHeaderNames();
    }

    /**
     * The default behaviour of this method is to call {@link HttpServletResponse#setTrailerFields} on the wrapped response
     * object.
     *
     * @param supplier of trailer headers
     *
     * @since Servlet 4.0
     */
    @Override
    public void setTrailerFields(Supplier<Map<String, String>> supplier) {
        _getHttpServletResponse().setTrailerFields(supplier);
    }

    /**
     * The default behaviour of this method is to call {@link HttpServletResponse#getTrailerFields} on the wrapped response
     * object.
     *
     * @return supplier of trailer headers
     *
     * @since Servlet 4.0
     */
    @Override
    public Supplier<Map<String, String>> getTrailerFields() {
        return _getHttpServletResponse().getTrailerFields();
    }
}
