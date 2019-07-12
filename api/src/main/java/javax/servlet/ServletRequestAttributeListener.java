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
 * Interface for receiving notification events about ServletRequest
 * attribute changes.
 *
 * <p>Notifications will be generated while the request
 * is within the scope of the web application. A ServletRequest
 * is defined as coming into scope of a web application when it
 * is about to enter the first servlet or filter of the web
 * application, and as going out of scope when it exits the last
 * servlet or the first filter in the chain.
 *
 * <p>In order to receive these notification events, the implementation
 * class must be either declared in the deployment descriptor of the web
 * application, annotated with {@link javax.servlet.annotation.WebListener},
 * or registered via one of the addListener methods defined on
 * {@link ServletContext}.
 *
 * <p>The order in which implementations of this interface are invoked is
 * unspecified.
 *
 * @since Servlet 2.4
 */

public interface ServletRequestAttributeListener extends EventListener {

    /**
     * Receives notification that an attribute has been added to the
     * ServletRequest.
     *
     * @param srae the ServletRequestAttributeEvent containing the 
     * ServletRequest and the name and value of the attribute that was
     * added
     *
     * @implSpec
     * The default implementation takes no action.
     */
    default public void attributeAdded(ServletRequestAttributeEvent srae) {}

    /**
     * Receives notification that an attribute has been removed from the
     * ServletRequest.
     *
     * @param srae the ServletRequestAttributeEvent containing the 
     * ServletRequest and the name and value of the attribute that was
     * removed
     *
     * @implSpec
     * The default implementation takes no action.
     */
    default public void attributeRemoved(ServletRequestAttributeEvent srae) {}

    /**
     * Receives notification that an attribute has been replaced on the
     * ServletRequest.
     *
     * @param srae the ServletRequestAttributeEvent containing the 
     * ServletRequest and the name and (old) value of the attribute
     * that was replaced
     *
     * @implSpec
     * The default implementation takes no action.
     */
    default public void attributeReplaced(ServletRequestAttributeEvent srae) {}
}

