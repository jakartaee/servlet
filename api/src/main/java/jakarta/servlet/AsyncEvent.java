/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package jakarta.servlet;

/**
 * Event that gets fired when the asynchronous operation initiated on a ServletRequest (via a call to
 * {@link ServletRequest#startAsync} or {@link ServletRequest#startAsync(ServletRequest, ServletResponse)}) has
 * completed, timed out, or produced an error.
 *
 * @since Servlet 3.0
 */
public class AsyncEvent {

    private final AsyncContext context;
    private final ServletRequest request;
    private final ServletResponse response;
    private final Throwable throwable;

    /**
     * Constructs an AsyncEvent from the given AsyncContext.
     *
     * @param context the AsyncContex to be delivered with this AsyncEvent
     */
    public AsyncEvent(AsyncContext context) {
        this(context, context.getRequest(), context.getResponse(), null);
    }

    /**
     * Constructs an AsyncEvent from the given AsyncContext, ServletRequest, and ServletResponse.
     *
     * @param context the AsyncContex to be delivered with this AsyncEvent
     * @param request the ServletRequest to be delivered with this AsyncEvent
     * @param response the ServletResponse to be delivered with this AsyncEvent
     */
    public AsyncEvent(AsyncContext context, ServletRequest request, ServletResponse response) {
        this(context, request, response, null);
    }

    /**
     * Constructs an AsyncEvent from the given AsyncContext and Throwable.
     *
     * @param context the AsyncContex to be delivered with this AsyncEvent
     * @param throwable the Throwable to be delivered with this AsyncEvent
     */
    public AsyncEvent(AsyncContext context, Throwable throwable) {
        this(context, context.getRequest(), context.getResponse(), throwable);
    }

    /**
     * Constructs an AsyncEvent from the given AsyncContext, ServletRequest, ServletResponse, and Throwable.
     *
     * @param context the AsyncContex to be delivered with this AsyncEvent
     * @param request the ServletRequest to be delivered with this AsyncEvent
     * @param response the ServletResponse to be delivered with this AsyncEvent
     * @param throwable the Throwable to be delivered with this AsyncEvent
     */
    public AsyncEvent(AsyncContext context, ServletRequest request, ServletResponse response, Throwable throwable) {
        this.context = context;
        this.request = request;
        this.response = response;
        this.throwable = throwable;
    }

    /**
     * Gets the AsyncContext from this AsyncEvent.
     *
     * @return the AsyncContext that was used to initialize this AsyncEvent
     */
    public AsyncContext getAsyncContext() {
        return context;
    }

    /**
     * Gets the ServletRequest from this AsyncEvent.
     *
     * <p>
     * If the AsyncListener to which this AsyncEvent is being delivered was added using
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}, the returned ServletRequest will be
     * the same as the one supplied to the above method. If the AsyncListener was added via
     * {@link AsyncContext#addListener(AsyncListener)}, this method must return null.
     *
     * @return the ServletRequest that was used to initialize this AsyncEvent, or null if this AsyncEvent was initialized
     * without any ServletRequest
     */
    public ServletRequest getSuppliedRequest() {
        return request;
    }

    /**
     * Gets the ServletResponse from this AsyncEvent.
     *
     * <p>
     * If the AsyncListener to which this AsyncEvent is being delivered was added using
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}, the returned ServletResponse will
     * be the same as the one supplied to the above method. If the AsyncListener was added via
     * {@link AsyncContext#addListener(AsyncListener)}, this method must return null.
     *
     * @return the ServletResponse that was used to initialize this AsyncEvent, or null if this AsyncEvent was initialized
     * without any ServletResponse
     */
    public ServletResponse getSuppliedResponse() {
        return response;
    }

    /**
     * Gets the Throwable from this AsyncEvent.
     *
     * @return the Throwable that was used to initialize this AsyncEvent, or null if this AsyncEvent was initialized without
     * any Throwable
     */
    public Throwable getThrowable() {
        return throwable;
    }

}
