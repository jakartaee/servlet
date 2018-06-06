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
 * attribute changes.
 *
 * <p>In order to receive these notification events, the implementation
 * class must be either declared in the deployment descriptor of the web
 * application, annotated with {@link javax.servlet.annotation.WebListener},
 * or registered via one of the addListener methods defined on
 * {@link javax.servlet.ServletContext}.
 *
 * <p>The order in which implementations of this interface are invoked is
 * unspecified.
 *
 * @since Servlet 2.3
 */

public interface HttpSessionAttributeListener extends EventListener {

    /**
     * Receives notification that an attribute has been added to a
     * session.
     *
     * @param event the HttpSessionBindingEvent containing the session
     * and the name and value of the attribute that was added
     */
    default public void attributeAdded(HttpSessionBindingEvent event) {}

    /**
     * Receives notification that an attribute has been removed from a
     * session.
     *
     * @param event the HttpSessionBindingEvent containing the session
     * and the name and value of the attribute that was removed
     */
    default public void attributeRemoved(HttpSessionBindingEvent event) {}

    /**
     * Receives notification that an attribute has been replaced in a
     * session.
     *
     * @param event the HttpSessionBindingEvent containing the session
     * and the name and (old) value of the attribute that was replaced
     */
    default public void attributeReplaced(HttpSessionBindingEvent event) {}

}

