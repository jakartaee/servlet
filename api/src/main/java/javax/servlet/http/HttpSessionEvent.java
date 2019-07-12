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

/**
 * This is the class representing event notifications for changes to
 * sessions within a web application.
 *
 * @since Servlet 2.3
 */
public class HttpSessionEvent extends java.util.EventObject {

    private static final long serialVersionUID = -7622791603672342895L;

    /**
     * Construct a session event from the given source.
     *
     * @param source the {@link HttpSession} corresponding to this event
     */
    public HttpSessionEvent(HttpSession source) {
        super(source);
    }

    /**
     * Return the session that changed.
     * @return the {@link HttpSession} for this event.
     */
    public HttpSession getSession () { 
        return (HttpSession) super.getSource();
    }
}

