/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package jakarta.servlet.http;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CookieTest {
    @SuppressWarnings("removal")
    @Test
    public void testCookie() {
        Cookie cookie = new Cookie("name", "value");
        assertThat(cookie.getName(), is("name"));
        assertThat(cookie.getValue(), is("value"));
        assertThat(cookie.getComment(), nullValue());
        assertThat(cookie.getDomain(), nullValue());
        assertThat(cookie.getMaxAge(), is(-1));
        assertThat(cookie.getPath(), nullValue());
        assertThat(cookie.getSecure(), is(false));
        assertThat(cookie.isHttpOnly(), is(false));
        assertThat(cookie.getVersion(), is(0));
        assertThat(cookie.getAttributes().size(), is(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "=",
            " ",
            "name=value",
            "\377",
    })
    public void testBadCookie(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Cookie(name, "value"));
    }

    @SuppressWarnings("removal")
    @Test
    public void testComment() {
        Cookie cookie = new Cookie("name", "value");
        cookie.setComment("comment");
        assertThat(cookie.getComment(), nullValue());
        assertThat(cookie.getAttributes().size(), is(0));
        cookie.setAttribute("COMMENT", "Comment!");
        assertThat(cookie.getComment(), nullValue());
        assertThat(cookie.getAttributes().keySet(), contains("COMMENT"));
        assertThat(cookie.getAttributes().values(), contains("Comment!"));
        cookie.setAttribute("COMMENT", null);
        assertThat(cookie.getComment(), nullValue());
        assertThat(cookie.getAttributes().size(), is(0));
    }

    @Test
    public void testDomain() {
        Cookie cookie = new Cookie("name", "value");
        cookie.setDomain("domain");
        assertThat(cookie.getDomain(), is("domain"));
        assertThat(cookie.getAttributes().keySet(), contains("Domain"));
        assertThat(cookie.getAttributes().values(), contains("domain"));
        cookie.setAttribute("DOMAIN", "Domain!");
        assertThat(cookie.getDomain(), is("Domain!"));
        assertThat(cookie.getAttributes().keySet(), contains("Domain"));
        assertThat(cookie.getAttributes().values(), contains("Domain!"));
        cookie.setDomain(null);
        assertThat(cookie.getDomain(), nullValue());
        assertThat(cookie.getAttributes().size(), is(0));
    }

    @Test
    public void testMaxAge() {
        Cookie cookie = new Cookie("name", "value");
        cookie.setMaxAge(100);
        assertThat(cookie.getMaxAge(), is(100));
        assertThat(cookie.getAttributes().size(), is(1));
        assertThat(cookie.getAttributes().keySet(), contains("Max-Age"));
        assertThat(cookie.getAttributes().values(), contains("100"));
        cookie.setAttribute("Max-Age", "200");
        assertThat(cookie.getMaxAge(), is(200));
        assertThat(cookie.getAttributes().size(), is(1));
        assertThat(cookie.getAttributes().keySet(), contains("Max-Age"));
        assertThat(cookie.getAttributes().values(), contains("200"));
        cookie.setMaxAge(-1);
        assertThat(cookie.getMaxAge(), is(-1));
        assertThat(cookie.getAttributes().size(), is(0));
        assertThrows(NumberFormatException.class, () -> cookie.setAttribute("max-age", "not-a-number"));
    }

    @Test
    public void testPath() {
        Cookie cookie = new Cookie("name", "value");
        cookie.setPath("path");
        assertThat(cookie.getPath(), is("path"));
        assertThat(cookie.getAttributes().keySet(), contains("Path"));
        assertThat(cookie.getAttributes().values(), contains("path"));
        cookie.setAttribute("PATH", "Path!");
        assertThat(cookie.getPath(), is("Path!"));
        assertThat(cookie.getAttributes().keySet(), contains("Path"));
        assertThat(cookie.getAttributes().values(), contains("Path!"));
        cookie.setPath(null);
        assertThat(cookie.getPath(), nullValue());
        assertThat(cookie.getAttributes().size(), is(0));
    }

    @Test
    public void testSecure() {
        Cookie cookie = new Cookie("name", "value");
        cookie.setSecure(true);
        assertThat(cookie.getSecure(), is(true));
        assertThat(cookie.getAttributes().keySet(), contains("Secure"));
        assertThat(cookie.getAttributes().values(), contains(""));
        cookie.setSecure(false);
        assertThat(cookie.getSecure(), is(false));
        assertThat(cookie.getAttributes().keySet(), empty());
        assertThat(cookie.getAttributes().values(), empty());
        cookie.setAttribute("Secure", "");
        assertThat(cookie.getSecure(), is(true));
        cookie.setAttribute("Secure", "other");
        assertThat(cookie.getAttributes().keySet(), contains("Secure"));
        assertThat(cookie.getAttributes().values(), contains("other"));
        assertThat(cookie.getSecure(), is(false));
        cookie.setAttribute("Secure", null);
        assertThat(cookie.getSecure(), is(false));
        assertThat(cookie.getAttributes().size(), is(0));
    }

    @Test
    public void testValue() {
        Cookie cookie = new Cookie("name", "value");
        assertThat(cookie.getName(), is("name"));
        assertThat(cookie.getValue(), is("value"));
        cookie.setValue("other");
        assertThat(cookie.getValue(), is("other"));
        cookie.setValue(null);
        assertThat(cookie.getValue(), nullValue());
    }

    @SuppressWarnings("removal")
    @Test
    public void testVersion() {
        Cookie cookie = new Cookie("name", "value");
        assertThat(cookie.getVersion(), is(0));
        cookie.setVersion(1);
        assertThat(cookie.getVersion(), is(0));
        assertThat(cookie.getAttributes().size(), is(0));
        cookie.setVersion(Integer.MAX_VALUE);
        assertThat(cookie.getVersion(), is(0));
        assertThat(cookie.getAttributes().size(), is(0));
    }

    @Test
    public void testHttpOnly() {
        Cookie cookie = new Cookie("name", "value");
        cookie.setHttpOnly(true);
        assertThat(cookie.isHttpOnly(), is(true));
        assertThat(cookie.getAttributes().keySet(), contains("HttpOnly"));
        assertThat(cookie.getAttributes().values(), contains(""));
        cookie.setHttpOnly(false);
        assertThat(cookie.isHttpOnly(), is(false));
        assertThat(cookie.getAttributes().keySet(), empty());
        assertThat(cookie.getAttributes().values(), empty());
        cookie.setAttribute("HttpOnly", "");
        assertThat(cookie.isHttpOnly(), is(true));
        cookie.setAttribute("HttpOnly", "other");
        assertThat(cookie.getAttributes().keySet(), contains("HttpOnly"));
        assertThat(cookie.getAttributes().values(), contains("other"));
        assertThat(cookie.isHttpOnly(), is(false));
        cookie.setAttribute("HttpOnly", null);
        assertThat(cookie.isHttpOnly(), is(false));
        assertThat(cookie.getAttributes().size(), is(0));
    }

    @Test
    public void testAttribute() {
        Cookie cookie = new Cookie("name", "value");
        assertThat(cookie.getAttributes().size(), is(0));
        assertThat(cookie.getAttribute("A0"), nullValue());
        cookie.setAttribute("A0", "V0");
        assertThat(cookie.getAttributes().keySet(), contains("A0"));
        assertThat(cookie.getAttributes().values(), contains("V0"));
        assertThat(cookie.getAttribute("A0"), is("V0"));
        cookie.setAttribute("A1", "V1");
        assertThat(cookie.getAttributes().keySet(), contains("A0", "A1"));
        assertThat(cookie.getAttributes().values(), contains("V0", "V1"));
        assertThat(cookie.getAttribute("A0"), is("V0"));
        cookie.setAttribute("a0", "v0");
        assertThat(cookie.getAttributes().keySet(), contains("A0", "A1"));
        assertThat(cookie.getAttributes().values(), contains("v0", "V1"));
        assertThat(cookie.getAttribute("A0"), is("v0"));
        cookie.setAttribute("a0", null);
        assertThat(cookie.getAttributes().keySet(), contains("A1"));
        assertThat(cookie.getAttributes().values(), contains("V1"));
        assertThat(cookie.getAttribute("A0"), nullValue());

        assertThrows(UnsupportedOperationException.class, cookie.getAttributes()::clear);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "\377",
    })
    public void testBadAttribute(String name) {
        Cookie cookie = new Cookie("name", "value");
        assertThrows(IllegalArgumentException.class, () -> cookie.setAttribute(name, "value"));
    }

    @Test
    public void testCloneHashEquals() {
        Cookie cookie = new Cookie("name", "value");
        cookie.setDomain("domain");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(10);
        cookie.setPath("/path");
        cookie.setSecure(false);
        cookie.setAttribute("A0", "V0");

        Cookie clone = (Cookie) cookie.clone();
        assertNotSame(cookie, clone);

        assertThat(clone.getName(), is("name"));
        assertThat(clone.getValue(), is("value"));
        assertThat(clone.getDomain(), is("domain"));
        assertThat(clone.getMaxAge(), is(10));
        assertThat(clone.getPath(), is("/path"));
        assertThat(clone.getSecure(), is(false));
        assertThat(clone.isHttpOnly(), is(true));
        assertThat(clone.getAttributes().size(), is(cookie.getAttributes().size()));

        assertEquals(cookie, clone);
        assertEquals(cookie.hashCode(), clone.hashCode());

        clone.setAttribute("a0", "V0");
        assertEquals(cookie, clone);
        assertEquals(cookie.hashCode(), clone.hashCode());

        clone.setAttribute("a0", "V1");
        assertNotEquals(cookie, clone);
        assertNotEquals(cookie.hashCode(), clone.hashCode());

        clone = (Cookie) cookie.clone();
        assertEquals(cookie, clone);
        assertEquals(cookie.hashCode(), clone.hashCode());
        clone.setValue("x");
        assertNotEquals(cookie, clone);
        assertNotEquals(cookie.hashCode(), clone.hashCode());
    }
}
