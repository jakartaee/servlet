/*
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
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

package javax.servlet;

import java.util.EventListener;

/**
 * Interface for receiving notification events about requests coming
 * into and going out of scope of a web application.
 *
 * <p>A ServletRequest is defined as coming into scope of a web
 * application when it is about to enter the first servlet or filter
 * of the web application, and as going out of scope as it exits
 * the last servlet or the first filter in the chain.
 *
 * <p>In order to receive these notification events, the implementation
 * class must be either declared in the deployment descriptor of the web
 * application, annotated with {@link javax.servlet.annotation.WebListener}, 
 * or registered via one of the addListener methods defined on
 * {@link ServletContext}.
 *
 * <p>Implementations of this interface are invoked at their
 * {@link #requestInitialized} method in the order in which they have been
 * declared, and at their {@link #requestDestroyed} method in reverse
 * order.
 *
 * @since Servlet 2.4
 */

public interface ServletRequestListener extends EventListener {

    /**
     * Receives notification that a ServletRequest is about to go out
     * of scope of the web application.
     *
     * @param sre the ServletRequestEvent containing the ServletRequest
     * and the ServletContext representing the web application
     *
     * @implSpec
     * The default implementation takes no action.
     */
    default public void requestDestroyed(ServletRequestEvent sre) {}

    /**
     * Receives notification that a ServletRequest is about to come
     * into scope of the web application.
     *
     * @param sre the ServletRequestEvent containing the ServletRequest
     * and the ServletContext representing the web application
     *
     * @implSpec
     * The default implementation takes no action.
     */
    default public void requestInitialized(ServletRequestEvent sre) {}
}
