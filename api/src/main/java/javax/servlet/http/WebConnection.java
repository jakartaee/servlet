/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

package javax.servlet.http;

import java.io.IOException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;

/**
 * This interface encapsulates the connection for an upgrade request.
 * It allows the protocol handler to send service requests and status
 * queries to the container.
 *
 * @since Servlet 3.1
 */

public interface WebConnection extends AutoCloseable {
    /**
     * Returns an input stream for this web connection.
     *
     * @return a ServletInputStream for reading binary data
     *
     * @exception IOException if an I/O error occurs
     */
    public ServletInputStream getInputStream() throws IOException;

    /**
     * Returns an output stream for this web connection.
     *
     * @return a ServletOutputStream for writing binary data
     *
     * @exception IOException if an I/O error occurs
     */
    public ServletOutputStream getOutputStream() throws IOException;
}
