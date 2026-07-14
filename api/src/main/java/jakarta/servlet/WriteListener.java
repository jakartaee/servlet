/*
 * Copyright (c) 2017, 2023 Oracle and/or its affiliates and others.
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
 *
 * Callback notification mechanism that signals to the developer it's possible to write content without blocking.
 *
 * @since Servlet 3.1
 */
public interface WriteListener extends EventListener {

    /**
     * When an instance of the WriteListener is registered with a {@link ServletOutputStream}, this method will be invoked
     * by the container the first time when it is possible to write data. Subsequently the container will invoke this method
     * if and only if the {@link jakarta.servlet.ServletOutputStream#isReady()} method has been called and has returned a
     * value of <code>false</code>. a write operation has subsequently become possible and any previous call to this method
     * has returned to the container.
     *
     * @throws IOException if an I/O related error has occurred during processing
     */
    void onWritePossible() throws IOException;

    /**
     * Invoked when an error occurs writing data after {@link ServletOutputStream#setWriteListener(WriteListener)} has been
     * called. This method will be invoked if there is a problem while data is being written to the stream and either:
     * <ul>
     * <li>{@link ServletOutputStream#isReady()} has been invoked and returned false</li>
     * <li>{@link ServletOutputStream#close()} has been called, and the failure occurred before the response could be fully
     * written to the client</li>
     * </ul>
     *
     * If these conditions are not met and the stream is still open then any failure notification will be delivered either:
     * by an exception thrown from a {@code IO} operation after an invocation of {@link ServletOutputStream#isReady()} has
     * returned {@code true}; or by a call to this method after an invocation of {@link ServletOutputStream#isReady()} has
     * returned {@code false};
     * <p>
     * This method will not be invoked in any circumstances after {@link AsyncListener#onComplete(AsyncEvent)} has been
     * called.
     *
     * @param t the throwable to indicate why the write operation failed
     */
    void onError(final Throwable t);

}
