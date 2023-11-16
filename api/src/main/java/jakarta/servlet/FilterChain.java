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

import java.io.IOException;

/**
 * A FilterChain is an object provided by the servlet container to the developer giving a view into the invocation chain
 * of a filtered request for a resource. Filters use the FilterChain to invoke the next filter in the chain, or if the
 * calling filter is the last filter in the chain, to invoke the resource at the end of the chain.
 *
 * @see Filter
 * @since Servlet 2.3
 */
public interface FilterChain {

    /**
     * Causes the next filter in the chain to be invoked, or if the calling filter is the last filter in the chain, causes
     * the resource at the end of the chain to be invoked.
     *
     * @param request the request to pass along the chain.
     * @param response the response to pass along the chain.
     * @throws IOException if an I/O related error has occurred during the processing
     * @throws ServletException if an exception has occurred that interferes with the filterChain's normal operation
     */
    void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException;

}
