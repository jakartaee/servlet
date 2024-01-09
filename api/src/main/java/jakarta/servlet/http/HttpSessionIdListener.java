/*
 * Copyright (c) 2017, 2024 Oracle and/or its affiliates and others.
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

package jakarta.servlet.http;

import java.util.EventListener;

/**
 * Interface for receiving notification events about HttpSession id changes.
 *
 * <p>
 * In order to receive these notification events, the implementation class must be either declared in the deployment
 * descriptor of the web application, annotated with {@link jakarta.servlet.annotation.WebListener}, or registered via
 * one of the addListener methods defined on {@link jakarta.servlet.ServletContext}.
 *
 * <p>
 * The order in which implementations of this interface are invoked is unspecified.
 *
 * @since Servlet 3.1
 */
public interface HttpSessionIdListener extends EventListener {

    /**
     * Receives notification that session id has been changed in a session.
     *
     * @param event the HttpSessionEvent containing the session for which the ID changed
     *
     * @param oldSessionId the old session id
     */
    void sessionIdChanged(HttpSessionEvent event, String oldSessionId);

}
