/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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
 * Provides information about the connection made to the Servlet container. This interface is intended primarily for
 * debugging purposes and as such provides the raw information as seen by the container. Unless explicitly stated
 * otherwise in the Javadoc for a method, no adjustment is made for the presence of reverse proxies or similar
 * configurations.
 * 
 * @since Servlet 6.0
 */
public interface ServletConnection {

    /**
     * Obtain a unique (within the lifetime of the JVM) identifier string for the network connection to the JVM that is
     * being used for the {@code ServletRequest} from which this {@code ServletConnection} was obtained.
     * <p>
     * There is no defined format for this string. The format is implementation dependent.
     * 
     * @return A unique identifier for the network connection
     */
    String getConnectionId();

    /**
     * Obtain a unique (within the lifetime of the JVM) identifier string for the {@code ServletRequest} from which this
     * {@code ServletConnection} was obtained.
     * <p>
     * There is no defined format for this string. The format is implementation dependent.
     * 
     * @return A unique identifier for the servlet request
     */
    String getRequestId();

    /**
     * Obtain the name of the protocol as presented to the JVM after the removal, if present, of any TLS encryption. This
     * may not be the same as the protocol seen by the application. For example, a reverse proxy may present AJP whereas the
     * application will see HTTP 1.1.
     * <p>
     * If the protocol has an entry in the <a href=
     * "https://www.iana.org/assignments/tls-extensiontype-values/tls-extensiontype-values.xhtml#alpn-protocol-ids">IANA
     * registry for ALPN names then the identification sequence, in string form, must be returned. Registered identification
     * sequences MUST only be used for the associated protocol. Return values for other protocols are implementation
     * dependent. Unknown protocols should return the string "unknown".
     * 
     * @return The name of the protocol presented to the JVM after decryption of TLS, if any.
     */
    String getProtocol();

    /**
     * Obtain the connection identifier for the network connection to the JVM that is being used for the
     * {@code ServletRequest} from which this {@code ServletConnection} was obtained as defined by the protocol in use. Note
     * that some protocols do not define such an identifier.
     * 
     * @return The connection identifier if one is defined, otherwise an empty string
     */
    String getProtocolConnectionId();

    /**
     * Obtain the request identifier for the {@code ServletRequest} from which this {@code ServletConnection} was obtained
     * as defined by the protocol in use. Note that some protocols do not define such an identifier.
     * 
     * @return The request identifier if one is defined, otherwise an empty string
     */
    String getProtocolRequestId();

    /**
     * Determine whether or not the incoming network connection to the JVM used TLS or not. Note that where a reverse proxy
     * is used, the application may have a different view as to whether TLS is being used due to the use of headers like
     * {@code X-Forwarded-Proto}
     * 
     * @return {@code true} if the incoming network connection used TLS, otherwise {@code false}
     */
    boolean getTLS();
}
