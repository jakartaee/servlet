/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates and others.
 * All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 * Copyright (c) 2020 Payara Services Ltd.
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

import java.util.Enumeration;

/**
 *
 * @author Various
 *
 * @deprecated As of Java(tm) Servlet API 2.1 for security reasons, with no replacement. This interface will be removed
 * in a future version of this API.
 *
 * @see HttpSession
 * @see HttpSessionBindingEvent
 * @see HttpSessionBindingListener
 *
 */
@Deprecated
public interface HttpSessionContext {

    /**
     *
     * @deprecated As of Java Servlet API 2.1 with no replacement. This method must return null and will be removed in a
     * future version of this API.
     * @param sessionId the id of the session to be returned
     *
     * @return null in all cases
     */
    @Deprecated
    default public HttpSession getSession(String sessionId) {
        return null;
    }

    /**
     *
     * @deprecated As of Java Servlet API 2.1 with no replacement. This method must return an empty <code>Enumeration</code>
     * and will be removed in a future version of this API.
     *
     * @return null
     *
     */
    @Deprecated
    default public Enumeration<String> getIds() {
        return null;
    }
}
