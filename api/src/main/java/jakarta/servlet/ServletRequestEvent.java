/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates and others.
 * All rights reserved.
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

package jakarta.servlet;

/**
 * Events of this kind indicate lifecycle events for a ServletRequest. The source of the event is the ServletContext of
 * this web application.
 *
 * @see ServletRequestListener
 * @since Servlet 2.4
 */
public class ServletRequestEvent extends java.util.EventObject {

    private static final long serialVersionUID = -7467864054698729101L;

    private final transient ServletRequest request;

    /**
     * Construct a ServletRequestEvent for the given ServletContext and ServletRequest.
     *
     * @param sc the ServletContext of the web application.
     * @param request the ServletRequest that is sending the event.
     */
    public ServletRequestEvent(ServletContext sc, ServletRequest request) {
        super(sc);
        this.request = request;
    }

    /**
     * Returns the ServletRequest that is changing.
     *
     * @return the {@link ServletRequest} corresponding to this event.
     */
    public ServletRequest getServletRequest() {
        return this.request;
    }

    /**
     * Returns the ServletContext of this web application.
     *
     * @return the {@link ServletContext} for this web application.
     */
    public ServletContext getServletContext() {
        return (ServletContext) super.getSource();
    }
}
