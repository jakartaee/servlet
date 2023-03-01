/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates and others.
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

import java.io.IOException;
import java.util.EventListener;

/**
 * Listener that will be notified in the event that an asynchronous operation initiated on a ServletRequest to which the
 * listener had been added has completed, timed out, or resulted in an error.
 *
 * @since Servlet 3.0
 */
public interface AsyncListener extends EventListener {

    /**
     * Notifies this AsyncListener that an asynchronous operation has been completed.
     *
     * <p>
     * The {@link AsyncContext} corresponding to the asynchronous operation that has been completed may be obtained by
     * calling {@link AsyncEvent#getAsyncContext getAsyncContext} on the given <tt>event</tt>.
     *
     * <p>
     * In addition, if this AsyncListener had been registered via a call to
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}, the supplied ServletRequest and
     * ServletResponse objects may be retrieved by calling {@link AsyncEvent#getSuppliedRequest getSuppliedRequest} and
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse}, respectively, on the given <tt>event</tt>.
     *
     * @param event the AsyncEvent indicating that an asynchronous operation has been completed
     *
     * @throws IOException if an I/O related error has occurred during the processing of the given AsyncEvent
     */
    public void onComplete(AsyncEvent event) throws IOException;

    /**
     * Notifies this AsyncListener that an asynchronous operation has timed out.
     *
     * <p>
     * The {@link AsyncContext} corresponding to the asynchronous operation that has timed out may be obtained by calling
     * {@link AsyncEvent#getAsyncContext getAsyncContext} on the given <tt>event</tt>.
     *
     * <p>
     * In addition, if this AsyncListener had been registered via a call to
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}, the supplied ServletRequest and
     * ServletResponse objects may be retrieved by calling {@link AsyncEvent#getSuppliedRequest getSuppliedRequest} and
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse}, respectively, on the given <tt>event</tt>.
     *
     * @param event the AsyncEvent indicating that an asynchronous operation has timed out
     *
     * @throws IOException if an I/O related error has occurred during the processing of the given AsyncEvent
     */
    public void onTimeout(AsyncEvent event) throws IOException;

    /**
     * Notifies this AsyncListener that an asynchronous operation has failed to complete.
     *
     * <p>
     * The {@link AsyncContext} corresponding to the asynchronous operation that failed to complete may be obtained by
     * calling {@link AsyncEvent#getAsyncContext getAsyncContext} on the given <tt>event</tt>.
     *
     * <p>
     * In addition, if this AsyncListener had been registered via a call to
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}, the supplied ServletRequest and
     * ServletResponse objects may be retrieved by calling {@link AsyncEvent#getSuppliedRequest getSuppliedRequest} and
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse}, respectively, on the given <tt>event</tt>.
     *
     * @param event the AsyncEvent indicating that an asynchronous operation has failed to complete
     *
     * @throws IOException if an I/O related error has occurred during the processing of the given AsyncEvent
     */
    public void onError(AsyncEvent event) throws IOException;

    /**
     * Notifies this AsyncListener that a new asynchronous cycle is being initiated via a call to one of the
     * {@link ServletRequest#startAsync} methods.
     *
     * <p>
     * The {@link AsyncContext} corresponding to the asynchronous operation that is being reinitialized may be obtained by
     * calling {@link AsyncEvent#getAsyncContext getAsyncContext} on the given <tt>event</tt>.
     *
     * <p>
     * In addition, if this AsyncListener had been registered via a call to
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}, the supplied ServletRequest and
     * ServletResponse objects may be retrieved by calling {@link AsyncEvent#getSuppliedRequest getSuppliedRequest} and
     * {@link AsyncEvent#getSuppliedResponse getSuppliedResponse}, respectively, on the given <tt>event</tt>.
     *
     * <p>
     * This AsyncListener will not receive any events related to the new asynchronous cycle unless it registers itself (via
     * a call to {@link AsyncContext#addListener}) with the AsyncContext that is delivered as part of the given AsyncEvent.
     *
     * @param event the AsyncEvent indicating that a new asynchronous cycle is being initiated
     *
     * @throws IOException if an I/O related error has occurred during the processing of the given AsyncEvent
     */
    public void onStartAsync(AsyncEvent event) throws IOException;

}
