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

package javax.servlet.http;

import java.util.EventListener;

/** 
 * Interface for receiving notification events about HttpSession
 * lifecycle changes.
 *
 * <p>In order to receive these notification events, the implementation
 * class must be either declared in the deployment descriptor of the web
 * application, annotated with {@link javax.servlet.annotation.WebListener},
 * or registered via one of the addListener methods defined on
 * {@link javax.servlet.ServletContext}.
 *
 * <p>Implementations of this interface are invoked at their
 * {@link #sessionCreated} method in the order in which they have been
 * declared, and at their {@link #sessionDestroyed} method in reverse
 * order.
 *
 * @see HttpSessionEvent
 *
 * @since Servlet 2.3
 */
public interface HttpSessionListener extends EventListener {
    
    /** 
     * Receives notification that a session has been created.
     *
     * @implSpec
     * The default implementation takes no action.
     *
     * @param se the HttpSessionEvent containing the session
     */
    default public void sessionCreated(HttpSessionEvent se) {}
    
    /** 
     * Receives notification that a session is about to be invalidated.
     *
     * @implSpec
     * The default implementation takes no action.
     *
     * @param se the HttpSessionEvent containing the session
     */
    default public void sessionDestroyed(HttpSessionEvent se) {}
}
