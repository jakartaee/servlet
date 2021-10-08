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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MockServletConfig implements ServletConfig {
    private final String name;
    private final ServletContext servletContext;
    private final Map<String, String> params;

    public MockServletConfig() {
        this(null, null, null);
    }

    public MockServletConfig(String name) {
        this(name, null, null);
    }

    public MockServletConfig(String name, ServletContext servletContext, Map<String, String> params) {
        this.name = name == null ? "Servlet" : name;
        this.servletContext = servletContext == null ? new MockServletContext() : servletContext;
        this.params = params == null ? new HashMap<>() : params;
    }

    @Override
    public String getServletName() {
        return null;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public String getInitParameter(String name) {
        return params.get(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return Collections.enumeration(params.keySet());
    }

    public void setInitParameter(String name, String value) {
        params.put(name, value);
    }
}
