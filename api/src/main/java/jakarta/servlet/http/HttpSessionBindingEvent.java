/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates and others.
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

package jakarta.servlet.http;

/**
 *
 * Events of this type are either sent to an object that implements {@link HttpSessionBindingListener} when it is bound
 * or unbound from a session, or to an {@link HttpSessionAttributeListener} that has been configured in the deployment
 * descriptor when any attribute is bound, unbound or replaced in a session.
 *
 * <p>
 * The session binds the object by a call to <code>HttpSession.setAttribute</code> and unbinds the object by a call to
 * <code>HttpSession.removeAttribute</code>.
 *
 * @author Various
 *
 * @see HttpSession
 * @see HttpSessionBindingListener
 * @see HttpSessionAttributeListener
 */
public class HttpSessionBindingEvent extends HttpSessionEvent {

    private static final long serialVersionUID = 7308000419984825907L;

    /* The name to which the object is being bound or unbound */
    private final String name;

    /* The object is being bound or unbound */
    private Object value;

    /**
     *
     * Constructs an event that notifies an object that it has been bound to or unbound from a session. To receive the
     * event, the object must implement {@link HttpSessionBindingListener}.
     *
     * @param session the session to which the object is bound or unbound
     * @param name the name with which the object is bound or unbound
     *
     * @see #getName
     * @see #getSession
     */
    public HttpSessionBindingEvent(HttpSession session, String name) {
        super(session);
        this.name = name;
    }

    /**
     *
     * Constructs an event that notifies an object that it has been bound to or unbound from a session. To receive the
     * event, the object must implement {@link HttpSessionBindingListener}.
     *
     * @param session the session to which the object is bound or unbound
     * @param name the name with which the object is bound or unbound
     * @param value the object that is bound or unbound
     *
     * @see #getName
     * @see #getSession
     */
    public HttpSessionBindingEvent(HttpSession session, String name, Object value) {
        super(session);
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the name with which the attribute is bound to or unbound from the session.
     *
     * @return a string specifying the name with which the object is bound to or unbound from the session
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of the attribute that has been added, removed or replaced. If the attribute was added (or bound),
     * this is the value of the attribute. If the attribute was removed (or unbound), this is the value of the removed
     * attribute. If the attribute was replaced, this is the old value of the attribute.
     *
     * @return the value of the attribute that has been added, removed or replaced
     *
     * @since Servlet 2.3
     */
    public Object getValue() {
        return this.value;
    }
}
