/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
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

package ee.jakarta.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
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
        return name;
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
