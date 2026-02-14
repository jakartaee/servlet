/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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

package ee.jakarta.servlet.http;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

public class HttpServletResponseTooManyRequestsTest {

    @Test
    public void testScTooManyRequestsValue() {
        assertThat(HttpServletResponse.SC_TOO_MANY_REQUESTS, is(429));
    }

    @Test
    public void testScTooManyRequestsIsDistinct() {
        assertNotEquals(HttpServletResponse.SC_OK, HttpServletResponse.SC_TOO_MANY_REQUESTS);
        assertNotEquals(HttpServletResponse.SC_BAD_REQUEST, HttpServletResponse.SC_TOO_MANY_REQUESTS);
        assertNotEquals(HttpServletResponse.SC_UNAUTHORIZED, HttpServletResponse.SC_TOO_MANY_REQUESTS);
        assertNotEquals(HttpServletResponse.SC_FORBIDDEN, HttpServletResponse.SC_TOO_MANY_REQUESTS);
    }
}